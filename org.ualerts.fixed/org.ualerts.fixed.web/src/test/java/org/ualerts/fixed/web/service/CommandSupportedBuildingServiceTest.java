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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;
import org.ualerts.fixed.Building;
import org.ualerts.fixed.service.CommandService;
import org.ualerts.fixed.service.commands.FindAllBuildingsCommand;
import org.ualerts.fixed.web.model.BuildingModel;
import org.ualerts.fixed.web.model.BuildingsModel;

/**
 * Test case for the CommandSupportedBuildingService class
 *
 * @author Michael Irwin
 */
public class CommandSupportedBuildingServiceTest {

  private Mockery context;
  private CommandService commandService;
  private CommandSupportedBuildingService service;
  
  /**
   * Setup tasks
   */
  @Before
  public void setUp() {
    context = new Mockery();
    commandService = context.mock(CommandService.class);
    service = new CommandSupportedBuildingService();
    service.setCommandService(commandService);
  }
  
  /**
   * Validate the getAllBuildings method
   */
  @Test
  public void testRetrieveAllBuildings() throws Exception {
    final FindAllBuildingsCommand command = new FindAllBuildingsCommand();
    final List<Building> buildings = new ArrayList<Building>();
    Building building = new Building();
    building.setAbbreviation("ACME");
    building.setId("1995");
    building.setName("Acme Labs");
    buildings.add(building);
    
    context.checking(new Expectations() { { 
      oneOf(commandService).newCommand(FindAllBuildingsCommand.class);
      will(returnValue(command));
      oneOf(commandService).invoke(command);
      will(returnValue(buildings));
    } });
    
    BuildingsModel buildingsModel = service.getAllBuildings();
    context.assertIsSatisfied();
    assertNotNull(buildingsModel);
    assertEquals(buildings.size(), buildingsModel.getBuildings().length);
    BuildingModel buildingModel = buildingsModel.getBuildings()[0];
    assertEquals(building.getAbbreviation(), buildingModel.getAbbreviation());
    assertEquals(building.getId(), buildingModel.getId());
    assertEquals(building.getName(), buildingModel.getName());
  }
}
