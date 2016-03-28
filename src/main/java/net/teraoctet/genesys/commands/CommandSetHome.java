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
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;


public class CommandSetHome implements CommandExecutor {
    
    @Override
    public CommandResult execute(CommandSource sender, CommandContext ctx) throws CommandException {

        Player player = (Player) sender;
        if(!player.hasPermission("genesys.sethome")) { 
            sender.sendMessage(NO_PERMISSIONS()); 
            return CommandResult.success(); 
        }
                   
        if(sender instanceof Player == false) { 
            sender.sendMessage(NO_CONSOLE()); 
            return CommandResult.success(); 
        }
        
	Optional<String> home = ctx.<String> getOne("home");
        GPlayer gplayer = getGPlayer(player.getUniqueId().toString());
        String name = "default"; 
        if(home.isPresent()) name = home.get().toLowerCase();

        HashMap<String, GHome> homes = gplayer.getHomes();
        if(homes.containsKey(name)) {
            sender.sendMessage(HOME_ALREADY_EXIST());
            return CommandResult.success(); 
        }

        int nbHomes = 0;
        for(int i = 1; i <= 100; i++) {if(player.hasPermission("genesys.sethome." + i)) nbHomes = i;}

        if(!player.hasPermission("genesys.home.unlimited") && nbHomes <= homes.size()) {
            sender.sendMessage(NB_ALLOWED_HOME(player,String.valueOf(nbHomes)));
            return CommandResult.success(); 
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
            sender.sendMessage(HOME_SET_SUCCESS(player,""));
        } else {
            sender.sendMessage(HOME_SET_SUCCESS(player,name));
        }
        
        if(!player.hasPermission("genesys.home.unlimited")) sender.sendMessage(NB_HOME(player,String.valueOf(homes.size()),"illimité"));
        else sender.sendMessage(NB_HOME(player,String.valueOf(homes.size()),String.valueOf(nbHomes)));

	return CommandResult.success();
    }
}
