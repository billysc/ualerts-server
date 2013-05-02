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
import org.ualerts.fixed.domain.Asset;
import org.ualerts.fixed.domain.Building;
import org.ualerts.fixed.domain.Fixture;
import org.ualerts.fixed.domain.PositionHint;
import org.ualerts.fixed.domain.Room;
import org.ualerts.fixed.repository.FixedRepository;
import org.ualerts.fixed.service.CommandComponent;
import org.ualerts.fixed.service.InetAddress;
import org.ualerts.fixed.service.InvalidRequestException;
import org.ualerts.fixed.service.MacAddress;

/**
 * Command to add a fixture to the UAlerts system.
 *
 * @author Brian Early
 */
@CommandComponent
public class AddFixtureCommand extends AbstractCommand<Fixture> {
  private String roomNumber;
  private String buildingId;
  private String positionHint;
  private String ipAddress;
  private String serialNumber;
  private String inventoryNumber;
  private String macAddress;
  private FixedRepository repository;
  
  /**
   * {@inheritDoc}
   */
  @Override
  protected void onValidate() throws Exception {
    super.onValidate();
    if (StringUtils.isBlank(roomNumber)) {
      throw new InvalidRequestException("Room is required.");
    }
    if (StringUtils.isBlank(buildingId)) {
      throw new InvalidRequestException("Building ID is required.");
    }
    if (StringUtils.isBlank(positionHint)) {
      throw new InvalidRequestException("Position hint is required.");
    }
    if (StringUtils.isBlank(ipAddress)) {
      throw new InvalidRequestException("IP Address is required.");
    }
    if (StringUtils.isBlank(serialNumber)) {
      throw new InvalidRequestException("Serial number is required.");
    }
    if (StringUtils.isBlank(inventoryNumber)) {
      throw new InvalidRequestException("Inventory number is required.");
    }
    if (StringUtils.isBlank(macAddress)) {
      throw new InvalidRequestException("MAC Address is required.");
    }
    try {
      InetAddress.getByAddress(ipAddress);
    }
    catch (Exception ex) {
      throw new InvalidRequestException("Invalid IP address.");
    }
    try {
      new MacAddress(macAddress);
    }
    catch (Exception ex) {
      throw new InvalidRequestException("Invalid MAC address.");
    }
    if (repository.findBuildingById(buildingId) == null) {
      throw new InvalidRequestException("Unknown building.");
    }
    if (repository.findAssetByInventoryNumber(inventoryNumber) != null) {
      throw new InvalidRequestException("Inventory number is already in use.");
    }
    if (repository.findAssetBySerialNumber(serialNumber) != null) {
      throw new InvalidRequestException("Serial number is already in use.");
    }
    if (repository.findAssetByMacAddress(macAddress) != null) {
      throw new InvalidRequestException("MAC address is already in use.");
    }
    Room room = repository.findRoom(buildingId, roomNumber);
    PositionHint hint = repository.findHint(positionHint);
    if ((room != null) && (hint != null)) {
      if (repository.findFixtureByLocation(room.getId(), hint.getId()) != null) {
        throw new InvalidRequestException("Location (room & position hint) is already in use.");
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Fixture onExecute() throws Exception {
    Room room = repository.findRoom(buildingId, roomNumber);
    if (room == null) {
      Building building = repository.findBuildingById(buildingId);
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
    asset.setMacAddress(macAddress);
    asset.setSerialNumber(serialNumber);
    repository.addAsset(asset);
    Fixture fixture = new Fixture();
    fixture.setAsset(asset);
    fixture.setDateCreated(creationDate);
    fixture.setIpAddress(ipAddress);
    fixture.setPositionHint(hint);
    fixture.setRoom(room);
    repository.addFixture(fixture);
    return fixture;
  }

  /**
   * Sets the {@code roomNumber} property.
   */
  public void setRoomNumber(String roomNumber) {
    this.roomNumber = roomNumber;
  }

  /**
   * Sets the {@code buildingId} property.
   */
  public void setBuildingId(String buildingId) {
    this.buildingId = buildingId;
  }

  /**
   * Sets the {@code positionHint} property.
   */
  public void setPositionHint(String positionHint) {
    this.positionHint = positionHint;
  }

  /**
   * Sets the {@code ipAddress} property.
   */
  public void setIpAddress(String ipAddress) {
    this.ipAddress = ipAddress;
  }

  /**
   * Sets the {@code serialNumber} property.
   */
  public void setSerialNumber(String serialNumber) {
    this.serialNumber = serialNumber;
  }

  /**
   * Sets the {@code inventoryNumber} property.
   */
  public void setInventoryNumber(String inventoryNumber) {
    this.inventoryNumber = inventoryNumber;
  }

  /**
   * Sets the {@code macAddress} property.
   */
  public void setMacAddress(String macAddress) {
    this.macAddress = macAddress;
  }

  /**
   * Sets the {@code repository} property.
   */
  @Autowired
  public void setRepository(FixedRepository repository) {
    this.repository = repository;
  }

}
