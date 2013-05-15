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

import java.util.ArrayList;
import java.util.List;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.Errors;
import org.ualerts.fixed.web.model.FixtureModel;
import org.ualerts.fixed.web.validator.fixture.FixtureValidationRule;

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
  private List<FixtureValidationRule> rules;
  
  
  /**
   * Setup for each test
   */
  @Before
  public void setup() {
    fixture = new FixtureModel();
    rules = new ArrayList<FixtureValidationRule>();

    context = new Mockery();
    errors = context.mock(Errors.class);
    validator = new FixtureValidator();
    validator.setValidationRules(rules);
  }
  
  /**
   * Validate the Validator supports the FixtureModel class
   */
  @Test
  public void testSupportsFixtureModelObject() {
    Assert.assertTrue(validator.supports(FixtureModel.class));
  }
  
  /**
   * Validate that the Validator passes validation to its rules
   */
  @Test
  public void testValidationExecution() {
    final FixtureValidationRule rule = 
        context.mock(FixtureValidationRule.class);
    rules.add(rule);
    
    context.checking(new Expectations() { { 
      oneOf(rule).validate(fixture, errors);
    } });
    validator.validate(fixture, errors);
    context.assertIsSatisfied();
  }
  
}
