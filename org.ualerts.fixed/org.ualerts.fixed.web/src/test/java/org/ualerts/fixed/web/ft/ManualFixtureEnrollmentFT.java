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

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ualerts.fixed.web.controller.IndexController;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import edu.vt.cns.kestrel.common.IntegrationTestRunner;

/**
 * Functional tests for manual fixture enrollment (AFDP-10).
 *
 * @author ceharris
 */
@RunWith(IntegrationTestRunner.class)
public class ManualFixtureEnrollmentFT {

  private static final String UI_PATH = "/ui";    // TODO repeated in web.xml
  
  private WebClient client = new WebClient(BrowserVersion.FIREFOX_17);
  private String contextUrl = WebContextUtil.getUrl();

  @Before
  public void setUp() throws Exception {
    client.setCssErrorHandler(new NoOpErrorHandler());
  }
  
  @Test
  public void testGetManualEnrollmentForm() throws Exception {
    HtmlPage page = client.getPage(contextUrl + UI_PATH + IndexController.INDEX_PATH);
    assertTrue(page.getTitleText().contains("Manual Enrollment"));
  }

}
