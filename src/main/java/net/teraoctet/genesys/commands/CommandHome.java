package net.teraoctet.genesys.commands;

import com.flowpowered.math.vector.Vector3d;
import java.util.Optional;
import net.teraoctet.genesys.utils.DeSerialize;
import static net.teraoctet.genesys.utils.GData.getGPlayer;
import net.teraoctet.genesys.utils.GHome;
import net.teraoctet.genesys.player.GPlayer;
import static net.teraoctet.genesys.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.genesys.utils.MessageManager.NO_PERMISSIONS;
import static net.teraoctet.genesys.utils.MessageManager.HOME_NOT_FOUND;
import static net.teraoctet.genesys.utils.MessageManager.ERROR;
import static net.teraoctet.genesys.utils.MessageManager.HOME_TP_SUCCESS;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.world.Location;

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

            GHome ghome = gplayer.getHome(homename);
            if(ghome == null) { 
                src.sendMessage(HOME_NOT_FOUND()); 
                return CommandResult.empty();
            }
           
            Location lastLocation = player.getLocation();

            if(!player.transferToWorld(ghome.getWorld(), new Vector3d(ghome.getX(), ghome.getY(), ghome.getZ()))) { 
                src.sendMessage(ERROR()); 
                return CommandResult.empty(); 
            }

            gplayer.setLastposition(DeSerialize.location(lastLocation));
            gplayer.update();

            if (homename.equalsIgnoreCase("default")){
                src.sendMessage(HOME_TP_SUCCESS(player,""));
            } else {
                src.sendMessage(HOME_TP_SUCCESS(player,homename));
            }
            return CommandResult.success();
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
