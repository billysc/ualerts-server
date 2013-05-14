/*
 * File created on May 13, 2013
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

package org.ualerts.testing.jpa;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.hibernate.tool.hbm2ddl.MultipleLinesSqlCommandExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A {@link PersistentDataResource} that utilizes a Hibernate Session
 * obtained from an {@link EntityManager} to gain access to the database.
 *
 * @author ceharris
 */
public class HibernatePersistentDataResource
    extends PersistentDataResource {

  private static final Logger logger =    // CHECKSTYLE:idiomatic
      LoggerFactory.getLogger(HibernatePersistentDataResource.class);
  
  /**
   * Constructs a new instance.
   * @param entityManagerFactory entity manager factory delegate
   */
  public HibernatePersistentDataResource(
      EntityManagerFactoryResource entityManagerFactory) {
    super(entityManagerFactory);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doExecuteSQL(EntityManager entityManager, 
      URL resource) throws Throwable {
    logger.info("processing resource: {}", resource);
    Session session = (Session) entityManager.getDelegate();
    final String[] sqlStatements = loadSQLStatements(resource);
    logger.debug("loaded {} statements", sqlStatements.length);
    session.doWork(new Work() {
      @Override
      public void execute(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        int count = 0;
        try {
          for (int i = 0; i < sqlStatements.length; i++) {
            logger.debug("executing: {}", 
                sqlStatements[i].replaceAll("\n", ""));
            statement.execute(sqlStatements[i]);
            count++;
          }
        }
        finally {
          statement.close();
          logger.info("executed {} statements", count);
        }
      }       
    });
  }

  private String[] loadSQLStatements(URL resource) throws IOException {
    InputStream inputStream = resource.openStream();
    try {
      Reader reader = new InputStreamReader(inputStream);
      MultipleLinesSqlCommandExtractor extractor = 
          new MultipleLinesSqlCommandExtractor();
      return extractor.extractCommands(reader);
    }
    finally {
      inputStream.close();
    }
  }
  
}
