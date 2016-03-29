package net.teraoctet.genesys.commands;

import net.teraoctet.genesys.plot.PlotManager;
import static net.teraoctet.genesys.utils.GData.getGPlayer;
import net.teraoctet.genesys.player.GPlayer;
import static net.teraoctet.genesys.utils.MessageManager.USAGE;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.chat.ChatTypes;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.Location;
import static net.teraoctet.genesys.utils.MessageManager.BUYING_COST_PLOT;
import static net.teraoctet.genesys.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.genesys.utils.MessageManager.NO_PERMISSIONS;
import static net.teraoctet.genesys.utils.MessageManager.PLOT_NAME_ALREADY_USED;
import static net.teraoctet.genesys.utils.MessageManager.ALREADY_OWNED_PLOT;
import static net.teraoctet.genesys.utils.MessageManager.UNDEFINED_PLOT_ANGLES;
import static net.teraoctet.genesys.utils.MessageManager.MESSAGE;

public class CommandPlotCreate implements CommandExecutor {
           
    @Override
    public CommandResult execute(CommandSource sender, CommandContext ctx) throws CommandException {

        Player player = (Player) sender;
        GPlayer gplayer = getGPlayer(player.getUniqueId().toString());

        PlotManager plotManager = PlotManager.getSett(player);
        if(!player.hasPermission("genesys.plot.create")) { 
                sender.sendMessage(NO_PERMISSIONS()); 
                return CommandResult.success(); 
        }
                   
        if(sender instanceof Player == false) { 
            sender.sendMessage(NO_CONSOLE()); 
            return CommandResult.success(); 
        }
                
        if(!ctx.getOne("name").isPresent()) { 
            player.sendMessage(ChatTypes.CHAT,USAGE("/plot create <name> [strict] : cr\351ation d'une parcelle"));
            player.sendMessage(ChatTypes.CHAT,MESSAGE("&7option [strict] : protection sur les points d\351clar\351s"));
            return CommandResult.success();
        }

        String name = ctx.<String> getOne("name").get();
        Boolean strict = false;

        if (plotManager.hasPlot(name) == false){
            Location[] c = {plotManager.getBorder1(), plotManager.getBorder2()};
            if ((c[0] == null) || (c[1] == null)){
                player.sendMessage(ChatTypes.CHAT,UNDEFINED_PLOT_ANGLES());
                return CommandResult.success();
            }

            if(plotManager.plotAllow(plotManager.getBorder1(), plotManager.getBorder2())){
                player.sendMessage(ChatTypes.CHAT,ALREADY_OWNED_PLOT());
                return CommandResult.success();
            }

            int X = (int) Math.round(c[0].getX()-c[1].getX());
            int Z = (int) Math.round(c[0].getZ()-c[1].getZ());
            if(X < 0)X = -X;
            if(Z < 0)Z = -Z;
            int nbBlock =  (X * Z);
            int amount = 1;

            if(nbBlock < 51){ amount = 1;}
            else if(nbBlock < 101){ amount = 2;}
            else if(nbBlock < 201){ amount = 3;}
            else { amount = nbBlock / 60;}

            if(gplayer.getMoney()>= amount || gplayer.getLevel() == 10){
                if(ctx.getOne("strict").isPresent()){
                    if (ctx.<String> getOne("strict").get().equalsIgnoreCase("strict")) strict = true;
                }
                player.sendMessage(ChatTypes.CHAT,MESSAGE("&7Le co\373t de cette transaction est de : &e" + amount + " \351meraudes"));
                player.sendMessage(ChatTypes.CHAT,Text.builder("Clique ici pour confirmer la cr\351ation de ta parcelle").onClick(TextActions.runCommand("/p createok " + name + " " + amount + " " + strict)).color(TextColors.AQUA).build());   
                return CommandResult.success();
            } else {
                player.sendMessage(ChatTypes.CHAT,BUYING_COST_PLOT(player,String.valueOf(amount),String.valueOf(gplayer.getMoney())));
                return CommandResult.success();
            }
        } else {
            player.sendMessage(ChatTypes.CHAT,PLOT_NAME_ALREADY_USED());
            return CommandResult.success();
        }
    }
}