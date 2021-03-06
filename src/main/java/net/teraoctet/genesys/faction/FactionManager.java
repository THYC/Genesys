package net.teraoctet.genesys.faction;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.teraoctet.genesys.player.GPlayer;
import static net.teraoctet.genesys.utils.GData.commit;
import static net.teraoctet.genesys.utils.GData.getGFaction;
import static net.teraoctet.genesys.utils.GData.getGPlayer;
import static net.teraoctet.genesys.utils.GData.getPlayers;
import static net.teraoctet.genesys.utils.GData.removeGFaction;
import org.spongepowered.api.entity.living.player.Player;

public class FactionManager {
    
    public FactionManager(){}
    
    /**
     * Si TRUE le joueur est owner/chef de faction
     * @param player joueur
     * @return Boolean
     */
    public static Boolean isOwner(Player player) {
        GPlayer gplayer = getGPlayer(player.getIdentifier());
        return gplayer.getFactionRank() == 1;
    }
    
    /**
     * Si TRUE le joueur est owner/chef de faction
     * @param gplayer Object GPlayer
     * @return Boolean
     */
    public static Boolean isOwner(GPlayer gplayer) { return gplayer.getFactionRank() == 1; }
    
    /**
     * Si TRUE le joueur est membre d'une faction
     * @param gplayer Object GPlayer
     * @return Boolean
     */
    public static Boolean hasAnyFaction(GPlayer gplayer) { return gplayer.getID_faction() != 0; }
    
    /**
     * Si TRUE le joueur est membre d'une faction
     * @param player joueur 
     * @return Boolean
     */
    public static Boolean hasAnyFaction(Player player) {
        GPlayer gplayer = getGPlayer(player.getIdentifier());
        return gplayer.getFactionRank() == 1;
    }
    
    /**
     * Calcul une nouvelle clé unique pour GFaction
     * @return Clé type Int
     */
    public Integer newKey(){
        int key = 1;
        
        for (String uuid : getPlayers().keySet()) {
            if(getPlayers().get(uuid).getID_faction() >= key){
               key = getPlayers().get(uuid).getID_faction() + 1;
            }
        }
        return key;
    }
    
    /**
     * Expulse tous les joueurs d'une faction associé à son ID
     * et supprime la faction
     * @param id_faction id de la faction a supprimer
     */
    /*public void removeFaction(int id_faction){
        for (String uuid : getPlayers().keySet()) {
            if(getPlayers().get(uuid).getID_faction() == id_faction){
                getPlayers().get(uuid).setID_faction(0);
                getPlayers().get(uuid).setFactionRank(0);
                getPlayers().get(uuid).update();
                GFaction gfaction = getGFaction(id_faction);
                gfaction.delete();
                removeGFaction(id_faction);
                commit();
            }
        }
        GData.commit();
    }*/
    
    public void removeFaction(int id_faction){
        for(Map.Entry<String,GPlayer> p : getPlayers().entrySet()){
            if(p.getValue().getID_faction() == id_faction){
                p.getValue().setFactionRank(0);
                p.getValue().setID_faction(0);
                p.getValue().update();
            }
        }
        GFaction gfaction = getGFaction(id_faction);
        gfaction.delete();
        removeGFaction(id_faction);
        commit();
    }
    
    /**
     * Retourne la liste de joueur d'une faction associé à son ID
     * @param id_faction id de la faction
     * @return List
     */
    public List<String> getFactionPlayers(int id_faction){
        List<String> listPlayer =  new ArrayList<>() ;
        getPlayers().entrySet().stream().filter((p) -> (p.getValue().getID_faction() == id_faction)).forEach((p) -> {
            listPlayer.add(p.getValue().getName());
        });
        return listPlayer;
    }
    
    /**
     * Retourne la liste de joueur d'une faction associé à son ID et son Rank
     * @param id_faction id de la faction
     * @param rank filtre le rank a retourner
     * @return List
     */
    public List<String> getFactionPlayers(int id_faction, int rank){
        List<String> listPlayer =  new ArrayList<>() ;
        getPlayers().entrySet().stream().filter((p) -> (p.getValue().getID_faction() == id_faction && p.getValue().getFactionRank() == rank)).forEach((p) -> {
            listPlayer.add(p.getValue().getName());
        });
        return listPlayer;
    }
    
    /**
     * Retourne le owner d'une faction
     * @param id_faction id de la faction
     * @return Objet GPlayer
     */
    public GPlayer getOwner(int id_faction){
        for(Map.Entry<String,GPlayer> p : getPlayers().entrySet()){
            if(p.getValue().getID_faction() == id_faction && p.getValue().getFactionRank() == 1){
                return p.getValue();
            }
        }
        return null;
    }
    
    /**
     * Retourne le nom du grade correspondant à son ID
     * @param rank rank a retourner
     * @return String
     */
    public String rankIDtoString(int rank){
        String grade;
        switch(rank) {
            case 1:
                grade = "Chef";
                break;
            case 2:
                grade = "Sous-Chef";
                break;
            case 3:
                grade = "Officier";
                break;
            case 4:
                grade = "Membre";
                break;
            case 5:
                grade = "Recrue";
                break;
            default:
                grade = "ERREUR";
                break;
        }
        return grade;
    }
}
