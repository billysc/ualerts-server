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
package org.ualerts.fixed.service;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.NetworkInterface;
import java.net.UnknownHostException;

/**
 * A decorator for InetAddress that makes it more convenient to use 
 * for purposes other than opening sockets.
 *
 * @author Carl Harris
 */
public class InetAddress implements Serializable {

  public enum Family {
    inet4,
    inet6;
  }
  
  private static final long serialVersionUID = -1690013910134168604L;

  private String value;
  private transient java.net.InetAddress inetAddress;
  private Family family;
  
  protected InetAddress(java.net.InetAddress inetAddress) {
    this.inetAddress = inetAddress;
    this.value = inetAddress.getHostAddress();
    this.family = familyOf(inetAddress);
  }

  private static Family familyOf(java.net.InetAddress inetAddress) {
    if (inetAddress instanceof Inet4Address) {
      return Family.inet4;
    }
    else if (inetAddress instanceof Inet6Address) {
      return Family.inet6;
    }
    else {
      throw new IllegalArgumentException("unrecognized address family");
    }
  }

  public static InetAddress getByAddress(String address) {
    if (address == null) {
      throw new IllegalArgumentException("address is required");
    }
    try {
      return new InetAddress(java.net.InetAddress.getByName(address));
    }
    catch (UnknownHostException ex) {
      throw new IllegalArgumentException(ex);
    }
  }
  
  public static InetAddress getByName(String name) 
      throws UnknownHostException {
    if (name == null) {
      throw new IllegalArgumentException("name is required");
    }
    return new InetAddress(java.net.InetAddress.getByName(name));
  }
  
  @Override
  public boolean equals(Object o) {
    if (!(o instanceof InetAddress)) return false;
    if (this.value == null) return false;
    return this.value.equals(((InetAddress) o).value);
  }

  @Override
  public int hashCode() {
    if (this.value == null) return 0;
    return this.value.hashCode();
  }

  @Override
  public String toString() {
    return this.value;
  }

  public Family getFamily() {
    return family;
  }

  public long getAddressAsLong() {
    byte[] octets = getAddress();
    long address = (octets[0] & 0xff);
    address = (address<<8) | (octets[1] & 0xff);
    address = (address<<8) | (octets[2] & 0xff);
    address = (address<<8) | (octets[3] & 0xff);
    return address;
  }
  
  public byte[] getAddress() {
    return inetAddress.getAddress();
  }

  public String getCanonicalHostName() {
    return inetAddress.getCanonicalHostName();
  }

  public String getHostAddress() {
    return value;
  }

  public String getHostName() {
    return inetAddress.getHostName();
  }

  public boolean isAnyLocalAddress() {
    return inetAddress.isAnyLocalAddress();
  }

  public boolean isLinkLocalAddress() {
    return inetAddress.isLinkLocalAddress();
  }

  public boolean isLoopbackAddress() {
    return inetAddress.isLoopbackAddress();
  }

  public boolean isMCGlobal() {
    return inetAddress.isMCGlobal();
  }

  public boolean isMCLinkLocal() {
    return inetAddress.isMCLinkLocal();
  }

  public boolean isMCNodeLocal() {
    return inetAddress.isMCNodeLocal();
  }

  public boolean isMCOrgLocal() {
    return inetAddress.isMCOrgLocal();
  }

  public boolean isMCSiteLocal() {
    return inetAddress.isMCSiteLocal();
  }

  public boolean isMulticastAddress() {
    return inetAddress.isMulticastAddress();
  }

  public boolean isReachable(int ttl) throws IOException {
    return inetAddress.isReachable(ttl);
  }

  public boolean isReachable(NetworkInterface netif, int ttl, int timeout) 
      throws IOException {
    return inetAddress.isReachable(netif, ttl, timeout);
  }

  public boolean isSiteLocalAddress() {
    return inetAddress.isSiteLocalAddress();
  }

  private void readObject(ObjectInputStream is) throws IOException {
    StringBuilder sb = new StringBuilder(100);
    int length = is.readInt();
    for (int i = 0; i < length; i++) {
      sb.append(is.readChar());
    }
    this.inetAddress = java.net.InetAddress.getByName(sb.toString());
    this.value = this.inetAddress.getHostAddress();
    this.family = familyOf(this.inetAddress);
  }
  
  private void writeObject(ObjectOutputStream os) throws IOException {
    String addr = getHostAddress();
    os.writeInt(addr.length());
    os.writeChars(addr);
  }

}
