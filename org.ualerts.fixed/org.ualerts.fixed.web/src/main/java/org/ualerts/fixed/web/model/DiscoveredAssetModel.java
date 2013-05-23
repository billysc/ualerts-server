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

package org.ualerts.fixed.web.model;

import java.util.Date;

/**
 * Model to represent a Discovered Asset.
 *
 * @author Brian Early
 * @author Michael Irwin
 */
public class DiscoveredAssetModel {

  private Long id;
  private String status;
  private String macAddress;
  private String ipAddress;
  private Date dateCreated;
  private Date dateModified;
  private String discoveryHint;
  private String serialNumber;
  private String inventoryNumber;
  
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
   * Gets the {@code status} property.
   * @return property value
   */
  public String getStatus() {
    return status;
  }
  
  /**
   * Sets the {@code status} property.
   * @param status the value to set
   */
  public void setStatus(String status) {
    this.status = status;
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
   * Gets the {@code dateCreated} property.
   * @return property value
   */
  public Date getDateCreated() {
    return dateCreated;
  }
  
  /**
   * Sets the {@code dateCreated} property.
   * @param dateCreated the value to set
   */
  public void setDateCreated(Date dateCreated) {
    this.dateCreated = dateCreated;
  }
  
  /**
   * Gets the {@code dateModified} property.
   * @return property value
   */
  public Date getDateModified() {
    return dateModified;
  }
  
  /**
   * Sets the {@code dateModified} property.
   * @param dateModified the value to set
   */
  public void setDateModified(Date dateModified) {
    this.dateModified = dateModified;
  }
  
  /**
   * Gets the {@code discoveryHint} property.
   * @return property value
   */
  public String getDiscoveryHint() {
    return discoveryHint;
  }
  
  /**
   * Sets the {@code discoveryHint} property.
   * @param discoveryHint the value to set
   */
  public void setDiscoveryHint(String discoveryHint) {
    this.discoveryHint = discoveryHint;
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
  
}
