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
    public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException {
        Optional<Player> tplayer = ctx.<Player> getOne("player");
        
        if (tplayer.isPresent() && src.hasPermission("genesys.kills.others")) {
            tplayer.get().offer(Keys.HEALTH, 0d);
            getGame().getServer().getBroadcastChannel().send(KILLED_BY(tplayer.get().getName(), src.getName()));
            
        } 
        
        else if (src.hasPermission("genesys.kills")){
            if(src instanceof Player) {
                Player player = (Player) src;
                player.offer(Keys.HEALTH, 0d);
                getGame().getServer().getBroadcastChannel().send(SUICIDE(src.getName())); 
            } else {
                src.sendMessage(USAGE("/kill <player>"));
            }
        }
        
        else {
            src.sendMessage(NO_PERMISSIONS());
        }
        
        return CommandResult.success();	 
    }
}
