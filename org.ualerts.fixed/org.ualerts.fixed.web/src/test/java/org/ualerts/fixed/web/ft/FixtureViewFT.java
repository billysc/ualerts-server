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

import javax.persistence.EntityManager;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ualerts.fixed.web.controller.IndexController;

import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
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

  private static final String HTML_ID_FIXTURE_TABLE = "fixturesList";
  
  private static EntityManager entityManager;
  
  /**
   * Performs one-time setup needed to run the test
   * @throws Exception
   */
  @BeforeClass
  public static void oneTimeSetup() throws Exception {
    entityManager = PersistenceTestSupport
        .createEntityManagerFactory("etc/persistence-test.properties")
        .createEntityManager();
  }
  
  /**
   * Performs one-time teardown
   * @throws Exception
   */
  @AfterClass
  public static void oneTimeTearDown() throws Exception {
    if (entityManager != null && entityManager.isOpen()) {
      DBSetupUtility.cleanDatabase(entityManager);
      entityManager.close();
    }
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
        .getFirstByXPath("//div[@id='" + HTML_ID_FIXTURE_TABLE + "']"))
        .getTextContent().contains("no fixtures");
  }
  
  /**
   * Validate that a known fixture displays the correct properties on the
   * fixtures view
   * @throws Exception
   */
  @Test
  public void testValidateFixtureDisplay() throws Exception {
    DBSetupUtility.populateDatabase(entityManager);
    HtmlPage page = getHtmlPage(IndexController.INDEX_PATH);
    HtmlTable table = getFixtureTable(page);
    HtmlTableRow row = (HtmlTableRow) table.getFirstByXPath("//tbody/tr[1]");
    validateRowDisplay(row, 
        DBSetupUtility.BUILDING_ABBR + " " + DBSetupUtility.FIXTURE_ROOM_NUMBER,
        DBSetupUtility.FIXTURE_POSITION_HINT, DBSetupUtility.FIXTURE_IP_ADDR, 
        DBSetupUtility.FIXTURE_MAC_ADDR, DBSetupUtility.FIXTURE_INV_NUMBER);
  }
  
  /**
   * Validate the table is sortable by clicking the first header and verifying
   * the row contents
   * @throws Exception
   */
  @Test
  public void testValidateTableSorting() throws Exception {
    DBSetupUtility.populateDatabase(entityManager);
    HtmlPage page = getHtmlPage(IndexController.INDEX_PATH);
    HtmlTable table = getFixtureTable(page);
    HtmlTableRow row = (HtmlTableRow) table.getFirstByXPath("//tbody/tr[1]");
    validateRowDisplay(row, 
        DBSetupUtility.BUILDING_ABBR + " " + DBSetupUtility.FIXTURE_ROOM_NUMBER,
        DBSetupUtility.FIXTURE_POSITION_HINT, DBSetupUtility.FIXTURE_IP_ADDR, 
        DBSetupUtility.FIXTURE_MAC_ADDR, DBSetupUtility.FIXTURE_INV_NUMBER);
    row = (HtmlTableRow) table.getFirstByXPath("//tbody/tr[2]");
    validateRowDisplay(row, DBSetupUtility.BUILDING_ABBR + " " 
        + DBSetupUtility.FIXTURE2_ROOM_NUMBER,
        DBSetupUtility.FIXTURE2_POSITION_HINT, DBSetupUtility.FIXTURE2_IP_ADDR, 
        DBSetupUtility.FIXTURE2_MAC_ADDR, DBSetupUtility.FIXTURE2_INV_NUMBER);
    
    HtmlTableRow header = (HtmlTableRow) table.getFirstByXPath("//thead/tr[1]");
    ((HtmlTableCell) header.getFirstByXPath("//td[1]")).click();
    row = (HtmlTableRow) table.getFirstByXPath("//tbody/tr[1]");
    validateRowDisplay(row, DBSetupUtility.BUILDING_ABBR + " " 
        + DBSetupUtility.FIXTURE2_ROOM_NUMBER,
        DBSetupUtility.FIXTURE2_POSITION_HINT, DBSetupUtility.FIXTURE2_IP_ADDR, 
        DBSetupUtility.FIXTURE2_MAC_ADDR, DBSetupUtility.FIXTURE2_INV_NUMBER);
    
    ((HtmlTableCell) header.getFirstByXPath("//td[1]")).click();
    row = (HtmlTableRow) table.getFirstByXPath("//tbody/tr[1]");
    validateRowDisplay(row, 
        DBSetupUtility.BUILDING_ABBR + " " + DBSetupUtility.FIXTURE_ROOM_NUMBER,
        DBSetupUtility.FIXTURE_POSITION_HINT, DBSetupUtility.FIXTURE_IP_ADDR, 
        DBSetupUtility.FIXTURE_MAC_ADDR, DBSetupUtility.FIXTURE_INV_NUMBER);
  }
  
  private HtmlTable getFixtureTable(HtmlPage page) {
    return (HtmlTable) page
        .getFirstByXPath("//table[@id='" + HTML_ID_FIXTURE_TABLE + "']");
  }
  
  private void validateRowDisplay(HtmlTableRow row, String location, 
      String positionHint, String ip, String mac, String inventoryTag) 
          throws Exception {
    int index = 0;
    assertEquals(location, getCell(row, index++).getTextContent());
    assertEquals(positionHint, getCell(row, index++).getTextContent());
    assertEquals(ip, getCell(row, index++).getTextContent());
    assertEquals(mac, getCell(row, index++).getTextContent());
    assertEquals(inventoryTag, getCell(row, index++).getTextContent());

    assertTrue(getCell(row, index).getTextContent().contains("Details"));
  }
  
  /**
   * Retrieve a cell from a row
   * @param row The row to retrieve the cell for
   * @param index The index of the row, 0-based
   * @return
   */
  private HtmlTableCell getCell(HtmlTableRow row, int index) {
    return (HtmlTableCell) row.getFirstByXPath("//td[" + (index + 1) + "]");
  }
  
}
