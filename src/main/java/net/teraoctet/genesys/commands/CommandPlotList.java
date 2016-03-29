package net.teraoctet.genesys.commands;

import static net.teraoctet.genesys.Genesys.plotManager;
import static net.teraoctet.genesys.utils.GServer.getPlayer;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.chat.ChatTypes;
import static net.teraoctet.genesys.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.genesys.utils.MessageManager.NO_PERMISSIONS;
import static net.teraoctet.genesys.utils.MessageManager.MESSAGE;

public class CommandPlotList implements CommandExecutor {
    
    @Override
    public CommandResult execute(CommandSource sender, CommandContext ctx) throws CommandException {
               
        if(sender instanceof Player == false) { 
            sender.sendMessage(NO_CONSOLE()); 
            return CommandResult.success(); 
        }
        
        Player player = (Player) sender;
        
        if(!player.hasPermission("genesys.plot.list")) { 
                sender.sendMessage(NO_PERMISSIONS()); 
                return CommandResult.success(); 
        }
        
        String targetUUID = player.getUniqueId().toString();
        String targetName = player.getName();
        
        if(ctx.getOne("target").isPresent() && player.hasPermission("genesys.plot.otherlist")){
            targetName = ctx.<String> getOne("target").get();
            targetUUID = getPlayer(targetName).getIdentifier();
        } else {
            sender.sendMessage(NO_PERMISSIONS()); 
            return CommandResult.success(); 
        }
                        
        Text listPlot = plotManager.getListPlot(targetUUID);
        player.sendMessage(ChatTypes.CHAT,MESSAGE("&bListe des parcelles appartenant \340 &7" + targetName));
        player.sendMessage(ChatTypes.CHAT,listPlot);
        
        return CommandResult.success();	
    }
}