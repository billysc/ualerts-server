/*
 * File created on May 9, 2013 
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
package org.ualerts.fixed.service.commands;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.util.Assert;
import org.ualerts.fixed.Room;
import org.ualerts.fixed.repository.RoomRepository;
import org.ualerts.fixed.service.CommandComponent;
import org.ualerts.fixed.service.errors.UnspecifiedConstraintException;

/**
 * Command to find all rooms for a particular building in the UAlerts system.
 *
 * @author Brian Early
 */
@CommandComponent
public class FindRoomsForBuildingCommand
extends AbstractCommand<List<Room>> {
  private String buildingId;
  private RoomRepository roomRepository;

  /**
   * {@inheritDoc}
   */
  @Override
  protected void onValidate() throws Exception {
    super.onValidate();
    Assert.notNull(buildingId, "buildingId is required");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected List<Room> onExecute() throws UnspecifiedConstraintException {
    return roomRepository.findRoomsForBuilding(buildingId);
  }

  /**
   * Gets the {@code buildingId} property.
   * @return property value
   */
  public String getBuildingId() {
    return buildingId;
  }

  /**
   * Sets the {@code buildingId} property.
   * @param buildingId the value to set
   */
  public void setBuildingId(String buildingId) {
    this.buildingId = buildingId;
  }

  /**
   * Sets the {@code roomRepository} property.
   * @param roomRepository the room repository
   */
  @Resource
  public void setRoomRepository(RoomRepository roomRepository) {
    this.roomRepository = roomRepository;
  }

}
