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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Entity representing a room.
 *
 * @author Brian Early
 */
@Entity
@Table(name = "Room")
public class Room extends AbstractEntity {

  private static final long serialVersionUID = -8230988187096633342L;
  private Building building;
  private String roomNumber;
  
  /**
   * Gets the {@code building} associated with this room.
   * @return property value
   */
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "building_id")
  public Building getBuilding() {
    return building;
  }
  
  /**
   * Sets the {@code building} associated with this room.
   * @param building building entity to associate with this room
   */
  public void setBuilding(Building building) {
    this.building = building;
  }
  
  /**
   * Gets the {@code roomNumber} property.
   * @return property value
   */
  @Column(name = "room_number")
  public String getRoomNumber() {
    return roomNumber;
  }
  
  /**
   * Sets the {@code roomNumber} property.
   * @param roomNumber property value to set
   */
  public void setRoomNumber(String roomNumber) {
    this.roomNumber = roomNumber;
  }
  
}
