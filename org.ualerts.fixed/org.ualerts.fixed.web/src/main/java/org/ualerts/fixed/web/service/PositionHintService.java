/*
 * File created on May 17, 2013
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

package org.ualerts.fixed.web.service;

import org.ualerts.fixed.web.model.PositionHintsModel;

/**
 * A service to interact with Position Hints.
 *
 * @author Michael Irwin
 */
public interface PositionHintService {

  /**
   * Retrieve all position hints
   * @return All of the known position hints
   * @throws Exception Any exception that can occur
   */
  PositionHintsModel getAllPositionHints() throws Exception;
  
}
