package net.teraoctet.genesys.commands.faction;

import static net.teraoctet.genesys.Genesys.factionManager;
import net.teraoctet.genesys.player.GPlayer;
import static net.teraoctet.genesys.utils.GData.getGFaction;
import static net.teraoctet.genesys.utils.GData.getGPlayer;
import static net.teraoctet.genesys.utils.MessageManager.FACTION_CHEF_GRADE_GIVEN;
import static net.teraoctet.genesys.utils.MessageManager.FACTION_NEW_CHEF;
import static net.teraoctet.genesys.utils.MessageManager.FACTION_YOU_ARE_NEW_CHEF;
import static net.teraoctet.genesys.utils.MessageManager.NOT_IN_SAME_FACTION;
import static net.teraoctet.genesys.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.genesys.utils.MessageManager.NO_FACTION;
import static net.teraoctet.genesys.utils.MessageManager.NO_PERMISSIONS;
import static net.teraoctet.genesys.utils.MessageManager.WRONG_RANK;
import static org.spongepowered.api.Sponge.getGame;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;

public class CommandFactionSetowner implements CommandExecutor {
        
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {

        if(src instanceof Player && src.hasPermission("genesys.faction.setowner")) {
            GPlayer gplayer = getGPlayer(src.getIdentifier());
            
            if(factionManager.hasAnyFaction(gplayer)) {
                if(factionManager.isOwner(gplayer)) {
                    Player targetPlayer = ctx.<Player> getOne("player").get();
                    GPlayer target_gplayer = getGPlayer(targetPlayer.getIdentifier());
                    int target_id_faction = target_gplayer.getID_faction();
                    int player_id_faction = gplayer.getID_faction();
                    
                    if(player_id_faction == target_id_faction) {
                        String factionName = getGFaction(player_id_faction).getName();
                        gplayer.setFactionRank(2);
                        target_gplayer.setFactionRank(1);
                        gplayer.update();
                        target_gplayer.update();
                        src.sendMessage(FACTION_CHEF_GRADE_GIVEN(targetPlayer.getName()));
                        targetPlayer.sendMessage(FACTION_YOU_ARE_NEW_CHEF());
                        getGame().getServer().getBroadcastChannel().send(FACTION_NEW_CHEF(targetPlayer.getName(), factionName));
                        return CommandResult.success();   
                    } else {
                        src.sendMessage(NOT_IN_SAME_FACTION(targetPlayer.getName()));
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
