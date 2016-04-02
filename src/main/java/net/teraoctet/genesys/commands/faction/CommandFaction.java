package net.teraoctet.genesys.commands.faction;

import static net.teraoctet.genesys.Genesys.factionManager;
import net.teraoctet.genesys.faction.GFaction;
import net.teraoctet.genesys.player.GPlayer;
import static net.teraoctet.genesys.utils.GConfig.FACTION_MAX_NUMBER_OF_MEMBER;
import static net.teraoctet.genesys.utils.GData.getGFaction;
import static net.teraoctet.genesys.utils.GData.getGPlayer;
import static net.teraoctet.genesys.utils.MessageManager.GUIDE_FACTION;
import static net.teraoctet.genesys.utils.MessageManager.MESSAGE;
import static net.teraoctet.genesys.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.genesys.utils.MessageManager.NO_FACTION;
import static net.teraoctet.genesys.utils.MessageManager.NO_PERMISSIONS;
import static net.teraoctet.genesys.utils.MessageManager.ONHOVER_FACTION_DELETE;
import static net.teraoctet.genesys.utils.MessageManager.ONHOVER_FACTION_DEPOSIT;
import static net.teraoctet.genesys.utils.MessageManager.ONHOVER_FACTION_INVIT;
import static net.teraoctet.genesys.utils.MessageManager.ONHOVER_FACTION_MOREACTIONS;
import static net.teraoctet.genesys.utils.MessageManager.ONHOVER_FACTION_REMOVEMEMBER;
import static net.teraoctet.genesys.utils.MessageManager.ONHOVER_FACTION_RENAME;
import static net.teraoctet.genesys.utils.MessageManager.ONHOVER_FACTION_SETGRADE;
import static net.teraoctet.genesys.utils.MessageManager.ONHOVER_FACTION_WITHDRAWAL;
import static net.teraoctet.genesys.utils.MessageManager.WRONG_RANK;
import static org.spongepowered.api.Sponge.getGame;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.service.pagination.PaginationList;
import org.spongepowered.api.service.pagination.PaginationService;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.serializer.TextSerializers;

