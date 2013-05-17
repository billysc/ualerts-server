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

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.ualerts.fixed.web.model.BuildingsModel;
import org.ualerts.fixed.web.service.BuildingService;

/**
 * A controller that provides RESTful interactions
 *
 * @author Michael Irwin
 */
@Controller
public class RestController {
  
  private BuildingService buildingService;

  /**
   * Get all buildings known to the system
   * @return A list of all buildings
   */
  @ResponseBody
  @RequestMapping(value = "/buildings", method = RequestMethod.GET)
  public BuildingsModel getAllBuildings() throws Exception {
    return buildingService.getAllBuildings();
  }
  
  /**
   * Sets the {@code buildingService} property.
   * @param buildingService the value to set
   */
  @Resource
  public void setBuildingService(BuildingService buildingService) {
    this.buildingService = buildingService;
  }
  
}
