/*
 * File created on May 9, 2013 
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
package org.ualerts.fixed.service.commands;

import java.util.List;

import javax.annotation.Resource;

import org.ualerts.fixed.Building;
import org.ualerts.fixed.repository.BuildingRepository;
import org.ualerts.fixed.service.CommandComponent;
import org.ualerts.fixed.service.errors.UnspecifiedConstraintException;

/**
 * Command to find all buildings in the UAlerts system.
 *
 * @author Brian Early
 */
@CommandComponent
public class FindAllBuildingsCommand extends AbstractCommand<List<Building>> {
  private BuildingRepository buildingRepository;

  /**
   * {@inheritDoc}
   */
  @Override
  protected List<Building> onExecute() throws UnspecifiedConstraintException {
    return buildingRepository.findAllBuildings();
  }

  /**
   * Sets the {@code buildingRepository} property.
   * @param buildingRepository the building repository
   */
  @Resource
  public void setBuildingRepository(BuildingRepository buildingRepository) {
    this.buildingRepository = buildingRepository;
  }

}
