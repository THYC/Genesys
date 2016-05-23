package net.teraoctet.genesys.commands.plot;

import java.util.Optional;
import static net.teraoctet.genesys.Genesys.plotManager;
import net.teraoctet.genesys.plot.GPlot;
import static net.teraoctet.genesys.utils.MessageManager.USAGE;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import static net.teraoctet.genesys.utils.MessageManager.MESSAGE;
import static net.teraoctet.genesys.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.genesys.utils.MessageManager.NO_PLOT;
import static net.teraoctet.genesys.utils.MessageManager.NO_PERMISSIONS;
import org.spongepowered.api.command.source.ConsoleSource;

public class CommandPlotLevel implements CommandExecutor {
       
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {

        if(src instanceof Player && src.hasPermission("genesys.plot.level")) { 
            Player player = (Player) src;
            Optional<GPlot> gplot = Optional.empty();

            if(ctx.getOne("name").isPresent()){
                String plotName = ctx.<String> getOne("name").get();
                gplot = plotManager.getPlot(plotName);                
            } else {
                gplot = plotManager.getPlot(player.getLocation());
            }

            if (gplot == null){
                player.sendMessage(NO_PLOT());
                return CommandResult.empty();
            }

            if(ctx.getOne("level").isPresent()){
                int level = ctx.<Integer> getOne("level").get();  
                
                gplot.get().setLevel(level);
                gplot.get().update();
                player.sendMessage(MESSAGE("&e" + gplot.get().getName() + " level :&7" + gplot.get().getLevel()));
                return CommandResult.success();
            } else {
                player.sendMessage(MESSAGE("&e" + gplot.get().getName() + " level :&7" + gplot.get().getLevel()));
                player.sendMessage(USAGE("/plot level [level]"));
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