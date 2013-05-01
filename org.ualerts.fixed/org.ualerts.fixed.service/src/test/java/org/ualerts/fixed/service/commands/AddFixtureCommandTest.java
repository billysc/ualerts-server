/*
 * File created on May 1, 2013
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

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.ualerts.fixed.domain.Asset;
import org.ualerts.fixed.domain.Building;
import org.ualerts.fixed.domain.Fixture;
import org.ualerts.fixed.domain.PositionHint;
import org.ualerts.fixed.domain.Room;
import org.ualerts.fixed.repository.FixedRepository;

/**
 * Unit tests for {@link AddFixtureCommand}.
 *
 * @author Brian Early
 */
public class AddFixtureCommandTest {
  private Mockery context;
  private FixedRepository repository;
  private AddFixtureCommand command;

  /**
   * @throws java.lang.Exception
   */
  @Before
  public void setUp() throws Exception {
    context = new Mockery();
    repository = context.mock(FixedRepository.class);
    command = new AddFixtureCommand();
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
   * Test method for {@link org.ualerts.fixed.service.commands.AddFixtureCommand#onValidate()}.
   */
  @Test
  public void testOnValidate() throws Exception {
    final Building building = new Building();
    populateCommand(command);
    context.checking(new Expectations() {{
      oneOf(repository).findBuildingById("buildingId");
      will(returnValue(building));
    }});
    command.onValidate();
    context.assertIsSatisfied();
  }
  
  /**
   * Test method for {@link org.ualerts.fixed.service.commands.AddFixtureCommand#onExecute()}.
   */
  @Test
  public void testOnExecute() throws Exception {
    final Room room = new Room();
    final PositionHint positionHint = new PositionHint();
    populateCommand(command);
    
    context.checking(new Expectations() {{
      oneOf(repository).findRoom("buildingId", "roomNumber");
      will(returnValue(room));
      oneOf(repository).findHint("hint");
      will(returnValue(positionHint));
      oneOf(repository).addAsset(with(any(Asset.class)));
      oneOf(repository).addFixture(with(any(Fixture.class)));
    }});
    command.onExecute();
    context.assertIsSatisfied();
  }

  private void populateCommand(AddFixtureCommand command) {
    command.setRoomNumber("roomNumber");
    command.setBuildingId("buildingId");
    command.setPositionHint("hint");
    command.setIpAddress("127.0.0.1");
    command.setMacAddress("0A-1B-2C-3D-4E-5F");
    command.setSerialNumber("serialNumber");
    command.setInventoryNumber("inventoryNumber");
  }
  
}
