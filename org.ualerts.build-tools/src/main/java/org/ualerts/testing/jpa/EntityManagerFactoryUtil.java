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

package org.ualerts.testing.jpa;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;

import org.apache.commons.io.IOUtils;

/**
 * Static utility methods that provide support creating an 
 * {@link EntityManagerFactory}.
 *
 * @author Carl Harris
 */
public final class EntityManagerFactoryUtil {

  /**
   * The property name of the persistence unit.
   */
  public static final String PERSISTENCE_UNIT_PROPERTY = "persistence.unit";

  /**
   * Default of the persistence test properties resource.
   */
  public static final String DEFAULT_PROPERTIES_RESOURCE = 
      "persistence-test.properties";
  
  private static final String DEFAULT_PERSISTENCE_UNIT_NAME = 
      "unspecified-persistence-unit";

  private static Map<String, EntityManagerFactory> factoryMap = 
      new HashMap<String, EntityManagerFactory>();

  private EntityManagerFactoryUtil() {
  }
  
  /**
   * Creates a new {@code EntityManagerFactory} configured using the 
   * properties specified in the default properties resource.
   * @return factory object
   * @throws Exception
   * @see {@link #DEFAULT_PROPERTIES_RESOURCE}
   */
  public static EntityManagerFactory createEntityManagerFactory() 
      throws IOException, PersistenceException {
    return createEntityManagerFactory(DEFAULT_PROPERTIES_RESOURCE);
  }

  /**
   * Creates a new {@code EntityManagerFactory} configured using the 
   * properties specified in the given resource.
   * @param propertiesResource a class path resource containing properties
   * @return factory object
   * @throws IOException
   * @throws PersistenceException
   */
  public static EntityManagerFactory createEntityManagerFactory(
      String propertiesResource) throws IOException, PersistenceException {
    
    if (factoryMap.containsKey(propertiesResource))
      return factoryMap.get(propertiesResource);
    
    Properties properties = loadProperties(propertiesResource);
    String persistenceUnit = properties.getProperty(PERSISTENCE_UNIT_PROPERTY, 
        DEFAULT_PERSISTENCE_UNIT_NAME);
    
    factoryMap.put(propertiesResource, 
        createEntityManagerFactory(properties, persistenceUnit, 
            propertiesResource));
    
    return factoryMap.get(propertiesResource);    
  }

  private static EntityManagerFactory createEntityManagerFactory(
      Properties properties, String unit, String propertiesResource) 
          throws PersistenceException {
    try {
      return Persistence.createEntityManagerFactory(
          unit, properties);
    }
    catch (PersistenceException ex) {
      System.err.println("Cannot create persistence unit '" + unit + "'.");
      System.err.println("Make sure the '" + PERSISTENCE_UNIT_PROPERTY 
          + "' property is specified in " + propertiesResource);
      System.err.println("and try cleaning the project.");
      throw ex;
    }
  }
  
  private static Properties loadProperties(String propertiesResource)
      throws IOException {
    InputStream inputStream = getResourceAsStream(propertiesResource);
    try {
      Properties properties = new Properties();
      properties.load(inputStream);
      return properties;
    }
    finally {
      IOUtils.closeQuietly(inputStream);
    }
  }

  private static InputStream getResourceAsStream(String propertiesResource)
      throws FileNotFoundException {
    InputStream inputStream = EntityManagerFactoryUtil.class.getClassLoader()
        .getResourceAsStream(propertiesResource);
    if (inputStream == null) {
      System.err.println("Cannot find resource '" 
          + propertiesResource + "' at the root of the classpath.");
      System.err.println("Make sure the resource exists and "
          + "try cleaning the project.");
      throw new FileNotFoundException(propertiesResource);
    }
    return inputStream;
  }
  
}
