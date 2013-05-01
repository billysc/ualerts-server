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
package org.ualerts.fixed.web.dto;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * A Data Transfer Object representing a fixture.
 *
 * @author Michael Irwin
 */
public class FixtureDTO {

  @NotEmpty
  private String building;
  
  @NotEmpty
  private String room;
  
  @NotEmpty
  private String positionHint;
  
  private String inventoryNumber;
  
  @NotEmpty
  private String serialNumber;
  
  @NotEmpty
  @Pattern(regexp="^(?:[0-9]{1,3}\\.){3}[0-9]{1,3}$")
  private String ipAddress;

  @NotEmpty
  @Pattern(regexp="^([0-9A-F]{2}[:-]){5}([0-9A-F]{2})$")
  private String macAddress;
  
  /**
   * Gets the {@code building} property.
   */
  public String getBuilding() {
    return building;
  }
  
  /**
   * Sets the {@code building} property.
   */
  public void setBuilding(String building) {
    this.building = building;
  }
  
  /**
   * Gets the {@code room} property.
   */
  public String getRoom() {
    return room;
  }
  
  /**
   * Sets the {@code room} property.
   */
  public void setRoom(String room) {
    this.room = room;
  }
  
  /**
   * Gets the {@code positionHint} property.
   */
  public String getPositionHint() {
    return positionHint;
  }
  
  /**
   * Sets the {@code positionHint} property.
   */
  public void setPositionHint(String positionHint) {
    this.positionHint = positionHint;
  }
  
  /**
   * Gets the {@code inventoryNumber} property.
   */
  public String getInventoryNumber() {
    return inventoryNumber;
  }
  
  /**
   * Sets the {@code inventoryNumber} property.
   */
  public void setInventoryNumber(String inventoryNumber) {
    this.inventoryNumber = inventoryNumber;
  }
  
  /**
   * Gets the {@code serialNumber} property.
   */
  public String getSerialNumber() {
    return serialNumber;
  }
  
  /**
   * Sets the {@code serialNumber} property.
   */
  public void setSerialNumber(String serialNumber) {
    this.serialNumber = serialNumber;
  }
  
  /**
   * Gets the {@code ipAddress} property.
   */
  public String getIpAddress() {
    return ipAddress;
  }
  
  /**
   * Sets the {@code ipAddress} property.
   */
  public void setIpAddress(String ipAddress) {
    this.ipAddress = ipAddress;
  }
  
  /**
   * Gets the {@code macAddress} property.
   */
  public String getMacAddress() {
    return macAddress;
  }
  
  /**
   * Sets the {@code macAddress} property.
   */
  public void setMacAddress(String macAddress) {
    this.macAddress = macAddress;
  }
  
}
