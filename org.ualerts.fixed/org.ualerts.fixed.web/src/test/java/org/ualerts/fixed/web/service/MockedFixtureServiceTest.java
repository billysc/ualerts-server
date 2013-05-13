/*
 * File created on May 2, 2013
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

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.ualerts.fixed.InetAddress;
import org.ualerts.fixed.MacAddress;
import org.ualerts.fixed.service.commands.AddFixtureCommand;
import org.ualerts.fixed.web.dto.FixtureDTO;

/**
 * Test case for the {@link MockedFixtureService} class.
 *
 * @author Michael Irwin
 */
public class MockedFixtureServiceTest {

  private MockedFixtureService service;
  private AddFixtureCommand command;
  
  /**
   * Setup function
   */
  @Before
  public void setup() {
    service = new MockedFixtureService();
    command = new AddFixtureCommand();
    command.setBuildingName("Burruss Hall");
    command.setInventoryNumber("INV-123");
    command.setInetAddress(InetAddress.getByAddress("123.123.123.123"));
    command.setMacAddress(new MacAddress("0A:12:34:0B:56:78"));
    command.setPositionHint("TOP-RIGHT");
    command.setRoomNumber("110");
    command.setSerialNumber("1234567890ABC");
  }
  
  /**
   * Test that creation sets the id and version attributes of the Fixture
   */
  @Test
  public void testCreateFixture() throws Exception {
    FixtureDTO fixture = service.createFixture(command);
    assertNotNull(fixture.getId());
    assertNotNull(fixture.getVersion());
  }
  
  /**
   * Test the retrieval of all fixtures
   * @throws Exception
   */
  @Test
  public void testRetrieveAllFixtures() throws Exception {
    List<FixtureDTO> fixtures = service.retrieveAllFixtures();
    assertNotNull(fixtures);
    assertEquals(0, fixtures.size());
    
    service.createFixture(command);
    fixtures = service.retrieveAllFixtures();
    assertNotNull(fixtures);
    assertEquals(1, fixtures.size());
  }
}
