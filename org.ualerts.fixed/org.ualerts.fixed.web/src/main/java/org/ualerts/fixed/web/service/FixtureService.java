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

import java.util.List;

import org.ualerts.fixed.web.model.FixtureModel;

/**
 * A service object that helps in the standard create, update, retrieve, and
 * delete operations around Fixture objects.
 *
 * @author Michael Irwin
 * @author Carl Harris
 */
public interface FixtureService {
  
  /**
   * Save the provided fixture into persistence
   * 
   * @param fixture The fixture to be created
   * @throws Exception Any other internally-caused exception
   * @return A new model of the newly saved fixture
   */
  FixtureModel createFixture(FixtureModel fixture) throws Exception;
  
  /**
   * Finds a fixture using its persistent ID.
   * @param id ID of the subject fixture
   * @return matching fixture or {@code null} if there exists no such fixture
   * @throws Exception
   */
  FixtureModel findFixtureById(Long id) throws Exception;
  
  /**
   * Retrieve all Fixtures stored in persistence
   * @return A list of all Fixtures stored in persistence
   * @throws Exception
   */
  List<FixtureModel> retrieveAllFixtures() throws Exception;
  
  /**
   * Removes a persistent fixture.
   * @param id ID of the fixture to remove.
   * @return partial fixture model for the removed fixture (only the ID
   *    is guaranteed to be available in the model)
   * @throws Exception
   */
  FixtureModel removeFixture(Long id) throws Exception;
  
}
