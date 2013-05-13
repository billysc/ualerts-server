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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ualerts.fixed.web.controller.IndexController;

import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import edu.vt.cns.kestrel.common.IntegrationTestRunner;

/**
 * Functional tests for manual fixture enrollment (AFDP-10).
 *
 * @author ceharris
 */
@RunWith(IntegrationTestRunner.class)
public class ManualFixtureEnrollmentFT extends AbstractFunctionalTest {
  
  private static final String HTML_ID_GLOBAL_ERRORS = "globalErrorContainer";
  private static final String HTML_ID_IP_ADDRESS = "ipAddressContainer";
  private static final String HTML_ID_BUILDING = "buildingContainer";
  private static final String HTML_ID_FIXTURE_BUTTON = "addFixture";
  private static final String HTML_ID_INV_NUMBER = "inventoryNumberContainer";
  private static final String HTML_ID_MAC_ADDRESS = "macAddressContainer";
  private static final String HTML_ID_POSITION_HINT = "positionHintContainer";
  private static final String HTML_ID_ROOM_NUMBER = "roomContainer";
  private static final String HTML_ID_SERIAL_NUMBER = "serialNumberContainer";
  private static final String VALID_BUILDING = DBSetupUtility.BUILDING_NAME;
  private static final String VALID_INVENTORY_NUMBER = "INV-12345";
  private static final String VALID_MAC_ADDRESS = "0A:12:34:0B:56:78";
  private static final String VALID_POSITION_HINT = "TOP-RIGHT";
  private static final String VALID_ROOM_NUMBER = "123";
  private static final String VALID_SERIAL_NUMBER = "SER-12345";
  
  private static EntityManager entityManager;

  /**
   * One time setup that sets the EntityManager to be used
   * @throws Exception
   */
  @BeforeClass
  public static void oneTimeSetup() throws Exception {
    entityManager = PersistenceTestSupport
        .createEntityManagerFactory("etc/persistence-test.properties")
        .createEntityManager();
  }
  
  /**
   * One-time teardown that clears out the EntityManager
   * @throws Exception
   */
  @AfterClass
  public static void oneTimeTearDown() throws Exception {
    if (entityManager != null && entityManager.isOpen()) {
      entityManager.close();
    }
  }
  
  /**
   * Setup a transaction and populate the database for testing
   * @throws Exception
   */
  @Before
  public void setUp() throws Exception {
    DBSetupUtility.populateBuildings(entityManager);
  }
  
  /**
   * Rollback any changes that were performed
   * @throws Exception
   */
  @After
  public void tearDown() throws Exception {
    DBSetupUtility.cleanDatabase(entityManager);
  }
  
  /**
   * Verify the page loads and the Add Fixture button exists.
   * @throws Exception
   */
  @Test
  public void testGetManualEnrollmentForm() throws Exception {
    HtmlPage page = getHtmlPage(IndexController.INDEX_PATH);
    assertTrue(page.getTitleText().contains("Fixtures Dashboard"));
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
    populateField(page, HTML_ID_IP_ADDRESS, "260.0.0.0");
    populateField(page, HTML_ID_MAC_ADDRESS, "invalidMacAddress");
    clickSubmitButtonAndWait(page);
    assertFieldHasError(page, HTML_ID_IP_ADDRESS, "is required");
    assertFieldHasError(page, HTML_ID_MAC_ADDRESS, "is required");
  }
  
  /**
   * Verify that a non-existing building cannot be used
   * @throws Exception
   */
  @Test
  public void testValidateInvalidBuilding() throws Exception {
    HtmlPage page = getHtmlPage(IndexController.INDEX_PATH);
    openEnrollFixtureDialog(page);
    populateField(page, HTML_ID_BUILDING, "Unknown Building");
    clickSubmitButtonAndWait(page);
    assertFieldHasError(page, HTML_ID_BUILDING, "does not exist");
  }
  
  /**
   * Test a valid form submission and that the modal form disappears
   * @throws Exception
   */
  @Test
  public void testValidSubmission() throws Exception {
    HtmlPage page = getHtmlPage(IndexController.INDEX_PATH);
    submitValidForm(page);
    assertNoErrors(page);
    assertFalse(getModalBody(page).isDisplayed());
  }
  
  /**
   * After submitting a valid form, validate that certain fields cannot be use
   * the same value again.
   * @throws Exception
   */
  @Test
  public void testDuplicatingFields() throws Exception {
    HtmlPage page = getHtmlPage(IndexController.INDEX_PATH);
    submitValidForm(page);
    assertNoErrors(page);
    openEnrollFixtureDialog(page);
    populateField(page, HTML_ID_MAC_ADDRESS, VALID_MAC_ADDRESS);
    populateField(page, HTML_ID_SERIAL_NUMBER, VALID_SERIAL_NUMBER);
    populateField(page, HTML_ID_INV_NUMBER, VALID_INVENTORY_NUMBER);
    populateField(page, HTML_ID_BUILDING, VALID_BUILDING);
    populateField(page, HTML_ID_ROOM_NUMBER, VALID_ROOM_NUMBER);
    populateField(page, HTML_ID_POSITION_HINT, VALID_POSITION_HINT);
    clickSubmitButtonAndWait(page);
    assertFieldHasError(page, HTML_ID_MAC_ADDRESS, "already in use");
    assertFieldHasError(page, HTML_ID_SERIAL_NUMBER, "already in use");
    assertFieldHasError(page, HTML_ID_INV_NUMBER, "already in use");
    assertFieldHasError(page, HTML_ID_MAC_ADDRESS, "already in use");
    
    assertTrue(((HtmlDivision) page.getFirstByXPath("//div[@id='" 
        + HTML_ID_GLOBAL_ERRORS + "']" + "/div")).getTextContent()
        .contains("combination is already in use"));
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
  
  private void populateField(HtmlPage page, String fieldId, String value) 
      throws Exception {
    ((HtmlInput) page.getFirstByXPath("//div[@id='" + fieldId + "']//input"))
      .setValueAttribute(value);
  }
  
  private void submitValidForm(HtmlPage page) throws Exception {
    openEnrollFixtureDialog(page);
    populateField(page, "ipAddressContainer", "192.168.1.1");
    populateField(page, "macAddressContainer", VALID_MAC_ADDRESS);
    populateField(page, "serialNumberContainer", VALID_SERIAL_NUMBER);
    populateField(page, "inventoryNumberContainer", VALID_INVENTORY_NUMBER);
    populateField(page, "buildingContainer", VALID_BUILDING);
    populateField(page, "roomContainer", VALID_ROOM_NUMBER);
    populateField(page, "positionHintContainer", VALID_POSITION_HINT);
    clickSubmitButtonAndWait(page);
  }
  
}
