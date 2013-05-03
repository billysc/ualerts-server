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

import junit.framework.Assert;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;
import org.ualerts.fixed.Fixture;
import org.ualerts.fixed.InetAddress;
import org.ualerts.fixed.MacAddress;
import org.ualerts.fixed.service.CommandService;
import org.ualerts.fixed.service.commands.AddFixtureCommand;
import org.ualerts.fixed.web.dto.FixtureDTO;

/**
 * Test case for the {@link ServiceSupportedFixtureService} class.
 *
 * @author Michael Irwin
 */
public class ServiceSupportedFixtureServiceTest {

  private Mockery context;
  private FixtureDTO fixture;
  private AddFixtureCommand command;
  private CommandService commandService;
  private ServiceSupportedFixtureService service;
  
  /**
   * Setup for each test
   */
  @Before
  public void setup() {
    context = new Mockery();
    command = new AddFixtureCommand();
    commandService = context.mock(CommandService.class);
    service = new ServiceSupportedFixtureService();
    service.setCommandService(commandService);
    
    fixture = new FixtureDTO();
    fixture.setBuilding("A Building");
    fixture.setInventoryNumber("INV-234567890");
    fixture.setIpAddress("192.168.1.1");
    fixture.setIpAddressObj(InetAddress.getByAddress(fixture.getIpAddress()));
    fixture.setMacAddress("0A:12:34:0B:56:78");
    fixture.setMacAddressObj(new MacAddress(fixture.getMacAddress()));
    fixture.setPositionHint("TOP-RIGHT");
    fixture.setRoom("100");
    fixture.setSerialNumber("0ABCDEF123456");
  }
  
  /**
   * Validate that creation uses the command and sets the id and version 
   * attributes of the Fixture upon completion, to their expected values.
   * @throws Exception
   */
  @Test
  public void validateFixtureCreation() throws Exception {
    final Fixture fixtureObj = new Fixture();
    fixtureObj.setId(new Long(0));
    fixtureObj.setVersion(new Long(1));
    
    context.checking(new Expectations() { {
      oneOf(commandService).newCommand(with(AddFixtureCommand.class));
      will(returnValue(command));
      oneOf(commandService).invoke(with(command));
      will(returnValue(fixtureObj));
    } });
    
    service.createFixture(fixture);
    
    context.assertIsSatisfied();
    Assert.assertEquals(fixtureObj.getId(), fixture.getId());
    Assert.assertEquals(fixtureObj.getVersion(), fixture.getVersion());
  }
  
  
}
