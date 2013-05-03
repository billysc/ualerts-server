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
import org.ualerts.fixed.Asset;
import org.ualerts.fixed.Building;
import org.ualerts.fixed.Fixture;
import org.ualerts.fixed.InetAddress;
import org.ualerts.fixed.MacAddress;
import org.ualerts.fixed.PositionHint;
import org.ualerts.fixed.Room;
import org.ualerts.fixed.repository.FixedRepository;
import org.ualerts.fixed.service.errors.InventoryNumberConflictException;
import org.ualerts.fixed.service.errors.LocationConflictException;
import org.ualerts.fixed.service.errors.MacAddressConflictException;
import org.ualerts.fixed.service.errors.MissingFieldException;
import org.ualerts.fixed.service.errors.SerialNumberConflictException;
import org.ualerts.fixed.service.errors.UnknownBuildingException;
import org.ualerts.fixed.service.errors.ValidationErrors;

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
   * Test method for
   * {@link org.ualerts.fixed.service.commands.AddFixtureCommand#onValidate()}.
   */
  @Test
  public void testOnValidate() throws Exception {
    final Building building = new Building();
    building.setId("buildingId");
    final Room room = new Room();
    room.setId(1L);
    final PositionHint hint = new PositionHint();
    hint.setId(2L);
    populateCommand(command);
    context.checking(new Expectations() { {
      oneOf(repository).findBuildingByName("buildingName");
      will(returnValue(building));
      oneOf(repository).findAssetBySerialNumber("serialNumber");
      will(returnValue(null));
      oneOf(repository).findAssetByInventoryNumber("inventoryNumber");
      will(returnValue(null));
      oneOf(repository).findAssetByMacAddress("0A-1B-2C-3D-4E-5F");
      will(returnValue(null));
      oneOf(repository).findRoom("buildingId", "roomNumber");
      will(returnValue(room));
      oneOf(repository).findHint("hint");
      will(returnValue(hint));
      oneOf(repository).findFixtureByLocation(1L, 2L);
      will(returnValue(null));
    } });
    command.onValidate();
    context.assertIsSatisfied();
  }

  /**
   * Test method with missing building for
   * {@link org.ualerts.fixed.service.commands.AddFixtureCommand#onValidate()}.
   */
  @Test
  public void testOnValidateBlankBuilding() throws Exception {
    try {
      populateCommand(command);
      command.setBuildingName(null);
      context.checking(new Expectations() { {
        oneOf(repository).findAssetBySerialNumber("serialNumber");
        will(returnValue(null));
        oneOf(repository).findAssetByInventoryNumber("inventoryNumber");
        will(returnValue(null));
        oneOf(repository).findAssetByMacAddress("0A-1B-2C-3D-4E-5F");
        will(returnValue(null));
      } });
      command.onValidate();
      assertTrue(false);
    }
    catch (ValidationErrors ex) {
      context.assertIsSatisfied();
      assertTrue(ex.getErrors().size() == 1);
      assertTrue(((MissingFieldException) ex.getErrors().get(0)).
          getFieldName().equals("buildingName"));
    }
  }

  /**
   * Test method with missing inventory number for
   * {@link org.ualerts.fixed.service.commands.AddFixtureCommand#onValidate()}.
   */
  @Test
  public void testOnValidateBlankInventory() throws Exception {
    final Building building = new Building();
    building.setId("buildingId");
    final Room room = new Room();
    room.setId(1L);
    final PositionHint hint = new PositionHint();
    hint.setId(2L);
    try {
      populateCommand(command);
      command.setInventoryNumber(null);
      context.checking(new Expectations() { {
        oneOf(repository).findBuildingByName("buildingName");
        will(returnValue(building));
        oneOf(repository).findAssetBySerialNumber("serialNumber");
        will(returnValue(null));
        oneOf(repository).findAssetByMacAddress("0A-1B-2C-3D-4E-5F");
        will(returnValue(null));
        oneOf(repository).findRoom("buildingId", "roomNumber");
        will(returnValue(room));
        oneOf(repository).findHint("hint");
        will(returnValue(hint));
        oneOf(repository).findFixtureByLocation(1L, 2L);
        will(returnValue(null));
      } });
      command.onValidate();
      assertTrue(false);
    }
    catch (ValidationErrors ex) {
      context.assertIsSatisfied();
      assertTrue(ex.getErrors().size() == 1);
      assertTrue(((MissingFieldException) ex.getErrors().get(0)).
          getFieldName().equals("inventoryNumber"));
    }
  }

  /**
   * Test method with missing IP address for
   * {@link org.ualerts.fixed.service.commands.AddFixtureCommand#onValidate()}.
   */
  @Test
  public void testOnValidateBlankIpAddress() throws Exception {
    final Building building = new Building();
    building.setId("buildingId");
    final Room room = new Room();
    room.setId(1L);
    final PositionHint hint = new PositionHint();
    hint.setId(2L);
    try {
      populateCommand(command);
      command.setInetAddress(null);
      context.checking(new Expectations() { {
        oneOf(repository).findBuildingByName("buildingName");
        will(returnValue(building));
        oneOf(repository).findAssetBySerialNumber("serialNumber");
        will(returnValue(null));
        oneOf(repository).findAssetByInventoryNumber("inventoryNumber");
        will(returnValue(null));
        oneOf(repository).findAssetByMacAddress("0A-1B-2C-3D-4E-5F");
        will(returnValue(null));
        oneOf(repository).findRoom("buildingId", "roomNumber");
        will(returnValue(room));
        oneOf(repository).findHint("hint");
        will(returnValue(hint));
        oneOf(repository).findFixtureByLocation(1L, 2L);
        will(returnValue(null));
      } });
      command.onValidate();
      assertTrue(false);
    }
    catch (ValidationErrors ex) {
      context.assertIsSatisfied();
      assertTrue(ex.getErrors().size() == 1);
      assertTrue(((MissingFieldException) ex.getErrors().get(0)).
          getFieldName().equals("inetAddress"));
    }
  }

  /**
   * Test method with missing MAC address for
   * {@link org.ualerts.fixed.service.commands.AddFixtureCommand#onValidate()}.
   */
  @Test
  public void testOnValidateBlankMacAddress() throws Exception {
    final Building building = new Building();
    building.setId("buildingId");
    final Room room = new Room();
    room.setId(1L);
    final PositionHint hint = new PositionHint();
    hint.setId(2L);
    try {
      populateCommand(command);
      command.setMacAddress(null);
      context.checking(new Expectations() { {
        oneOf(repository).findBuildingByName("buildingName");
        will(returnValue(building));
        oneOf(repository).findAssetBySerialNumber("serialNumber");
        will(returnValue(null));
        oneOf(repository).findAssetByInventoryNumber("inventoryNumber");
        will(returnValue(null));
        oneOf(repository).findRoom("buildingId", "roomNumber");
        will(returnValue(room));
        oneOf(repository).findHint("hint");
        will(returnValue(hint));
        oneOf(repository).findFixtureByLocation(1L, 2L);
        will(returnValue(null));
      } });
      command.onValidate();
      assertTrue(false);
    }
    catch (ValidationErrors ex) {
      context.assertIsSatisfied();
      assertTrue(ex.getErrors().size() == 1);
      assertTrue(((MissingFieldException) ex.getErrors().get(0)).
          getFieldName().equals("macAddress"));
    }
  }

  /**
   * Test method with missing position hint for
   * {@link org.ualerts.fixed.service.commands.AddFixtureCommand#onValidate()}.
   */
  @Test
  public void testOnValidateBlankPositionHint() throws Exception {
    final Building building = new Building();
    building.setId("buildingId");
    final Room room = new Room();
    room.setId(1L);
    final PositionHint hint = new PositionHint();
    hint.setId(2L);
    try {
      populateCommand(command);
      command.setPositionHint(null);
      context.checking(new Expectations() { {
        oneOf(repository).findBuildingByName("buildingName");
        will(returnValue(building));
        oneOf(repository).findAssetBySerialNumber("serialNumber");
        will(returnValue(null));
        oneOf(repository).findAssetByInventoryNumber("inventoryNumber");
        will(returnValue(null));
        oneOf(repository).findAssetByMacAddress("0A-1B-2C-3D-4E-5F");
        will(returnValue(null));
      } });
      command.onValidate();
      assertTrue(false);
    }
    catch (ValidationErrors ex) {
      context.assertIsSatisfied();
      assertTrue(ex.getErrors().size() == 1);
      assertTrue(((MissingFieldException) ex.getErrors().get(0)).
          getFieldName().equals("positionHint"));
    }
  }

  /**
   * Test method with missing room for
   * {@link org.ualerts.fixed.service.commands.AddFixtureCommand#onValidate()}.
   */
  @Test
  public void testOnValidateBlankRoom() throws Exception {
    final Building building = new Building();
    building.setId("buildingId");
    final Room room = new Room();
    room.setId(1L);
    final PositionHint hint = new PositionHint();
    hint.setId(2L);
    try {
      populateCommand(command);
      command.setRoomNumber(null);
      context.checking(new Expectations() { {
        oneOf(repository).findBuildingByName("buildingName");
        will(returnValue(building));
        oneOf(repository).findAssetBySerialNumber("serialNumber");
        will(returnValue(null));
        oneOf(repository).findAssetByInventoryNumber("inventoryNumber");
        will(returnValue(null));
        oneOf(repository).findAssetByMacAddress("0A-1B-2C-3D-4E-5F");
        will(returnValue(null));
      } });
      command.onValidate();
      assertTrue(false);
    }
    catch (ValidationErrors ex) {
      context.assertIsSatisfied();
      assertTrue(ex.getErrors().size() == 1);
      assertTrue(((MissingFieldException) ex.getErrors().get(0)).
          getFieldName().equals("roomNumber"));
    }
  }

  /**
   * Test method with missing serial number for
   * {@link org.ualerts.fixed.service.commands.AddFixtureCommand#onValidate()}.
   */
  @Test
  public void testOnValidateBlankSerialNumber() throws Exception {
    final Building building = new Building();
    building.setId("buildingId");
    final Room room = new Room();
    room.setId(1L);
    final PositionHint hint = new PositionHint();
    hint.setId(2L);
    try {
      populateCommand(command);
      command.setSerialNumber(null);
      context.checking(new Expectations() { {
        oneOf(repository).findBuildingByName("buildingName");
        will(returnValue(building));
        oneOf(repository).findAssetByInventoryNumber("inventoryNumber");
        will(returnValue(null));
        oneOf(repository).findAssetByMacAddress("0A-1B-2C-3D-4E-5F");
        will(returnValue(null));
        oneOf(repository).findRoom("buildingId", "roomNumber");
        will(returnValue(room));
        oneOf(repository).findHint("hint");
        will(returnValue(hint));
        oneOf(repository).findFixtureByLocation(1L, 2L);
        will(returnValue(null));
      } });
      command.onValidate();
      assertTrue(false);
    }
    catch (ValidationErrors ex) {
      context.assertIsSatisfied();
      assertTrue(ex.getErrors().size() == 1);
      assertTrue(((MissingFieldException) ex.getErrors().get(0)).
          getFieldName().equals("serialNumber"));
    }
  }

  /**
   * Test method with a bad building name for
   * {@link org.ualerts.fixed.service.commands.AddFixtureCommand#onValidate()}.
   */
  @Test
  public void testOnValidateBadBuildingName() throws Exception {
    final Room room = new Room();
    room.setId(1L);
    final PositionHint hint = new PositionHint();
    hint.setId(2L);
    try {
      populateCommand(command);
      context.checking(new Expectations() { {
        oneOf(repository).findBuildingByName("buildingName");
        will(returnValue(null));
        oneOf(repository).findAssetBySerialNumber("serialNumber");
        will(returnValue(null));
        oneOf(repository).findAssetByInventoryNumber("inventoryNumber");
        will(returnValue(null));
        oneOf(repository).findAssetByMacAddress("0A-1B-2C-3D-4E-5F");
        will(returnValue(null));
      } });
      command.onValidate();
      assertTrue(false);
    }
    catch (ValidationErrors ex) {
      context.assertIsSatisfied();
      assertTrue(ex.getErrors().size() == 1);
      assertTrue(ex.getErrors().get(0).getClass().
          equals(UnknownBuildingException.class));
    }
  }

  /**
   * Test method with an inventory number conflict for
   * {@link org.ualerts.fixed.service.commands.AddFixtureCommand#onValidate()}.
   */
  @Test
  public void testOnValidateInventoryNumberConflict() throws Exception {
    final Building building = new Building();
    building.setId("buildingId");
    final Asset asset = new Asset();
    final Room room = new Room();
    room.setId(1L);
    final PositionHint hint = new PositionHint();
    hint.setId(2L);
    try {
      populateCommand(command);
      context.checking(new Expectations() { {
        oneOf(repository).findBuildingByName("buildingName");
        will(returnValue(building));
        oneOf(repository).findAssetBySerialNumber("serialNumber");
        will(returnValue(null));
        oneOf(repository).findAssetByInventoryNumber("inventoryNumber");
        will(returnValue(asset));
        oneOf(repository).findAssetByMacAddress("0A-1B-2C-3D-4E-5F");
        will(returnValue(null));
        oneOf(repository).findRoom("buildingId", "roomNumber");
        will(returnValue(room));
        oneOf(repository).findHint("hint");
        will(returnValue(hint));
        oneOf(repository).findFixtureByLocation(1L, 2L);
        will(returnValue(null));
      } });
      command.onValidate();
      assertTrue(false);
    }
    catch (ValidationErrors ex) {
      context.assertIsSatisfied();
      assertTrue(ex.getErrors().size() == 1);
      assertTrue(ex.getErrors().get(0).getClass()
          .equals(InventoryNumberConflictException.class));
    }
  }

  /**
   * Test method with a serial number conflict for
   * {@link org.ualerts.fixed.service.commands.AddFixtureCommand#onValidate()}.
   */
  @Test
  public void testOnValidateSerialNumberConflict() throws Exception {
    final Building building = new Building();
    building.setId("buildingId");
    final Asset asset = new Asset();
    final Room room = new Room();
    room.setId(1L);
    final PositionHint hint = new PositionHint();
    hint.setId(2L);
    try {
      populateCommand(command);
      context.checking(new Expectations() { {
        oneOf(repository).findBuildingByName("buildingName");
        will(returnValue(building));
        oneOf(repository).findAssetBySerialNumber("serialNumber");
        will(returnValue(asset));
        oneOf(repository).findAssetByInventoryNumber("inventoryNumber");
        will(returnValue(null));
        oneOf(repository).findAssetByMacAddress("0A-1B-2C-3D-4E-5F");
        will(returnValue(null));
        oneOf(repository).findRoom("buildingId", "roomNumber");
        will(returnValue(room));
        oneOf(repository).findHint("hint");
        will(returnValue(hint));
        oneOf(repository).findFixtureByLocation(1L, 2L);
        will(returnValue(null));
      } });
      command.onValidate();
      assertTrue(false);
    }
    catch (ValidationErrors ex) {
      context.assertIsSatisfied();
      assertTrue(ex.getErrors().size() == 1);
      assertTrue(ex.getErrors().get(0).getClass()
          .equals(SerialNumberConflictException.class));
    }
  }

  /**
   * Test method with a MAC address conflict for
   * {@link org.ualerts.fixed.service.commands.AddFixtureCommand#onValidate()}.
   */
  @Test
  public void testOnValidateMacAddressConflict() throws Exception {
    final Building building = new Building();
    building.setId("buildingId");
    final Asset asset = new Asset();
    final Room room = new Room();
    room.setId(1L);
    final PositionHint hint = new PositionHint();
    hint.setId(2L);
    try {
      populateCommand(command);
      context.checking(new Expectations() { {
        oneOf(repository).findBuildingByName("buildingName");
        will(returnValue(building));
        oneOf(repository).findAssetBySerialNumber("serialNumber");
        will(returnValue(null));
        oneOf(repository).findAssetByInventoryNumber("inventoryNumber");
        will(returnValue(null));
        oneOf(repository).findAssetByMacAddress("0A-1B-2C-3D-4E-5F");
        will(returnValue(asset));
        oneOf(repository).findRoom("buildingId", "roomNumber");
        will(returnValue(room));
        oneOf(repository).findHint("hint");
        will(returnValue(hint));
        oneOf(repository).findFixtureByLocation(1L, 2L);
        will(returnValue(null));
      } });
      command.onValidate();
      assertTrue(false);
    }
    catch (ValidationErrors ex) {
      context.assertIsSatisfied();
      assertTrue(ex.getErrors().size() == 1);
      assertTrue(ex.getErrors().get(0).getClass()
          .equals(MacAddressConflictException.class));
    }
  }

  /**
   * Test method with a location conflict for
   * {@link org.ualerts.fixed.service.commands.AddFixtureCommand#onValidate()}.
   */
  @Test
  public void testOnValidateLocationConflict() throws Exception {
    final Building building = new Building();
    building.setId("buildingId");
    final Room room = new Room();
    room.setId(1L);
    final PositionHint hint = new PositionHint();
    hint.setId(2L);
    final Fixture fixture = new Fixture();
    try {
      populateCommand(command);
      context.checking(new Expectations() { {
        oneOf(repository).findBuildingByName("buildingName");
        will(returnValue(building));
        oneOf(repository).findAssetBySerialNumber("serialNumber");
        will(returnValue(null));
        oneOf(repository).findAssetByInventoryNumber("inventoryNumber");
        will(returnValue(null));
        oneOf(repository).findAssetByMacAddress("0A-1B-2C-3D-4E-5F");
        will(returnValue(null));
        oneOf(repository).findRoom("buildingId", "roomNumber");
        will(returnValue(room));
        oneOf(repository).findHint("hint");
        will(returnValue(hint));
        oneOf(repository).findFixtureByLocation(1L, 2L);
        will(returnValue(fixture));
      } });
      command.onValidate();
      assertTrue(false);
    }
    catch (ValidationErrors ex) {
      context.assertIsSatisfied();
      assertTrue(ex.getErrors().size() == 1);
      assertTrue(ex.getErrors().get(0).getClass()
          .equals(LocationConflictException.class));
    }
  }

  /**
   * Test method for
   * {@link org.ualerts.fixed.service.commands.AddFixtureCommand#onValidate()}.
   */
  @Test
  public void testOnValidateNoInstalledBy() throws Exception {
    // At lest currently, this field is not required, so having it null
    // should not cause an exception.
    final Building building = new Building();
    building.setId("buildingId");
    final Room room = new Room();
    room.setId(1L);
    final PositionHint hint = new PositionHint();
    hint.setId(2L);
    populateCommand(command);
    command.setInstalledBy(null);
    context.checking(new Expectations() { {
      oneOf(repository).findBuildingByName("buildingName");
      will(returnValue(building));
      oneOf(repository).findAssetBySerialNumber("serialNumber");
      will(returnValue(null));
      oneOf(repository).findAssetByInventoryNumber("inventoryNumber");
      will(returnValue(null));
      oneOf(repository).findAssetByMacAddress("0A-1B-2C-3D-4E-5F");
      will(returnValue(null));
      oneOf(repository).findRoom("buildingId", "roomNumber");
      will(returnValue(room));
      oneOf(repository).findHint("hint");
      will(returnValue(hint));
      oneOf(repository).findFixtureByLocation(1L, 2L);
      will(returnValue(null));
    } });
    command.onValidate();
    context.assertIsSatisfied();
  }

  /**
   * Test method for
   * {@link org.ualerts.fixed.service.commands.AddFixtureCommand#onExecute()}.
   */
  @Test
  public void testOnExecute() throws Exception {
    final Building building = new Building();
    building.setId("buildingId");
    final Room room = new Room();
    final PositionHint positionHint = new PositionHint();
    populateCommand(command);

    context.checking(new Expectations() { {
      oneOf(repository).findBuildingByName("buildingName");
      will(returnValue(building));
      oneOf(repository).findRoom("buildingId", "roomNumber");
      will(returnValue(room));
      oneOf(repository).findHint("hint");
      will(returnValue(positionHint));
      oneOf(repository).addAsset(with(any(Asset.class)));
      oneOf(repository).addFixture(with(any(Fixture.class)));
    } });
    Fixture result = command.onExecute();
    context.assertIsSatisfied();
    assertTrue(result.getIpAddress().equals("127.0.0.1"));
  }

  /**
   * Test method creating a new room for
   * {@link org.ualerts.fixed.service.commands.AddFixtureCommand#onExecute()}.
   */
  @Test
  public void testOnExecuteCreateNewRoom() throws Exception {
    final Building building = new Building();
    building.setId("buildingId");
    final PositionHint positionHint = new PositionHint();
    populateCommand(command);

    context.checking(new Expectations() { {
      oneOf(repository).findBuildingByName("buildingName");
      will(returnValue(building));
      oneOf(repository).findRoom("buildingId", "roomNumber");
      will(returnValue(null));
      oneOf(repository).addRoom(with(any(Room.class)));
      oneOf(repository).findHint("hint");
      will(returnValue(positionHint));
      oneOf(repository).addAsset(with(any(Asset.class)));
      oneOf(repository).addFixture(with(any(Fixture.class)));
    } });
    command.onExecute();
    context.assertIsSatisfied();
  }

  /**
   * Test method creating a new position hint for
   * {@link org.ualerts.fixed.service.commands.AddFixtureCommand#onExecute()}.
   */
  @Test
  public void testOnExecuteCreateNewPositionHint() throws Exception {
    final Building building = new Building();
    building.setId("buildingId");
    final Room room = new Room();
    populateCommand(command);

    context.checking(new Expectations() { {
      oneOf(repository).findBuildingByName("buildingName");
      will(returnValue(building));
      oneOf(repository).findRoom("buildingId", "roomNumber");
      will(returnValue(room));
      oneOf(repository).findHint("hint");
      will(returnValue(null));
      oneOf(repository).addPositionHint(with(any(PositionHint.class)));
      oneOf(repository).addAsset(with(any(Asset.class)));
      oneOf(repository).addFixture(with(any(Fixture.class)));
    } });
    command.onExecute();
    context.assertIsSatisfied();
  }

  private void populateCommand(AddFixtureCommand command) {
    command.setRoomNumber("roomNumber");
    command.setBuildingName("buildingName");
    command.setPositionHint("hint");
    command.setInetAddress(InetAddress.getByAddress("127.0.0.1"));
    command.setMacAddress(new MacAddress("0A-1B-2C-3D-4E-5F"));
    command.setSerialNumber("serialNumber");
    command.setInventoryNumber("inventoryNumber");
    command.setInstalledBy("installedBy");
  }

}
