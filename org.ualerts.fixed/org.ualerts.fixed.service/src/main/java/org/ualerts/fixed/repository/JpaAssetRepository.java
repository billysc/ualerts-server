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

import javax.persistence.NoResultException;

import org.springframework.stereotype.Repository;
import org.ualerts.fixed.Asset;

/**
 * JPA implementation of {@link AssetRepository}.
 *
 * @author Brian Early
 */
@Repository("assetRepository")
public class JpaAssetRepository extends AbstractJpaRepository
  implements AssetRepository {

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
  public void addAsset(Asset asset) {
    getEntityManager().persist(asset);
  }

}
