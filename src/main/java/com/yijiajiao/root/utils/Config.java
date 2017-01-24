package com.yijiajiao.root.utils;

import java.util.ResourceBundle;

public class Config {

  private static final ResourceBundle config = ResourceBundle.getBundle("config");

  private Config() {
  }

  public static String getString(String key) {
    return config.getString(key);
  }

  public static Integer getInt(String key) {
    return Integer.parseInt(config.getString(key));
  }

}