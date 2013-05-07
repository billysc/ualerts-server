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
package org.ualerts.fixed.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * A controller that serves up the start/index page.
 *
 * @author Michael Irwin
 */
@Controller
public class IndexController {

  /** Servlet path for the index resource */
  public static final String INDEX_PATH = "/";
  
  /**
   * Display the basic start/index page
   * @return The name of the JSP file to be rendered
   */
  @RequestMapping(INDEX_PATH)
  public String displayIndex() {
    return "index";
  }
  
}
