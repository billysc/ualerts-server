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

package org.ualerts.fixed.service.api;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.ualerts.fixed.Fixture;
import org.ualerts.fixed.InetAddress;
import org.ualerts.fixed.MacAddress;
import org.ualerts.fixed.service.CommandService;
import org.ualerts.fixed.service.api.model.FixtureModel;
import org.ualerts.fixed.service.commands.AddFixtureCommand;
import org.ualerts.fixed.service.commands.DeleteFixtureCommand;
import org.ualerts.fixed.service.commands.FindAllFixturesCommand;
import org.ualerts.fixed.service.commands.FindFixtureCommand;
import org.ualerts.fixed.service.commands.UpdateFixtureCommand;

/**
 * An implementation of the {@link FixtureService} class that uses commands from
 * the {@code org.ualerts.service} module to complete the various tasks.
 * 
 * @author Michael Irwin
 * @author Carl Harris
 */
@Service
public class CommandSupportedFixtureService implements FixtureService {

  private CommandService commandService;
  
  /**
   * {@inheritDoc}
   */
  @Override
  public FixtureModel createFixture(FixtureModel fixture) throws Exception {
    AddFixtureCommand command = 
        commandService.newCommand(AddFixtureCommand.class);
    command.setBuildingId(fixture.getBuildingId());
    command.setInetAddress(InetAddress.getByAddress(fixture.getIpAddress()));
    command.setInventoryNumber(fixture.getInventoryNumber());
    command.setMacAddress(new MacAddress(fixture.getMacAddress()));
    command.setPositionHint(fixture.getPositionHint());
    command.setRoomNumber(fixture.getRoom());
    command.setSerialNumber(fixture.getSerialNumber());
    Fixture newFixture = commandService.invoke(command);
    return new FixtureModel(newFixture);
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public FixtureModel findFixtureById(Long id) throws Exception {
    FindFixtureCommand command = commandService.newCommand(
        FindFixtureCommand.class);
    command.setId(id);
    return new FixtureModel(commandService.invoke(command));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<FixtureModel> retrieveAllFixtures() throws Exception {
    FindAllFixturesCommand command = 
        commandService.newCommand(FindAllFixturesCommand.class);
    List<FixtureModel> fixtures = new ArrayList<FixtureModel>();
    for (Fixture fixture : commandService.invoke(command)) {
      fixtures.add(new FixtureModel(fixture));
    }
    return fixtures;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public FixtureModel removeFixture(Long id) throws Exception {
    DeleteFixtureCommand command = commandService.newCommand(
        DeleteFixtureCommand.class);
    command.setId(id);
    commandService.invoke(command);
    FixtureModel fixture = new FixtureModel();
    fixture.setId(id);
    return fixture;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public FixtureModel updateFixture(FixtureModel fixture) throws Exception {
    UpdateFixtureCommand command = 
        commandService.newCommand(UpdateFixtureCommand.class);
    command.setBuildingName(fixture.getBuilding());
    command.setId(fixture.getId());
    command.setInetAddress(InetAddress.getByName(fixture.getIpAddress()));
    command.setPositionHint(fixture.getPositionHint());
    command.setRoomNumber(fixture.getRoom());
    Fixture updatedFixture = commandService.invoke(command);
    return new FixtureModel(updatedFixture);
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
