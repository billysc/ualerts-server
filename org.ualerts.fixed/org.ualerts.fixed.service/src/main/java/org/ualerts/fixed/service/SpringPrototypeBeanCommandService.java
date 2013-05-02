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

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * An implementation of {@link CommandService} that utilizes the Spring
 * Framework's prototype bean concept.  Commands using this service must
 * be annotated with {@link CommandComponent}.
 *
 * @author Carl Harris
 */
@Service("commandService")
public class SpringPrototypeBeanCommandService
    implements CommandService, ApplicationContextAware {

  private ApplicationContext applicationContext;
  private CommandInvoker commandInvoker;
  
  /**
   * Gets the {@code applicationContext} property.
   * @return property value
   */
  public ApplicationContext getApplicationContext() {
    return applicationContext;
  }

  /**
   * ${@inheritDoc}
   */
  public void setApplicationContext(ApplicationContext applicationContext)
      throws BeansException {
    this.applicationContext = applicationContext;
  }

  /**
   * Sets the {@code commandInvoker} property.
   * @param commandInvoker the property value to set
   */
  @Autowired
  public void setCommandInvoker(CommandInvoker commandInvoker) {
    this.commandInvoker = commandInvoker;
  }

  /**
   * {@inheritDoc}
   * <strong>NOTE</strong>: The command must be annotated with 
   * {@link CommandComponent}
   */
  @SuppressWarnings("rawtypes")
  public <T extends Command> T newCommand(Class<T> commandClass) 
      throws Exception {
    return getApplicationContext().getBean(commandClass);
  }

  /**
   * {@inheritDoc}
   * <strong>NOTE</strong>: The command must be annotated with 
   * {@link CommandComponent}
   */
  @SuppressWarnings({ "rawtypes" })
  public <T extends Command> T newCommand(String beanName, 
      Class<T> commandClass) throws Exception {
    return getApplicationContext().getBean(beanName, commandClass);
  }

  /**
   * {@inheritDoc}
   */
  @Transactional(rollbackFor = { java.lang.Exception.class })
  public <T> T invoke(Command<T> command) throws Exception {
    return commandInvoker.invoke(command);
  }

}
