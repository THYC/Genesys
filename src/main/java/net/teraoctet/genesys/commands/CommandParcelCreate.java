package net.teraoctet.genesys.commands;

import net.teraoctet.genesys.parcel.ParcelManager;
import static net.teraoctet.genesys.utils.GData.getGPlayer;
import static net.teraoctet.genesys.utils.MessageManager.AMOUNT_PARCEL;
import static net.teraoctet.genesys.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.genesys.utils.MessageManager.NO_PERMISSIONS;
import static net.teraoctet.genesys.utils.MessageManager.PARCEL_NAME_FAILED;
import static net.teraoctet.genesys.utils.MessageManager.RESERVED_PARCEL;
import static net.teraoctet.genesys.utils.MessageManager.UNDEFINED_PARCEL;
import net.teraoctet.genesys.player.GPlayer;
import static net.teraoctet.genesys.utils.MessageManager.MESSAGE;
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

public class CommandParcelCreate implements CommandExecutor {
           
    @Override
    public CommandResult execute(CommandSource sender, CommandContext ctx) throws CommandException {

        Player player = (Player) sender;
        GPlayer gplayer = getGPlayer(player.getUniqueId().toString());

        ParcelManager parcelManager = ParcelManager.getSett(player);
        if(!player.hasPermission("genesys.parcel.create")) { 
                sender.sendMessage(NO_PERMISSIONS()); 
                return CommandResult.success(); 
        }
                   
        if(sender instanceof Player == false) { 
            sender.sendMessage(NO_CONSOLE()); 
            return CommandResult.success(); 
        }
                
        if(!ctx.getOne("name").isPresent()) { 
            player.sendMessage(ChatTypes.CHAT,USAGE("/parcel create <name> [strict] : creation d'une parcelle"));
            player.sendMessage(ChatTypes.CHAT,MESSAGE("&7option [strict] : protection sur les points déclares"));
            return CommandResult.success();
        }

        String name = ctx.<String> getOne("name").get();
        Boolean strict = false;

        if (parcelManager.hasParcel(name) == false){
            Location[] c = {parcelManager.getBorder1(), parcelManager.getBorder2()};
            if ((c[0] == null) || (c[1] == null)){
                player.sendMessage(ChatTypes.CHAT,UNDEFINED_PARCEL());
                return CommandResult.success();
            }

            if(parcelManager.parcelAllow(parcelManager.getBorder1(), parcelManager.getBorder2())){
                player.sendMessage(ChatTypes.CHAT,RESERVED_PARCEL());
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
                player.sendMessage(ChatTypes.CHAT,MESSAGE("&7Le cout de cette transaction est de : &e" + amount + " émeraudes"));
                player.sendMessage(ChatTypes.CHAT,Text.builder("&bClick ici pour confirmer la création de ta parcelle").onClick(TextActions.runCommand("/p createok " + name + " " + amount + " " + strict)).color(TextColors.AQUA).build());   
                return CommandResult.success();
            } else {
                player.sendMessage(ChatTypes.CHAT,AMOUNT_PARCEL(player,String.valueOf(amount)));
                return CommandResult.success();
            }
        } else {
            player.sendMessage(ChatTypes.CHAT,PARCEL_NAME_FAILED());
            return CommandResult.success();
        }
    }
}