package net.teraoctet.genesys.commands;

import static net.teraoctet.genesys.Genesys.factionManager;
import net.teraoctet.genesys.faction.GFaction;
import net.teraoctet.genesys.player.GPlayer;
import static net.teraoctet.genesys.utils.GConfig.FACTION_MAX_NUMBER_OF_MEMBER;
import static net.teraoctet.genesys.utils.GData.getGFaction;
import static net.teraoctet.genesys.utils.GData.getGPlayer;
import static net.teraoctet.genesys.utils.MessageManager.GUIDE_FACTION;
import static net.teraoctet.genesys.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.genesys.utils.MessageManager.NO_FACTION;
import static net.teraoctet.genesys.utils.MessageManager.NO_PERMISSIONS;
import static net.teraoctet.genesys.utils.MessageManager.ONHOVER_ACTION_MENU;
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
                        builder.header(Text.builder().append(TextSerializers.formattingCode('&').deserialize("&2Actions:")).toText())
                            .contents(Text.builder().append(TextSerializers.formattingCode('&').deserialize("&2+ &aAjouter un membre"))
                                    .onClick(TextActions.suggestCommand("/faction addplayer "))    
                                    .onHover(TextActions.showText(TextSerializers.formattingCode('&')
                                            .deserialize("&l&6Inviter un joueur \342 rejoindre la faction\n&r&e&nUtilisable par :&r Chef, Sous-chef, Officier\n/faction addplayer <player>")
                                            .toText()))
                                    .toText(),
                                Text.builder().append(TextSerializers.formattingCode('&').deserialize("&2+ &aChanger le grade d'un membre"))
                                    .onClick(TextActions.suggestCommand("/faction setplayergrade "))    
                                    .onHover(TextActions.showText(TextSerializers.formattingCode('&')
                                            .deserialize("&l&6Changer le grade d'un membre\n&r&e&nUtilisable par :&r Chef, Sous-chef, Officier\n/faction setplayergrade <player> <grade>\n&o&n&7Grade :&r&o&7 2 -> Sous-chef | 3 -> Officer | 4 -> Membre | 5 -> Recrue")
                                            .toText()))
                                    .toText(),
                                Text.builder().append(TextSerializers.formattingCode('&').deserialize("&2+ &aSupprimer un membre"))
                                    .onClick(TextActions.suggestCommand("/faction removeplayer "))    
                                    .onHover(TextActions.showText(TextSerializers.formattingCode('&')
                                            .deserialize("&l&6Supprimer un membre\n&r&e&nUtilisable par :&r Chef, Sous-chef\n/faction removeplayer <player>")
                                            .toText()))
                                    .toText(),
                                Text.builder().append(TextSerializers.formattingCode('&').deserialize("&2+ &aRetrait bancaire"))
                                    .onClick(TextActions.suggestCommand("/faction retrait "))    
                                    .onHover(TextActions.showText(TextSerializers.formattingCode('&')
                                            .deserialize("&l&6Retirer des \351meraudes de la banque de faction\n&r&e&nUtilisable par :&r Chef, Sous-chef\n/faction retrait <montant>")
                                            .toText()))
                                    .toText(),
                                Text.builder().append(TextSerializers.formattingCode('&').deserialize("&2+ &aRenommer la faction"))
                                    .onClick(TextActions.suggestCommand("/faction rename "))    
                                    .onHover(TextActions.showText(TextSerializers.formattingCode('&')
                                            .deserialize("&l&6Renommer la faction \n&r&e&nUtilisable par :&r Chef, Sous-chef\n/faction rename <nom>")
                                            .toText()))
                                    .toText(),
                                Text.builder().append(TextSerializers.formattingCode('&').deserialize("&2+ &aSupprimer la faction"))
                                    .onClick(TextActions.suggestCommand("/faction delete "))    
                                    .onHover(TextActions.showText(TextSerializers.formattingCode('&')
                                            .deserialize("&l&6Supprimer la faction\n&r&e&nUtilisable par :&r Chef\n/faction delete <nom>")
                                            .toText()))
                                    .toText())
                                .padding(Text.of("-"))
                                .sendTo(src);
                        return CommandResult.success();
                    } else {
                        src.sendMessage(WRONG_RANK());
                    }
                //Menu affiché par défaut   
                } else {
                    builder.title(Text.builder().append(TextSerializers.formattingCode('&').deserialize("&2Faction : &f" + gfaction.getName())).toText())
                         .header(Text.builder().append(TextSerializers.formattingCode('&')
                                 .deserialize("&2Membres: ")) //+ factionManager.getListPlayerFaction(gfaction.getID()).size() + " / " + FACTION_MAX_NUMBER_OF_MEMBER()))
                                 .build())
                         .contents(Text.builder().append(TextSerializers.formattingCode('&').deserialize("&aChef :")).toText(),
                             Text.builder().append(TextSerializers.formattingCode('&').deserialize("&a- Sous-chef (" + ")"))
                                     .onClick(TextActions.runCommand("/faction memberslist"))
                                     .onHover(TextActions.showText(Text.builder("Sous-chef(s): ").build()))      //mettre la liste des SOUS-CHEFS en hover
                                     .toText(),
                             Text.builder().append(TextSerializers.formattingCode('&').deserialize("&a- Officier (" + ")"))
                                     .onClick(TextActions.runCommand("/faction memberslist"))
                                     .onHover(TextActions.showText(Text.builder("Officier(s): ").build()))      //mettre la liste des OFFICIERS en hover
                                     .toText(),
                             Text.builder().append(TextSerializers.formattingCode('&').deserialize("&a- Membre (" + ")"))
                                     .onClick(TextActions.runCommand("/faction memberslist"))
                                     .onHover(TextActions.showText(Text.builder("Membre(s): ").build()))      //mettre la liste des MEMBRES en hover
                                     .toText(),
                             Text.builder().append(TextSerializers.formattingCode('&').deserialize("&a- Recrue (" + ")"))
                                     .onClick(TextActions.runCommand("/faction memberslist"))
                                     .onHover(TextActions.showText(Text.builder("Recrue(s): ").build()))      //mettre la liste des RECRUES en hover
                                     .toText(),
                             Text.builder().append(TextSerializers.formattingCode('&').deserialize("&2Bank de Faction : &a" + gfaction.getMoney() + " \351meraudes"))
                                     .onClick(TextActions.suggestCommand("/faction depot "))
                                     .onHover(TextActions.showText(TextSerializers.formattingCode('&').deserialize("&l&6D\351poser des \351meraudes dans la banque de faction\n&f/faction depot <montant>").toText()))
                                     .toText(),
                             Text.builder().append(TextSerializers.formattingCode('&').deserialize("&2+ Afficher les Actions"))
                                     .onClick(TextActions.runCommand("/faction -a"))
                                     .onHover(TextActions.showText(ONHOVER_ACTION_MENU()))
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
                src.sendMessage(Text.builder().append(TextSerializers.formattingCode('&').deserialize("&bCliquez ici pour en cr\351er une !"))
                        .onClick(TextActions.suggestCommand("/faction create "))    
                        .onHover(TextActions.showText(TextSerializers.formattingCode('&').deserialize("&eCr\351er une nouvelle faction\n&f/faction create <name>").toText()))
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

