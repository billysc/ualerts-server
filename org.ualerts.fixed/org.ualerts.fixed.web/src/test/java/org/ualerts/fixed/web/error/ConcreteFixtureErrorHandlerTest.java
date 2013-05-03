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

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.Errors;
import org.ualerts.fixed.service.errors.InventoryNumberConflictException;
import org.ualerts.fixed.service.errors.LocationConflictException;
import org.ualerts.fixed.service.errors.MacAddressConflictException;
import org.ualerts.fixed.service.errors.SerialNumberConflictException;
import org.ualerts.fixed.service.errors.UnknownBuildingException;
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
  
  private InventoryNumberConflictException invNumConflictException;
  private LocationConflictException locationConflictException;
  private MacAddressConflictException macAddConflictException;
  private SerialNumberConflictException serialNumConflictException;
  private UnknownBuildingException unknownBuildingException;
  
  /**
   * Setup needed for each test
   */
  @Before
  public void setup() {
    context = new Mockery();
    errors = context.mock(Errors.class);
    errorHandler = new ConcreteFixtureErrorHandler();
    
    invNumConflictException = new InventoryNumberConflictException();
    locationConflictException = new LocationConflictException();
    macAddConflictException = new MacAddressConflictException();
    serialNumConflictException = new SerialNumberConflictException();
    unknownBuildingException = new UnknownBuildingException();
    
    validationErrors = new ValidationErrors();
    validationErrors.addError(invNumConflictException);
    validationErrors.addError(locationConflictException);
    validationErrors.addError(macAddConflictException);
    validationErrors.addError(serialNumConflictException);
    validationErrors.addError(unknownBuildingException);
  }
  
  /**
   * Validate that the exception handling works as expected
   */
  @Test
  public void validateExceptionHandling() {
    final String msgPrefix = "PREFIX_";
    context.checking(new Expectations() { {
      oneOf(errors).rejectValue("inventoryNumber", 
          msgPrefix + invNumConflictException.getMessageProperty());
      oneOf(errors).reject(msgPrefix 
          + locationConflictException.getMessageProperty());
      oneOf(errors).rejectValue("macAddress", 
          msgPrefix + macAddConflictException.getMessageProperty());
      oneOf(errors).rejectValue("serialNumber", 
          msgPrefix + serialNumConflictException.getMessageProperty());
      oneOf(errors).rejectValue("building", 
          msgPrefix + unknownBuildingException.getMessageProperty());
    } });
    
    errorHandler.applyErrors(validationErrors, errors, msgPrefix);
    context.assertIsSatisfied();
  }
  
}
