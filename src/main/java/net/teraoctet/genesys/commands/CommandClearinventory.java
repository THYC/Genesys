package net.teraoctet.genesys.commands;

import java.util.Optional;
import static net.teraoctet.genesys.utils.MessageManager.NO_PERMISSIONS;
import static net.teraoctet.genesys.utils.MessageManager.USAGE;
import static net.teraoctet.genesys.utils.MessageManager.INVENTORY_CLEARED;
import static net.teraoctet.genesys.utils.MessageManager.CLEARINVENTORY_SUCCESS;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
    
public class CommandClearinventory implements CommandExecutor {
        
    @Override
    public CommandResult execute(CommandSource sender, CommandContext ctx) throws CommandException {
        Optional<Player> player = ctx.<Player> getOne("player");
        
        if(!sender.hasPermission("genesys.clearinventory")) { 
            sender.sendMessage(NO_PERMISSIONS()); 
            return CommandResult.success(); 
        }
        
        if (player.isPresent()) { 
            if(!sender.hasPermission("genesys.clearinventory.others")) { 
                sender.sendMessage(NO_PERMISSIONS()); 
                return CommandResult.success(); 
            }
            player.get().getInventory().clear(); 
            player.get().sendMessage(INVENTORY_CLEARED());
            sender.sendMessage(CLEARINVENTORY_SUCCESS(player.get().getName()));
        } else {
            if(sender instanceof Player == false) {
                sender.sendMessage(USAGE("/clearinventory <player>"));
                return CommandResult.success(); 
            }
            Player senderPlayer = (Player)sender;
            senderPlayer.getInventory().clear();
            senderPlayer.sendMessage(INVENTORY_CLEARED());
        }
        
        return CommandResult.success();
    }
}
