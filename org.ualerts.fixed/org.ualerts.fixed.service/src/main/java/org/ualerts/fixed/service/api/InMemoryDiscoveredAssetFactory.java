/*
 * File created on May 23, 2013
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

package org.ualerts.fixed.service.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.ualerts.fixed.service.DateTimeService;
import org.ualerts.fixed.service.api.model.DiscoveredAssetModel;
import org.ualerts.fixed.service.api.model.EnrolledFixtureModel;

/**
 * Configuration factory to create discovered assets for use by the
 * {@link InMemoryDiscoveredAssetService}.
 *
 * @author Brian Early
 * @author Michael Irwin
 */
@Configuration
public class InMemoryDiscoveredAssetFactory {
  private List<DiscoveredAssetModel> discoveredAssets =
      new ArrayList<DiscoveredAssetModel>();
  private List<EnrolledFixtureModel> enrolledFixtures =
      new ArrayList<EnrolledFixtureModel>();
  private DateTimeService dateTimeService;

  /**
   * Retrieves a list of pre-defined discovered assets.
   * @return the discovered assets.
   */
  @Bean(name = "inMemoryDiscoveredAssets")
  public List<DiscoveredAssetModel> getDiscoveredAssets() {
    if (discoveredAssets.size() == 0) {
      populateDiscoveredAssets();
    }
    return discoveredAssets;
  }

  private void populateDiscoveredAssets() {
    for (Long i = 1L; i < 6L; i++) { // CHECKSTYLE:FU
      DiscoveredAssetModel asset = new DiscoveredAssetModel();
      Date date = dateTimeService.getCurrentDate();
      asset.setDateCreated(date);
      asset.setDateModified(date);
      asset.setId(i);
      asset.setInventoryNumber("INV-TEST-" + i);
      asset.setIpAddress("0.0.0." + i);
      asset.setMacAddress("00-00-00-00-00-0" + i);
      asset.setSerialNumber("SERIAL_TEST_" + i);
      switch (i.intValue()) {
        case 1:
          asset.setStatus("NEW");
          break;
        case 2:
          asset.setStatus("PENDING");
          break;
        case 3: // CHECKSTYLE:FU
          asset.setStatus("ENROLLED");
          break;
        case 4: // CHECKSTYLE:FU
          asset.setStatus("IGNORE");
          break;
        case 5: // CHECKSTYLE:FU
          asset.setStatus("NEW");
          break;
        default:
          asset.setStatus("NEW");
          break;
      }
      discoveredAssets.add(asset);
    }
  }

  /**
   * Retrieves a pre-defined set of enrolled fixtures.
   * @return the list of enrolled fixtures.
   */
  @Bean(name = "inMemoryEnrolledFixtures")
  public List<EnrolledFixtureModel> getEnrolledFixtures() {
    if (enrolledFixtures.size() == 0) {
      populateEnrolledFixtures();
    }
    return enrolledFixtures;
  }
  
  private void populateEnrolledFixtures() {
    for (Long i = 1L; i < 6L; i++) { // CHECKSTYLE:FU
      EnrolledFixtureModel model = new EnrolledFixtureModel();
      Date date = dateTimeService.getCurrentDate();
      model.setBuildingAbbreviation("BLDG" + i);
      model.setEnrolledOn(date);
      model.setFixtureId(i);
      model.setInstalledBy("Person Name" + i);
      model.setIpAddress("0.0.0." + i);
      model.setMacAddress("00-00-00-00-00-0" + i);
      model.setRoom("10" + i);
      enrolledFixtures.add(model);
    }
  }

  /**
   * Sets the {@code dateTimeService} property.
   * @param dateTimeService the value to set
   */
  @Resource
  public void setDateTimeService(DateTimeService dateTimeService) {
    this.dateTimeService = dateTimeService;
  }
  
}
