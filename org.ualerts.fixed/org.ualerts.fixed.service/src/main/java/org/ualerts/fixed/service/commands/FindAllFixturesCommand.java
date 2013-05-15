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

import javax.annotation.Resource;

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
  private FixtureRepository fixtureRepository;

  /**
   * {@inheritDoc}
   */
  @Override
  protected List<Fixture> onExecute() throws UnspecifiedConstraintException {
    return fixtureRepository.findAllFixtures();
  }

  /**
   * Sets the {@code fixtureRepository} property.
   * @param fixtureRepository the fixed repository
   */
  @Resource
  public void setFixtureRepository(FixtureRepository fixtureRepository) {
    this.fixtureRepository = fixtureRepository;
  }

}
