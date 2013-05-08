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
package org.ualerts.fixed;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * An entity representing a position hint.
 *
 * @author Brian Early
 */
@Entity
@Table(name = "position_hint")
public class PositionHint extends AbstractEntity {

  private static final long serialVersionUID = 626731060336569077L;
  private String hintText;

  /**
   * Gets the {@code hintText} property.
   * @return property value
   */
  @Column(name = "hint_text")
  public String getHintText() {
    return hintText;
  }

  /**
   * Sets the {@code hintText} property.
   * @param hintText property value to set
   */
  public void setHintText(String hintText) {
    this.hintText = hintText;
  }
  
}
