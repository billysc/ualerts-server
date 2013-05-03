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

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
/**
 * A wrapper around the HttpServletResponse that provides a local OutputStream
 * instead of the stream from the original response, allowing for retrieval
 * of the HTML data.
 *
 * Use cases include the desire to embed a rendered JSP page (html) in a JSON
 * response.  When the response is normally sent through the dispatcher, the
 * output is written directly to the end user.  With this wrapper, the output
 * is written internally.  This allows the output to be retrieved and used in
 * another manner, such as a value in a JSON model.
 *
 * @author Michael Irwin
 */
public class FakeHttpServletResponse extends HttpServletResponseWrapper {

  private StringWriter stringWriter = new StringWriter();
  private PrintWriter writer = new PrintWriter(stringWriter);
  
  /**
   * Create a new wrapped response that will buffer all output
   * @param response The response that should be wrapped
   */
  public FakeHttpServletResponse(HttpServletResponse response) {
    super(response);
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public PrintWriter getWriter() throws IOException {
    return writer;
  }

  /**
   * Get the StringWriter that was embedded within the PrintWriter
   * @return The StringWriter that contains the data written to the response
   */
  public StringWriter getStringWriter() {
    return stringWriter;
  }

}
