/*
 * File created on May 14, 2013
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

package org.ualerts.fixed.web.validator.fixture;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.Errors;
import org.ualerts.fixed.Asset;
import org.ualerts.fixed.repository.AssetRepository;
import org.ualerts.fixed.web.model.FixtureModel;
import org.ualerts.fixed.web.validator.FixtureValidator;

/**
 * Test case for the IpAddressValidationRule class
 *
 * @author Michael Irwin
 */
public class IpAddressValidationRuleTest {

  private static final String MSG_PREFIX = FixtureValidator.MSG_PREFIX;
  
  private Mockery context;
  private Errors errors;
  private FixtureModel fixture;
  private IpAddressValidationRule rule;
  
  /**
   * Setup tasks
   */
  @Before
  public void setUp() {
    context = new Mockery();
    errors = context.mock(Errors.class);
    fixture = new FixtureModel();
    
    rule = new IpAddressValidationRule();
  }
  
  /**
   * Validate that an error occurs when no IP Address is provided
   */
  @Test
  public void testEmptyIpAddress() {
    context.checking(new Expectations() { { 
      oneOf(errors).rejectValue("ipAddress", MSG_PREFIX + "ipAddress.empty");
    } });
    rule.validate(fixture, errors);
    context.assertIsSatisfied();
  }
  
  /**
   * Validate that a rejection occurs if an invalid IP Address is provided
   */
  @Test
  public void testInvalidIpAddress() {
    context.checking(new Expectations() { { 
      exactly(2).of(errors).rejectValue("ipAddress", 
          MSG_PREFIX + "ipAddress.notValid");
    } });
    
    fixture.setIpAddress("NOT.VALID.IP.ADDRESS");
    rule.validate(fixture, errors);
    
    fixture.setIpAddress("256.256.0.0");
    rule.validate(fixture, errors);
    
    context.assertIsSatisfied();
  }
  
  /**
   * Validate that no errors appear when no conflict occurs
   */
  @Test
  public void testGoodIpAddress() {
    final String invNumber = "192.168.1.1";
    fixture.setIpAddress(invNumber);
    rule.validate(fixture, errors);
  }
  
}
