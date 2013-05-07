/*
 * File created on May 7, 2013 
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
package org.ualerts.fixed;

import java.beans.PropertyEditorSupport;

/**
 * A {@code PropertyEditor} for an {@link InetAddress}.
 *
 * @author Carl Harris
 */
public class InetAddressEditor extends PropertyEditorSupport {

  @Override
  public String getAsText() {
    return getValue() == null ?
        null : ((InetAddress) getValue()).getHostAddress();
  }

  @Override
  public void setAsText(String text) throws IllegalArgumentException {
    setValue(InetAddress.getByAddress(text));
  }

}
