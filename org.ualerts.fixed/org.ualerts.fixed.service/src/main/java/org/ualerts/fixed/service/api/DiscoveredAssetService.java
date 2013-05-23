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

import java.util.Date;
import java.util.List;

import org.ualerts.fixed.repository.EntityNotFoundException;
import org.ualerts.fixed.service.api.model.DiscoveredAssetModel;
import org.ualerts.fixed.service.api.model.EnrolledFixtureModel;

/**
 * Service API for interacting with DiscoveredAssets in the system.
 *
 * @author Brian Early
 * @author Michael Irwin
 */
public interface DiscoveredAssetService {

  /**
   * Start the enrollment for a Discovered Asset in the NEW state.
   * 
   * The Discovered Asset's state will be updated to PENDING.
   * 
   * @param id Id of the Discovered Asset.
   * @throws EntityNotFoundException Thrown if id provided does not match a 
   * known Discovered Asset
   * @throws IllegalStateException Thrown if provided asset is not in NEW state
   */
  void startEnrollment(Long id) throws EntityNotFoundException, 
      IllegalStateException;
  
  /**
   * Cancel the enrollment for a Discovered Asset in the PENDING state.
   * 
   * The Discovered Asset's state will be updated to NEW.
   * 
   * @param id Id of the Discovered Asset.
   * @throws EntityNotFoundException Thrown if id provided does not match a 
   * known Discovered Asset
   * @throws IllegalStateException Thrown if provided asset is not in PENDING 
   * state
   */
  void cancelEnrollment(Long id) throws EntityNotFoundException, 
      IllegalStateException;
  
  /**
   * Retrieve a Discovered Asset based on id.
   * @param id The id of the Discovered Asset to retrieve.
   * @return The retrieved Discovered Asset, if found. Otherwise, null.
   */
  DiscoveredAssetModel getDiscoveredAsset(Long id);
  
  /**
   * Get all of the Discovered Assets that are in the specified status.
   * @param status the status of the Discovered Assets to retrieve.
   * @return All Discovered Assets in specified status.
   */
  List<DiscoveredAssetModel> getByStatus(String status);
  
  /**
   * Get all fixtures that have been enrolled since the provided date.
   * @param enrolledSince Earliest accepted enrollment date
   * @return All enrolled fixtures enrolled since provided date
   */
  List<EnrolledFixtureModel> getEnrolledFixtures(Date enrolledSince);
  
}
