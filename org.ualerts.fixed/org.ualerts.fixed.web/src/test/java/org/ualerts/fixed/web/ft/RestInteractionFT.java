/*
 * File created on May 9, 2013
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ualerts.fixed.web.model.BuildingsModel;
import org.ualerts.fixed.web.model.PositionHintsModel;
import org.ualerts.fixed.web.model.RoomsModel;
import org.ualerts.testing.jpa.EntityManagerFactoryResource;
import org.ualerts.testing.jpa.HibernatePersistentDataResource;
import org.ualerts.testing.jpa.PersistentDataResource;
import org.ualerts.testing.jpa.TestResources;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

import edu.vt.cns.kestrel.common.IntegrationTestRunner;

/**
 * Functional tests for Fixtures View
 *
 * @author Michael Irwin
 */
@RunWith(IntegrationTestRunner.class)
public class RestInteractionFT extends AbstractFunctionalTest {
  
  private static final String REST_POINT = "/api";
  private static final String BUILDING_URL = "/buildings";
  private static final String POSITION_HINTS_URL = "/positionHints";
  private static final String ROOMS_URL = "/buildings/%s/rooms";

  // CHECKSTYLE:OFF
  @ClassRule
  public static EntityManagerFactoryResource entityManagerFactory = 
      new EntityManagerFactoryResource("persistence-test.properties");
  
  @Rule
  public PersistentDataResource persistentData =
     new HibernatePersistentDataResource(entityManagerFactory);
  // CHECKSTYLE:ON
  
  private static PropertiesAccessor properties;
      
  private BuildingValidator buildingValidator;
  private Client client;
  
    /**
   * Perform one-time set up tasks
   * @throws Exception
   */
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    properties = PropertiesAccessor.newInstance("persistent-data.properties");
  }
  
  /**
   * Setup tasks
   */
  @Before
  public void setUp() {
    ClientConfig clientConfig = new DefaultClientConfig();
    clientConfig.getClasses().add(JacksonJsonProvider.class);
    client = Client.create(clientConfig);
    buildingValidator = new BuildingValidator(properties);
  }
  
  /**
   * Test the retrieval of all buildings, using XML
   */
  @Test
  @TestResources(prefix = "sql/", before = "RestInteractionFT_buildings_before",
      after = "RestInteractionFT_buildings_after")
  public void testGetBuildingsXML() {
    BuildingsModel buildings = getBuildings("application/xml");
    assertEquals(2, buildings.getBuildings().length);
    buildingValidator.validateIsBuilding1(buildings.getBuildings()[0]);
    buildingValidator.validateIsBuilding2(buildings.getBuildings()[1]);
  }
  
  /**
   * Test the retrieval of all buildings, using JSON
   */
  @Test
  @TestResources(prefix = "sql/", before = "RestInteractionFT_buildings_before",
      after = "RestInteractionFT_buildings_after")
  public void testGetBuildingsJSON() {
    BuildingsModel buildings = getBuildings("application/json");
    assertEquals(2, buildings.getBuildings().length);
    buildingValidator.validateIsBuilding1(buildings.getBuildings()[0]);
    buildingValidator.validateIsBuilding2(buildings.getBuildings()[1]);
  }
  
  /**
   * Test the retrieval of all position hints, using XML
   */
  @Test
  @TestResources(prefix = "sql/", 
      before = "RestInteractionFT_positionHints_before",
      after = "RestInteractionFT_positionHints_after")
  public void testGetPositionHintsXml() {
    PositionHintsModel hintsModel = getPositionHints("application/xml");
    assertNotNull(hintsModel);
    validateHints(hintsModel.getPositionHints());
  }
  
  /**
   * Test the retrieval of all position hints, using JSON
   */
  @Test
  @TestResources(prefix = "sql/", 
      before = "RestInteractionFT_positionHints_before",
      after = "RestInteractionFT_positionHints_after")
  public void testGetPositionHintsJson() {
    PositionHintsModel hintsModel = getPositionHints("application/json");
    assertNotNull(hintsModel);
    validateHints(hintsModel.getPositionHints());
  }
  
  /**
   * Test the retrieval of all rooms, using XML
   */
  @Test
  @TestResources(prefix = "sql/", before = "RestInteractionFT_rooms_before",
      after = "RestInteractionFT_rooms_after")
  public void testGetRoomsXml() {
    String buildingId = properties.getString("building.1.id");
    RoomsModel roomsModel = getRooms(buildingId, "application/xml");
    assertNotNull(roomsModel);
    validateRooms(roomsModel.getRooms());
  }
  
  /**
   * Test the retrieval of all rooms, using JSON
   */
  @Test
  @TestResources(prefix = "sql/", before = "RestInteractionFT_rooms_before",
      after = "RestInteractionFT_rooms_after")
  public void testGetRoomsJson() {
    String buildingId = properties.getString("building.1.id");
    RoomsModel roomsModel = getRooms(buildingId, "application/json");
    assertNotNull(roomsModel);
    validateRooms(roomsModel.getRooms());
  }
  
  private void validateHints(String[] hints) {
    assertEquals(2, hints.length);
    assertEquals(properties.getString("positionHint.1.hintText"), hints[0]);
    assertEquals(properties.getString("positionHint.2.hintText"), hints[1]);
  }
  
  private void validateRooms(String[] rooms) {
    assertEquals(2, rooms.length);
    assertEquals(properties.getString("room.1.roomNumber"), rooms[0]);
    assertEquals(properties.getString("room.2.roomNumber"), rooms[1]);
  }
  
  private BuildingsModel getBuildings(String mediaType) {
    WebResource resource = client.resource(getContextUrl() + REST_POINT 
        + BUILDING_URL);
    return resource.header("Accept", mediaType).get(BuildingsModel.class);
  }
  
  private PositionHintsModel getPositionHints(String mediaType) {
    WebResource resource = client.resource(getContextUrl() + REST_POINT 
        + POSITION_HINTS_URL);
    return resource.header("Accept", mediaType).get(PositionHintsModel.class);
  }
  
  private RoomsModel getRooms(String buildingId, String mediaType) {
    WebResource resource = client.resource(getContextUrl() + REST_POINT 
        + String.format(ROOMS_URL, buildingId));
    return resource.header("Accept", mediaType).get(RoomsModel.class);
  }
  
}
