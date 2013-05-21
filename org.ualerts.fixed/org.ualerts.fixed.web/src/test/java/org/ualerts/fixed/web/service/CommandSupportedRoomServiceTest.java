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
import org.ualerts.fixed.Room;
import org.ualerts.fixed.service.CommandService;
import org.ualerts.fixed.service.commands.FindRoomsForBuildingCommand;
import org.ualerts.fixed.web.model.RoomsModel;

/**
 * Test case for the CommandSupportedRoomService class
 *
 * @author Michael Irwin
 */
public class CommandSupportedRoomServiceTest {

  private Mockery context;
  private CommandService commandService;
  private CommandSupportedRoomService service;
  
  /**
   * Setup tasks
   */
  @Before
  public void setUp() {
    context = new Mockery();
    commandService = context.mock(CommandService.class);
    service = new CommandSupportedRoomService();
    service.setCommandService(commandService);
  }
  
  /**
   * Validate the getAllRoomsForBuilding method
   */
  @Test
  public void testGetAllRoomsForBuilding() throws Exception {
    final String buildingId = "12";
    final FindRoomsForBuildingCommand command = 
        new FindRoomsForBuildingCommand();
    final List<Room> rooms = new ArrayList<Room>();
    Room room = new Room();
    room.setRoomNumber("100");
    rooms.add(room);
    
    context.checking(new Expectations() { { 
      oneOf(commandService).newCommand(FindRoomsForBuildingCommand.class);
      will(returnValue(command));
      oneOf(commandService).invoke(command);
      will(returnValue(rooms));
    } });
    
    RoomsModel roomsModel = service.getRoomsForBuilding(buildingId);
    context.assertIsSatisfied();
    assertEquals(buildingId, command.getBuildingId());
    assertNotNull(roomsModel);
    assertEquals(rooms.size(), roomsModel.getRooms().length);
    assertEquals(room.getRoomNumber(), roomsModel.getRooms()[0]);
  }
  
}
