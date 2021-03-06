package net.teraoctet.genesys.commands.portal;

import static net.teraoctet.genesys.Genesys.portalManager;
import net.teraoctet.genesys.portal.GPortal;
import net.teraoctet.genesys.utils.GData;
import static net.teraoctet.genesys.utils.MessageManager.USAGE;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import static net.teraoctet.genesys.utils.MessageManager.MESSAGE;
import static net.teraoctet.genesys.utils.MessageManager.NOT_FOUND;
import static net.teraoctet.genesys.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.genesys.utils.MessageManager.NO_PERMISSIONS;
import org.spongepowered.api.command.source.ConsoleSource;

public class CommandPortalTPFrom implements CommandExecutor {
       
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {
        
        if(src instanceof Player && src.hasPermission("genesys.admin.portal")) { 
            Player player = (Player) src;
        
            if(!ctx.getOne("name").isPresent()) { 
               player.sendMessage(USAGE("/portal tpfrom <name> : enregistre le point d'apparition du portail"));
               return CommandResult.empty();
            }

            String name = ctx.<String> getOne("name").get();  
            if (portalManager.hasPortal(name) == true){
                GPortal gportal = portalManager.getPortal(name);
                gportal.settoworld(player.getWorld().getName());
                gportal.settoX(player.getLocation().getBlockX());
                gportal.settoY(player.getLocation().getBlockY());
                gportal.settoZ(player.getLocation().getBlockZ());
                gportal.update();
                GData.commit();
                player.sendMessage(MESSAGE("&e" + gportal.getName() + ": &a point d'arriv\351 enregistr\351"));
            } else {
                player.sendMessage(NOT_FOUND(name));
                return CommandResult.empty();
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