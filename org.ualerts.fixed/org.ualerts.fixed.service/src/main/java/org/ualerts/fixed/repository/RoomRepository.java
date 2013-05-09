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

import java.util.List;

import org.ualerts.fixed.Room;

/**
 * Defines basic behavior for the repository supporting rooms.
 *
 * @author Brian Early
 */
public interface RoomRepository {
  /**
   * Finds a room by its building ID and room.
   * @param buildingId the ID of the room's building
   * @param roomNumber the room number
   * @return the Room object.  Null if it isn't found.
   */
  Room findRoom(String buildingId, String roomNumber);
  
  /**
   * Finds all rooms associated with a particular building.
   * @param buildingId the ID of the building
   * @return list of Room objects.  May be empty/null.
   */
  List<Room> findRoomsForBuilding(String buildingId);
  
  /**
   * Adds a room to persistence control.
   * @param room the room to add.
   */
  void addRoom(Room room);

}
