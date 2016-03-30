package net.teraoctet.genesys.commands;

import java.util.HashMap;
import java.util.Optional;
import static net.teraoctet.genesys.utils.GData.commit;
import static net.teraoctet.genesys.utils.GData.getGPlayer;
import net.teraoctet.genesys.utils.GHome;
import net.teraoctet.genesys.player.GPlayer;
import static net.teraoctet.genesys.utils.MessageManager.NB_ALLOWED_HOME;
import static net.teraoctet.genesys.utils.MessageManager.HOME_SET_SUCCESS;
import static net.teraoctet.genesys.utils.MessageManager.HOME_ALREADY_EXIST;
import static net.teraoctet.genesys.utils.MessageManager.NB_HOME;
import static net.teraoctet.genesys.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.genesys.utils.MessageManager.NO_PERMISSIONS;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;


public class CommandSetHome implements CommandExecutor {
    
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {

        if(src instanceof Player && src.hasPermission("genesys.sethome")) { 
            Player player = (Player) src;
            Optional<String> home = ctx.<String> getOne("home");
            GPlayer gplayer = getGPlayer(player.getUniqueId().toString());
            String name = "default"; 
            
            if(home.isPresent()) name = home.get().toLowerCase();

            HashMap<String, GHome> homes = gplayer.getHomes();
            if(homes.containsKey(name)) {
                src.sendMessage(HOME_ALREADY_EXIST());
                return CommandResult.empty(); 
            }

            int nbHomes = 0;
            for(int i = 1; i <= 100; i++) {if(player.hasPermission("genesys.sethome." + i)) nbHomes = i;}

            if(!player.hasPermission("genesys.home.unlimited") && nbHomes <= homes.size()) {
                src.sendMessage(NB_ALLOWED_HOME(player,String.valueOf(nbHomes)));
                return CommandResult.empty(); 
            }

            String world = player.getWorld().getName();
            double x = player.getLocation().getX();
            double y = player.getLocation().getY();
            double z = player.getLocation().getZ();

            GHome ghome = new GHome(gplayer.getUUID(), name, world, x, y, z);
            ghome.insert();
            commit();
            gplayer.setHome(name, ghome);

            if(name.equalsIgnoreCase("default")){
                src.sendMessage(HOME_SET_SUCCESS(player,""));
            } else {
                src.sendMessage(HOME_SET_SUCCESS(player,name));
            }

            if(!player.hasPermission("genesys.home.unlimited")) src.sendMessage(NB_HOME(player,String.valueOf(homes.size()),"illimité"));
            else src.sendMessage(NB_HOME(player,String.valueOf(homes.size()),String.valueOf(nbHomes)));  
            
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
