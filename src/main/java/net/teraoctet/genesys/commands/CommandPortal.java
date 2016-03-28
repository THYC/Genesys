package net.teraoctet.genesys.commands;

import java.util.Optional;
import static net.teraoctet.genesys.Genesys.portalManager;
import net.teraoctet.genesys.parcel.ParcelManager;
import net.teraoctet.genesys.portal.GPortal;
import net.teraoctet.genesys.utils.GData;
import static net.teraoctet.genesys.utils.MessageManager.MESSAGE;
import static net.teraoctet.genesys.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.genesys.utils.MessageManager.NO_PERMISSIONS;
import static net.teraoctet.genesys.utils.MessageManager.PARCEL_NAME_FAILED;
import static net.teraoctet.genesys.utils.MessageManager.PROTECT_LOADED_PARCEL;
import static net.teraoctet.genesys.utils.MessageManager.UNDEFINED_PARCEL;
import static net.teraoctet.genesys.utils.MessageManager.USAGE;
import org.spongepowered.api.Game;
import static org.spongepowered.api.Sponge.getGame;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.service.pagination.PaginationList.Builder;
import org.spongepowered.api.service.pagination.PaginationService;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.chat.ChatTypes;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.serializer.TextSerializers;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

public class CommandPortal implements CommandExecutor {
    
    public CommandPortal(Game game){}
       
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
        
        Optional<String> arguments = ctx.<String> getOne("arguments");
        String[] args = arguments.get().split(" ");
        
        if(!arguments.isPresent()) {
            PaginationService paginationService = getGame().getServiceManager().provide(PaginationService.class).get();
            Builder builder = paginationService.builder();
            
            builder.title(Text.builder().append(TextSerializers.formattingCode('&').deserialize("&6Portal")).toText())
            .contents(Text.builder().append(TextSerializers.formattingCode('&').deserialize("&6/portal create <name> : &7creation d'un portail au point declare")).toText(),
                Text.builder().append(TextSerializers.formattingCode('&').deserialize("&6/portal remove <name> : &7supprime le portail")).toText(),
                Text.builder().append(TextSerializers.formattingCode('&').deserialize("&6/portal tpfrom  <name> : &7enristre le point d'arrivé du portail")).toText(),
                Text.builder().append(TextSerializers.formattingCode('&').deserialize("&6/portal list : &7liste les portails")).toText(),
                Text.builder().append(TextSerializers.formattingCode('&').deserialize("&6/portal message <name> : &7affiche le message d'arrivée du portail")).toText(),
                Text.builder().append(TextSerializers.formattingCode('&').deserialize("&6/portal message <name> <message> : &7modifie le message d'arrivée du portail")).toText())
            .header(Text.builder().append(TextSerializers.formattingCode('&').deserialize("&eUsage:")).toText())
            .padding(Text.of("-"))
            .sendTo(sender);
           
            return CommandResult.success();	
        }
                
        if("remove".equalsIgnoreCase(args[0])) {
            if(args.length > 1){
                String name = args[1];
                if (portalManager.hasPortal(name) == true){
                    GPortal gportal = portalManager.getPortal(name);
                    gportal.delete();
                    GData.commit();
                    GData.removePortal(gportal);
                    player.sendMessage(ChatTypes.CHAT,MESSAGE("&e" + gportal.getName() + " &aa été supprimé"));
                } else {
                    player.sendMessage(ChatTypes.CHAT,PARCEL_NAME_FAILED());
                    return CommandResult.success();
                }
            } else {
                player.sendMessage(ChatTypes.CHAT,USAGE("/portal remove <name>"));
                return CommandResult.success();
            }	
        }
        
        if("tpfrom".equalsIgnoreCase(args[0])) {
            if(args.length > 1){
                String name = args[1];
                if (portalManager.hasPortal(name) == true){
                    GPortal gportal = portalManager.getPortal(name);
                    gportal.settoworld(player.getWorld().getName());
                    gportal.settoX(player.getLocation().getBlockX());
                    gportal.settoY(player.getLocation().getBlockY());
                    gportal.settoZ(player.getLocation().getBlockZ());
                    gportal.update();
                    GData.commit();
                    player.sendMessage(ChatTypes.CHAT,MESSAGE("&e" + gportal.getName() + ": &a point d'arrivé enregistré"));
                } else {
                    player.sendMessage(ChatTypes.CHAT,PARCEL_NAME_FAILED());
                    return CommandResult.success();
                }
            } else {
                player.sendMessage(ChatTypes.CHAT,USAGE("/portal tpfrom <name>"));
                return CommandResult.success();
            }	
        }
        
        if("list".equalsIgnoreCase(args[0])) {
            Text list = portalManager.listPortal();
            player.sendMessage(ChatTypes.CHAT,list); 
            return CommandResult.success();	
        }
        
        if("message".equalsIgnoreCase(args[0])) {
            if(args.length > 1){
                String name = args[1];
                if (portalManager.hasPortal(name) == true){
                    GPortal gportal = portalManager.getPortal(name);
                    if(args.length == 2){
                        Text msg = MESSAGE(gportal.getMessage());
                        player.sendMessage(ChatTypes.CHAT,msg); 
                        return CommandResult.success();
                    } else if (args.length > 2) {
                        String smsg = "";
                        for(int i = 2; i < args.length; i++){
                           smsg = smsg + args[i] + " ";
                        }
                        Text msg = MESSAGE(smsg);
                        gportal.setMessage(smsg);
                        player.sendMessage(ChatTypes.CHAT,MESSAGE("&cMessage :"));
                        player.sendMessage(ChatTypes.CHAT,msg);
                        return CommandResult.success();
                    }     
                } else {
                    player.sendMessage(ChatTypes.CHAT,PARCEL_NAME_FAILED());
                    return CommandResult.success();
                }   
            } else {
                player.sendMessage(ChatTypes.CHAT,USAGE("/portal message <name> [message]"));
                return CommandResult.success();
            }
        }
        
        return CommandResult.success();
    }
}