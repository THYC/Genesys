package net.teraoctet.genesys.commands;

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
import static net.teraoctet.genesys.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.genesys.utils.MessageManager.NO_PERMISSIONS;

public class CommandPlot implements CommandExecutor {
           
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

        if(!player.hasPermission("genesys.plot")) { 
            sender.sendMessage(NO_PERMISSIONS()); 
            return CommandResult.success(); 
        }
                       
        PaginationService paginationService = getGame().getServiceManager().provide(PaginationService.class).get();
        Builder builder = paginationService.builder();  

        builder.title(Text.builder().append(TextSerializers.formattingCode('&').deserialize("&6Plot")).toText())
            .contents(Text.builder().append(TextSerializers.formattingCode('&').deserialize("&e/plot create <name> &5[strict] : &7cr\351ation d'une parcelle")).toText(),
                Text.builder().append(TextSerializers.formattingCode('&').deserialize("&e/plot list [player] : &7liste des parcelles poss\351d\351es par un joueur")).toText(),
                Text.builder().append(TextSerializers.formattingCode('&').deserialize("&e/plot remove &5[NomParcelle] : &7supprime une parcelle")).toText(),
                Text.builder().append(TextSerializers.formattingCode('&').deserialize("&e/plot flaglist <NomParcelle> : &7liste les diff\351rents flags")).toText(),
                Text.builder().append(TextSerializers.formattingCode('&').deserialize("&e/plot flag <nameFlag]> <0|1> &5[NomParcelle] : &7modifie un flag d'une parcelle")).toText(),
                Text.builder().append(TextSerializers.formattingCode('&').deserialize("&e/plot addplayer <player> &5[NomParcelle] : &7ajoute un habitant \340 une parcelle")).toText(),
                Text.builder().append(TextSerializers.formattingCode('&').deserialize("&e/plot removeplayer <player> &5[NomParcelle] : &7retire un habitant d'une parcelle")).toText(),
                Text.builder().append(TextSerializers.formattingCode('&').deserialize("&e/plot ownerset <player> &5[NomParcelle] : &7change le propi\351taire d'une parcelle")).toText(),
                Text.builder().append(TextSerializers.formattingCode('&').deserialize("&e/plot sale <price> &5[NomParcelle] : &7place un panneau pour vendre une parcelle")).toText())
            .header(Text.builder().append(TextSerializers.formattingCode('&').deserialize("&eUsage:")).toText())
            .padding(Text.of("-"))
            .sendTo(sender);
        
        return CommandResult.success();
    }
}