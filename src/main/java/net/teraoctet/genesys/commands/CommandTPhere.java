package net.teraoctet.genesys.commands;

import static net.teraoctet.genesys.Genesys.Atpa;
import static net.teraoctet.genesys.utils.MessageManager.MESSAGE;
import static net.teraoctet.genesys.utils.MessageManager.NOT_FOUND;
import static net.teraoctet.genesys.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.genesys.utils.MessageManager.NO_PERMISSIONS;
import static net.teraoctet.genesys.utils.MessageManager.USAGE;
import net.teraoctet.genesys.utils.TPAH;
import net.teraoctet.genesys.utils.TaskTP;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;

public class CommandTPhere implements CommandExecutor {
        
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {

        if(src instanceof Player && src.hasPermission("genesys.tpa")) {
            Player player = (Player) src; 
            if(ctx.getOne("target").isPresent()) {
                Player target = ctx.<Player> getOne("player").get();
                if(!target.isOnline()) {
                    src.sendMessage(NOT_FOUND(target.getName()));
                    return CommandResult.empty();
                }
            
                TPAH tpa = new TPAH(target, player,"tphere");
                Atpa.add(tpa);
                final int index = Atpa.indexOf(tpa);

                player.sendMessage(MESSAGE("&eVeuillez patienter, demande envoy\351 ..."));
                target.sendMessage(MESSAGE("&eAcceptez vous que %player% vous t\351l\351porte sur lui ?",target));
                target.sendMessage(MESSAGE("&eVous avez 30s pour accepter cette demande en tapant : /tpaccept"));
                
                TaskTP tp = new TaskTP(player,index);
                tp.run();
                                
                return CommandResult.success();
                
            }else{
                player.sendMessage(USAGE("/tphere <player>"));
                return CommandResult.empty();
            } 
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

