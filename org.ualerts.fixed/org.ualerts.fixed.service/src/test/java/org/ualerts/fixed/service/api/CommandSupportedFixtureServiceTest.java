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

import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;
import org.ualerts.fixed.Fixture;
import org.ualerts.fixed.service.CommandService;
import org.ualerts.fixed.service.api.CommandSupportedFixtureService;
import org.ualerts.fixed.service.api.model.FixtureModel;
import org.ualerts.fixed.service.commands.AddFixtureCommand;
import org.ualerts.fixed.service.commands.DeleteFixtureCommand;
import org.ualerts.fixed.service.commands.FindAllFixturesCommand;
import org.ualerts.fixed.service.commands.FindFixtureCommand;
import org.ualerts.fixed.service.commands.UpdateFixtureCommand;

/**
 * Test case for the {@link CommandSupportedFixtureService} class.
 *
 * @author Michael Irwin
 */
public class CommandSupportedFixtureServiceTest {

  private Mockery context;
  private FixtureModel fixture;
  private CommandService commandService;
  private CommandSupportedFixtureService service;
  
  /**
   * Setup for each test
   */
  @Before
  public void setUp() {
    context = new Mockery();
    fixture = new FixtureModel();
    commandService = context.mock(CommandService.class);
    service = new CommandSupportedFixtureService();
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
  public void testCreateFixture() throws Exception {
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
    assertEquals(fixture.getBuildingId(), command.getBuildingId());
    assertEquals(fixture.getInventoryNumber(), command.getInventoryNumber());
    assertEquals(fixture.getIpAddress(), 
        command.getInetAddress().getHostAddress());
    assertEquals(fixture.getMacAddress(), command.getMacAddress().toString());
    assertEquals(fixture.getPositionHint(), command.getPositionHint());
    assertEquals(fixture.getRoom(), command.getRoomNumber());
    assertEquals(fixture.getSerialNumber(), command.getSerialNumber());
  }

  /**
   * Test for {@link CommandSupportedFixtureService#findFixtureById(Long)}.
   * @throws Exception
   */
  @Test
  public void testFindFixtureById() throws Exception {
    final Long id = -1L;
    final FindFixtureCommand command = new FindFixtureCommand();
    final Fixture fixture = new Fixture();
    fixture.setId(id);
    
    context.checking(new Expectations() { { 
      oneOf(commandService).newCommand(FindFixtureCommand.class);
      will(returnValue(command));
      oneOf(commandService).invoke(with(allOf(same(command),
          hasProperty("id", equalTo(id)))));
      will(returnValue(fixture));
    } });
    
    FixtureModel result = service.findFixtureById(id);
    context.assertIsSatisfied();
    assertEquals(id, result.getId());
  }
  
  /**
   * Validate the retrival of all fixtures.
   * @throws Exception
   */
  @Test
  public void testRetrievalAllFixtures() throws Exception {
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
  
  /**
   * Test {@link CommandSupportedFixtureService#removeFixture(FixtureModel)}.
   * @throws Exception
   */
  @Test
  public void testRemoveFixture() throws Exception {
    final DeleteFixtureCommand command = new DeleteFixtureCommand();
    final Long id = -1L;
    context.checking(new Expectations() { {
      oneOf(commandService).newCommand(DeleteFixtureCommand.class);
      will(returnValue(command));
      oneOf(commandService).invoke(with(allOf(same(command), 
         hasProperty("id", equalTo(id)))));
    } });
    
    FixtureModel fixture = service.removeFixture(id);
    context.assertIsSatisfied();
    assertEquals(id, fixture.getId());
  }
  
  /**
   * Test {@link CommandSupportedFixtureService#updateFixture(FixtureModel)}
   * @throws Exception
   */
  public void testUpdateFixture() throws Exception {
    final UpdateFixtureCommand command = new UpdateFixtureCommand();
    
    final Fixture fixture = new Fixture();
    fixture.setId(2L);
    
    final FixtureModel fixtureModel = new FixtureModel();
    fixtureModel.setBuilding("BUILDING 1");
    fixtureModel.setIpAddress("123.123.123.123");
    fixtureModel.setPositionHint("FRONT-RIGHT");
    fixtureModel.setRoom("404");
    
    context.checking(new Expectations() { { 
      oneOf(commandService).newCommand(UpdateFixtureCommand.class);
      will(returnValue(command));
      oneOf(commandService).invoke(command);
      will(returnValue(fixture));
    } });
    
    FixtureModel returnedFixture = service.updateFixture(fixtureModel);
    context.assertIsSatisfied();
    assertEquals(fixtureModel.getBuilding(), command.getBuildingName());
    assertEquals(fixtureModel.getIpAddress(), 
        command.getInetAddress().toString());
    assertEquals(fixtureModel.getPositionHint(), command.getPositionHint());
    assertEquals(fixtureModel.getRoom(), command.getRoomNumber());
    assertEquals(fixture.getId(), returnedFixture.getId());
  }
  
}
