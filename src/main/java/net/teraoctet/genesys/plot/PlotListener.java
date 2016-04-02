package net.teraoctet.genesys.plot;

import java.util.ArrayList;
import java.util.Optional;
import static net.teraoctet.genesys.Genesys.plotManager;
import net.teraoctet.genesys.utils.GData;
import static net.teraoctet.genesys.utils.GData.getGPlayer;
import static net.teraoctet.genesys.utils.MessageManager.PLOT_INFO;
import static net.teraoctet.genesys.utils.MessageManager.PLOT_PROTECTED;
import static net.teraoctet.genesys.utils.MessageManager.PLOT_NO_ENTER;
import static net.teraoctet.genesys.utils.MessageManager.PLOT_NO_FLY;
import net.teraoctet.genesys.player.GPlayer;
import static net.teraoctet.genesys.utils.GConfig.DISPLAY_PLOT_MSG_FOR_OWNER;
import org.spongepowered.api.block.BlockSnapshot;
import static org.spongepowered.api.block.BlockTypes.AIR;
import static org.spongepowered.api.block.BlockTypes.FIRE;
import static org.spongepowered.api.block.BlockTypes.FLOWING_LAVA;
import static org.spongepowered.api.block.BlockTypes.FLOWING_WATER;
import org.spongepowered.api.block.tileentity.Sign;
import org.spongepowered.api.block.tileentity.TileEntity;
import org.spongepowered.api.data.Transaction;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.mutable.tileentity.SignData;
import org.spongepowered.api.entity.explosive.Explosive;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.event.command.SendCommandEvent;
import org.spongepowered.api.event.entity.DisplaceEntityEvent;
import org.spongepowered.api.event.world.ExplosionEvent;
import static org.spongepowered.api.item.ItemTypes.WOODEN_SHOVEL;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.chat.ChatTypes;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.explosion.Explosion;
import static net.teraoctet.genesys.utils.MessageManager.MISSING_BALANCE;
import static net.teraoctet.genesys.utils.MessageManager.MESSAGE;
import static org.spongepowered.api.block.BlockTypes.STANDING_SIGN;
import static org.spongepowered.api.block.BlockTypes.WALL_SIGN;

public class PlotListener {
        
    public PlotListener() {}

