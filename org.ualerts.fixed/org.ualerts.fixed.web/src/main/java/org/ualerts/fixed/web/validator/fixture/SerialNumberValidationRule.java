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

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.ualerts.fixed.repository.AssetRepository;
import org.ualerts.fixed.web.model.FixtureModel;

/**
 * A FixtureValidationRule that validates the serial number for a Fixture.
 *
 * @author Michael Irwin
 */
@Component
public class SerialNumberValidationRule extends AbstractFixtureValidationRule {

  private AssetRepository assetRepository;
  
  /**
   * {@inheritDoc}
   */
  @Override
  public void doValidate(FixtureModel fixture) {
    String serialNumber = fixture.getSerialNumber();
    if (StringUtils.isBlank(serialNumber)) {
      rejectValue("serialNumber", "serialNumber.empty");
    }
    else if (assetRepository.findAssetBySerialNumber(serialNumber) != null) {
      rejectValue("serialNumber", "serialNumber.conflict");
    }
  }
  
  /**
   * Sets the {@code assetRepository} property.
   * @param assetRepository the value to set
   */
  @Resource
  public void setAssetRepository(AssetRepository assetRepository) {
    this.assetRepository = assetRepository;
  }
  
}
