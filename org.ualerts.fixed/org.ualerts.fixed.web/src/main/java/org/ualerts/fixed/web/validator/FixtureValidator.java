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

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.ualerts.fixed.web.dto.FixtureDTO;

/**
 * A Validator to be used to validate FixtureDTO objects.
 *
 * @author Michael Irwin
 */
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
    rejectIfEmpty(errors, "building", "validation.fixture.building.empty");
    rejectIfEmpty(errors, "room", "validation.fixture.room.empty");
    rejectIfEmpty(errors, "positionHint", "validation.fixture.positionHint.empty");
    rejectIfEmpty(errors, "serialNumber", "validation.fixture.serialNumber.empty");
    rejectIfEmpty(errors, "ipAddress", "validation.fixture.ipAddress.empty");
    rejectIfEmpty(errors, "macAddress", "validation.fixture.macAddress.empty");
    rejectIfEmpty(errors, "room", "validation.fixture.room.empty");
    
    FixtureDTO fixture = (FixtureDTO)obj;
    // TODO Still need to validate the MAC and IP addresses
  }
    
}
