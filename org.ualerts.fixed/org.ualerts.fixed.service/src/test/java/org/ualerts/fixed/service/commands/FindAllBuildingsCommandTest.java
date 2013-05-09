/*
 * File created on May 8, 2013
 *
 * Copyright 2008-2011 Virginia Polytechnic Institute and State University
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package org.ualerts.fixed.service.commands;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.ualerts.fixed.Building;
import org.ualerts.fixed.repository.BuildingRepository;
import org.ualerts.fixed.service.errors.UnspecifiedConstraintException;

/**
 * Unit tests for {@link FindAllBuildingsCommand}.
 *
 * @author Brian Early
 */
public class FindAllBuildingsCommandTest {
  private Mockery context;
  private BuildingRepository repository;
  private FindAllBuildingsCommand command;

  /**
   * @throws java.lang.Exception
   */
  @Before
  public void setUp() throws Exception {
    context = new Mockery();
    repository = context.mock(BuildingRepository.class);
    command = new FindAllBuildingsCommand();
    command.setRepository(repository);
  }

  /**
   * @throws java.lang.Exception
   */
  @After
  public void tearDown() throws Exception {
    command = null;
    repository = null;
  }

  /**
   * Test method for
   * {@link org.ualerts.fixed.service.commands
   *     .FindAllBuildingsCommand#onExecute()}.
   */
  @Test
  public void testOnExecute() throws Exception {
    final List<Building> buildings = new ArrayList<Building>();
    context.checking(new Expectations() { {
      oneOf(repository).findAllBuildings();
      will(returnValue(buildings));
    } });
    command.onExecute();
    context.assertIsSatisfied();
  }

  /**
   * Test method for
   * {@link org.ualerts.fixed.service.commands
   *     .FindAllBuildingsCommand#onExecute()}.
   */
  @Test(expected = UnspecifiedConstraintException.class)
  public void testOnExecuteWithException() throws Exception {
    try {
      context.checking(new Expectations() { {
        oneOf(repository).findAllBuildings();
        will(throwException(new PersistenceException("GAH!")));
      } });
      command.onExecute();
      assertTrue(false);
    }
    catch (PersistenceException ex) {
      context.assertIsSatisfied();
    }
  }

}
