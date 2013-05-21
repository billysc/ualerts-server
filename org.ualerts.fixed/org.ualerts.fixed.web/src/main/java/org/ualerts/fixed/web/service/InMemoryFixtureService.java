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

package org.ualerts.fixed.web.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.ualerts.fixed.web.model.FixtureModel;

/**
 * A mocked implementation of the {@link FixtureService} interface.
 *
 * @author Michael Irwin
 */
public class InMemoryFixtureService implements FixtureService {

  private Long lastUsedId;
  private List<FixtureModel> fixtures;
  
  /**
   * Constructs a new instance of the mocked service.
   */
  public InMemoryFixtureService() {
    lastUsedId = 1L;
    fixtures = new ArrayList<FixtureModel>();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public FixtureModel createFixture(FixtureModel fixture) throws Exception {
    fixture.setId(lastUsedId++);
    fixture.setVersion(1L);
    fixtures.add(fixture);
    return fixture;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public FixtureModel findFixtureById(Long id) throws Exception {
    Iterator<FixtureModel> i = fixtures.iterator();
    while (i.hasNext()) {
      FixtureModel candidate = i.next();
      if (candidate.getId().equals(id)) {
        return candidate;
      }
    }
    throw new EntityNotFoundException();
  } 

  /**
   * {@inheritDoc}
   */
  @Override
  public List<FixtureModel> retrieveAllFixtures() throws Exception {
    return fixtures;
  }
  
  /**
   * {@inheritDoc}
   */
  public FixtureModel removeFixture(Long id) throws Exception {
    Iterator<FixtureModel> i = fixtures.iterator();
    boolean found = false;
    while (!found && i.hasNext()) {
      FixtureModel candidate = i.next();
      found = candidate.getId().equals(id);
    }
    if (found) {
      i.remove();
    }
    FixtureModel fixture = new FixtureModel();
    fixture.setId(id);
    return fixture;
  }
  
}
