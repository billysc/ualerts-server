/*
 * File created on May 15, 2013
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

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;
import org.ualerts.fixed.PositionHint;
import org.ualerts.fixed.service.CommandService;
import org.ualerts.fixed.service.commands.FindAllPositionHintsCommand;
import org.ualerts.fixed.web.model.BuildingModel;
import org.ualerts.fixed.web.model.PositionHintsModel;

/**
 * Test case for the CommandSupportedPositionHintService class
 *
 * @author Michael Irwin
 */
public class CommandSupportedPositionHintServiceTest {

  private Mockery context;
  private CommandService commandService;
  private CommandSupportedPositionHintService service;
  
  /**
   * Setup tasks
   */
  @Before
  public void setUp() {
    context = new Mockery();
    commandService = context.mock(CommandService.class);
    service = new CommandSupportedPositionHintService();
    service.setCommandService(commandService);
  }
  
  /**
   * Validate the getAllPositionHints method
   */
  @Test
  public void testGetAllPositionHints() throws Exception {
    final FindAllPositionHintsCommand command = 
        new FindAllPositionHintsCommand();
    final List<PositionHint> hints = new ArrayList<PositionHint>();
    PositionHint hint = new PositionHint();
    hint.setHintText("TOP-RIGHT");
    hint.setId(2L);
    hints.add(hint);
    
    context.checking(new Expectations() { { 
      oneOf(commandService).newCommand(FindAllPositionHintsCommand.class);
      will(returnValue(command));
      oneOf(commandService).invoke(command);
      will(returnValue(hints));
    } });
    
    PositionHintsModel hintsModel = service.getAllPositionHints();
    context.assertIsSatisfied();
    assertNotNull(hintsModel);
    assertEquals(hints.size(), hintsModel.getPositionHints().length);
    assertEquals(hint.getHintText(), hintsModel.getPositionHints()[0]);
  }
  
}
