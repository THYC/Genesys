package net.teraoctet.genesys.commands;

import java.util.Optional;
import static net.teraoctet.genesys.Genesys.plotManager;
import net.teraoctet.genesys.player.GPlayer;
import static net.teraoctet.genesys.utils.GData.getGPlayer;
import static net.teraoctet.genesys.utils.GServer.getPlayer;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import static net.teraoctet.genesys.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.genesys.utils.MessageManager.NO_PERMISSIONS;
import static net.teraoctet.genesys.utils.MessageManager.PLAYER_NOT_FOUND;
import static net.teraoctet.genesys.utils.MessageManager.PLOT_LIST;
import static net.teraoctet.genesys.utils.MessageManager.TARGET_PLOT_LIST;
import org.spongepowered.api.command.source.ConsoleSource;

public class CommandPlotList implements CommandExecutor {
    
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException {
        if(src instanceof Player && src.hasPermission("genesys.plot.list")) {
            Player player = (Player) src;

            if(ctx.getOne("tplayer").isPresent() && src.hasPermission("genesys.plot.otherlist")){
                String targetName = ctx.<String> getOne("tplayer").get();
                GPlayer gplayer = getGPlayer(getPlayer(targetName).getUniqueId().toString());
                // si <tplayer> n'est pas dans la base de donnée
                if(gplayer == null){
                    player.sendMessage(PLAYER_NOT_FOUND(getPlayer(targetName)));
                } else {    //lorsque <tplayer> est dans la base de donnée
                    String targetUUID = getPlayer(targetName).getIdentifier();
                    Text listPlot = plotManager.getListPlot(targetUUID);
                    player.sendMessage(TARGET_PLOT_LIST(targetName));
                    player.sendMessage(listPlot); 
                }
            }
            else {
                String playerUUID = player.getUniqueId().toString();
                Text listPlot = plotManager.getListPlot(playerUUID);
                player.sendMessage(PLOT_LIST());
                player.sendMessage(listPlot);
            }
        } 
        
        else if (src instanceof ConsoleSource) {
            src.sendMessage(NO_CONSOLE());
        }
        
        else {
            src.sendMessage(NO_PERMISSIONS());
        }
        
        return CommandResult.success();	
    }
}