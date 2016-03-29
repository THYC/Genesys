package net.teraoctet.genesys.commands;

import static net.teraoctet.genesys.Genesys.plotManager;
import net.teraoctet.genesys.plot.GPlot;
import net.teraoctet.genesys.utils.GData;
import static net.teraoctet.genesys.utils.GData.getGPlayer;
import static net.teraoctet.genesys.utils.MessageManager.USAGE;
import net.teraoctet.genesys.player.GPlayer;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.chat.ChatTypes;
import static net.teraoctet.genesys.utils.MessageManager.MESSAGE;
import static net.teraoctet.genesys.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.genesys.utils.MessageManager.NO_PLOT;
import static net.teraoctet.genesys.utils.MessageManager.NO_PERMISSIONS;
import static net.teraoctet.genesys.utils.MessageManager.ALREADY_OWNED_PLOT;
import org.spongepowered.api.command.source.ConsoleSource;

public class CommandPlotRemove implements CommandExecutor {
           
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException {
        
        if(src instanceof Player && src.hasPermission("genesys.plot.remove")) { 
            Player player = (Player) src;
            GPlayer gplayer = getGPlayer(player.getUniqueId().toString());
            GPlot gplot = null;

            if(ctx.getOne("name").isPresent()){
                String plotName = ctx.<String> getOne("name").get();
                gplot = plotManager.getPlot(plotName);                
            } else {
                gplot = plotManager.getPlot(player.getLocation());
            }

            if (gplot == null){
                player.sendMessage(ChatTypes.CHAT,NO_PLOT());
                player.sendMessage(ChatTypes.CHAT,USAGE("/plot remove : supprime une parcelle - vous devez \352tre sur la parcelle"));
                player.sendMessage(ChatTypes.CHAT,USAGE("/plot remove <NomParcelle> : supprime la parcelle nomm\351e"));
                return CommandResult.success(); 
            } else if (!gplot.getUuidOwner().equalsIgnoreCase(player.getUniqueId().toString()) && gplayer.getLevel() != 10){
                player.sendMessage(ChatTypes.CHAT,ALREADY_OWNED_PLOT());
                return CommandResult.success();   
            }

            gplot.delete();
            GData.commit();
            GData.removePlot(gplot);
            player.sendMessage(ChatTypes.CHAT,MESSAGE("&eLa parcelle " + gplot.getName() + " &7a \351t\351 supprim\351e"));   
        } 
        
        else if (src instanceof ConsoleSource){
            src.sendMessage(NO_CONSOLE());
        }
        
        else {
            src.sendMessage(NO_PERMISSIONS());
        }
        
        return CommandResult.success();	
    }  
}