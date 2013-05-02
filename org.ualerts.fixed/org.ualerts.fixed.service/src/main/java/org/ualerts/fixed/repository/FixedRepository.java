/*
 * File created on May 1, 2013 
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

import org.ualerts.fixed.Asset;
import org.ualerts.fixed.Building;
import org.ualerts.fixed.Fixture;
import org.ualerts.fixed.PositionHint;
import org.ualerts.fixed.Room;

/**
 * Defines basic behavior for the repository supporting fixed services.
 *
 * @author Brian Early
 */
public interface FixedRepository {
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

  /**
   * Finds a room by its building ID and room.
   * @param buildingId the ID of the room's building
   * @param roomNumber the room number
   * @return the Room object.  Null if it isn't found.
   */
  Room findRoom(String buildingId, String roomNumber);
  
  /**
   * Finds a position hint based on its text.
   * @param hintText the text of the hint.
   * @return the PositionHint object.  Null if it isn't found.
   */
  PositionHint findHint(String hintText);
  
  /**
   * Finds an asset by its serial number.
   * @param serialNumber the serial number to look for.
   * @return the Asset object.  Null if it isn't found.
   */
  Asset findAssetBySerialNumber(String serialNumber);
  
  /**
   * Finds an asset by its inventory number.
   * @param inventoryNumber the inventory number to look for.
   * @return the Asset object.  Null if it isn't found.
   */
  Asset findAssetByInventoryNumber(String inventoryNumber);
  
  /**
   * Finds an asset by its MAC address.
   * @param macAddress the MAC address to look for.
   * @return the Asset object.  Null if it isn't found.
   */
  Asset findAssetByMacAddress(String macAddress);
  
  /**
   * Finds a fixture by its location (room & position hint)
   * @param roomId the ID of the room
   * @param hintId the ID of the position hint
   * @return the Fixture object.  Null if it isn't found.
   */
  Fixture findFixtureByLocation(Long roomId, Long hintId);
  
  /**
   * Adds a room to persistence control.
   * @param room the room to add.
   */
  void addRoom(Room room);

  /**
   * Adds a position hint to persistence control.
   * @param hint the position hint to add.
   */
  void addPositionHint(PositionHint hint);
  
  /**
   * Adds an asset to persistence control.
   * @param asset the asset to add.
   */
  void addAsset(Asset asset);
  
  /**
   * Adds a fixture to persistence control.  Assumes that any linked entities
   * have already been persisted.
   * @param fixture the fixture to add.
   */
  void addFixture(Fixture fixture);
}
