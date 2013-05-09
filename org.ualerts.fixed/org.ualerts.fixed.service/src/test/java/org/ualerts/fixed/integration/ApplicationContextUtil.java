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

package org.ualerts.fixed.integration;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * A utility for loading an {@code ApplicationContext}
 *
 * @author Brian Early
 */
public final class ApplicationContextUtil {

  private static ClassPathXmlApplicationContext context;

  private ApplicationContextUtil() {
  }
  
  /**
   * Gets the application context.
   * @return the application context.
   */
  public static synchronized ApplicationContext getContext() {
    try {
    if (context == null) {
      context =
          new ClassPathXmlApplicationContext("spring/fixed-data.xml", 
          "spring/fixed-service.xml");
    }
    return context;
    }
    catch (RuntimeException ex) {
      ex.getCause().printStackTrace();
      throw ex;
    }
  }
  
  /**
   * Closes the application context.
   */
  public static synchronized void close() {
    if (context == null) return;
    context.close();
    context = null;
  }

}
