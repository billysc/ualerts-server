/*
 * File created on May 3, 2013
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

import org.springframework.validation.Errors;
import org.ualerts.fixed.service.errors.ValidationErrorCollection;

/**
 * An error handler for handling the various errors that can be produced when
 * interacting with the service layer.
 * 
 * To prevent the service layer from knowing too much about the web layer, the
 * service layer simply throws a collection of ValidationError objects. This
 * error handler maps those various ValidationErrors to the field names in the
 * FixtureDTO.
 *
 * @author Michael Irwin
 */
public interface FixtureErrorHandler {

  /**
   * Given the provided collection of ValidationErrors, map them to the provided
   * Errors object. 
   * @param validationErrors The various ValidationErrors that occurred and need
   * to be mapped
   * @param errors A Spring Errors object to be used for mapping of errors
   * @param msgPrefix A message property prefix to be used with the properties
   * provided in the validation errors
   */
  void applyErrors(ValidationErrorCollection validationErrors,
      Errors errors, String msgPrefix);
  
}