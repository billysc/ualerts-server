/*
 * File created on May 14, 2013
 *
 * Copyright 2008-2013 Virginia Polytechnic Institute and State University
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

package org.ualerts.fixed.web.validator.fixture;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.Errors;
import org.ualerts.fixed.Building;
import org.ualerts.fixed.Fixture;
import org.ualerts.fixed.PositionHint;
import org.ualerts.fixed.Room;
import org.ualerts.fixed.repository.BuildingRepository;
import org.ualerts.fixed.repository.FixtureRepository;
import org.ualerts.fixed.repository.PositionHintRepository;
import org.ualerts.fixed.repository.RoomRepository;
import org.ualerts.fixed.web.model.FixtureModel;
import org.ualerts.fixed.web.validator.FixtureValidator;

/**
 * Test case for the LocationValidationRule class
 *
 * @author Michael Irwin
 */
public class LocationValidationRuleTest {

  private static final String MSG_PREFIX = FixtureValidator.MSG_PREFIX;
  
  private Mockery context;
  private BuildingRepository buildingRepository;
  private FixtureRepository fixtureRepository;
  private RoomRepository roomRepository;
  private PositionHintRepository positionHintRepository;

  private Errors errors;
  private FixtureModel fixture;
  private LocationValidationRule rule;
  
  /**
   * Setup tasks
   */
  @Before
  public void setUp() {
    context = new Mockery();
    buildingRepository = context.mock(BuildingRepository.class);
    fixtureRepository = context.mock(FixtureRepository.class);
    roomRepository = context.mock(RoomRepository.class);
    positionHintRepository = context.mock(PositionHintRepository.class);
    
    errors = context.mock(Errors.class);
    fixture = new FixtureModel();
    
    rule = new LocationValidationRule();
    rule.setBuildingRepository(buildingRepository);
    rule.setFixtureRepository(fixtureRepository);
    rule.setPositionHintRepository(positionHintRepository);
    rule.setRoomRepository(roomRepository);
  }
  
  /**
   * Test that a missing building name causes a rejection
   */
  @Test
  public void testValidateMissingBuilding() {
    context.checking(new Expectations() { { 
      exactly(2).of(errors).rejectValue("building", 
          MSG_PREFIX + "building.empty");
    } });
    rule.setErrors(errors);
    rule.validateBuilding(null);
    rule.validateBuilding("");
    context.assertIsSatisfied();
  }
  
  /**
   * Test that submitting an invalid building name causes a rejection
   */
  @Test
  public void testInvalidBuilding() {
    final String buildingId = "BUILDING1";
    context.checking(new Expectations() { { 
      oneOf(buildingRepository).findBuildingById(buildingId);
      will(returnValue(null));
      oneOf(errors).rejectValue("building", MSG_PREFIX + "building.unknown");
    } });
    rule.setErrors(errors);
    rule.validateBuilding(buildingId);
    context.assertIsSatisfied();
  }
  
  /**
   * Validate that a missing position hint causes a rejection
   */
  @Test
  public void testMissingPositionHint() {
    context.checking(new Expectations() { { 
      exactly(2).of(errors).rejectValue("positionHint", 
          MSG_PREFIX + "positionHint.empty");
    } });
    rule.setErrors(errors);
    rule.positionHintIsValid("");
    rule.positionHintIsValid(null);
    context.assertIsSatisfied();
  }
  
  /**
   * Validate that a missing room causes a rejection
   */
  @Test
  public void testMissingRoom() {
    context.checking(new Expectations() { { 
      exactly(2).of(errors).rejectValue("room", MSG_PREFIX + "room.empty");
    } });
    rule.setErrors(errors);
    rule.roomIsValid("");
    rule.roomIsValid(null);
    context.assertIsSatisfied();
  }
  
  /**
   * Validate that if everything is on order, no rejections are made
   */
  @Test
  public void testEverythingClean() {
    testFullSubmission(null);
  }
  
  /**
   * Validate that if a location conflict occurs, a rejection is made
   */
  @Test
  public void testLocationConflict() {
    context.checking(new Expectations() { { 
      oneOf(errors).reject(MSG_PREFIX + "location.conflict");
    } });
    Fixture fixture = new Fixture();
    testFullSubmission(fixture);
  }
  
