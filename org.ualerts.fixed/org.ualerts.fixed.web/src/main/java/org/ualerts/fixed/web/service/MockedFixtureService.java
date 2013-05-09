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

import org.springframework.validation.BindException;
import org.ualerts.fixed.service.Command;
import org.ualerts.fixed.service.commands.AddFixtureCommand;
import org.ualerts.fixed.web.dto.FixtureDTO;

/**
 * A mocked implementation of the {@link FixtureService} interface.
 *
 * @author Michael Irwin
 */
public class MockedFixtureService implements FixtureService {

  private Long lastUsedId;
  
  /**
   * Constructs a new instance of the mocked service.
   */
  public MockedFixtureService() {
    lastUsedId = 1L;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  @SuppressWarnings("rawtypes")
  public <T extends Command> T newCommand(Class<T> commandClass) 
      throws Exception {
    return commandClass.newInstance();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public FixtureDTO createFixture(AddFixtureCommand command)
      throws BindException, Exception {
    FixtureDTO fixture = new FixtureDTO();
    fixture.setBuilding(command.getBuildingName());
    fixture.setId(lastUsedId++);
    fixture.setInventoryNumber(command.getInventoryNumber());
    fixture.setIpAddress(command.getInetAddress().toString());
    fixture.setMacAddress(command.getMacAddress().toString());
    fixture.setPositionHint(command.getPositionHint());
    fixture.setRoom(command.getRoomNumber());
    fixture.setSerialNumber(command.getSerialNumber());
    fixture.setVersion(1L);
    return fixture;
  }
  
}