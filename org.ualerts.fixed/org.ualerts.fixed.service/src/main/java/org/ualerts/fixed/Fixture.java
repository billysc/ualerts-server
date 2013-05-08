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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Entity representing a specific alerting fixture.
 *
 * @author Brian Early
 */
@Entity
@Table(name = "fixture", uniqueConstraints =
       @UniqueConstraint(columnNames = { "room_id", "position_hint_id" }))
public class Fixture extends AbstractEntity {

  private static final long serialVersionUID = -7583794853410644355L;
  private Asset asset;
  private Room room;
  private PositionHint positionHint;
  private String ipAddress;
  private String installedBy;
  private Date dateCreated;
  private Date dateModified;
  
  /**
   * Gets the {@code asset} property.
   * @return property value
   */
  @OneToOne(optional = false)
  public Asset getAsset() {
    return asset;
  }
  
  /**
   * Sets the {@code asset} property.
   * @param asset the property value to set
   */
  public void setAsset(Asset asset) {
    this.asset = asset;
  }
  
  /**
   * Gets the {@code room} property.
   * @return property value
   */
  @ManyToOne
  @JoinColumn(name = "room_id")
  public Room getRoom() {
    return room;
  }
  
  /**
   * Sets the {@code room} property.
   * @param room the property value to set
   */
  public void setRoom(Room room) {
    this.room = room;
  }
  
  /**
   * Gets the {@code positionHint} property.
   * @return property value
   */
  @ManyToOne
  @JoinColumn(name = "position_hint_id")
  public PositionHint getPositionHint() {
    return positionHint;
  }
  
  /**
   * Sets the {@code positionHint} property.
   * @param positionHint the property value to set
   */
  public void setPositionHint(PositionHint positionHint) {
    this.positionHint = positionHint;
  }
  
  /**
   * Gets the {@code ipAddress} property.
   * @return property value
   */
  @Column(name = "ip_address")
  public String getIpAddress() {
    return ipAddress;
  }
  
  /**
   * Sets the {@code ipAddress} property.
   * @param ipAddress the property value to set
   */
  public void setIpAddress(String ipAddress) {
    this.ipAddress = ipAddress;
  }

  /**
   * Gets the {@code installedBy} property.
   * @return property value
   */
  @Column(name = "installed_by")
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
   * Gets the {@code dateCreated} property.
   * @return property value
   */
  @Column(name = "date_created")
  public Date getDateCreated() {
    return dateCreated;
  }

  /**
   * Sets the {@code dateCreated} property.
   * @param dateCreated the property value to set
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
   * @param dateModified the property value to set
   */
  public void setDateModified(Date dateModified) {
    this.dateModified = dateModified;
  }

}
