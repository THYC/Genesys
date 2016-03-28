package net.teraoctet.genesys.commands;

import static net.teraoctet.genesys.Genesys.parcelManager;
import static net.teraoctet.genesys.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.genesys.utils.MessageManager.NO_PERMISSIONS;
import static net.teraoctet.genesys.utils.GServer.getPlayer;
import static net.teraoctet.genesys.utils.MessageManager.MESSAGE;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.chat.ChatTypes;

public class CommandParcelList implements CommandExecutor {
    
    @Override
    public CommandResult execute(CommandSource sender, CommandContext ctx) throws CommandException {
               
        if(sender instanceof Player == false) { 
            sender.sendMessage(NO_CONSOLE()); 
            return CommandResult.success(); 
        }
        
        Player player = (Player) sender;
        
        if(!player.hasPermission("genesys.parcel.list")) { 
                sender.sendMessage(NO_PERMISSIONS()); 
                return CommandResult.success(); 
        }
        
        String targetUUID = player.getUniqueId().toString();
        String targetName = player.getName();
        
        if(ctx.getOne("target").isPresent() && player.hasPermission("genesys.parcel.otherlist")){
            targetName = ctx.<String> getOne("target").get();
            targetUUID = getPlayer(targetName).getIdentifier();
        } else {
            sender.sendMessage(NO_PERMISSIONS()); 
            return CommandResult.success(); 
        }
                        
        Text listParcel = parcelManager.getListParcel(targetUUID);
        player.sendMessage(ChatTypes.CHAT,MESSAGE("&bListe des parcelles appartenant a &7" + targetName));
        player.sendMessage(ChatTypes.CHAT,listParcel);
        
        return CommandResult.success();	
    }
}