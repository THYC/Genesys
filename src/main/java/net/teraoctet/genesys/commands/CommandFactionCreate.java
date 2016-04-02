package net.teraoctet.genesys.commands;

import static net.teraoctet.genesys.Genesys.factionManager;
import net.teraoctet.genesys.faction.FactionManager;
import net.teraoctet.genesys.faction.GFaction;
import net.teraoctet.genesys.player.GPlayer;
import static net.teraoctet.genesys.utils.GConfig.FACTION_NAME_MAX_SIZE;
import static net.teraoctet.genesys.utils.GConfig.FACTION_NAME_MIN_SIZE;
import net.teraoctet.genesys.utils.GData;
import static net.teraoctet.genesys.utils.GData.getGPlayer;
import static net.teraoctet.genesys.utils.MessageManager.ALREADY_FACTION_MEMBER;
import static net.teraoctet.genesys.utils.MessageManager.FACTION_CREATED_SUCCESS;
import static net.teraoctet.genesys.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.genesys.utils.MessageManager.NO_PERMISSIONS;
import static net.teraoctet.genesys.utils.MessageManager.WRONG_CHARACTERS_NUMBER;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;

public class CommandFactionCreate implements CommandExecutor {
        
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {

        if(src instanceof Player && src.hasPermission("genesys.faction.create")) {
            Player player = (Player) src;
            GPlayer gplayer = getGPlayer(player.getIdentifier());
            
            if(factionManager.hasAnyFaction(gplayer)) {
                src.sendMessage(ALREADY_FACTION_MEMBER());
            } else {
                String factionName = ctx.<String> getOne("name").get();
                if(factionName.length() >= FACTION_NAME_MIN_SIZE() && factionName.length() <= FACTION_NAME_MAX_SIZE()) {
                    int key = factionManager.newKey();
                    GFaction gfaction = new GFaction(key, factionName,"N",0,0,0,0,0,0,0);
                    gplayer.setFactionRank(1);
                    gplayer.setID_faction(key);
                    gplayer.update();
                    gfaction.insert();
                    GData.commit();
                    GData.addGFaction(key, gfaction); 
                    src.sendMessage(FACTION_CREATED_SUCCESS(factionName));
                    return CommandResult.success();
                } else {
                    src.sendMessage(WRONG_CHARACTERS_NUMBER(Integer.toString(FACTION_NAME_MIN_SIZE()), Integer.toString(FACTION_NAME_MAX_SIZE())));
                }
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