  /*
   * The next series of tests validates that location conflict checks are only
   * performed if the building, room, and position hints pass validation. To
   * test this, one could use a truth table.
   */
  
  /**
   * Validate that location conflict checks aren't done when only the building
   * passes validation
   */
  @Test
  public void testValidCheckBuildingGood() {
    Building building = new Building();
    building.setId("BUILDING1");
    testValidCheckRoute(building, null, null);
  }
  
  /**
   * Validate that location conflict checks aren't done when only the room is
   * provided
   */
  @Test
  public void testValidCheckRoomGood() {
    testValidCheckRoute(null, "ROOM", null);
  }
  
  /**
   * Validate that location conflict checks aren't done when only the position
   * hint is provided.
   */
  @Test
  public void testValidCheckPositionHint() {
    testValidCheckRoute(null, null, "TOP-RIGHT");
  }
  
  /**
   * Validate that location conflict checks aren't done when the position hint
   * isn't provided
   */
  @Test
  public void testValidCheckBuildingAndRoomGood() {
    Building building = new Building();
    building.setId("BUILDING1");
    testValidCheckRoute(building, "ROOM", null);
  }
  
  /**
   * Validate that location conflict checks aren't done when the room isn't
   * provided
   */
  @Test
  public void testValidCheckBuildingAndPositionHintGood() {
    Building building = new Building();
    building.setId("BUILDING1");
    testValidCheckRoute(building, null, "TOP-RIGHT");
  }
  
  /**
   * Validate that location conflict checks aren't done when the building is
   * not provided
   */
  @Test
  public void testValidCheckRoomAndPositionHintGood() {
    testValidCheckRoute(null, "ROOM", "TOP-RIGHT");
  }
  
  private void testFullSubmission(final Fixture locationFixture) {
    final String building = "Building";
    final String buildingId = "buildingId";
    final Building buildingObj = new Building();
    buildingObj.setId(buildingId);
    buildingObj.setName(building);
    
    final String room = "110";
    final Long roomId = 3L;
    final Room roomObj = new Room();
    roomObj.setBuilding(buildingObj);
    roomObj.setRoomNumber(room);
    roomObj.setId(roomId);
    
    final String positionHint = "TOP-RIGHT";
    final Long positionHintId = 45L;
    final PositionHint positionHintObj = new PositionHint();
    positionHintObj.setHintText(positionHint);
    positionHintObj.setId(positionHintId);
    
    final String ipAddress = "192.168.1.1";
    final String macAddress = "01-12-23-34-45-56";
    
    fixture.setBuilding(building);
    fixture.setBuildingId(buildingId);
    fixture.setIpAddress(ipAddress);
    fixture.setMacAddress(macAddress);
    fixture.setPositionHint(positionHint);
    fixture.setRoom(room);
    
    context.checking(new Expectations() { { 
      exactly(2).of(buildingRepository).findBuildingById(buildingId);
      will(returnValue(buildingObj));
      
      oneOf(roomRepository).findRoom(buildingId, room);
      will(returnValue(roomObj));
      
      oneOf(positionHintRepository).findHint(positionHint);
      will(returnValue(positionHintObj));
      
      oneOf(fixtureRepository).findFixtureByLocation(roomId, positionHintId);
      will(returnValue(locationFixture));
    } });
    
    rule.validate(fixture, errors);
    context.assertIsSatisfied();
  }

  private void testValidCheckRoute(final Building building, String room, 
      String positionHint) {
    context.checking(new Expectations() { { 
      allowing(errors).rejectValue(with(any(String.class)), 
          with(any(String.class)));
    } });
    
    if (building != null) {
      fixture.setBuilding(building.getName());
      fixture.setBuildingId(building.getId());
      context.checking(new Expectations() { { 
        oneOf(buildingRepository).findBuildingById(building.getId());
        will(returnValue(building));
      } });
    }
    fixture.setRoom(room);
    fixture.setPositionHint(positionHint);
    rule.validate(fixture, errors);
    context.assertIsSatisfied();
  }
  
  
}
