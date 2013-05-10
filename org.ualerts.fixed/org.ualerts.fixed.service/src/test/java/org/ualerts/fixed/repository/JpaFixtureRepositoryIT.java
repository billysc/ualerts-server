/*
 * File created on May 8, 2013
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

package org.ualerts.fixed.repository;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ualerts.fixed.Asset;
import org.ualerts.fixed.Fixture;
import org.ualerts.fixed.integration.ApplicationContextUtil;

import edu.vt.cns.kestrel.common.IntegrationTestRunner;

/**
 * Integration tests for {@link JpaFixtureRepository}.
 *
 * @author Brian Early
 */
@RunWith(IntegrationTestRunner.class)
public class JpaFixtureRepositoryIT {
  private static EntityManagerFactory entityManagerFactory;
  private EntityManager entityManager;
  private EntityTransaction tx;
  private JpaFixtureRepository repository;
  private JpaAssetRepository assetRepository;
  
  /**
   * Things to set up before the test instance of this
   * class has been created.
   * @throws Exception as needed.
   */
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    entityManagerFactory =
        ApplicationContextUtil.getContext().getBean(EntityManagerFactory.class);
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
   * Sets up necessary objects to perform tests.
   * @throws Exception as needed
   */
  @Before
  public void setupUp() throws Exception {
    entityManager = entityManagerFactory.createEntityManager();
    tx = entityManager.getTransaction();
    tx.begin();
    repository = new JpaFixtureRepository();
    repository.setEntityManager(entityManager);
    repository.setEntityManagerFactory(entityManagerFactory);
    assetRepository = new JpaAssetRepository();
    assetRepository.setEntityManager(entityManager);
    assetRepository.setEntityManagerFactory(entityManagerFactory);
  }
  
  /**
   * Tears down any outstanding objects before exiting.
   * @throws Exception as needed
   */
  @After
  public void tearDown() throws Exception {
    if (tx != null || tx.isActive()) {
      tx.rollback();
    }
    if (entityManager != null) {
      entityManager.close();
    }
  }

  /**
   * Verifies that a request for all Fixtures works. 
   * @throws Exception as needed
   */
  @Test
  public void testFindAllFixtures() throws Exception {
    Fixture fixture = createFixtureTwo();
    List<Fixture> results = repository.findAllFixtures();
    assertNotNull(results);
    Fixture match = null;
    for (Fixture f : results) {
      if (f.getId().equals(fixture.getId())) {
        match = f;
      }
    }
    assertNotNull(match);
  }

  /**
   * Verifies that a search for a single Fixture works.
   * @throws Exception as needed
   */
  @Test
  public void testFindFixtureById() throws Exception {
    Fixture fixture = createFixtureOne();
    assertNotNull(fixture.getId());
    assertNotNull(fixture.getVersion());
    Fixture result = repository.findFixtureById(fixture.getId());
    assertTrue(fixture.getId() == result.getId());
    assertTrue(fixture.getVersion() == result.getVersion());
    assertTrue(fixture.getInstalledBy().equals(result.getInstalledBy()));
    assertTrue(fixture.getIpAddress().equals(result.getIpAddress()));
  }
  
  /**
   * Verifies that a search for a fixture that isn't there will
   * result in an EntityNotFoundException. 
   * @throws Exception as needed
   */
  @Test(expected = EntityNotFoundException.class)
  public void testFindFixtureByIdNotFound() throws Exception {
    List<Fixture> results = repository.findAllFixtures();
    Long id = 0L;
    for (Fixture f : results) {
      if (f.getId() > id) {
        id = f.getId();
      }
    }
    id++;
    repository.findFixtureById(id);
  }

  /**
   * Verifies that deleting a fixture actually removes
   * it from persistence
   * @throws Exception as needed
   */
  @Test
  public void testDeleteFixture() throws Exception {
    Fixture fixture = createFixtureThree();
    assertNotNull(fixture.getId());
    assertNotNull(fixture.getVersion());
    repository.deleteFixture(fixture);
    try {
      repository.findFixtureById(fixture.getId());
      fail("Did not receive expected exception.");
    }
    catch (EntityNotFoundException ex) {
      assertTrue(true);
    }
  }
  
  private Fixture createFixtureOne() throws Exception {
    Fixture fixture = new Fixture();
    Date date = new Date();
    fixture.setDateCreated(date);
    fixture.setInstalledBy("earlyb");
    fixture.setIpAddress("127.0.0.1");
    Asset asset = new Asset();
    asset.setDateCreated(date);
    asset.setInventoryNumber("inventoryNumber1");
    asset.setMacAddress("0A-1B-2C-3D-4E-5E");
    asset.setSerialNumber("serialNumber1");
    assetRepository.addAsset(asset);
    fixture.setAsset(asset);
    repository.addFixture(fixture);
    return fixture;
  }

  private Fixture createFixtureTwo() throws Exception {
    Fixture fixture = new Fixture();
    Date date = new Date();
    fixture.setDateCreated(date);
    fixture.setInstalledBy("earlyb");
    fixture.setIpAddress("127.0.0.2");
    Asset asset = new Asset();
    asset.setDateCreated(date);
    asset.setInventoryNumber("inventoryNumber2");
    asset.setMacAddress("0A-1B-2C-3D-4E-5F");
    asset.setSerialNumber("serialNumber2");
    assetRepository.addAsset(asset);
    fixture.setAsset(asset);
    repository.addFixture(fixture);
    return fixture;
  }

  private Fixture createFixtureThree() throws Exception {
    Fixture fixture = new Fixture();
    Date date = new Date();
    fixture.setDateCreated(date);
    fixture.setInstalledBy("earlyb");
    fixture.setIpAddress("127.0.0.3");
    Asset asset = new Asset();
    asset.setDateCreated(date);
    asset.setInventoryNumber("inventoryNumber3");
    asset.setMacAddress("0A-1B-2C-3D-4E-5A");
    asset.setSerialNumber("serialNumber3");
    assetRepository.addAsset(asset);
    fixture.setAsset(asset);
    repository.addFixture(fixture);
    return fixture;
  }

}
