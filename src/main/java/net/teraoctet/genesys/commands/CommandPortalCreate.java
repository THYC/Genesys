package net.teraoctet.genesys.commands;

import static net.teraoctet.genesys.Genesys.portalManager;
import net.teraoctet.genesys.plot.PlotManager;
import static net.teraoctet.genesys.utils.MessageManager.USAGE;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.Location;
import static net.teraoctet.genesys.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.genesys.utils.MessageManager.NO_PERMISSIONS;
import static net.teraoctet.genesys.utils.MessageManager.NAME_ALREADY_USED;
import static net.teraoctet.genesys.utils.MessageManager.UNDEFINED_PLOT_ANGLES;
import org.spongepowered.api.command.source.ConsoleSource;

public class CommandPortalCreate implements CommandExecutor {
           
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {
          
        if(src instanceof Player && src.hasPermission("genesys.admin.portal")) { 
            Player player = (Player) src;
            PlotManager plotManager = PlotManager.getSett(player);
        
            if(!ctx.getOne("name").isPresent()) { 
               player.sendMessage(USAGE("/portal create <name> : cr\351ation d'un portail au point d\351clar\351"));
               return CommandResult.empty();
           }

           String name = ctx.<String> getOne("name").get();

           if (portalManager.hasPortal(name) == false){
               Location[] c = {plotManager.getBorder1(), plotManager.getBorder2()};
               if ((c[0] == null) || (c[1] == null)){
                   player.sendMessage(UNDEFINED_PLOT_ANGLES());
                   return CommandResult.empty();
               }
               player.sendMessage(Text.builder("Clique ici pour confirmer la cr\351ation du portail").onClick(TextActions.runCommand("/portal createok " + name )).color(TextColors.AQUA).build()); 
               return CommandResult.success();
           } else {
               player.sendMessage(NAME_ALREADY_USED());
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