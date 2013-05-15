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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.ualerts.fixed.InetAddress;
import org.ualerts.fixed.InetAddressEditor;
import org.ualerts.fixed.MacAddress;
import org.ualerts.fixed.MacAddressEditor;
import org.ualerts.fixed.web.model.FixtureModel;
import org.ualerts.fixed.web.service.FixtureService;
import org.ualerts.fixed.web.validator.FixtureValidator;

/**
 * Controller to handle the manual enrollment of a fixture.
 *
 * @author Michael Irwin
 */
@Controller
public class ManuallyEnrollFixtureController {
  
  private static final Logger LOGGER = 
      LoggerFactory.getLogger(ManuallyEnrollFixtureController.class);

  private MessageSource messageSource;
  private FixtureService fixtureService;
  private Validator fixtureValidator;

  /**
   * Sets the validator to be used for the controller
   * @param binder The binder
   */
  @InitBinder
  protected void initBinder(WebDataBinder binder) {
    binder.addValidators(fixtureValidator);
  }

  /**
   * Display the fixture enrollment form.
   * @param model The model for the UI
   * @return The name of the view to display
   */
  @RequestMapping(value = "/enrollment", method = RequestMethod.GET)
  public String displayForm(Model model) {
    model.addAttribute("fixture", new FixtureModel());
    return "enrollment/manualForm";
  }

  /**
   * Handle the form submission, producing a JSON result.
   * @param request The incoming request
   * @param response The outgoing response
   * @param fixture The fixture model submitted in the request
   * @param bindingResult Results of binding failures/errors
   * @return A Map to be used for marshalling into JSON
   * @throws Exception Only internal exceptions that cannot be handled
   */
  @ResponseBody
  @RequestMapping(value = "/enrollment", method = RequestMethod.POST, 
      produces = { "application/json" })
  public Map<String, Object> handleFormSubmission(HttpServletRequest request,
      HttpServletResponse response, 
      @Valid @ModelAttribute("fixture") FixtureModel fixture,
      BindingResult bindingResult) throws Exception {

    Map<String, Object> responseData = new HashMap<String, Object>();
    if (bindingResult.hasErrors()) {
      responseData.put("success", false);
      responseData.put("errors", getMappedErrors(bindingResult));
    }
    else {
      fixture.setInstalledBy(""); //TODO Replace this with real data
      FixtureModel dto = fixtureService.createFixture(fixture);
      responseData.put("success", true);
      responseData.put("fixture", dto);
    }

    return responseData;
  }

  /**
   * Handles form submission, producing a HTML output.
   * @param fixture The fixture model, based on submitted data in request
   * @param result Any binding errors/failures
   * @return Name of the view to be rendered
   */
  @RequestMapping(value = "/enrollment", method = RequestMethod.POST, 
      produces = { "text/html" })
  public String handleFormSubmission(
      @ModelAttribute("fixture") @Valid FixtureModel fixture,
      BindingResult result) throws Exception {

    fixture.setInstalledBy("");
    if (!result.hasErrors()) {
      fixtureService.createFixture(fixture);
    }
    return "enrollment/manualForm";
  }

  private Map<String, Object> getMappedErrors(Errors errors) {
    if (!errors.hasErrors())
      return null;
    
    Map<String, Object> bindErrors = new HashMap<String, Object>();
    if (errors.hasFieldErrors())
      bindErrors.put("fields", getFieldErrors(errors));
    if (errors.hasGlobalErrors())
      bindErrors.put("global", getGlobalErrors(errors));
    
    return bindErrors;
  }
  
  private Map<String, List<String>> getFieldErrors(Errors errors) {
    if (!errors.hasFieldErrors())
      return null;
    
    Map<String, List<String>> fieldErrors = new HashMap<String, List<String>>();
    for (FieldError error : errors.getFieldErrors()) {
      if (!fieldErrors.containsKey(error.getField()))
        fieldErrors.put(error.getField(), new ArrayList<String>());
      fieldErrors.get(error.getField()).add(getMessage(error.getCode()));
    }
    return fieldErrors;
  }
  
  private List<String> getGlobalErrors(Errors errors) {
    if (!errors.hasGlobalErrors())
      return null;
    
    List<String> globalErrors = new ArrayList<String>();
    for (ObjectError error : errors.getGlobalErrors()) {
      globalErrors.add(getMessage(error.getCode()));
    }
    return globalErrors;
  }
  
  private String getMessage(String messageCode) {
    return messageSource.getMessage(messageCode, null, "", null);
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
   * Sets the {@code messageSource} property.
   * @param messageSource the value to set
   */
  @Resource
  public void setMessageSource(MessageSource messageSource) {
    this.messageSource = messageSource;
  }

  /**
   * Sets the {@code fixtureValidator} property.
   * @param fixtureValidator the value to set
   */
  @Resource(name = "fixtureValidator")
  public void setFixtureValidator(Validator fixtureValidator) {
    this.fixtureValidator = fixtureValidator;
  }
  
}
