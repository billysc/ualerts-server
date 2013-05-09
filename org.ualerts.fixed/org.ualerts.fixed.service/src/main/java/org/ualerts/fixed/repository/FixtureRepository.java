/*
 * File created on May 1, 2013 
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
package org.ualerts.fixed.repository;

import java.util.List;

import org.ualerts.fixed.Fixture;

/**
 * Defines basic behavior for the repository supporting fixtures.
 *
 * @author Brian Early
 */
public interface FixtureRepository {
  
  /**
   * Finds all fixtures currently in the repository.
   * @return a list of all fixtures.  May be empty/null.
   */
  List<Fixture> findAllFixtures();
  
  /**
   * Finds a fixture by its location (room & position hint)
   * @param roomId the ID of the room
   * @param hintId the ID of the position hint
   * @return the Fixture object.  Null if it isn't found.
   */
  Fixture findFixtureByLocation(Long roomId, Long hintId);
  
  /**
   * Finds a fixture by its ID.
   * @param id the ID of the fixture
   * @return the Fixture object.
   * @throws EntityNotFoundException if the fixture can't be found.
   */
  Fixture findFixtureById(Long id) throws EntityNotFoundException;
  
  /**
   * Adds a fixture to persistence control.  Assumes that any linked entities
   * have already been persisted.
   * @param fixture the fixture to add.
   */
  void addFixture(Fixture fixture);
}
