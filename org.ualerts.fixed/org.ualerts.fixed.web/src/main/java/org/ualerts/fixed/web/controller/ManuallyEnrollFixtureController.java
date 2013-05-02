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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.ualerts.fixed.web.dto.FixtureDTO;
import org.ualerts.fixed.web.service.FixtureService;
import org.ualerts.fixed.web.util.FakeHttpServletRequest;
import org.ualerts.fixed.web.util.FakeHttpServletResponse;

/**
 * Controller to handle the manual enrollment of a fixture.
 *
 * @author Michael Irwin
 */
@Controller
public class ManuallyEnrollFixtureController {
  
  private FixtureService fixtureService;
  
  @RequestMapping(value = "/enrollment", method = RequestMethod.GET)
  public String displayForm(Model model) {
    model.addAttribute("fixture", new FixtureDTO());
    return "enrollment/manualForm";
  }
  
  @ResponseBody
  @RequestMapping(value = "/enrollment", method = RequestMethod.POST, 
    produces = {"application/json", "application/xml"})
  public Map<String, Object> handleFormSubmission(HttpServletRequest request,
      HttpServletResponse response,
      @Valid FixtureDTO fixture,
      BindingResult bindingResult) {
    
    Map<String, Object> responseData = new HashMap<String, Object>();
    if (bindingResult.hasErrors()) {
      responseData.put("success", false);
      FakeHttpServletRequest wRequest = new FakeHttpServletRequest(request, "text/html");
      responseData.put("html", getHtmlOutput("enrollment", wRequest, response));
    } else {
      fixtureService.createFixture(fixture);
      responseData.put("success", true);
      responseData.put("fixture", fixture);
    }
    
    return responseData;
  }
  
  @RequestMapping(value = "/enrollment", method = RequestMethod.POST, 
    produces = {"text/html"})
  public String handleFormSubmission_html( 
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
    } catch (Exception e) {
      data = "";
      e.printStackTrace(System.err);
    } finally {
      //response.reset();
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
  
}
