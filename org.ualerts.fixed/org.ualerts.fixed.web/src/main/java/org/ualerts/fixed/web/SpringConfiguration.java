/*
 * File created on May 6, 2013
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

package org.ualerts.fixed.web;

import static org.ualerts.fixed.service.errors.ErrorCodes.INVENTORY_NUMBER_CONFLICT;
import static org.ualerts.fixed.service.errors.ErrorCodes.MAC_ADDRESS_CONFLICT;
import static org.ualerts.fixed.service.errors.ErrorCodes.MISSING_BUILDING_FIELD;
import static org.ualerts.fixed.service.errors.ErrorCodes.MISSING_INET_ADDRESS_FIELD;
import static org.ualerts.fixed.service.errors.ErrorCodes.MISSING_INVENTORY_NUMBER_FIELD;
import static org.ualerts.fixed.service.errors.ErrorCodes.MISSING_MAC_ADDRESS_FIELD;
import static org.ualerts.fixed.service.errors.ErrorCodes.MISSING_POSITION_HINT_FIELD;
import static org.ualerts.fixed.service.errors.ErrorCodes.MISSING_ROOM_FIELD;
import static org.ualerts.fixed.service.errors.ErrorCodes.MISSING_SERIAL_NUMBER_FIELD;
import static org.ualerts.fixed.service.errors.ErrorCodes.SERIAL_NUMBER_CONFLICT;
import static org.ualerts.fixed.service.errors.ErrorCodes.UNKNOWN_BUILDING;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.ualerts.fixed.web.model.FixtureModel;

/**
 * A configuration object to perform various beans.
 * 
 * @author Michael Irwin
 */
@Configuration
public class SpringConfiguration {

  /**
   * Generates the error code to field mapping used in error handling when
   * errors are thrown from the service layer
   * @return The mapping of error codes to field name.
   */
  @Bean
  public Map<String, String> errorFieldMapping() {
    Map<String, String> map = new HashMap<String, String>();
    map.put(INVENTORY_NUMBER_CONFLICT, FixtureModel.FIELD_INV_NUMBER);
    map.put(MAC_ADDRESS_CONFLICT, FixtureModel.FIELD_MAC_ADDRESS);
    map.put(MISSING_BUILDING_FIELD, FixtureModel.FIELD_BUILDING);
    map.put(MISSING_INET_ADDRESS_FIELD, FixtureModel.FIELD_IP_ADDRESS);
    map.put(MISSING_INVENTORY_NUMBER_FIELD, FixtureModel.FIELD_INV_NUMBER);
    map.put(MISSING_MAC_ADDRESS_FIELD, FixtureModel.FIELD_MAC_ADDRESS);
    map.put(MISSING_POSITION_HINT_FIELD, FixtureModel.FIELD_POSITION_HINT);
    map.put(MISSING_ROOM_FIELD, FixtureModel.FIELD_ROOM);
    map.put(MISSING_SERIAL_NUMBER_FIELD, FixtureModel.FIELD_SER_NUMBER);
    map.put(SERIAL_NUMBER_CONFLICT, FixtureModel.FIELD_SER_NUMBER);
    map.put(UNKNOWN_BUILDING, FixtureModel.FIELD_BUILDING);
    return map;
  }

}
