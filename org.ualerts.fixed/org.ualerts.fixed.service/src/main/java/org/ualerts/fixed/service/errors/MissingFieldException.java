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

/**
 * Validation exception for a missing field.
 *
 * @author Brian Early
 */
public class MissingFieldException extends ValidationError {
  private static final long serialVersionUID = 4305220797715331722L;
  private String fieldName;
  
  /**
   * Constructs a new instance.
   * @param fieldName the name of the missing field.
   */
  public MissingFieldException(String fieldName) {
    super("field.missing");
    this.fieldName = fieldName;
  }

  /**
   * Gets the {@code fieldName} property.
   * @return property value
   */
  public String getFieldName() {
    return fieldName;
  }

}
