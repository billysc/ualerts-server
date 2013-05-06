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

import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import junit.framework.Assert;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.ualerts.fixed.service.errors.ValidationErrors;
import org.ualerts.fixed.web.dto.FixtureDTO;
import org.ualerts.fixed.web.error.FixtureErrorHandler;
import org.ualerts.fixed.web.service.FixtureService;
import org.ualerts.fixed.web.util.FakeHttpServletRequest;
import org.ualerts.fixed.web.util.FakeHttpServletResponse;
import org.ualerts.fixed.web.validator.FixtureValidator;

/**
 * Test case for the {@link ManuallyEnrollFixtureController} class.
 *
 * @author Michael Irwin
 */
public class ManuallyEnrollFixtureControllerTest {

  private Mockery context;
  private FixtureErrorHandler errorHandler;
  private FixtureService fixtureService;
  private ManuallyEnrollFixtureController controller;
  
  /**
   * Setup to be performed before each test
   */
  @Before
  public void setup() {
    context = new Mockery();
    errorHandler = context.mock(FixtureErrorHandler.class);
    fixtureService = context.mock(FixtureService.class);

    controller = new ManuallyEnrollFixtureController();
    controller.setFixtureErrorHandler(errorHandler);
    controller.setFixtureService(fixtureService);
  }
  
  /**
   * Validate the controller method used to handle GET requests for the form
   */
  @Test
  public void validateFormDisplayedUsingGet() {
    final Model model = context.mock(Model.class);
    context.checking(new Expectations() { { 
      oneOf(model).addAttribute(with("fixture"), with(any(FixtureDTO.class)));
    } });
    String view = controller.displayForm(model);
    Assert.assertEquals("enrollment/manualForm", view);
  }
  
  /**
   * Validate the controller method used to handle POST requests when expected
   * to produce text/html.
   */
  @Test
  public void validatePostHandlerGeneratingHTML() {
    final FixtureDTO fixture = new FixtureDTO();
    final BindingResult result = context.mock(BindingResult.class);
    String view = controller.handleFormSubmission(fixture, result);
    Assert.assertEquals("enrollment/manualForm", view);
  }
  
  /**
   * Validate the data returned during a completely clean submission
   */
  @Test
  public void validateGoodSubmittion() throws Exception {
    final HttpServletRequest request = context.mock(HttpServletRequest.class);
    final HttpServletResponse resp = context.mock(HttpServletResponse.class);
    final FixtureDTO fixture = new FixtureDTO();
    final BindingResult result = context.mock(BindingResult.class);
    
    context.checking(new Expectations() { { 
      exactly(2).of(result).hasErrors();
      will(returnValue(false));
      oneOf(fixtureService).createFixture(fixture);
    } });
    
    Map<String, Object> response = controller.handleFormSubmission(request, 
        resp, fixture, result);
    
    context.assertIsSatisfied();
    Assert.assertNotNull(response);
    Assert.assertTrue((Boolean) response.get("success"));
    Assert.assertNotNull(response.get("fixture"));
    Assert.assertEquals(fixture, response.get("fixture"));
  }
  
  /**
   * Validate the controller method for handling POST data when there are
   * initial BindingResult errors.
   * @throws Exception
   */
  @Test
  public void validateErrorsOnSyntax() throws Exception {
    final HttpServletRequest request = context.mock(HttpServletRequest.class);
    final HttpServletResponse resp = context.mock(HttpServletResponse.class);
    final FixtureDTO fixture = new FixtureDTO();
    final BindingResult result = context.mock(BindingResult.class);
    final RequestDispatcher dispatcher = context.mock(RequestDispatcher.class);
    
    context.checking(new Expectations() { { 
      exactly(2).of(result).hasErrors();
      will(returnValue(true));
      oneOf(request).getRequestDispatcher("enrollment");
      will(returnValue(dispatcher));
      oneOf(dispatcher).include(with(any(FakeHttpServletRequest.class)), 
          with(any(FakeHttpServletResponse.class)));
    } });
    
    Map<String, Object> response = controller.handleFormSubmission(request, 
        resp, fixture, result);
    context.assertIsSatisfied();
    Assert.assertNotNull(response);
    Assert.assertFalse((Boolean) response.get("success"));
    Assert.assertNotNull(response.get("html"));
  }
  
  /**
   * Validate that the error handler is used when errors are discovered by the
   * FixtureService.
   */
  @Test
  public void validateServiceErrorHandling() throws Exception {
    final HttpServletRequest request = context.mock(HttpServletRequest.class);
    final HttpServletResponse resp = context.mock(HttpServletResponse.class);
    final FixtureDTO fixture = new FixtureDTO();
    final BindingResult result = context.mock(BindingResult.class);
    final RequestDispatcher dispatcher = context.mock(RequestDispatcher.class);
    final ValidationErrors validationErrors = new ValidationErrors();
    
    context.checking(new Expectations() { { 
      oneOf(result).hasErrors();
      will(returnValue(false));
      oneOf(fixtureService).createFixture(fixture);
      will(throwException(validationErrors));
      oneOf(errorHandler).applyErrors(validationErrors, result, 
          FixtureValidator.MSG_PREFIX);
      
      oneOf(result).hasErrors();
      will(returnValue(true));
      oneOf(request).getRequestDispatcher("enrollment");
      will(returnValue(dispatcher));
      oneOf(dispatcher).include(with(any(FakeHttpServletRequest.class)), 
          with(any(FakeHttpServletResponse.class)));
    } });
    

    Map<String, Object> response = controller.handleFormSubmission(request, 
        resp, fixture, result);
    context.assertIsSatisfied();
    
    Assert.assertNotNull(response);
    Assert.assertFalse((Boolean) response.get("success"));
    Assert.assertNotNull(response.get("html"));
  }

}
