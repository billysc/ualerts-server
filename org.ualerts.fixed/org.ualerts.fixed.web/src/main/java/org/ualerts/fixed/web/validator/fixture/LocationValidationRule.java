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

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.ualerts.fixed.Building;
import org.ualerts.fixed.PositionHint;
import org.ualerts.fixed.Room;
import org.ualerts.fixed.repository.BuildingRepository;
import org.ualerts.fixed.repository.FixtureRepository;
import org.ualerts.fixed.repository.PositionHintRepository;
import org.ualerts.fixed.repository.RoomRepository;
import org.ualerts.fixed.web.model.FixtureModel;

/**
 * A FixtureValidationRule that validates the IP Address for a Fixture.
 * 
 * @author Michael Irwin
 */
@Component
public class LocationValidationRule extends AbstractFixtureValidationRule {
  
  private BuildingRepository buildingRepository;
  private FixtureRepository fixtureRepository;
  private PositionHintRepository positionHintRepository;
  private RoomRepository roomRepository;
  
  /**
   * {@inheritDoc}
   */
  @Override
  public void doValidate(FixtureModel fixture) {
    String buildingId = fixture.getBuildingId();
    String roomText = fixture.getRoom();
    String hintText = fixture.getPositionHint();

    validateBuilding(buildingId);
    roomIsValid(roomText);
    positionHintIsValid(hintText);

    if (!hasAddedErrors()) {
      Building building = buildingRepository.findBuildingById(buildingId);
      Room room = roomRepository.findRoom(building.getId(), roomText);
      PositionHint hint = positionHintRepository.findHint(hintText);
      if ((room != null) && (hint != null)) {
        if (fixtureRepository.findFixtureByLocation(room.getId(),
            hint.getId()) != null) {
          reject("location.conflict");
        }
      }
    }
  }
  
  /**
   * Validate that the building isn't empty and exists
   * @param buildingId The id of the building
   */
  protected void validateBuilding(String buildingId) {
    if (StringUtils.isBlank(buildingId)) {
      rejectValue("building", "building.empty");
    }
    else if (buildingRepository.findBuildingById(buildingId) == null) {
      rejectValue("building", "building.unknown");
    }
  }
  
  /**
   * Validate that the position hint isn't empty
   * @param positionHint The position hint to validate
   */
  protected void positionHintIsValid(String positionHint) {
    if (StringUtils.isBlank(positionHint)) {
      rejectValue("positionHint", "positionHint.empty");
    }
  }

  /**
   * Validate that the room isn't empty
   * @param room The room name to validate
   */
  protected void roomIsValid(String room) {
    if (StringUtils.isBlank(room)) {
      rejectValue("room", "room.empty");
    }
  }
  
  /**
   * Sets the {@code buildingRepository} property.
   * @param buildingRepository the value to set
   */
  @Resource
  public void setBuildingRepository(BuildingRepository buildingRepository) {
    this.buildingRepository = buildingRepository;
  }
  
  /**
   * Sets the {@code fixtureRepository} property.
   * @param fixtureRepository the value to set
   */
  @Resource
  public void setFixtureRepository(FixtureRepository fixtureRepository) {
    this.fixtureRepository = fixtureRepository;
  }
  
  /**
   * Sets the {@code positionHintRepository} property.
   * @param positionHintRepository the value to set
   */
  @Resource
  public void setPositionHintRepository(
      PositionHintRepository positionHintRepository) {
    this.positionHintRepository = positionHintRepository;
  }
  
  /**
   * Sets the {@code roomRepository} property.
   * @param roomRepository the value to set
   */
  @Resource
  public void setRoomRepository(RoomRepository roomRepository) {
    this.roomRepository = roomRepository;
  }
}
