package net.teraoctet.genesys.commands;

import java.util.Optional;
import static net.teraoctet.genesys.utils.GData.getGPlayer;
import net.teraoctet.genesys.player.GPlayer;
import static net.teraoctet.genesys.utils.MessageManager.MESSAGE;
import static net.teraoctet.genesys.utils.MessageManager.NO_PERMISSIONS;
import static net.teraoctet.genesys.utils.MessageManager.NOT_FOUND;
import static net.teraoctet.genesys.utils.MessageManager.USAGE;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;

public class CommandLevel implements CommandExecutor {
    
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {
        
        if(src.hasPermission("genesys.admin.level")) { 
            if(!ctx.getOne("level").isPresent() || (src instanceof ConsoleSource && !ctx.getOne("player").isPresent())){
                if(src instanceof Player) {
                    src.sendMessage(USAGE("/level <level> [player]"));
                } else {
                    src.sendMessage(USAGE("/level <level> <player>"));
                }
                return CommandResult.empty();
            }

            Optional<Integer> level = ctx.<Integer> getOne("level");
            Player player = null;
            GPlayer gplayer = null;
            
            if(ctx.getOne("player").isPresent()) {
                player = ctx.<Player> getOne("player").get();
                if(!player.isOnline()) {
                    src.sendMessage(NOT_FOUND(player.getName()));
                    return CommandResult.empty();
                }
            } else {
                player = (Player) src; 
            }

            gplayer = getGPlayer(player.getUniqueId().toString()); 
            gplayer.setLevel(level.get());
            src.sendMessage(MESSAGE("&6%name% est mont\351e au level %var1%",player,String.valueOf(level.get())));
            return CommandResult.success();
        }
        
        else {
            src.sendMessage(NO_PERMISSIONS());
        }
 
        return CommandResult.empty();
    }
}