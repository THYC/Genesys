package net.teraoctet.genesys.commands.faction;

import static net.teraoctet.genesys.Genesys.factionManager;
import net.teraoctet.genesys.faction.GFaction;
import net.teraoctet.genesys.player.GPlayer;
import static net.teraoctet.genesys.utils.GConfig.FACTION_NAME_MAX_SIZE;
import static net.teraoctet.genesys.utils.GConfig.FACTION_NAME_MIN_SIZE;
import static net.teraoctet.genesys.utils.GData.getGFaction;
import static net.teraoctet.genesys.utils.GData.getGPlayer;
import static net.teraoctet.genesys.utils.MessageManager.FACTION_RENAMED_SUCCESS;
import static net.teraoctet.genesys.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.genesys.utils.MessageManager.NO_FACTION;
import static net.teraoctet.genesys.utils.MessageManager.NO_PERMISSIONS;
import static net.teraoctet.genesys.utils.MessageManager.WRONG_CHARACTERS_NUMBER;
import static net.teraoctet.genesys.utils.MessageManager.WRONG_RANK;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;

public class CommandFactionRename implements CommandExecutor {
        
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {

        if(src instanceof Player && src.hasPermission("genesys.faction.rename")) {
            GPlayer gplayer = getGPlayer(src.getIdentifier());
            
            if(factionManager.hasAnyFaction(gplayer)) {
                if(gplayer.getFactionRank() <= 2){
                    String newName = ctx.<String> getOne("name").get();
                    if(newName.length() >= FACTION_NAME_MIN_SIZE() && newName.length() <= FACTION_NAME_MAX_SIZE()) {
                        GFaction gfaction = getGFaction(gplayer.getID_faction());
                        String oldName = gfaction.getName();
                        gfaction.setName(newName);
                        gfaction.update();
                        src.sendMessage(FACTION_RENAMED_SUCCESS(oldName, newName));
                        return CommandResult.success(); 
                    } else {
                        src.sendMessage(WRONG_CHARACTERS_NUMBER(Integer.toString(FACTION_NAME_MIN_SIZE()), Integer.toString(FACTION_NAME_MAX_SIZE())));
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
