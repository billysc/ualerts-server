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
import org.ualerts.fixed.Building;

/**
 * JPA implementation of {@link BuildingRepository}.
 *
 * @author Brian Early
 */
@Repository("buildingRepository")
public class JpaBuildingRepository extends AbstractJpaRepository
  implements BuildingRepository {

  /**
   * {@inheritDoc}
   */
  @Override
  public List<Building> findAllBuildings() {
    TypedQuery<Building> query =
        getEntityManager().createNamedQuery("findAllBuildings", Building.class);
    return query.getResultList();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Building findBuildingById(String id) {
    Building result = null;
    try {
      result = getEntityManager()
          .createNamedQuery("findBuildingById", Building.class)
          .setParameter("id", id)
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
  public Building findBuildingByName(String name) {
    Building result = null;
    try {
      result = getEntityManager()
          .createNamedQuery("findBuildingByName", Building.class)
          .setParameter("name", name)
          .getSingleResult();
    }
    catch (NoResultException ex) {
      result = null;
    }
    return result;
  }

}
