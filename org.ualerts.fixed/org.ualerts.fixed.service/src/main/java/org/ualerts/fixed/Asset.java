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
package org.ualerts.fixed;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * An entity representing an asset.
 *
 * @author Brian Early
 */
@Entity
@Table(name = "fixed_asset")
public class Asset extends AbstractEntity {

  private static final long serialVersionUID = -8272019373642337929L;
  private String serialNumber;
  private String inventoryNumber;
  private String macAddress;
  private Date dateCreated;
  private Date dateModified;

  /**
   * Gets the {@code serialNumber} property.
   * @return property value
   */
  @Column(name = "serial_number", unique = true)
  public String getSerialNumber() {
    return serialNumber;
  }

  /**
   * Sets the {@code serialNumber} property.
   * @param serialNumber property value to set
   */
  public void setSerialNumber(String serialNumber) {
    this.serialNumber = serialNumber;
  }

  /**
   * Gets the {@code inventoryNumber} property.
   * @return property value
   */
  @Column(name = "inventory_number", unique = true)
  public String getInventoryNumber() {
    return inventoryNumber;
  }

  /**
   * Sets the {@code inventoryNumber} property.
   * @param inventoryNumber property value to set
   */
  public void setInventoryNumber(String inventoryNumber) {
    this.inventoryNumber = inventoryNumber;
  }

  /**
   * Gets the {@code macAddress} property.
   * @return property value
   */
  @Column(name = "mac_address", unique = true)
  public String getMacAddress() {
    return macAddress;
  }

  /**
   * Sets the {@code macAddress} property.
   * @param macAddress property value to set
   */
  public void setMacAddress(String macAddress) {
    this.macAddress = macAddress;
  }

  /**
   * Gets the {@code dateCreated} property.
   * @return property value
   */
  @Column(name = "date_created")
  public Date getDateCreated() {
    return dateCreated;
  }

  /**
   * Sets the {@code dateCreated} property.
   * @param dateCreated property value to set
   */
  public void setDateCreated(Date dateCreated) {
    this.dateCreated = dateCreated;
  }

  /**
   * Gets the {@code dateModified} property.
   * @return property value
   */
  @Column(name = "date_modified")
  public Date getDateModified() {
    return dateModified;
  }

  /**
   * Sets the {@code dateModified} property.
   * @param dateModified property value to set
   */
  public void setDateModified(Date dateModified) {
    this.dateModified = dateModified;
  }
  
}
