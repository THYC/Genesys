package net.teraoctet.genesys.commands;

import static net.teraoctet.genesys.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.genesys.utils.MessageManager.NO_PERMISSIONS;
import static net.teraoctet.genesys.utils.MessageManager.STORM_MESSAGE;
import static org.spongepowered.api.Sponge.getGame;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.weather.Weathers;

public class CommandStorm implements CommandExecutor {
        
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException {

        if(src instanceof Player && src.hasPermission("genesys.weather.storm")) { 
            Player player = (Player) src;
            World world = player.getLocation().getExtent();
            world.setWeather(Weathers.THUNDER_STORM);
            getGame().getServer().getBroadcastChannel().send(STORM_MESSAGE(player));
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


