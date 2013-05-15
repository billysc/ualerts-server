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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

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
import org.ualerts.fixed.service.commands.FindAllFixturesCommand;
import org.ualerts.fixed.web.model.FixtureModel;

/**
 * Test case for the {@link ServiceSupportedFixtureService} class.
 *
 * @author Michael Irwin
 */
public class ServiceSupportedFixtureServiceTest {

  private Mockery context;
  private FixtureModel fixture;
  private CommandService commandService;
  private ServiceSupportedFixtureService service;
  
  /**
   * Setup for each test
   */
  @Before
  public void setup() {
    context = new Mockery();
    fixture = new FixtureModel();
    commandService = context.mock(CommandService.class);
    service = new ServiceSupportedFixtureService();
    service.setCommandService(commandService);
    
    fixture.setBuilding("A Building");
    fixture.setInventoryNumber("INV-234567890");
    fixture.setIpAddress("192.168.1.1");
    fixture.setMacAddress("0A-12-34-0B-56-78");
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
    
    final AddFixtureCommand command = new AddFixtureCommand();
    
    context.checking(new Expectations() { {
      oneOf(commandService).newCommand(AddFixtureCommand.class);
      will(returnValue(command));
      oneOf(commandService).invoke(command);
      will(returnValue(fixtureObj));
    } });
    
    FixtureModel returnedFixture = service.createFixture(fixture);
    
    context.assertIsSatisfied();
    assertEquals(fixtureObj.getId(), returnedFixture.getId());
    assertEquals(fixtureObj.getVersion(), returnedFixture.getVersion());
    assertEquals(fixture.getBuilding(), command.getBuildingName());
    assertEquals(fixture.getInventoryNumber(), command.getInventoryNumber());
    assertEquals(fixture.getIpAddress(), 
        command.getInetAddress().getHostAddress());
    assertEquals(fixture.getMacAddress(), command.getMacAddress().toString());
    assertEquals(fixture.getPositionHint(), command.getPositionHint());
    assertEquals(fixture.getRoom(), command.getRoomNumber());
    assertEquals(fixture.getSerialNumber(), command.getSerialNumber());
  }
  
  /**
   * Validate the retrival of all fixtures.
   * @throws Exception
   */
  @Test
  public void validateRetrievalAllFixtures() throws Exception {
    final FindAllFixturesCommand command = new FindAllFixturesCommand();
    final List<Fixture> fixtures = new ArrayList<Fixture>();
    
    context.checking(new Expectations() { { 
      oneOf(commandService).newCommand(FindAllFixturesCommand.class);
      will(returnValue(command));
      oneOf(commandService).invoke(command);
      will(returnValue(fixtures));
    } });
    
    List<FixtureModel> fixtureDtos = service.retrieveAllFixtures();
    context.assertIsSatisfied();
    assertNotNull(fixtureDtos);
    assertEquals(fixtures.size(), fixtureDtos.size());
  }
  
}
