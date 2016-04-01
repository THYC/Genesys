package net.teraoctet.genesys.commands;

import net.teraoctet.genesys.faction.GFaction;
import net.teraoctet.genesys.player.GPlayer;
import net.teraoctet.genesys.utils.GData;
import static net.teraoctet.genesys.utils.GData.getGPlayer;
import static net.teraoctet.genesys.utils.MessageManager.ALREADY_FACTION_MEMBER;
import static net.teraoctet.genesys.utils.MessageManager.FACTION_CREATED_SUCCESS;
import static net.teraoctet.genesys.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.genesys.utils.MessageManager.NO_PERMISSIONS;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;

public class CommandFactionCreate implements CommandExecutor {
        
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {

        if(src instanceof Player && src.hasPermission("genesys.")) {
            Player player = (Player) src;
            GPlayer gplayer = getGPlayer(player.getIdentifier());
            
            if(gplayer.getID_faction() != 0) {
                src.sendMessage(ALREADY_FACTION_MEMBER());
            } else {
                String factionName = ctx.<String> getOne("name").get();
                GFaction gfaction = new GFaction(factionName,0,0);
                gfaction.insert();
                GData.commit();
                GData.addGFaction(factionName, gfaction); 
                src.sendMessage(FACTION_CREATED_SUCCESS(factionName));
                gplayer.setID_faction(gfaction.getID());
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
