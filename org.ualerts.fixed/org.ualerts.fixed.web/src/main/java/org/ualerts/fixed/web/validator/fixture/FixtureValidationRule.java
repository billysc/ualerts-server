/*
 * File created on May 14, 2013
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

import org.springframework.validation.Errors;
import org.ualerts.fixed.service.api.model.FixtureModel;

/**
 * An interface for validation rules related to a Fixture
 *
 * @author Michael Irwin
 */
public interface FixtureValidationRule {
  
  /**
   * Describes the types of actions that rules can support
   */
  public enum ActionType {
    
    /**
     * Supports validation when a new fixture is being added to the system
     */
    ADD, 
    
    /**
     * Supports validation when a fixture is being edited/updated
     */
    EDIT
  }
  
  /**
   * Does this rule support the provided rule type?
   * @param actionType The ActionType to check
   * @return True if the rule supports the action type
   */
  boolean supports(ActionType actionType);
  
  /**
   * Perform validation related to this specific rule on the provided 
   * FixtureModel
   * @param fixture The fixture to be validated
   * @param errors The object to bind errors to
   */
  void validate(FixtureModel fixture, Errors errors);
  
}
