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

import org.springframework.validation.BindException;
import org.ualerts.fixed.service.Command;
import org.ualerts.fixed.service.commands.AddFixtureCommand;
import org.ualerts.fixed.web.model.FixtureModel;

/**
 * A service object that helps in the standard create, update, retrieve, and
 * delete operations around Fixture objects.
 *
 * @author Michael Irwin
 */
public interface FixtureService {
  
  /**
   * Execute the provided command and return its newly created Fixture.
   * 
   * @param command The command and model to be executed
   * @throws BindException An validation errors that occur
   * @throws Exception Any other internally-caused exception
   */
  FixtureModel createFixture(AddFixtureCommand command) 
      throws BindException, Exception;
  
  /**
   * Retrieve all Fixtures stored in persistence
   * @return A list of all Fixtures stored in persistence
   * @throws Exception
   */
  List<FixtureModel> retrieveAllFixtures() throws Exception;
  
}
