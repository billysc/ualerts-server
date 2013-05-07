/*
 * File created on May 1, 2013 
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

import javax.persistence.PersistenceException;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.ualerts.fixed.Asset;
import org.ualerts.fixed.Building;
import org.ualerts.fixed.Fixture;
import org.ualerts.fixed.InetAddress;
import org.ualerts.fixed.MacAddress;
import org.ualerts.fixed.PositionHint;
import org.ualerts.fixed.Room;
import org.ualerts.fixed.repository.AssetRepository;
import org.ualerts.fixed.repository.BuildingRepository;
import org.ualerts.fixed.repository.FixtureRepository;
import org.ualerts.fixed.repository.PositionHintRepository;
import org.ualerts.fixed.repository.RoomRepository;
import org.ualerts.fixed.service.CommandComponent;
import org.ualerts.fixed.service.DateTimeService;
import org.ualerts.fixed.service.errors.UnspecifiedConstraintException;
import org.ualerts.fixed.service.errors.ValidationErrors;

/**
 * Command to add a fixture to the UAlerts system.
 *
 * @author Brian Early
 */
@CommandComponent
public class AddFixtureCommand extends AbstractCommand<Fixture> {
  private String roomNumber;
  // TODO - This will change to buildingId in a future task
  private String buildingName;
  private String positionHint;
  private InetAddress inetAddress;
  private String serialNumber;
  private String inventoryNumber;
  private MacAddress macAddress;
  private String installedBy;
  private AssetRepository assetRepository;
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
    ValidationErrors errors = new ValidationErrors();
    try {
      validateSerialNumber(errors);
      validateInventoryNumber(errors);
      validateMacAddress(errors);
      validateInetAddress(errors);
      validateLocation(errors);
    }
    catch (PersistenceException ex) {
      throw new UnspecifiedConstraintException(ex);
    }
    if (errors.hasErrors()) {
      throw errors;
    }

  }
  
  private void validateSerialNumber(ValidationErrors errors) {
    if (StringUtils.isBlank(getSerialNumber())) {
      errors.addError(ValidationErrors.MISSING_SERIAL_NUMBER_FIELD);
    }
    else if (assetRepository.findAssetBySerialNumber(getSerialNumber()) != null) {
      errors.addError(ValidationErrors.SERIAL_NUMBER_CONFLICT);
    }
  }

  private void validateInventoryNumber(ValidationErrors errors) {
    if (StringUtils.isBlank(getInventoryNumber())) {
      errors.addError(ValidationErrors.MISSING_INVENTORY_NUMBER_FIELD);
    }
    else if (assetRepository
             .findAssetByInventoryNumber(getInventoryNumber()) != null) {
      errors.addError(ValidationErrors.INVENTORY_NUMBER_CONFLICT);
    }
  }
  
  private void validateMacAddress(ValidationErrors errors) {
    if (getMacAddress() == null) {
      errors.addError(ValidationErrors.MISSING_MAC_ADDRESS_FIELD);
    }
    else if (assetRepository.
        findAssetByMacAddress(getMacAddress().toString()) != null) {
      errors.addError(ValidationErrors.MAC_ADDRESS_CONFLICT);
    }
  }
  
  private void validateInetAddress(ValidationErrors errors) {
    if (getInetAddress() == null) {
      errors.addError(ValidationErrors.MISSING_INET_ADDRESS_FIELD);
    }
  }
  
  private void validateLocation(ValidationErrors errors) {
    Building building = null;
    boolean locationComplete = true;
    if (StringUtils.isBlank(getRoomNumber())) {
      errors.addError(ValidationErrors.MISSING_ROOM_FIELD);
      locationComplete = false;
    }
    if (StringUtils.isBlank(getBuildingName())) {
      errors.addError(ValidationErrors.MISSING_BUILDING_FIELD);
      locationComplete = false;
    }
    else {
      building = buildingRepository.findBuildingByName(getBuildingName());
      if (building == null) {
        errors.addError(ValidationErrors.UNKNOWN_BUILDING);
        locationComplete = false;
      }
    }
    if (StringUtils.isBlank(getPositionHint())) {
      errors.addError(ValidationErrors.MISSING_POSITION_HINT_FIELD);
      locationComplete = false;
    }
    if (locationComplete) {
      Room room = roomRepository.findRoom(building.getId(), getRoomNumber());
      PositionHint hint = positionHintRepository.findHint(getPositionHint());
      if ((room != null) && (hint != null)) {
        if (fixtureRepository.findFixtureByLocation(room.getId(),
            hint.getId()) != null) {
          errors.addError(ValidationErrors.LOCATION_CONFLICT);
        }
      }
    }
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  protected Fixture onExecute() throws Exception {
    try {
      Building building = buildingRepository.findBuildingByName(getBuildingName());
      Room room = findOrConstructRoom(building);
      PositionHint hint = findOrConstructPositionHint();
      Asset asset = constructAsset();
      Fixture fixture = new Fixture();
      fixture.setAsset(asset);
      fixture.setDateCreated(dateService.getCurrentDate());
      fixture.setInstalledBy(getInstalledBy());
      fixture.setIpAddress(getInetAddress().toString());
      fixture.setPositionHint(hint);
      fixture.setRoom(room);
      fixtureRepository.addFixture(fixture);
      return fixture;
    }
    catch (Exception ex) {
      throw new UnspecifiedConstraintException(ex);
    }
  }

  private Room findOrConstructRoom(Building building) {
    Room room = roomRepository.findRoom(building.getId(), getRoomNumber());
    if (room == null) {
      room = new Room();
      room.setBuilding(building);
      room.setRoomNumber(getRoomNumber());
      roomRepository.addRoom(room);
    }
    return room;
  }
  
  private PositionHint findOrConstructPositionHint() {
    PositionHint hint = positionHintRepository.findHint(getPositionHint());
    if (hint == null) {
      hint = new PositionHint();
      hint.setHintText(getPositionHint());
      positionHintRepository.addPositionHint(hint);
    }
    return hint;
  }
  
  private Asset constructAsset() {
    Asset asset = new Asset();
    asset.setDateCreated(dateService.getCurrentDate());
    asset.setInventoryNumber(getInventoryNumber());
    asset.setMacAddress(getMacAddress().toString());
    asset.setSerialNumber(getSerialNumber());
    assetRepository.addAsset(asset);
    return asset;
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
   * Gets the {@code serialNumber} property.
   * @return property value
   */
  public String getSerialNumber() {
    return serialNumber;
  }

  /**
   * Sets the {@code serialNumber} property.
   * @param serialNumber the value to set
   */
  public void setSerialNumber(String serialNumber) {
    this.serialNumber = serialNumber;
  }

  /**
   * Gets the {@code inventoryNumber} property.
   * @return property value
   */
  public String getInventoryNumber() {
    return inventoryNumber;
  }

  /**
   * Sets the {@code inventoryNumber} property.
   * @param inventoryNumber the value to set
   */
  public void setInventoryNumber(String inventoryNumber) {
    this.inventoryNumber = inventoryNumber;
  }

  /**
   * Gets the {@code macAddress} property.
   * @return property value
   */
  public MacAddress getMacAddress() {
    return macAddress;
  }

  /**
   * Sets the {@code macAddress} property.
   * @param macAddress the value to set
   */
  public void setMacAddress(MacAddress macAddress) {
    this.macAddress = macAddress;
  }

  /**
   * Gets the {@code installedBy} property.
   * @return property value
   */
  public String getInstalledBy() {
    return installedBy;
  }

  /**
   * Sets the {@code installedBy} property.
   * @param installedBy the value to set
   */
  public void setInstalledBy(String installedBy) {
    this.installedBy = installedBy;
  }

  /**
   * Gets the {@code dateService} property.
   * @return property value
   */
  public DateTimeService getDateService() {
    return dateService;
  }

  /**
   * Sets the {@code assetRepository} property.
   * @param assetRepository the value to set
   */
  @Autowired
  public void setAssetRepository(AssetRepository assetRepository) {
    this.assetRepository = assetRepository;
  }

  /**
   * Sets the {@code buildingRepository} property.
   * @param buildingRepository the value to set
   */
  @Autowired
  public void setBuildingRepository(BuildingRepository buildingRepository) {
    this.buildingRepository = buildingRepository;
  }

  /**
   * Sets the {@code roomRepository} property.
   * @param roomRepository the value to set
   */
  @Autowired
  public void setRoomRepository(RoomRepository roomRepository) {
    this.roomRepository = roomRepository;
  }

  /**
   * Sets the {@code positionHintRepository} property.
   * @param positionHintRepository the value to set
   */
  @Autowired
  public void setPositionHintRepository(
      PositionHintRepository positionHintRepository) {
    this.positionHintRepository = positionHintRepository;
  }

  /**
   * Sets the {@code repository} property.
   * @param repository the fixed repository
   */
  @Autowired
  public void setFixtureRepository(FixtureRepository repository) {
    this.fixtureRepository = repository;
  }

  /**
   * Sets the {@code dateService} property.
   * @param dateService the value to set
   */
  @Autowired
  public void setDateService(DateTimeService dateService) {
    this.dateService = dateService;
  }

}
