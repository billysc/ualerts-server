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

import javax.persistence.NoResultException;

import org.ualerts.fixed.domain.Asset;
import org.ualerts.fixed.domain.Building;
import org.ualerts.fixed.domain.Fixture;
import org.ualerts.fixed.domain.PositionHint;
import org.ualerts.fixed.domain.Room;

/**
 * JPA implementation of {@link FixedRepository}.
 *
 * @author Brian Early
 */
public class JpaFixedRepository extends AbstractJpaRepository implements FixedRepository {

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
  public Room findRoom(String buildingId, String roomNumber) {
    Room result = null;
    try {
      result = getEntityManager()
          .createNamedQuery("findRoom", Room.class)
          .setParameter("buildingId", buildingId)
          .setParameter("roomNumber", roomNumber)
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
  public void addRoom(Room room) {
    getEntityManager().persist(room);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addPositionHint(PositionHint hint) {
    getEntityManager().persist(hint);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addAsset(Asset asset) {
    getEntityManager().persist(asset);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addFixture(Fixture fixture) {
    getEntityManager().persist(fixture);
  }

}
