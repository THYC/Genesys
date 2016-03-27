package net.teraoctet.genesys.commands;

import static net.teraoctet.genesys.Genesys.parcelManager;
import net.teraoctet.genesys.parcel.GParcel;
import net.teraoctet.genesys.utils.GData;
import static net.teraoctet.genesys.utils.GData.getGPlayer;
import static net.teraoctet.genesys.utils.MessageManager.MESSAGE;
import static net.teraoctet.genesys.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.genesys.utils.MessageManager.NO_PARCEL;
import static net.teraoctet.genesys.utils.MessageManager.NO_PERMISSIONS;
import static net.teraoctet.genesys.utils.MessageManager.RESERVED_PARCEL;
import static net.teraoctet.genesys.utils.MessageManager.USAGE;
import net.teraoctet.genesys.player.GPlayer;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.chat.ChatTypes;

public class CommandParcelRemove implements CommandExecutor {
           
    @Override
    public CommandResult execute(CommandSource sender, CommandContext ctx) throws CommandException {
        
        if(sender instanceof Player == false) { 
            sender.sendMessage(NO_CONSOLE()); 
            return CommandResult.success(); 
        }
        
        Player player = (Player) sender;
        GPlayer gplayer = getGPlayer(player.getUniqueId().toString());

        if(!player.hasPermission("genesys.parcel.remove")) { 
            sender.sendMessage(NO_PERMISSIONS()); 
            return CommandResult.success(); 
        }
        
        GParcel gparcel = null;
  
        if(ctx.getOne("name").isPresent()){
            String parcelName = ctx.<String> getOne("name").get();
            gparcel = parcelManager.getParcel(parcelName);                
        } else {
            gparcel = parcelManager.getParcel(player.getLocation());
        }

        if (gparcel == null){
            player.sendMessage(ChatTypes.CHAT,NO_PARCEL());
            player.sendMessage(ChatTypes.CHAT,USAGE("/parcel remove : supprime une parcelle - vous devez etre sur la parcelle"));
            player.sendMessage(ChatTypes.CHAT,USAGE("/parcel remove <NomParcelle> : supprime la parcelle nommee"));
            return CommandResult.success(); 
        } else if (!gparcel.getUuidOwner().equalsIgnoreCase(player.getUniqueId().toString()) && gplayer.getLevel() != 10){
            player.sendMessage(ChatTypes.CHAT,RESERVED_PARCEL());
            return CommandResult.success();   
        }

        gparcel.delete();
        GData.commit();
        GData.removeParcel(gparcel);
        player.sendMessage(ChatTypes.CHAT,MESSAGE("&e" + gparcel.getName() + " &7a été supprime"));
        return CommandResult.success();	
    }  
}