/*
 * File created on May 1, 2013 
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


/**
 * Provides the ability to invoke/execute commands.
 *
 * @author Brian Early
 */
public interface CommandInvoker {

  /**
   * Invokes a command.
   * 
   * @param <T> the model subtype returned by the command
   * 
   * @param command command object to invoke
   * @return resulting model
   * @throws Exception as needed
   */
  <T> T invoke(Command<T> command) throws Exception;

}
