/*
 * File created on Apr 30, 2013
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

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.ualerts.fixed.service.errors.ValidationErrorCollection;
import org.ualerts.fixed.web.dto.FixtureDTO;
import org.ualerts.fixed.web.error.FixtureErrorHandler;
import org.ualerts.fixed.web.service.FixtureService;
import org.ualerts.fixed.web.util.FakeHttpServletRequest;
import org.ualerts.fixed.web.util.FakeHttpServletResponse;
import org.ualerts.fixed.web.validator.FixtureValidator;

/**
 * Controller to handle the manual enrollment of a fixture.
 *
 * @author Michael Irwin
 */
@Controller
public class ManuallyEnrollFixtureController {

  private FixtureService fixtureService;
  private FixtureErrorHandler fixtureErrorHandler;

  /**
   * Sets the validator to be used for the controller
   * @param binder The binder
   */
  @InitBinder
  protected void initBinder(WebDataBinder binder) {
    binder.setValidator(new FixtureValidator());
  }

  /**
   * Display the fixture enrollment form.
   * @param model The model for the UI
   * @return The name of the view to display
   */
  @RequestMapping(value = "/enrollment", method = RequestMethod.GET)
  public String displayForm(Model model) {
    model.addAttribute("fixture", new FixtureDTO());
    return "enrollment/manualForm";
  }

  /**
   * Handle the form submission, producing a JSON result.
   * @param request The incoming request
   * @param response The outgoing response
   * @param fixture The command object that the user submitted
   * @param bindingResult Results of binding failures/errors
   * @return A Map to be used for marshalling into JSON
   * @throws Exception Only internal exceptions that cannot be handled
   */
  @ResponseBody
  @RequestMapping(value = "/enrollment", method = RequestMethod.POST, 
      produces = { "application/json" })
  public Map<String, Object> handleFormSubmission(HttpServletRequest request,
      HttpServletResponse response, @Valid FixtureDTO fixture,
      BindingResult bindingResult) throws Exception {

    Map<String, Object> responseData = new HashMap<String, Object>();

    // Passed syntactic validation from the FixtureValidator
    if (!bindingResult.hasErrors()) {
      try {
        fixtureService.createFixture(fixture);
      }
      catch (ValidationErrorCollection errorCollection) {
        fixtureErrorHandler.applyErrors(errorCollection, bindingResult,
            FixtureValidator.MSG_PREFIX);
      }
    }

    // Other errors could have been added from the service
    if (bindingResult.hasErrors()) {
      responseData.put("success", false);
      FakeHttpServletRequest wRequest =
          new FakeHttpServletRequest(request, "text/html");
      responseData.put("html",
          getHtmlOutput("enrollment", wRequest, response));
    }
    else {
      responseData.put("success", true);
      responseData.put("fixture", fixture);
    }

    return responseData;
  }

  /**
   * Handles form submission, producing a HTML output.
   * @param fixture The command object, based on the POST data
   * @param result Any binding errors/failures
   * @return Name of the JSP to be rendered
   */
  @RequestMapping(value = "/enrollment", method = RequestMethod.POST, 
      produces = { "text/html" })
  public String handleFormSubmission(
      @ModelAttribute("fixture") @Valid FixtureDTO fixture,
      BindingResult result) {

    return "enrollment/manualForm";
  }

  private String getHtmlOutput(String route, HttpServletRequest request,
      HttpServletResponse response) {

    FakeHttpServletResponse wResponse = new FakeHttpServletResponse(response);
    String data;
    try {
      request.getRequestDispatcher(route).include(request, wResponse);
      data = wResponse.getStringWriter().toString();
    }
    catch (Exception e) {
      data = "";
      e.printStackTrace(System.err);
    }

    return data;
  }

  /**
   * Sets the {@code fixtureService} property.
   * @param fixtureService the value to set
   */
  @Resource
  public void setFixtureService(FixtureService fixtureService) {
    this.fixtureService = fixtureService;
  }
  
  /**
   * Sets the {@code fixtureErrorHandler} property.
   * @param fixtureErrorHandler the value to set
   */
  @Resource
  public void setFixtureErrorHandler(FixtureErrorHandler fixtureErrorHandler) {
    this.fixtureErrorHandler = fixtureErrorHandler;
  }

}
