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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ualerts.fixed.integration.ApplicationContextUtil;
import org.ualerts.fixed.repository.EntityNotFoundException;
import org.ualerts.fixed.service.api.model.DiscoveredAssetModel;
import org.ualerts.fixed.service.api.model.EnrolledFixtureModel;

import edu.vt.cns.kestrel.common.IntegrationTestRunner;

/**
 * Integration tests for {@link DiscoveredAssetService}.
 *
 * @author Brian Early
 */
@RunWith(IntegrationTestRunner.class)
public class DiscoveredAssetServiceIT {
  private static final Long ILLEGAL_ID = -1L;
  private static DiscoveredAssetService assetService;
  
  /**
   * Things to set up before the test instance of this
   * class has been created.
   * @throws Exception as needed.
   */
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    assetService = ApplicationContextUtil.getContext()
        .getBean(DiscoveredAssetService.class);
  }
  
  /**
   * Things to set up after the test instance of this class
   * has been destroyed.
   * @throws Exception as needed.
   */
  @AfterClass
  public static void tearDownAfterClass() throws Exception {
    ApplicationContextUtil.close();
  }
  
  /**
   * Verifies that a request for all new discovered assets works. 
   * @throws Exception as needed
   */
  @Test
  public void testGetByStatus() throws Exception {
    List<DiscoveredAssetModel> results = assetService.getByStatus("NEW");
    assertNotNull(results);
    assertTrue(results.size() > 0);
    for (DiscoveredAssetModel m : results) {
      assertEquals("NEW", m.getStatus());
    }
  }

  /**
   * Verifies that a request for a specific discovered asset works.
   * @throws Exception as needed
   */
  @Test
  public void testGetDiscoveredAsset() throws Exception {
    List<DiscoveredAssetModel> results = assetService.getByStatus("NEW");
    DiscoveredAssetModel original = results.get(0);
    DiscoveredAssetModel match =
        assetService.getDiscoveredAsset(original.getId());
    assertNotNull(match);
    assertEquals(original.getId(), match.getId());
  }
  
  /**
   * Verifies that a request for an ID that doesn't match any discovered
   * asset works and returns null.
   * @throws Exception as needed
   */
  @Test
  public void testGetDiscoveredAssetNotFound() throws Exception {
    assertNull(assetService.getDiscoveredAsset(ILLEGAL_ID));
  }
  
 /**
   * Verifies that a request for enrolled fixtures works.
   * @throws Exception as needed
   */
  @Test
  public void testGetEnrolledFixtures() throws Exception {
    Calendar cal = Calendar.getInstance();
    cal.add(Calendar.YEAR, -1);
    List<EnrolledFixtureModel> results =
        assetService.getEnrolledFixtures(cal.getTime());
    assertNotNull(results);
    assertTrue(results.size() > 0);
  }
  
  /**
   * Verifies that a request to start the enrollment process for a
   * discovered asset works.
   * @throws Exception as needed
   */
  @Test
  public void testStartEnrollment() throws Exception {
    List<DiscoveredAssetModel> results = assetService.getByStatus("NEW");
    DiscoveredAssetModel model = results.get(0);
    Date originalModifiedDate = model.getDateModified();
    Long id = model.getId();
    assetService.startEnrollment(id);
    model = assetService.getDiscoveredAsset(id);
    assertEquals("PENDING", model.getStatus());
    assertFalse(originalModifiedDate.equals(model.getDateModified()));
  }

  /**
   * Verifies that a request to start the enrollment process for a
   * discovered asset fails if the status of the asset is incorrect.
   * @throws Exception as needed
   */
  @Test(expected = IllegalStateException.class)
  public void testStartEnrollmentBadStatus() throws Exception {
    List<DiscoveredAssetModel> results = assetService.getByStatus("PENDING");
    DiscoveredAssetModel model = results.get(0);
    assetService.startEnrollment(model.getId());
  }

  /**
   * Verifies that a request to start the enrollment process for an
   * ID number that doesn't exist throws the right exception
   * @throws Exception as needed
   */
  @Test(expected = EntityNotFoundException.class)
  public void testStartEnrollmentBadId() throws Exception {
    assetService.startEnrollment(ILLEGAL_ID);
  }

  /**
   * Verifies that a request to cancel the enrollment process for a
   * discovered asset works.
   * @throws Exception as needed
   */
  @Test
  public void testCancelEnrollment() throws Exception {
    List<DiscoveredAssetModel> results = assetService.getByStatus("PENDING");
    DiscoveredAssetModel model = results.get(0);
    Date originalModifiedDate = model.getDateModified();
    Long id = model.getId();
    assetService.cancelEnrollment(id);
    model = assetService.getDiscoveredAsset(id);
    assertEquals("NEW", model.getStatus());
    assertFalse(originalModifiedDate.equals(model.getDateModified()));
  }
  
  /**
   * Verifies that a request to cancel the enrollment process for a
   * discovered asset fails if the status of the asset is incorrect.
   * @throws Exception as needed
   */
  @Test(expected = IllegalStateException.class)
  public void testCancelEnrollmentBadStatus() throws Exception {
    List<DiscoveredAssetModel> results = assetService.getByStatus("NEW");
    DiscoveredAssetModel model = results.get(0);
    assetService.cancelEnrollment(model.getId());
  }

  /**
   * Verifies that a request to cancel the enrollment process for an
   * ID number that doesn't exist throws the right exception
   * @throws Exception as needed
   */
  @Test(expected = EntityNotFoundException.class)
  public void testCancelEnrollmentBadId() throws Exception {
    assetService.cancelEnrollment(ILLEGAL_ID);
  }

  /**
   * Verifies that a request for enrolled fixtures works when the requested
   * cut-off date is after the dates for the fixtures.
   * @throws Exception as needed
   */
  @Test
  public void testGetEnrolledFixturesAllTooOld() throws Exception {
    List<EnrolledFixtureModel> results =
        assetService.getEnrolledFixtures(new Date());
    assertNotNull(results);
    assertTrue(results.size() == 0);
  }

}
