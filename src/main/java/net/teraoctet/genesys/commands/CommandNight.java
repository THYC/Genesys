package net.teraoctet.genesys.commands;

import static net.teraoctet.genesys.utils.MessageManager.NIGHT_MESSAGE;
import static net.teraoctet.genesys.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.genesys.utils.MessageManager.NO_PERMISSIONS;
import static org.spongepowered.api.Sponge.getGame;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.world.World;

public class CommandNight implements CommandExecutor {
        
    @Override
    public CommandResult execute(CommandSource sender, CommandContext ctx) throws CommandException {

        Player player = (Player) sender;    
        if(!player.hasPermission("genesys.time.night")) { 
                sender.sendMessage(NO_PERMISSIONS()); 
                return CommandResult.success(); 
        }
        
        World world = player.getLocation().getExtent();
            
        if(sender instanceof Player == false) { 
            sender.sendMessage(NO_CONSOLE()); 
            return CommandResult.success(); 
        }
        
        world.getProperties().setWorldTime(12); 
        //getGame().getServer().getBroadcastSink().sendMessage(NIGHT_MESSAGE(player));
        getGame().getServer().getBroadcastChannel().send(NIGHT_MESSAGE(player));
 
        return CommandResult.success();	
    }
}


