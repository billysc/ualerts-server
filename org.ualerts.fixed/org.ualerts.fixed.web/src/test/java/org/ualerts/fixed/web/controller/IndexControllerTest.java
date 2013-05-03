/*
 * File created on May 3, 2013
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

package org.ualerts.fixed.web.controller;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

/**
 * Test case for the {@link IndexController} class.
 *
 * @author Michael Irwin
 */
public class IndexControllerTest {

  private IndexController controller;
  
  /**
   * Setup to be performed for each test
   */
  @Before
  public void setup() {
    controller = new IndexController();
  }
  
  /**
   * Ensure that the index page is returned (and therefore will be rendered)
   * when the index page is requested.
   */
  @Test
  public void validateIndexPage() {
    Assert.assertEquals("index", controller.displayIndex());
  }
  
}
