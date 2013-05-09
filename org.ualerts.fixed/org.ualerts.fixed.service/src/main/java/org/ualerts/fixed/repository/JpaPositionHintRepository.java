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

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.ualerts.fixed.PositionHint;

/**
 * JPA implementation of {@link PositionHintRepository}.
 *
 * @author Brian Early
 */
@Repository("positionHintRepository")
public class JpaPositionHintRepository extends AbstractJpaRepository
  implements PositionHintRepository {

  /**
   * {@inheritDoc}
   */
  @Override
  public List<PositionHint> findAllHints() {
    TypedQuery<PositionHint> query = getEntityManager()
        .createNamedQuery("findAllPositionHints", PositionHint.class);
    return query.getResultList();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public PositionHint findHint(String hintText) {
    PositionHint result = null;
    try {
      result = getEntityManager()
          .createNamedQuery("findPositionHint", PositionHint.class)
          .setParameter("hintText", hintText)
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
  public void addPositionHint(PositionHint hint) {
    getEntityManager().persist(hint);
  }

}
