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

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindException;
import org.ualerts.fixed.Fixture;
import org.ualerts.fixed.service.Command;
import org.ualerts.fixed.service.CommandService;
import org.ualerts.fixed.service.commands.AddFixtureCommand;
import org.ualerts.fixed.service.commands.FindAllFixturesCommand;
import org.ualerts.fixed.web.model.FixtureModel;

/**
 * An implementation of the {@link FixtureService} class that uses commands from
 * the {@code org.ualerts.service} module to complete the various tasks.
 * 
 * @author Michael Irwin
 */
@Service
public class ServiceSupportedFixtureService implements FixtureService {

  private CommandService commandService;

  /**
   * {@inheritDoc}
   */
  @Override
  @SuppressWarnings("rawtypes")
  public <T extends Command> T newCommand(Class<T> clazz) throws Exception {
    return commandService.newCommand(clazz);
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public FixtureModel createFixture(AddFixtureCommand command)
      throws BindException, Exception {
    Fixture fixture = commandService.invoke(command);
    return new FixtureModel(fixture);
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public List<FixtureModel> retrieveAllFixtures() throws Exception {
    FindAllFixturesCommand command = newCommand(FindAllFixturesCommand.class);
    List<FixtureModel> fixtures = new ArrayList<FixtureModel>();
    for (Fixture fixture : commandService.invoke(command)) {
      fixtures.add(new FixtureModel(fixture));
    }
    return fixtures;
  }
  
  /**
   * Sets the {@code commandService} property.
   * @param commandService the value to set
   */
  @Resource
  public void setCommandService(CommandService commandService) {
    this.commandService = commandService;
  }

}
