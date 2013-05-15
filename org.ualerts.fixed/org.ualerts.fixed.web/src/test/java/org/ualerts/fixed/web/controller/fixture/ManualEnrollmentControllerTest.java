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

package org.ualerts.fixed.web.controller.fixture;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import junit.framework.Assert;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.ualerts.fixed.web.model.FixtureModel;
import org.ualerts.fixed.web.service.FixtureService;

/**
 * Test case for the {@link ManualEnrollmentController} class.
 *
 * @author Michael Irwin
 */
public class ManualEnrollmentControllerTest {

  private Mockery context;
  private FixtureService fixtureService;
  private ManualEnrollmentController controller;
  
  /**
   * Setup to be performed before each test
   */
  @Before
  public void setup() {
    context = new Mockery();
    fixtureService = context.mock(FixtureService.class);

    controller = new ManualEnrollmentController();
    controller.setFixtureService(fixtureService);
  }
  
  /**
   * Validate the controller method used to handle GET requests for the form
   */
  @Test
  public void validateFormDisplayedUsingGet() {
    final Model model = context.mock(Model.class);
    context.checking(new Expectations() { { 
      oneOf(model).addAttribute(with("fixture"), with(any(FixtureModel.class)));
    } });
    String view = controller.displayForm(model);
    context.assertIsSatisfied();
    Assert.assertEquals("fixtures/enrollment", view);
  }
  
  /**
   * Validate the controller method used to handle POST requests when expected
   * to produce text/html.
   */
  @Test
  public void validatePostHandlerGeneratingHTML() throws Exception {
    final FixtureModel fixture = new FixtureModel();
    final BindingResult result = context.mock(BindingResult.class);
    
    context.checking(new Expectations() { { 
      oneOf(result).hasErrors();
      will(returnValue(false));
      oneOf(fixtureService).createFixture(fixture);
    } });
    
    String view = controller.handleFormSubmission(fixture, result);
    context.assertIsSatisfied();
    Assert.assertEquals("fixtures/enrollment", view);
  }
  
  /**
   * Validate the data returned during a completely clean submission
   */
  @Test
  public void validateGoodSubmission() throws Exception {
    final HttpServletRequest request = context.mock(HttpServletRequest.class);
    final HttpServletResponse resp = context.mock(HttpServletResponse.class);
    final FixtureModel fixture = new FixtureModel();
    final FixtureModel returningFixture = new FixtureModel();
    final BindingResult result = context.mock(BindingResult.class);
    
    fixture.setId(1L);
    returningFixture.setId(2L);
    
    context.checking(new Expectations() { { 
      oneOf(result).hasErrors();
      will(returnValue(false));
      oneOf(fixtureService).createFixture(fixture);
      will(returnValue(returningFixture));
    } });
    
    Map<String, Object> response = controller.handleFormSubmission(request, 
        resp, fixture, result);
    
    context.assertIsSatisfied();
    Assert.assertNotNull(response);
    Assert.assertTrue((Boolean) response.get("success"));
    Assert.assertNotNull(response.get("fixture"));
    Assert.assertEquals(FixtureModel.class, response.get("fixture").getClass());
    Assert.assertEquals(returningFixture, response.get("fixture"));
  }
  
  /**
   * Validate the controller method for handling POST data when there are
   * initial BindingResult errors.
   * @throws Exception
   */
  @Test
  public void validateErrorHandling() throws Exception  {
    final HttpServletRequest request = context.mock(HttpServletRequest.class);
    final HttpServletResponse resp = context.mock(HttpServletResponse.class);
    final FixtureModel fixture = new FixtureModel();
    final BindingResult result = context.mock(BindingResult.class);
    
    context.checking(new Expectations() { { 
      oneOf(result).hasErrors();
      will(returnValue(true));
      
      oneOf(result).hasErrors();        
      will(returnValue(true));
      exactly(2).of(result).hasFieldErrors();
      will(returnValue(true));
      oneOf(result).getFieldErrors();
      will(returnValue(new ArrayList<FieldError>()));
      exactly(2).of(result).hasGlobalErrors();
      will(returnValue(true));
      oneOf(result).getGlobalErrors();
      will(returnValue(new ArrayList<ObjectError>()));
      
    } });
    
    Map<String, Object> response = controller.handleFormSubmission(request, 
        resp, fixture, result);
    context.assertIsSatisfied();
    Assert.assertNotNull(response);
    Assert.assertFalse((Boolean) response.get("success"));
    Assert.assertNotNull(response.get("errors"));
  }
  

}
