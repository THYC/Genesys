package net.teraoctet.genesys.commands;

import java.util.Optional;
import static net.teraoctet.genesys.utils.MessageManager.NO_PERMISSIONS;
import static net.teraoctet.genesys.utils.MessageManager.KILLED_BY;
import static net.teraoctet.genesys.utils.MessageManager.SUICIDE;
import static net.teraoctet.genesys.utils.MessageManager.USAGE;
import static org.spongepowered.api.Sponge.getGame;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;

public class CommandKill implements CommandExecutor {
    
    @Override
    public CommandResult execute(CommandSource sender, CommandContext ctx) throws CommandException {
        Optional<Player> tplayer = ctx.<Player> getOne("player");
            
        if(!sender.hasPermission("genesys.kills")) { 
            sender.sendMessage(NO_PERMISSIONS()); 
            return CommandResult.success(); 
        }

        if (tplayer.isPresent()) {
            if(!sender.hasPermission("genesys.kills.others")) {
                sender.sendMessage(NO_PERMISSIONS()); 
            } else {
                tplayer.get().offer(Keys.HEALTH, 0d);
                getGame().getServer().getBroadcastChannel().send(KILLED_BY(tplayer.get().getName(), sender.getName()));
            }
            return CommandResult.success();
        } else {
            if(sender instanceof Player == false) { 
                sender.sendMessage(USAGE("/kill <player>")); 
            } else {
                Player player = (Player) sender;
                player.offer(Keys.HEALTH, 0d);
                getGame().getServer().getBroadcastChannel().send(SUICIDE(sender.getName())); 
            }
        }
        return CommandResult.success();	 
    }
}
