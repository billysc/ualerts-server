/*
 * File created on May 17, 2013
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
import org.ualerts.fixed.PositionHint;
import org.ualerts.fixed.service.CommandService;
import org.ualerts.fixed.service.api.model.PositionHintsModel;
import org.ualerts.fixed.service.commands.FindAllPositionHintsCommand;

/**
 * An implementation of the PositionHintService that uses commands to provide
 * back-end functionality.
 *
 * @author Michael Irwin
 */
@Service
public class CommandSupportedPositionHintService 
    implements PositionHintService {

  private CommandService commandService;
  
  /**
   * {@inheritDoc}
   */
  @Override
  public PositionHintsModel getAllPositionHints() throws Exception {
    List<String> hints = new ArrayList<String>();
    
    FindAllPositionHintsCommand command = 
        commandService.newCommand(FindAllPositionHintsCommand.class);
    for (PositionHint positionHint : commandService.invoke(command)) {
      hints.add(positionHint.getHintText());
    }
    return new PositionHintsModel(hints.toArray(new String[0]));
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
