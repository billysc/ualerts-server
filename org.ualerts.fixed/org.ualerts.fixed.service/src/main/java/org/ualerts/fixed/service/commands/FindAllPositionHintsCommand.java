/*
 * File created on May 9, 2013 
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

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.ualerts.fixed.PositionHint;
import org.ualerts.fixed.repository.PositionHintRepository;
import org.ualerts.fixed.service.CommandComponent;
import org.ualerts.fixed.service.errors.UnspecifiedConstraintException;

/**
 * Command to find all position hints in the UAlerts system.
 *
 * @author Brian Early
 */
@CommandComponent
public class FindAllPositionHintsCommand
extends AbstractCommand<List<PositionHint>> {
  private PositionHintRepository positionHintRepository;

  /**
   * {@inheritDoc}
   */
  @Override
  protected List<PositionHint> onExecute()
      throws UnspecifiedConstraintException {
    return positionHintRepository.findAllHints();
  }

  /**
   * Sets the {@code positionHintRepository} property.
   * @param positionHintRepository the position hint repository
   */
  @Autowired
  public void setPositionHintRepository(
      PositionHintRepository positionHintRepository) {
    this.positionHintRepository = positionHintRepository;
  }

}
