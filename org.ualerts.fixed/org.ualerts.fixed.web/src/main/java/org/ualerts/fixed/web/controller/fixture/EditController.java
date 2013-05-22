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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.ualerts.fixed.web.controller.AbstractFormController;
import org.ualerts.fixed.web.model.FixtureModel;
import org.ualerts.fixed.web.service.FixtureService;
import org.ualerts.fixed.web.validator.FixtureValidator;
import org.ualerts.fixed.web.validator.fixture.FixtureValidationRule;

/**
 * Controller that handles the editing of fixtures
 *
 * @author Michael Irwin
 */
@Controller
@RequestMapping("/fixtures")
public class EditController extends AbstractFormController {

  /**
   * Name of the view returned by {@link #provideForm(Long, Model)}.
   */
  static final String FORM_VIEW_NAME = "fixtures/edit";
  
  private FixtureService fixtureService;
  private List<FixtureValidationRule> fixtureValidationRules;
  
  /**
   * Sets the validator to be used for the controller
   * @param binder The binder
   */
  @InitBinder
  protected void initBinder(WebDataBinder binder) {
    binder.addValidators(new FixtureValidator(fixtureValidationRules));
  }
  
  /**
   * Displays the edit fixture view
   * @param id The id of the fixture to be edited
   * @param model The model to be used for the view
   * @return The name of the JSP to be rendered
   * @throws Exception
   */
  @RequestMapping(value = "/{id}/edit.html", method = RequestMethod.GET)
  public String provideForm(@PathVariable("id") Long id, Model model) 
      throws Exception {
    FixtureModel fixture = fixtureService.findFixtureById(id);    
    model.addAttribute("fixture", fixture);
    return FORM_VIEW_NAME;
  }

  /**
   * Updates the fixture based on input provided by the user
   * @param fixtureId ID of the fixture to remove
   * @param fixture The fixture model submitted by the user
   * @param bindingResult Any errors that occurred during binding/validation
   * @return map containing a single {@code success} attribute
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping(value = "/{id}/edit.json", method = RequestMethod.POST,
      produces = "application/json")
  public Map<String, Object> handleFormSubmission(
      @PathVariable("id") Long fixtureId,
      @Valid @ModelAttribute("fixture") FixtureModel fixture,
      BindingResult bindingResult) throws Exception {
    
    Map<String, Object> result = new HashMap<String, Object>();
    if (bindingResult.hasErrors()) {
      result.put("success", false);
      result.put("errors", getMappedErrors(bindingResult));
    }
    else {
      FixtureModel fixtureModel = fixtureService.updateFixture(fixture);
      result.put("fixture", fixtureModel);
      result.put("success", true);
      result.put("fixture", fixtureModel);
    }
    return result;
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
   * Sets the {@code fixtureValidationRules} property.
   * @param fixtureValidationRules the value to set
   */
  @Resource(name = "editFixtureValidationRules")
  public void setFixtureValidationRules(
      List<FixtureValidationRule> fixtureValidationRules) {
    this.fixtureValidationRules = fixtureValidationRules;
  }
  
}
