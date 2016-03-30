package net.teraoctet.genesys.commands;

import static net.teraoctet.genesys.utils.MessageManager.MESSAGE;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource; 
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;

import static net.teraoctet.genesys.utils.MessageManager.NO_PERMISSIONS;
import static net.teraoctet.genesys.utils.MessageManager.USAGE;
import static org.spongepowered.api.Sponge.getGame;

public class CommandBroadcastmessage implements CommandExecutor {
        
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {
        if(ctx.hasAny("message") && src.hasPermission("genesys.broadcastmessage")){
            boolean hided = ctx.<Boolean> getOne("hide").get();
            String[] args = ctx.<String> getOne("message").get().split(" ");
            String prefix = "&4[ADMIN] ";
            String message = "";
            
            //ajoute le nom de la source si elle n'est pas masquée (hide = 0)
            if (hided == false) {
                prefix = prefix + src.getName();
            }
            prefix= prefix + " : ";
            
            for(int i = 0; i < args.length; i++){
                message = message + args[i] + " ";
            }    
            
            message = prefix + message;

            getGame().getServer().getBroadcastChannel().send(MESSAGE(message));
            return CommandResult.success();
        }

        else if (src.hasPermission("genesys.broadcastmessage")) {
            src.sendMessage(USAGE("/broadcast <hide = 0:1> <message..>"));
        }       
        
        //si on arrive jusqu'ici c'est que la source n'a pas les permissions pour cette commande ou que quelque chose s'est mal passé
        else {
            src.sendMessage(NO_PERMISSIONS());
        }
                
        return CommandResult.empty();
    }
}
