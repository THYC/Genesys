package net.teraoctet.genesys.commands;

import java.util.Optional;
import static net.teraoctet.genesys.utils.GData.getGPlayer;
import net.teraoctet.genesys.utils.GHome;
import net.teraoctet.genesys.player.GPlayer;
import static net.teraoctet.genesys.utils.GData.commit;
import static net.teraoctet.genesys.utils.MessageManager.HOME_DEL_SUCCESS;
import static net.teraoctet.genesys.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.genesys.utils.MessageManager.NO_PERMISSIONS;
import static net.teraoctet.genesys.utils.MessageManager.HOME_NOT_FOUND;
import static net.teraoctet.genesys.utils.MessageManager.USAGE;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;

public class CommandDelhome implements CommandExecutor {
    
    @Override
    public CommandResult execute(CommandSource sender, CommandContext ctx) throws CommandException {

        Player player = (Player) sender;
        if(!player.hasPermission("genesys.delhome")) { 
            sender.sendMessage(NO_PERMISSIONS()); 
            return CommandResult.success(); 
        }
                   
        if(sender instanceof Player == false) { 
            sender.sendMessage(NO_CONSOLE()); 
            return CommandResult.success(); 
        }
            
        GPlayer gplayer = getGPlayer(player.getUniqueId().toString());
        String homename = "default"; 
        Optional<String> home = ctx.<String> getOne("home");
        
        if(home.isPresent()) { 
            homename = home.get().toLowerCase();
        }
        
        GHome ghome = gplayer.getHome(homename);

        if(ghome == null) { 
            sender.sendMessage(HOME_NOT_FOUND()); 
            return CommandResult.success(); 
        }
        
        ghome.delete();
        gplayer.removeGHome(homename);
        commit();
        sender.sendMessage(HOME_DEL_SUCCESS(player,""));
        return CommandResult.success();
    }
}
