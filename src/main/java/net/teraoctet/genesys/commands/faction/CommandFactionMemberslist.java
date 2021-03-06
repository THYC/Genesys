package net.teraoctet.genesys.commands.faction;

import java.util.List;
import static net.teraoctet.genesys.Genesys.factionManager;
import net.teraoctet.genesys.faction.FactionManager;
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

        if(src instanceof Player && src.hasPermission("genesys.faction.memberslist")) {
            GPlayer gplayer = getGPlayer(src.getIdentifier());
            
            //si le joueur est membre d'une faction
            if(FactionManager.hasAnyFaction(gplayer)) {
                GFaction gfaction = getGFaction(gplayer.getID_faction());
                List list = factionManager.getFactionPlayers(gfaction.getID());
                
                src.sendMessage(MESSAGE("&2Listes des membres de " + gfaction.getName() + " : &a" + list));
                
                
                //src.sendMessage(MESSAGE("&2Listes des membres de " + gfaction.getName() + " : &a" + String.valueOf(factionManager.getPlayers(gfaction.getID()).size())));
                return CommandResult.success();
            }
            
            //si le joueur n'est dans aucune faction
            else {
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
