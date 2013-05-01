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
package org.ualerts.fixed.persistence;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;

/**
 * Static utility methods that provide support for unit testing persistence
 * classes.
 *
 * @author Carl Harris
 */
public class PersistenceTestSupport {

  public static final String PERSISTENCE_UNIT_PROPERTY = "persistence.unit";
  
  public static final String PERSISTENCE_TEST_PROPERTIES = 
      "persistence-test.properties";

  /**
   * Creates a new {@code EntityManagerFactory} configured using the 
   * properties specified in the given resource.
   * @param propertiesResource a class path resource containing properties
   * @return factory object
   * @throws Exception
   */
  public static EntityManagerFactory createEntityManagerFactory(
      String propertiesResource) throws IOException, PersistenceException {
    String unit = null;
    InputStream inputStream = null;
    try {
      inputStream = PersistenceTestSupport.class.getClassLoader()
          .getResourceAsStream(propertiesResource);
      if (inputStream == null) {
        throw new FileNotFoundException(propertiesResource);
      }
      Properties properties = new Properties();
      properties.load(inputStream);
      unit = properties.getProperty(PERSISTENCE_UNIT_PROPERTY, 
          "unspecified-persistence-unit");
      return Persistence.createEntityManagerFactory(
          unit, properties);
    }
    catch (FileNotFoundException ex) {
      System.err.println("Cannot find resource '" 
          + propertiesResource + "' at the root of the classpath.");
      System.err.println("Make sure the resource exists and try cleaning the project.");
      throw ex;
    }
    catch (PersistenceException ex) {
      System.err.println("Cannot create persistence unit '" + unit + "'.");
      System.err.println("Make sure the '" + PERSISTENCE_UNIT_PROPERTY 
          + "' property is specified in " + propertiesResource);
      System.err.println("and try cleaning the project.");
      throw ex;
    }
    finally {
      if (inputStream != null) {
        try {
          inputStream.close();
        }
        catch (IOException ex) {
          // oh, well
        }
      }
    }
    
  }
  
  /**
   * Creates a new {@code EntityManagerFactory} configured using the 
   * properties specified in the {@code persistence-test.properties} resource.
   * @return factory object
   * @throws Exception
   */
  public static EntityManagerFactory createEntityManagerFactory() 
      throws IOException, PersistenceException {
    return createEntityManagerFactory(PERSISTENCE_TEST_PROPERTIES);
  }
  
}
