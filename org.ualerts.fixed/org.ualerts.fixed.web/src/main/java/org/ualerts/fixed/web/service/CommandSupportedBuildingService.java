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

package org.ualerts.fixed.web.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.ualerts.fixed.Building;
import org.ualerts.fixed.service.CommandService;
import org.ualerts.fixed.service.commands.FindAllBuildingsCommand;
import org.ualerts.fixed.web.model.BuildingModel;
import org.ualerts.fixed.web.model.BuildingsModel;

/**
 * Implementation of the BuildingService that uses commands to provide all
 * needed functionality.
 *
 * @author Michael Irwin
 */
@Service
public class CommandSupportedBuildingService implements BuildingService {

  private CommandService commandService;
  
  /**
   * {@inheritDoc}
   */
  @Override
  public BuildingsModel getAllBuildings() throws Exception {
    List<BuildingModel> buildings = new ArrayList<BuildingModel>();
    
    FindAllBuildingsCommand command = 
        commandService.newCommand(FindAllBuildingsCommand.class);
    for (Building building : commandService.invoke(command)) {
      buildings.add(new BuildingModel(building));
    }
    
    return new BuildingsModel(buildings);
  }
  
  /**
   * Sets the {@code commandService} property.
   * @param commandService the value to set
   */
  @Resource
  public void setCommandService(CommandService commandService) {
    this.commandService = commandService;
  }
  
}
