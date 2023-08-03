package com.hci.exp;


import com.github.megatronking.stringfog.xor.StringFogImpl;

/**
 * Generated code from StringFog gradle plugin. Do not modify!
 */
public final class StringFog {
  private static final StringFogImpl IMPL = new StringFogImpl();

  public static String encrypt(String value) {
    return IMPL.encrypt(value, "senight");
  }

  public static String decrypt(String value) {
    return IMPL.decrypt(value, "senight");
  }

  public static boolean overflow(String value) {
    return IMPL.overflow(value, "senight");
  }

}
