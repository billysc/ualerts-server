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

package org.ualerts.fixed.web.validator;

import static org.springframework.validation.ValidationUtils.rejectIfEmpty;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.ualerts.fixed.InetAddress;
import org.ualerts.fixed.MacAddress;
import org.ualerts.fixed.web.dto.FixtureDTO;

/**
 * A Validator to be used to validate FixtureDTO objects.
 *
 * @author Michael Irwin
 */
@Component
public class FixtureValidator implements Validator {

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean supports(Class<?> clazz) {
    return FixtureDTO.class.equals(clazz);
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public void validate(Object obj, Errors errors) {
    String errorPrefix = "validation.fixture.";
    
    rejectIfEmpty(errors, "building", errorPrefix + "building.empty");
    rejectIfEmpty(errors, "room", errorPrefix + "room.empty");
    rejectIfEmpty(errors, "positionHint", errorPrefix + "positionHint.empty");
    rejectIfEmpty(errors, "serialNumber", errorPrefix + "serialNumber.empty");
    rejectIfEmpty(errors, "ipAddress", errorPrefix + "ipAddress.empty");
    rejectIfEmpty(errors, "macAddress", errorPrefix + "macAddress.empty");
    
    FixtureDTO fixture = (FixtureDTO)obj;
    if (StringUtils.isNotEmpty(fixture.getIpAddress())) {
      try {
        InetAddress address = InetAddress.getByAddress(fixture.getIpAddress());
        fixture.setIpAddressObj(address);
      } catch (Exception e) {
        errors.rejectValue("ipAddress", errorPrefix + "ipAddress.notValid");
      }
    }
    
    if (StringUtils.isNotEmpty(fixture.getMacAddress())) {
      try {
        MacAddress address = new MacAddress(fixture.getMacAddress());
        fixture.setMacAddressObj(address);
      } catch (Exception e) {
        errors.rejectValue("macAddress", errorPrefix + "macAddress.notValid");
      }
    }
    
  }
    
}
