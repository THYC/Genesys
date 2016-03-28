package net.teraoctet.genesys.commands;

import net.teraoctet.genesys.parcel.ParcelManager;
import net.teraoctet.genesys.portal.GPortal;
import net.teraoctet.genesys.utils.GData;
import static net.teraoctet.genesys.utils.MessageManager.MESSAGE;
import static net.teraoctet.genesys.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.genesys.utils.MessageManager.NO_PERMISSIONS;
import static net.teraoctet.genesys.utils.MessageManager.PROTECT_LOADED_PARCEL;
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
import org.spongepowered.api.world.World;

public class CommandPortalCreateOK implements CommandExecutor {
           
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
            player.sendMessage(ChatTypes.CHAT,MESSAGE("&bVous devez utliser la commande &7/portal create &bpour creer un portail :"));
            player.sendMessage(ChatTypes.CHAT,USAGE("/portal create <name> : creation d'un portail au point declare"));
            return CommandResult.success();
        }

        String portalName = ctx.<String> getOne("name").get();
        Location[] c = {parcelManager.getBorder1(), parcelManager.getBorder2()};
        Location <World> world = c[0];
        String worldName = world.getExtent().getName();
        int x1 = c[0].getBlockX();
        int y1 = c[0].getBlockY();
        int z1 = c[0].getBlockZ();
        int x2 = c[1].getBlockX();
        int y2 = c[1].getBlockY();
        int z2 = c[1].getBlockZ();
        String message = "&c.. vers l infini et au dela ...";

        GPortal gportal = new GPortal(portalName,0,worldName,x1,y1,z1,x2,y2,z2,message);
        gportal.insert();
        GData.commit();
        GData.addPortal(gportal);
        
        player.sendMessage(ChatTypes.CHAT,Text.builder("Click ici pour lire le message d'accueil par defaut du portail").onClick(TextActions.runCommand("/portal message " + portalName )).color(TextColors.AQUA).build());
        player.sendMessage(ChatTypes.CHAT,Text.builder("Tape /portal message <message> &bpour pour modifier le message par defaut").onClick(TextActions.suggestCommand("/portal message " + portalName + " 'remplace ce texte par ton message'")).color(TextColors.AQUA).build());
        player.sendMessage(ChatTypes.ACTION_BAR,PROTECT_LOADED_PARCEL(player,portalName));
        return CommandResult.success();
    }                         
}