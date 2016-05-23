package net.teraoctet.genesys.commands.plot;

import java.util.Optional;
import static net.teraoctet.genesys.Genesys.plotManager;
import static net.teraoctet.genesys.Genesys.serverManager;
import net.teraoctet.genesys.player.GPlayer;
import net.teraoctet.genesys.plot.GPlot;
import net.teraoctet.genesys.utils.GData;
import static net.teraoctet.genesys.utils.GData.getGPlayer;
import static net.teraoctet.genesys.utils.MessageManager.MESSAGE;
import static net.teraoctet.genesys.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.genesys.utils.MessageManager.NO_PERMISSIONS;
import static net.teraoctet.genesys.utils.MessageManager.NO_PLOT;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

public class CommandPlotMsg implements CommandExecutor {
        
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {

        if(src instanceof Player && src.hasPermission("genesys.plot.msg")) {
            Optional<String> arguments = ctx.<String> getOne("arguments");
                        
            Player player = (Player)src;
            GPlayer gplayer = getGPlayer(player.getUniqueId().toString());
                        
            // on vérifie que le joueur se situe bien sur une parcelle sinon on sort
            Optional<GPlot> gplot = plotManager.getPlot(player.getLocation());
            if(!gplot.isPresent()){
                player.sendMessage(NO_PLOT());
                return CommandResult.empty();
            }
            
            // on vérifie que le joueur est bien le owner de la parcelle
            if(gplot.get().getUuidOwner().equals(player.getIdentifier()) || gplayer.getLevel() == 10){
                
                // si le joueur n'a pas tapé d'arguments on affiche le message existant
                if(!ctx.<String> getOne("arguments").isPresent()){
                    Text msg = MESSAGE(gplot.get().getMessage());
                    player.sendMessage(msg); 
                    return CommandResult.success();
                    
                // sinon on remplace le message par les arguments
                } else {
                    String[] args = arguments.get().split(" ");
                    String smsg = "";
                    for (String arg : args) {
                        smsg = smsg + arg + " ";
                    }
                    Text msg = MESSAGE(smsg);
                    gplot.get().setMessage(serverManager.quoteToSQL(smsg));
                    gplot.get().update();
                    GData.commit();
                    player.sendMessage(MESSAGE("&cVotre nouveau message :"));
                    player.sendMessage(msg);
                    return CommandResult.success();
                }     
            } else {
                player.sendMessage(MESSAGE("Vous n'etes pas le proprietaire de cette parcelle"));
                return CommandResult.empty();
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
