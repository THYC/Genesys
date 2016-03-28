package net.teraoctet.genesys.commands;

import net.teraoctet.genesys.utils.GData;
import static net.teraoctet.genesys.utils.MessageManager.NO_PERMISSIONS;
import static net.teraoctet.genesys.utils.MessageManager.OTHER_TELEPORTED_TO_WORLD;
import static net.teraoctet.genesys.utils.MessageManager.PLAYER_NOT_FOUND;
import static net.teraoctet.genesys.utils.MessageManager.TELEPORTED_TO_WORLD;
import static net.teraoctet.genesys.utils.MessageManager.USAGE;
import static net.teraoctet.genesys.utils.MessageManager.WORLD_NOT_FOUND;
import net.teraoctet.genesys.world.GWorld;

import static org.spongepowered.api.Sponge.getGame;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.chat.ChatTypes;
import org.spongepowered.api.world.World;
    
public class CommandWorldTP implements CommandExecutor {
            
    @Override
    public CommandResult execute(CommandSource sender, CommandContext ctx) throws CommandException {
	
        Player player = null;
        if (sender instanceof Player){
            player = (Player)sender;
            if(!player.hasPermission("genesys.world.worldtp")) { player.sendMessage(ChatTypes.CHAT,NO_PERMISSIONS()); return CommandResult.success();}
        }
        	 
        if(!ctx.getOne("worldName").isPresent()) { 
            sender.sendMessage(USAGE("/worldtp <world> [player]"));
            return CommandResult.success();
        }
                
        String worldName = ctx.<String> getOne("worldName").get();
        
        if(!ctx.getOne("target").isPresent() && sender instanceof Player == false) { 
            sender.sendMessage(USAGE("/worldtp <world> <player>"));
            return CommandResult.success();
        } 
                
        if(!getGame().getServer().getWorld(worldName).isPresent()) { 
            sender.sendMessage(WORLD_NOT_FOUND(player,worldName));return CommandResult.success(); 
        }
        
        World world = getGame().getServer().getWorld(worldName).get(); 
        GWorld gworld = GData.getWorld(worldName);
        
        if(gworld == null) {
            sender.sendMessage(WORLD_NOT_FOUND(player,worldName));return CommandResult.success();
        }
                        
        if(!ctx.getOne("target").isPresent()) {		
            player.setLocation(world.getSpawnLocation());
            player.offer(Keys.GAME_MODE, gworld.getGamemode());
            sender.sendMessage(TELEPORTED_TO_WORLD(player,worldName));
            return CommandResult.success();
        }
        
        Player target = ctx.<Player> getOne("target").get();
        
        if(!sender.hasPermission("genesys.admin.world.worldtp")) { sender.sendMessage(NO_PERMISSIONS()); return CommandResult.success();}
        	
        if(ctx.getOne("target").isPresent()) {
            if(target == null) {
                sender.sendMessage(PLAYER_NOT_FOUND(target));
                return CommandResult.success();
            }
            target.setLocation(world.getSpawnLocation());
            target.offer(Keys.GAME_MODE, gworld.getGamemode());
            sender.sendMessage(OTHER_TELEPORTED_TO_WORLD(target,worldName));
            target.sendMessage(ChatTypes.CHAT,TELEPORTED_TO_WORLD(target,worldName));
        }
        return CommandResult.success();
    }
}
