package net.teraoctet.genesys.portal;

import com.flowpowered.math.vector.Vector3d;
import com.flowpowered.math.vector.Vector3i;
import java.util.Optional;
import static net.teraoctet.genesys.Genesys.portalManager;
import net.teraoctet.genesys.utils.DeSerialize;
import net.teraoctet.genesys.utils.GData;
import static net.teraoctet.genesys.utils.GData.getGPlayer;
import net.teraoctet.genesys.player.GPlayer;
import static net.teraoctet.genesys.utils.MessageManager.MESSAGE;
import static net.teraoctet.genesys.utils.MessageManager.PROTECT_PORTAL;
import net.teraoctet.genesys.world.GWorld;
import static org.spongepowered.api.Sponge.getGame;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.data.Transaction;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.effect.particle.ParticleEffect;
import org.spongepowered.api.effect.particle.ParticleTypes;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.event.entity.DisplaceEntityEvent;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.world.ExplosionEvent;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.chat.ChatTypes;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.title.Title;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.explosion.Explosion;

public class PortalListener {
          
    public PortalListener() {}
    
    @Listener
    public void onPlayerMovePortal(DisplaceEntityEvent.Move event, @First Player player) {
        GPlayer gplayer = getGPlayer(player.getUniqueId().toString());
        Location locTo = event.getToTransform().getLocation();
        GPortal gportal = portalManager.getPortal(locTo);
           
        if (gportal != null){            
            if(player.hasPermission("genesys.portal." + gportal.getName()) || gplayer.getLevel() == 10)
            {
                if(gportal.gettoworld().equalsIgnoreCase("DISABLED")){
                    player.sendMessage(MESSAGE("&aPoint de spawn du portail non configuré, aller au point d'apparition souhaité et taper &e/portal tpfrom " + gportal.getName())); 
                    return;
                }
                                
                gplayer.setLastposition(DeSerialize.location(event.getFromTransform().getLocation()));
                gplayer.update();
                GWorld gworld = GData.getWorld(gportal.gettoworld()); 
                
                Optional<World> world = getGame().getServer().getWorld(gportal.gettoworld());
                Location loc = new Location(world.get(), new Vector3d(gportal.gettoX(), gportal.gettoY(), gportal.gettoZ()));
                player.setLocation(loc);

                player.sendTitle(Title.of(Text.of(TextColors.DARK_GREEN, gworld.getName()),MESSAGE(gportal.getMessage(),player))); 
                if (gplayer.getLevel() == 10){
                    player.sendMessage(ChatTypes.CHAT,MESSAGE("&o&7" + gportal.getName()));
                }
                
                ParticleEffect pEffect = getGame().getRegistry().createBuilder(ParticleEffect.Builder.class)
                        .type(ParticleTypes.PORTAL)
                        .count(20)
                        .build();
            
                Vector3d origin = new Vector3d(player.getLocation().getPosition().getX(), player.getLocation().getPosition().getY() + 1, player.getLocation().getPosition().getZ());
                for (double incr = 0; incr < 3.14; incr += 0.1) {
                    player.getLocation().getExtent().spawnParticles(pEffect, origin.add(Math.cos(incr), 0, Math.sin(incr)), 500);
                    player.getLocation().getExtent().spawnParticles(pEffect, origin.add(Math.cos(incr), 0, -Math.sin(incr)), 500);
                    player.getLocation().getExtent().spawnParticles(pEffect, origin.add(-Math.cos(incr), 0, Math.sin(incr)), 500);
                    player.getLocation().getExtent().spawnParticles(pEffect, origin.add(-Math.cos(incr), 0, -Math.sin(incr)), 500);
                }
                player.offer(Keys.GAME_MODE, player.getWorld().getProperties().getGameMode());
            }
        }
    }
    
    @Listener
    public void onEntityMovePortal(DisplaceEntityEvent.Move event, @First Entity entity) {
        Location locTo = event.getToTransform().getLocation();
        GPortal gportal = portalManager.getPortal(locTo);
        
        if(entity instanceof Player == false){
            if (gportal != null){  
                if(!gportal.gettoworld().equalsIgnoreCase("DISABLED")){
                    Optional<World> world = getGame().getServer().getWorld(gportal.gettoworld());                    
                    Location<World> loc = new Location(world.get(), new Vector3d(gportal.gettoX(), gportal.gettoY(), gportal.gettoZ()));
                    Vector3i chunkPos = loc.getChunkPosition();                    
                    entity.setLocation(loc);
                    loc.getExtent().loadChunk(chunkPos, true);
                }
            }
        }
    }
        
    @Listener
    public void onBreakBlock(ChangeBlockEvent.Break event) {
        Optional<Player> optPlayer = event.getCause().first(Player.class);
        if (!optPlayer.isPresent()) {
            return;
        }
        Player player = optPlayer.get();
        GPlayer gplayer = getGPlayer(player.getUniqueId().toString());
        Transaction<BlockSnapshot> block = event.getTransactions().get(0);
        Optional<Location<World>> optLoc = block.getOriginal().getLocation();
        Location loc = optLoc.get();
    
        GPortal gportal = portalManager.getPortal(loc);
        if (gportal != null && gplayer.getLevel() != 10){
            player.sendMessage(ChatTypes.CHAT,PROTECT_PORTAL());
            event.setCancelled(true);
        }
    }
    
    @Listener
    public void onPlaceBlock(ChangeBlockEvent.Place event) {
        Optional<Player> optPlayer = event.getCause().first(Player.class);
        if (!optPlayer.isPresent()) {
            return;
        }
        Player player = optPlayer.get();
        GPlayer gplayer = getGPlayer(player.getUniqueId().toString());
        Transaction<BlockSnapshot> block = event.getTransactions().get(0);
        
        Optional<Location<World>> optLoc = block.getOriginal().getLocation();
        Location loc = optLoc.get();
        
        GPortal gportal = portalManager.getPortal(loc);
        if (gportal != null && gplayer.getLevel() != 10){
            player.sendMessage(ChatTypes.CHAT,PROTECT_PORTAL());
            event.setCancelled(true);
        }
    }
    
    @Listener
    public void onFluidBlock(ChangeBlockEvent.Modify event) {
        Optional<Player> optPlayer = event.getCause().first(Player.class);
        if (!optPlayer.isPresent()) {
            return;
        }
        Player player = optPlayer.get();
        GPlayer gplayer = getGPlayer(player.getUniqueId().toString());
        Transaction<BlockSnapshot> block = event.getTransactions().get(0);
        
        Optional<Location<World>> optLoc = block.getOriginal().getLocation();
        Location loc = optLoc.get();
        
        GPortal gportal = portalManager.getPortal(loc);
        if (gportal != null && gplayer.getLevel() != 10){
            player.sendMessage(ChatTypes.CHAT,PROTECT_PORTAL());
            event.setCancelled(true);
        }
    }
            
    @Listener
    public void onExplosion(ExplosionEvent.Pre event) {
        Explosion explosion = event.getExplosion();
        Location loc = new Location(event.getTargetWorld(),explosion.getOrigin());
        
        GPortal gportal = portalManager.getPortal(loc);
        if (gportal != null){event.setCancelled(true);}
    }
}
