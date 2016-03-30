package net.teraoctet.genesys.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static net.teraoctet.genesys.utils.MessageManager.MESSAGE;
import static net.teraoctet.genesys.utils.MessageManager.NO_PERMISSIONS;
import net.teraoctet.genesys.utils.Permissions;
import static org.spongepowered.api.Sponge.getGame;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.gamemode.GameMode;
import static org.spongepowered.api.entity.living.player.gamemode.GameModes.CREATIVE;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.service.permission.SubjectData;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.util.Tristate;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.WorldCreationSettings;
import org.spongepowered.api.world.extent.Extent;
import org.spongepowered.api.world.storage.WorldProperties;

public class CommandTest implements CommandExecutor {
            
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException {    

        Player player = (Player) src;
        /*if(!player.hasPermission("genesys.test")) { 
            sender.sendMessage(NO_PERMISSIONS()); 
            return CommandResult.success(); 
        }*/
        
        
        //WorldProperties properties = player.getLocation().getExtent().getProperties();
                    
        //String value = optValue.get();
        //properties.setGameMode(CREATIVE);//setGameRule(gamerule, value);
        //properties.setWorldBorderCenter(-220, 266);
                        
        //World w = player.getWorld();
        //WorldCreationSettings ws =  w.getCreationSettings();
        
        //WorldProperties wp = w.getProperties();
        //wp.setEnabled(false);
        //wp.setGameMode(CREATIVE);
        //wp.setEnabled(true);
        
        
        /*Extent extent = player.getLocation().getExtent();
        // We need to create the entity
        Optional<Entity> optional = extent.createEntity(EntityTypes.HUMAN,player.getLocation().getPosition());
        if (optional.isPresent()) {
            Entity human = optional.get();
            human.offer(Keys.DISPLAY_NAME, MESSAGE("thyc82"));
            human.offer(Keys.SKIN_UNIQUE_ID, player.getUniqueId());
            human.offer(Keys.REPRESENTED_PLAYER,player.getProfile());
            //human.offer(Keys.IS_SITTING,true);
            extent.spawnEntity(human, Cause.of(player));
        }*/

        //Title build;
        //build = new Title(MESSAGE("test"),MESSAGE("test"),20,20,20,true,true);
        //Title build1 = build.builder().title(MESSAGE("test")).build();
        //build.builder().build().getTitle().
        //player.sendMessage(ChatTypes.ACTION_BAR, Texts.of("Some text"));
        
        //player.sendTitle(build1);
        
            /*for (Inventory inventory : player.getInventory()) {
            inventory.query(types)
            }*/
            
            /*Optional<Entity> optional = player.getLocation().getExtent().createEntity(EntityTypes.HUMAN, player.getLocation().getPosition());
            if(optional.isPresent()) {
            Human human = (Human) optional.get();
            //human.offer(Keys.REPRESENTED_PLAYER, player.getProfile());
            human.offer(Keys.AI_ENABLED,true);
            human.offer(Keys.DISPLAY_NAME,MESSAGE("thyc82"));
            //human.offer(Keys.SKIN,player.getUniqueId());
            human.offer(Keys.IS_SITTING,true);
            
            player.getLocation().getExtent().spawnEntity(human, Cause.of(player));
            }*/
        
        //player.getSubjectData().getPermissions(SubjectData.GLOBAL_CONTEXT).put("genesys.fly", Boolean.FALSE);
        //player.getSubjectData().getParents(SubjectData.GLOBAL_CONTEXT).
        player.getSubjectData().setPermission(SubjectData.GLOBAL_CONTEXT, "genesys.fly", Tristate.FALSE); 
        List<String> msg = new ArrayList<>();
        msg = Permissions.getGroups(player);
        src.sendMessage(MESSAGE(msg,player,""));
        
        
        return CommandResult.success();
    }	
}

