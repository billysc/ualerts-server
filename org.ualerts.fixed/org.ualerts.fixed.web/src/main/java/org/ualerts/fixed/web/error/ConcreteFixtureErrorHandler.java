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

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.ualerts.fixed.service.errors.InventoryNumberConflictException;
import org.ualerts.fixed.service.errors.LocationConflictException;
import org.ualerts.fixed.service.errors.MacAddressConflictException;
import org.ualerts.fixed.service.errors.SerialNumberConflictException;
import org.ualerts.fixed.service.errors.UnknownBuildingException;
import org.ualerts.fixed.service.errors.ValidationError;
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

  /**
   * {@inheritDoc}
   */
  public void applyErrors(ValidationErrors validationErrors,
      Errors errors, String msgPrefix) {

    for (ValidationError error : validationErrors.getErrors()) {
      try {
        throw error;
      }
      catch (InventoryNumberConflictException e) {
        errors.rejectValue("inventoryNumber",
            msgPrefix + e.getMessageProperty());
      }
      catch (LocationConflictException e) {
        errors.reject(msgPrefix + e.getMessageProperty());
      }
      catch (MacAddressConflictException e) {
        errors.rejectValue("macAddress", msgPrefix + e.getMessageProperty());
      }
      catch (SerialNumberConflictException e) {
        errors
            .rejectValue("serialNumber", msgPrefix + e.getMessageProperty());
      }
      catch (UnknownBuildingException e) {
        errors.rejectValue("building", msgPrefix + e.getMessageProperty());
      }
      catch (ValidationError e) {
        errors.reject(msgPrefix + e.getMessageProperty());
      }
    }
  }

}
