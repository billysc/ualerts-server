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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.ualerts.fixed.web.model.BuildingsModel;
import org.ualerts.fixed.web.model.PositionHintsModel;
import org.ualerts.fixed.web.model.RoomsModel;
import org.ualerts.fixed.web.service.BuildingService;
import org.ualerts.fixed.web.service.PositionHintService;
import org.ualerts.fixed.web.service.RoomService;

/**
 * A controller that provides RESTful interactions
 *
 * @author Michael Irwin
 */
@Controller
public class RestController {
  
  private BuildingService buildingService;
  private PositionHintService positionHintService;
  private RoomService roomService;

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
   * Get all of the position hints known to the system
   * @return A collection of position hints
   */
  @ResponseBody
  @RequestMapping(value = "/positionHints", method = RequestMethod.GET)
  public PositionHintsModel getAllPositionHints() throws Exception {
    return positionHintService.getAllPositionHints();
  }
  
  /**
   * Get all of the rooms for a given building
   * @param buildingId The id of the building to get rooms for
   * @return The rooms for the building
   */
  @ResponseBody
  @RequestMapping(value = "/buildings/{id}/rooms", method = RequestMethod.GET)
  public RoomsModel getRoomsForBuilding(@PathVariable("id") String buildingId) 
      throws Exception {
    return roomService.getRoomsForBuilding(buildingId);
  }
  
  /**
   * Sets the {@code buildingService} property.
   * @param buildingService the value to set
   */
  @Resource
  public void setBuildingService(BuildingService buildingService) {
    this.buildingService = buildingService;
  }
  
  /**
   * Sets the {@code positionHintService} property.
   * @param positionHintService the value to set
   */
  @Resource
  public void setPositionHintService(PositionHintService positionHintService) {
    this.positionHintService = positionHintService;
  }
  
  /**
   * Sets the {@code roomService} property.
   * @param roomService the value to set
   */
  @Resource
  public void setRoomService(RoomService roomService) {
    this.roomService = roomService;
  }
  
}
