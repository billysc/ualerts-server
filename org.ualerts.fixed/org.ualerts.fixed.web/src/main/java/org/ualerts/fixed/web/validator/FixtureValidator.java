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
   * A message property prefix to be used for fixture validation errors
   */
  public static final String MSG_PREFIX = "validation.fixture.";

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
    FixtureDTO fixture = (FixtureDTO) obj;

    rejectIfEmpty(fixture.getBuilding(), errors, "building", "building.empty");
    rejectIfEmpty(fixture.getRoom(), errors, "room", "room.empty");
    rejectIfEmpty(fixture.getPositionHint(), errors, "positionHint",
        "positionHint.empty");
    rejectIfEmpty(fixture.getSerialNumber(), errors, "serialNumber",
        "serialNumber.empty");
    rejectIfEmpty(fixture.getIpAddress(), errors, "ipAddress",
        "ipAddress.empty");
    rejectIfEmpty(fixture.getMacAddress(), errors, "macAddress",
        "macAddress.empty");

    if (StringUtils.isNotEmpty(fixture.getIpAddress())) {
      try {
        InetAddress address =
            InetAddress.getByAddress(fixture.getIpAddress());
        fixture.setIpAddressObj(address);
      }
      catch (Exception e) {
        errors.rejectValue("ipAddress", MSG_PREFIX + "ipAddress.notValid");
      }
    }

    if (StringUtils.isNotEmpty(fixture.getMacAddress())) {
      try {
        MacAddress address = new MacAddress(fixture.getMacAddress());
        fixture.setMacAddressObj(address);
      }
      catch (Exception e) {
        errors.rejectValue("macAddress", MSG_PREFIX + "macAddress.notValid");
      }
    }

  }

  /**
   * Helper class that rejects the provided value if it is null or empty
   * @param value The value to validate
   * @param errors The Errors object that the rejection will be applied to if 
   * needed
   * @param fieldName The fieldname to be applied if a rejectValue is needed
   * @param msgProp The message property to be applied, without the prefix
   */
  protected void rejectIfEmpty(String value, Errors errors, String fieldName,
      String msgProp) {

    if (value == null || value.isEmpty())
      errors.rejectValue(fieldName, MSG_PREFIX + msgProp);
  }

}
