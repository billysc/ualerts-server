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
import java.util.Collection;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * A wrapper of a HttpServletResponse that writes output to an internal buffer,
 * rather than to the actual output stream to the end client.
 * 
 * Use cases include the desire to embed a rendered JSP page (html) in a JSON
 * response.
 *
 * @author Michael Irwin
 */
public class FakeHttpServletResponse implements HttpServletResponse {

  private HttpServletResponse response;
  private StringWriter stringWriter = new StringWriter();
  private PrintWriter writer = new PrintWriter(stringWriter);
  
  public FakeHttpServletResponse(HttpServletResponse response) {
    this.response = response;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public void flushBuffer() throws IOException {
    response.flushBuffer();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getBufferSize() {
    return response.getBufferSize();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getCharacterEncoding() {
    return response.getCharacterEncoding();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getContentType() {
    return response.getContentType();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Locale getLocale() {
    return response.getLocale();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ServletOutputStream getOutputStream() throws IOException {
    return response.getOutputStream();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public PrintWriter getWriter() throws IOException {
    return writer;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isCommitted() {
    return response.isCommitted();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void reset() {
    response.reset();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void resetBuffer() {
    response.resetBuffer();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setBufferSize(int arg0) {
    response.setBufferSize(arg0);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setCharacterEncoding(String arg0) {
    response.setCharacterEncoding(arg0);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setContentLength(int arg0) {
    response.setContentLength(arg0);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setContentType(String arg0) {
    response.setContentType(arg0);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setLocale(Locale arg0) {
    response.setLocale(arg0);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addCookie(Cookie arg0) {
    response.addCookie(arg0);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addDateHeader(String arg0, long arg1) {
    response.addDateHeader(arg0, arg1);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addHeader(String arg0, String arg1) {
    response.addHeader(arg0, arg1);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addIntHeader(String arg0, int arg1) {
    response.addIntHeader(arg0, arg1);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean containsHeader(String arg0) {
    return response.containsHeader(arg0);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String encodeRedirectURL(String arg0) {
    return response.encodeRedirectURL(arg0);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String encodeURL(String arg0) {
    return response.encodeURL(arg0);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void sendError(int arg0) throws IOException {
    response.sendError(arg0);
  }

  /**
   * {@inheritDoc}
   */
  @Override
 public void sendError(int arg0, String arg1) throws IOException {
    response.sendError(arg0, arg1);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void sendRedirect(String arg0) throws IOException {
    response.sendRedirect(arg0);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setDateHeader(String arg0, long arg1) {
    response.setDateHeader(arg0, arg1);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setHeader(String arg0, String arg1) {
    response.setHeader(arg0, arg1);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setIntHeader(String arg0, int arg1) {
    response.setIntHeader(arg0, arg1);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setStatus(int arg0) {
    response.setStatus(arg0);
  }
  
  public StringWriter getStringWriter() {
    return stringWriter;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getHeader(String arg0) {
    return response.getHeader(arg0);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Collection<String> getHeaderNames() {
    return response.getHeaderNames();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Collection<String> getHeaders(String arg0) {
    return response.getHeaders(arg0);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getStatus() {
    return response.getStatus();
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("deprecation")
  @Override
  public void setStatus(int arg0, String arg1) {
    response.setStatus(arg0, arg1);
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("deprecation")
  @Override
  public String encodeRedirectUrl(String arg0) {
    return response.encodeRedirectUrl(arg0);
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("deprecation")
  @Override
  public String encodeUrl(String arg0) {
    return response.encodeUrl(arg0);
  }

}
