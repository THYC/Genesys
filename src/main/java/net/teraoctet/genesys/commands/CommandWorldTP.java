package net.teraoctet.genesys.commands;

import net.teraoctet.genesys.utils.GData;
import static net.teraoctet.genesys.utils.MessageManager.NO_PERMISSIONS;
import static net.teraoctet.genesys.utils.MessageManager.OTHER_TELEPORTED_TO_WORLD;
import static net.teraoctet.genesys.utils.MessageManager.NOT_FOUND;
import static net.teraoctet.genesys.utils.MessageManager.TELEPORTED_TO_WORLD;
import static net.teraoctet.genesys.utils.MessageManager.USAGE;
import static net.teraoctet.genesys.utils.MessageManager.NOT_FOUND;
import net.teraoctet.genesys.world.GWorld;

import static org.spongepowered.api.Sponge.getGame;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.world.World;
    
public class CommandWorldTP implements CommandExecutor {
            
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {
	
        if (src.hasPermission("genesys.world.worldtp")){ 
            //si le joueur ne tape pas le <world>
            if(!ctx.getOne("worldName").isPresent() && src instanceof Player) { 
                src.sendMessage(USAGE("/worldtp <world> [player]"));
            }

            //si ce n'est pas un joueur (console) et que <world> ou <player> ne sont pas renseignés
            else if((!ctx.getOne("target").isPresent() || !ctx.getOne("worldName").isPresent()) && !(src instanceof Player)) { 
                src.sendMessage(USAGE("/worldtp <world> <player>"));
            }

            //quand la commande est correctement renseignée par la source
            else {
                String worldName = ctx.<String> getOne("worldName").get();
                GWorld gworld = GData.getWorld(worldName);
            
                //monde introuvable
                if(!getGame().getServer().getWorld(worldName).isPresent()) { 
                    src.sendMessage(NOT_FOUND(worldName));
                } else if(gworld == null) {
                    src.sendMessage(NOT_FOUND(worldName));
                }
                //le monde est correctement trouvé
                else {
                    World world = getGame().getServer().getWorld(worldName).get(); 

                    //si [player] n'est pas renseigné, la source est ciblé (doit être un joueur)
                    if(!ctx.getOne("target").isPresent()) {
                        Player player = (Player)src;
                        player.setLocation(world.getSpawnLocation());
                        player.offer(Keys.GAME_MODE, gworld.getGamemode());
                        src.sendMessage(TELEPORTED_TO_WORLD(player,worldName));
                        return CommandResult.success();
                    }

                    //lorsque un [player] est ciblé par la commande
                    else if(ctx.getOne("target").isPresent() && src.hasPermission("genesys.admin.world.worldtp")) {
                        if(src.hasPermission("genesys.admin.world.worldtp")){
                            Player target = ctx.<Player> getOne("target").get();
                            if(target != null) {
                                target.setLocation(world.getSpawnLocation());
                                target.offer(Keys.GAME_MODE, gworld.getGamemode());
                                src.sendMessage(OTHER_TELEPORTED_TO_WORLD(target,worldName));
                                target.sendMessage(TELEPORTED_TO_WORLD(target,worldName));
                                return CommandResult.success();
                            } else {
                                src.sendMessage(NOT_FOUND(target.getName()));
                            }
                        } else {
                            src.sendMessage(NO_PERMISSIONS());
                        }  
                    }   
                }
            }
        }
        
        else {
            src.sendMessage(NO_PERMISSIONS());
        }
        
        return CommandResult.empty();
    }
}
