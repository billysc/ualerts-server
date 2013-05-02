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
 * Validation exception for a location conflict (room and position
 * hint combination already in use).
 *
 * @author earlyb
 */
public class LocationConflictException
    extends AbstractValidationException {
  private static final long serialVersionUID = -1539453602409947599L;

  /**
   * Constructs a new instance.
   */
  public LocationConflictException() {
    super("ualerts.fixed.validation.locationConflict");
  }

}
