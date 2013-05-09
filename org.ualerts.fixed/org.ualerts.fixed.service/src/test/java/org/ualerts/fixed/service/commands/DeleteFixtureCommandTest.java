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

import static org.junit.Assert.fail;

import javax.persistence.PersistenceException;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.ualerts.fixed.Fixture;
import org.ualerts.fixed.repository.EntityNotFoundException;
import org.ualerts.fixed.repository.FixtureRepository;

/**
 * Unit tests for {@link DeleteFixtureCommand}.
 *
 * @author Brian Early
 */
public class DeleteFixtureCommandTest {
  private Mockery context;
  private FixtureRepository fixtureRepository;
  private DeleteFixtureCommand command;

  /**
   * @throws java.lang.Exception
   */
  @Before
  public void setUp() throws Exception {
    context = new Mockery();
    fixtureRepository = context.mock(FixtureRepository.class);
    command = new DeleteFixtureCommand();
    command.setFixtureRepository(fixtureRepository);
  }

  /**
   * @throws java.lang.Exception
   */
  @After
  public void tearDown() throws Exception {
    command = null;
    fixtureRepository = null;
  }

  /**
   * Test method for
   * {@link DeleteFixtureCommand#onValidate()}.
   */
  @Test
  public void testOnValidate() throws Exception {
    command.setId(1L);
    command.onValidate();
  }

  /**
   * Test method for
   * {@link DeleteFixtureCommand#onValidate()}.
   */
  @Test(expected = Exception.class)
  public void testOnValidateMissingId() throws Exception {
    command.onValidate();
  }

  /**
   * Test method for
   * {@link DeleteFixtureCommand#onExecute()}.
   */
  @Test
  public void testOnExecute() throws Exception {
    final Fixture fixture = new Fixture();
    command.setId(1L);
    context.checking(new Expectations() { {
      oneOf(fixtureRepository).findFixtureById(1L);
      will(returnValue(fixture));
      oneOf(fixtureRepository).deleteFixture(with(any(Fixture.class)));
    } });
    command.onExecute();
    context.assertIsSatisfied();
  }

  /**
   * Test method for
   * {@link DeleteFixtureCommand#onExecute()}.
   */
  @Test
  public void testOnExecuteWithExceptionOnFind() throws Exception {
    try {
      command.setId(1L);
      context.checking(new Expectations() { {
        oneOf(fixtureRepository).findFixtureById(1L);
        will(throwException(new EntityNotFoundException(Fixture.class, 1L)));
      } });
      command.onExecute();
      fail("Did not receive expected exception.");
    }
    catch (EntityNotFoundException ex) {
      context.assertIsSatisfied();
    }
  }

  /**
   * Test method for
   * {@link DeleteFixtureCommand#onExecute()}.
   */
  @Test
  public void testOnExecuteWithExceptionOnDelete() throws Exception {
    final Fixture fixture = new Fixture();
    try {
      command.setId(1L);
      context.checking(new Expectations() { {
        oneOf(fixtureRepository).findFixtureById(1L);
        will(returnValue(fixture));
        oneOf(fixtureRepository).deleteFixture(with(any(Fixture.class)));
        will(throwException(new PersistenceException("GAH!")));
      } });
      command.onExecute();
      fail("Did not receive expected exception.");
    }
    catch (PersistenceException ex) {
      context.assertIsSatisfied();
    }
  }
  
}
