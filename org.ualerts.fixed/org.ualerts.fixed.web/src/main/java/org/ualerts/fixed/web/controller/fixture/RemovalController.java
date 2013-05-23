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
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.ualerts.fixed.service.api.FixtureService;
import org.ualerts.fixed.service.api.model.FixtureModel;

/**
 * A controller that handles fixture removal.
 *
 * @author ceharris
 */
@Controller
@RequestMapping("/fixtures")
public class RemovalController {

  /**
   * Name of the view returned by {@link #provideForm(Long, Model)}.
   */
  static final String FORM_VIEW_NAME = "fixtures/remove";
  
  private FixtureService fixtureService;
  
  /**
   * Provides the fixture removal form.
   * @param id ID of the fixture to remove
   * @param model view model
   * @return name of the view to display
   * @throws Exception
   */
  @RequestMapping(value = "/{id}/remove.html", method = RequestMethod.GET)
  public String provideForm(@PathVariable("id") Long id, Model model) 
      throws Exception {
    FixtureModel fixture = fixtureService.findFixtureById(id);    
    model.addAttribute("fixture", fixture);
    return FORM_VIEW_NAME;
  }

  /**
   * Removes the fixture identified by the given ID.
   * @param id ID of the fixture to remove
   * @return map containing a single {@code success} attribute
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping(value = "/{id}/remove.json", method = RequestMethod.POST,
      produces = "application/json")
  public Map<String, Object> handleFormSubmission(@PathVariable("id") Long id)
      throws Exception {
    FixtureModel fixture = fixtureService.removeFixture(id);
    Map<String, Object> result = new HashMap<String, Object>();
    result.put("fixture", fixture);
    result.put("success", true);
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
  
}
