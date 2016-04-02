package net.teraoctet.genesys.commands.plot;

import net.teraoctet.genesys.plot.GPlot;
import net.teraoctet.genesys.plot.PlotManager;
import net.teraoctet.genesys.utils.GData;
import static net.teraoctet.genesys.utils.GData.getGPlayer;
import static net.teraoctet.genesys.utils.MessageManager.USAGE;
import net.teraoctet.genesys.player.GPlayer;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.chat.ChatType;
import org.spongepowered.api.text.chat.ChatTypes;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import static net.teraoctet.genesys.utils.MessageManager.BEDROCK2SKY_PROTECT_PLOT_SUCCESS;
import static net.teraoctet.genesys.utils.MessageManager.MESSAGE;
import static net.teraoctet.genesys.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.genesys.utils.MessageManager.NO_PERMISSIONS;
import static net.teraoctet.genesys.utils.MessageManager.PROTECT_LOADED_PLOT;
import static net.teraoctet.genesys.utils.MessageManager.PROTECT_PLOT_SUCCESS;
import org.spongepowered.api.command.source.ConsoleSource;

public class CommandPlotCreateOK implements CommandExecutor {
           
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {
        
        if(src instanceof Player && src.hasPermission("genesys.plot.create")) { 
            Player player = (Player) src;
            GPlayer gplayer = getGPlayer(player.getUniqueId().toString());
            PlotManager plotManager = PlotManager.getSett(player);

            if(!ctx.getOne("name").isPresent() || !ctx.getOne("strict").isPresent() || !ctx.getOne("amount").isPresent()) { 
                player.sendMessage(MESSAGE("&bVous devez utliser la commande &7/plot create &bpour cr\351er une parcelle :"));
                player.sendMessage(USAGE("/plot create <name> [strict] : cr\351ation d'une parcelle"));
                player.sendMessage(MESSAGE("&7option [strict] : protection sur les points d\351clar\351s"));
                return CommandResult.empty();
            }

            String plotName = ctx.<String> getOne("name").get();
            Boolean strict = ctx.<Boolean> getOne("strict").get();
            int amount = ctx.<Integer> getOne("amount").get();
            Location[] c = {plotManager.getBorder1(), plotManager.getBorder2()};

            String playerUUID = player.getUniqueId().toString();
            if(gplayer.getLevel() == 10){ playerUUID = "ADMIN";} else { gplayer.debitMoney(amount);}

            int y1 = (int)c[0].getY();
            int y2 = (int)c[1].getY();

            if(strict == false) { 
                player.sendMessage(BEDROCK2SKY_PROTECT_PLOT_SUCCESS(player,plotName));
                y1 = 0;
                y2 = 500;
            } else {
                player.sendMessage((ChatType) PROTECT_PLOT_SUCCESS(player,plotName),Text.of(TextColors.GREEN," Y " + y1 + " : " + y2));
            }

            Location <World> world = c[0];
            String worldName = world.getExtent().getName();
            int x1 = c[0].getBlockX();
            int z1 = c[0].getBlockZ();
            int x2 = c[1].getBlockX();
            int z2 = c[1].getBlockZ();
            String message = "&b-- SECURE --";

            GPlot gplot = new GPlot(plotName,0,worldName,x1,y1,z1,x2,y2,z2,0,0,1,1,1,0,1,1,message,0,1,1,1,playerUUID,playerUUID);
            gplot.insert();
            GData.commit();
            GData.addPlot(gplot);

            player.sendMessage(ChatTypes.ACTION_BAR,PROTECT_LOADED_PLOT(player,plotName));
            player.sendMessage(Text.builder("Clique ici, pour voir les flags de ta parcelle !").onClick(TextActions.runCommand("/p flaglist " + plotName)).color(TextColors.AQUA).build());  
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