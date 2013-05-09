/*
 * File created on May 9, 2013
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

import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.ualerts.fixed.Room;
import org.ualerts.fixed.repository.RoomRepository;
import org.ualerts.fixed.service.errors.UnspecifiedConstraintException;

/**
 * Unit tests for {@link FindRoomsForBuildingCommand}.
 *
 * @author Brian Early
 */
public class FindRoomsForBuildingCommandTest {
  private Mockery context;
  private RoomRepository repository;
  private FindRoomsForBuildingCommand command;

  /**
   * @throws java.lang.Exception
   */
  @Before
  public void setUp() throws Exception {
    context = new Mockery();
    repository = context.mock(RoomRepository.class);
    command = new FindRoomsForBuildingCommand();
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
   *     .FindRoomsForBuildingCommand#onValidate()}.
   */
  @Test
  public void testOnValidate() throws Exception {
    command.setBuildingId("BLDG1");
    command.onValidate();
  }

  /**
   * Test method for
   * {@link org.ualerts.fixed.service.commands
   *     .FindRoomsForBuildingCommand#onValidate()}.
   */
  @Test(expected = Exception.class)
  public void testOnValidateMissingBuildingId() throws Exception {
    command.onValidate();
  }

  /**
   * Test method for
   * {@link org.ualerts.fixed.service.commands
   *     .FindRoomsForBuildingCommand#onExecute()}.
   */
  @Test
  public void testOnExecute() throws Exception {
    final List<Room> rooms = new ArrayList<Room>();
    command.setBuildingId("BLDG1");
    context.checking(new Expectations() { {
      oneOf(repository).findRoomsForBuilding("BLDG1");
      will(returnValue(rooms));
    } });
    command.onExecute();
    context.assertIsSatisfied();
  }

  /**
   * Test method for
   * {@link org.ualerts.fixed.service.commands
   *     .FindRoomsForBuildingCommand#onExecute()}.
   */
  @Test(expected = UnspecifiedConstraintException.class)
  public void testOnExecuteWithException() throws Exception {
    command.setBuildingId("BLDG1");
    try {
      context.checking(new Expectations() { {
        oneOf(repository).findRoomsForBuilding("BLDG1");
        will(throwException(new PersistenceException("GAH!")));
      } });
      command.onExecute();
      Assert.fail("Did not receive the expected exception.");
    }
    catch (PersistenceException ex) {
      context.assertIsSatisfied();
    }
  }

}
