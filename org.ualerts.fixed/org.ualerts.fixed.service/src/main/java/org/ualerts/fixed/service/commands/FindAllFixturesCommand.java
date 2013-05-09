/*
 * File created on May 8, 2013 
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
package org.ualerts.fixed.service.commands;

import java.util.List;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.ualerts.fixed.Fixture;
import org.ualerts.fixed.repository.FixtureRepository;
import org.ualerts.fixed.service.CommandComponent;
import org.ualerts.fixed.service.errors.UnspecifiedConstraintException;

/**
 * Command to find all fixtures in the UAlerts system.
 *
 * @author Brian Early
 */
@CommandComponent
public class FindAllFixturesCommand extends AbstractCommand<List<Fixture>> {
  private FixtureRepository repository;

  /**
   * {@inheritDoc}
   */
  @Override
  protected List<Fixture> onExecute() throws UnspecifiedConstraintException {
    try {
      return repository.findAllFixtures();
    }
    catch (PersistenceException ex) {
      throw new UnspecifiedConstraintException(ex);
    }
  }

  /**
   * Sets the {@code repository} property.
   * @param repository the fixed repository
   */
  @Autowired
  public void setFixtureRepository(FixtureRepository repository) {
    this.repository = repository;
  }

}
