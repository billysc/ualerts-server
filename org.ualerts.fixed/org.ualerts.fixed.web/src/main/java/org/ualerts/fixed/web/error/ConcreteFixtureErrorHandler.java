/*
 * File created on May 2, 2013
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

package org.ualerts.fixed.web.error;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.ualerts.fixed.service.errors.ValidationErrors;

/**
 * A utility class for mapping exceptions from the CommandService to UI-level
 * errors.
 * 
 * The CommandService, when executing, batches all of the validation exceptions
 * into a ValidationErrorCollection. This utility iterates through each
 * contained exception and maps it back as an error on the provided Spring
 * {@link Errors} object. This allows the CommandService to be ignorant of the
 * UI implementation.
 * 
 * @author Michael Irwin
 */
@Component
public class ConcreteFixtureErrorHandler implements FixtureErrorHandler {

  private Map<String, String> errorMapping;
  
  /**
   * {@inheritDoc}
   */
  public void applyErrors(ValidationErrors validationErrors,
      Errors errors, String msgPrefix) {

    for (String errorCode : validationErrors.getErrors()) {
      String field = errorMapping.get(errorCode);
      if (field == null) {
        errors.reject(errorCode);
      }
      else {
        errors.rejectValue(field, errorCode);
      }
    }
  }
  
  /**
   * Sets the {@code errorMapping} property.
   * @param errorMapping the value to set
   */
  @Resource(name = "errorFieldMapping")
  public void setErrorMapping(Map<String, String> errorMapping) {
    this.errorMapping = errorMapping;
  }

}
