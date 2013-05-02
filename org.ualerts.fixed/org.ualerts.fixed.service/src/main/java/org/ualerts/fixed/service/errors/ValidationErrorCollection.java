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

package org.ualerts.fixed.service.errors;

import java.util.ArrayList;
import java.util.List;

/**
 * Error exception that contains a collection of {@link ValidationError}
 * objects.
 *
 * @author earlyb
 */
public class ValidationErrorCollection extends Exception {
  private static final long serialVersionUID = 221782850535788392L;
  private List<ValidationError> errors = new ArrayList<ValidationError>();

  /**
   * Gets the {@code errors} property.
   * @return property value
   */
  public List<ValidationError> getErrors() {
    return errors;
  }
  
  /**
   * Adds a new error to the collection.
   * @param error the error to add
   */
  public void addError(ValidationError error) {
    errors.add(error);
  }

  /**
   * Returns whether or not there are errors in the collection.
   * @return true if there are errors, otherwise false.
   */
  public boolean hasErrors() {
    return !errors.isEmpty();
  }
  
}
