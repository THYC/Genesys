package net.teraoctet.genesys.commands;

import java.util.Optional;
import static net.teraoctet.genesys.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.genesys.utils.MessageManager.NO_PERMISSIONS;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.mutable.entity.FlyingData;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.chat.ChatTypes;
import org.spongepowered.api.text.format.TextColors;

public class CommandFly implements CommandExecutor {
    
    @Override
    public CommandResult execute(CommandSource sender, CommandContext ctx) throws CommandException {

        Player player = (Player) sender;    
        if(!player.hasPermission("genesys.fly")) { 
                sender.sendMessage(NO_PERMISSIONS()); 
                return CommandResult.success(); 
        }
                    
        if(sender instanceof Player == false) { 
            sender.sendMessage(NO_CONSOLE()); 
            return CommandResult.success(); 
        }
                
        Optional<FlyingData> optFlying = player.get(FlyingData.class);
        boolean isFlying = optFlying.isPresent() && optFlying.get().flying().get();
        if(isFlying == true) 
        {           
            player.offer(Keys.IS_FLYING, false); 
            player.offer(Keys.CAN_FLY, false); 

            player.sendMessage(ChatTypes.CHAT,Text.builder("Fly OFF").color(TextColors.YELLOW).build());
        } else {
            player.offer(Keys.CAN_FLY,true);
            player.sendMessage(ChatTypes.CHAT,Text.builder("Fly ON").color(TextColors.YELLOW).build());
        } 
        return CommandResult.success();	
    }
}
