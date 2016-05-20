package net.teraoctet.genesys.commands;

import java.util.Optional;
import static net.teraoctet.genesys.Genesys.serverManager;
import static net.teraoctet.genesys.utils.GData.getGPlayer;
import net.teraoctet.genesys.utils.GHome;
import net.teraoctet.genesys.player.GPlayer;
import net.teraoctet.genesys.utils.GConfig;
import static net.teraoctet.genesys.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.genesys.utils.MessageManager.NO_PERMISSIONS;
import static net.teraoctet.genesys.utils.MessageManager.HOME_NOT_FOUND;
import static net.teraoctet.genesys.utils.MessageManager.HOME_TP_SUCCESS;
import static net.teraoctet.genesys.utils.MessageManager.MESSAGE;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

public class CommandHome implements CommandExecutor {
    
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {       
                   
        if(src instanceof Player && src.hasPermission("genesys.home")) { 
            Player player = (Player) src;           
            GPlayer gplayer = getGPlayer(player.getUniqueId().toString());
            String homename = "default"; 
            Optional<String> home = ctx.<String> getOne("home");

            if(home.isPresent()) { 
                homename = home.get().toLowerCase();
            }
            
            if(gplayer.getHome(homename).isPresent()){
                GHome ghome = gplayer.getHome(homename).get();
                Text msg = HOME_TP_SUCCESS(player,"");
                if (!homename.equalsIgnoreCase("default")){
                    msg = HOME_TP_SUCCESS(player,homename);
                }
                serverManager.teleport(player, ghome.getWorld(), ghome.getX(), ghome.getY(), ghome.getZ(),msg);
                src.sendMessage(MESSAGE("&eVous serez TP dans environ " + GConfig.COOLDOWN_TO_TP()));

                return CommandResult.success();
            }else{
                src.sendMessage(HOME_NOT_FOUND()); 
                return CommandResult.empty();
            }
        } 
        
        else if (src instanceof ConsoleSource){
            src.sendMessage(NO_CONSOLE());
        }
        
        else {
            src.sendMessage (NO_PERMISSIONS());
        }
        
        return CommandResult.empty();
    }
}
