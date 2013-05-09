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
import org.ualerts.fixed.Building;

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
  public static final String BUILDING1_NAME = "Building 1";

  /**
   * Abbreviation of a building that is put into persistence for testing
   */
  public static final String BUILDING1_ABBR = "BULD1";
  
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
    createBuilding(entityManager, BUILDING1_NAME, BUILDING1_ABBR);
    tx.commit();
    LOGGER.info("Database populated");
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
  
  private static void createBuilding(EntityManager entityManager, 
      String name, String abbreviation) {
    LOGGER.info(String.format("Creating building %s (%s) : %s", name, 
        abbreviation, lastUsedId));
    Building building = new Building();
    building.setAbbreviation(abbreviation);
    building.setId(lastUsedId.toString());
    building.setName(name);
    entityManager.persist(building);
    lastUsedId++;
  }
  
}
