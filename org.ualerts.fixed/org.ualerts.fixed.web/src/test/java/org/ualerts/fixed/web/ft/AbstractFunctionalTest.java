/*
 * File created on May 9, 2013
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

import junit.framework.Assert;

import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gargoylesoftware.htmlunit.AlertHandler;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * Utility methods that are shared amongst all functional tests.
 *
 * @author Michael Irwin
 */
public abstract class AbstractFunctionalTest {
  
  private static final String UI_PATH = "/ui/fixtures";
      // TODO repeated in web.xml
  
  /**
   * Shared logger
   */
  protected final Logger logger =  // CHECKSTYLE:idiomatic
      LoggerFactory.getLogger(getClass());   
  
  /**
   * Short delay for Javascript wait time. Intended for short functions that
   * don't fetch remote sources
   */
  protected static final Long JS_SHORT_DELAY = 4000L;
  
  /**
   * Long delay for Javascript wait time. Should be used whenever attempting
   * to fetch remote sources
   */
  protected static final Long JS_LONG_DELAY = 10000L;

  private WebClient client = new WebClient(BrowserVersion.FIREFOX_17);
  private String contextUrl = WebContextUtil.getUrl();
  
  /**
   * Setup for the functional test 
   */
  @Before
  public void clientSetup() {
    getClient().setCssErrorHandler(new NoOpErrorHandler());
    getClient().setAlertHandler(new AlertHandler() {
      @Override
      public void handleAlert(Page page, String message) {
        logger.error("javascript alert: " + message 
            + " on page " + page);
      }
    });
  }
  
  /**
   * Load an html page and return it
   * @param url The url to be loaded
   * @return The loaded page
   * @throws Exception
   */
  protected HtmlPage getHtmlPage(String url) throws Exception {
    HtmlPage page = client.getPage(contextUrl + UI_PATH + url);
    client.waitForBackgroundJavaScript(JS_SHORT_DELAY);
    return page;
  }
  
  /**
   * Get the context url
   * @return The context url
   */
  protected String getContextUrl() {
    return contextUrl;
  }
  
  /**
   * Get the web client
   * @return The web client
   */
  protected WebClient getClient() {
    return client;
  }
  
  /**
   * Assert that a value is either null or empty
   * @param value The value to assert
   */
  protected void assertEmpty(String value) {
    if (value != null && !value.trim().isEmpty())
      Assert.fail("Expected empty, but got " + value);
  }
  
}
