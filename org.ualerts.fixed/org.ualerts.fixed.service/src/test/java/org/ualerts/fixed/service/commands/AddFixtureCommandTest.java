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

import static org.junit.Assert.assertTrue;

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
import org.ualerts.fixed.service.InvalidRequestException;

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
      oneOf(repository).findAssetByInventoryNumber("inventoryNumber");
      will(returnValue(null));
      oneOf(repository).findAssetBySerialNumber("serialNumber");
      will(returnValue(null));
      oneOf(repository).findAssetByMacAddress("0A-1B-2C-3D-4E-5F");
      will(returnValue(null));
      oneOf(repository).findRoom("buildingId", "roomNumber");
      will(returnValue(null));
      oneOf(repository).findHint("hint");
      will(returnValue(null));
    }});
    command.onValidate();
    context.assertIsSatisfied();
  }

  /**
   * Test method for {@link org.ualerts.fixed.service.commands.AddFixtureCommand#onValidate()} with missing building.
   */
  @Test(expected = InvalidRequestException.class)
  public void testOnValidateBlankBuilding() throws Exception {
    populateCommand(command);
    command.setBuildingId(null);
    command.onValidate();
  }

  /**
   * Test method for {@link org.ualerts.fixed.service.commands.AddFixtureCommand#onValidate()} with missing inventory number.
   */
  @Test(expected = InvalidRequestException.class)
  public void testOnValidateBlankInventory() throws Exception {
    populateCommand(command);
    command.setInventoryNumber(null);
    command.onValidate();
  }

  /**
   * Test method for {@link org.ualerts.fixed.service.commands.AddFixtureCommand#onValidate()} with missing IP address.
   */
  @Test(expected = InvalidRequestException.class)
  public void testOnValidateBlankIpAddress() throws Exception {
    populateCommand(command);
    command.setIpAddress(null);
    command.onValidate();
  }

  /**
   * Test method for {@link org.ualerts.fixed.service.commands.AddFixtureCommand#onValidate()} with missing MAC address.
   */
  @Test(expected = InvalidRequestException.class)
  public void testOnValidateBlankMacAddress() throws Exception {
    populateCommand(command);
    command.setMacAddress(null);
    command.onValidate();
  }

  /**
   * Test method for {@link org.ualerts.fixed.service.commands.AddFixtureCommand#onValidate()} with missing position hint.
   */
  @Test(expected = InvalidRequestException.class)
  public void testOnValidateBlankPositionHint() throws Exception {
    populateCommand(command);
    command.setPositionHint(null);
    command.onValidate();
  }

  /**
   * Test method for {@link org.ualerts.fixed.service.commands.AddFixtureCommand#onValidate()} with missing room.
   */
  @Test(expected = InvalidRequestException.class)
  public void testOnValidateBlankRoom() throws Exception {
    populateCommand(command);
    command.setRoomNumber(null);
    command.onValidate();
  }

  /**
   * Test method for {@link org.ualerts.fixed.service.commands.AddFixtureCommand#onValidate()} with missing serial number.
   */
  @Test(expected = InvalidRequestException.class)
  public void testOnValidateBlankSerialNumber() throws Exception {
    populateCommand(command);
    command.setSerialNumber(null);
    command.onValidate();
  }

  /**
   * Test method for {@link org.ualerts.fixed.service.commands.AddFixtureCommand#onValidate()} with invalid IP address.
   */
  @Test(expected = InvalidRequestException.class)
  public void testOnValidateInvalidIpAddress() throws Exception {
    populateCommand(command);
    command.setIpAddress("300.4.2");
    command.onValidate();
  }

  /**
   * Test method for {@link org.ualerts.fixed.service.commands.AddFixtureCommand#onValidate()} with invalid MAC address.
   */
  @Test(expected = InvalidRequestException.class)
  public void testOnValidateInvalidMacAddress() throws Exception {
    populateCommand(command);
    command.setMacAddress("0A-1B-2C-3D-4E");
    command.onValidate();
  }

  /**
   * Test method for {@link org.ualerts.fixed.service.commands.AddFixtureCommand#onValidate()} with a bad building ID.
   */
  @Test
  public void testOnValidateBadBuildingId() throws Exception {
    try {
      populateCommand(command);
      context.checking(new Expectations() {{
        oneOf(repository).findBuildingById("buildingId");
        will(returnValue(null));
      }});
      command.onValidate();
      assertTrue(false);
    }
    catch (InvalidRequestException ex) {
      context.assertIsSatisfied();
    }
  }

  /**
   * Test method for {@link org.ualerts.fixed.service.commands.AddFixtureCommand#onValidate()} with an inventory number conflict.
   */
  @Test
  public void testOnValidateInventoryNumberConflict() throws Exception {
    final Building building = new Building();
    final Asset asset = new Asset();
    try {
      populateCommand(command);
      context.checking(new Expectations() {{
        oneOf(repository).findBuildingById("buildingId");
        will(returnValue(building));
        oneOf(repository).findAssetByInventoryNumber("inventoryNumber");
        will(returnValue(asset));
      }});
      command.onValidate();
      assertTrue(false);
    }
    catch (InvalidRequestException ex) {
      context.assertIsSatisfied();
    }
  }

  /**
   * Test method for {@link org.ualerts.fixed.service.commands.AddFixtureCommand#onValidate()} with a serial number conflict.
   */
  @Test
  public void testOnValidateSerialNumberConflict() throws Exception {
    final Building building = new Building();
    final Asset asset = new Asset();
    try {
      populateCommand(command);
      context.checking(new Expectations() {{
        oneOf(repository).findBuildingById("buildingId");
        will(returnValue(building));
        oneOf(repository).findAssetByInventoryNumber("inventoryNumber");
        will(returnValue(null));
        oneOf(repository).findAssetBySerialNumber("serialNumber");
        will(returnValue(asset));
      }});
      command.onValidate();
      assertTrue(false);
    }
    catch (InvalidRequestException ex) {
      context.assertIsSatisfied();
    }
  }

  /**
   * Test method for {@link org.ualerts.fixed.service.commands.AddFixtureCommand#onValidate()} with a MAC address conflict.
   */
  @Test
  public void testOnValidateMacAddressConflict() throws Exception {
    final Building building = new Building();
    final Asset asset = new Asset();
    try {
      populateCommand(command);
      context.checking(new Expectations() {{
        oneOf(repository).findBuildingById("buildingId");
        will(returnValue(building));
        oneOf(repository).findAssetByInventoryNumber("inventoryNumber");
        will(returnValue(null));
        oneOf(repository).findAssetBySerialNumber("serialNumber");
        will(returnValue(null));
        oneOf(repository).findAssetByMacAddress("0A-1B-2C-3D-4E-5F");
        will(returnValue(asset));
      }});
      command.onValidate();
      assertTrue(false);
    }
    catch (InvalidRequestException ex) {
      context.assertIsSatisfied();
    }
  }

  /**
   * Test method for {@link org.ualerts.fixed.service.commands.AddFixtureCommand#onValidate()} with a location conflict.
   */
  @Test
  public void testOnValidateLocationConflict() throws Exception {
    final Building building = new Building();
    final Room room = new Room();
    room.setId(1L);
    final PositionHint hint = new PositionHint();
    hint.setId(2L);
    final Fixture fixture = new Fixture();
    try {
      populateCommand(command);
      context.checking(new Expectations() {{
        oneOf(repository).findBuildingById("buildingId");
        will(returnValue(building));
        oneOf(repository).findAssetByInventoryNumber("inventoryNumber");
        will(returnValue(null));
        oneOf(repository).findAssetBySerialNumber("serialNumber");
        will(returnValue(null));
        oneOf(repository).findAssetByMacAddress("0A-1B-2C-3D-4E-5F");
        will(returnValue(null));
        oneOf(repository).findRoom("buildingId", "roomNumber");
        will(returnValue(room));
        oneOf(repository).findHint("hint");
        will(returnValue(hint));
        oneOf(repository).findFixtureByLocation(1L, 2L);
        will(returnValue(fixture));
     }});
      command.onValidate();
      assertTrue(false);
    }
    catch (InvalidRequestException ex) {
      context.assertIsSatisfied();
    }
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

  /**
   * Test method for {@link org.ualerts.fixed.service.commands.AddFixtureCommand#onExecute()} that creates a new room.
   */
  @Test
  public void testOnExecuteCreateNewRoom() throws Exception {
    final Building building = new Building();
    final PositionHint positionHint = new PositionHint();
    populateCommand(command);

    context.checking(new Expectations() {{
      oneOf(repository).findRoom("buildingId", "roomNumber");
      will(returnValue(null));
      oneOf(repository).findBuildingById("buildingId");
      will(returnValue(building));
      oneOf(repository).addRoom(with(any(Room.class)));
      oneOf(repository).findHint("hint");
      will(returnValue(positionHint));
      oneOf(repository).addAsset(with(any(Asset.class)));
      oneOf(repository).addFixture(with(any(Fixture.class)));
    }});
    command.onExecute();
    context.assertIsSatisfied();
  }

  /**
   * Test method for {@link org.ualerts.fixed.service.commands.AddFixtureCommand#onExecute()} that creates a new position hint.
   */
  @Test
  public void testOnExecuteCreateNewPositionHint() throws Exception {
    final Room room = new Room();
    populateCommand(command);

    context.checking(new Expectations() {{
      oneOf(repository).findRoom("buildingId", "roomNumber");
      will(returnValue(room));
      oneOf(repository).findHint("hint");
      will(returnValue(null));
      oneOf(repository).addPositionHint(with(any(PositionHint.class)));
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
