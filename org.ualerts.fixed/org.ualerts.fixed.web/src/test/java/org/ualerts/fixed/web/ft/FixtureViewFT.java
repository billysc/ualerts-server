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
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ualerts.fixed.web.controller.IndexController;
import org.ualerts.testing.jpa.EntityManagerFactoryResource;
import org.ualerts.testing.jpa.HibernatePersistentDataResource;
import org.ualerts.testing.jpa.PersistentDataResource;
import org.ualerts.testing.jpa.TestResources;

import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableBody;
import com.gargoylesoftware.htmlunit.html.HtmlTableCell;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;

import edu.vt.cns.kestrel.common.IntegrationTestRunner;

/**
 * Functional tests for Fixtures View
 *
 * @author Michael Irwin
 */
@RunWith(IntegrationTestRunner.class)
public class FixtureViewFT extends AbstractFunctionalTest {

  private static final String HTML_ID_FIXTURE_EMPTY = "fixturesListEmpty";
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
  private static FixtureViewValidator validator;
      
  /**
   * Perform one-time set up tasks
   * @throws Exception
   */
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    properties = PropertiesAccessor.newInstance("persistent-data.properties");
    validator = new FixtureViewValidator(properties);
  }
  
  /**
   * Validate that if there are no fixtures in the system, an appropriate
   * message is displayed
   * @throws Exception
   */
  @Test
  public void testValidateEmptyMessageWhenNoFixtures() throws Exception {
    HtmlPage page = getHtmlPage(IndexController.INDEX_PATH);
    assertTrue(page.getTitleText().contains("Enrolled Fixtures"));
    ((HtmlDivision) page
        .getFirstByXPath("//div[@id='" + HTML_ID_FIXTURE_EMPTY + "']"))
        .getTextContent().contains("no fixtures");
  }
  
  /**
   * Validate that a known fixture displays the correct properties on the
   * fixtures view
   * @throws Exception
   */
  @Test
  @TestResources(prefix = "sql/", 
      before = "FixtureViewFT_before", after = "FixtureViewFT_after")
  public void testValidateFixtureDisplay() throws Exception {
    HtmlPage page = getHtmlPage(IndexController.INDEX_PATH);
    HtmlTable table = getFixtureTable(page);
    HtmlTableRow row = (HtmlTableRow) table.getFirstByXPath("//tbody/tr[1]");
    validator.validateIsFixture1(row);
  }
  
  /**
   * Validate the table is sortable by clicking the first header and verifying
   * the row contents
   * @throws Exception
   */
  @Test
  @TestResources(prefix = "sql/", 
      before = "FixtureViewFT_before", after = "FixtureViewFT_after")
  public void testValidateTableSorting() throws Exception {
    HtmlPage page = getHtmlPage(IndexController.INDEX_PATH);

    HtmlTable table = getFixtureTable(page);
    HtmlTableRow row = (HtmlTableRow) table.getFirstByXPath("tbody/tr[1]");
    validator.validateIsFixture1(row); 
  
    row = (HtmlTableRow) table.getFirstByXPath("tbody/tr[2]");
    validator.validateIsFixture2(row);
    
    HtmlTableRow header = (HtmlTableRow) table.getFirstByXPath("thead/tr[1]");
    ((HtmlTableCell) header.getFirstByXPath("th[1]")).click();
    ((HtmlTableCell) header.getFirstByXPath("th[1]")).click();
    row = (HtmlTableRow) table.getFirstByXPath("tbody/tr[1]");
    validator.validateIsFixture2(row);
    
    ((HtmlTableCell) header.getFirstByXPath("th[1]")).click();
    row = (HtmlTableRow) table.getFirstByXPath("tbody/tr[1]");
    validator.validateIsFixture1(row);
  }

  /**
   * Validate the table filtering works
   * @throws Exception
   */
  @Test
  @TestResources(prefix = "sql/", 
      before = "FixtureViewFT_before", after = "FixtureViewFT_after")
  public void validateTableFiltering() throws Exception {
    HtmlPage page = getHtmlPage(IndexController.INDEX_PATH);
    HtmlTable table = getFixtureTable(page);
    
    HtmlInput searchField = page
        .getFirstByXPath("//div[@class='dataTables_filter']/label[1]/input[1]");
    searchField.type(properties.getString("positionHint.2.hintText"));
    
    HtmlTableBody body = (HtmlTableBody) table.getFirstByXPath("tbody");
    assertEquals(1, body.getChildElementCount());
    
    HtmlTableRow row = (HtmlTableRow) table.getFirstByXPath("tbody/tr[1]");
    validator.validateIsFixture2(row);
  }
  
  private HtmlTable getFixtureTable(HtmlPage page) {
    return (HtmlTable) page
        .getFirstByXPath("//table[@id='" + HTML_ID_FIXTURE_TABLE + "']");
  }
  
}
