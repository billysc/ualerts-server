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

import java.util.ArrayList;
import java.util.List;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.ualerts.fixed.Fixture;
import org.ualerts.fixed.repository.FixtureRepository;

/**
 * Unit tests for {@link FindAllFixturesCommand}.
 *
 * @author Brian Early
 */
public class FindAllFixturesCommandTest {
  private Mockery context;
  private FixtureRepository fixtureRepository;
  private FindAllFixturesCommand command;

  /**
   * @throws java.lang.Exception
   */
  @Before
  public void setUp() throws Exception {
    context = new Mockery();
    fixtureRepository = context.mock(FixtureRepository.class);
    command = new FindAllFixturesCommand();
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
   * {@link FindAllFixturesCommand#onExecute()}.
   */
  @Test
  public void testOnExecute() throws Exception {
    final List<Fixture> fixtures = new ArrayList<Fixture>();
    context.checking(new Expectations() { {
      oneOf(fixtureRepository).findAllFixtures();
      will(returnValue(fixtures));
    } });
    command.onExecute();
    context.assertIsSatisfied();
  }

}
