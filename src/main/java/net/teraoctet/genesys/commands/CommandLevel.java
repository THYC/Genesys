package net.teraoctet.genesys.commands;

import java.util.Optional;
import static net.teraoctet.genesys.utils.GData.getGPlayer;
import net.teraoctet.genesys.player.GPlayer;
import static net.teraoctet.genesys.utils.MessageManager.MESSAGE;
import static net.teraoctet.genesys.utils.MessageManager.NO_PERMISSIONS;
import static net.teraoctet.genesys.utils.MessageManager.USAGE;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;

public class CommandLevel implements CommandExecutor {
    
    @Override
    public CommandResult execute(CommandSource sender, CommandContext ctx) throws CommandException {
        
        if(!sender.hasPermission("genesys.admin.level")) { 
            sender.sendMessage(NO_PERMISSIONS()); 
            return CommandResult.success(); 
        }
      
        if(!ctx.getOne("level").isPresent()){
            sender.sendMessage(USAGE("/level <level> [playerName]"));
            return CommandResult.success();
        }
        
        Optional<Integer> level = ctx.<Integer> getOne("level");
        Player player = null;
        GPlayer gplayer = null;
        
        if(!ctx.getOne("player").isPresent()){
            player = (Player) sender;           
        } else {
            player = ctx.<Player> getOne("player").get();
            if (!player.isOnline()){
                sender.sendMessage(MESSAGE("&6Le joueur doit etre connecte"));
                return CommandResult.success();
            }
        }
        gplayer = getGPlayer(player.getUniqueId().toString()); 
        gplayer.setLevel(level.get());
        sender.sendMessage(MESSAGE("&6%name% est montee au level %var1%",player,String.valueOf(level.get())));
        
        return CommandResult.success();
    }
}