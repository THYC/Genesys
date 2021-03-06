package net.teraoctet.genesys.commands.portal;

import net.teraoctet.genesys.plot.PlotManager;
import net.teraoctet.genesys.portal.GPortal;
import net.teraoctet.genesys.portal.PortalManager;
import net.teraoctet.genesys.utils.GData;
import static net.teraoctet.genesys.utils.MessageManager.USAGE;
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
import org.spongepowered.api.world.World;
import static net.teraoctet.genesys.utils.MessageManager.MESSAGE;
import static net.teraoctet.genesys.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.genesys.utils.MessageManager.NO_PERMISSIONS;
import static net.teraoctet.genesys.utils.MessageManager.PROTECT_LOADED_PLOT;
import org.spongepowered.api.command.source.ConsoleSource;

public class CommandPortalCreateOK implements CommandExecutor {
           
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {
        
        if(src instanceof Player && src.hasPermission("genesys.admin.portal")) { 
            Player player = (Player) src;
            PlotManager plotManager = PlotManager.getSett(player);
            PortalManager portalManager = new PortalManager();
        
            if(!ctx.getOne("name").isPresent()) { 
                player.sendMessage(MESSAGE("&bVous devez utliser la commande &7/portal create &bpour creer un portail :"));
                player.sendMessage(USAGE("/portal create <name> : cr\351ation d'un portail au point d\351clar\351"));
                return CommandResult.empty();
            }
            
            String portalName = ctx.<String> getOne("name").get();
            if(portalManager.hasPortal(portalName)){
                player.sendMessage(MESSAGE("&bce portail existe d\351ja"));
                return CommandResult.empty();
            }

            Location[] c = {plotManager.getBorder1(), plotManager.getBorder2()};
            Location <World> world = c[0];
            String worldName = world.getExtent().getName();
            int x1 = c[0].getBlockX();
            int y1 = c[0].getBlockY();
            int z1 = c[0].getBlockZ();
            int x2 = c[1].getBlockX();
            int y2 = c[1].getBlockY();
            int z2 = c[1].getBlockZ();
            String message = "&c.. vers l''infini et au del\340 ...";

            GPortal gportal = new GPortal(portalName,0,worldName,x1,y1,z1,x2,y2,z2,message);
            gportal.insert();
            GData.commit();
            GData.addPortal(gportal);

            player.sendMessage(Text.builder("Clique ici pour lire le message par d\351faut du portail").onClick(TextActions.runCommand("/portal msg " + portalName )).color(TextColors.AQUA).build());
            player.sendMessage(Text.builder("Tape /portal msg <message> &bpour pour modifier le message par d\351faut").onClick(TextActions.suggestCommand("/portal msg " + portalName + " 'remplace ce texte par ton message'")).color(TextColors.AQUA).build());
            player.sendMessage(ChatTypes.ACTION_BAR,PROTECT_LOADED_PLOT(player,portalName));
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