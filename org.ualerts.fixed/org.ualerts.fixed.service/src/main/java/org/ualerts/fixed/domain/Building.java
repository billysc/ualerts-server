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
package org.ualerts.fixed.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * A read-only entity representing a building.
 *
 * @author Brian Early
 */
@Entity
@Table(name = "building")
@org.hibernate.annotations.Immutable
public class Building {
  private String id;
  private String name;
  
  /**
   * Gets the {@code id} property.
   */
  @Id
  public String getId() {
    return id;
  }
  
  /**
   * Sets the {@code id} property.
   */
  public void setId(String id) {
    this.id = id;
  }
  
  /**
   * Gets the {@code name} property.
   */
  @Column(name = "name")
  public String getName() {
    return name;
  }
  
  /**
   * Sets the {@code name} property.
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * @inheritDoc
   */
  @Override
  public int hashCode() {
    if (this.id == null) return 0;
    return this.id.hashCode();
  }

  /**
   * @inheritDoc
   */
  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Building)) return false;
    if (this.id == null) return false;
    return this.id.equals(((Building) obj).id);
  }

}
