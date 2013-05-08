/*
 * File created on May 6, 2013
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
package org.ualerts.fixed.service;

import org.springframework.stereotype.Service;

/**
 * The default implementation of {@link CommandInvoker}.
 *
 * @author Brian Early
 */
@Service
public class DefaultCommandInvoker implements CommandInvoker {

  /**
   * {@inheritDoc}
   */
  public <T> T invoke(Command<T> command) throws Exception {
    return command.execute();
  }

}
