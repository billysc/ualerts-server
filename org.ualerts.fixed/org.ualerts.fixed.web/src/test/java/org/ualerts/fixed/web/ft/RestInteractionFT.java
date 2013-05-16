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
import junit.framework.ComparisonFailure;

import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ualerts.fixed.web.model.BuildingsModel;
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
  
  private BuildingsModel getBuildings(String mediaType) {
    WebResource resource = client.resource(getContextUrl() + REST_POINT 
        + BUILDING_URL);
    return resource.header("Accept", mediaType).get(BuildingsModel.class);
  }
  
}
