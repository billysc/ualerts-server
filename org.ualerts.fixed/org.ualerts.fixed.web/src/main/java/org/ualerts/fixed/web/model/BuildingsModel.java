/*
 * File created on May 15, 2013
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

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Model that wraps a collection of buildings
 *
 * @author Michael Irwin
 */
@XmlRootElement(name = "buildings")
public class BuildingsModel {

  @XmlElement(name = "building")
  private BuildingModel[] buildings;

  /**
   * Constructs a new, empty instance.
   */
  public BuildingsModel() {
    
  }
  
  /**
   * Constructs a new instance with the provided set of buildings.
   * @param buildings The buildings this model should contain
   */
  public BuildingsModel(List<BuildingModel> buildings) {
    this.buildings = buildings.toArray(new BuildingModel[ buildings.size() ]);
  }
  
  /**
   * Gets the {@code buildings} property.
   * @return property value
   */
  public BuildingModel[] getBuildings() {
    return buildings;
  }
  
  /**
   * Sets the {@code buildings} property.
   * @param buildings the value to set
   */
  public void setBuildings(BuildingModel[] buildings) {
    this.buildings = buildings;
  }
  
}
