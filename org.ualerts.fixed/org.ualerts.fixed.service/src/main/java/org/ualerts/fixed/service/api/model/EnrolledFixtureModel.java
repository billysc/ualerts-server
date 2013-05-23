/*
 * File created on May 23, 2013
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

package org.ualerts.fixed.service.api.model;

import java.util.Date;

/**
 * Defines a model for a Fixture that was enrolled from a discovered asset.
 *
 * @author Brian Early
 * @author Michael Irwin
 */
public class EnrolledFixtureModel {

  private Long fixtureId;
  private String buildingAbbreviation;
  private String room;
  private Date enrolledOn;
  private String macAddress;
  private String ipAddress;
  private String installedBy;
  
  /**
   * Gets the {@code fixtureId} property.
   * @return property value
   */
  public Long getFixtureId() {
    return fixtureId;
  }
  
  /**
   * Sets the {@code fixtureId} property.
   * @param fixtureId the value to set
   */
  public void setFixtureId(Long fixtureId) {
    this.fixtureId = fixtureId;
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
   * Gets the {@code enrolledOn} property.
   * @return property value
   */
  public Date getEnrolledOn() {
    return enrolledOn;
  }
  
  /**
   * Sets the {@code enrolledOn} property.
   * @param enrolledOn the value to set
   */
  public void setEnrolledOn(Date enrolledOn) {
    this.enrolledOn = enrolledOn;
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
