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

package org.ualerts.fixed.web.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.ualerts.fixed.Room;
import org.ualerts.fixed.service.CommandService;
import org.ualerts.fixed.service.commands.FindRoomsForBuildingCommand;
import org.ualerts.fixed.web.model.RoomsModel;

/**
 * An implementation of RoomService that uses the CommandService when working
 * with the backend.
 *
 * @author Michael Irwin
 */
@Service
public class CommandSupportedRoomService implements RoomService {

  private CommandService commandService;
  
  /**
   * {@inheritDoc}
   */
  @Override
  public RoomsModel getRoomsForBuilding(String buildingId) throws Exception {
    FindRoomsForBuildingCommand command = 
        commandService.newCommand(FindRoomsForBuildingCommand.class);
    command.setBuildingId(buildingId);
    
    List<String> rooms = new ArrayList<String>();
    for (Room room : commandService.invoke(command)) {
      rooms.add(room.getRoomNumber());
    }
    return new RoomsModel(rooms.toArray(new String[0]));
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
