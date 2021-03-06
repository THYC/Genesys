package net.teraoctet.genesys.commands;

import static net.teraoctet.genesys.utils.MessageManager.DAY_MESSAGE;
import static net.teraoctet.genesys.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.genesys.utils.MessageManager.NO_PERMISSIONS;
import static org.spongepowered.api.Sponge.getGame;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.world.World;

public class CommandDay implements CommandExecutor {
    
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {
    
        if(src instanceof Player && src.hasPermission("genesys.time.day")) {
            Player player = (Player) src;
            World world = player.getLocation().getExtent();        
            world.getProperties().setWorldTime(0); 
            getGame().getServer().getBroadcastChannel().send(DAY_MESSAGE(player));
            return CommandResult.success();
        }  
        
        else if (src instanceof ConsoleSource) {
            src.sendMessage(NO_CONSOLE()); 
        } 
        
        else {
            src.sendMessage(NO_PERMISSIONS());
        }
 
        return CommandResult.empty();	
    }
}


