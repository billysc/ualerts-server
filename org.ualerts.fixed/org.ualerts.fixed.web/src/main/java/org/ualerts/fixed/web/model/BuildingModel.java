/*
 * File created on May 15, 2013
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
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.ualerts.fixed.Building;

/**
 * A simple model for a Building object.
 *
 * @author Michael Irwin
 */
@XmlRootElement(name = "building")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class BuildingModel {

  private String id;
  private String name;
  private String abbreviation;
  
  /**
   * Constructs a new instance.
   */
  public BuildingModel() {
    
  }
  
  /**
   * Constructs a new instance based on the provided Building object
   * @param building The Building to base the model from
   */
  public BuildingModel(Building building) {
    this.id = building.getId();
    this.name = building.getName();
    this.abbreviation = building.getAbbreviation();
  }
  
  /**
   * Gets the {@code id} property.
   * @return property value
   */
  @XmlAttribute
  public String getId() {
    return id;
  }
 
  /**
   * Sets the {@code id} property.
   * @param id the value to set
   */
  public void setId(String id) {
    this.id = id;
  }
  
  /**
   * Gets the {@code name} property.
   * @return property value
   */
  @XmlElement
  public String getName() {
    return name;
  }
  
  /**
   * Sets the {@code name} property.
   * @param name the value to set
   */
  public void setName(String name) {
    this.name = name;
  }
  
  /**
   * Gets the {@code abbreviation} property.
   * @return property value
   */
  @XmlElement
  public String getAbbreviation() {
    return abbreviation;
  }
 
  /**
   * Sets the {@code abbreviation} property.
   * @param abbreviation the value to set
   */
  public void setAbbreviation(String abbreviation) {
    this.abbreviation = abbreviation;
  }
  
}
