package net.teraoctet.genesys.utils;

import com.flowpowered.math.vector.Vector3d;
import java.util.concurrent.TimeUnit;
import static net.teraoctet.genesys.Genesys.plugin;
import net.teraoctet.genesys.player.GPlayer;
import static net.teraoctet.genesys.utils.GData.getGPlayer;
import org.spongepowered.api.Sponge;
import static org.spongepowered.api.Sponge.getGame;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.world.Location;

public class CountdownToTP {
    
    private Task taskCountdown = null;
    private final Player player;
    private final String world;
    private final int X;
    private final int Y;
    private final int Z;
    private boolean result;
    
    public CountdownToTP(Player player, String world, int X, int Y, int Z){
        this.result = false;
        this.player = player;
        this.world = world;
        this.X = X;
        this.Y = Y;
        this.Z = Z;
    }
    
    @SuppressWarnings("empty-statement")
    public void run(){
        taskCountdown = Sponge.getScheduler().createTaskBuilder().execute(() -> {
            Location lastLocation = player.getLocation();
            player.transferToWorld(getGame().getServer().getWorld(world).get().getUniqueId(), new Vector3d(X, Y, Z));
            GPlayer gplayer = getGPlayer(player.getUniqueId().toString());
            gplayer.setLastposition(DeSerialize.location(lastLocation));
            gplayer.update();
            this.result = true;
             
        }).delay(GConfig.COULDOWN_TO_TP(), TimeUnit.SECONDS).submit(plugin);
    }
    
    public boolean getResult(){
        return this.result;
    }

    public void stopTP()
    {
        taskCountdown.cancel();
    }
}
