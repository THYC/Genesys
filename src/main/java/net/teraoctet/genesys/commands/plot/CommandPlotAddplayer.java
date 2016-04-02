package net.teraoctet.genesys.commands.plot;

import static net.teraoctet.genesys.Genesys.plotManager;
import net.teraoctet.genesys.plot.GPlot;
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

public class CommandPlotAddplayer implements CommandExecutor {
       
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {

        if(src instanceof Player && src.hasPermission("genesys.plot.addplayer")) { 
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
                player.sendMessage(NO_PLOT());
                player.sendMessage(USAGE("/plot addplayer : ajoute un habitant - vous devez \352tre sur la parcelle"));
                player.sendMessage(USAGE("/plot addplayer <nomParcelle> : ajoute un habitant sur la parcelle nomm\351e"));
                return CommandResult.empty();
            } else if (!gplot.getUuidAllowed().contains(player.getUniqueId().toString()) && gplayer.getLevel() != 10){
                player.sendMessage(ALREADY_OWNED_PLOT());
                return CommandResult.empty();   
            }

            if(ctx.getOne("player").isPresent()){
                Player target = ctx.<Player> getOne("player").get();  
                if (target == null){
                    player.sendMessage(MESSAGE("&e" + target + " &7 doit \352tre connect\351 pour l'ajouter"));
                    return CommandResult.empty();
                }
                gplot.setUuidAllowed(gplot.getUuidAllowed() + " " + target.getUniqueId().toString());
                player.sendMessage(MESSAGE("&e" + target.getName() + " &7 a \351t\351 ajout\351 à la liste des habitants"));
                target.sendMessage(MESSAGE("&7Vous \352tes maintenant habitant de &e" + gplot.getName()));
                return CommandResult.success();
            } else {
                player.sendMessage(USAGE("/plot addplayer <playerName> [nomParcelle]"));
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