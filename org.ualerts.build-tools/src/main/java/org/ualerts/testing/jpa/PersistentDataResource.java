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

import java.net.URL;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.junit.rules.ExternalResource;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * A test resource that sets up and tears down persistent data using an
 * {@link EntityManagerFactoryResource}.
 *
 * @author Carl Harris
 */
public abstract class PersistentDataResource extends ExternalResource {

  private static final String BEFORE_PHASE_NAME = "before";
  private static final String AFTER_PHASE_NAME = "after";

  private final EntityManagerFactoryResource entityManagerFactory;
  
  private URL before;
  private URL after;
  
  /**
   * Constructs a new instance.
   * @param entityManagerFactory entity manager factory delegate
   */
  public PersistentDataResource(
      EntityManagerFactoryResource entityManagerFactory) {
    this.entityManagerFactory = entityManagerFactory;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Statement apply(Statement base, Description description) {
    TestResources annotation = description.getAnnotation(TestResources.class);
    if (annotation != null) {
      before = urlForResource(annotation.before(), BEFORE_PHASE_NAME,
          annotation, description);
      after = urlForResource(annotation.after(), AFTER_PHASE_NAME,
          annotation, description);
    }
    return super.apply(base, description);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void before() throws Throwable {
    super.before();
    if (before != null) {
      executeSQL(before);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void after() {
    try {
      if (after != null) {
        executeSQL(after);
      }
    }
    catch (Throwable t) {
      t.printStackTrace(System.err);
    }
    super.after();
  }

  private URL urlForResource(String resourceName, String phaseName,
      TestResources annotation, Description description) {
    if (resourceName.length() == 0) {
      resourceName = defaultName(phaseName, description);
    }
    return urlForResource(resourceName, annotation, 
        description.getTestClass());
  }
  
  private URL urlForResource(String resourceName, TestResources annotation,
      Class<?> testClass) {
    URL url = null;
    if (annotation.prefix().length() == 0) {
      url = testClass.getResource(resourceName + annotation.suffix());
    }
    else {
      url = testClass.getClassLoader().getResource(
          annotation.prefix() + resourceName + annotation.suffix());    
    }
    if (url == null) {
      System.err.format("%s%s%s: resource not found\n",
          annotation.prefix(), resourceName, annotation.suffix());
    }
    return url;    
  }
  
  private String defaultName(String distinguisher, Description description) {
    StringBuilder sb = new StringBuilder();
    sb.append(description.getTestClass().getSimpleName());
    sb.append("_");
    sb.append(description.getMethodName());
    sb.append("_");
    sb.append(distinguisher);
    return sb.toString();
  }
  
  private void executeSQL(URL resource) throws Throwable {
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    EntityTransaction tx = entityManager.getTransaction();
    tx.begin();
    try {
      doExecuteSQL(entityManager, resource);
      tx.commit();
    }
    catch (Throwable t) {
      tx.rollback();
      throw t;
    }
    finally {
      entityManager.close();
    }
  }

  /**
   * Executes the SQL statements in a resource using an entity manager.
   * @param entityManager entity manager that will be used to execute the
   *    statements
   * @param resource resource containing zero or more SQL statements
   * @throws Throwable
   */
  protected abstract void doExecuteSQL(EntityManager entityManager,
      URL resource) throws Throwable;

}
