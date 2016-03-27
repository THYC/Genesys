package net.teraoctet.genesys.utils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static org.spongepowered.api.Sponge.getGame;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

public class DeSerialize {

    public static String list(List<String> list) {

            if(list.isEmpty()) return "";

            StringBuilder serialized = new StringBuilder();
            for(String s : list) serialized.append(s + ",");
            serialized.deleteCharAt(serialized.length() - 1);

            return serialized.toString();

    }

    public static String location(String world, double x, double y, double z) {
            return world + ":" + x + ":" + y + ":" + z;
    }
    
    public static String location(Location location) {
            return location.getExtent().getUniqueId().toString() + ":" + String.valueOf(location.getBlockX()) + ":" + String.valueOf(location.getBlockY()) + ":" + String.valueOf(location.getBlockZ());
    }

    public static Location<World> getLocation(String s) {
        String loc[] = s.split(":");
        //Optional<World> world = getGame().getServer().getWorld(loc[0]);
        UUID uuid = UUID.fromString(loc[0]);
        Optional<World> world = getGame().getServer().getWorld(uuid);
        Location<World> location = new Location<>(world.get(), Double.valueOf(loc[1]), Double.valueOf(loc[2]), Double.valueOf(loc[3]));
        return location;
    }

    public static String messages(List<String> messages) {

        if(messages.isEmpty()) return "";

        StringBuilder serialized = new StringBuilder();
        for(String s : messages) serialized.append(s + "-;;");
        for(int i = 1; i <= 3; i++) serialized.deleteCharAt(serialized.length() - 1);

        return serialized.toString();

    }	
}
