package net.teraoctet.genesys.commands;

import com.flowpowered.math.vector.Vector3d;
import static java.lang.Math.abs;
import static net.teraoctet.genesys.Genesys.plotManager;
import net.teraoctet.genesys.plot.GPlot;
import static net.teraoctet.genesys.utils.GData.getGPlayer;
import static net.teraoctet.genesys.utils.MessageManager.USAGE;
import net.teraoctet.genesys.player.GPlayer;
import static net.teraoctet.genesys.utils.MessageManager.BUYING_COST_PLOT;
import static net.teraoctet.genesys.utils.MessageManager.MESSAGE;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.chat.ChatTypes;
import static net.teraoctet.genesys.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.genesys.utils.MessageManager.NO_PERMISSIONS;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;

public class CommandPlotExtand implements CommandExecutor {
           
    @Override
    public CommandResult execute(CommandSource sender, CommandContext ctx) throws CommandException {
        
        if(sender instanceof Player == false) { 
            sender.sendMessage(NO_CONSOLE()); 
            return CommandResult.success(); 
        }
        
        Player player = (Player) sender;
        
        // on vérifie que le joueur à bien les droits d'utiliser cette commande
        if(!player.hasPermission("genesys.plot.create")) { 
                sender.sendMessage(NO_PERMISSIONS()); 
                return CommandResult.success(); 
        }
        
        // on vérifie que le paramètre value est bien renseigné sinon on sort
        if(!ctx.getOne("value").isPresent()) { 
            player.sendMessage(ChatTypes.CHAT,MESSAGE("&bVous devez regarder vers un des 4 points cardinaux et taper la commande :"));
            player.sendMessage(ChatTypes.CHAT,USAGE("/plot extand <value> : extension d'une parcelle"));
            return CommandResult.success();
        }
        
        // on vérifie que le jouer se situe bien sur une parcelle sinon on sort
        GPlot gplot = plotManager.getPlot(player.getLocation());
        if(gplot == null){
            player.sendMessage(ChatTypes.CHAT,MESSAGE("&bVous devez être sur la parcelle"));
            return CommandResult.success();
        }
        
        // on vérifie que le joueur est bien le propiétaire de la parcelle sinon on sort
        if(!gplot.getUuidOwner().contains(player.getIdentifier())){
            player.sendMessage(ChatTypes.CHAT,MESSAGE("&bVous devez être le propriétaire de cette parcelle"));
            return CommandResult.success();
        }
        
        // on initialise les variables néccéssaires au calcul 
        int extand = ctx.<Integer> getOne("value").get();   // valeur d'allongement de la parcelle en nb de bloc
        int nbBlock = 0;                                    // var ou sera stocké la surface en nb de bloc après calcul
        String axe = "X";                                   // var ou sera stocké le point d'axe à modifier vers laquelle on étend la parcelle 
        int point = 0;                                      // var sockant la nouvelle valeur du point x ou z après calcul
        
        // on vérifie que le joueur fait une demande d'extension, si 1 des 3 paramètres est renseigné 
        // c'est qu'il est possible que l'on soit sur une validation
        if(!ctx.getOne("card").isPresent() && !ctx.getOne("nbblock").isPresent() && !ctx.getOne("point").isPresent()) { 
            
            // on identifie vers quel direction regarde le joueur
            Vector3d rotation = player.getHeadRotation();
        
                // le joueur regarde vers le nord
            if (rotation.getY() < -135 && rotation.getY() > -225){
                if(gplot.getZ1() > gplot.getZ2()){
                    // on sauve la valeur dans "point" pour l'affecter à "gplot.setZ2" après validation
                    point = gplot.getZ2() - extand; 
                    // on sauve la valeur du point à modifier
                    axe = "Z2";
                } else {
                    // on sauve la valeur dans "point" pour l'affecter à "gplot.setZ1" après validation
                    point = gplot.getZ1() - extand;
                    // on sauve la valeur du point à modifier
                    axe = "Z1";
                }
                // on sauve le nombre de bloc ajouté pour le calcul du prix
                nbBlock = extand * abs(gplot.getX1() - gplot.getX2()); 

                // le joueur regarde vers l'ouest
            } else if (rotation.getY() < -225 && rotation.getY() > -315){
                if(gplot.getX1() > gplot.getX2()){
                    // on sauve la valeur dans "point" pour l'affecter à "gplot.setX2" après validation
                    point = gplot.getX2() - extand; 
                    // on sauve la valeur du point à modifier
                    axe = "X2";
                } else {
                    // on sauve la valeur dans "point" pour l'affecter à "gplot.setX1" après validation
                    point = gplot.getX1() - extand;
                    // on sauve la valeur du point à modifier
                    axe = "X1";
                }
                nbBlock = extand * abs(gplot.getZ1() - gplot.getZ2());

                // le joueur regarde vers le sud
            } else if (rotation.getY() < -315 && rotation.getY() > -360){
                if(gplot.getZ1() > gplot.getZ2()){
                    gplot.setZ1(gplot.getZ1() + extand);
                } else {
                    gplot.setZ2(gplot.getZ2() + extand);
                }
                nbBlock = extand * abs(gplot.getX1() - gplot.getX2());

                // le joueur regarde vers l'est
            } else if (rotation.getY() < 0 && rotation.getY() > -45){
                if(gplot.getX1() > gplot.getX2()){
                    gplot.setX1(gplot.getX1() + extand);
                } else {
                    gplot.setX2(gplot.getX2() + extand);
                }
                nbBlock = extand * abs(gplot.getZ1() - gplot.getZ2());
            }
        
        } else {
            
            String playerUUID = player.getUniqueId().toString();
            GPlayer gplayer = getGPlayer(playerUUID);

            int amount = 1;
            if(nbBlock < 51){ amount = 1;}
            else if(nbBlock < 101){ amount = 2;}
            else if(nbBlock < 201){ amount = 3;}
            else { amount = nbBlock / 60;}

            if(!ctx.getOne("value").isPresent()) { 
                player.sendMessage(ChatTypes.CHAT,MESSAGE("&bVous devez regarder vers un des 4 points cardinaux et taper la commande :"));
                player.sendMessage(ChatTypes.CHAT,USAGE("/plot extand <value> : extension d'une parcelle"));
                return CommandResult.success();
            }

            if(gplayer.getLevel() == 10){ playerUUID = "ADMIN";} else { gplayer.debitMoney(amount);}

            if(gplayer.getMoney()>= amount || gplayer.getLevel() == 10){
                if(ctx.getOne("strict").isPresent()){
                        //if (ctx.<String> getOne("strict").get().equalsIgnoreCase("strict")) strict = true;
                    }
                    player.sendMessage(ChatTypes.CHAT,MESSAGE("&7Le co\373t de cette transaction est de : &e" + amount + " \351meraudes"));
                    //player.sendMessage(ChatTypes.CHAT,Text.builder("Clique ici pour confirmer la cr\351ation de ta parcelle").onClick(TextActions.runCommand("/p createok " + name + " " + amount + " " + strict)).color(TextColors.AQUA).build());   
                    return CommandResult.success();
            } else {
                player.sendMessage(ChatTypes.CHAT,BUYING_COST_PLOT(player,String.valueOf(amount),String.valueOf(gplayer.getMoney())));
                return CommandResult.success();
            }
        }
        
        //player.sendMessage(MESSAGE(String.valueOf(rotation.getFloorX()) + " _ " +  String.valueOf(rotation.getFloorY()) + " _ " +  String.valueOf(rotation.getFloorZ())));
        
        return CommandResult.success();
    }
}