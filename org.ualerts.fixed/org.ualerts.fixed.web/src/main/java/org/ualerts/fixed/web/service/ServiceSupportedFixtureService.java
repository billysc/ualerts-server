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

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.ualerts.fixed.Fixture;
import org.ualerts.fixed.service.CommandService;
import org.ualerts.fixed.service.commands.AddFixtureCommand;
import org.ualerts.fixed.service.errors.ValidationErrors;
import org.ualerts.fixed.web.dto.FixtureDTO;

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
  public void createFixture(FixtureDTO fixture)
      throws ValidationErrors, Exception {

    AddFixtureCommand command =
        commandService.newCommand(AddFixtureCommand.class);
    command.setBuildingName(fixture.getBuilding());
    command.setInetAddress(fixture.getIpAddressObj());
    command.setInventoryNumber(fixture.getInventoryNumber());
    command.setMacAddress(fixture.getMacAddressObj());
    command.setPositionHint(fixture.getPositionHint());
    command.setRoomNumber(fixture.getRoom());
    command.setSerialNumber(fixture.getSerialNumber());
    
    Fixture newFixture = commandService.invoke(command);
    
    fixture.setId(newFixture.getId());
    fixture.setVersion(newFixture.getVersion());
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
