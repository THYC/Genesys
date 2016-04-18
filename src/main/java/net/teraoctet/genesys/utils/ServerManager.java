package net.teraoctet.genesys.utils;

import com.flowpowered.math.vector.Vector3d;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.TimeZone;
import net.teraoctet.genesys.player.GPlayer;
import static net.teraoctet.genesys.utils.GData.getGPlayer;
import org.spongepowered.api.Sponge;
import static org.spongepowered.api.Sponge.getGame;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.profile.GameProfile;
import org.spongepowered.api.service.ProviderRegistration;
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
    public Optional<Player> getPlayer(String player){
        Optional<ProviderRegistration<UserStorageService>> opt_provider = Sponge.getServiceManager().getRegistration(UserStorageService.class);
        if(opt_provider.isPresent()) {
            ProviderRegistration<UserStorageService> provider = opt_provider.get();
            UserStorageService service = provider.getProvider();
            Optional<User> opt_user = service.get(player);
            
            if(opt_user.isPresent()) {
                return Optional.of(opt_user.get().getPlayer().get());
            }else{
                return Optional.empty();
            }
        }
        return Optional.empty();
    }
    
    /**
     * retourne l'idetifier UUID du joueur
     * @param player nom du joueur à retourner
     * @return 
     */
    public Optional<String> getPlayerUUID(String player){
        Optional<ProviderRegistration<UserStorageService>> opt_provider = Sponge.getServiceManager().getRegistration(UserStorageService.class);
        if(opt_provider.isPresent()) {
            ProviderRegistration<UserStorageService> provider = opt_provider.get();
            UserStorageService service = provider.getProvider();
            Optional<User> opt_user = service.get(player);
            
            if(opt_user.isPresent()) {
                return Optional.of(opt_user.get().getIdentifier());
            }else{
                return Optional.empty();
            }
        }
        return Optional.empty();
    }
    
    /**
     * retourne le GameProfile du joueur
     * @param player nom du joueur à retourner
     * @return 
     */
    public Optional<GameProfile> getPlayerProfile(String player){
        Optional<ProviderRegistration<UserStorageService>> opt_provider = Sponge.getServiceManager().getRegistration(UserStorageService.class);
        if(opt_provider.isPresent()) {
            ProviderRegistration<UserStorageService> provider = opt_provider.get();
            UserStorageService service = provider.getProvider();
            Optional<User> opt_user = service.get(player);
            
            if(opt_user.isPresent()) {
                return Optional.of(opt_user.get().getProfile());
            }else{
                return Optional.empty();
            }
        }
        return Optional.empty();
    }
    
    /**
     * retourne le GameProfile du joueur
     * @param player nom du joueur à retourner
     * @return 
     */
    public Optional<Inventory> getPlayerInventory(String player){
        Optional<ProviderRegistration<UserStorageService>> opt_provider = Sponge.getServiceManager().getRegistration(UserStorageService.class);
        if(opt_provider.isPresent()) {
            ProviderRegistration<UserStorageService> provider = opt_provider.get();
            UserStorageService service = provider.getProvider();
            Optional<User> opt_user = service.get(player);
            
            if(opt_user.isPresent()) {
                return Optional.of(opt_user.get().getInventory());
            }else{
                return Optional.empty();
            }
        }
        return Optional.empty();
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
     * 
     * @param milliseconds
     * @return 
     */
    public static String dateShortToString(double milliseconds) {
        int days = 0;
        int hours = 0;
        int minutes = 0;

        while(milliseconds >= 1000 * 60 * 60 * 24) { days += 1; milliseconds -= 1000 * 60 * 60 * 24; }
        while(milliseconds >= 1000 * 60 * 60) { hours += 1; milliseconds -= 1000 * 60 * 60; }
        while(milliseconds >= 1000 * 60) { minutes += 1; milliseconds -= 1000 * 60; }

        String time = "";
        if(days > 0) time = days + "d ";
        time = time + hours + "h ";
        time = time + minutes + "m";

        return time;

    }
    
    /**
     * 
     * @param time
     * @param unit
     * @return 
     */
    public static double dateToMilliseconds(double time, String unit) {
        if(unit.equalsIgnoreCase("days")) { return time * 1000 * 60 * 60 * 24; }
        else if(unit.equalsIgnoreCase("hours")) { return time * 1000 * 60 * 60; }
        else if(unit.equalsIgnoreCase("minutes")) { return time * 1000 * 60; }
        else if(unit.equalsIgnoreCase("seconds")) { return time * 1000; }

        return 0;
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
     * retourne la date au format dd MMM yyyy HH:mm:ss
     * @return 
     */
    public String dateToString(){
        Calendar cal = Calendar.getInstance();
	SimpleDateFormat simpleDate = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
	simpleDate.setTimeZone(TimeZone.getTimeZone("GMT"));
	return simpleDate.format(cal.getTime());
    }
    
    /**
     * retourne la date au format ss
     * @return 
     */
    public Long dateToLong(){
        Calendar cal = Calendar.getInstance();
	return cal.getTimeInMillis();
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
