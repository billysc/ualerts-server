/*
 * File created on May 15, 2013
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

package org.ualerts.fixed.database;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.hsqldb.server.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;


/**
 * A bean that manages an embedded HSQL database.
 *
 * @author ceharris
 */
public class EmbeddedHsqlDatabaseServer implements InitializingBean, 
    DisposableBean {

  private static final Logger logger =    // CHECKSTYLE:idiomatic
      LoggerFactory.getLogger(EmbeddedHsqlDatabaseServer.class);

  private static final PrintWriter LOG_WRITER = 
      new PrintWriter(new LoggerWriter(logger, LoggerWriter.TRACE));
  
  private static final PrintWriter ERROR_WRITER = 
      new PrintWriter(new LoggerWriter(logger, LoggerWriter.ERROR));

  private static final int DB_INDEX = 0;
  
  private static final String URI_SCHEME = "file:";
  
  private static final long MAX_SHUTDOWN_DELAY = 5000;
  
  private static final long SHUTDOWN_DELAY = 250;
  
  private static final int SERVER_STATE_SHUTDOWN = 16;
  
  /** Default name of the database */
  public static final String DEFAULT_DATABASE_NAME = "embedded_db";
  
  /** Default local address to which the database server will bind */
  public static final String DEFAULT_ADDRESS = "127.0.0.1";
  
  /** Default local port to which the database server will bind */
  public static final int DEFAULT_PORT = 9003;
  
  private Resource location;
  private String dataSourceName;
  private Server server;

  /**
   * Sets a resource that represents the file system location for the
   * embedded database's content.
   * @param location the value to set
   */
  public void setLocation(Resource location) {
    this.location = location;
  }

  /**
   * Sets the name of the JNDI resource that represents the application's
   * {@link DataSource}.
   * @param dataSourceName JNDI resource name
   */
  public void setDataSourceName(String dataSourceName) {
    this.dataSourceName = dataSourceName;
  }

  /**
   * {@inheritDoc}
   */
  public void afterPropertiesSet() throws Exception {
    Assert.notNull(location, "location is required");
    Assert.notNull(dataSourceName, "dataSourceName is required");
    if (!wantEmbeddedDatabase()) return;
    try {
      server = startServer();
    }
    catch (Exception ex) {
      logger.error("database startup error: " + ex, ex);
    }    
  }

  /**
   * {@inheritDoc}
   */
  public void destroy() throws Exception {
    if (server == null) return;
    server.shutdown();
    waitForShutdown();
    logger.info("database shut down");
  }

  private boolean wantEmbeddedDatabase() {
    boolean useEmbedded = false;
    try {
      Context initCtx = new InitialContext();
      DataSource ds = (DataSource) initCtx.lookup(dataSourceName);
      Class<?> dataSourceClass = ds.getClass();
      Method urlMethod = dataSourceClass.getMethod("getUrl");
      String url = (String) urlMethod.invoke(ds);
      useEmbedded = url.endsWith("/" + DEFAULT_DATABASE_NAME); 
    }
    catch (NoSuchMethodException ex) {
      logger.warn("data source has no accessor for the URL");
    }
    catch (IllegalAccessException ex) {
      logger.warn("cannot access the data source URL");
    }
    catch (InvocationTargetException ex) {
      logger.warn("error accessing the data source URL");
    }
    catch (NamingException ex) {
      logger.warn("JNDI lookup failed: " + ex);
    }
    return useEmbedded;
  }

  private Server startServer() throws IOException {
    Server server = new Server();
    server.setLogWriter(LOG_WRITER);
    server.setErrWriter(ERROR_WRITER);
    server.setAddress(DEFAULT_ADDRESS);
    server.setPort(DEFAULT_PORT);
    server.setDatabaseName(DB_INDEX, DEFAULT_DATABASE_NAME);
    server.setDatabasePath(DB_INDEX, getDatabaseURI());
    server.start();
    logger.info("database server started @{}:{}",
        server.getAddress(), server.getPort());
    logger.info("database path: {}", 
        new File(server.getDatabasePath(DB_INDEX, true)).getParent());
    return server;
  }

  private String getDatabaseURI() throws IOException {
    return URI_SCHEME + 
        new File(location.getFile(), DEFAULT_DATABASE_NAME).getAbsolutePath();
  }

  private void waitForShutdown() {
    try {
      long start = System.currentTimeMillis();
      long now = start;
      while (server.getState() != SERVER_STATE_SHUTDOWN
          && (now - start) < MAX_SHUTDOWN_DELAY) {
        Thread.sleep(SHUTDOWN_DELAY);
        now = System.currentTimeMillis();
      }
    }
    catch (InterruptedException ex) {
      logger.warn("interrupted while waiting for server to shut down");
    }
  }


}
