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

import java.io.Serializable;

/**
 * A request thrown when a request to locate a specific persistent entity
 * fails because the entity does not exist.
 *
 * @author Brian Early
 */
public class EntityNotFoundException extends Exception {
  private static final long serialVersionUID = 8656168652001294760L;
  private final Class<?> entityClass;
  private final Serializable id;
  
  /**
   * Constructs a new instance.
   * @param entityClass the entity class
   * @param id the ID of the entity.
   */
  public EntityNotFoundException(Class<?> entityClass, Serializable id) {
    super("entity of type " + entityClass.getCanonicalName() + " with ID " 
        + id + " does not exist");
    this.entityClass = entityClass;
    this.id = id;
  }

  /**
   * Gets the {@code id} property.
   * @return the entity ID
   */
  public Serializable getId() {
    return id;
  }

  /**
   * Gets the {@code entityClass} property.
   * @return the entity class
   */
  public Class<?> getEntityClass() {
    return entityClass;
  }
  
}
