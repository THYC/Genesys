package net.teraoctet.genesys.commands.plot;

import java.util.Optional;
import static net.teraoctet.genesys.Genesys.plotManager;
import net.teraoctet.genesys.plot.GPlot;
import net.teraoctet.genesys.utils.GData;
import static net.teraoctet.genesys.utils.GData.getGPlayer;
import static net.teraoctet.genesys.utils.MessageManager.USAGE;
import net.teraoctet.genesys.player.GPlayer;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import static net.teraoctet.genesys.utils.MessageManager.MESSAGE;
import static net.teraoctet.genesys.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.genesys.utils.MessageManager.NO_PLOT;
import static net.teraoctet.genesys.utils.MessageManager.NO_PERMISSIONS;
import static net.teraoctet.genesys.utils.MessageManager.ALREADY_OWNED_PLOT;
import org.spongepowered.api.command.source.ConsoleSource;

public class CommandPlotRemove implements CommandExecutor {
           
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {
        
        if(src instanceof Player && src.hasPermission("genesys.plot.remove")) { 
            Player player = (Player) src;
            GPlayer gplayer = getGPlayer(player.getUniqueId().toString());
            Optional<GPlot> gplot = Optional.empty();

            if(ctx.getOne("name").isPresent()){
                String plotName = ctx.<String> getOne("name").get();
                gplot = plotManager.getPlot(plotName);                
            } else {
                gplot = plotManager.getPlot(player.getLocation());
            }

            if (!gplot.isPresent()){
                player.sendMessage(NO_PLOT());
                player.sendMessage(USAGE("/plot remove : supprime une parcelle - vous devez \352tre sur la parcelle"));
                player.sendMessage(USAGE("/plot remove <NomParcelle> : supprime la parcelle nomm\351e"));
                return CommandResult.empty(); 
            } else if (!gplot.get().getUuidOwner().equalsIgnoreCase(player.getUniqueId().toString()) && gplayer.getLevel() != 10){
                player.sendMessage(ALREADY_OWNED_PLOT());
                return CommandResult.empty();  
            }

            gplot.get().delete();
            GData.commit();
            GData.removePlot(gplot.get());
            player.sendMessage(MESSAGE("&eLa parcelle " + gplot.get().getName() + " &7a \351t\351 supprim\351e"));  
            return CommandResult.success();
        } 
        
        else if (src instanceof ConsoleSource){
            src.sendMessage(NO_CONSOLE());
        }
        
        else {
            src.sendMessage(NO_PERMISSIONS());
        }
        
        return CommandResult.empty();	
    }  
}