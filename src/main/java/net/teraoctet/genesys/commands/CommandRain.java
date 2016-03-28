package net.teraoctet.genesys.commands;

import static net.teraoctet.genesys.utils.MessageManager.NO_PERMISSIONS;
import static net.teraoctet.genesys.utils.MessageManager.RAIN_MESSAGE;
import static org.spongepowered.api.Sponge.getGame;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.weather.Weathers;
    
public class CommandRain implements CommandExecutor {
    
    @Override
    public CommandResult execute(CommandSource sender, CommandContext ctx) throws CommandException {

        Player player = (Player) sender;    
        if(!player.hasPermission("genesys.weather.rain")) { 
            sender.sendMessage(NO_PERMISSIONS()); 
            return CommandResult.success(); 
        }
        
        World world = player.getWorld();
        world.setWeather(Weathers.RAIN);
        getGame().getServer().getBroadcastChannel().send(RAIN_MESSAGE(player));
        return CommandResult.success();	
    }
}
