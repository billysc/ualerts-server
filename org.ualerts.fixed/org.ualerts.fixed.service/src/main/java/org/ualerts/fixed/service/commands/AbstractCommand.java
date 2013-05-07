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
package org.ualerts.fixed.service.commands;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.validation.BindException;

import org.ualerts.fixed.service.Command;
import org.ualerts.fixed.service.errors.UnspecifiedConstraintException;

/**
 * An abstract base for {@link Command} implementations.
 *
 * @author Carl Harris
 */
public abstract class AbstractCommand<T> 
    implements Command<T>, InitializingBean {

  /**
   * @inheritDoc
   */
  public void afterPropertiesSet() throws Exception {
  }

  /**
   * @inheritDoc
   */
  public final T execute()
      throws Exception {
    onValidate();
    return onExecute();
  }

  /**
   * Performs any required validation of command state and member parameters.
   * @throws BindException if there are validation errors.
   * @throws UnspecifiedConstraintException if there is an unexpected
   *         error in the persistence layer.
   */
  protected void onValidate() throws BindException,
      UnspecifiedConstraintException {
  }
  
  /**
   * Performs the execution of the command.
   * @return a reference to the object the command uses, as appropriate.
   * @throws Exception as needed.
   */
  protected abstract T onExecute() throws Exception;
}
