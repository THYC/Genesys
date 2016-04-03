package net.teraoctet.genesys.commands.faction;

import static net.teraoctet.genesys.Genesys.factionManager;
import net.teraoctet.genesys.faction.GFaction;
import net.teraoctet.genesys.player.GPlayer;
import static net.teraoctet.genesys.utils.GData.getGFaction;
import static net.teraoctet.genesys.utils.GData.getGPlayer;
import static net.teraoctet.genesys.utils.MessageManager.FACTION_DELETED_NOTIFICATION;
import static net.teraoctet.genesys.utils.MessageManager.FACTION_DELETED_SUCCESS;
import static net.teraoctet.genesys.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.genesys.utils.MessageManager.NO_FACTION;
import static net.teraoctet.genesys.utils.MessageManager.NO_PERMISSIONS;
import static net.teraoctet.genesys.utils.MessageManager.WRONG_NAME;
import static net.teraoctet.genesys.utils.MessageManager.WRONG_RANK;
import static org.spongepowered.api.Sponge.getGame;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;

public class CommandFactionDelete implements CommandExecutor {
        
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {

        if(src instanceof Player && src.hasPermission("genesys.faction.delete")) {
            GPlayer gplayer = getGPlayer(src.getIdentifier());
            
            if(factionManager.hasAnyFaction(gplayer)) {
                if(factionManager.isOwner(gplayer)){
                    String name = ctx.<String> getOne("name").get();
                    GFaction gfaction = getGFaction(gplayer.getID_faction());
                    String factionName = gfaction.getName();
                    
                    if(factionName.toLowerCase().contains(name.toLowerCase())) {
                        int id_faction = gplayer.getID_faction();
                        factionManager.removeFaction(id_faction);
                        src.sendMessage(FACTION_DELETED_SUCCESS(factionName));
                        getGame().getServer().getBroadcastChannel().send(FACTION_DELETED_NOTIFICATION(factionName));
                        return CommandResult.success();
                    } else {
                        src.sendMessage(WRONG_NAME());
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
