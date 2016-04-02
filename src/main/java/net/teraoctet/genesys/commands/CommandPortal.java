package net.teraoctet.genesys.commands;

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

public class CommandPortal implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {

        if(src instanceof Player && src.hasPermission("genesys.admin.portal")) { 
            PaginationService paginationService = getGame().getServiceManager().provide(PaginationService.class).get();
            Builder builder = paginationService.builder();

            builder.title(Text.builder().append(TextSerializers.formattingCode('&').deserialize("&6Portal")).toText())
            .contents(Text.builder().append(TextSerializers.formattingCode('&').deserialize("&6/portal create <name> : &7cr\351ation d'un portail au point d\351clar\351")).toText(),
                Text.builder().append(TextSerializers.formattingCode('&').deserialize("&6/portal remove <name> : &7supprime le portail")).toText(),
                Text.builder().append(TextSerializers.formattingCode('&').deserialize("&6/portal tpfrom  <name> : &7enregistre le point d'arriv\351 du portail")).toText(),
                Text.builder().append(TextSerializers.formattingCode('&').deserialize("&6/portal list : &7liste les portails")).toText(),
                Text.builder().append(TextSerializers.formattingCode('&').deserialize("&6/portal message <name> : &7affiche le message d'arriv\351e du portail")).toText(),
                Text.builder().append(TextSerializers.formattingCode('&').deserialize("&6/portal message <name> <message> : &7modifie le message d'arriv\351e du portail")).toText())
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