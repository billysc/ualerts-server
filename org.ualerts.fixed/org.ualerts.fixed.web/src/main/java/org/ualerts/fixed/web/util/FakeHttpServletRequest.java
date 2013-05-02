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
package org.ualerts.fixed.web.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;


/**
 * A wrapper around the HttpServletRequest that allows for changing of the
 * Accepts Mime Type.
 * 
 * A use case might be sending the same request through the Dispatcher, but
 * changing the path it takes.  For example, if a Controller has one method
 * that has "produces='application/json'" and wanted to embed a rendered JSP in
 * the response, it could send its own request back through wrapped in this
 * class with a changed Accept header.  The Controller then could have another
 * method for "produces='text/html'" that will then be called to render the JSP.
 *
 * @author Michael Irwin
 */
public class FakeHttpServletRequest extends HttpServletRequestWrapper {
  
  private String acceptsMime;
  
  public FakeHttpServletRequest(HttpServletRequest request, String acceptsMime) {
    super(request);
    this.acceptsMime = acceptsMime;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public String getHeader(String name) {
    if (name.equals("Accept")) {
      return acceptsMime;
    }
    return super.getHeader(name);
  }
  
}
