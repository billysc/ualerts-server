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
 * Test case for the SerialNumberValidationRule class
 *
 * @author Michael Irwin
 */
public class SerialNumberValidationRuleTest {

  private static final String MSG_PREFIX = FixtureValidator.MSG_PREFIX;
  
  private Mockery context;
  private AssetRepository assetRepository;
  private Errors errors;
  private FixtureModel fixture;
  private SerialNumberValidationRule rule;
  
  /**
   * Setup tasks
   */
  @Before
  public void setUp() {
    context = new Mockery();
    assetRepository = context.mock(AssetRepository.class);
    errors = context.mock(Errors.class);
    fixture = new FixtureModel();
    
    rule = new SerialNumberValidationRule();
    rule.setAssetRepository(assetRepository);
  }
  
  /**
   * Validate that nothing happens if an empty serial number is provided
   */
  @Test
  public void testRequiresSerialNumber() {
    context.checking(new Expectations() { { 
      oneOf(errors).rejectValue("serialNumber", 
          MSG_PREFIX + "serialNumber.empty");
    } });
    rule.validate(fixture, errors);
    context.assertIsSatisfied();
  }
  
  /**
   * Validate that a rejection occurs if a conflict occurs
   */
  @Test
  public void testConflictingSerialNumber() {
    final Asset asset = new Asset();
    final String serialNumber = "SER-12345";
    fixture.setSerialNumber(serialNumber);
    
    context.checking(new Expectations() { { 
      oneOf(assetRepository).findAssetBySerialNumber(serialNumber);
      will(returnValue(asset));
      oneOf(errors).rejectValue("serialNumber",
          MSG_PREFIX + "serialNumber.conflict");
    } });
    
    rule.validate(fixture, errors);
    context.assertIsSatisfied();
  }
  
  /**
   * Validate that no errors appear when no conflict occurs
   */
  @Test
  public void testGoodSerialNumber() {
    final String serialNumber = "SER-12345";
    fixture.setSerialNumber(serialNumber);
    context.checking(new Expectations() { { 
      oneOf(assetRepository).findAssetBySerialNumber(serialNumber);
      will(returnValue(null));
    } });
    rule.validate(fixture, errors);
    context.assertIsSatisfied();
  }
  
}
