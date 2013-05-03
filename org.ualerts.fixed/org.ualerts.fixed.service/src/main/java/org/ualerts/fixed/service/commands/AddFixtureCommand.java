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

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.ualerts.fixed.Asset;
import org.ualerts.fixed.Building;
import org.ualerts.fixed.Fixture;
import org.ualerts.fixed.InetAddress;
import org.ualerts.fixed.MacAddress;
import org.ualerts.fixed.PositionHint;
import org.ualerts.fixed.Room;
import org.ualerts.fixed.repository.FixedRepository;
import org.ualerts.fixed.service.CommandComponent;
import org.ualerts.fixed.service.errors.InventoryNumberConflictException;
import org.ualerts.fixed.service.errors.LocationConflictException;
import org.ualerts.fixed.service.errors.MacAddressConflictException;
import org.ualerts.fixed.service.errors.MissingFieldException;
import org.ualerts.fixed.service.errors.SerialNumberConflictException;
import org.ualerts.fixed.service.errors.UnknownBuildingException;
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
  private FixedRepository repository;

  /**
   * {@inheritDoc}
   */
  @Override
  protected void onValidate() throws Exception {
    super.onValidate();
    ValidationErrors errors = new ValidationErrors();
    try {
      Building building = null;
      boolean locationComplete = true;
      if (StringUtils.isBlank(roomNumber)) {
        errors.addError(new MissingFieldException("roomNumber"));
        locationComplete = false;
      }
      if (StringUtils.isBlank(buildingName)) {
        errors.addError(new MissingFieldException("buildingName"));
        locationComplete = false;
      }
      else {
        building = repository.findBuildingByName(buildingName);
        if (building == null) {
          errors.addError(new UnknownBuildingException());
          locationComplete = false;
        }
      }
      if (StringUtils.isBlank(positionHint)) {
        errors.addError(new MissingFieldException("positionHint"));
        locationComplete = false;
      }
      if (inetAddress == null) {
        errors.addError(new MissingFieldException("inetAddress"));
      }
      if (StringUtils.isBlank(serialNumber)) {
        errors.addError(new MissingFieldException("serialNumber"));
      }
      else if (repository.findAssetBySerialNumber(serialNumber) != null) {
        errors.addError(new SerialNumberConflictException());
      }
      if (StringUtils.isBlank(inventoryNumber)) {
        errors.addError(new MissingFieldException("inventoryNumber"));
      }
      else if (repository.findAssetByInventoryNumber(inventoryNumber) != null) {
        errors.addError(new InventoryNumberConflictException());
      }
      if (macAddress == null) {
        errors.addError(new MissingFieldException("macAddress"));
      }
      else if (repository.
          findAssetByMacAddress(macAddress.toString()) != null) {
        errors.addError(new MacAddressConflictException());
      }
      if (locationComplete) {
        Room room = repository.findRoom(building.getId(), roomNumber);
        PositionHint hint = repository.findHint(positionHint);
        if ((room != null) && (hint != null)) {
          if (repository.findFixtureByLocation(room.getId(),
              hint.getId()) != null) {
            errors.addError(new LocationConflictException());
          }
        }
      }
    }
    catch (Exception ex) {
      throw new UnspecifiedConstraintException(ex);
    }
    if (errors.hasErrors()) {
      throw errors;
    }

  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Fixture onExecute() throws Exception {
    try {
      Building building = repository.findBuildingByName(buildingName);
      Room room = repository.findRoom(building.getId(), roomNumber);
      if (room == null) {
        room = new Room();
        room.setBuilding(building);
        room.setRoomNumber(roomNumber);
        repository.addRoom(room);
      }
      PositionHint hint = repository.findHint(positionHint);
      if (hint == null) {
        hint = new PositionHint();
        hint.setHintText(positionHint);
        repository.addPositionHint(hint);
      }
      Asset asset = new Asset();
      Date creationDate = new Date();
      asset.setDateCreated(creationDate);
      asset.setInventoryNumber(inventoryNumber);
      asset.setMacAddress(macAddress.toString());
      asset.setSerialNumber(serialNumber);
      repository.addAsset(asset);
      Fixture fixture = new Fixture();
      fixture.setAsset(asset);
      fixture.setDateCreated(creationDate);
      fixture.setInstalledBy(installedBy);
      fixture.setIpAddress(inetAddress.toString());
      fixture.setPositionHint(hint);
      fixture.setRoom(room);
      repository.addFixture(fixture);
      return fixture;
    }
    catch (Exception ex) {
      throw new UnspecifiedConstraintException(ex);
    }
  }

  /**
   * Sets the {@code roomNumber} property.
   * @param roomNumber the room number
   */
  public void setRoomNumber(String roomNumber) {
    this.roomNumber = roomNumber;
  }

  /**
   * Sets the {@code buildingName} property.
   * @param buildingName the building name
   */
  public void setBuildingName(String buildingName) {
    this.buildingName = buildingName;
  }

  /**
   * Sets the {@code positionHint} property.
   * @param positionHint the position hint
   */
  public void setPositionHint(String positionHint) {
    this.positionHint = positionHint;
  }

  /**
   * Sets the {@code inetAddress} property.
   * @param inetAddress the Inet Address
   */
  public void setInetAddress(InetAddress inetAddress) {
    this.inetAddress = inetAddress;
  }

  /**
   * Sets the {@code serialNumber} property.
   * @param serialNumber the serial number
   */
  public void setSerialNumber(String serialNumber) {
    this.serialNumber = serialNumber;
  }

  /**
   * Sets the {@code inventoryNumber} property.
   * @param inventoryNumber the inventory number
   */
  public void setInventoryNumber(String inventoryNumber) {
    this.inventoryNumber = inventoryNumber;
  }

  /**
   * Sets the {@code macAddress} property.
   * @param macAddress the MAC address
   */
  public void setMacAddress(MacAddress macAddress) {
    this.macAddress = macAddress;
  }

  /**
   * Sets the {@code installedBy} property.
   * @param installedBy the value to set
   */
  public void setInstalledBy(String installedBy) {
    this.installedBy = installedBy;
  }

  /**
   * Sets the {@code repository} property.
   * @param repository the fixed repository
   */
  @Autowired
  public void setRepository(FixedRepository repository) {
    this.repository = repository;
  }

}
