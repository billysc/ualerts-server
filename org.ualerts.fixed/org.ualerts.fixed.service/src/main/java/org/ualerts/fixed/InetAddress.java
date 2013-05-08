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
package org.ualerts.fixed;

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

  /**
   * An enumeration of address family types.
   */
  public enum Family {
    /** protocol version 4 */
    inet4,
    /** protocol version 6 */
    inet6;
  }
  
  private static final long serialVersionUID = -1690013910134168604L;

  private String value;
  private transient java.net.InetAddress inetAddress;
  private Family family;
  
  /**
   * Constructs a new instance.
   * @param inetAddress the {@link java.net.InetAddress} delegate
   */
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

  /**
   * Gets an instance of {@link InetAddress} using a string representation
   * of an address.
   * @param address string representation of the address
   * @return address object
   */
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
  
  /**
   * Gets an instance of {@link InetAddress} using a host name.
   * @param name subject host name
   * @return address object
   * @throws UnknownHostException
   */
  public static InetAddress getByName(String name) 
      throws UnknownHostException {
    if (name == null) {
      throw new IllegalArgumentException("name is required");
    }
    return new InetAddress(java.net.InetAddress.getByName(name));
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object o) {
    if (!(o instanceof InetAddress)) return false;
    if (this.value == null) return false;
    return this.value.equals(((InetAddress) o).value);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    if (this.value == null) return 0;
    return this.value.hashCode();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    return this.value;
  }

  /**
   * Gets the address family for the receiver.
   * @return address family
   */
  public Family getFamily() {
    return family;
  }

  /**
   * Gets the binary representation of the receiver as a long.
   * @return binary address representation
   * @throws UnsupportedOperationException if the address family is not
   *    {@link Family#inet4}
   */
  public long getAddressAsLong() throws UnsupportedOperationException {
    if (getFamily() != Family.inet4) {
      throw new UnsupportedOperationException("address family is not " 
          + Family.inet4);
    }
    final int mask = 0xff;
    final int bitLength = 8;
    final byte[] octets = getAddress();
    long address = 0;
    for (int i = 0; i < octets.length; i++) {
      address = (address << bitLength) | (octets[i] & mask);
    }
    return address;
  }
  
  /**
   * Gets the binary representation of the receiver.
   * @return array of octets containing the binary address representation
   */
  public byte[] getAddress() {
    return inetAddress.getAddress();
  }

  /**
   * Gets the canonical host name associated with the receiver.
   * @return host name
   */
  public String getCanonicalHostName() {
    return inetAddress.getCanonicalHostName();
  }

  /**
   * Gets the string representation of the receiver.
   * @return string representation of the address
   */
  public String getHostAddress() {
    return value;
  }

  /**
   * Gets the host name associated with the receiver.
   * @return host name
   */
  public String getHostName() {
    return inetAddress.getHostName();
  }

  /**
   * @see java.net.InetAddress#isAnyLocalAddress()
   * @return flag
   */
  public boolean isAnyLocalAddress() {
    return inetAddress.isAnyLocalAddress();
  }

  /**
   * @see java.net.InetAddress#isLinkLocalAddress()
   * @return flag
   */
  public boolean isLinkLocalAddress() {
    return inetAddress.isLinkLocalAddress();
  }

  /**
   * @see java.net.InetAddress#isLoopbackAddress()
   * @return flag
   */
  public boolean isLoopbackAddress() {
    return inetAddress.isLoopbackAddress();
  }

  /**
   * @see java.net.InetAddress#isMCGlobal()
   * @return flag
   */
  public boolean isMCGlobal() {
    return inetAddress.isMCGlobal();
  }

  /**
   * @see java.net.InetAddress#isMCLinkLocal()
   * @return flag
   */
  public boolean isMCLinkLocal() {
    return inetAddress.isMCLinkLocal();
  }

  /**
   * @see java.net.InetAddress#isMCNodeLocal()
   * @return flag
   */
  public boolean isMCNodeLocal() {
    return inetAddress.isMCNodeLocal();
  }

  /**
   * @see java.net.InetAddress#isMCOrgLocal()
   * @return flag
   */
  public boolean isMCOrgLocal() {
    return inetAddress.isMCOrgLocal();
  }

  /**
   * @see java.net.InetAddress#isMCSiteLocal()
   * @return flag
   */
  public boolean isMCSiteLocal() {
    return inetAddress.isMCSiteLocal();
  }

  /**
   * @see java.net.InetAddress#isMulticastAddress()
   * @return flag
   */
  public boolean isMulticastAddress() {
    return inetAddress.isMulticastAddress();
  }

  /**
   * @see java.net.InetAddress#isReachable()
   * @param ttl time-to-live
   * @return flag
   * @throws IOException
   */
  public boolean isReachable(int ttl) throws IOException {
    return inetAddress.isReachable(ttl);
  }

  /**
   * @see java.net.InetAddress#isReachable(NetworkInterface, int, int) 
   * @param netif network interface
   * @param ttl time-to-live
   * @param timeout timeout
   * @return flag
   * @throws IOException
   */
  public boolean isReachable(NetworkInterface netif, int ttl, int timeout) 
      throws IOException {
    return inetAddress.isReachable(netif, ttl, timeout);
  }

  /**
   * @see java.net.InetAddress#isSiteLocalAddress()
   * @return flag
   */
  public boolean isSiteLocalAddress() {
    return inetAddress.isSiteLocalAddress();
  }

  private void readObject(ObjectInputStream is) throws IOException {
    StringBuilder sb = new StringBuilder();
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
