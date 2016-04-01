package net.teraoctet.genesys.commands;

import net.teraoctet.genesys.faction.FactionManager;
import net.teraoctet.genesys.faction.GFaction;
import net.teraoctet.genesys.player.GPlayer;
import net.teraoctet.genesys.utils.GData;
import static net.teraoctet.genesys.utils.GData.getGPlayer;
import static net.teraoctet.genesys.utils.MessageManager.ALREADY_FACTION_MEMBER;
import static net.teraoctet.genesys.utils.MessageManager.FACTION_CREATED_SUCCESS;
import static net.teraoctet.genesys.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.genesys.utils.MessageManager.NO_PERMISSIONS;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;

public class CommandFactionCreate implements CommandExecutor {
        
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {

        if(src instanceof Player && src.hasPermission("genesys.faction.create")) {
            Player player = (Player) src;
            GPlayer gplayer = getGPlayer(player.getIdentifier());
            
            if(FactionManager.hasAnyFaction(gplayer)) {
                src.sendMessage(ALREADY_FACTION_MEMBER());
            } else {
                String factionName = ctx.<String> getOne("name").get();
                GFaction gfaction = new GFaction(factionName);
                
                //###############################################################
                // important ici il faut aussi faire une modif dans GPlayer 
                // pour dire qui est le owner de la faction !
                // et après il faudra mettre un controle sur chaque commande de faction specifique au owner
                
                // je dit que gplayer est l'admin de la faction
                // par contre du coup ca ne va pas ici l'utilisation de l'ID
                //car on ne connaitra l'ID que quand il y aura eu un commit de Faction et un reload
                // je te laisse modifier, il faut soit changer finalement sur le nom :
                // soit dans gplayer mettre un chant : factionname
                // ou soit creer soit même l'ID par du code
                gplayer.setID_faction(0); 
                gplayer.setFactionRank(1);
                
                // je met à jour par update
                gplayer.update();
                
                // le commit valide la modif des 2 tables
                gfaction.insert();
                GData.commit();
                GData.addGFaction(factionName, gfaction); 
                src.sendMessage(FACTION_CREATED_SUCCESS(factionName));
                gplayer.setID_faction(gfaction.getID());
                return CommandResult.success();
            }
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
