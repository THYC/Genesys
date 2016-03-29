package net.teraoctet.genesys.commands;

import static net.teraoctet.genesys.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.genesys.utils.MessageManager.NO_PERMISSIONS;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.NamedCause;

public class CommandInvsee implements CommandExecutor {
    
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException {
        if(src instanceof Player && src.hasPermission("genesys.invsee")) {
            Player target = ctx.<Player> getOne("target").get();
            Player player = (Player) src; 
            player.openInventory(target.getInventory(), Cause.of(NamedCause.source(src)));
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