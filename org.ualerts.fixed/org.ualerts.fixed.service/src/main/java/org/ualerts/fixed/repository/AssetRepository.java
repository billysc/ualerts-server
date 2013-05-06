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

import org.ualerts.fixed.Asset;

/**
 * Defines basic behavior for the repository supporting assets.
 *
 * @author Brian Early
 */
public interface AssetRepository {
  /**
   * Finds an asset by its serial number.
   * @param serialNumber the serial number to look for.
   * @return the Asset object.  Null if it isn't found.
   */
  Asset findAssetBySerialNumber(String serialNumber);
  
  /**
   * Finds an asset by its inventory number.
   * @param inventoryNumber the inventory number to look for.
   * @return the Asset object.  Null if it isn't found.
   */
  Asset findAssetByInventoryNumber(String inventoryNumber);
  
  /**
   * Finds an asset by its MAC address.
   * @param macAddress the MAC address to look for.
   * @return the Asset object.  Null if it isn't found.
   */
  Asset findAssetByMacAddress(String macAddress);
  
  /**
   * Adds an asset to persistence control.
   * @param asset the asset to add.
   */
  void addAsset(Asset asset);
  
}
