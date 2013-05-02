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
 * A service interface for obtaining and invoking {@link Command} objects.
 *
 * @author Carl Harris
 */
public interface CommandService {

  /**
   * Creates a new command instance given the class of the command.  The command
   * must have been annotated with {@link CommandComponent}.
   * @param commandClass the class of the command
   * @param <T> the command subtype
   * @return a new instance of the command
   * @throws Exception
   */
  @SuppressWarnings("rawtypes")
  <T extends Command> T newCommand(Class<T> commandClass) throws Exception;
  
  /**
   * Creates a new command instance given the name and class of the command 
   * bean.  The command must be annotated with {@link CommandComponent}.
   * @param beanName the name of the bean
   * @param commandClass the class of the command
   * @param <T> the command subtype
   * @return a new instance of the command
   * @throws Exception
   */
  @SuppressWarnings("rawtypes")
  <T extends Command> T newCommand(String beanName, Class<T> commandClass)
      throws Exception;

  /**
   * Invokes/executes the command.
   * @param command the command to execute
   * @param <T> identifies the return type for the command
   * @return the value returned by {@link Command#execute()}
   * @throws Exception
   */
  <T> T invoke(Command<T> command) throws Exception;
  
}
