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

package org.ualerts.fixed.web.controller;

import static org.junit.Assert.assertEquals;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;
import org.ualerts.fixed.web.model.BuildingsModel;
import org.ualerts.fixed.web.model.PositionHintsModel;
import org.ualerts.fixed.web.service.BuildingService;
import org.ualerts.fixed.web.service.PositionHintService;

/**
 * Test case for RestController
 *
 * @author Michael Irwin
 */
public class RestControllerTest {

  private Mockery context;
  private BuildingService buildingService;
  private PositionHintService positionHintService;
  private RestController controller;
  
  /**
   * Setup tasks
   */
  @Before
  public void setUp() {
    context = new Mockery();
    buildingService = context.mock(BuildingService.class);
    positionHintService = context.mock(PositionHintService.class);
    
    controller = new RestController();
    controller.setBuildingService(buildingService);
    controller.setPositionHintService(positionHintService);
  }
  
  /**
   * Validate the get all buildings method
   */
  @Test
  public void testGetAllBuildings() throws Exception {
    final BuildingsModel buildings = new BuildingsModel();
    context.checking(new Expectations() { { 
      oneOf(buildingService).getAllBuildings();
      will(returnValue(buildings));
    } });
    
    assertEquals(buildings, controller.getAllBuildings());
    context.assertIsSatisfied();
  }
  
  /**
   * Validate the get all position hints method
   * @throws Exception
   */
  @Test
  public void testGetAllPositionHints() throws Exception {
    final PositionHintsModel model = new PositionHintsModel();
    context.checking(new Expectations() { { 
      oneOf(positionHintService).getAllPositionHints();
      will(returnValue(model));
    } });
    assertEquals(model, controller.getAllPositionHints());
    context.assertIsSatisfied();
  }
  
}
