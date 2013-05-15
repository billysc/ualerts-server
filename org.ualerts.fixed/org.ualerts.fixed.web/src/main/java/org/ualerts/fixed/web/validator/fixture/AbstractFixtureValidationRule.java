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
 * An abstract class for a FixtureValidationRule that adds helper methods for
 * adding errors.
 * 
 * @author Michael Irwin
 */
public abstract class AbstractFixtureValidationRule
    implements FixtureValidationRule {

  private static final String MSG_PREFIX = FixtureValidator.MSG_PREFIX;
  
  private boolean hasAddedErrors;
  private Errors errors;
  
  /**
   * {@inheritDoc}
   */
  @Override
  public final void validate(FixtureModel fixture, Errors errors) {
    hasAddedErrors = false;
    this.errors = errors;
    doValidate(fixture);
  }
  
  /**
   * Perform validations to the provided fixture.
   * @param fixture The fixture to validate
   */
  protected abstract void doValidate(FixtureModel fixture);
  
  /**
   * Helper method to add a global error
   * @param code The message code, without any prefix
   */
  protected void reject(String code) {
    hasAddedErrors = true;
    errors.reject(MSG_PREFIX + code);
  }
  
  /**
   * Helper method to add an error to a field
   * @param field The name of the field to reject
   * @param code The message code, without the prefix
   */
  protected void rejectValue(String field, String code) {
    hasAddedErrors = true;
    errors.rejectValue(field, MSG_PREFIX + code);
  }
  
  /**
   * Set the Errors to be used. Used for testing.
   * @param errors The errors object that rejections should be bound to.
   */
  protected void setErrors(Errors errors) {
    this.errors = errors;
  }
  
  /**
   * Gets the {@code hasAddedErrors} property.
   * @return property value
   */
  public boolean hasAddedErrors() {
    return hasAddedErrors;
  }
  
}
