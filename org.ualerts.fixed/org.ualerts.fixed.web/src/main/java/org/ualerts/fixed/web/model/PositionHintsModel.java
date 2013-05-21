/*
 * File created on May 17, 2013
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

package org.ualerts.fixed.web.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * A model that holds position hints.
 *
 * @author Michael Irwin
 */
@XmlRootElement(name = "positionHints")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class PositionHintsModel {

  private String[] positionHints;
  
  /**
   * Constructs a new instance.
   */
  public PositionHintsModel() {
  }
  
  /**
   * Constructs a new instance.
   * @param positionHints position hints to wrap
   */
  public PositionHintsModel(String[] positionHints) {
    this.positionHints = positionHints;
  }
  
  /**
   * Gets the {@code positionHints} property.
   * @return property value
   */
  @XmlElement(name = "positionHint")
  public String[] getPositionHints() {
    return positionHints;
  }
  
  /**
   * Sets the {@code positionHints} property.
   * @param positionHints the value to set
   */
  public void setPositionHints(String[] positionHints) {
    this.positionHints = positionHints;
  }
  
}
