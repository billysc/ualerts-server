/*
 * File created on May 13, 2013
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

package org.ualerts.testing.jpa;

import java.util.Map;

import javax.persistence.Cache;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnitUtil;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.metamodel.Metamodel;

import org.junit.rules.ExternalResource;

/**
 * A JUnit {@link ExternalResource} that represents a JPA persistence unit.
 *
 * @author ceharris
 */
public class EntityManagerFactoryResource extends ExternalResource 
    implements EntityManagerFactory {

  private final String propertiesLocation;
  
  private EntityManagerFactory delegate;
  
  /**
   * Constructs a new instance.
   * @param propertiesLocation a class path resource containing properties
   *    for the persistence unit
   */
  public EntityManagerFactoryResource(String propertiesLocation) {
    super();
    this.propertiesLocation = propertiesLocation;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void close() {
    delegate.close();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public EntityManager createEntityManager() {
    return delegate.createEntityManager();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @SuppressWarnings("rawtypes")
  public EntityManager createEntityManager(Map properties) {
    return delegate.createEntityManager(properties);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Cache getCache() {
    return delegate.getCache();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public CriteriaBuilder getCriteriaBuilder() {
    return delegate.getCriteriaBuilder();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Metamodel getMetamodel() {
    return delegate.getMetamodel();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public PersistenceUnitUtil getPersistenceUnitUtil() {
    return delegate.getPersistenceUnitUtil();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Map<String, Object> getProperties() {
    return delegate.getProperties();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isOpen() {
    return delegate.isOpen();
  }

  @Override
  protected void before() throws Throwable {
    super.before();
    delegate = EntityManagerFactoryUtil
        .createEntityManagerFactory(propertiesLocation);
  }

  @Override
  protected void after() {
    super.after();
    if (delegate != null) {
      delegate.close();
    }
  }

}
