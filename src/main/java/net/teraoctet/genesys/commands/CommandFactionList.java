package net.teraoctet.genesys.commands;

import java.util.ArrayList;
import net.teraoctet.genesys.faction.GFaction;
import static net.teraoctet.genesys.utils.MessageManager.MESSAGE;
import static net.teraoctet.genesys.utils.MessageManager.NO_PERMISSIONS;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;

public class CommandFactionList implements CommandExecutor {
        
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {

        if(src.hasPermission("genesys.faction.list")) {
            ArrayList<GFaction> factions = new ArrayList<>();
            src.sendMessage(MESSAGE("&eFactions : &f" + factions.toString()));
            return CommandResult.success();
        } 
        
        //si on arrive jusqu'ici c'est que la source n'a pas les permissions pour cette commande ou que quelque chose s'est mal passé
        else {
            src.sendMessage(NO_PERMISSIONS());
        }
                
        return CommandResult.empty();
    }
}
