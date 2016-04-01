package net.teraoctet.genesys.commands;

import net.teraoctet.genesys.faction.GFaction;
import net.teraoctet.genesys.player.GPlayer;
import static net.teraoctet.genesys.utils.GData.getGFaction;
import static net.teraoctet.genesys.utils.GData.getGPlayer;
import static net.teraoctet.genesys.utils.MessageManager.MESSAGE;
import static net.teraoctet.genesys.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.genesys.utils.MessageManager.NO_FACTION;
import static net.teraoctet.genesys.utils.MessageManager.NO_PERMISSIONS;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;

public class CommandFactionMemberslist implements CommandExecutor {
        
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {

        if(src instanceof Player && src.hasPermission("genesys.")) {
            GPlayer gplayer = getGPlayer(src.getIdentifier());
            GFaction gfaction = getGFaction(src.getName());
            
            //si le joueur n'est dans aucune faction
            if(gplayer.getID_faction() == 0) {
                src.sendMessage(NO_FACTION());
                return CommandResult.empty();
            }
            //si le joueur est membre d'une faction
            else {
                src.sendMessage(MESSAGE("&2Listes des membres de " + gfaction.getName() + " : &a"));
                return CommandResult.success();
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
