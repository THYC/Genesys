package net.teraoctet.genesys.commands;

import java.util.Optional;
import static net.teraoctet.genesys.Genesys.portalManager;
import net.teraoctet.genesys.plot.PlotManager;
import net.teraoctet.genesys.portal.GPortal;
import net.teraoctet.genesys.utils.GData;
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
import org.spongepowered.api.text.chat.ChatTypes;
import org.spongepowered.api.text.serializer.TextSerializers;
import static net.teraoctet.genesys.utils.MessageManager.MESSAGE;
import static net.teraoctet.genesys.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.genesys.utils.MessageManager.NO_PERMISSIONS;
import static net.teraoctet.genesys.utils.MessageManager.PLOT_NAME_ALREADY_USED;
import org.spongepowered.api.command.source.ConsoleSource;

public class CommandPortal implements CommandExecutor {
    
    public CommandPortal(Game game){}
       
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException {

        if(src instanceof Player && src.hasPermission("genesys.admin.portal")) { 
            Optional<String> arguments = ctx.<String> getOne("arguments");
            String[] args = arguments.get().split(" ");

            if(!arguments.isPresent()) {
                PaginationService paginationService = getGame().getServiceManager().provide(PaginationService.class).get();
                Builder builder = paginationService.builder();

                builder.title(Text.builder().append(TextSerializers.formattingCode('&').deserialize("&6Portal")).toText())
                .contents(Text.builder().append(TextSerializers.formattingCode('&').deserialize("&6/portal create <name> : &7cr\351ation d'un portail au point d\351clar\351")).toText(),
                    Text.builder().append(TextSerializers.formattingCode('&').deserialize("&6/portal remove <name> : &7supprime le portail")).toText(),
                    Text.builder().append(TextSerializers.formattingCode('&').deserialize("&6/portal tpfrom  <name> : &7enregistre le point d'arriv\351 du portail")).toText(),
                    Text.builder().append(TextSerializers.formattingCode('&').deserialize("&6/portal list : &7liste les portails")).toText(),
                    Text.builder().append(TextSerializers.formattingCode('&').deserialize("&6/portal message <name> : &7affiche le message d'arriv\351e du portail")).toText(),
                    Text.builder().append(TextSerializers.formattingCode('&').deserialize("&6/portal message <name> <message> : &7modifie le message d'arriv\351e du portail")).toText())
                .header(Text.builder().append(TextSerializers.formattingCode('&').deserialize("&eUsage:")).toText())
                .padding(Text.of("-"))
                .sendTo(src);

                return CommandResult.success();	
            }

            Player player = (Player) src;
            PlotManager plotManager = PlotManager.getSett(player);

            if("remove".equalsIgnoreCase(args[0])) {
                if(args.length > 1){
                    String name = args[1];
                    if (portalManager.hasPortal(name) == true){
                        GPortal gportal = portalManager.getPortal(name);
                        gportal.delete();
                        GData.commit();
                        GData.removePortal(gportal);
                        player.sendMessage(ChatTypes.CHAT,MESSAGE("&e" + gportal.getName() + " &aa \351t\351 supprim\351"));
                    } else {
                        player.sendMessage(ChatTypes.CHAT,PLOT_NAME_ALREADY_USED());
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
                        player.sendMessage(ChatTypes.CHAT,MESSAGE("&e" + gportal.getName() + ": &a point d'arriv\351 enregistr\351"));
                    } else {
                        player.sendMessage(ChatTypes.CHAT,PLOT_NAME_ALREADY_USED());
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
                        player.sendMessage(ChatTypes.CHAT,PLOT_NAME_ALREADY_USED());
                        return CommandResult.success();
                    }   
                } else {
                    player.sendMessage(ChatTypes.CHAT,USAGE("/portal message <name> [message]"));
                    return CommandResult.success();
                }
            }    
        } 
        
        else if (src instanceof ConsoleSource) {
            src.sendMessage(NO_CONSOLE()); 
        }
        
        else {
            src.sendMessage(NO_PERMISSIONS());
        }

        return CommandResult.success();
    }
}