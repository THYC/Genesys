package net.teraoctet.genesys.economy;

import static net.teraoctet.genesys.utils.GData.getGPlayer;
import net.teraoctet.genesys.player.GPlayer;
import org.spongepowered.api.entity.living.player.Player;

public class Economy {
    public void Credit(Player player, double amount){
        GPlayer gplayer = getGPlayer(player.getUniqueId().toString());
        double solde = gplayer.getMoney() + amount;
        gplayer.setMoney(solde);
        gplayer.update();
    }
    
    public void Debit(Player player, double amount){
        GPlayer gplayer = getGPlayer(player.getUniqueId().toString());
        double solde = gplayer.getMoney() - amount;
        gplayer.setMoney(solde);
        gplayer.update();
    }    
}
