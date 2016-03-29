package net.teraoctet.genesys.commands;

import com.flowpowered.math.vector.Vector3d; 

import org.spongepowered.api.Sponge; 
import org.spongepowered.api.command.CommandException; 
import org.spongepowered.api.command.CommandResult; 
import org.spongepowered.api.command.CommandSource; 
import org.spongepowered.api.command.args.CommandContext; 
import org.spongepowered.api.data.key.Keys; 
import org.spongepowered.api.entity.living.player.Player; 
import org.spongepowered.api.text.Text; 
import org.spongepowered.api.text.format.TextColors; 

import java.util.Optional; 
import static net.teraoctet.genesys.utils.MessageManager.USAGE;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.chat.ChatTypes;

public class CommandRocket implements CommandExecutor{ 
    
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException { 
        Optional<Player> target = ctx.<Player> getOne("target"); 
        Optional<String> targets = ctx.<String> getOne("targets"); 

        if (!target.isPresent() && !targets.isPresent()) { 
            if (src instanceof Player) { 
                Player player = (Player) src; 
                Vector3d velocity = null; 


                if (ctx.hasAny("hard")) 
                { 
                        velocity = new Vector3d(0, 4, 0); 
                } 
                else 
                { 
                        velocity = new Vector3d(0, 2, 0);  				} 


                player.offer(Keys.VELOCITY, velocity); 
                
                player.sendMessage(ChatTypes.CHAT,Text.of(TextColors.YELLOW, "Rocketed!"));  			
            } else {
               src.sendMessage(USAGE("/rocket <player>")); 
            }
        } 
        else if(targets.isPresent())
        { 
                String targ = targets.get(); 

                if(targ.equalsIgnoreCase("a") || targ.equalsIgnoreCase("@a")) 
                { 
                        for(Player player : Sponge.getServer().getOnlinePlayers()) 
                        { 
                                Vector3d velocity = null; 


                                if (ctx.hasAny("hard")) 
                                { 
                                        velocity = new Vector3d(0, 4, 0); 
                                } 
                                else 
                                { 
                                        velocity = new Vector3d(0, 2, 0); 
                                } 


                                player.offer(Keys.VELOCITY, velocity); 
                                player.sendMessage(ChatTypes.CHAT,Text.of(TextColors.YELLOW, "You've been rocketed by " + src.getName() + ".")); 
                        } 
                } 
                else 
                { 
                        src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Invalid argument supplied!")); 
                } 
        } 
        else if(target.isPresent())
        { 
                Player player = target.get(); 
                Vector3d velocity = null; 


                if (ctx.hasAny("hard")) 
                { 
                        velocity = new Vector3d(0, 4, 0); 
                } 
                else 
                { 
                        velocity = new Vector3d(0, 2, 0); 
                } 


                player.offer(Keys.VELOCITY, velocity); 
                player.sendMessage(ChatTypes.CHAT,Text.of(TextColors.YELLOW, "You've been rocketed by " + src.getName() + ".")); 
        } 
        else 
        { 
                src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "You do not have permission to rocket other players!")); 
        } 


        return CommandResult.success();  	
    } 
} 

    

