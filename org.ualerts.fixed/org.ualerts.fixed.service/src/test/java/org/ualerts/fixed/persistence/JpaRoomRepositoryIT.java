/*
 * File created on May 9, 2013
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

package org.ualerts.fixed.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.util.Assert;
import org.ualerts.fixed.Building;
import org.ualerts.fixed.Room;
import org.ualerts.fixed.integration.ApplicationContextUtil;
import org.ualerts.fixed.repository.JpaBuildingRepository;
import org.ualerts.fixed.repository.JpaRoomRepository;

import edu.vt.cns.kestrel.common.IntegrationTestRunner;

/**
 * Integration tests for {@link JpaRoomRepository}.
 *
 * @author Brian Early
 */
@RunWith(IntegrationTestRunner.class)
public class JpaRoomRepositoryIT {
  private static EntityManagerFactory entityManagerFactory;
  private EntityManager entityManager;
  private EntityTransaction tx;
  private JpaRoomRepository repository;
  private JpaBuildingRepository buildingRepository;
  private Building building;
  private Room room;
  
  /**
   * Things to set up before the test instance of this
   * class has been created.
   * @throws Exception as needed.
   */
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    entityManagerFactory =
        ApplicationContextUtil.getContext().getBean(EntityManagerFactory.class);
  }
  
  /**
   * Things to set up after the test instance of this class
   * has been destroyed.
   * @throws Exception as needed.
   */
  @AfterClass
  public static void tearDownAfterClass() throws Exception {
    ApplicationContextUtil.close();
  }

  /**
   * Sets up necessary objects to perform tests.
   * @throws Exception as needed
   */
  @Before
  public void setupUp() throws Exception {
    entityManager = entityManagerFactory.createEntityManager();
    tx = entityManager.getTransaction();
    tx.begin();
    repository = new JpaRoomRepository();
    repository.setEntityManager(entityManager);
    repository.setEntityManagerFactory(entityManagerFactory);
    buildingRepository = new JpaBuildingRepository();
    buildingRepository.setEntityManager(entityManager);
    buildingRepository.setEntityManagerFactory(entityManagerFactory);
    building = createBuilding();
    room = createRoom(building);
    
  }
  
  /**
   * Tears down any outstanding objects before exiting.
   * @throws Exception as needed
   */
  @After
  public void tearDown() throws Exception {
    if (tx != null || tx.isActive()) {
      tx.rollback();
    }
    if (entityManager != null) {
      entityManager.close();
    }
  }

  /**
   * Verifies that a search for a particular room works. 
   * @throws Exception as needed
   */
  @Test
  public void testFindRoom() throws Exception {
    Room result =
        repository.findRoom(building.getId(), room.getRoomNumber());
    Assert.notNull(result);
    Assert.isTrue(result.getId() == room.getId());
    Assert.isTrue(result.getBuilding().getId().equals(building.getId()));
    Assert.isTrue(result.getRoomNumber().equals(room.getRoomNumber()));
  }

  /**
   * Verifies that a search for a non-existent room works. 
   * @throws Exception as needed
   */
  @Test
  public void testFindRoomNotFound() throws Exception {
    Room result =
        repository.findRoom(building.getId(), "BLAH!");
    Assert.isNull(result);
  }

  /**
   * Verifies that a search for rooms for a building works. 
   * @throws Exception as needed
   */
  @Test
  public void testFindRoomsForBuilding() throws Exception {
    List<Room> results = repository.findRoomsForBuilding(building.getId());
    Assert.notNull(results);
    Assert.isTrue(results.size() > 0);
    Room match = null;
    for (Room r : results) {
      if (r.getId() == room.getId()) {
        match = r;
      }
    }
    Assert.notNull(match);
  }

  private Building createBuilding() {
    Building building = new Building();
    building.setAbbreviation("bldg1");
    building.setId("BLDG1");
    building.setName("Building 1");
    entityManager.persist(building);
    return building;
  }
  
  private Room createRoom(Building building) {
    Room room = new Room();
    room.setBuilding(building);
    room.setRoomNumber("123");
    repository.addRoom(room);
    return room;
  }
  
}
