package net.teraoctet.genesys.commands;

import static net.teraoctet.genesys.utils.GData.getGPlayer;
import static net.teraoctet.genesys.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.genesys.utils.MessageManager.NO_PERMISSIONS;
import net.teraoctet.genesys.player.GPlayer;
import static org.spongepowered.api.Sponge.getGame;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.service.pagination.PaginationList.Builder;
import org.spongepowered.api.service.pagination.PaginationService;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;

public class CommandParcel implements CommandExecutor {
           
    @Override
    public CommandResult execute(CommandSource sender, CommandContext ctx) throws CommandException {
        
        if(ctx.getOne("arg").isPresent()){
            return CommandResult.success(); 
        }
        
        if(sender instanceof Player == false) { 
            sender.sendMessage(NO_CONSOLE()); 
            return CommandResult.success(); 
        }
        
        Player player = (Player) sender;

        if(!player.hasPermission("genesys.parcel")) { 
            sender.sendMessage(NO_PERMISSIONS()); 
            return CommandResult.success(); 
        }
                       
        PaginationService paginationService = getGame().getServiceManager().provide(PaginationService.class).get();
        Builder builder = paginationService.builder();  

        builder.title(Text.builder().append(TextSerializers.formattingCode('&').deserialize("&6Parcel")).toText())
            .contents(Text.builder().append(TextSerializers.formattingCode('&').deserialize("&e/parcel create <name> &5[strict] : &7creation d'une parcelle")).toText(),
                Text.builder().append(TextSerializers.formattingCode('&').deserialize("&e/parcel list [player] : &7liste les parcelles appartenant au joueur")).toText(),
                Text.builder().append(TextSerializers.formattingCode('&').deserialize("&e/parcel remove &5[NomParcelle] : &7supprime une parcelle")).toText(),
                Text.builder().append(TextSerializers.formattingCode('&').deserialize("&e/parcel flaglist <NomParcelle> : &7liste les flags")).toText(),
                Text.builder().append(TextSerializers.formattingCode('&').deserialize("&e/parcel flag <nameFlag]> <0|1> &5[NomParcelle] : &7modifie un flag")).toText(),
                Text.builder().append(TextSerializers.formattingCode('&').deserialize("&e/parcel addplayer <player> &5[NomParcelle] : &7ajoute un habitant")).toText(),
                Text.builder().append(TextSerializers.formattingCode('&').deserialize("&e/parcel removeplayer <player> &5[NomParcelle] : &7retire un habitant")).toText(),
                Text.builder().append(TextSerializers.formattingCode('&').deserialize("&e/parcel ownerset <player> &5[NomParcelle] : &7change le propietaire")).toText(),
                Text.builder().append(TextSerializers.formattingCode('&').deserialize("&e/parcel sale <price> &5[NomParcelle] : &7place un panneau pour vendre")).toText())
            .header(Text.builder().append(TextSerializers.formattingCode('&').deserialize("&eUsage:")).toText())
            .padding(Text.of("-"))
            .sendTo(sender);
        
        return CommandResult.success();
    }
}