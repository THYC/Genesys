package net.teraoctet.genesys.commands;

import java.util.Date;
import static net.teraoctet.genesys.Genesys.serverManager;
import net.teraoctet.genesys.player.GPlayer;
import static net.teraoctet.genesys.utils.GData.getGPlayer;
import static net.teraoctet.genesys.utils.GData.getUUID;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;
import static net.teraoctet.genesys.utils.MessageManager.MESSAGE;
import static net.teraoctet.genesys.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.genesys.utils.MessageManager.NO_PERMISSIONS;
import static net.teraoctet.genesys.utils.MessageManager.DATA_NOT_FOUND;
import static net.teraoctet.genesys.utils.MessageManager.ONHOVER_PI_NAME;
import static net.teraoctet.genesys.utils.MessageManager.TP_AT_COORDS;
import static org.spongepowered.api.Sponge.getGame;
import org.spongepowered.api.service.pagination.PaginationList;
import org.spongepowered.api.service.pagination.PaginationService;

public class CommandPlayerinfo implements CommandExecutor {
        
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {
        //partie de la commande qui cible un <player>, exécuté que si la source a la permission et qu'elle a rempli l'argument <player>
        if(src instanceof Player && ctx.getOne("tplayer").isPresent() && src.hasPermission("genesys.playerinfo.others")) {
            String targetName = ctx.<String> getOne("tplayer").get();
            String targetUUID = getUUID(targetName);
            
            if(targetUUID == null){
                src.sendMessage(DATA_NOT_FOUND(targetName));
            } else { 
                GPlayer gplayer = getGPlayer(targetUUID);
                PaginationService paginationService = getGame().getServiceManager().provide(PaginationService.class).get();
                PaginationList.Builder builder = paginationService.builder(); 

                builder.title(MESSAGE("&8PlayerInfo"))
                        .contents(Text.builder().append(MESSAGE("&8Player: " + targetName))
                                .onHover(TextActions.showText(ONHOVER_PI_NAME(targetUUID)))
                                .onClick(TextActions.suggestCommand("/kick " + targetName))
                                .onShiftClick(TextActions.insertText("/ban " + targetName))
                                .toText(),
                            Text.builder().append(MESSAGE("AAAAAAA")).toText())
                        .padding(Text.of("-"))
                        .sendTo(src);

                /*src.sendMessage(Text.builder("----" + targetName + "----")
                            .onHover(TextActions.showText(Text.builder("UUID: " + targetUUID).build()))
                            .color(TextColors.DARK_GRAY)
                            .build());*/
                                        
                if(serverManager.isOnline(targetName)){
                    Player tPlayer = getGame().getServer().getPlayer(targetName).get();
                    src.sendMessage(Text.builder(targetName + " est connect\351")
                            .onHover(TextActions.showText(Text.builder("IP: " + tPlayer.getConnection().getAddress().toString()).build()))
                            .color(TextColors.DARK_GRAY)
                            .build());
                    src.sendMessage(Text.builder("Position : World=" + tPlayer.getLocation().getExtent().getName() + " X=" + tPlayer.getLocation().getBlockX() + " Y=" + tPlayer.getLocation().getBlockY() + " Z=" + tPlayer.getLocation().getBlockZ())
                            //.onClick(TextActions.runCommand("/"))
                            .onHover(TextActions.showText(TP_AT_COORDS()))
                            .color(TextColors.DARK_GRAY)
                            .build());
                } else {
                    String lastConnection = serverManager.dateShortToString(gplayer.getLastonline());
                    src.sendMessage(Text.builder(targetName + " est d\351connect\351")
                            .onHover(TextActions.showText(Text.builder("Derni\350re connexion: " + lastConnection.toString()).build()))
                            .color(TextColors.DARK_GRAY)
                            .build());
                    src.sendMessage(Text.builder("Derni\350re position : " + gplayer.getLastposition())
                            //.onClick(TextActions.runCommand("/"))
                            .onHover(TextActions.showText(TP_AT_COORDS()))
                            .color(TextColors.DARK_GRAY)
                            .build());
                }

                src.sendMessage(MESSAGE("&8--------------------"));
                /*src.sendMessage(Text.builder("Home (default): World= " + gplayer.getHome("default").getWorld() + " X=" + gplayer.getHome("default").getX() + " Y=" + gplayer.getHome("default").getY() + " Z=" + gplayer.getHome("default").getZ())
                            //.onClick(TextActions.runCommand("/"))
                            .onHover(TextActions.showText(TP_AT_COORDS()))
                            .color(TextColors.DARK_GRAY)
                            .build());*/
                src.sendMessage(MESSAGE("&8Nb de Home(s) : " + gplayer.getHomes().size()));
                src.sendMessage(MESSAGE("&8Bank: " + gplayer.getMoney() + " \351meraudes"));
                src.sendMessage(Text.builder("Droits suppl\351mentaires accordés : ")
                            .onHover(TextActions.showText(Text.builder("Points accumul\351s : ").build()))
                            .color(TextColors.DARK_GRAY)
                            .build());
                src.sendMessage(MESSAGE("&8--------------------"));
                src.sendMessage(Text.builder("> Statistiques minage <")
                            //.onClick(TextActions.runCommand("/"))
                            .onHover(TextActions.showText(Text.builder("Affiche les statistiques des blocs min\351s").build()))
                            .color(TextColors.DARK_GRAY)
                            .build());
                src.sendMessage(MESSAGE("&8--------------------"));
                return CommandResult.success();
            }
        } 
        
        //si la source est un joueur qui n'a pas rempli l'argument <player>, affiche les infos de la source
        else if (src instanceof Player && src.hasPermission("genesys.playerinfo")) {
            Player player = (Player) src;
            GPlayer gplayer = getGPlayer(player.getIdentifier());
            src.sendMessage(MESSAGE("&8--------------------"));
            src.sendMessage(MESSAGE("&7Mes infos : " + player.getName()));
            src.sendMessage(MESSAGE("&8--------------------"));
            src.sendMessage(MESSAGE("&8Temps de connexion : "));
            src.sendMessage(MESSAGE("&8Nombre de points accumulés : "));
            src.sendMessage(MESSAGE("&8Droits suppl\351mentaires accordés : "));
            src.sendMessage(MESSAGE("&8--------------------"));
            //playerPlots(player.getIdentifier());
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
