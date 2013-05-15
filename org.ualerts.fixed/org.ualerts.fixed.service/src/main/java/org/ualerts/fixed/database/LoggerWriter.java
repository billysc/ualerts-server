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

import java.io.IOException;
import java.io.Writer;

import org.slf4j.Logger;

/**
 * A {@link Writer} that writes an an SLF4j {@link Logger}.
 * 
 * TODO: contribute this to SLF4j
 *
 * @author Carl Harris
 */
public class LoggerWriter extends Writer {

  /** Singleton strategy instance that writes messages at TRACE level */
  public static final Strategy TRACE = new TraceStrategy();

  /** Singleton strategy instance that writes messages at DEBUG level */
  public static final Strategy DEBUG = new DebugStrategy();
  
  /** Singleton strategy instance that writes messages at INFO level */
  public static final Strategy INFO = new InfoStrategy();
  
  /** Singleton strategy instance that writes messages at WARN level */
  public static final Strategy WARN = new WarnStrategy();
  
  /** Singleton strategy instance that writes messages at ERROR level */
  public static final Strategy ERROR = new ErrorStrategy();

  /**
   * A strategy for writing to a logger.
   * <p>
   * An implementation of this interface determines which of the logging
   * methods will be utilized to log a message.
   *
   * @author ceharris
   */
  public interface Strategy {
    
    /**
     * Logs a message. 
     * @param logger the target logger
     * @param message the message to write
     */
    void log(Logger logger, String message);
    
  }
 
  private final Logger logger;
  private final Strategy strategy;
  private final StringBuilder buffer = new StringBuilder();
 
  /**
   * Constructs a new instance.
   * @param logger logger delegate
   * @param strategy strategy for writing to the logger
   */
  public LoggerWriter(Logger logger, Strategy strategy) {
    this.logger = logger;
    this.strategy = strategy;
  }

  @Override
  public void close() throws IOException {
    flush();
  }

  @Override
  public void flush() throws IOException {
    if (buffer.length() > 0) {
      strategy.log(logger, buffer.toString());
      buffer.delete(0, buffer.length());
    }
  }

  @Override
  public void write(char[] buf, int offset, int length) throws IOException {
    for (int i = 0; i < length; i++) {
      char c = buf[offset + i];
      if (c == '\n' || c == '\r') {
        flush();
      }
      else {
        buffer.append(c);
      }
    }
  }

  /**
   * A {@link Strategy} that writes at TRACE level.
   */
  private static class TraceStrategy implements Strategy {
    public void log(Logger logger, String message) {
      if (logger.isTraceEnabled()) {
        logger.trace(message);
      }
    }
  }
  
  /**
   * A {@link Strategy} that writes at DEBUG level.
   */
  private static class DebugStrategy implements Strategy {
    public void log(Logger logger, String message) {
      logger.debug(message);
    }    
  }

  /**
   * A {@link Strategy} that writes at INFO level.
   */
  private static class InfoStrategy implements Strategy {
    public void log(Logger logger, String message) {
      logger.info(message);
    }
  }

  /**
   * A {@link Strategy} that writes at WARN level.
   */
  private static class WarnStrategy implements Strategy {
    public void log(Logger logger, String message) {
      logger.warn(message);
    }    
  }

  /**
   * A {@link Strategy} that writes at ERROR level.
   */
  private static class ErrorStrategy implements Strategy {
    public void log(Logger logger, String message) {
      logger.error(message);
    }
  }
  
}
