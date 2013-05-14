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
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.ualerts.fixed.service.commands.AddFixtureCommand;
import org.ualerts.fixed.web.model.FixtureModel;
import org.ualerts.fixed.web.service.FixtureService;

/**
 * Test case for the {@link ManuallyEnrollFixtureController} class.
 *
 * @author Michael Irwin
 */
public class ManuallyEnrollFixtureControllerTest {

  private Mockery context;
  private FixtureService fixtureService;
  private ManuallyEnrollFixtureController controller;
  
  /**
   * Setup to be performed before each test
   */
  @Before
  public void setup() {
    context = new Mockery();
    fixtureService = context.mock(FixtureService.class);

    controller = new ManuallyEnrollFixtureController();
    controller.setFixtureService(fixtureService);
  }
  
  /**
   * Validate the controller method used to handle GET requests for the form
   */
  @Test
  public void validateFormDisplayedUsingGet() {
    final Model model = context.mock(Model.class);
    final AddFixtureCommand command = new AddFixtureCommand();
    context.checking(new Expectations() { { 
      oneOf(model).addAttribute(with("fixture"), with(command));
    } });
    String view = controller.displayForm(command, model);
    Assert.assertEquals("enrollment/manualForm", view);
  }
  
  /**
   * Validate the controller method used to handle POST requests when expected
   * to produce text/html.
   */
  @Test
  public void validatePostHandlerGeneratingHTML() throws Exception {
    final AddFixtureCommand command = new AddFixtureCommand();
    final BindingResult result = context.mock(BindingResult.class);
    
    context.checking(new Expectations() { { 
      oneOf(fixtureService).createFixture(command);
    } });
    
    String view = controller.handleFormSubmission(command, result);
    context.assertIsSatisfied();
    Assert.assertEquals("enrollment/manualForm", view);
  }
  
  /**
   * Validate the data returned during a completely clean submission
   */
  @Test
  public void validateGoodSubmission() throws Exception {
    final HttpServletRequest request = context.mock(HttpServletRequest.class);
    final HttpServletResponse resp = context.mock(HttpServletResponse.class);
    final AddFixtureCommand command = new AddFixtureCommand();
    final BindingResult result = context.mock(BindingResult.class);
    final FixtureModel fixture = new FixtureModel();
    
    fixture.setId(1L);
    
    context.checking(new Expectations() { { 
      oneOf(fixtureService).createFixture(command);
      will(returnValue(fixture));
    } });
    
    Map<String, Object> response = controller.handleFormSubmission(request, 
        resp, command, result);
    
    context.assertIsSatisfied();
    Assert.assertNotNull(response);
    Assert.assertTrue((Boolean) response.get("success"));
    Assert.assertNotNull(response.get("fixture"));
    Assert.assertEquals(FixtureModel.class, response.get("fixture").getClass());
    Assert.assertEquals(fixture, response.get("fixture"));
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
    final AddFixtureCommand command = new AddFixtureCommand();
    final BindingResult result = context.mock(BindingResult.class);
    final BindException exception = new BindException("", "exception");
    
    context.checking(new Expectations() { { 
      oneOf(fixtureService).createFixture(command);
      will(throwException(exception));
      
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
        resp, command, result);
    context.assertIsSatisfied();
    Assert.assertNotNull(response);
    Assert.assertFalse((Boolean) response.get("success"));
    Assert.assertNotNull(response.get("errors"));
  }
  

}
