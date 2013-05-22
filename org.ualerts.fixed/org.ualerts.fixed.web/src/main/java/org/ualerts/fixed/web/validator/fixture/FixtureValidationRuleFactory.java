/*
 * File created on May 22, 2013
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

package org.ualerts.fixed.web.validator.fixture;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.ualerts.fixed.web.validator.fixture.FixtureValidationRule.ActionType;

/**
 * A factory object that creates the various lists of FixtureValidationRules
 * based on the different uses of validation rules.
 *
 * @author Michael Irwin
 */
@Configuration
public class FixtureValidationRuleFactory {

  private List<FixtureValidationRule> validationRules;
  
  /**
   * Generates a list of FixtureValidationRules that are used for validation
   * when a new fixture is being created.
   * @return The list of rules that support ActionType.ADD
   */
  @Bean(name = "addFixtureValidationRules")
  public List<FixtureValidationRule> addFixtureValidationRules() {
    return getRules(ActionType.ADD);
  }
  
  /**
   * Generates a list of FixtureValidationRules that are used for validation
   * when a fixture is being edited/updated.
   * @return The list of rules that support ActionType.EDIT
   */
  @Bean(name = "editFixtureValidationRules")
  public List<FixtureValidationRule> editFixtureValidationRules() {
    return getRules(ActionType.EDIT);
  }
  
  private List<FixtureValidationRule> getRules(ActionType actionType) {
    List<FixtureValidationRule> rules = new ArrayList<FixtureValidationRule>();
    for (FixtureValidationRule rule : validationRules) {
      if (rule.supports(actionType)) {
        rules.add(rule);
      }
    }
    return rules;
  }
  
  /**
   * Sets the {@code validationRules} property.
   * @param validationRules the value to set
   */
  @Resource
  public void setValidationRules(List<FixtureValidationRule> validationRules) {
    this.validationRules = validationRules;
  }
  
}
