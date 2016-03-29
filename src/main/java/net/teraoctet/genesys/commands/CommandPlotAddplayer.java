package net.teraoctet.genesys.commands;

import static net.teraoctet.genesys.Genesys.plotManager;
import net.teraoctet.genesys.plot.GPlot;
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

public class CommandPlotAddplayer implements CommandExecutor {
       
    @Override
    public CommandResult execute(CommandSource sender, CommandContext ctx) throws CommandException {

        if(sender instanceof Player == false) { 
            sender.sendMessage(NO_CONSOLE()); 
            return CommandResult.success(); 
        }
        
        Player player = (Player) sender;
        GPlayer gplayer = getGPlayer(player.getUniqueId().toString());

        if(!player.hasPermission("genesys.plot.addplayer")) { 
            sender.sendMessage(NO_PERMISSIONS()); 
            return CommandResult.success(); 
        }
        
        GPlot gplot = null;
  
        if(ctx.getOne("name").isPresent()){
            String plotName = ctx.<String> getOne("name").get();
            gplot = plotManager.getPlot(plotName);                
        } else {
            gplot = plotManager.getPlot(player.getLocation());
        }

        if (gplot == null){
            player.sendMessage(ChatTypes.CHAT,NO_PLOT());
            player.sendMessage(ChatTypes.CHAT,USAGE("/plot addplayer : ajoute un habitant - vous devez \352tre sur la parcelle"));
            player.sendMessage(ChatTypes.CHAT,USAGE("/plot addplayer <NomParcelle> : ajoute un habitant sur la parcelle nomm\351e"));
            return CommandResult.success(); 
        } else if (!gplot.getUuidAllowed().contains(player.getUniqueId().toString()) && gplayer.getLevel() != 10){
            player.sendMessage(ChatTypes.CHAT,ALREADY_OWNED_PLOT());
            return CommandResult.success();   
        }
        
        if(ctx.getOne("player").isPresent()){
            Player target = ctx.<Player> getOne("player").get();  
            if (target == null){
                player.sendMessage(ChatTypes.CHAT,MESSAGE("&e" + target + " &7 doit \352tre connect\351 pour l'ajouter"));
                return CommandResult.success();
            }
            
            gplot.setUuidAllowed(gplot.getUuidAllowed() + " " + target.getUniqueId().toString());
            player.sendMessage(ChatTypes.CHAT,MESSAGE("&e" + target.getName() + " &7 a \351t\351 ajout\351 à la liste des habitants"));
            target.sendMessage(ChatTypes.CHAT,MESSAGE("&7Vous \352tes maintenant habitant de &e" + gplot.getName()));
        } else {
            player.sendMessage(ChatTypes.CHAT,USAGE("/plot addplayer <playerName> [NomParcelle]"));
        }
        return CommandResult.success();	
    }
}