package net.teraoctet.genesys.commands;

import net.teraoctet.genesys.player.GPlayer;
import static net.teraoctet.genesys.utils.GData.getGPlayer;
import static net.teraoctet.genesys.utils.GData.getUUID;
import static net.teraoctet.genesys.utils.GServer.getPlayer;
import static net.teraoctet.genesys.utils.MessageManager.MESSAGE;
import static net.teraoctet.genesys.utils.MessageManager.NO_PERMISSIONS;
import static net.teraoctet.genesys.utils.MessageManager.USAGE;
import static net.teraoctet.genesys.utils.MessageManager.PLAYER_DATA_NOT_FOUND;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;

public class CommandPlayerinfo implements CommandExecutor {
        
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {
        //partie de la commande qui cible un <player>, exécuté que si la source a la permission et qu'elle a rempli l'argument <player>
        if(ctx.getOne("tplayer").isPresent() && src.hasPermission("genesys.playerinfo.others")) {
            String targetName = ctx.<String> getOne("tplayer").get();
            boolean isOnline = false;
            
            for (Player player : Sponge.getServer().getOnlinePlayers()) {
                if(player.getName().equals(targetName)){
                    isOnline = true;
                }
            }

            String targetUUID = getUUID(targetName);
            
            if(targetUUID == null){
                src.sendMessage(PLAYER_DATA_NOT_FOUND(targetName));
                return CommandResult.empty();
            }
            
            GPlayer gplayer = getGPlayer(targetUUID);
            
            src.sendMessage(MESSAGE("&8--------------------"));
            src.sendMessage(MESSAGE("&7" + targetName));
            src.sendMessage(MESSAGE("&8UUID: " + targetUUID));
            src.sendMessage(MESSAGE("&8--------------------"));
            src.sendMessage(MESSAGE("&8Home (default): " + gplayer.getHome("default")));
            src.sendMessage(MESSAGE("&8Nb de Home : " + gplayer.getHomes().size()));
            
            if(isOnline){
                Player tPlayer = getPlayer(targetName);
                src.sendMessage(MESSAGE("&8" + targetName + " est connect\351"));
                src.sendMessage(MESSAGE("&8Position : " + tPlayer.getLocation()));
            } else {
                src.sendMessage(MESSAGE("&8" + targetName + " est d\351connect\351"));
                src.sendMessage(MESSAGE("&8Derni\350re position : " + gplayer.getLastposition()));
            }

            return CommandResult.success();
        } 
        
        //si la source est un joueur qui n'a pas rempli l'argument <player>, affiche les infos de la source
        else if (src instanceof Player && src.hasPermission("genesys.playerinfo")) {
            Player player = (Player) src;
            src.sendMessage(MESSAGE("&8--------------------"));
            src.sendMessage(MESSAGE("&7" + src.getName()));
            src.sendMessage(MESSAGE("&8--------------------"));
            
            return CommandResult.success();
        }
        
        //si on arrive jusqu'ici et que la source est la console c'est qu'elle a pas rempli l'argument <player>
        else if (src instanceof ConsoleSource) {
            src.sendMessage(USAGE("/playerinfo <player>"));
        }
        
        //si on arrive jusqu'ici c'est que la source n'a pas les permissions pour cette commande ou que quelque chose s'est mal passé
        else {
            src.sendMessage(NO_PERMISSIONS());
        }
                
        return CommandResult.empty();
    }
}
