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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Date;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.ualerts.fixed.Building;
import org.ualerts.fixed.Fixture;
import org.ualerts.fixed.InetAddress;
import org.ualerts.fixed.PositionHint;
import org.ualerts.fixed.Room;
import org.ualerts.fixed.repository.BuildingRepository;
import org.ualerts.fixed.repository.FixtureRepository;
import org.ualerts.fixed.repository.PositionHintRepository;
import org.ualerts.fixed.repository.RoomRepository;
import org.ualerts.fixed.service.DateTimeService;

/**
 * Unit tests for {@link UpdateFixtureCommand}.
 *
 * @author Brian Early
 */
public class UpdateFixtureCommandTest {
  private static final String BUILDING_NAME = "buildingName";
  private static final String IP_ADDRESS = "127.0.0.1";
  private static final String BUILDING_ID = "buildingId";
  private static final String ROOM_NUMBER = "roomNumber";
  private static final String POSITION_HINT = "hint";

  private Mockery context;
  private BuildingRepository buildingRepository;
  private RoomRepository roomRepository;
  private PositionHintRepository positionHintRepository;
  private FixtureRepository fixtureRepository;
  private DateTimeService dateService;
  private UpdateFixtureCommand command;

  /**
   * @throws java.lang.Exception
   */
  @Before
  public void setUp() throws Exception {
    context = new Mockery();
    buildingRepository = context.mock(BuildingRepository.class);
    roomRepository = context.mock(RoomRepository.class);
    positionHintRepository = context.mock(PositionHintRepository.class);
    fixtureRepository = context.mock(FixtureRepository.class);
    dateService = context.mock(DateTimeService.class);
    command = new UpdateFixtureCommand();
    command.setFixtureRepository(fixtureRepository);
    command.setBuildingRepository(buildingRepository);
    command.setRoomRepository(roomRepository);
    command.setPositionHintRepository(positionHintRepository);
    command.setDateService(dateService);
  }

  /**
   * @throws java.lang.Exception
   */
  @After
  public void tearDown() throws Exception {
    command = null;
    fixtureRepository = null;
    buildingRepository = null;
    roomRepository = null;
    positionHintRepository = null;
  }

  /**
   * Test method for
   * {@link UpdateFixtureCommand#onValidate()}.
   */
  @Test
  public void testOnValidate() throws Exception {
    populateCommand(command);
    command.onValidate();
  }

  /**
   * Test method with missing building for
   * {@link UpdateFixtureCommand#onValidate()}.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testOnValidateBlankBuilding() throws Exception {
    populateCommand(command);
    command.setBuildingName(null);
    command.onValidate();
    fail("Expected exception did not occur.");
  }

  /**
   * Test method with missing INET address for
   * {@link UpdateFixtureCommand#onValidate()}.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testOnValidateBlankInetAddress() throws Exception {
    populateCommand(command);
    command.setInetAddress(null);
    command.onValidate();
    fail("Did not receive expected exception.");
  }

  /**
   * Test method with missing position hint for
   * {@link UpdateFixtureCommand#onValidate()}.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testOnValidateBlankPositionHint() throws Exception {
    populateCommand(command);
    command.setPositionHint(null);
    command.onValidate();
    fail("Expected exception did not occur.");
  }

  /**
   * Test method with missing room for
   * {@link UpdateFixtureCommand#onValidate()}.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testOnValidateBlankRoom() throws Exception {
    populateCommand(command);
    command.setRoomNumber(null);
    command.onValidate();
    fail("Expected exception did not occur.");
  }

  /**
   * Test method for
   * {@link UpdateFixtureCommand#onExecute()}.
   */
  @Test
  public void testOnExecute() throws Exception {
    final Building building = new Building();
    building.setId(BUILDING_ID);
    final Room room = new Room();
    final PositionHint positionHint = new PositionHint();
    final Date date = new Date();
    final Fixture fixture = new Fixture();
    populateCommand(command);

    context.checking(new Expectations() { {
      oneOf(buildingRepository).findBuildingByName(BUILDING_NAME);
      will(returnValue(building));
      oneOf(roomRepository).findRoom(BUILDING_ID, ROOM_NUMBER);
      will(returnValue(room));
      oneOf(positionHintRepository).findHint(POSITION_HINT);
      will(returnValue(positionHint));
      oneOf(dateService).getCurrentDate();
      will(returnValue(date));
      oneOf(fixtureRepository).findFixtureById(1L);
      will(returnValue(fixture));
    } });
    Fixture result = command.onExecute();
    context.assertIsSatisfied();
    assertEquals(IP_ADDRESS, result.getIpAddress());
  }

  /**
   * Test method creating a new room for
   * {@link UpdateFixtureCommand#onExecute()}.
   */
  @Test
  public void testOnExecuteCreateNewRoom() throws Exception {
    final Building building = new Building();
    final Date date = new Date();
    building.setId(BUILDING_ID);
    final PositionHint positionHint = new PositionHint();
    final Fixture fixture = new Fixture();
    populateCommand(command);

    context.checking(new Expectations() { {
      oneOf(buildingRepository).findBuildingByName(BUILDING_NAME);
      will(returnValue(building));
      oneOf(roomRepository).findRoom(BUILDING_ID, ROOM_NUMBER);
      will(returnValue(null));
      oneOf(roomRepository).addRoom(with(any(Room.class)));
      oneOf(positionHintRepository).findHint(POSITION_HINT);
      will(returnValue(positionHint));
      oneOf(dateService).getCurrentDate();
      will(returnValue(date));
      oneOf(fixtureRepository).findFixtureById(1L);
      will(returnValue(fixture));
    } });
    command.onExecute();
    context.assertIsSatisfied();
  }

  /**
   * Test method creating a new position hint for
   * {@link UpdateFixtureCommand#onExecute()}.
   */
  @Test
  public void testOnExecuteCreateNewPositionHint() throws Exception {
    final Building building = new Building();
    building.setId(BUILDING_ID);
    final Room room = new Room();
    final Date date = new Date();
    final Fixture fixture = new Fixture();
    populateCommand(command);

    context.checking(new Expectations() { {
      oneOf(buildingRepository).findBuildingByName(BUILDING_NAME);
      will(returnValue(building));
      oneOf(roomRepository).findRoom(BUILDING_ID, ROOM_NUMBER);
      will(returnValue(room));
      oneOf(positionHintRepository).findHint(POSITION_HINT);
      will(returnValue(null));
      oneOf(positionHintRepository)
      .addPositionHint(with(any(PositionHint.class)));
      oneOf(dateService).getCurrentDate();
      will(returnValue(date));
      oneOf(fixtureRepository).findFixtureById(1L);
      will(returnValue(fixture));
    } });
    command.onExecute();
    context.assertIsSatisfied();
  }

  private void populateCommand(UpdateFixtureCommand command) {
    command.setId(1L);
    command.setRoomNumber(ROOM_NUMBER);
    command.setBuildingName(BUILDING_NAME);
    command.setPositionHint(POSITION_HINT);
    command.setInetAddress(InetAddress.getByAddress(IP_ADDRESS));
  }

}
