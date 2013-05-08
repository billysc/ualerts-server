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

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A MAC Address.
 *
 * @author Carl Harris
 */
public class MacAddress implements Serializable {

  private static final long serialVersionUID = 6224928416704233645L;

  /*
   * IEEE format
   * e.g. 1A-2B-3C-4D-5E-6F or 1a:2b:3c:4d:5e:6f  
   */
  private static final String IEEE_FORMAT =
      "^(\\p{XDigit}?\\p{XDigit})[:-](\\p{XDigit}?\\p{XDigit})"
      + "[:-](\\p{XDigit}?\\p{XDigit})[:-](\\p{XDigit}?\\p{XDigit})"
      + "[:-](\\p{XDigit}?\\p{XDigit})[:-](\\p{XDigit}?\\p{XDigit})$";
  
  /*
   * Dot-delimited format (a.k.a. Cisco format) regex
   * e.g. 1a2b.3c4d.5e6f
   */
  private static final String DOT_FORMAT =
      "^(\\p{XDigit}\\p{XDigit}\\p{XDigit}\\p{XDigit})"
      + "\\.(\\p{XDigit}\\p{XDigit}\\p{XDigit}\\p{XDigit})"
      + "\\.(\\p{XDigit}\\p{XDigit}\\p{XDigit}\\p{XDigit})$";

  /*
   * Undelimited format regex
   * e.g. 1a2b3c4d5e6f 
   */
  private static final String UNDELIMITED_FORMAT =
      "^\\p{XDigit}\\p{XDigit}\\p{XDigit}\\p{XDigit}"
      + "\\p{XDigit}\\p{XDigit}\\p{XDigit}\\p{XDigit}"
      + "\\p{XDigit}\\p{XDigit}\\p{XDigit}\\p{XDigit}$";

  private static final Pattern[] PATTERNS = new Pattern[] {
    Pattern.compile(IEEE_FORMAT), 
    Pattern.compile(DOT_FORMAT), 
    Pattern.compile(UNDELIMITED_FORMAT)
  };

  private static final int MAC_ADDRESS_LENGTH = 6;
  
  /**
   * The delimiter used in the canonical (IEEE) form for a MAC address
   */
  public static final char CANONICAL_DELIMITER = '-';
  
  private final String macAddress;
  private final byte[] octets;

  /**
   * Constructs a new instance.
   * @param s string representation of a MAC address
   */
  public MacAddress(String s) {
    Matcher matcher = getMatcher(s);
    if (matcher != null) {
      octets = extractOctets(matcher);
      macAddress = getCanonicalForm(octets);
    }
    else {
      throw new IllegalArgumentException("Invalid MAC address format");
    }
  }

  /**
   * Determines whether a given string is a valid MAC address representation.
   * @param s the string to test
   * @return {@code true} if the {@code s} is a valid representation of a
   *    MAC address
   */
  public static boolean isValid(String s) {
    return getMatcher(s) != null;
  }
  
  /**
   * Gets the binary representation of a MAC address
   * @return an array of octets representing the binary-encoded MAC address
   */
  public byte[] getOctets() {
    return octets;
  }
  
  /**
   * Converts a binary-encoded MAC address to a string representation.
   * @param octets the binary-encoded MAC address
   * @return string representation of the {@code octets}
   */
  public static String getCanonicalForm(byte[] octets) {
    StringBuilder sb = new StringBuilder();
    final int mask = 0x100;
    final int minLength = 2;
    for (int i = 0; i < octets.length; i++) {
      String hex = Integer.toHexString(octets[i] | mask);
      sb.append(hex.substring(hex.length() - minLength));
      if (i < octets.length - 1) {
        sb.append(CANONICAL_DELIMITER);
      }
    }
    return sb.toString().toUpperCase();
  }
  
  @Override
  public boolean equals(Object o) {
    if (o == this) return true;
    boolean equals = false;
    if (o instanceof MacAddress) {
      MacAddress that = (MacAddress) o;
      equals = this.macAddress.equals(that.macAddress);
    }
    return equals;
  }
  
  @Override
  public int hashCode() {
    return macAddress.hashCode();
  }
  
  @Override
  public String toString() {
    return macAddress;
  }
  
  private static Matcher getMatcher(String s) {
    if (s == null) return null;
    for (Pattern pattern : PATTERNS) {
      Matcher matcher = pattern.matcher(s);
      if (matcher.matches()) {
        return matcher;
      }
    }
    return null;
  }
  
  private static byte[] extractOctets(Matcher matcher) {
    if (matcher.groupCount() == 0) {
      return extractUndelimitedOctets(matcher);
    }
    else {
      return extractDelimitedOctets(matcher);
    }
  }

  private static byte[] extractDelimitedOctets(Matcher matcher) {
    byte[] octets = new byte[MAC_ADDRESS_LENGTH];
    int octetIndex = 0;
    int groupCount = matcher.groupCount();
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < groupCount; i++) {
      sb.delete(0, sb.length());
      sb.append(matcher.group(i + 1));
      int fieldLength = sb.length();
      if (fieldLength % 2 != 0) {
        sb.insert(0, '0');
      }
      String field = sb.toString();
      final int radix = 16;
      for (int j = 0; j < fieldLength; j = j + 2) {
        octets[octetIndex++] = (byte) Integer.parseInt(
            field.substring(j, j + 2), radix);
      }
    }
    return octets;
  }
  
  private static byte[] extractUndelimitedOctets(Matcher matcher) {
    byte[] octets = new byte[MAC_ADDRESS_LENGTH];
    int octetIndex = 0;
    String field = matcher.group(0);
    int fieldLength = matcher.group(0).length();
    assert fieldLength % 2 == 0;
    final int radix = 16;
    for (int i = 0; i < fieldLength; i = i + 2) {
      octets[octetIndex++] = (byte) 
          Integer.parseInt(field.substring(i, i + 2), radix);
    }
    return octets;
  }

}
