package net.teraoctet.genesys.commands.faction;

import static net.teraoctet.genesys.Genesys.factionManager;
import net.teraoctet.genesys.faction.GFaction;
import net.teraoctet.genesys.player.GPlayer;
import static net.teraoctet.genesys.utils.GData.getGFaction;
import static net.teraoctet.genesys.utils.GData.getGPlayer;
import static net.teraoctet.genesys.utils.MessageManager.FACTION_MISSING_BALANCE;
import static net.teraoctet.genesys.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.genesys.utils.MessageManager.NO_FACTION;
import static net.teraoctet.genesys.utils.MessageManager.NO_PERMISSIONS;
import static net.teraoctet.genesys.utils.MessageManager.WITHDRAW_SUCCESS;
import static net.teraoctet.genesys.utils.MessageManager.WRONG_RANK;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;

public class CommandFactionWithdrawal implements CommandExecutor {
        
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {

        if(src instanceof Player && src.hasPermission("genesys.faction.withdrawal")) {
            GPlayer gplayer = getGPlayer(src.getIdentifier());
            
            if(factionManager.hasAnyFaction(gplayer)) {
                if(gplayer.getFactionRank() <= 2){
                    double amount = ctx.<Double> getOne("amount").get();
                    GFaction gfaction = getGFaction(gplayer.getID_faction());
                    double factionMoney = gfaction.getMoney();
                    
                    if(factionMoney >= amount) {
                        double playerMoney = gplayer.getMoney();

                        gfaction.setMoney(factionMoney - amount);
                        playerMoney = playerMoney + amount;
                        gplayer.setMoney(playerMoney);
                        gplayer.update();
                        gfaction.update();

                        src.sendMessage(WITHDRAW_SUCCESS(Double.toString(amount)));
                        return CommandResult.success();
                    } else {
                        src.sendMessage(FACTION_MISSING_BALANCE());
                    }
                } else {
                    src.sendMessage(WRONG_RANK());
                }
            } else {
                src.sendMessage(NO_FACTION());
            }
        } 
        
        else if (src instanceof ConsoleSource) {
            src.sendMessage(NO_CONSOLE());
        }
        
        //si on arrive jusqu'ici c'est que la source n'a pas les permissions pour cette commande ou que quelque chose s'est mal passé
        else {
            src.sendMessage(NO_PERMISSIONS());
        }
                
        return CommandResult.empty();
    }
}
