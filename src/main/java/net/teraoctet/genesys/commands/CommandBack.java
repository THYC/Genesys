package net.teraoctet.genesys.commands;

import net.teraoctet.genesys.utils.DeSerialize;
import net.teraoctet.genesys.utils.GData;
import static net.teraoctet.genesys.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.genesys.utils.MessageManager.NO_PERMISSIONS;
import static net.teraoctet.genesys.utils.MessageManager.TP_BACK;
import static net.teraoctet.genesys.utils.DeSerialize.getLocation;
import net.teraoctet.genesys.player.GPlayer;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

public class CommandBack implements CommandExecutor {
    
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException {
                
        if (src instanceof Player && src.hasPermission("genesys.back")){           
            Player player = (Player) src;
            GPlayer gplayer = GData.getGPlayer(player.getUniqueId().toString());
            Location<World> location = getLocation(gplayer.getLastposition());
            gplayer.setLastposition(DeSerialize.location(player.getLocation()));
            gplayer.update();
                
            if (player.getLocation().getExtent().getUniqueId().equals(location.getExtent().getUniqueId())) {
                player.setLocation(location);
            } 
            
            else {
                player.transferToWorld(location.getExtent().getUniqueId(), location.getPosition());
            }		
            
            src.sendMessage(TP_BACK(player));
	} 
       
        else if(src instanceof ConsoleSource) {
            src.sendMessage(NO_CONSOLE());
	}
        
        else {
            src.sendMessage(NO_PERMISSIONS());
        }
        
	return CommandResult.success();
    }
}