public class CommandFaction implements CommandExecutor {
        
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {
        if(src instanceof Player && src.hasPermission("genesys.faction")) {
            GPlayer gplayer = getGPlayer(src.getIdentifier());
            
            //si le joueur est membre d'une faction
            if(factionManager.hasAnyFaction(gplayer)) {
                GFaction gfaction = getGFaction(gplayer.getID_faction());
                PaginationService paginationService = getGame().getServiceManager().provide(PaginationService.class).get();
                PaginationList.Builder builder = paginationService.builder();  

                //Menu des actions, affiché lorsque showActionsMenu
                if(ctx.hasAny("displayaction")){
                    //Si le joueur a un grade suffisant dans la faction pour accéder à ce menu
                    if(gplayer.getFactionRank() <= 3) {
                        builder.header(Text.builder().append(MESSAGE("&2Actions:")).toText())
                                .contents(Text.builder().append(MESSAGE("&2+ &aAjouter un membre"))
                                        .onClick(TextActions.suggestCommand("/faction invit "))    
                                        .onHover(TextActions.showText(ONHOVER_FACTION_INVIT()))
                                        .toText(),
                                    Text.builder().append(MESSAGE("&2+ &aChanger le grade d'un membre"))
                                        .onClick(TextActions.suggestCommand("/faction setplayergrade "))    
                                        .onHover(TextActions.showText(ONHOVER_FACTION_SETGRADE()))
                                        .toText(),
                                    Text.builder().append(MESSAGE("&2+ &aSupprimer un membre"))
                                        .onClick(TextActions.suggestCommand("/faction removeplayer "))    
                                        .onHover(TextActions.showText(ONHOVER_FACTION_REMOVEMEMBER()))
                                        .toText(),
                                    Text.builder().append(MESSAGE("&2+ &aRetrait bancaire"))
                                        .onClick(TextActions.suggestCommand("/faction withdraw "))    
                                        .onHover(TextActions.showText(ONHOVER_FACTION_WITHDRAWAL()))
                                        .toText(),
                                    Text.builder().append(TextSerializers.formattingCode('&').deserialize("&2+ &aRenommer la faction"))
                                        .onClick(TextActions.suggestCommand("/faction rename "))    
                                        .onHover(TextActions.showText(ONHOVER_FACTION_RENAME()))
                                        .toText(),
                                    Text.builder().append(MESSAGE("&2+ &aSupprimer la faction"))
                                        .onClick(TextActions.suggestCommand("/faction delete "))    
                                        .onHover(TextActions.showText(ONHOVER_FACTION_DELETE()))
                                        .toText())
                                .padding(Text.of("-"))
                                .sendTo(src);
                        return CommandResult.success();
                    } else {
                        src.sendMessage(WRONG_RANK());
                    }
                //Menu affiché par défaut   
                } else {
                    builder.title(Text.builder().append(MESSAGE("&2Faction : &f" + gfaction.getName())).toText())
                            .header(Text.builder().append(MESSAGE("&2Membres: ")).build()) //+ factionManager.getListPlayerFaction(gfaction.getID()).size() + " / " + FACTION_MAX_NUMBER_OF_MEMBER()))
                            .contents(Text.builder().append(MESSAGE("&aChef :")).toText(),
                                    Text.builder().append(MESSAGE("&a- Sous-chef (" + ")"))
                                            .onClick(TextActions.runCommand("/faction memberslist"))
                                            .onHover(TextActions.showText(MESSAGE("Sous-chef(s): ")))      //mettre la liste des SOUS-CHEFS en hover
                                            .toText(),
                                    Text.builder().append(MESSAGE("&a- Officier (" + ")"))
                                            .onClick(TextActions.runCommand("/faction memberslist"))
                                            .onHover(TextActions.showText(MESSAGE("Officier(s): ")))      //mettre la liste des OFFICIERS en hover
                                            .toText(),
                                    Text.builder().append(MESSAGE("&a- Membre (" + ")"))
                                            .onClick(TextActions.runCommand("/faction memberslist"))
                                            .onHover(TextActions.showText(MESSAGE("Membre(s): ")))      //mettre la liste des MEMBRES en hover
                                            .toText(),
                                    Text.builder().append(MESSAGE("&a- Recrue (" + ")"))
                                            .onClick(TextActions.runCommand("/faction memberslist"))
                                            .onHover(TextActions.showText(MESSAGE("Recrue(s): ")))      //mettre la liste des RECRUES en hover
                                            .toText(),
                                    Text.builder().append(MESSAGE("&2Bank de Faction : &a" + gfaction.getMoney() + " \351meraudes"))
                                            .onClick(TextActions.suggestCommand("/faction depot "))
                                            .onHover(TextActions.showText(ONHOVER_FACTION_DEPOSIT()))
                                            .toText(),
                                    Text.builder().append(MESSAGE("&2+ Afficher les Actions"))
                                            .onClick(TextActions.runCommand("/faction -a"))
                                            .onHover(TextActions.showText(ONHOVER_FACTION_MOREACTIONS()))
                                            .toText())
                            .padding(Text.of("-"))
                            .sendTo(src); 
                    return CommandResult.success();
                }
            }
            
            //si le joueur n'est dans aucune faction
            else {
                src.sendMessage(NO_FACTION());
                src.sendMessage(GUIDE_FACTION());
                src.sendMessage(Text.builder().append(MESSAGE("&bCliquez ici pour en cr\351er une !"))
                        .onClick(TextActions.suggestCommand("/faction create "))    
                        .onHover(TextActions.showText(MESSAGE("&eCr\351er une nouvelle faction\n&f/faction create <name>")))
                        .toText()); 
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

