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

import javax.annotation.Resource;

import org.springframework.util.Assert;
import org.ualerts.fixed.Fixture;
import org.ualerts.fixed.repository.FixtureRepository;
import org.ualerts.fixed.service.CommandComponent;

/**
 * Command to delete a specific fixture from the UAlerts system.
 *
 * @author Brian Early
 */
@CommandComponent
public class DeleteFixtureCommand extends AbstractCommand<Void> {
  private Long id;
  private FixtureRepository fixtureRepository;

  /**
   * {@inheritDoc}
   */
  @Override
  protected void onValidate() throws Exception {
    super.onValidate();
    Assert.notNull(getId(), "id is required");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Void onExecute() throws Exception {
    Fixture fixture = fixtureRepository.findFixtureById(getId());
    fixtureRepository.deleteFixture(fixture);
    return null;
  }

  /**
   * Gets the {@code id} property.
   * @return property value
   */
  public Long getId() {
    return id;
  }

  /**
   * Sets the {@code id} property.
   * @param id the value to set
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Sets the {@code repository} property.
   * @param repository the fixed repository
   */
  @Resource
  public void setFixtureRepository(FixtureRepository repository) {
    this.fixtureRepository = repository;
  }

}
