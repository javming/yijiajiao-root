package com.yijiajiao.root.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

public class Config {
    private static final ResourceBundle baseConfig = ResourceBundle.getBundle("config");

    private static Locale locale=Locale.getDefault();

    private static final Properties config = new Properties();

    static {
        try {
            config.load(new FileInputStream(baseConfig.getString("localConfig")));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private Config() {}

    public static String getString(String key) {
        return config.getProperty(key);
    }

    public static Integer getInt(String key) {
        return Integer.parseInt(config.getProperty(key));
    }

    public static String getBaseString(String key){
        return baseConfig.getString(key);
    }


    public static void main(String[] args) {
        String user = Config.getString("user_server");
        System.out.println(user);
    }
}