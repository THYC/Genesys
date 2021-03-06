package net.teraoctet.genesys.commands.plot;

import static org.spongepowered.api.Sponge.getGame;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.service.pagination.PaginationList.Builder;
import org.spongepowered.api.service.pagination.PaginationService;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;
import static net.teraoctet.genesys.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.genesys.utils.MessageManager.NO_PERMISSIONS;
import org.spongepowered.api.command.source.ConsoleSource;

public class CommandPlot implements CommandExecutor {
           
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {
        if(src instanceof Player && src.hasPermission("genesys.plot")) {
            PaginationService paginationService = getGame().getServiceManager().provide(PaginationService.class).get();
            Builder builder = paginationService.builder();  

            builder.title(Text.builder().append(TextSerializers.formattingCode('&').deserialize("&6Plot")).toText())
                .contents(Text.builder().append(TextSerializers.formattingCode('&').deserialize("&e/plot create &b<name> &b[strict] : &7cr\351ation d'une nouvelle parcelle")).toText(),
                    Text.builder().append(TextSerializers.formattingCode('&').deserialize("&e/plot list &b[player] : &7liste des parcelles poss\351d\351es par un joueur")).toText(),
                    Text.builder().append(TextSerializers.formattingCode('&').deserialize("&e/plot remove &b[NomParcelle] : &7supprime une parcelle")).toText(),
                    Text.builder().append(TextSerializers.formattingCode('&').deserialize("&e/plot flaglist &b<NomParcelle> : &7liste les diff\351rents flags")).toText(),
                    Text.builder().append(TextSerializers.formattingCode('&').deserialize("&e/plot flag &b<nameFlag]> <0|1> &b[NomParcelle] : &7modifie un flag d'une parcelle")).toText(),
                    Text.builder().append(TextSerializers.formattingCode('&').deserialize("&e/plot addplayer &b<player> &b[NomParcelle] : &7ajoute un habitant \340 la parcelle")).toText(),
                    Text.builder().append(TextSerializers.formattingCode('&').deserialize("&e/plot removeplayer &b<player> &b[NomParcelle] : &7retire un habitant de la parcelle")).toText(),
                    Text.builder().append(TextSerializers.formattingCode('&').deserialize("&e/plot ownerset &b<player> &b[NomParcelle] : &7change le propi\351taire de la parcelle")).toText(),
                    Text.builder().append(TextSerializers.formattingCode('&').deserialize("&e/plot sale &b<price> &b[NomParcelle] : &7place un panneau pour vendre ta parcelle")).toText(),
                    Text.builder().append(TextSerializers.formattingCode('&').deserialize("&e/plot msg &5[Message] : &7Lire / modifier le message d'accueil")).toText(),
                    Text.builder().append(TextSerializers.formattingCode('&').deserialize("&e/plot tp &b<name> : &7t\351l\351porte sur la parcelle")).toText())
                .header(Text.builder().append(TextSerializers.formattingCode('&').deserialize("&eUsage:")).toText())
                .padding(Text.of("-"))
                .sendTo(src); 
            
            return CommandResult.success();
        } 
        
        else if (src instanceof ConsoleSource) {
            src.sendMessage(NO_CONSOLE());
        }
        
        else {
            src.sendMessage(NO_PERMISSIONS());
        }
        
        return CommandResult.empty();
    }
}
