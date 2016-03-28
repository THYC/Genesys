package net.teraoctet.genesys.commands;

import java.util.Optional;
import static net.teraoctet.genesys.utils.MessageManager.FLY_DISABLED;
import static net.teraoctet.genesys.utils.MessageManager.FLY_ENABLED;
import static net.teraoctet.genesys.utils.MessageManager.FLY_GIVEN;
import static net.teraoctet.genesys.utils.MessageManager.FLY_RETIRED;
import static net.teraoctet.genesys.utils.MessageManager.NO_PERMISSIONS;
import static net.teraoctet.genesys.utils.MessageManager.USAGE;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.mutable.entity.FlyingData;
import org.spongepowered.api.entity.living.player.Player;

public class CommandFly implements CommandExecutor {
    
    @Override
    public CommandResult execute(CommandSource sender, CommandContext ctx) throws CommandException {
         Optional<Player> tplayer = ctx.<Player> getOne("player");
           
        if(!sender.hasPermission("genesys.fly")) { 
                sender.sendMessage(NO_PERMISSIONS()); 
                return CommandResult.success(); 
        }
        
        if (tplayer.isPresent()) {
            if(!sender.hasPermission("genesys.fly.others")) { 
                sender.sendMessage(NO_PERMISSIONS()); 
                return CommandResult.success(); 
            }
            
            Optional<FlyingData> optFlying = tplayer.get().get(FlyingData.class);
            boolean isFlying = optFlying.isPresent() && optFlying.get().flying().get();
            if(isFlying == true) 
            {           
                tplayer.get().offer(Keys.IS_FLYING, false); 
                tplayer.get().offer(Keys.CAN_FLY, false); 
                tplayer.get().sendMessage(FLY_DISABLED());
                sender.sendMessage(FLY_RETIRED(tplayer.get().getName()));
            } else {
                tplayer.get().offer(Keys.CAN_FLY,true);
                tplayer.get().sendMessage(FLY_ENABLED());
                sender.sendMessage(FLY_GIVEN(tplayer.get().getName()));
            }     
        } else {
            if(sender instanceof Player == false) { 
                sender.sendMessage(USAGE("/fly <player>")); 
                return CommandResult.success(); 
            }
            
            Player player = (Player) sender;
            Optional<FlyingData> optFlying = player.get(FlyingData.class);
            boolean isFlying = optFlying.isPresent() && optFlying.get().flying().get();
            if(isFlying == true) 
            {           
                player.offer(Keys.IS_FLYING, false); 
                player.offer(Keys.CAN_FLY, false); 
                player.sendMessage(FLY_DISABLED());
            } else {
                player.offer(Keys.CAN_FLY,true);
                player.sendMessage(FLY_ENABLED());
            }
        }
        return CommandResult.success();	
    }
}
