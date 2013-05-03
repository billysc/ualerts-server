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

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.ualerts.fixed.web.dto.FixtureDTO;

/**
 * Test case for the {@link MockedFixtureService} class.
 *
 * @author Michael Irwin
 */
public class MockedFixtureServiceTest {

  private MockedFixtureService service;
  private FixtureDTO fixture;
  
  /**
   * Setup function
   */
  @Before
  public void setup() {
    service = new MockedFixtureService();
    fixture = new FixtureDTO();
    fixture.setBuilding("Burruss Hall");
    fixture.setInventoryNumber("INV-123");
    fixture.setIpAddress("123.123.123.123");
    fixture.setMacAddress("0A:12:34:0B:56:78");
    fixture.setPositionHint("TOP-RIGHT");
    fixture.setRoom("110");
    fixture.setSerialNumber("1234567890ABC");
  }
  
  /**
   * Test that creation sets the id and version attributes of the Fixture
   */
  @Test
  public void testCreateFixture() {
    service.createFixture(fixture);
    Assert.assertNotNull(fixture.getId());
    Assert.assertNotNull(fixture.getVersion());
  }
  
}
