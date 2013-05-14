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

import static org.ualerts.fixed.web.validator.FixtureValidator.MSG_PREFIX;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.Errors;
import org.ualerts.fixed.web.model.FixtureModel;

/**
 * Test case of the {@link FixtureValidator} class.
 *
 * @author Michael Irwin
 */
public class FixtureValidatorTest {

  private Mockery context;
  private Errors errors;
  private FixtureValidator validator;
  private FixtureModel fixture;
  
  /**
   * Setup for each test
   */
  @Before
  public void setup() {
    context = new Mockery();
    errors = context.mock(Errors.class);
    
    validator = new FixtureValidator();
    fixture = new FixtureModel();
  }
  
  /**
   * Validate the Validator supports the FixtureDTO class
   */
  @Test
  public void supportsFixtureDtoObject() {
    Assert.assertTrue(validator.supports(FixtureModel.class));
  }
  
  /**
   * Validate that if nothing is set on the fixture, errors are added for each
   * of the fields that require a value.  Also validates that the fields that
   * the IP and MAC address syntax validations do NOT occur, since there is no
   * value.
   */
  @Test
  public void validateEmptyChecking() {
    context.checking(new Expectations() { {
      oneOf(errors).rejectValue("building", MSG_PREFIX + "building.empty");
      oneOf(errors).rejectValue("room", MSG_PREFIX + "room.empty");
      oneOf(errors).rejectValue("positionHint", 
          MSG_PREFIX + "positionHint.empty");
      oneOf(errors).rejectValue("serialNumber", 
          MSG_PREFIX + "serialNumber.empty");
      oneOf(errors).rejectValue("ipAddress", MSG_PREFIX + "ipAddress.empty");
      oneOf(errors).rejectValue("macAddress", MSG_PREFIX + "macAddress.empty");
    } });
    validator.validate(fixture, errors);
  }
  
  /**
   * Validate that an invalid IP address is not allowed.
   */
  @Test
  public void shouldntAllowInvalidIpAddress() {
    setupFixture();
    
    context.checking(new Expectations() { {
      exactly(2).of(errors).rejectValue("ipAddress", 
          MSG_PREFIX + "ipAddress.notValid");
    } });
    
    fixture.setIpAddress("256.1.4.6");
    validator.validate(fixture, errors);
    
    fixture.setIpAddress("Some other value");
    validator.validate(fixture, errors);

    context.assertIsSatisfied();
  }
  
  /**
   * Validate that an invalid MAC address is not allowed
   */
  @Test
  public void shouldntAllowInvalidMacAddress() {
    setupFixture();
    context.checking(new Expectations() { {
      exactly(2).of(errors).rejectValue("macAddress", 
          MSG_PREFIX + "macAddress.notValid");
    } });

    fixture.setMacAddress("AZ:VA:DC:12:34");
    validator.validate(fixture, errors);

    fixture.setMacAddress("Clearly not a mac");
    validator.validate(fixture, errors);
    
    context.assertIsSatisfied();
  }
  
  /**
   * Validate that the rejectIfEmpty method works as expected
   */
  @Test
  public void verifyRejection() {
    final String key = "someKey";
    final String msgProp = "someProp";
    context.checking(new Expectations() { {
      exactly(2).of(errors).rejectValue(key, MSG_PREFIX + msgProp);
    } });
    validator.rejectIfEmpty(null, errors, key, msgProp);
    validator.rejectIfEmpty("", errors, key, msgProp);
    validator.rejectIfEmpty("should.go.through", errors, key, msgProp);
    context.assertIsSatisfied();
  }
  
  private void setupFixture() {
    fixture.setBuilding("A building");
    fixture.setInventoryNumber("INV-12345678");
    fixture.setIpAddress("192.168.1.1");
    fixture.setMacAddress("0A:12:34:0B:56:78");
    fixture.setPositionHint("TOP-RIGHT");
    fixture.setRoom("110");
    fixture.setSerialNumber("ABCD-123456789");
  }
  
}
