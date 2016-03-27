package net.teraoctet.genesys.commands;

import net.teraoctet.genesys.parcel.GParcel;
import net.teraoctet.genesys.parcel.ParcelManager;
import net.teraoctet.genesys.utils.GData;
import static net.teraoctet.genesys.utils.GData.getGPlayer;
import static net.teraoctet.genesys.utils.MessageManager.ALL_PROTECT_PARCEL;
import static net.teraoctet.genesys.utils.MessageManager.MESSAGE;
import static net.teraoctet.genesys.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.genesys.utils.MessageManager.NO_PERMISSIONS;
import static net.teraoctet.genesys.utils.MessageManager.PROTECT_LOADED_PARCEL;
import static net.teraoctet.genesys.utils.MessageManager.PROTECT_PARCEL;
import static net.teraoctet.genesys.utils.MessageManager.USAGE;
import net.teraoctet.genesys.player.GPlayer;
import org.spongepowered.api.command.CommandException;
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

public class CommandParcelCreateOK implements CommandExecutor {
           
    @Override
    public CommandResult execute(CommandSource sender, CommandContext ctx) throws CommandException {
        
        if(sender instanceof Player == false) { 
            sender.sendMessage(NO_CONSOLE()); 
            return CommandResult.success(); 
        }
        
        Player player = (Player) sender;
        GPlayer gplayer = getGPlayer(player.getUniqueId().toString());

        ParcelManager parcelManager = ParcelManager.getSett(player);
        if(!player.hasPermission("genesys.parcel.create")) { 
                sender.sendMessage(NO_PERMISSIONS()); 
                return CommandResult.success(); 
        }
        
        if(!ctx.getOne("name").isPresent() || !ctx.getOne("strict").isPresent() || !ctx.getOne("amount").isPresent()) { 
            player.sendMessage(ChatTypes.CHAT,MESSAGE("&bVous devez utliser la commande &7/parcel create &bpour creer une parcelle :"));
            player.sendMessage(ChatTypes.CHAT,USAGE("/parcel create <name> [strict] : creation d'une parcelle"));
            player.sendMessage(ChatTypes.CHAT,MESSAGE("&7option [strict] : protection sur les points déclares"));
            return CommandResult.success();
        }
        
        String parcelName = ctx.<String> getOne("name").get();
        Boolean strict = ctx.<Boolean> getOne("strict").get();
        int amount = ctx.<Integer> getOne("amount").get();
        Location[] c = {parcelManager.getBorder1(), parcelManager.getBorder2()};

        String playerUUID = player.getUniqueId().toString();
        if(gplayer.getLevel() == 10){ playerUUID = "ADMIN";} else { gplayer.debitMoney(amount);}

        int y1 = (int)c[0].getY();
        int y2 = (int)c[1].getY();

        if(strict == false) { 
            player.sendMessage(ChatTypes.CHAT,ALL_PROTECT_PARCEL(player,parcelName));
            y1 = 0;
            y2 = 500;
        } else {
            player.sendMessage((ChatType) PROTECT_PARCEL(player,parcelName),Text.of(TextColors.GREEN," Y " + y1 + " : " + y2));
        }

        Location <World> world = c[0];
        String worldName = world.getExtent().getName();
        int x1 = c[0].getBlockX();
        int z1 = c[0].getBlockZ();
        int x2 = c[1].getBlockX();
        int z2 = c[1].getBlockZ();
        String message = "&b-- SECURE --";

        GParcel gparcel = new GParcel(parcelName,0,worldName,x1,y1,z1,x2,y2,z2,0,0,1,1,1,0,1,1,message,0,1,1,1,playerUUID,playerUUID);
        gparcel.insert();
        GData.commit();
        GData.addParcel(gparcel);

        player.sendMessage(ChatTypes.ACTION_BAR,PROTECT_LOADED_PARCEL(player,parcelName));
        player.sendMessage(ChatTypes.CHAT,Text.builder("&bClick ici, pour voir les flags de ta parcelle !").onClick(TextActions.runCommand("/p flaglist " + parcelName)).color(TextColors.AQUA).build());
        return CommandResult.success();
    }
}