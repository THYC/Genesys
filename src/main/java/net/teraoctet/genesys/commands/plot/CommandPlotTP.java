package net.teraoctet.genesys.commands.plot;

import com.flowpowered.math.vector.Vector3d;
import java.util.Optional;
import static net.teraoctet.genesys.Genesys.plotManager;
import net.teraoctet.genesys.plot.GPlot;
import static net.teraoctet.genesys.utils.GData.getGPlayer;
import net.teraoctet.genesys.player.GPlayer;
import net.teraoctet.genesys.utils.DeSerialize;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import static net.teraoctet.genesys.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.genesys.utils.MessageManager.NO_PLOT;
import static net.teraoctet.genesys.utils.MessageManager.NO_PERMISSIONS;
import static net.teraoctet.genesys.utils.MessageManager.ERROR;
import static net.teraoctet.genesys.utils.MessageManager.USAGE;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.world.Location;

public class CommandPlotTP implements CommandExecutor {
       
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {
        
        if(src instanceof Player && src.hasPermission("genesys.plot.tp")) { 
            Player player = (Player) src;
            GPlayer gplayer = getGPlayer(player.getUniqueId().toString());
            GPlot gplot = null;

            if(ctx.getOne("name").isPresent()){
                String plotName = ctx.<String> getOne("name").get();
                gplot = plotManager.getPlot(plotName);                

                if (gplot == null){
                    player.sendMessage(NO_PLOT());
                    return CommandResult.empty();
                } else if (gplot.getNoTeleport() == 1 || gplayer.getLevel() == 10){
                    Optional<Location> spawn = gplot.getSpawnPlot();
                    if(spawn.isPresent()){
                        Location lastLocation = player.getLocation();
                        if(!player.transferToWorld(gplot.getworldName(), 
                                new Vector3d(spawn.get().getBlockX(), spawn.get().getBlockY(), spawn.get().getBlockZ()))) { 
                            gplayer.setLastposition(DeSerialize.location(lastLocation));
                            gplayer.update();
                            return CommandResult.success();
                        }
                    } else {
                        src.sendMessage(ERROR()); 
                        return CommandResult.empty(); 
                    }
                } else {
                    src.sendMessage(NO_PERMISSIONS());
                    return CommandResult.empty(); 
                }
            } else {
                src.sendMessage(USAGE("/plot tp <NamePlot>"));
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