    @Listener
    @SuppressWarnings("null")
    public void onInteractBlock(InteractBlockEvent  event){
        Optional<Player> optPlayer = event.getCause().first(Player.class);
        if (!optPlayer.isPresent()) {
            return;
        }
        Player player = optPlayer.get();
        GPlayer gplayer = getGPlayer(player.getUniqueId().toString());
        BlockSnapshot b = event.getTargetBlock();
        Location loc = b.getLocation().get();
        Optional<ItemStack> itemInHand = player.getItemInHand();
        GPlot plot = plotManager.getPlot(loc);
        
        // Event click gauche -- saisie angle 1 plot
        if (event instanceof InteractBlockEvent.Primary){
            if(itemInHand.isPresent()){
                if(itemInHand.get().getItem() == WOODEN_SHOVEL){
                    if (plot != null) {
                        player.sendMessage(ChatTypes.CHAT,PLOT_INFO(player,plot.getNameAllowed(),plot.getNameOwner(),plot.getName()));
                        event.setCancelled(true);
                        return;
                    } else {
                        PlotManager plotPlayer = PlotManager.getSett(player);
                        plotPlayer.setBorder(1, loc);
                        player.sendMessage(ChatTypes.CHAT,Text.of(TextColors.GREEN, "Angle1 : ", TextColors.YELLOW, String.format("%d %d %d", new Object[] { loc.getBlockX(), loc.getBlockY(), loc.getBlockZ() })));
                        event.setCancelled(true);
                        return;
                    }
                } 
            }
        }
        
        // Event click droit -- saisie angle 2 plot
	if (event instanceof InteractBlockEvent.Secondary){
            if(itemInHand.isPresent()){
                if(itemInHand.get().getItem() == WOODEN_SHOVEL){
                    if (plot != null) {
                        player.sendMessage(ChatTypes.CHAT,PLOT_INFO(player,plot.getNameAllowed(),plot.getNameOwner(),plot.getName()));
                        event.setCancelled(true);
                        return;
                    } else {
                        PlotManager plotPlayer = PlotManager.getSett(player);
                        plotPlayer.setBorder(2, loc);
                        player.sendMessage(ChatTypes.CHAT,Text.of(TextColors.GREEN, "Angle2 : ", TextColors.YELLOW, String.format("%d %d %d", new Object[] { loc.getBlockX(), loc.getBlockY(), loc.getBlockZ() })));
                        event.setCancelled(true);
                    }
                    return;
                } 
            }
        }
        
        // Interact sur sign "Parcelle a vendre"
        Optional<TileEntity> block = loc.getTileEntity();
        if (block.isPresent()) {
            TileEntity tile=block.get();
            if (tile instanceof Sign) {
                if (event instanceof InteractBlockEvent.Secondary){
                    Sign sign=(Sign)tile;
                    Optional<SignData> optional=sign.getOrCreate(SignData.class);
                    if (optional.isPresent()) {
                        SignData offering = optional.get();
                        Text txt1 = offering.lines().get(0);
                        if (txt1.equals(MESSAGE("&1A VENDRE"))){
                            int cout = Integer.valueOf(Text.of(offering.getValue(Keys.SIGN_LINES).get().get(2)).toPlain());
                            plot = plotManager.getPlot(Text.of(offering.getValue(Keys.SIGN_LINES).get().get(1)).toPlain());
                            if (gplayer.getMoney()< cout && !plot.getUuidOwner().contains(player.getIdentifier())){
                                player.sendMessage(ChatTypes.CHAT,MISSING_BALANCE());
                                event.setCancelled(true);
                            }
                            if (!plot.getUuidOwner().contains("ADMIN") && !plot.getUuidOwner().contains(player.getIdentifier())){
                                GPlayer vendeur = getGPlayer(plot.getUuidOwner());
                                vendeur.creditMoney(cout);
                                vendeur.sendMessage(MESSAGE("&6" + player.getName() + " &7vient d'acheter votre parcelle"));
                                vendeur.sendMessage(MESSAGE("&6" + cout + " emeraudes &7ont ete ajoute a votre compte"));
                                vendeur.sendMessage(MESSAGE("&6/bank &7pour consulter votre compte"));  
                            }
                            plot.delSale();
                            if(!plot.getUuidOwner().contains(player.getIdentifier())){
                                gplayer.debitMoney(cout);
                                plot.setUuidOwner(gplayer.getUUID());
                                plot.setUuidAllowed(gplayer.getUUID());
                                plot.update();
                                GData.commit();
                                player.sendMessage(MESSAGE("&eVous etes maintenant le nouveau proprietaire de cette parcelle"));
                            } else {
                                player.sendMessage(MESSAGE("&eVous avez annule la vente de votre parcelle"));
                            }
                            return;
                        } 
                    }
                }
            } 
        }
        
        // Interact sur autre block
        if (plot != null && !plot.getUuidAllowed().contains(player.getUniqueId().toString()) 
                && plot.getNoInteract() == 1 && gplayer.getLevel() != 10) {
            player.sendMessage(ChatTypes.CHAT,PLOT_PROTECTED());
            event.setCancelled(true);
        }
    }
    
    @Listener
    public void onPlayerMovePlot(DisplaceEntityEvent.Move event) {
        if (event.getTargetEntity() instanceof Player){
            Player player = (Player) event.getTargetEntity();
            GPlayer gplayer = getGPlayer(player.getUniqueId().toString());
            Location locTo = event.getToTransform().getLocation();
            Location locFrom = event.getFromTransform().getLocation();
            GPlot gplot = plotManager.getPlot(locTo);
            GPlot jail = plotManager.getPlot(locFrom,true);
            
            if(plotManager.getPlot(locFrom) == null) {
                if (gplot != null && DISPLAY_PLOT_MSG_FOR_OWNER() && gplot.getUuidAllowed().contains(player.getUniqueId().toString())){ 
                    player.sendMessage(ChatTypes.CHAT,MESSAGE(gplot.getMessage(),player));
                }
            }
            
            if (gplot != null && !gplot.getUuidAllowed().contains(player.getUniqueId().toString()) && gplayer.getLevel() != 10){
                if (player.get(Keys.CAN_FLY).isPresent()) { 
                    if(player.get(Keys.CAN_FLY).get() == true && gplot.getNoFly() == 1) {
                        player.offer(Keys.IS_FLYING, false); 
                        player.offer(Keys.CAN_FLY, false); 
                        player.sendMessage(ChatTypes.CHAT,PLOT_NO_FLY());
                        event.setCancelled(true);
                    }
                }

                if(plotManager.getPlot(locFrom) == null) {
                    player.sendMessage(ChatTypes.CHAT,MESSAGE(gplot.getMessage(),player));
                    if(gplot.getNoEnter() == 1 && !player.hasPermission("genesys.plot.enter")) {
                        player.setLocation(locFrom);
                        player.sendMessage(ChatTypes.CHAT,PLOT_NO_ENTER());
                        event.setCancelled(true);
                    }
                }
            }

            if (jail != null){
                if(plotManager.getPlot(locTo, true) == null && gplayer.getJail().equalsIgnoreCase(jail.getName())) {
                    player.setLocation(locFrom);
                    player.sendMessage(ChatTypes.CHAT,PLOT_NO_ENTER());
                }
            }
        }
    }
    
