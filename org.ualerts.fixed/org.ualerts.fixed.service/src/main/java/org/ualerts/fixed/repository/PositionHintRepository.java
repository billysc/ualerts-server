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
package org.ualerts.fixed.repository;

import java.util.List;

import org.ualerts.fixed.PositionHint;

/**
 * Defines basic behavior for the repository supporting position hints.
 *
 * @author Brian Early
 */
public interface PositionHintRepository {
  
  /**
   * Finds all position hints.
   * @return the list of PositionHint objects.  May be empty/null.
   */
  List<PositionHint> findAllHints();
  
  /**
   * Finds a position hint based on its text.
   * @param hintText the text of the hint.
   * @return the PositionHint object.  Null if it isn't found.
   */
  PositionHint findHint(String hintText);

  /**
   * Adds a position hint to persistence control.
   * @param hint the position hint to add.
   */
  void addPositionHint(PositionHint hint);
  
}
