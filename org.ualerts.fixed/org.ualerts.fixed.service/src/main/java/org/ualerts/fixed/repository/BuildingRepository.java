/*
 * File created on May 6, 2013
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

package org.ualerts.fixed.repository;

import org.ualerts.fixed.Building;

/**
 * Defines behavior for the repository supporting buildings.
 *
 * @author Brian Early
 */
public interface BuildingRepository {
  /**
   * Finds a building by its ID.
   * @param id the ID of the building
   * @return the Building object.  Null if it isn't found.
   */
  Building findBuildingById(String id);
  
  /**
   * Finds a building by its name.
   * @param name the name of the building
   * @return the Building object.  Null if it isn't found.
   */
  Building findBuildingByName(String name);

}
