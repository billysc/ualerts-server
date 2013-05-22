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
import org.junit.ClassRule;
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
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableCell;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;
import com.gargoylesoftware.htmlunit.javascript.host.Event;
import com.gargoylesoftware.htmlunit.javascript.host.KeyboardEvent;

import edu.vt.cns.kestrel.common.IntegrationTestRunner;

/**
 * Functional tests for fixture editing.
 *
 * @author Michael Irwin
 */
@RunWith(IntegrationTestRunner.class)
public class FixtureEditFT extends AbstractFunctionalTest {

  private static final String HTML_ID_BUILDING = "buildingContainer";
  private static final String HTML_ID_IP_ADDRESS = "ipAddressContainer";
  private static final String HTML_ID_POSITION_HINT = "positionHintContainer";
  private static final String HTML_ID_ROOM = "roomContainer";
  private static final String HTML_ID_FIXTURE_TABLE = "fixturesList";
  private static final String NEW_IP_ADDRESS = "65.67.77.69";
  private static final String NEW_ROOM = "400";
  private static final String NEW_POSITION_HINT = "FORGOTTEN-CORNER";
  
  // CHECKSTYLE:OFF
  @ClassRule
  public static EntityManagerFactoryResource entityManagerFactory = 
      new EntityManagerFactoryResource("persistence-test.properties");
  
  @Rule
  public PersistentDataResource persistentData =
     new HibernatePersistentDataResource(entityManagerFactory);
  // CHECKSTYLE:ON
  
  private HtmlPage page;
  private HtmlTable table;
  private HtmlTableRow row;
  private HtmlAnchor control;
  private PropertiesAccessor properties;
  
  /**
   * Performs per-test setup tasks.
   * @throws Exception
   */
  @Before
  public void setUp() throws Exception {
    page = getHtmlPage(IndexController.INDEX_PATH);
    table = getFixtureTable(page);
    row = getFirstTableRow(table);
    control = getEditControl(row);    
    properties = PropertiesAccessor.newInstance("persistent-data.properties");
  }
  
  /**
   * Tests that when the remove confirmation dialog is canceled, the fixture
   * is not removed.
   * @throws Exception
   */
  @Test
  @TestResources(prefix = "sql/", before = "FixtureEditFT_before",
      after = "FixtureEditFT_after")
  public void testNotUpdatedWhenCanceled() throws Exception {    
    clickControlButtonAndWait();
    clickCancelButtonAndWait();
    HtmlTableRow topRow = getFirstTableRow(table);
    
    int index = 0;
    assertEquals(properties.getString("building.1.abbreviation") + " " 
        + properties.getString("room.1.roomNumber"), 
        getContents(topRow, index++));
    assertEquals(properties.getString("positionHint.1.hintText"), 
        getContents(topRow, index++));
    assertEquals(properties.getString("fixture.1.ipAddress"), 
        getContents(topRow, index++));
    assertEquals(properties.getString("asset.1.macAddress"), 
        getContents(topRow, index++));
    assertEquals(properties.getString("asset.1.inventoryNumber"), 
        getContents(topRow, index++));
  }
  
  /**
   * Tests that when the removal is confirmed, the fixture is removed.
   * @throws Exception
   */
  @Test
  @TestResources(prefix = "sql/", before = "FixtureEditFT_before", 
      after = "FixtureEditFT_after")
  public void testRowUpdatedWhenSubmitted() throws Exception {
    clickControlButtonAndWait();
    
    populateAutocompleteField(HTML_ID_BUILDING, 
        properties.getString("building.2.name").substring(0, 2));
    populateAutocompleteField(HTML_ID_ROOM, NEW_ROOM);
    populateAutocompleteField(HTML_ID_POSITION_HINT, NEW_POSITION_HINT);
    populateField(HTML_ID_IP_ADDRESS, NEW_IP_ADDRESS);
    
    clickSubmitButtonAndWait();
    
    int index = 0;
    HtmlTableRow topRow = getFirstTableRow(table);
    assertEquals(properties.getString("building.2.abbreviation") + " " 
        + NEW_ROOM, getContents(topRow, index++));
    assertEquals(NEW_POSITION_HINT, getContents(topRow, index++));
    assertEquals(NEW_IP_ADDRESS, getContents(topRow, index++));
    assertEquals(properties.getString("asset.1.macAddress"), 
        getContents(topRow, index++));
    assertEquals(properties.getString("asset.1.inventoryNumber"), 
        getContents(topRow, index++));
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
  
  private HtmlAnchor getEditControl(HtmlTableRow row) {
    HtmlAnchor control = (HtmlAnchor) row.getFirstByXPath(
        "//a[@data-control-function='edit']");
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
  
  private String getContents(HtmlTableRow row, int cellIndex) {
    return ((HtmlTableCell) row.getFirstByXPath("td[" + (cellIndex + 1) + "]"))
        .getTextContent();
  }
  
  private void populateAutocompleteField(String fieldContainerId, String value)
      throws Exception {
    HtmlInput input = ((HtmlInput) page
        .getFirstByXPath("//div[@id='" + fieldContainerId + "']//input"));
    input.setValueAttribute("");
    input.type(value);
    getClient().waitForBackgroundJavaScript(JS_LONG_DELAY);
    input.type(KeyboardEvent.DOM_VK_TAB);
    input.fireEvent(Event.TYPE_BLUR);
    getClient().waitForBackgroundJavaScript(JS_SHORT_DELAY);
  }
  
  private void populateField(String fieldContainerId, String value) 
      throws Exception {
    HtmlInput input = ((HtmlInput) page
        .getFirstByXPath("//div[@id='" + fieldContainerId + "']//input"));
    input.setValueAttribute("");
    input.type(value);
  }
  
}
