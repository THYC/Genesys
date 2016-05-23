package net.teraoctet.genesys.commands.plot;

import java.util.Optional;
import static net.teraoctet.genesys.Genesys.plotManager;
import net.teraoctet.genesys.plot.GPlot;
import net.teraoctet.genesys.utils.GData;
import static net.teraoctet.genesys.utils.GData.getGPlayer;
import net.teraoctet.genesys.player.GPlayer;
import static net.teraoctet.genesys.utils.MessageManager.USAGE;
import static net.teraoctet.genesys.utils.MessageManager.formatText;
import static org.spongepowered.api.Sponge.getGame;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.service.pagination.PaginationList.Builder;
import org.spongepowered.api.service.pagination.PaginationService;
import org.spongepowered.api.text.Text;
import static net.teraoctet.genesys.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.genesys.utils.MessageManager.NO_PLOT;
import static net.teraoctet.genesys.utils.MessageManager.NO_PERMISSIONS;
import static net.teraoctet.genesys.utils.MessageManager.ALREADY_OWNED_PLOT;
import static net.teraoctet.genesys.utils.MessageManager.MESSAGE;
import org.spongepowered.api.command.source.ConsoleSource;

public class CommandPlotFlag implements CommandExecutor {
       
    @Override
    @SuppressWarnings("null")
    public CommandResult execute(CommandSource src, CommandContext ctx) {
        
        if(src instanceof Player && src.hasPermission("genesys.plot.flag")) { 
            Player player = (Player) src;
            GPlayer gplayer = getGPlayer(player.getUniqueId().toString());
            Optional<GPlot> gplot = Optional.empty();

            if(ctx.getOne("name").isPresent()){
                String plotName = ctx.<String> getOne("name").get();
                gplot = plotManager.getPlot(plotName);                
            } else {
                gplot = plotManager.getPlot(player.getLocation());
            }

            if (gplot == null){
                player.sendMessage(NO_PLOT());
                player.sendMessage(USAGE("/plot flag <flag> <0|1> : modifie la valeur &6Oui = 1 Non = 0, vous devez \352tre sur la parcelle"));
                player.sendMessage(USAGE("/plot flag <flag> <0|1> [NomParcelle]: modifie la valeur d'un flag &6Oui = 1 Non = 0"));
                player.sendMessage(USAGE("/plot flag> : liste les flags de la parcelle, vous devez \352tre sur la parcelle"));
                player.sendMessage(USAGE("/plot flaglist <NomParcelle> : liste les flags de la parcelle nomm\351e"));
                return CommandResult.empty();
            } else if (!gplot.get().getUuidOwner().equalsIgnoreCase(player.getUniqueId().toString()) && gplayer.getLevel() != 10){
                player.sendMessage(ALREADY_OWNED_PLOT());
                return CommandResult.empty();   
            }

            if(!ctx.getOne("flag").isPresent()){
                PaginationService paginationService = getGame().getServiceManager().provide(PaginationService.class).get();
                Builder builder = paginationService.builder();

                builder.title(formatText("&6Plot Flag"))
                    .contents(  formatText("&e/plot flag <flag> <0|1> : &7modifie la valeur d'un flag"),
                                formatText("&enoEnter : &b[" + gplot.get().getNoEnter() + "] &7ils peuvent entrer sur la parcelle"),
                                formatText("&enoFly : &b[" + gplot.get().getNoFly() + "] &7ils peuvent pas voler au dessus"),
                                formatText("&enoBuild : &b[" + gplot.get().getNoBuild() + "] &7ils peuvent construirent"),
                                formatText("&enoBreak : &b[" + gplot.get().getNoBreak() + "] &7ils peuvent casser"),
                                formatText("&enoInteract : &b[" + gplot.get().getNoInteract() + "] &7ils peuvent ouvrir les portes,coffres..."),
                                formatText("&enoTeleport : &b[" + gplot.get().getNoTeleport() + "] &7ils peuvent se t\351l\351porter"),
                                formatText("&enoFire : &b[" + gplot.get().getNoFire() + "] &7mettre le feu"),
                                formatText("&enoMob : &b[" + gplot.get().getNoMob() + "] &7les monstres spawn"),
                                formatText("&enoTNT : &b[" + gplot.get().getNoTNT() + "] &7activation de la TNT"),
                                formatText("&enoCommand : &b[" + gplot.get().getNoCommand() + "] &7ils peuvent taper des commandes"))
                    .header(formatText("&ePlot " + gplot.get().getName() + " : &7Droits accord\351s aux autres joueurs, 0 = Oui, 1 = Non"))
                    .padding(Text.of("-"))
                    .sendTo(src);
	
            } else {
                if(!ctx.getOne("value").isPresent()){
                    player.sendMessage(USAGE("/plot flag <flag> <0|1> [NomParcelle]: modifie la valeur d'un flag &6Oui = 1 Non = 0"));
                    return CommandResult.empty();
                }

                String flag = ctx.<String> getOne("flag").get();
                Integer value = ctx.<Integer> getOne("value").get(); 

                if (value != 1 && value != 0){
                    player.sendMessage(USAGE("/plot flag <flag> <0|1> [NomParcelle]: modifie la valeur d'un flag &6Oui = 1 Non = 0"));
                    return CommandResult.empty();	
                }

                switch (flag.toLowerCase()){
                    case "noenter":
                        gplot.get().setNoEnter(value);
                        player.sendMessage(MESSAGE("&7Flag enregistr\351 : &enoEnter = " + value));
                        break;
                    case "nofly":
                        gplot.get().setNoFly(value);
                        player.sendMessage(MESSAGE("&7Flag enregistr\351 : &enoFly = " + value));
                        break;
                    case "nobuild":
                        gplot.get().setNoBuild(value);
                        player.sendMessage(MESSAGE("&7Flag enregistr\351 : &enoBuild = " + value));
                        break;
                    case "nobreak":
                        gplot.get().setNoBreak(value);
                        player.sendMessage(MESSAGE("&7Flag enregistr\351 : &enoBreak = " + value));
                        break;
                    case "nointeract":
                        gplot.get().setNoInteract(value);
                        player.sendMessage(MESSAGE("&7Flag enregistr\351 : &enoInteract = " + value));
                        break;
                    case "noteleport":
                        gplot.get().setNoTeleport(value);
                        player.sendMessage(MESSAGE("&7Flag enregistr\351 : &enoTeleport = " + value));
                        break;
                    case "nofire":
                        gplot.get().setNoFire(value);
                        player.sendMessage(MESSAGE("&7Flag enregistr\351 : &enoFire = " + value));
                        break;
                    case "nomob":
                        gplot.get().setNoMob(value);
                        player.sendMessage(MESSAGE("&7Flag enregistr\351 : &enoMob = " + value));
                        break;
                    case "notnt":
                        gplot.get().setNoTNT(value);
                        player.sendMessage(MESSAGE("&7Flag enregistr\351 : &enoTNT = " + value));
                        break;
                    case "nocommand":
                        gplot.get().setNoCommand(value);
                        player.sendMessage(MESSAGE("&7Flag enregistr\351 : &enoCommand = " + value));
                        break;
                }
                gplot.get().update();
                GData.commit();
                return CommandResult.success();
            }
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
