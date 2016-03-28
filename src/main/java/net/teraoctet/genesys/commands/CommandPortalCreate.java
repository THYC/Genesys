package net.teraoctet.genesys.commands;

import static net.teraoctet.genesys.Genesys.portalManager;
import net.teraoctet.genesys.parcel.ParcelManager;
import static net.teraoctet.genesys.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.genesys.utils.MessageManager.NO_PERMISSIONS;
import static net.teraoctet.genesys.utils.MessageManager.PARCEL_NAME_FAILED;
import static net.teraoctet.genesys.utils.MessageManager.UNDEFINED_PARCEL;
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

public class CommandPortalCreate implements CommandExecutor {
           
    @Override
    public CommandResult execute(CommandSource sender, CommandContext ctx) throws CommandException {
          
        Player player = (Player) sender;
        ParcelManager parcelManager = ParcelManager.getSett(player);
        if(!player.hasPermission("genesys.admin.portal")) { 
                sender.sendMessage(NO_PERMISSIONS()); 
                return CommandResult.success(); 
        }
                   
        if(sender instanceof Player == false) { 
            sender.sendMessage(NO_CONSOLE()); 
            return CommandResult.success(); 
        }
        
         if(!ctx.getOne("name").isPresent()) { 
            player.sendMessage(ChatTypes.CHAT,USAGE("/portal create <name> : creation d'un portail au point declare"));
            return CommandResult.success();
        }

        String name = ctx.<String> getOne("name").get();

        if (portalManager.hasPortal(name) == false){
            Location[] c = {parcelManager.getBorder1(), parcelManager.getBorder2()};
            if ((c[0] == null) || (c[1] == null)){
                player.sendMessage(ChatTypes.CHAT,UNDEFINED_PARCEL());
                return CommandResult.success();
            }
                    
            player.sendMessage(ChatTypes.CHAT,Text.builder("Click ici pour confirmer la création du portail").onClick(TextActions.runCommand("/portal createok " + name )).color(TextColors.AQUA).build());   
        } else {
            player.sendMessage(ChatTypes.CHAT,PARCEL_NAME_FAILED());
        } 
        return CommandResult.success();
    }                         
}