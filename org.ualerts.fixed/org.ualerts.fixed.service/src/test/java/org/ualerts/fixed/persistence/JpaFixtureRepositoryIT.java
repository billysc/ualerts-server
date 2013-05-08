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

package org.ualerts.fixed.persistence;

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
import org.springframework.util.Assert;
import org.ualerts.fixed.Fixture;
import org.ualerts.fixed.integration.ApplicationContextUtil;
import org.ualerts.fixed.repository.JpaFixtureRepository;

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
  public void testFindVlanByIdNotFound() throws Exception {
    List<Fixture> results = repository.findAllFixtures();
    Assert.notNull(results);
  }

}
