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

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.ualerts.fixed.web.model.FixtureModel;
import org.ualerts.fixed.web.validator.fixture.FixtureValidationRule;

/**
 * A Validator to be used to validate FixtureDTO objects.
 * 
 * @author Michael Irwin
 */
@Component("fixtureValidator")
public class FixtureValidator implements Validator {

  /**
   * A message property prefix to be used for fixture validation errors
   */
  public static final String MSG_PREFIX = "validation.fixture.";

  private List<FixtureValidationRule> validationRules;

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean supports(Class<?> modelClass) {
    return FixtureModel.class.equals(modelClass);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void validate(Object obj, Errors errors) {
    FixtureModel fixture = (FixtureModel) obj;
    for (FixtureValidationRule rule : validationRules) {
      rule.validate(fixture, errors);
    }
  }

  /**
   * Sets the {@code validationRules} property.
   * @param validationRules the value to set
   */
  @Resource
  public void setValidationRules(List<FixtureValidationRule> validationRules) {
    this.validationRules = validationRules;
  }
  
}
