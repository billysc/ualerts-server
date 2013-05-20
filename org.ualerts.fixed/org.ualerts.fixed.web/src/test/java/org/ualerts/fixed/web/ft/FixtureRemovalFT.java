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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ualerts.fixed.web.controller.fixture.IndexController;
import org.ualerts.testing.jpa.EntityManagerFactoryResource;
import org.ualerts.testing.jpa.HibernatePersistentDataResource;
import org.ualerts.testing.jpa.PersistentDataResource;
import org.ualerts.testing.jpa.TestResources;

import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;

import edu.vt.cns.kestrel.common.IntegrationTestRunner;

/**
 * Functional tests for fixture removal.
 *
 * @author Carl Harris
 */
@RunWith(IntegrationTestRunner.class)
public class FixtureRemovalFT extends AbstractFunctionalTest {

  private static final String HTML_ID_FIXTURE_TABLE = "fixturesList";
  
  // CHECKSTYLE:OFF
  @ClassRule
  public static EntityManagerFactoryResource entityManagerFactory = 
      new EntityManagerFactoryResource("persistence-test.properties");
  
  @Rule
  public PersistentDataResource persistentData =
     new HibernatePersistentDataResource(entityManagerFactory);
  // CHECKSTYLE:ON
  
  private static PropertiesAccessor properties;
      
  private HtmlPage page;
  private HtmlTable table;
  private HtmlTableRow row;
  private HtmlAnchor control;
  
  /**
   * Performs one-time set up tasks.
   * @throws Exception
   */
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    properties = PropertiesAccessor.newInstance("persistent-data.properties");
  }
  
  /**
   * Performs per-test setup tasks.
   * @throws Exception
   */
  @Before
  public void setUp() throws Exception {
    page = getHtmlPage(IndexController.INDEX_PATH);
    table = getFixtureTable(page);
    row = getFirstTableRow(table);
    control = getRemoveControl(row);    
  }
  
  /**
   * Tests that when the remove dialog is canceled, the fixture is not
   * removed.
   * @throws Exception
   */
  @Test
  @TestResources(prefix = "sql/", before = "FixtureRemovalFT_before",
      after = "FixtureRemovalFT_after")
  public void testNotRemovedWhenDialogCanceled() throws Exception {
    String id = row.getAttribute("data-entity-id");
    clickControlButtonAndWait();
    clickCancelButtonAndWait();
    HtmlTableRow topRow = getFirstTableRow(table);
    assertEquals(id, topRow.getAttribute("data-entity-id"));
  }

  /**
   * Tests that when the remove confirmation dialog is canceled, the fixture
   * is not removed.
   * @throws Exception
   */
  @Ignore
  @Test
  @TestResources(prefix = "sql/", before = "FixtureRemovalFT_before",
      after = "FixtureRemovalFT_after")
  public void testNotRemovedWhenConfirmationCanceled() throws Exception {    
    org.junit.Assert.fail("not implemented");
  }
  
  /**
   * Tests that when the removal is confirmed, the fixture is removed.
   * @throws Exception
   */
  @Ignore
  @Test
  @TestResources(prefix = "sql/", before = "FixtureRemovalFT_before",
      after = "FixtureRemovalFT_after")
  public void testRemovedWhenConfirmed() throws Exception {
    clickControlButtonAndWait();
    clickSubmitButtonAndWait();
    assertEquals(0, table.getBodies().get(0).getRows().size());
  }

  private void clickControlButtonAndWait() throws IOException {
    clickButtonAndWait(control);
  }

  private void clickCancelButtonAndWait() throws Exception {
    HtmlButton button = (HtmlButton) page.getFirstByXPath(
        "//button[@data-dismiss='modal']");
    assertNotNull(button);
    clickButtonAndWait(button);
  }

  private void clickSubmitButtonAndWait() throws Exception {
    HtmlButton button = (HtmlButton) page.getFirstByXPath(
        "//button[@class='btn btn-primary']");
    assertNotNull(button);
    clickButtonAndWait(button);
  }
  
  private void clickButtonAndWait(HtmlElement button) throws IOException {
    button.click();  
    getClient().waitForBackgroundJavaScript(JS_LONG_DELAY);
  }
  
  private HtmlAnchor getRemoveControl(HtmlTableRow row) {
    HtmlAnchor control = (HtmlAnchor) row.getFirstByXPath(
        "//a[@data-control-function='remove']");
    assertNotNull(control);
    return control;
  }

  private HtmlTableRow getFirstTableRow(HtmlTable table) {
    HtmlTableRow row = (HtmlTableRow) table.getFirstByXPath("//tbody/tr[1]");
    assertNotNull(row);
    return row;
  }

  private HtmlTable getFixtureTable(HtmlPage page) {
    HtmlTable table = (HtmlTable) page
        .getFirstByXPath("//table[@id='" + HTML_ID_FIXTURE_TABLE + "']");
    assertNotNull(table);
    return table;
  }
  
}
