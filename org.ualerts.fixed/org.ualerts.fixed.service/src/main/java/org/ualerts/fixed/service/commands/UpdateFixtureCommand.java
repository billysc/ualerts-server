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

import javax.annotation.Resource;
import javax.persistence.PersistenceException;
import org.springframework.util.Assert;
import org.ualerts.fixed.Building;
import org.ualerts.fixed.Fixture;
import org.ualerts.fixed.InetAddress;
import org.ualerts.fixed.PositionHint;
import org.ualerts.fixed.Room;
import org.ualerts.fixed.repository.BuildingRepository;
import org.ualerts.fixed.repository.EntityNotFoundException;
import org.ualerts.fixed.repository.FixtureRepository;
import org.ualerts.fixed.repository.PositionHintRepository;
import org.ualerts.fixed.repository.RoomRepository;
import org.ualerts.fixed.service.CommandComponent;
import org.ualerts.fixed.service.DateTimeService;
import org.ualerts.fixed.service.errors.UnspecifiedConstraintException;

/**
 * Command to update a fixture in the UAlerts system.
 *
 * @author Brian Early
 */
@CommandComponent
public class UpdateFixtureCommand extends AbstractCommand<Fixture> {
  private Long id;
  private String roomNumber;
  // TODO - This will change to buildingId in a future task
  private String buildingName;
  private String positionHint;
  private InetAddress inetAddress;
  
  private BuildingRepository buildingRepository;
  private RoomRepository roomRepository;
  private PositionHintRepository positionHintRepository;
  private FixtureRepository fixtureRepository;
  private DateTimeService dateService;

  /**
   * {@inheritDoc}
   */
  @Override
  protected void onValidate() throws Exception {
    super.onValidate();
    Assert.notNull(id, "id is required");
    Assert.hasLength(roomNumber, "roomNumber is required");
    Assert.hasLength(buildingName, "buildingName is required");
    Assert.hasLength(positionHint, "positionHint is required");
    Assert.notNull(inetAddress);
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  protected Fixture onExecute() throws UnspecifiedConstraintException,
      EntityNotFoundException {
    try {
      Building building =
          buildingRepository.findBuildingByName(getBuildingName());
      Room room = findOrCreateRoom(building);
      PositionHint hint = findOrCreatePositionHint();
      return updateFixture(hint, room);
    }
    catch (PersistenceException ex) {
      throw new UnspecifiedConstraintException(ex);
    }
  }
  
  private Room findOrCreateRoom(Building building) {
    Room room = roomRepository.findRoom(building.getId(), getRoomNumber());
    if (room == null) {
      room = new Room();
      room.setBuilding(building);
      room.setRoomNumber(getRoomNumber());
      roomRepository.addRoom(room);
    }
    return room;
  }
  
  private PositionHint findOrCreatePositionHint() {
    PositionHint hint = positionHintRepository.findHint(getPositionHint());
    if (hint == null) {
      hint = new PositionHint();
      hint.setHintText(getPositionHint());
      positionHintRepository.addPositionHint(hint);
    }
    return hint;
  }

  private Fixture updateFixture(PositionHint hint, Room room)
      throws EntityNotFoundException {
    Fixture fixture = fixtureRepository.findFixtureById(getId());
    fixture.setDateModified(dateService.getCurrentDate());
    fixture.setIpAddress(getInetAddress().toString());
    fixture.setPositionHint(hint);
    fixture.setRoom(room);
    return fixture;
  }
  
  /**
   * Gets the {@code id} property.
   * @return property value
   */
  public Long getId() {
    return id;
  }

  /**
   * Sets the {@code id} property.
   * @param id the value to set
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Gets the {@code roomNumber} property.
   * @return property value
   */
  public String getRoomNumber() {
    return roomNumber;
  }

  /**
   * Sets the {@code roomNumber} property.
   * @param roomNumber the value to set
   */
  public void setRoomNumber(String roomNumber) {
    this.roomNumber = roomNumber;
  }

  /**
   * Gets the {@code buildingName} property.
   * @return property value
   */
  public String getBuildingName() {
    return buildingName;
  }

  /**
   * Sets the {@code buildingName} property.
   * @param buildingName the value to set
   */
  public void setBuildingName(String buildingName) {
    this.buildingName = buildingName;
  }

  /**
   * Gets the {@code positionHint} property.
   * @return property value
   */
  public String getPositionHint() {
    return positionHint;
  }

  /**
   * Sets the {@code positionHint} property.
   * @param positionHint the value to set
   */
  public void setPositionHint(String positionHint) {
    this.positionHint = positionHint;
  }

  /**
   * Gets the {@code inetAddress} property.
   * @return property value
   */
  public InetAddress getInetAddress() {
    return inetAddress;
  }

  /**
   * Sets the {@code inetAddress} property.
   * @param inetAddress the value to set
   */
  public void setInetAddress(InetAddress inetAddress) {
    this.inetAddress = inetAddress;
  }

  /**
   * Gets the {@code dateService} property.
   * @return property value
   */
  public DateTimeService getDateService() {
    return dateService;
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
   * Sets the {@code roomRepository} property.
   * @param roomRepository the value to set
   */
  @Resource
  public void setRoomRepository(RoomRepository roomRepository) {
    this.roomRepository = roomRepository;
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
   * Sets the {@code repository} property.
   * @param repository the fixed repository
   */
  @Resource
  public void setFixtureRepository(FixtureRepository repository) {
    this.fixtureRepository = repository;
  }

  /**
   * Sets the {@code dateService} property.
   * @param dateService the value to set
   */
  @Resource
  public void setDateService(DateTimeService dateService) {
    this.dateService = dateService;
  }

}
