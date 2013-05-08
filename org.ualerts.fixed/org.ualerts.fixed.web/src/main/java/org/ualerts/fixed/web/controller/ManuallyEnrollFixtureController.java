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
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
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
import org.ualerts.fixed.service.commands.AddFixtureCommand;
import org.ualerts.fixed.web.dto.FixtureDTO;
import org.ualerts.fixed.web.service.FixtureService;

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

  /**
   * Sets the validator to be used for the controller
   * @param binder The binder
   */
  @InitBinder
  protected void initBinder(WebDataBinder binder) {
    binder.registerCustomEditor(InetAddress.class, new InetAddressEditor());
    binder.registerCustomEditor(MacAddress.class, new MacAddressEditor());
  }
  
  /**
   * Helper method to generate the fixture command to be used throughout the
   * controller
   * @return The command to be used for the form
   * @throws Exception Any exception due to command creation
   */
  @ModelAttribute("fixture")
  public AddFixtureCommand generateFixtureCommand() throws Exception {
    return fixtureService.newCommand(AddFixtureCommand.class);
  }

  /**
   * Display the fixture enrollment form.
   * @param command A command object
   * @param model The model for the UI
   * @return The name of the view to display
   */
  @RequestMapping(value = "/enrollment", method = RequestMethod.GET)
  public String displayForm(
      @ModelAttribute("fixture") AddFixtureCommand command, Model model) {
    model.addAttribute("fixture", command);
    return "enrollment/manualForm";
  }

  /**
   * Handle the form submission, producing a JSON result.
   * @param request The incoming request
   * @param response The outgoing response
   * @param command The command object that the user submitted
   * @param bindingResult Results of binding failures/errors
   * @return A Map to be used for marshalling into JSON
   * @throws Exception Only internal exceptions that cannot be handled
   */
  @ResponseBody
  @RequestMapping(value = "/enrollment", method = RequestMethod.POST, 
      produces = { "application/json" })
  public Map<String, Object> handleFormSubmission(HttpServletRequest request,
      HttpServletResponse response, 
      @Valid @ModelAttribute("fixture") AddFixtureCommand command,
      BindingResult bindingResult) throws Exception {

    Map<String, Object> responseData = new HashMap<String, Object>();
    command.setErrors(new BindException(bindingResult));

    try {
      FixtureDTO dto = fixtureService.createFixture(command);
      responseData.put("fixture", dto);
      responseData.put("success", true);
    }
    catch (BindException errorCollection) {
      responseData.put("success", false);
      responseData.put("errors", getMappedErrors(bindingResult));
    }

    return responseData;
  }

  /**
   * Handles form submission, producing a HTML output.
   * @param command The command object, based on the POST data
   * @param result Any binding errors/failures
   * @return Name of the view to be rendered
   */
  @RequestMapping(value = "/enrollment", method = RequestMethod.POST, 
      produces = { "text/html" })
  public String handleFormSubmission(
      @ModelAttribute("fixture") @Valid AddFixtureCommand command,
      BindingResult result) throws Exception {
    
    command.setErrors(new BindException(result));
    fixtureService.createFixture(command);
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

}
