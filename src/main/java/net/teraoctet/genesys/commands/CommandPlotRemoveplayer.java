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

public class CommandPlotRemoveplayer implements CommandExecutor {
       
    @Override
    public CommandResult execute(CommandSource sender, CommandContext ctx) throws CommandException {

        if(sender instanceof Player == false) { 
            sender.sendMessage(NO_CONSOLE()); 
            return CommandResult.success(); 
        }
        
        Player player = (Player) sender;
        GPlayer gplayer = getGPlayer(player.getUniqueId().toString());

        if(!player.hasPermission("genesys.plot.removeplayer")) { 
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
            player.sendMessage(ChatTypes.CHAT,USAGE("/plot removeplayer : retire un habitant - vous devez \352tre sur la parcelle"));
            player.sendMessage(ChatTypes.CHAT,USAGE("/plot removeplayer <NomParcelle> : retire un habitant sur la parcelle nomm\351e"));
            return CommandResult.success(); 
        } else if (!gplot.getUuidOwner().equalsIgnoreCase(player.getUniqueId().toString()) && gplayer.getLevel() != 10){
            player.sendMessage(ChatTypes.CHAT,ALREADY_OWNED_PLOT());
            return CommandResult.success();   
        }
        
        if(ctx.getOne("player").isPresent()){
            Player target = ctx.<Player> getOne("player").get();  
            if (target == null){
                player.sendMessage(ChatTypes.CHAT,MESSAGE("&e" + target + " &7 doit \352tre connecté pour le retirer"));
                return CommandResult.success();
            }
            
            String uuidAllowed = gplot.getUuidAllowed();
            uuidAllowed = uuidAllowed.replace(target.getUniqueId().toString(), "");
            gplot.setUuidAllowed(uuidAllowed);
            player.sendMessage(ChatTypes.CHAT,MESSAGE("&e" + target.getName() + " &7a \351t\351 retir\351 de la liste des habitants"));
            target.sendMessage(ChatTypes.CHAT,MESSAGE("&e" + player.getName() + " &7vous a retir\351 des habitants de &e" + gplot.getName()));
        } else {
            player.sendMessage(ChatTypes.CHAT,USAGE("/plot removeplayer <playerName> [NomParcelle]"));
        }
        return CommandResult.success();	
    }
}