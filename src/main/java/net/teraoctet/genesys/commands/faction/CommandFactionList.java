package net.teraoctet.genesys.commands.faction;

import static net.teraoctet.genesys.Genesys.factionManager;
import net.teraoctet.genesys.player.GPlayer;
import static net.teraoctet.genesys.utils.GConfig.FACTION_MAX_NUMBER_OF_MEMBER;
import static net.teraoctet.genesys.utils.GData.getFactions;
import static net.teraoctet.genesys.utils.GData.getGPlayer;
import static net.teraoctet.genesys.utils.MessageManager.MESSAGE;
import static net.teraoctet.genesys.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.genesys.utils.MessageManager.NO_PERMISSIONS;
import static net.teraoctet.genesys.utils.MessageManager.ONHOVER_FACTION_LIST_LVL10;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;

public class CommandFactionList implements CommandExecutor {
        
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {

        if(src instanceof Player && src.hasPermission("genesys.faction.list")) {
            src.sendMessage(MESSAGE("&6&lListe des factions :"));
            
            for (Integer id_faction : getFactions().keySet()) {
                String factionName = getFactions().get(id_faction).getName();
                String ownerName = factionManager.getOwner(id_faction).getName();
                int factionSize = factionManager.getFactionPlayers(id_faction).size();
                GPlayer gplayer = getGPlayer(src.getIdentifier());
                int level = gplayer.getLevel();
                
                if(level == 10) {
                    src.sendMessage(Text.builder().append(MESSAGE("&e- ID: " + id_faction + " &r" + factionName + "&r&e (" + factionSize + "/" + FACTION_MAX_NUMBER_OF_MEMBER() + ")"))
                            .onShiftClick(TextActions.insertText("/faction delete " + factionName))
                            .onHover(TextActions.showText(ONHOVER_FACTION_LIST_LVL10(factionName, ownerName))).toText());
                } else {
                    src.sendMessage(Text.builder().append(MESSAGE("&e- &r" + factionName + "&r&e (" + factionSize + "/" + FACTION_MAX_NUMBER_OF_MEMBER() + ")"))
                            .onHover(TextActions.showText(MESSAGE("Chef : " + ownerName))).toText());
                }
            }
            
            return CommandResult.success();
        } 
        
        else if (src instanceof ConsoleSource) {
            src.sendMessage(NO_CONSOLE());
        }
        
        //si on arrive jusqu'ici c'est que la source n'a pas les permissions pour cette commande ou que quelque chose s'est mal passé
        else {
            src.sendMessage(NO_PERMISSIONS());
        }
                
        return CommandResult.empty();
    }
}
