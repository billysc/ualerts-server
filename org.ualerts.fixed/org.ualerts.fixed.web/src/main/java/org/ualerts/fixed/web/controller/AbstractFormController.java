/*
 * File created on May 21, 2013
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.MessageSource;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

/**
 * An abstract form controller that provides helper methods for error handling.
 *
 * @author Michael Irwin
 */
public abstract class AbstractFormController {

  private MessageSource messageSource;
  
  /**
   * Translate the provided errors into a Map that contains global and field
   * level errors
   * @param errors The errors to be translated
   * @return A Map containing the following:
   *   - "global" => a List of error messages for global-level errors
   *   - "fields" => a Map of field names (String) to errors (List of Strings)
   */
  protected Map<String, Object> getMappedErrors(Errors errors) {
    if (!errors.hasErrors())
      return null;
    
    Map<String, Object> bindErrors = new HashMap<String, Object>();
    if (errors.hasFieldErrors())
      bindErrors.put("fields", getFieldErrors(errors));
    if (errors.hasGlobalErrors())
      bindErrors.put("global", getGlobalErrors(errors));
    
    return bindErrors;
  }
  
  private Map<String, List<String>> getFieldErrors(Errors errors) {
    if (!errors.hasFieldErrors())
      return null;
    
    Map<String, List<String>> fieldErrors = new HashMap<String, List<String>>();
    for (FieldError error : errors.getFieldErrors()) {
      if (!fieldErrors.containsKey(error.getField()))
        fieldErrors.put(error.getField(), new ArrayList<String>());
      fieldErrors.get(error.getField()).add(getMessage(error.getCode()));
    }
    return fieldErrors;
  }
  
  private List<String> getGlobalErrors(Errors errors) {
    if (!errors.hasGlobalErrors())
      return null;
    
    List<String> globalErrors = new ArrayList<String>();
    for (ObjectError error : errors.getGlobalErrors()) {
      globalErrors.add(getMessage(error.getCode()));
    }
    return globalErrors;
  }
  
  private String getMessage(String messageCode) {
    return messageSource.getMessage(messageCode, null, "", null);
  }
  
  /**
   * Sets the {@code messageSource} property.
   * @param messageSource the value to set
   */
  @Resource
  public void setMessageSource(MessageSource messageSource) {
    this.messageSource = messageSource;
  }
  
}
