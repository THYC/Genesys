package net.teraoctet.genesys.commands;

import net.teraoctet.genesys.faction.GFaction;
import net.teraoctet.genesys.player.GPlayer;
import static net.teraoctet.genesys.utils.GData.getGFaction;
import static net.teraoctet.genesys.utils.GData.getGPlayer;
import static net.teraoctet.genesys.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.genesys.utils.MessageManager.NO_FACTION;
import static net.teraoctet.genesys.utils.MessageManager.NO_PERMISSIONS;
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
            Player player = (Player) src;
            GPlayer gplayer = getGPlayer(player.getIdentifier());
            
            //si le joueur n'est dans aucune faction
            if(gplayer.getID_faction() == 0) {
                src.sendMessage(NO_FACTION());
                src.sendMessage(Text.builder().append(TextSerializers.formattingCode('&').deserialize("&bCliquez ici pour en cr\351er une !"))
                        .onClick(TextActions.suggestCommand("/faction create "))    
                        .onHover(TextActions.showText(TextSerializers.formattingCode('&').deserialize("&eCr\351er une nouvelle faction\n&f/faction create <name>").toText()))
                        .toText());
            }
            
            //si le joueur est membre d'une faction
            else {
                GFaction gfaction = getGFaction(gplayer.getID_faction());
                PaginationService paginationService = getGame().getServiceManager().provide(PaginationService.class).get();
                PaginationList.Builder builder = paginationService.builder();  
                
                //Menu des actions, affiché lorsque showActionsMenu
                if(ctx.hasAny("actionmenu")){
                    //Si le joueur a un grade suffisant dans la faction pour accéder à ce menu
                    if(gplayer.getFactionRank() >= 3) {
                        builder.header(Text.builder().append(TextSerializers.formattingCode('&').deserialize("&2Actions:")).toText())
                            .contents(Text.builder().append(TextSerializers.formattingCode('&').deserialize("&2+ &aAjouter un membre"))
                                    .onClick(TextActions.suggestCommand("/faction addplayer "))    
                                    .onHover(TextActions.showText(TextSerializers.formattingCode('&').deserialize("&eAjoute un nouveau membre\n&f/faction addplayer <player>").toText()))
                                    .toText(),
                                Text.builder().append(TextSerializers.formattingCode('&').deserialize("&2+ &aChanger le grade d'un membre"))
                                    .onClick(TextActions.suggestCommand("/faction setplayergrade "))    
                                    .onHover(TextActions.showText(TextSerializers.formattingCode('&').deserialize("&eChanger le grade d'un membre\n&f/faction setplayergrade <player> <grade>").toText()))
                                    .toText(),
                                Text.builder().append(TextSerializers.formattingCode('&').deserialize("&2+ &aSupprimer un membre"))
                                    .onClick(TextActions.suggestCommand("/faction removeplayer "))    
                                    .onHover(TextActions.showText(TextSerializers.formattingCode('&').deserialize("&eSupprimer un membre\n&f/faction removeplayer <player>").toText()))
                                    .toText(),
                                Text.builder().append(TextSerializers.formattingCode('&').deserialize("&2+ &aSupprimer la faction"))
                                    .onClick(TextActions.suggestCommand("/faction delete "))    
                                    .onHover(TextActions.showText(TextSerializers.formattingCode('&').deserialize("&eSupprimer la faction\n&f/faction delete <nom>").toText()))
                                    .toText())
                                .padding(Text.of("-"))
                                .sendTo(src);
                        return CommandResult.success();
                    } else {
                        src.sendMessage(WRONG_RANK());
                    }
                //Menu affiché par défaut   
                } else {
                    builder.title(Text.builder().append(TextSerializers.formattingCode('&').deserialize(gfaction.getName())).toText())
                         .header(Text.builder().append(TextSerializers.formattingCode('&').deserialize("&2Membres: " + "x" + " / " + "x")).build())
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
                                     .onClick(TextActions.runCommand("/faction 1"))
                                     .onHover(TextActions.showText(Text.builder("Recrue(s): ").build()))      //mettre la liste des RECRUES en hover
                                     .toText(),
                             Text.builder().append(TextSerializers.formattingCode('&').deserialize("&2Bank de Faction : &a" + gfaction.getMoney() + " \351meraudes")).toText(),
                             Text.builder().append(TextSerializers.formattingCode('&').deserialize("&2+ Afficher les Actions"))
                                     .onClick(TextActions.runCommand("/faction -a"))
                                     .onHover(TextActions.showText(Text.builder("Affiche un menu pour g\351rer la faction").build()))      //mettre la liste des RECRUES en hover
                                     .toText())
                         .padding(Text.of("-"))
                         .sendTo(src); 
                    return CommandResult.success();
                }
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

