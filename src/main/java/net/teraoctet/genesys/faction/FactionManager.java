package net.teraoctet.genesys.faction;

import net.teraoctet.genesys.player.GPlayer;
import static net.teraoctet.genesys.utils.GData.getGPlayer;
import org.spongepowered.api.entity.living.player.Player;

public class FactionManager {
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
}
