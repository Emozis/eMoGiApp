package com.meta.emogi.util;
import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
public class ConfigUtil {
    private Properties properties;

    public ConfigUtil(Context context){
        properties = new Properties();
        try {
            InputStream inputStream = context.getAssets().open("config.properties");
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}
