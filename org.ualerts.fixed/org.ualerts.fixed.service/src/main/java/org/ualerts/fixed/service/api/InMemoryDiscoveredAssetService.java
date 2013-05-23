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

import org.springframework.stereotype.Service;
import org.ualerts.fixed.repository.EntityNotFoundException;
import org.ualerts.fixed.service.DateTimeService;
import org.ualerts.fixed.service.api.model.DiscoveredAssetModel;
import org.ualerts.fixed.service.api.model.EnrolledFixtureModel;

/**
 * In-memory implementation of {@link DiscoveredAssetService}.
 *
 * @author Brian Early
 * @author Michael Irwin
 */
@Service
public class InMemoryDiscoveredAssetService implements DiscoveredAssetService {
  private List<DiscoveredAssetModel> discoveredAssets =
      new ArrayList<DiscoveredAssetModel>();
  private List<EnrolledFixtureModel> enrolledFixtures =
      new ArrayList<EnrolledFixtureModel>();
  private DateTimeService dateTimeService;
  
  /**
   * {@inheritDoc}
   */
  @Override
  public void startEnrollment(Long id) throws EntityNotFoundException,
      IllegalStateException {
    updateStatus(id, "NEW", "PENDING");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void cancelEnrollment(Long id) throws EntityNotFoundException,
      IllegalStateException {
    updateStatus(id, "PENDING", "NEW");
  }

  private void updateStatus(Long id, String requiredStatus, String newStatus)
      throws EntityNotFoundException, IllegalStateException {
    DiscoveredAssetModel match = getDiscoveredAsset(id);
    if (match == null) {
      throw new EntityNotFoundException(DiscoveredAssetModel.class, id);
    }
    if (!match.getStatus().equals(requiredStatus)) {
      throw new
        IllegalStateException("Discovered Asset not in the "
            + requiredStatus + " state");
    }
    match.setStatus(newStatus);
    match.setDateModified(dateTimeService.getCurrentDate());
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public DiscoveredAssetModel getDiscoveredAsset(Long id) {
    DiscoveredAssetModel match = null;
    for (DiscoveredAssetModel m : discoveredAssets) {
      if (m.getId() == id) {
        match = m;
        break;
      }
    }
    return match;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<DiscoveredAssetModel> getByStatus(String status) {
    List<DiscoveredAssetModel> results = new ArrayList<DiscoveredAssetModel>();
    for (DiscoveredAssetModel m : discoveredAssets) {
      if (m.getStatus().equals(status)) {
        results.add(m);
      }
    }
    return results;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<EnrolledFixtureModel> getEnrolledFixtures(Date enrolledSince) {
    List<EnrolledFixtureModel> results = new ArrayList<EnrolledFixtureModel>();
    for (EnrolledFixtureModel m : enrolledFixtures) {
      if (m.getEnrolledOn().after(enrolledSince)) {
        results.add(m);
      }
    }
    return results;
  }

  /**
   * Sets the {@code discoveredAssets} property.
   * @param discoveredAssets the value to set
   */
  @Resource(name = "inMemoryDiscoveredAssets")
  public void setDiscoveredAssets(List<DiscoveredAssetModel> discoveredAssets) {
    this.discoveredAssets = discoveredAssets;
  }

  /**
   * Sets the {@code enrolledFixtures} property.
   * @param enrolledFixtures the value to set
   */
  @Resource(name = "inMemoryEnrolledFixtures")
  public void setEnrolledFixtures(List<EnrolledFixtureModel> enrolledFixtures) {
    this.enrolledFixtures = enrolledFixtures;
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
