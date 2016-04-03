package net.teraoctet.genesys.utils;

import com.flowpowered.math.vector.Vector3d;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.teraoctet.genesys.player.GPlayer;
import static net.teraoctet.genesys.utils.GData.getGPlayer;
import org.spongepowered.api.Sponge;
import static org.spongepowered.api.Sponge.getGame;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.service.user.UserStorageService;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;

public class ServerManager {
    
    public ServerManager(){}
    
    /**
     * retourne l'objet Player
     * @param player nom du joueur à retourner
     * @return 
     */
    public static Player getPlayer(String player){
        try {
            Optional<UserStorageService> service = Sponge.getServiceManager().provide(UserStorageService.class);
            UUID uuid = service.get().getOrCreate(Sponge.getGame().getServer().getGameProfileManager().get(player, false).get()).getUniqueId();
            Optional<User> userOptional = service.get().get(uuid);
            
            if (userOptional.isPresent()){
                return userOptional.get().getPlayer().get();
            }else{
                return null;
            }
        } catch (InterruptedException | ExecutionException ex) {
            Logger.getLogger(ServerManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    /**
     * retourne l'idetifier UUID du joueur
     * @param player nom du joueur à retourner
     * @return 
     * @throws java.lang.InterruptedException 
     * @throws java.util.concurrent.ExecutionException 
     */
    public static String getPlayerUUID(String player) throws InterruptedException, ExecutionException {
        Optional<UserStorageService> service = Sponge.getServiceManager().provide(UserStorageService.class);
        UUID uuid = service.get().getOrCreate(Sponge.getGame().getServer().getGameProfileManager().get(player, false).get()).getUniqueId();
        Optional<User> userOptional = service.get().get(uuid);

        if (userOptional.isPresent()){
            return userOptional.get().getIdentifier();
        }else{
            return null;
        }
    }
    
    /**
     * retourne True si le joueur est en ligne
     * @param playerName nom du joueur à controler
     * @return 
     */
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
    
    /**
     * retourne une date au format longDate
     * @param date date au format double
     * @return 
     */
    public Date doubleToDate(double date){
        double itemDouble = date;
        long itemLong = (long) (itemDouble * 1000);
        Date itemDate = new Date(itemLong);
        return itemDate;
    }
    
    /**
     * double les quotes contenu dans le message
     * @param message chaine du message à modifier
     * @return 
     */
    public String quoteToSQL(String message){
        if(message.contains("'") /*&& !message.contains("''")*/){
            message = message.replace("'", "''");
        }
        return message;
    }
    
    /**
     * Téléporte un joueur et enregistre ces prédentes coordonnées dans le param LastLocation 
     * @param player nom du joeur a téléporter
     * @param world nom du monde d'arrivée
     * @param X 
     * @param Y
     * @param Z
     * @return 
     */
    public boolean teleport(Player player,String world, int X, int Y, int Z){
                 
        Location lastLocation = player.getLocation();
        if(!player.transferToWorld(world, new Vector3d(X, Y, Z))) { return false;}
        GPlayer gplayer = getGPlayer(player.getUniqueId().toString());
        gplayer.setLastposition(DeSerialize.location(lastLocation));
        gplayer.update();
        return true;
    }
}
