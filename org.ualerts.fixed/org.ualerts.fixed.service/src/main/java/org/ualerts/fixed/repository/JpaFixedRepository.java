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

import org.springframework.stereotype.Repository;
import org.ualerts.fixed.Asset;
import org.ualerts.fixed.Building;
import org.ualerts.fixed.Fixture;
import org.ualerts.fixed.PositionHint;
import org.ualerts.fixed.Room;

/**
 * JPA implementation of {@link FixedRepository}.
 *
 * @author Brian Early
 */
@Repository("fixedRepository")
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
  public Asset findAssetBySerialNumber(String serialNumber) {
    Asset result = null;
    try {
      result = getEntityManager()
          .createNamedQuery("findAssetBySerialNumber", Asset.class)
          .setParameter("serialNumber", serialNumber)
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
  public Asset findAssetByInventoryNumber(String inventoryNumber) {
    Asset result = null;
    try {
      result = getEntityManager()
          .createNamedQuery("findAssetByInventoryNumber", Asset.class)
          .setParameter("inventoryNumber", inventoryNumber)
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
  public Asset findAssetByMacAddress(String macAddress) {
    Asset result = null;
    try {
      result = getEntityManager()
          .createNamedQuery("findAssetByMacAddress", Asset.class)
          .setParameter("macAddress", macAddress)
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
