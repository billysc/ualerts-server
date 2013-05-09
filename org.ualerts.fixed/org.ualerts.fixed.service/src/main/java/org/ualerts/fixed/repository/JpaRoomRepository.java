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
import org.ualerts.fixed.Room;

/**
 * JPA implementation of {@link RoomRepository}.
 *
 * @author Brian Early
 */
@Repository("roomRepository")
public class JpaRoomRepository extends AbstractJpaRepository
  implements RoomRepository {

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
  public List<Room> findRoomsForBuilding(String buildingId) {
    TypedQuery<Room> query =
        getEntityManager().createNamedQuery("findRoomsForBuilding", Room.class)
        .setParameter("buildingId", buildingId);
    return query.getResultList();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addRoom(Room room) {
    getEntityManager().persist(room);
  }

}
