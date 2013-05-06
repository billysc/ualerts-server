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

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

/**
 * Default implementation of {@link DateTimeService}.
 *
 * @author Brian Early
 */
@Service
public class DefaultDateTimeService implements DateTimeService {

  /**
   * {@inheritDoc}
   */
  @Override
  public Date getCurrentDate() {
    return new Date();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Calendar getCurrentCalendar() {
    return Calendar.getInstance();
  }

}
