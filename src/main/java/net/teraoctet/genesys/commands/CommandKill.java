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
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.chat.ChatTypes;
import org.spongepowered.api.text.format.TextColors;

public class CommandKill implements CommandExecutor {
    
    @Override
    public CommandResult execute(CommandSource sender, CommandContext ctx) throws CommandException {
        Optional<Player> tplayer = ctx.<Player> getOne("player");
            
        if(!sender.hasPermission("genesys.kills")) { 
            sender.sendMessage(NO_PERMISSIONS()); 
            return CommandResult.success(); 
        }

        if (tplayer.isPresent()) {
            if(!sender.hasPermission("genesys.kills.others")) {
                sender.sendMessage(Text.builder("Tu n'as pas la permission d'utiliser cette commande sur d'autres joueurs!").color(TextColors.RED).build());
            } else {
                tplayer.get().offer(Keys.HEALTH, 0d);
                sender.sendMessage(Text.of(TextColors.YELLOW, tplayer.get().getName(), TextColors.GRAY, " a été tué."));
                tplayer.get().sendMessage(ChatTypes.CHAT,Text.of(TextColors.GRAY, "Tu as été tué par ", TextColors.YELLOW, sender.getName()));
            }
            return CommandResult.success();
        } else {
            if(sender instanceof Player == false) { 
                sender.sendMessage(NO_CONSOLE()); 
            } else {
                Player player = (Player) sender;
                player.offer(Keys.HEALTH, 0d);
                sender.sendMessage(Text.of(TextColors.YELLOW, "Tu ", TextColors.GRAY, "es mort."));
            }
        }
        return CommandResult.success();	 
    }
}
