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

package org.ualerts.fixed.service.errors;

/**
 * Indicate that some unknown constraint violation has occurred.
 * This is a catch-all for various run-time exceptions that
 * could occur when executing a command.
 *
 * @author Brian Early
 */
public class UnspecifiedConstraintException
    extends Exception {
  private static final long serialVersionUID = -606051331690571077L;

  /**
   * Constructs a new instance.
   */
  public UnspecifiedConstraintException() {
    super();
  }

  /**
   * Constructs a new instance.
   * @param ex the exception that triggered this.
   */
  public UnspecifiedConstraintException(Exception ex) {
    super(ex);
  }
}
