/*
 * File created on May 20, 2013
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

package org.ualerts.fixed.web.controller.fixture;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.util.Map;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.Model;
import org.ualerts.fixed.web.model.FixtureModel;
import org.ualerts.fixed.web.service.FixtureService;

/**
 * Unit tests for {@link RemovalController}.
 *
 * @author Carl Harris
 */
public class RemovalControllerTest {

  private Mockery context;
  private FixtureService fixtureService;
  private Model model;
  private RemovalController controller;
  
  /**
   * Performs per-test set up.
   * @throws Exception
   */
  @Before
  public void setUp() throws Exception {
    context = new Mockery();
    fixtureService = context.mock(FixtureService.class);
    model = context.mock(Model.class);
    controller = new RemovalController();
    controller.setFixtureService(fixtureService);
  }

  /**
   * Test for {@link RemovalController#provideForm(Long, 
   *    org.springframework.ui.Model)}.
   * @throws Exception
   */
  @Test
  public void testProvideForm() throws Exception {
    final Long id = -1L;
    final FixtureModel fixture = new FixtureModel();
    context.checking(new Expectations() { { 
      oneOf(fixtureService).findFixtureById(with(same(id)));
      will(returnValue(fixture));
      oneOf(model).addAttribute(with("fixture"), with(same(fixture)));
    } });
    
    String viewName = controller.provideForm(id, model);
    context.assertIsSatisfied();
    assertEquals(RemovalController.FORM_VIEW_NAME, viewName);
  }
  
  /**
   * Test for {@link RemovalController#handleFormSubmission(Long)}.
   * @throws Exception
   */
  @Test
  public void testHandleFormSubmission() throws Exception {
    final Long id = -1L;
    final FixtureModel fixture = new FixtureModel();
    fixture.setId(id);
    context.checking(new Expectations() { { 
      oneOf(fixtureService).removeFixture(with(same(id)));
      will(returnValue(fixture));
    } });
    
    Map<String, Object> result = controller.handleFormSubmission(id);
    context.assertIsSatisfied();
    assertEquals(Boolean.TRUE, result.get("success"));
    assertSame(fixture, result.get("fixture"));
  }
  
}
  
