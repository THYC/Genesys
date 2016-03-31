package net.teraoctet.genesys.utils;

import java.util.Optional;
import org.spongepowered.api.Sponge;
import static org.spongepowered.api.Sponge.getGame;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.service.user.UserStorageService;
import org.spongepowered.api.text.Text;

public class GServer {

    public static Player getPlayer(String player) {

        Player found = null;        
        Optional<UserStorageService> userStorage = Sponge.getServiceManager().provide(UserStorageService.class);
        UserStorageService userService = userStorage.get();
        Optional<User> userOptional = userService.get(player); 
        if (userOptional.isPresent()) found = userOptional.get().getPlayer().get();
        
        return found;
    }
        
    public static boolean isOnline(String playerName){
        
        boolean online = false;
        for (Player player : Sponge.getServer().getOnlinePlayers()) {
            if(player.getName().toLowerCase().equals(playerName.toLowerCase())){ online = true;}
        }
        return online;
    }

    public static void broadcast(Text text) {
        getGame().getServer().getBroadcastChannel().send(text);
    }

}
