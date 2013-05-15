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
import org.ualerts.fixed.web.model.FixtureModel;
import org.ualerts.fixed.web.validator.FixtureValidator;

/**
 * An interface for validation rules related to a Fixture
 *
 * @author Michael Irwin
 */
public interface FixtureValidationRule {
  
  /**
   * Perform validation related to this specific rule on the provided 
   * FixtureModel
   * @param fixture The fixture to be validated
   * @param errors The object to bind errors to
   */
  void validate(FixtureModel fixture, Errors errors);
  
}
