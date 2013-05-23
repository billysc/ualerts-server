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
import org.ualerts.fixed.MacAddress;
import org.ualerts.fixed.repository.AssetRepository;
import org.ualerts.fixed.service.api.model.FixtureModel;

/**
 * A FixtureValidationRule that validates the MAC Address for a Fixture.
 * 
 * @author Michael Irwin
 */
@Component
public class MacAddressValidationRule
    extends AbstractFixtureValidationRule {

  private AssetRepository assetRepository;

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean supports(ActionType actionType) {
    return ActionType.ADD.equals(actionType);
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public void doValidate(FixtureModel fixture) {
    String macAddress = fixture.getMacAddress();
    if (StringUtils.isBlank(macAddress)) {
      rejectValue("macAddress", "macAddress.empty");
    }
    else if (!isValidMacAddress(macAddress)) {
      rejectValue("macAddress", "macAddress.notValid");
    }
    else if (macAddressExists(macAddress)) {
      rejectValue("macAddress", "macAddress.conflict");
    }
  }
  
  private boolean isValidMacAddress(String macAddress) {
    try {
      new MacAddress(macAddress);
      return true;
    } 
    catch (IllegalArgumentException e) {
      return false;
    }
  }
  
  private boolean macAddressExists(String macAddress) {
    return assetRepository.findAssetByMacAddress(macAddress) != null;
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
