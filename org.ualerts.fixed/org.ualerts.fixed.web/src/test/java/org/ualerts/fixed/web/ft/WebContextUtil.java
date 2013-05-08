/*
 * File created on May 7, 2013
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

/**
 * A utility for obtaining the context URL for a web application under test.
 *
 * @author Carl Harris
 */
public class WebContextUtil {

  private static final String RESOURCE_NAME = "etc/web-context.properties";

  private static final String contextUrl;

  private static final String contextPath;
  
  /**
   * Gets the context URL for the web application under test.
   * @return context URL.
   */
  public static String getUrl() {
    return contextUrl;
  }
  
  /**
   * Gets the {@code contextPath} property.
   */
  public static String getContextPath() {
    return contextPath;
  }

  static {
    try {
      Properties props = loadProperties();
      StringBuilder sb = new StringBuilder();
      sb.append(props.getProperty("context.scheme"));
      sb.append("://");
      sb.append(props.getProperty("context.host"));
      sb.append(":");
      sb.append(props.getProperty("context.port"));
      sb.append(props.getProperty("context.path"));
      contextUrl = sb.toString();
      contextPath = props.getProperty("context.path");
    }
    catch (Exception ex) {
      throw new ExceptionInInitializerError(ex);
    }
  }
  
  private static Properties loadProperties() throws IOException {
    InputStream inputStream = WebContextUtil.class.getClassLoader()
      .getResourceAsStream(RESOURCE_NAME);
    
    if (inputStream == null) {
      throw new FileNotFoundException(RESOURCE_NAME);
    }
    
    try {
      Properties properties = new Properties();
      properties.load(inputStream);
      return properties;
    }
    finally {
      try {
        inputStream.close();
      }
      catch (IOException ex) {
        // oh, well
      }
    }
  }
  
}