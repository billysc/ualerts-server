/*
 * File created on May 3, 2013
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

package org.ualerts.fixed.web.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;
import org.ualerts.fixed.web.dto.FixtureDTO;
import org.ualerts.fixed.web.service.FixtureService;

/**
 * Test case for the {@link IndexController} class.
 *
 * @author Michael Irwin
 */
public class IndexControllerTest {

  private Mockery context;
  private Map<String, Object> model;
  private IndexController controller;
  private FixtureService fixtureService;
  
  /**
   * Setup to be performed for each test
   */
  @Before
  public void setup() {
    context = new Mockery();
    model = new HashMap<String, Object>();
    fixtureService = context.mock(FixtureService.class);
    controller = new IndexController();
    controller.setFixtureService(fixtureService);
  }
  
  /**
   * Ensure that the index page is returned (and therefore will be rendered)
   * when the index page is requested.
   */
  @Test
  public void validateIndexPage() throws Exception {
    final List<FixtureDTO> fixtures = new ArrayList<FixtureDTO>();
    context.checking(new Expectations() { { 
      oneOf(fixtureService).retrieveAllFixtures();
      will(returnValue(fixtures));
    } });
    
    String view = controller.displayIndex(model);
    context.assertIsSatisfied();
    assertEquals("index", view);
    assertTrue(model.containsKey("fixtures"));
    assertEquals(fixtures, model.get("fixtures"));
  }
  
}
