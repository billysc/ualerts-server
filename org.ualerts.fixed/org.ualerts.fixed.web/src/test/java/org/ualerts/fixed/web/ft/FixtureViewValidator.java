/*
 * File created on May 14, 2013
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

import com.gargoylesoftware.htmlunit.html.HtmlTableCell;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;

/**
 * Validation utility to support functional tests involving the fixtures
 * view.
 *
 * @author ceharris
 */
public final class FixtureViewValidator {

  private final PropertiesAccessor properties;
  
  /**
   * Constructs a new instance.
   * @param properties properties that represent expected values in 
   */
  public FixtureViewValidator(PropertiesAccessor properties) {
    this.properties = properties;
  }
  
  /**
   * Validates that a row from the fixtures table view matches the
   * fixture defined as {@code fixture.1} in the properties file.
   * @param row the subject row to test
   * @throws Exception
   */
  public void validateIsFixture1(HtmlTableRow row) throws Exception {
    validateRowDisplay(properties.getString("building.1.abbreviation") 
        + " " + properties.getString("room.1.roomNumber"), 
        properties.getString("positionHint.1.hintText"),
        properties.getString("fixture.1.ipAddress"), 
        properties.getString("asset.1.macAddress"),
        properties.getString("asset.1.inventoryNumber"),
        row);
  }

  /**
   * Validates that a row from the fixtures table view matches the
   * fixture defined as {@code fixture.2} in the properties file.
   * @param row the subject row to test
   * @throws Exception
   */
  public void validateIsFixture2(HtmlTableRow row) throws Exception {
    validateRowDisplay(properties.getString("building.1.abbreviation") 
        + " " + properties.getString("room.2.roomNumber"),  
        properties.getString("positionHint.2.hintText"),
        properties.getString("fixture.2.ipAddress"), 
        properties.getString("asset.2.macAddress"),
        properties.getString("asset.2.inventoryNumber"),
        row);
  }

  /**
   * Validates that a reow from the fixtures table view matches the
   * given property values.
   * @param expectedLocation the expected location string
   * @param expectedPositionHint the expected position hint string
   * @param expectedIpAddress the expected IP address string
   * @param expectedMacAddress the expected MAC address string
   * @param expectedInventoryNumber the expected inventory number
   * @param row the subject row to test
   * @throws Exception
   */
  public void validateRowDisplay(String expectedLocation, 
      String expectedPositionHint, String expectedIpAddress, 
      String expectedMacAddress, String expectedInventoryNumber, 
      HtmlTableRow row) throws Exception {
    int index = 0;
    assertEquals(expectedLocation, 
        getCell(row, index++).getTextContent());
    assertEquals(expectedPositionHint, 
        getCell(row, index++).getTextContent());
    assertEquals(expectedIpAddress, 
        getCell(row, index++).getTextContent());
    assertEquals(expectedMacAddress, 
        getCell(row, index++).getTextContent());
    assertEquals(expectedInventoryNumber, 
        getCell(row, index++).getTextContent());
  }

  /**
   * Retrieve a cell from a row
   * @param row The row to retrieve the cell for
   * @param index The index of the row, 0-based
   * @return
   */
  private HtmlTableCell getCell(HtmlTableRow row, int index) {
    return (HtmlTableCell) row.getFirstByXPath("td[" + (index + 1) + "]");
  }
  

}
