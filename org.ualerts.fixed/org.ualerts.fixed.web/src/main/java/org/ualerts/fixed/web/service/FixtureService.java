/*
 * File created on May 2, 2013 
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

import org.ualerts.fixed.service.errors.ValidationErrorCollection;
import org.ualerts.fixed.web.dto.FixtureDTO;

/**
 * A service object that helps in the standard create, update, retrieve, and
 * delete operations around Fixture objects.
 *
 * @author Michael Irwin
 */
public interface FixtureService {

  /**
   * Save the provided Fixture.
   * 
   * Upon saving of the Fixture, the {@code id} and {@code version} properties 
   * of the Fixture will be updated to reflect their new values.
   * @param fixture The fixture to be saved.
   * @throws ValidationErrorCollection An validation errors that occur
   * @throws Exception Any other internally-caused exception
   */
  void createFixture(FixtureDTO fixture) 
      throws ValidationErrorCollection, Exception;
  
}
