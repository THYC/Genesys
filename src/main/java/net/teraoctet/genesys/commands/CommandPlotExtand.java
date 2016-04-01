package net.teraoctet.genesys.commands;

import com.flowpowered.math.vector.Vector3d;
import static java.lang.Math.abs;
import static net.teraoctet.genesys.Genesys.plotManager;
import net.teraoctet.genesys.plot.GPlot;
import static net.teraoctet.genesys.utils.GData.getGPlayer;
import static net.teraoctet.genesys.utils.MessageManager.USAGE;
import net.teraoctet.genesys.player.GPlayer;
import net.teraoctet.genesys.utils.GData;
import static net.teraoctet.genesys.utils.MessageManager.BUYING_COST_PLOT;
import static net.teraoctet.genesys.utils.MessageManager.MESSAGE;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import static net.teraoctet.genesys.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.genesys.utils.MessageManager.NO_PERMISSIONS;
import static net.teraoctet.genesys.utils.MessageManager.NO_PLOT;
import static net.teraoctet.genesys.utils.MessageManager.PROTECT_LOADED_PLOT;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;

public class CommandPlotExtand implements CommandExecutor {
           
    @Override
    public CommandResult execute(CommandSource sender, CommandContext ctx) {
        
        if(sender instanceof Player == false) { 
            sender.sendMessage(NO_CONSOLE()); 
            return CommandResult.success(); 
        }
        
        Player player = (Player) sender;
        
        // on vérifie que le joueur à bien les droits d'utiliser cette commande
        if(!player.hasPermission("genesys.plot.create")) { 
                sender.sendMessage(NO_PERMISSIONS()); 
                return CommandResult.empty(); 
        }
        
        // on vérifie que le paramètre value est bien renseigné sinon on sort
        if(!ctx.getOne("value").isPresent()) { 
            player.sendMessage(MESSAGE("&bVous devez regarder vers un des 4 points cardinaux et taper la commande :"));
            player.sendMessage(USAGE("/plot extand <value> : extension d'une parcelle"));
            return CommandResult.empty();
        }
        
        // on vérifie que le jouer se situe bien sur une parcelle sinon on sort
        GPlot gplot = plotManager.getPlot(player.getLocation());
        if(gplot == null){
            player.sendMessage(NO_PLOT());
            return CommandResult.empty();
        }
        
        // on vérifie que le joueur est bien le propiétaire de la parcelle sinon on sort
        if(!gplot.getUuidOwner().contains(player.getIdentifier())){
            player.sendMessage(MESSAGE("&bVous devez être le propriétaire de cette parcelle"));
            return CommandResult.empty();
        }
        
        // on initialise les variables néccéssaires au calcul 
        int extand = ctx.<Integer> getOne("value").get();   // valeur d'allongement de la parcelle en nb de bloc
        int nbBlock = 0;                                    // var ou sera stocké la surface en nb de bloc après calcul
        String axe = "X";                                   // var ou sera stocké le point d'axe à modifier vers laquelle on étend la parcelle 
        int point = 0;                                      // var stockant la nouvelle valeur du point x ou z après calcul
        int amount = 1;                                     // var stockant le prix de l'extension
        
        // on vérifie que le joueur fait une demande d'extension, si 1 des 3 paramètres est renseigné 
        // c'est qu'il est possible que l'on soit sur une validation
        if(!ctx.getOne("point").isPresent() && !ctx.getOne("axe").isPresent()) { 
            
            // on identifie vers quel direction regarde le joueur
            Vector3d rotation = player.getHeadRotation();
            
                // le joueur regarde vers le nord
            if ((rotation.getY() < -135 && rotation.getY() > -225) || (rotation.getY() > 135 && rotation.getY() < 225)){
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
            } else if ((rotation.getY() < -225 && rotation.getY() > -315) || (rotation.getY() > 45 && rotation.getY() < 135)){
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
            } else if ((rotation.getY() < -315 && rotation.getY() > -360) || (rotation.getY() < 0 && rotation.getY() > -45) ||
                    (rotation.getY() < 45 && rotation.getY() > 0) || (rotation.getY() < 360 && rotation.getY() > 315)){
                if(gplot.getZ1() > gplot.getZ2()){
                    // on sauve la valeur dans "point" pour l'affecter à "gplot.setZ1" après validation
                    point = gplot.getZ1() + extand; 
                    // on sauve la valeur du point à modifier
                    axe = "Z1";
                } else {
                    // on sauve la valeur dans "point" pour l'affecter à "gplot.setZ2" après validation
                    point = gplot.getZ2() + extand;
                    // on sauve la valeur du point à modifier
                    axe = "Z2";
                }
                nbBlock = extand * abs(gplot.getX1() - gplot.getX2());
                
                // le joueur regarde vers l'est
            } else if ((rotation.getY() > -135 && rotation.getY() < -45) || (rotation.getY() > 225 && rotation.getY() < 315)){
                if(gplot.getX1() > gplot.getX2()){
                    // on sauve la valeur dans "point" pour l'affecter à "gplot.setX1" après validation
                    point = gplot.getX1() + extand; 
                    // on sauve la valeur du point à modifier
                    axe = "X1";
                } else {
                    // on sauve la valeur dans "point" pour l'affecter à "gplot.setX2" après validation
                    point = gplot.getX2() + extand;
                    // on sauve la valeur du point à modifier
                    axe = "X2";
                }
                nbBlock = extand * abs(gplot.getZ1() - gplot.getZ2());
                
            }
                        
            if(nbBlock < 51){ amount = 1;}
            else if(nbBlock < 101){ amount = 2;}
            else if(nbBlock < 201){ amount = 3;}
            else { amount = nbBlock / 60;}
            
            player.sendMessage(MESSAGE("&7Le co\373t de cette transaction est de : &e" + amount + " \351meraudes"));
            player.sendMessage(Text.builder("Clique ici pour confirmer l'ajout de " + nbBlock + " block sur ta parcelle\n")
                    .onClick(TextActions.runCommand("/p extand " + extand + " " + point + " " + axe))
                    .color(TextColors.AQUA)
                    .build());   
            return CommandResult.success();
        
        } else {
            
            if(!ctx.getOne("point").isPresent() || !ctx.getOne("axe").isPresent()) { 
                player.sendMessage(MESSAGE("&bRecommencez, vous devez regarder vers un des 4 points cardinaux et taper la commande :"));
                player.sendMessage(USAGE("/plot extand <value> : extension d'une parcelle"));
                return CommandResult.empty();
            }
            
            String playerUUID = player.getUniqueId().toString();
            GPlayer gplayer = getGPlayer(playerUUID);
            
            axe = ctx.<String> getOne("axe").get();         // on récupère l'axe vers laquelle on étend la parcelle 
            point = ctx.<Integer> getOne("point").get();    // on récupére le nouveau point de coordonnée 
            
            Boolean SuccessTransaction = false;
            
            switch(axe){ 
                case "Z1": 
                    // on calcul le nombre de bloc ajouté pour le calcul du prix
                    nbBlock = extand * abs(gplot.getX1() - gplot.getX2());
                    SuccessTransaction = ConfirmTransaction(nbBlock,gplayer);
                    if (SuccessTransaction == true)gplot.setZ1(point);
                    break;
                case "Z2":
                    // on calcul le nombre de bloc ajouté pour le calcul du prix
                    nbBlock = extand * abs(gplot.getX1() - gplot.getX2()); 
                    SuccessTransaction = ConfirmTransaction(nbBlock,gplayer);
                    if (SuccessTransaction == true)gplot.setZ2(point);
                    break; 
                case "X1":
                    // on calcul le nombre de bloc ajouté pour le calcul du prix
                    nbBlock = extand * abs(gplot.getZ1() - gplot.getZ2());
                    SuccessTransaction = ConfirmTransaction(nbBlock,gplayer);
                    if (SuccessTransaction == true)gplot.setX1(point);
                    break;     
                case "X2":
                    // on calcul le nombre de bloc ajouté pour le calcul du prix
                    nbBlock = extand * abs(gplot.getZ1() - gplot.getZ2());
                    SuccessTransaction = ConfirmTransaction(nbBlock,gplayer);
                    if (SuccessTransaction == true)gplot.setX2(point);
                    break;
            }
            
            if (SuccessTransaction == true){
                gplot.update();
                GData.commit();
                player.sendMessage(PROTECT_LOADED_PLOT(player,gplot.getName()));
                return CommandResult.success();
            } else {
                player.sendMessage(BUYING_COST_PLOT(player,String.valueOf(amount),String.valueOf(gplayer.getMoney())));
                return CommandResult.empty();
            }
        }
    }
    
    private boolean ConfirmTransaction(Integer nbBlock, GPlayer gplayer){
        Integer amount = 1;
        if(nbBlock < 51){ amount = 1;}
        else if(nbBlock < 101){ amount = 2;}
        else if(nbBlock < 201){ amount = 3;}
        else { amount = nbBlock / 60;}

        if(gplayer.getMoney()>= amount || gplayer.getLevel() == 10){
            if(gplayer.getLevel() != 10){ gplayer.debitMoney(amount);}
            return true;
        } else { return false; }
    }
}