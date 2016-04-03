package net.teraoctet.genesys.commands.faction;

import static net.teraoctet.genesys.Genesys.factionManager;
import net.teraoctet.genesys.faction.GFaction;
import net.teraoctet.genesys.player.GPlayer;
import static net.teraoctet.genesys.utils.GData.getGFaction;
import static net.teraoctet.genesys.utils.GData.getGPlayer;
import static net.teraoctet.genesys.utils.MessageManager.LEAVING_FACTION_SUCCESS;
import static net.teraoctet.genesys.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.genesys.utils.MessageManager.NO_FACTION;
import static net.teraoctet.genesys.utils.MessageManager.NO_PERMISSIONS;
import static net.teraoctet.genesys.utils.MessageManager.OWNER_CANNOT_LEAVE;
import static org.spongepowered.api.Sponge.getGame;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;

public class CommandFactionLeave implements CommandExecutor {
        
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {

        if(src instanceof Player && src.hasPermission("genesys.faction.setowner")) {
            GPlayer gplayer = getGPlayer(src.getIdentifier());
            int id_faction = gplayer.getID_faction();
            GFaction gfaction = getGFaction(id_faction);
            
            if(factionManager.hasAnyFaction(gplayer)) {
                if(factionManager.isOwner(gplayer)) {
                    if(factionManager.getFactionPlayers(id_faction).size() > 1) {
                        src.sendMessage(OWNER_CANNOT_LEAVE());
                    } else {
                        getGame().getCommandManager().process(src, "faction delete " + gfaction.getName());
                    }
                } else {
                    String factionName = gfaction.getName();
                    gplayer.setID_faction(0);
                    gplayer.setFactionRank(0);
                    gplayer.update();
                    src.sendMessage(LEAVING_FACTION_SUCCESS(factionName));
                    //ENVOYER UNE NOTIFICATION DANS LE CANAL DE GUILDE
                    return CommandResult.success();
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
