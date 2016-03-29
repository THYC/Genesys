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
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;

public class CommandDelhome implements CommandExecutor {
    
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException {

        if(src instanceof Player && src.hasPermission("genesys.delhome")) {
            Player player = (Player) src;  
            GPlayer gplayer = getGPlayer(player.getUniqueId().toString());
            String homename = "default"; 
            Optional<String> home = ctx.<String> getOne("home");

            if(home.isPresent()) { 
                homename = home.get().toLowerCase();
            }

            GHome ghome = gplayer.getHome(homename);

            if(ghome == null) { 
                src.sendMessage(HOME_NOT_FOUND()); 
                return CommandResult.success(); 
            }

            ghome.delete();
            gplayer.removeGHome(homename);
            commit();
            src.sendMessage(HOME_DEL_SUCCESS(player,""));
            
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
