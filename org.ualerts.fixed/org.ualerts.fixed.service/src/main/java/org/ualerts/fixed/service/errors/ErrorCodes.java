/*
 * File created on May 3, 2013
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

package org.ualerts.fixed.service.errors;

import org.ualerts.fixed.InetAddress;
import org.ualerts.fixed.MacAddress;

/**
 * Defines all error codes defined for the service layer.
 *
 * @author Brian Early
 */
public interface ErrorCodes {

  /**
   * Preamble for all error codes.
   */
  String CODE_BASE = "org.ualerts.";
  
  /**
   * Error code representing a duplicate inventory number conflict
   * (number already in use).
   */
  String INVENTORY_NUMBER_CONFLICT = CODE_BASE + "inventoryNumber.conflict";
  
  /**
   * Error code representing a location conflict (i.e. room and
   * position hint combination already in use).
   */
  String LOCATION_CONFLICT = CODE_BASE + "location.conflict";
  
  /**
   * Error code representing a MAC address conflict (i.e.
   * (MAC address already in use).
   */
  String MAC_ADDRESS_CONFLICT = CODE_BASE + "macAddress.conflict";

  /**
   * Error code representing a serial number conflict (number
   * already in use).
   */
  String SERIAL_NUMBER_CONFLICT = CODE_BASE + "serialNumber.conflict";
  
  /**
   * Error code representing an unknown building (i.e. specified
   * building doesn't map to an actual building entity).
   */
  String UNKNOWN_BUILDING = CODE_BASE + "building.unknown";
  
  /**
   * Error code representing an unknown constraint error, typically
   * because of a runtime error.
   */
  String UNSPECIFIED_CONSTRAINT = CODE_BASE + "constraintError";
  
  /**
   * Error code representing a missing room field.
   */
  String MISSING_ROOM_FIELD = CODE_BASE + "missingField.room";
  
  /**
   * Error code representing a missing building field.
   */
  String MISSING_BUILDING_FIELD = CODE_BASE + "missingField.building";
  
  /**
   * Error code representing a missing position hint field.
   */
  String MISSING_POSITION_HINT_FIELD = CODE_BASE + "missingField.positionHint";
  
  /**
   * Error code representing a missing INET address field.
   */
  String MISSING_INET_ADDRESS_FIELD = CODE_BASE + "missingField.inetAddress";
  
  /**
   * Error code representing a missing serial number field.
   */
  String MISSING_SERIAL_NUMBER_FIELD = CODE_BASE + "missingField.serialNumber";
  
  /**
   * Error code representing a missing inventory number field.
   */
  String MISSING_INVENTORY_NUMBER_FIELD = CODE_BASE +
      "missingField.inventoryNumber";
  
  /**
   * Error code representing a missing MAC address field.
   */
  String MISSING_MAC_ADDRESS_FIELD = CODE_BASE + "missingField.macAddress";

}
