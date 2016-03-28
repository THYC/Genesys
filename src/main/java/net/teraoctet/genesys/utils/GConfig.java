package net.teraoctet.genesys.utils;
import java.io.File;
import java.io.IOException;

import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;

public class GConfig {
    public static File file = new File("config/genesys/genesys.conf");
    @SuppressWarnings("StaticNonFinalUsedInInitialization")
    public static ConfigurationLoader<?> manager = HoconConfigurationLoader.builder().setFile(file).build();
    public static ConfigurationNode config = manager.createEmptyNode(ConfigurationOptions.defaults());
    
    public static void setup() {
        try {
            if (!file.exists()) {
                file.createNewFile();
                config.getNode("mysql", "use").setValue(false);
                config.getNode("mysql", "host").setValue("localhost");
                config.getNode("mysql", "port").setValue(3306);
                config.getNode("mysql", "username").setValue("root");
                config.getNode("mysql", "password").setValue("password");
                config.getNode("mysql", "database").setValue("minecraft");
                config.getNode("limits", "MAX_TEMPBAN_TIME_IN_SECONDS").setValue(3600);
                config.getNode("limits", "MAX_MUTE_TIME_IN_SECONDS").setValue(600);
                config.getNode("version").setValue(1);
                manager.save(config);
            }
            config = manager.load();
        } catch (IOException e) { e.printStackTrace(); }
    }
    
    public static String SERVER_TITLE() { return config.getNode("server", "title").getString(); }
    public static String SERVER_SUBTITLE() { return config.getNode("server", "subtitle").getString(); }
    public static String SERVER_URLWEB() { return config.getNode("server", "urlweb").getString(); }
    
    public static boolean MYSQL_USE() { return config.getNode("mysql", "use").getBoolean(); }
    public static String MYSQL_HOST() { return config.getNode("mysql", "host").getString(); }
    public static int MYSQL_PORT() { return config.getNode("mysql", "port").getInt(); }
    public static String MYSQL_USERNAME() { return config.getNode("mysql", "username").getString(); }
    public static String MYSQL_PASSWORD() { return config.getNode("mysql", "password").getString(); }
    public static String MYSQL_DATABASE() { return config.getNode("mysql", "database").getString(); }

    public static int LIMITS_MAX_TEMPBAN_TIME_IN_SECONDS() { return config.getNode("limits", "MAX_TEMPBAN_TIME_IN_SECONDS").getInt(); }
    public static int LIMITS_MAX_MUTE_TIME_IN_SECONDS() { return config.getNode("limits", "MAX_MUTE_TIME_IN_SECONDS").getInt(); }
}