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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An annotation applied to a test method to provide the names of resources
 * that contain SQL statements to be executed before and after a test is run.
 *
 * @author Carl Harris
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TestResources {

  /**
   * Default resource name suffix.
   */
  String DEFAULT_SUFFIX = ".sql";
  
  /**
   * Identifies the prefix path for the resource.
   * <p>
   * By default the prefix is the class path package location for the
   * class containing the method annotated with {@link TestResources}.
   */
  String prefix() default "";
  
  /**
   * Identifies the suffix path for the resource.
   * <p>
   * This is used to apply a filename suffix to the resource path name.  
   * {@link #DEFAULT_SUFFIX} is used by default if no suffix is specified.
   */
  String suffix() default DEFAULT_SUFFIX;
  
  /**
   * Identifies the 'middle' portion of the path for the resource that should
   * be processed before the test.
   * <p> 
   * By default, this value is the name of the class containing the annotated
   * method, followed by underscore, followed by the annotated method's name,
   * followed by underscore, followed by {@code before};
   * e.g. {@code MyTestClass_myTestMethod_before}.
   */
  String before() default "";
  
  /**
   * Identifies the 'middle' portion of the path for the resource that should
   * be processed after the test.
   * <p> 
   * By default, this value is the name of the class containing the annotated
   * method, followed by underscore, followed by the annotated method's name,
   * followed by underscore, followed by {@code after};
   * e.g. {@code MyTestClass_myTestMethod_after}.
   */
  String after() default "";
  
}
