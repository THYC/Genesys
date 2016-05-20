package net.teraoctet.genesys.utils;

import com.flowpowered.math.vector.Vector3d;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import static net.teraoctet.genesys.Genesys.plugin;
import net.teraoctet.genesys.player.GPlayer;
import static net.teraoctet.genesys.utils.GData.getGPlayer;
import static net.teraoctet.genesys.utils.MessageManager.ERROR;
import org.spongepowered.api.Sponge;
import static org.spongepowered.api.Sponge.getGame;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;

public class CooldownToTP {
    
    private Task taskCountdown = null;
    private final Player player;
    private final String world;
    private final int X;
    private final int Y;
    private final int Z;
    private Optional<Text> msg = Optional.empty();
    private boolean result;
    
    public CooldownToTP(Player player, String world, int X, int Y, int Z){
        this.result = false;
        this.player = player;
        this.world = world;
        this.X = X;
        this.Y = Y;
        this.Z = Z;
    }
    
    public CooldownToTP(Player player, String world, int X, int Y, int Z, Optional<Text> msg){
        this.result = false;
        this.player = player;
        this.world = world;
        this.X = X;
        this.Y = Y;
        this.Z = Z;
        this.msg = msg;
    }
    
    @SuppressWarnings("empty-statement")
    public void run(){
        taskCountdown = Sponge.getScheduler().createTaskBuilder().execute(() -> {
            try{
            Location lastLocation = player.getLocation();
            player.transferToWorld(getGame().getServer().getWorld(world).get(), new Vector3d(X, Y, Z));
            GPlayer gplayer = getGPlayer(player.getUniqueId().toString());
            gplayer.setLastposition(DeSerialize.location(lastLocation));
            gplayer.update();
            this.result = true;
            if(msg.isPresent())player.sendMessage(msg.get());
            }catch(Exception ex){
                player.sendMessage(ERROR());
                this.result = false;
            }                          
        }).delay(GConfig.COOLDOWN_TO_TP(), TimeUnit.SECONDS).submit(plugin);
    }
    
    public boolean getResult(){
        return this.result;
    }

    public void stopTP()
    {
        taskCountdown.cancel();
    }
}
