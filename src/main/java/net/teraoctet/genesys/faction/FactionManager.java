package net.teraoctet.genesys.faction;

import java.util.ArrayList;
import java.util.List;
import net.teraoctet.genesys.player.GPlayer;
import net.teraoctet.genesys.utils.GData;
import static net.teraoctet.genesys.utils.GData.getFactions;
import static net.teraoctet.genesys.utils.GData.getGPlayer;
import static net.teraoctet.genesys.utils.GData.getPlayers;
import org.spongepowered.api.entity.living.player.Player;

public class FactionManager {
    
    public FactionManager(){}
    
    /**
     * Si TRUE le joueur est owner/chef de faction
     * @param player
     * @return 
     */
    public static Boolean isOwner(Player player) {
        GPlayer gplayer = getGPlayer(player.getIdentifier());
        return gplayer.getFactionRank() == 1;
    }
    
    /**
     * Si TRUE le joueur est owner/chef de faction
     * @param gplayer
     * @return 
     */
    public static Boolean isOwner(GPlayer gplayer) { return gplayer.getFactionRank() == 1; }
    
    /**
     * Si TRUE le joueur est membre d'une faction
     * @param gplayer
     * @return
     */
    public static Boolean hasAnyFaction(GPlayer gplayer) { return gplayer.getID_faction() != 0; }
    
    /**
     * Si TRUE le joueur est membre d'une faction
     * @param player
     * @return
     */
    public static Boolean hasAnyFaction(Player player) {
        GPlayer gplayer = getGPlayer(player.getIdentifier());
        return gplayer.getFactionRank() == 1;
    }
    
    /**
     * Calcul une nouvelle clé unique pour GFaction
     * @return 
     */
    public Integer newKey(){
        int size = getFactions().size();
        int key = 1;
        if(size > 0){
            key = getFactions().get(size).getID() + 1;
        }
        return key;
    }
    
    /**
     * Expulse tous les joueurs d'une faction associé à son ID
     * @param id_faction 
     */
    public void removePlayersToFaction(int id_faction){
        for (String uuid : getPlayers().keySet()) {
            if(getPlayers().get(uuid).getID_faction() == id_faction){
                getPlayers().get(uuid).setID_faction(0);
                getPlayers().get(uuid).update();
            }
        }
        GData.commit();
    }
    
    /**
     * Retourne la liste de joueur d'une faction associé à son ID
     * @param id_faction
     * @return 
     */
    public List getListPlayerFaction(int id_faction){
        List listPlayer = null;
        for (String uuid : getPlayers().keySet()) {
            if(getPlayers().get(uuid).getID_faction() == id_faction){
                listPlayer.add(getPlayers().get(uuid).getName());
            }
        }
        return listPlayer;
    }
    
    /**
     * Retourne la liste de joueur d'une faction associé à son ID et son Rank
     * @param id_faction
     * @param rank
     * @return 
     */
    public List getListPlayerFaction(int id_faction, int rank){
        List listPlayer = null;
        for (String uuid : getPlayers().keySet()) {
            if(getPlayers().get(uuid).getID_faction() == id_faction && getPlayers().get(uuid).getFactionRank() == rank){
                listPlayer.add(getPlayers().get(uuid).getName());
            }
        }
        return listPlayer;
    }
}
