/*
 * File created on May 14, 2013
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

package org.ualerts.fixed.web.ft;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.io.IOUtils;

/**
 * A wrapper for a {@link Properties} object.
 *
 * @author ceharris
 */
public class PropertiesAccessor {

  private final Properties properties;

  /**
   * Constructs a new instance.
   * @param properties properties delegate
   */
  public PropertiesAccessor(Properties properties) {
    this.properties = properties;
  }
  
  /**
   * Creates a new instance by loading the properties from the given
   * resource at the root of the classpath.
   * @param resource name of a resource to locate at the root of the
   *    classpath.
   * @return accessor object
   * @throws IOException
   */
  public static PropertiesAccessor newInstance(String resource)
      throws IOException {
    InputStream inputStream = 
        PropertiesAccessor.class.getClassLoader().getResourceAsStream(resource);
    if (inputStream == null) {
      throw new FileNotFoundException(resource);
    }
    try {
      Properties properties = new Properties();
      properties.load(inputStream);
      return new PropertiesAccessor(properties);
    }
    finally {
      IOUtils.closeQuietly(inputStream);
    }
  }
  
  /**
   * Gets a property value as a string.
   * @param property 
   * @return string property value 
   * @throws IllegalArgumentException if {@code property} does not exist
   */
  public String getString(String property) throws IllegalArgumentException {
    return getProperty(property);
  }
  
  /**
   * Gets a property value as an integer.
   * @param property 
   * @return integer property value 
   * @throws IllegalArgumentException if {@code property} does not exist
   * @throws NumberFormatException if the property value cannot be
   *    coerced to {@code int}.
   */
  public int getInt(String property) 
      throws IllegalArgumentException, NumberFormatException {
    return Integer.parseInt(getProperty(property));
  }
  
  /**
   * Gets a property value as a long.
   * @param property 
   * @return long property value 
   * @throws IllegalArgumentException if {@code property} does not exist
   * @throws NumberFormatException if the property value cannot be
   *    coerced to {@code long}.
   */
  public long getLong(String property) 
      throws IllegalArgumentException, NumberFormatException {
    return Long.parseLong(getProperty(property));
  }
  
  /**
   * Gets a property value as a boolean.
   * @param property 
   * @return boolean property value 
   * @throws IllegalArgumentException if {@code property} does not exist
   * @throws NumberFormatException if the property value cannot be
   *    coerced to {@code boolean}.
   */
  public boolean getBoolean(String property) 
      throws IllegalArgumentException, NumberFormatException {
    return Boolean.parseBoolean(getProperty(property));
  }
  
  private String getProperty(String property) {
    if (!properties.containsKey(property)) {
      throw new IllegalArgumentException(property + ": no such property");
    }
    return properties.getProperty(property);
  }
  
}