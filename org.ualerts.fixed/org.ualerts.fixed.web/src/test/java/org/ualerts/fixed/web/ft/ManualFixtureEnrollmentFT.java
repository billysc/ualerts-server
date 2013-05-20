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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ualerts.fixed.web.controller.fixture.IndexController;
import org.ualerts.testing.jpa.EntityManagerFactoryResource;
import org.ualerts.testing.jpa.HibernatePersistentDataResource;
import org.ualerts.testing.jpa.PersistentDataResource;
import org.ualerts.testing.jpa.TestResources;

import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlHiddenInput;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlListItem;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;
import com.gargoylesoftware.htmlunit.html.HtmlUnorderedList;
import com.gargoylesoftware.htmlunit.javascript.host.Event;
import com.gargoylesoftware.htmlunit.javascript.host.KeyboardEvent;

import edu.vt.cns.kestrel.common.IntegrationTestRunner;

/**
 * Functional tests for manual fixture enrollment
 *
 * @author ceharris
 */
@RunWith(IntegrationTestRunner.class)
public class ManualFixtureEnrollmentFT extends AbstractFunctionalTest {
  
  private static final Logger logger = 
      LoggerFactory.getLogger(ManualFixtureEnrollmentFT.class);
  
  private static final String HTML_ID_GLOBAL_ERRORS = "globalErrorContainer";
  private static final String HTML_ID_IP_ADDRESS = "ipAddressContainer";
  private static final String HTML_ID_BUILDING = "buildingContainer";
  private static final String HTML_ID_BUILDING_ID = "buildingId";
  private static final String HTML_ID_FIXTURE_BUTTON = "addFixture";
  private static final String HTML_ID_INV_NUMBER = "inventoryNumberContainer";
  private static final String HTML_ID_MAC_ADDRESS = "macAddressContainer";
  private static final String HTML_ID_POSITION_HINT = "positionHintContainer";
  private static final String HTML_ID_ROOM_NUMBER = "roomContainer";
  private static final String HTML_ID_SERIAL_NUMBER = "serialNumberContainer";
  private static final String VALID_INVENTORY_NUMBER = "INV-12345";
  private static final String VALID_IP_ADDRESS = "192.168.1.1";
  private static final String VALID_MAC_ADDRESS = "0A-12-34-0B-56-78";
  private static final String VALID_SERIAL_NUMBER = "SER-12345";
  private static final String INVALID_IP_ADDRESS = "260.0.0.0";
  private static final String INVALID_MAC_ADDRESS = "NOT_A_VALID_MAC_ADDRESS";
  
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
   * Performs one-time setup.
   * @throws Exception
   */
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    properties = PropertiesAccessor.newInstance("persistent-data.properties");
    validator = new FixtureViewValidator(properties);
  }
  
  /**
   * Verify the page loads and the Add Fixture button exists.
   * @throws Exception
   */
  @Test
  public void testGetManualEnrollmentForm() throws Exception {
    HtmlPage page = getHtmlPage(IndexController.INDEX_PATH);
    assertTrue(page.getTitleText().contains("Enrolled Fixtures"));
    assertNotNull(page.getHtmlElementById(HTML_ID_FIXTURE_BUTTON));
  }
  
  /**
   * Ensure that if the form is submitted with no values, empty errors appear
   * on the fields expected
   * @throws Exception
   */
  @Test
  public void testValidateEmptyFields() throws Exception {
    HtmlPage page = getHtmlPage(IndexController.INDEX_PATH);
    openEnrollFixtureDialog(page);
    HtmlDivision modalBody = getModalBody(page);
    assertTrue(modalBody.hasChildNodes());
    
    clickSubmitButtonAndWait(page);
    assertFieldHasError(page, HTML_ID_IP_ADDRESS, "is required");
    assertFieldHasError(page, HTML_ID_MAC_ADDRESS, "is required");
    assertFieldHasError(page, HTML_ID_SERIAL_NUMBER, "is required");
    assertFieldHasError(page, HTML_ID_BUILDING, "is required");
    assertFieldHasError(page, HTML_ID_ROOM_NUMBER, "is required");
    assertEmpty(getErrorMessage(page, HTML_ID_INV_NUMBER));
  }
  
  /**
   * Verify that error messages appear if invalid IP and MAC addresses are used.
   * @throws Exception
   */
  @Test
  public void testValidateInvalidIpAndMacAddresses() throws Exception {
    HtmlPage page = getHtmlPage(IndexController.INDEX_PATH);
    openEnrollFixtureDialog(page);
    populateField(page, HTML_ID_IP_ADDRESS, INVALID_IP_ADDRESS);
    populateField(page, HTML_ID_MAC_ADDRESS, INVALID_MAC_ADDRESS);
    clickSubmitButtonAndWait(page);
    assertFieldHasError(page, HTML_ID_IP_ADDRESS, "provide a valid");
    assertFieldHasError(page, HTML_ID_MAC_ADDRESS, "provide a valid");
  }
  
  /**
   * Test a valid form submission and that the modal form disappears
   * @throws Exception
   */
  @Test
  @TestResources(prefix = "sql/", before = "ManualFixtureEnrollmentFT_before",
      after = "ManualFixtureEnrollmentFT_after")
  public void testValidSubmission() throws Exception {
    HtmlPage page = getHtmlPage(IndexController.INDEX_PATH);
    submitValidForm(page);
    assertNoErrors(page);
    assertFalse(getModalBody(page).isDisplayed());
    
    HtmlTableRow row = page
        .getFirstByXPath("//table[@id='fixturesList']/tbody/tr[1]");

    validator.validateIsFixture1(row);
  }
    
  /**
   * After submitting a valid form, validate that certain fields cannot be use
   * the same value again.
   * @throws Exception
   */
  @Test
  @TestResources(prefix = "sql/", before = "ManualFixtureEnrollmentFT_before",
      after = "ManualFixtureEnrollmentFT_after")
  public void testDuplicatingFields() throws Exception {
    HtmlPage page = getHtmlPage(IndexController.INDEX_PATH);
    submitValidForm(page);
    assertNoErrors(page);
    assertFalse(getModalBody(page).isDisplayed());
    
    // FIXME Bug in HtmlUnit doesn't fire CSS transition events, which bootstrap
    // relies on for callbacks. Basically, modal doesn't get destroyed and 
    // doesn't refetch content. Reloading the page resets everything and allows
    // test to pass. Shouldn't have to be this way.
    page = getHtmlPage(IndexController.INDEX_PATH);
    openEnrollFixtureDialog(page);

    populateField(page, HTML_ID_MAC_ADDRESS, VALID_MAC_ADDRESS);
    populateField(page, HTML_ID_SERIAL_NUMBER, VALID_SERIAL_NUMBER);
    populateField(page, HTML_ID_INV_NUMBER, VALID_INVENTORY_NUMBER);

    populateAutocompeteField(page, HTML_ID_BUILDING, 
        properties.getString("building.1.name"), true);

    populateAutocompeteField(page, HTML_ID_ROOM_NUMBER, 
        properties.getString("room.1.roomNumber"), true);
    
    HtmlInput positionHint = populateAutocompeteField(page, 
        HTML_ID_POSITION_HINT, properties.getString("positionHint.1.hintText"), 
        true);
    positionHint.fireEvent(Event.TYPE_BLUR);
    getClient().waitForBackgroundJavaScript(JS_SHORT_DELAY);
    
    clickSubmitButtonAndWait(page);
    
    assertFieldHasError(page, HTML_ID_MAC_ADDRESS, "already in use");
    assertFieldHasError(page, HTML_ID_SERIAL_NUMBER, "already in use");
    assertFieldHasError(page, HTML_ID_INV_NUMBER, "already in use");
    assertFieldHasError(page, HTML_ID_MAC_ADDRESS, "already in use");
    
    assertTrue(((HtmlDivision) page.getFirstByXPath("//div[@id='" 
        + HTML_ID_GLOBAL_ERRORS + "']" + "/div")).getTextContent()
        .contains("the same building, room, and position hint"));
  }
  
  /**
   * Validate that the building autocomplete works
   */
  @Test
  @TestResources(prefix = "sql/", before = "ManualFixtureEnrollmentFT_before",
      after = "ManualFixtureEnrollmentFT_after")
  public void testAutoCompletionOfBuildingFindsMatch() throws Exception {
    String buildingName = properties.getString("building.1.name");
    String buildingId = properties.getString("building.1.id");
    
    HtmlPage page = getHtmlPage(IndexController.INDEX_PATH);
    openEnrollFixtureDialog(page);
    
    // Type first two characters into input
    HtmlInput input = populateAutocompeteField(page, HTML_ID_BUILDING, 
        buildingName.substring(0, 2), false);
    
    // Validate that the building appears in autocomplete
    HtmlUnorderedList dropdown = getAutocompleteList(page, HTML_ID_BUILDING);
    assertTrue(dropdown.getChildElementCount() > 0);
    HtmlListItem item = dropdown.getFirstByXPath("li[1]");
    assertEquals(buildingName, item.getTextContent());
    
    // Select the building
    input.type(KeyboardEvent.DOM_VK_TAB);
    input.fireEvent(Event.TYPE_BLUR);
    getClient().waitForBackgroundJavaScriptStartingBefore(JS_SHORT_DELAY);
    
    // Validate that the field is populated with the building
    assertEquals(buildingName, input.getValueAttribute());
    
    // Validate that the id field is populated with the id of the building
    HtmlHiddenInput buildingIdElement = 
        page.getHtmlElementById(HTML_ID_BUILDING_ID);
    assertEquals(buildingId, buildingIdElement.getValueAttribute());
  }
  
  /**
   * Verify that a non-existing building cannot be used
   * @throws Exception
   */
  @Test
  public void testValidateInvalidBuilding() throws Exception {
    HtmlPage page = getHtmlPage(IndexController.INDEX_PATH);
    openEnrollFixtureDialog(page);
    
    // Type first two characters into input
    HtmlInput input = populateAutocompeteField(page, HTML_ID_BUILDING, "Z_Z_Z", 
        true);
    input.fireEvent(Event.TYPE_BLUR);
    getClient().waitForBackgroundJavaScript(JS_SHORT_DELAY);
    assertEmpty(input.getValueAttribute());
    
    //Validate that the hidden building id value is empty too
    HtmlHiddenInput buildingIdElement = 
        page.getHtmlElementById(HTML_ID_BUILDING_ID);
    assertEmpty(buildingIdElement.getValueAttribute());
  }
  
  /**
   * Validate that the auto-completion of position hints works
   * @throws Exception Any exception that can occur
   */
  @Test
  @TestResources(prefix = "sql/", before = "ManualFixtureEnrollmentFT_before",
      after = "ManualFixtureEnrollmentFT_after")
  public void testAutocompletionOfPositionHints() throws Exception {
    String hint = properties.getString("positionHint.1.hintText");
    
    HtmlPage page = getHtmlPage(IndexController.INDEX_PATH);
    openEnrollFixtureDialog(page);
    
    HtmlInput input = populateAutocompeteField(page, HTML_ID_POSITION_HINT, 
        hint.substring(0, 2), false);
    HtmlUnorderedList dropdown = 
        getAutocompleteList(page, HTML_ID_POSITION_HINT);
    assertTrue(dropdown.getChildElementCount() > 0);

    HtmlListItem element = dropdown.getFirstByXPath("li");
    assertEquals(hint, element.getTextContent());
    
    input.type(KeyboardEvent.DOM_VK_TAB);
    getClient().waitForBackgroundJavaScript(JS_SHORT_DELAY);
    assertEquals(hint, element.getTextContent());
  }
  
  /**
   * Validate that the auto-completion of position hints works
   * @throws Exception Any exception that can occur
   */
  @Test
  @TestResources(prefix = "sql/", before = "ManualFixtureEnrollmentFT_before",
      after = "ManualFixtureEnrollmentFT_after")
  public void testAutocompletionOfRooms() throws Exception {
    String room = properties.getString("room.1.roomNumber");
    
    HtmlPage page = getHtmlPage(IndexController.INDEX_PATH);
    openEnrollFixtureDialog(page);
    
    HtmlInput input = populateAutocompeteField(page, HTML_ID_ROOM_NUMBER, 
        room.substring(0, 2), false);
    HtmlUnorderedList dropdown = getAutocompleteList(page, HTML_ID_ROOM_NUMBER);
    assertTrue(dropdown.getChildElementCount() > 0);

    HtmlListItem element = dropdown.getFirstByXPath("li");
    assertEquals(room, element.getTextContent());
    
    input.type(KeyboardEvent.DOM_VK_TAB);
    getClient().waitForBackgroundJavaScript(JS_SHORT_DELAY);
    assertEquals(room, element.getTextContent());
  }
  
  private void submitValidForm(HtmlPage page) throws Exception {
    openEnrollFixtureDialog(page);
    populateField(page, HTML_ID_IP_ADDRESS, VALID_IP_ADDRESS);
    populateField(page, HTML_ID_MAC_ADDRESS, VALID_MAC_ADDRESS);
    populateField(page, HTML_ID_SERIAL_NUMBER, VALID_SERIAL_NUMBER);
    populateField(page, HTML_ID_INV_NUMBER, VALID_INVENTORY_NUMBER);

    populateAutocompeteField(page, HTML_ID_BUILDING, 
        properties.getString("building.1.name"), true);
    
    populateField(page, HTML_ID_ROOM_NUMBER, 
        properties.getString("room.1.roomNumber"));

    populateAutocompeteField(page, HTML_ID_POSITION_HINT, 
        properties.getString("positionHint.1.hintText"), true);
    
    clickSubmitButtonAndWait(page);
  }

  private void openEnrollFixtureDialog(HtmlPage page) throws Exception {
    HtmlAnchor fixtureButton = page.getHtmlElementById(HTML_ID_FIXTURE_BUTTON);
    fixtureButton.click();
    getClient().waitForBackgroundJavaScript(JS_LONG_DELAY);
  }
  
  private HtmlDivision getModalBody(HtmlPage page) {
    return (HtmlDivision) page.getFirstByXPath("//div[@id='modalBody']");
  }
  
  private void clickSubmitButtonAndWait(HtmlPage page) throws Exception {
    ((HtmlButton) page.getFirstByXPath("//button[@class='btn btn-primary']"))
      .click();
    getClient().waitForBackgroundJavaScript(JS_LONG_DELAY);
  }
  
  private void assertNoErrors(HtmlPage page) throws Exception {
    assertEmpty(getErrorMessage(page, HTML_ID_BUILDING));
    assertEmpty(getErrorMessage(page, HTML_ID_INV_NUMBER));
    assertEmpty(getErrorMessage(page, HTML_ID_IP_ADDRESS));
    assertEmpty(getErrorMessage(page, HTML_ID_MAC_ADDRESS));
    assertEmpty(getErrorMessage(page, HTML_ID_POSITION_HINT));
    assertEmpty(getErrorMessage(page, HTML_ID_ROOM_NUMBER));
    assertEmpty(getErrorMessage(page, HTML_ID_SERIAL_NUMBER));
  }
  
  private void assertFieldHasError(HtmlPage page, String fieldId, String msg) 
      throws Exception {
    assertTrue(getErrorMessage(page, fieldId).contains(msg));
  }
  
  private String getErrorMessage(HtmlPage page, String fieldId) 
      throws Exception {
    return ((HtmlDivision) page.getFirstByXPath("//div[@id='" + fieldId + "']" 
        + "/div[1]/div[@class='error']")).getTextContent();
  }
  
  private HtmlInput populateAutocompeteField(HtmlPage page, String fieldId, 
      String value, boolean selectField) throws Exception {
    HtmlInput input = getInputField(page, fieldId);
    input.type(value);
    getClient().waitForBackgroundJavaScript(JS_LONG_DELAY);
    if (selectField) {
      input.type(KeyboardEvent.DOM_VK_TAB);
      getClient().waitForBackgroundJavaScript(JS_SHORT_DELAY);
    }
    return input;
  }
  
  private void populateField(HtmlPage page, String fieldId, String value) 
      throws Exception {
    HtmlInput input = getInputField(page, fieldId);
    input.setValueAttribute("");
    input.type(value);
  }
  
  private HtmlInput getInputField(HtmlPage page, String fieldId) {
    return page.getFirstByXPath("//div[@id='" + fieldId + "']//input"); 
  }
  
  private HtmlUnorderedList getAutocompleteList(HtmlPage page, String fieldId) {
    return page.getFirstByXPath("//div[@id='" + fieldId + "']//"
    		+ "ul[@class='typeahead dropdown-menu']");
  }
  
}
