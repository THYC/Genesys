package net.teraoctet.genesys.commands;

import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource; 
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;

import static net.teraoctet.genesys.utils.MessageManager.USAGE;
import static org.spongepowered.api.Sponge.getGame;
import static net.teraoctet.genesys.utils.MessageManager.MESSAGE;
import static net.teraoctet.genesys.utils.MessageManager.NO_PERMISSIONS;

public class CommandBroadcast implements CommandExecutor {
        
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {
        if(ctx.hasAny("message") && src.hasPermission("genesys.broadcast")){
            String prefix = "&4[ADMIN]&c";
            String message = ctx.<String> getOne("message").get();
            
            //ajoute le nom de la source si flag -h n'est pas présent
            if (!ctx.hasAny("hide")) {
                prefix = prefix + src.getName();
            }
            
            prefix= prefix + " : ";
            message = prefix + message;
            getGame().getServer().getBroadcastChannel().send(MESSAGE(message));
            return CommandResult.success();
        }

        else if (src.hasPermission("genesys.broadcast")) {
            src.sendMessage(USAGE("/broadcast [-h] <message..>"));
        }       
        
        //si on arrive jusqu'ici c'est que la source n'a pas les permissions pour cette commande ou que quelque chose s'est mal passé
        else {
            src.sendMessage(NO_PERMISSIONS());
        }
                
        return CommandResult.empty();
    }
}
