/*
 * File created on May 1, 2013 
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

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

/**
 * Abstract base class for stand-alone JPA Repositories.  Takes care
 * of auto-wiring the entity manager and factory.
 *
 * @author Brian Early
 */
public abstract class AbstractJpaRepository {
  private EntityManager entityManager;
  private EntityManagerFactory entityManagerFactory;

  /**
   * Gets the {@code entityManager} property.
   * @return the entity manager
   */
  public EntityManager getEntityManager() {
    return entityManager;
  }

  /**
   * Sets the {@code entityManager} property.
   * @param entityManager the entity manager
   */
  @PersistenceContext
  public void setEntityManager(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  /**
   * Gets the {@code entityManagerFactory} property.
   * @return the entity manager factory
   */
  public EntityManagerFactory getEntityManagerFactory() {
    return entityManagerFactory;
  }

  /**
   * Sets the {@code entityManagerFactory} property.
   * @param entityManagerFactory the entity manager factory
   */
  @PersistenceUnit
  public void setEntityManagerFactory(
      EntityManagerFactory entityManagerFactory) {
    this.entityManagerFactory = entityManagerFactory;
  }

}
