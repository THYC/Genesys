package net.teraoctet.genesys.commands.faction;

import static net.teraoctet.genesys.Genesys.factionManager;
import net.teraoctet.genesys.player.GPlayer;
import static net.teraoctet.genesys.utils.GData.getGPlayer;
import static net.teraoctet.genesys.utils.GData.getUUID;
import static net.teraoctet.genesys.utils.MessageManager.DATA_NOT_FOUND;
import static net.teraoctet.genesys.utils.MessageManager.FACTION_MEMBER_REMOVED_SUCCESS;
import static net.teraoctet.genesys.utils.MessageManager.FACTION_RETURNED_BY;
import static net.teraoctet.genesys.utils.MessageManager.NOT_FOUND;
import static net.teraoctet.genesys.utils.MessageManager.NOT_IN_SAME_FACTION;
import static net.teraoctet.genesys.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.genesys.utils.MessageManager.NO_FACTION;
import static net.teraoctet.genesys.utils.MessageManager.NO_PERMISSIONS;
import static net.teraoctet.genesys.utils.MessageManager.WRONG_RANK;
import static net.teraoctet.genesys.utils.ServerManager.isOnline;
import static org.spongepowered.api.Sponge.getGame;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;

public class CommandFactionRemoveplayer implements CommandExecutor {
        
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {

        if(src instanceof Player && src.hasPermission("genesys.faction.removeplayer")) {
            GPlayer gplayer = getGPlayer(src.getIdentifier());
            
            if(factionManager.hasAnyFaction(gplayer)) {
                int playerRank = gplayer.getFactionRank();
                if(playerRank <= 2) {
                    String targetName = ctx.<String> getOne("name").get();
                    String targetUUID = getUUID(targetName);
                    
                    if(targetUUID == null){
                        src.sendMessage(DATA_NOT_FOUND(targetName));
                    } else {
                        GPlayer target_gplayer = getGPlayer(targetUUID);
                        int target_id_faction = target_gplayer.getID_faction();
                        int player_id_faction = gplayer.getID_faction();

                        if(player_id_faction == target_id_faction) {
                            if(playerRank == 2 && target_gplayer.getFactionRank() <= 2) {
                                src.sendMessage(NO_PERMISSIONS());
                            } else {
                                target_gplayer.setID_faction(0);
                                target_gplayer.setFactionRank(0);
                                target_gplayer.update();

                                if(isOnline(targetName)) { 
                                    Player targetPlayer = getGame().getServer().getPlayer(targetName).get();
                                    targetPlayer.sendMessage(FACTION_RETURNED_BY(src.getName()));
                                } else {  
                                    //ENVOYER UN MAIL AU JOUEUR QUI A ETE RENVOYE
                                }

                                src.sendMessage(FACTION_MEMBER_REMOVED_SUCCESS(targetName));
                                return CommandResult.success();
                            }
                        } else {
                            src.sendMessage(NOT_IN_SAME_FACTION(targetName));
                        } 
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