    @Listener
    public void onPlayerSendCommand(SendCommandEvent event) {
        Optional<Player> optPlayer = event.getCause().first(Player.class);
        if (!optPlayer.isPresent()) {
            return;
        }
        Player player = optPlayer.get();
        GPlayer gplayer = getGPlayer(player.getUniqueId().toString());
        Location loc = player.getLocation();
        GPlot gplot = plotManager.getPlot(loc);
        if (gplot != null && !gplot.getUuidAllowed().contains(player.getUniqueId().toString()) && gplayer.getLevel() != 10){
            player.sendMessage(ChatTypes.CHAT,PLOT_PROTECTED());
            event.setCancelled(true);
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
                       
        GPlot gplot = plotManager.getPlot(loc);
        if (gplot != null && gplot.getNoBreak() == 1 && !gplot.getUuidAllowed().contains(player.getUniqueId().toString()) && gplayer.getLevel() != 10){
            player.sendMessage(PLOT_PROTECTED());
            event.setCancelled(true);
        }
    }
    
    @Listener
    public void onBreakSignSale(ChangeBlockEvent.Break event) {
        Transaction<BlockSnapshot> block = event.getTransactions().get(0);
        if (block.getOriginal().getState().equals(STANDING_SIGN) || block.getOriginal().getState().equals(WALL_SIGN)){
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
        
        GPlot gplot = plotManager.getPlot(loc);
        if (gplot != null && gplot.getNoBuild() == 1 && !gplot.getUuidAllowed().contains(player.getUniqueId().toString()) && gplayer.getLevel() != 10){
            player.sendMessage(PLOT_PROTECTED());
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
        
        GPlot gplot = plotManager.getPlot(loc);
        if (gplot != null && !gplot.getUuidAllowed().contains(player.getUniqueId().toString()) && gplayer.getLevel() != 10){
            player.sendMessage(PLOT_PROTECTED());
            event.setCancelled(true);
        }
    }
    
    @Listener
    public void onBurningBlock(ChangeBlockEvent.Post event) {
        Transaction<BlockSnapshot> block = event.getTransactions().get(0);
        Optional<Location<World>> optLoc = block.getOriginal().getLocation();
        Location loc = optLoc.get();
        GPlot gplot = plotManager.getPlot(loc);
        if (gplot != null){
            if(block.getFinal().getState().getType() == FIRE && gplot.getNoFire() == 1){                
                event.setCancelled(true);   
                return;
            }
                        
            Optional<Player> optPlayer = event.getCause().first(Player.class);
            Optional<Explosive> optExplosive = event.getCause().first(Explosive.class);
            
            if(block.getFinal().getState().getType() == AIR && block.getOriginal().getState().getType() != FLOWING_LAVA && 
            block.getOriginal().getState().getType() != FLOWING_WATER && !optPlayer.isPresent() && !optExplosive.isPresent() && gplot.getNoFire() == 1){
                event.setCancelled(true);   
            }
        }
    }
        
    @Listener
    public void onExplosion(ExplosionEvent.Pre event) {
        Explosion explosion = event.getExplosion();
        Location loc = new Location(event.getTargetWorld(),explosion.getOrigin());
        GPlot gplot = plotManager.getPlot(loc);
        if (gplot != null){
            if (gplot.getNoTNT() == 1){ event.setCancelled(true);}
        }
    }
}
