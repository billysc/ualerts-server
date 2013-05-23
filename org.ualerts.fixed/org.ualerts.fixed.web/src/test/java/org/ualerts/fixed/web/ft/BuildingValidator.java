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

package org.ualerts.fixed.web.ft;

import static junit.framework.Assert.assertEquals;

import org.ualerts.fixed.service.api.model.BuildingModel;

/**
 * Utility class to help validate buildings
 *
 * @author Michael Irwin
 */
public class BuildingValidator {

  private PropertiesAccessor properties;
  
  /**
   * Constructs a new instance
   * @param properties The properties this validator should work with
   */
  public BuildingValidator(PropertiesAccessor properties) {
    this.properties = properties;
  }
  
  /**
   * Validate that the provided building model is the same as the building set
   * in the properties
   * @param building The building model to validate
   */
  public void validateIsBuilding1(BuildingModel building) {
    assertEquals(properties.getString("building.1.id"), building.getId());
    assertEquals(properties.getString("building.1.abbreviation"), 
        building.getAbbreviation());
    assertEquals(properties.getString("building.1.name"), building.getName());
  }
  
  /**
   * Validate that the provided building model is the same as the building set
   * in the properties
   * @param building The building model to validate
   */
  public void validateIsBuilding2(BuildingModel building) {
    assertEquals(properties.getString("building.2.id"), building.getId());
    assertEquals(properties.getString("building.2.abbreviation"), 
        building.getAbbreviation());
    assertEquals(properties.getString("building.2.name"), building.getName());
  }
  
}
