/*
 * File created on May 20, 2013
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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * A model that contains a collection of room strings.
 *
 * @author Michael Irwin
 */
@XmlRootElement(name = "rooms")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class RoomsModel {

  private String[] rooms;
  
  /**
   * Constructs a new instance.
   */
  public RoomsModel() {
  }
  
  /**
   * Constructs a new instance with the provided room data
   * @param rooms The rooms to populate the model with
   */
  public RoomsModel(String[] rooms) {
    this.rooms = rooms;
  }
  
  /**
   * Gets the {@code rooms} property.
   * @return property value
   */
  @XmlElement(name = "room")
  public String[] getRooms() {
    return rooms;
  }
  
  /**
   * Sets the {@code rooms} property.
   * @param rooms the value to set
   */
  public void setRooms(String[] rooms) {
    this.rooms = rooms;
  }
  
}
