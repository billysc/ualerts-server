/*
 * File created on May 8, 2013
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

package org.ualerts.fixed.web.ft;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ualerts.fixed.Asset;
import org.ualerts.fixed.Building;
import org.ualerts.fixed.Fixture;
import org.ualerts.fixed.PositionHint;
import org.ualerts.fixed.Room;

/**
 * Simple bean that injects data into the database to be used for functional
 * tests
 *
 * @author Michael Irwin
 */
public final class DBSetupUtility {

  /**
   * Name of a building that is put into persistence for testing
   */
  public static final String BUILDING_NAME = "Building 1";

  /**
   * Abbreviation of a building that is put into persistence for testing
   */
  public static final String BUILDING_ABBR = "BULD1";

  /**
   * Installer for Fixture
   */
  public static final String FIXTURE_INSTALLED_BY = "installerA";
  
  /**
   * Inventory number used for fixture creation
   */
  public static final String FIXTURE_INV_NUMBER = "INV-12345";
  
  /**
   * IP Address used for fixture creation
   */
  public static final String FIXTURE_IP_ADDR = "192.168.1.1";
  
  /**
   * MAC Address used for fixture creation
   */
  public static final String FIXTURE_MAC_ADDR = "0A:12:34:0B:56:78";
  
  /**
   * Position hint text used for fixture creation
   */
  public static final String FIXTURE_POSITION_HINT = "TOP-RIGHT";
  
  /**
   * Room number used for fixture creation
   */
  public static final String FIXTURE_ROOM_NUMBER = "123";
  
  /**
   * Serial number used for fixture creation
   */
  public static final String FIXTURE_SER_NUMBER = "SER-12345";
  
  private static final Logger LOGGER = 
      LoggerFactory.getLogger(DBSetupUtility.class);
  
  private static Long lastUsedId = 1L;
  
  private DBSetupUtility() {
    // Do nothing
  }
  
  /**
   * Populate the database as needed
   * @param entityManager The EntityManager to use for database connection
   */
  public static void populateDatabase(EntityManager entityManager) 
      throws Exception {
    EntityTransaction tx = entityManager.getTransaction();
    tx.begin();
    Building building = createBuilding(entityManager);
    createFixture(entityManager, building);
    tx.commit();
    LOGGER.info("Database populated");
  }
  
  /**
   * Populate the database only with a building
   * @param entityManager The EntityManager to work with
   */
  public static void populateBuildings(EntityManager entityManager) {
    EntityTransaction tx = entityManager.getTransaction();
    tx.begin();
    Building building = createBuilding(entityManager);
    tx.commit();
    LOGGER.info("Buildings populated");
  }
  
  /**
   * Clean out the database
   * @param entityManager The EntityManager to use for database connection
   */
  public static void cleanDatabase(EntityManager entityManager) {
    EntityTransaction tx = entityManager.getTransaction();
    tx.begin();
    entityManager.createNativeQuery("DELETE FROM fixture").executeUpdate();
    entityManager.createNativeQuery("DELETE FROM room").executeUpdate();
    entityManager.createNativeQuery("DELETE FROM building").executeUpdate();
    entityManager.createNativeQuery("DELETE FROM position_hint")
      .executeUpdate();
    entityManager.createNativeQuery("DELETE FROM fixed_asset").executeUpdate();
    tx.commit();
    LOGGER.info("Database cleaned");
  }
  
  private static Building createBuilding(EntityManager entityManager) {
    Building building = new Building();
    building.setAbbreviation(BUILDING_ABBR);
    building.setId(lastUsedId.toString());
    building.setName(BUILDING_NAME);
    entityManager.persist(building);
    lastUsedId++;
    return building;
  }
  
  private static void createFixture(EntityManager entityManager, 
      Building building) {
    Asset asset = new Asset();
    asset.setInventoryNumber(FIXTURE_INV_NUMBER);
    asset.setMacAddress(FIXTURE_MAC_ADDR);
    asset.setSerialNumber(FIXTURE_SER_NUMBER);
    entityManager.persist(asset);
    
    PositionHint positionHint = new PositionHint();
    positionHint.setHintText(FIXTURE_POSITION_HINT);
    entityManager.persist(positionHint);
    
    Room room = new Room();
    room.setBuilding(building);
    room.setRoomNumber(FIXTURE_ROOM_NUMBER);
    entityManager.persist(room);
    
    Fixture fixture = new Fixture();
    fixture.setAsset(asset);
    fixture.setInstalledBy(FIXTURE_INSTALLED_BY);
    fixture.setIpAddress(FIXTURE_IP_ADDR);
    fixture.setPositionHint(positionHint);
    fixture.setRoom(room);
    entityManager.persist(fixture);
  }
  
}
