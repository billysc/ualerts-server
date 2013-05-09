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
package org.ualerts.fixed.repository;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.ualerts.fixed.Fixture;

/**
 * JPA implementation of {@link FixtureRepository}.
 *
 * @author Brian Early
 */
@Repository("fixtureRepository")
public class JpaFixtureRepository extends AbstractJpaRepository
  implements FixtureRepository {

  /**
   * {@inheritDoc}
   */
  @Override
  public List<Fixture> findAllFixtures() {
    TypedQuery<Fixture> query =
        getEntityManager().createNamedQuery("findAllFixtures", Fixture.class);
    return query.getResultList();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Fixture findFixtureByLocation(Long roomId, Long hintId) {
    Fixture result = null;
    try {
      result = getEntityManager()
          .createNamedQuery("findFixtureByLocation", Fixture.class)
          .setParameter("roomId", roomId)
          .setParameter("hintId", hintId)
          .getSingleResult();
    }
    catch (NoResultException ex) {
      result = null;
    }
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Fixture findFixtureById(Long id) throws EntityNotFoundException {
    try {
      return getEntityManager()
          .createNamedQuery("findFixtureById", Fixture.class)
          .setParameter("id", id)
          .getSingleResult();
    }
    catch (NoResultException ex) {
      throw new EntityNotFoundException(Fixture.class, id);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addFixture(Fixture fixture) {
    getEntityManager().persist(fixture);
  }

}
