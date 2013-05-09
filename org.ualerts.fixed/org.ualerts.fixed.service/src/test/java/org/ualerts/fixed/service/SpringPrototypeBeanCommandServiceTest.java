/*
 * File created on May 1, 2013
 *
 * Copyright 2008-2011 Virginia Polytechnic Institute and State University
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
package org.ualerts.fixed.service;

import static org.junit.Assert.assertTrue;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

/**
 * Unit tests for {@link SpringPrototypeBeanCommandService}.
 *
 * @author Brian Early
 */
public class SpringPrototypeBeanCommandServiceTest {
  private Mockery context;
  private CommandInvoker invoker;
  private Command<Object> command;
  private ApplicationContext applicationContext;
  private SpringPrototypeBeanCommandService service;
  
  /**
   * @throws java.lang.Exception
   */
  @SuppressWarnings("unchecked")
  @Before
  public void setUp() throws Exception {
    context = new Mockery();
    command = context.mock(Command.class);
    invoker = context.mock(CommandInvoker.class);
    applicationContext = context.mock(ApplicationContext.class);
    service = new SpringPrototypeBeanCommandService();
    service.setApplicationContext(applicationContext);
    service.setCommandInvoker(invoker);
  }

  /**
   * @throws java.lang.Exception
   */
  @After
  public void tearDown() throws Exception {
    service = null;
    applicationContext = null;
    command = null;
    invoker = null;
  }

  /**
   * Test method for
   * {@link SpringPrototypeBeanCommandService#newCommand()}.
   */
  @Test
  public void testNewComand() throws Exception {
    context.checking(new Expectations() { {
      oneOf(applicationContext).getBean(command.getClass());
      will(returnValue(command));
    } });
    assertTrue(service.newCommand(command.getClass()) != null);
    context.assertIsSatisfied();
  }

  /**
   * Test method for
   * {@link SpringPrototypeBeanCommandService#newCommand()}.
   */
  @Test
  public void testNewCommandWithBeanName() throws Exception {
    final String beanName = "beanName";
    context.checking(new Expectations() { {
      oneOf(applicationContext).getBean(beanName, command.getClass());
      will(returnValue(command));
    } });
    assertTrue(service.newCommand(beanName, command.getClass()) != null);
    context.assertIsSatisfied();
  }

  /**
   * Test method for
   * {@link SpringPrototypeBeanCommandService#invoke()}.
   */
  @Test
  public void testInvoke() throws Exception {
    final Object object = new Object();
    context.checking(new Expectations() { {
      oneOf(invoker).invoke(command);
      will(returnValue(object));
    } });
    assertTrue(service.invoke(command) != null);
    context.assertIsSatisfied();
  }

}
