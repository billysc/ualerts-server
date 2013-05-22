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
package org.ualerts.fixed.web.model;

import org.ualerts.fixed.Fixture;
import org.ualerts.fixed.Room;

/**
 * A model object representing a fixture.
 *
 * @author Michael Irwin
 */
public class FixtureModel {
  
  private Long id;
  private Long version;
  private String buildingId;
  private String building;
  private String buildingAbbreviation;
  private String room;
  private String positionHint;
  private String inventoryNumber;
  private String serialNumber;
  private String ipAddress;
  private String macAddress;
  private String installedBy;
  
  /**
   * Create a new, empty instance.
   */
  public FixtureModel() {
    
  }
  
  /**
   * Create a new instance, based on a Fixture object.
   * @param fixture The object to base construction from.
   */
  public FixtureModel(Fixture fixture) {
    if (fixture.getRoom() != null) {
      Room room = fixture.getRoom();
      this.building = room.getBuilding().getName();
      this.buildingAbbreviation = room.getBuilding().getAbbreviation();
      this.buildingId = room.getBuilding().getId();
      this.room = room.getRoomNumber();
    }
    if (fixture.getAsset() != null) {
      this.inventoryNumber = fixture.getAsset().getInventoryNumber();
      this.macAddress = fixture.getAsset().getMacAddress();
      this.serialNumber = fixture.getAsset().getSerialNumber();
    }
    if (fixture.getPositionHint() != null) {
      this.positionHint = fixture.getPositionHint().getHintText();
    }

    this.id = fixture.getId();
    this.ipAddress = fixture.getIpAddress();
    this.version = fixture.getVersion();
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
   * Gets the {@code version} property.
   * @return property value
   */
  public Long getVersion() {
    return version;
  }
  
  /**
   * Sets the {@code version} property.
   * @param version the value to set
   */
  public void setVersion(Long version) {
    this.version = version;
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
   * Gets the {@code building} property.
   * @return property value
   */
  public String getBuilding() {
    return building;
  }
  
  /**
   * Sets the {@code building} property.
   * @param building the value to set
   */
  public void setBuilding(String building) {
    this.building = building;
  }
  
  /**
   * Gets the {@code buildingAbbreviation} property.
   * @return property value
   */
  public String getBuildingAbbreviation() {
    return buildingAbbreviation;
  }
  
  /**
   * Sets the {@code buildingAbbreviation} property.
   * @param buildingAbbreviation the value to set
   */
  public void setBuildingAbbreviation(String buildingAbbreviation) {
    this.buildingAbbreviation = buildingAbbreviation;
  }
  
  /**
   * Gets the {@code room} property.
   * @return property value
   */
  public String getRoom() {
    return room;
  }
  
  /**
   * Sets the {@code room} property.
   * @param room the value to set
   */
  public void setRoom(String room) {
    this.room = room;
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
   * Gets the {@code ipAddress} property.
   * @return property value
   */
  public String getIpAddress() {
    return ipAddress;
  }
  
  /**
   * Sets the {@code ipAddress} property.
   * @param ipAddress the value to set
   */
  public void setIpAddress(String ipAddress) {
    this.ipAddress = ipAddress;
  }
  
  /**
   * Gets the {@code macAddress} property.
   * @return property value
   */
  public String getMacAddress() {
    return macAddress;
  }
  
  /**
   * Sets the {@code macAddress} property.
   * @param macAddress the value to set
   */
  public void setMacAddress(String macAddress) {
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
  
}
