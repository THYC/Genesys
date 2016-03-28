package net.teraoctet.genesys.commands;

import static net.teraoctet.genesys.utils.MessageManager.NO_CONSOLE;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.NamedCause;

public class CommandInvsee implements CommandExecutor {
    
    @Override
    public CommandResult execute(CommandSource sender, CommandContext ctx) throws CommandException {
        Player target = ctx.<Player> getOne("target").get();

	if (sender instanceof Player){
            Player player = (Player) sender;
            player.openInventory(target.getInventory(), Cause.of(NamedCause.source(sender)));
	} else { sender.sendMessage(NO_CONSOLE());}

	return CommandResult.success();
    }
}