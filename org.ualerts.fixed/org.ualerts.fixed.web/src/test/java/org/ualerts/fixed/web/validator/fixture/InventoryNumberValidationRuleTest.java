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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.Errors;
import org.ualerts.fixed.Asset;
import org.ualerts.fixed.repository.AssetRepository;
import org.ualerts.fixed.service.api.model.FixtureModel;
import org.ualerts.fixed.web.validator.FixtureValidator;
import org.ualerts.fixed.web.validator.fixture.FixtureValidationRule.ActionType;

/**
 * Test case for the InventoryNumberValidationRule class
 *
 * @author Michael Irwin
 */
public class InventoryNumberValidationRuleTest {

  private static final String MSG_PREFIX = FixtureValidator.MSG_PREFIX;
  
  private Mockery context;
  private AssetRepository assetRepository;
  private Errors errors;
  private FixtureModel fixture;
  private InventoryNumberValidationRule rule;
  
  /**
   * Setup tasks
   */
  @Before
  public void setUp() {
    context = new Mockery();
    assetRepository = context.mock(AssetRepository.class);
    errors = context.mock(Errors.class);
    fixture = new FixtureModel();
    
    rule = new InventoryNumberValidationRule();
    rule.setAssetRepository(assetRepository);
  }
  
  /**
   * Validate that the rule supports the expected ActionType value(s)
   */
  @Test
  public void testAcceptsExpectedActionTypes() {
    assertTrue(rule.supports(ActionType.ADD));
    assertFalse(rule.supports(ActionType.EDIT));
  }
  
  /**
   * Validate that nothing happens if an empty inventory number is provided
   */
  @Test
  public void testAllowsEmptyInventoryNumber() {
    rule.validate(fixture, errors);
  }
  
  /**
   * Validate that a rejection occurs if a conflict occurs
   */
  @Test
  public void testConflictingInventoryNumber() {
    final Asset asset = new Asset();
    final String invNumber = "INV-12345";
    fixture.setInventoryNumber(invNumber);
    
    context.checking(new Expectations() { { 
      oneOf(assetRepository).findAssetByInventoryNumber(invNumber);
      will(returnValue(asset));
      oneOf(errors).rejectValue("inventoryNumber", 
          MSG_PREFIX + "inventoryNumber.conflict");
    } });
    
    rule.validate(fixture, errors);
    context.assertIsSatisfied();
  }
  
  /**
   * Validate that no errors appear when no conflict occurs
   */
  @Test
  public void testGoodInventoryNumber() {
    final String invNumber = "INV-12345";
    fixture.setInventoryNumber(invNumber);
    context.checking(new Expectations() { { 
      oneOf(assetRepository).findAssetByInventoryNumber(invNumber);
      will(returnValue(null));
    } });
    rule.validate(fixture, errors);
    context.assertIsSatisfied();
  }
}
