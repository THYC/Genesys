package net.teraoctet.genesys.commands;

import static net.teraoctet.genesys.Genesys.Atpa;
import static net.teraoctet.genesys.Genesys.serverManager;
import static net.teraoctet.genesys.utils.MessageManager.MESSAGE;
import static net.teraoctet.genesys.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.genesys.utils.MessageManager.NO_PERMISSIONS;
import net.teraoctet.genesys.utils.TPAH;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;

public class CommandTPaccept implements CommandExecutor {
        
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {

        if(src instanceof Player && src.hasPermission("genesys.tpa")) {
            Player player = (Player) src; 
            Player toPlayer = null;
            Player fromPlayer = null;
            int i = 0;
            int index = 0;
            
            for(TPAH tpa:Atpa){
                if(tpa.getToPlayer() == player && "tpa".equals(tpa.getType())){
                    fromPlayer = tpa.getFromPlayer();
                    toPlayer = player;
                    index = i;
                }
                else if(tpa.getFromPlayer() == player && "tphere".equals(tpa.getType())){
                    fromPlayer = player;
                    toPlayer = tpa.getToPlayer();
                    index = i;
                }
                i++;
            }
            
            if (fromPlayer == null && toPlayer == null){
                player.sendMessage(MESSAGE("&eAucune demande en cours !"));
                return CommandResult.success();
            }
            serverManager.teleport(fromPlayer, toPlayer);
            Atpa.remove(index);
            return CommandResult.success();
        } 
        
        else if (src instanceof ConsoleSource) {
            src.sendMessage(NO_CONSOLE());
        }
        
        else {
            src.sendMessage(NO_PERMISSIONS());
        }
                
        return CommandResult.empty();
    }
}
