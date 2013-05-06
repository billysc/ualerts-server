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

import java.util.HashMap;
import java.util.Map;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.Errors;
import org.ualerts.fixed.service.errors.ValidationErrors;

/**
 * Test case for the {@link ConcreteFixtureErrorHandler} class.
 *
 * @author Michael Irwin
 */
public class ConcreteFixtureErrorHandlerTest {

  private Mockery context;
  private Errors errors;
  private ConcreteFixtureErrorHandler errorHandler;
  private ValidationErrors validationErrors;
  
  private Map<String, String> errorFieldMapping;
  
  /**
   * Setup needed for each test
   */
  @Before
  public void setup() {
    context = new Mockery();
    errorFieldMapping = new HashMap<String, String>();
    errors = context.mock(Errors.class);
    errorHandler = new ConcreteFixtureErrorHandler();
    validationErrors = new ValidationErrors();
  }
  
  /**
   * Validate that the exception handling works as expected
   */
  @Test
  public void validateExceptionHandling() {
    final String code1 = "ERROR_1";
    final String code2 = "ERROR_2";
    final String field1 = "FIELD_1";
    
    errorFieldMapping.put(code1, field1);
    errorHandler.setErrorMapping(errorFieldMapping);
    validationErrors.addError(code1);
    validationErrors.addError(code2);
    
    final String msgPrefix = "PREFIX_";
    context.checking(new Expectations() { {
      oneOf(errors).reject(code2);
      oneOf(errors).rejectValue(field1, code1);
    } });
    
    errorHandler.applyErrors(validationErrors, errors, msgPrefix);
    context.assertIsSatisfied();
  }
  
}
