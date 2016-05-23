package net.teraoctet.genesys.plot;

import com.flowpowered.math.vector.Vector3d;
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
//import org.spongepowered.api.event.entity.DisplaceEntityEvent;
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
import static org.spongepowered.api.Sponge.getGame;
import static org.spongepowered.api.block.BlockTypes.STANDING_SIGN;
import static org.spongepowered.api.block.BlockTypes.WALL_SIGN;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.event.Event;
import org.spongepowered.api.event.cause.entity.damage.source.EntityDamageSource;
import org.spongepowered.api.event.entity.DamageEntityEvent;
import org.spongepowered.api.event.entity.MoveEntityEvent;
import org.spongepowered.api.event.entity.TargetEntityEvent;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.filter.cause.Last;
import static org.spongepowered.api.item.ItemTypes.COMPASS;

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
        if(!b.getLocation().isPresent()){return;}
        Location loc = b.getLocation().get();
        Optional<ItemStack> itemInHand = player.getItemInHand();
        Optional<GPlot> plot = plotManager.getPlot(loc);
        
        // Event click gauche -- saisie angle 1 plot
        if (event instanceof InteractBlockEvent.Primary){
            if(itemInHand.isPresent()){
                if(itemInHand.get().getItem() == WOODEN_SHOVEL){
                    if (plot.isPresent()) {
                        player.sendMessage(PLOT_INFO(player,plot.get().getNameAllowed(),plot.get().getNameOwner(),plot.get().getName()));
                        if(player.hasPermission("genesys.admin.plot")){
                            PlotManager plotPlayer = PlotManager.getSett(player);
                            plotPlayer.setBorder(1, loc);
                            player.sendMessage(MESSAGE("&aNiveau : &e" + plot.get().getLevel()));
                            player.sendMessage(Text.of(TextColors.GREEN, "Angle1 : ", TextColors.YELLOW, String.format("%d %d %d", new Object[] { loc.getBlockX(), loc.getBlockY(), loc.getBlockZ() })));
                        }
                    } else {
                        PlotManager plotPlayer = PlotManager.getSett(player);
                        plotPlayer.setBorder(1, loc);
                        player.sendMessage(Text.of(TextColors.GREEN, "Angle1 : ", TextColors.YELLOW, String.format("%d %d %d", new Object[] { loc.getBlockX(), loc.getBlockY(), loc.getBlockZ() })));
                    }
                    event.setCancelled(true);
                    return;
                } 
            }
        }
        
        // Event click droit -- saisie angle 2 plot
	if (event instanceof InteractBlockEvent.Secondary){
            if(itemInHand.isPresent()){
                if(itemInHand.get().getItem() == WOODEN_SHOVEL){
                    if (plot.isPresent()) {
                        player.sendMessage(PLOT_INFO(player,plot.get().getNameAllowed(),plot.get().getNameOwner(),plot.get().getName()));
                        if(player.hasPermission("genesys.admin.plot")){
                            PlotManager plotPlayer = PlotManager.getSett(player);
                            plotPlayer.setBorder(2, loc);
                            player.sendMessage(MESSAGE("&aNiveau : &e" + plot.get().getLevel()));
                            player.sendMessage(Text.of(TextColors.GREEN, "Angle2 : ", TextColors.YELLOW, String.format("%d %d %d", new Object[] { loc.getBlockX(), loc.getBlockY(), loc.getBlockZ() })));
                        }
                    } else {
                        PlotManager plotPlayer = PlotManager.getSett(player);
                        plotPlayer.setBorder(2, loc);
                        player.sendMessage(Text.of(TextColors.GREEN, "Angle2 : ", TextColors.YELLOW, String.format("%d %d %d", new Object[] { loc.getBlockX(), loc.getBlockY(), loc.getBlockZ() })));
                    }
                    event.setCancelled(true);
                    return;
                } 
            }
        }
        
        // si la boussole est en main, on sort"
        Optional<ItemStack> is = player.getItemInHand();
        if (is.isPresent()) {
            if(is.get().getItem().equals(COMPASS)){  
                return;
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
                            if(!plotManager.hasPlot(Text.of(offering.getValue(Keys.SIGN_LINES).get().get(1)).toPlain())){
                                player.sendMessage(MESSAGE("&eCette parcelle n'existe plus"));
                                b.getLocation().get().removeBlock();
                                event.setCancelled(true);
                                return;
                            }
                            plot = plotManager.getPlot(Text.of(offering.getValue(Keys.SIGN_LINES).get().get(1)).toPlain());
                            if(plot.isPresent()){
                                if (gplayer.getMoney()< cout && !plot.get().getUuidOwner().contains(player.getIdentifier())){
                                    player.sendMessage(ChatTypes.CHAT,MISSING_BALANCE());
                                    event.setCancelled(true);
                                }
                                if (!plot.get().getUuidOwner().contains("ADMIN") && !plot.get().getUuidOwner().contains(player.getIdentifier())){
                                    GPlayer vendeur = getGPlayer(plot.get().getUuidOwner());
                                    vendeur.creditMoney(cout);
                                    vendeur.sendMessage(MESSAGE("&6" + player.getName() + " &7vient d'acheter votre parcelle"));
                                    vendeur.sendMessage(MESSAGE("&6" + cout + " emeraudes &7ont ete ajoute a votre compte"));
                                    vendeur.sendMessage(MESSAGE("&6/bank &7pour consulter votre compte"));  
                                }
                                plot.get().delSale();
                                if(!plot.get().getUuidOwner().contains(player.getIdentifier())){
                                    gplayer.debitMoney(cout);
                                    plot.get().setUuidOwner(gplayer.getUUID());
                                    plot.get().setUuidAllowed(gplayer.getUUID());
                                    plot.get().update();
                                    GData.commit();
                                    player.sendMessage(MESSAGE("&eVous etes maintenant le nouveau proprietaire de cette parcelle"));
                                } else {
                                    player.sendMessage(MESSAGE("&eVous avez annule la vente de votre parcelle"));
                                }
                            }
                            return;
                        } 
                    }
                }
            } 
        }
        
        // Interact sur autre block
        if (plot.isPresent()){
            if(!plot.get().getUuidAllowed().contains(player.getUniqueId().toString()) && plot.get().getNoInteract() == 1 && gplayer.getLevel() != 10) {
                player.sendMessage(PLOT_PROTECTED());
                player.sendMessage(MESSAGE(String.valueOf(plot.get().getNoInteract())));
                event.setCancelled(true);
            }
        }
    }
    
    @Listener
    public void onPlayerMovePlot2(TargetEntityEvent event, @Last Player player) {
        getGame().getServer().getConsole().sendMessage(MESSAGE(event.getCause().toString()));
    }
    
    @Listener
    public void onPlayerMovePlot(MoveEntityEvent.Position event , @Last Player player) {
        getGame().getServer().getConsole().sendMessage(MESSAGE(event.getTargetEntity().getType().getId()));
        getGame().getServer().getConsole().sendMessage(MESSAGE(player.getName()));
        GPlayer gplayer = getGPlayer(player.getUniqueId().toString());
        Vector3d to = event.getToPosition();
        Vector3d from = event.getFromPosition();
        String world = player.getWorld().getName();
        Optional<GPlot> gplot = plotManager.getPlot(world,to);
        Optional<GPlot> jail = plotManager.getPlot(world,from,true);

        getGame().getServer().getConsole().sendMessage(MESSAGE("to : " + to.toString()));
        getGame().getServer().getConsole().sendMessage(MESSAGE("from : " + from.toString()));

        if(!plotManager.getPlot(world,from).isPresent()) {
            if(gplot.isPresent()){
                if(DISPLAY_PLOT_MSG_FOR_OWNER() && gplot.get().getUuidAllowed().contains(player.getUniqueId().toString()))
                player.sendMessage(MESSAGE(gplot.get().getMessage(),player));
            }
        }

        if(gplot.isPresent()){
            if(!gplot.get().getUuidAllowed().contains(player.getUniqueId().toString()) && gplayer.getLevel() != 10){
                if (player.get(Keys.CAN_FLY).isPresent()) { 
                    if(player.get(Keys.CAN_FLY).get() == true && gplot.get().getNoFly() == 1) {
                        player.offer(Keys.IS_FLYING, false); 
                        player.offer(Keys.CAN_FLY, false); 
                        player.sendMessage(PLOT_NO_FLY());
                        event.setCancelled(true);
                    }
                }

                if(!plotManager.getPlot(world,from).isPresent()) {
                    player.sendMessage(MESSAGE(gplot.get().getMessage(),player));
                    if(gplot.get().getNoEnter() == 1 && !player.hasPermission("genesys.plot.enter")) {
                        player.transferToWorld(getGame().getServer().getWorld(world).get(), from);
                        player.sendMessage(PLOT_NO_ENTER());
                        event.setCancelled(true);
                    }
                }
            }
        }

        if (jail.isPresent()){
            if(!plotManager.getPlot(world, to, true).isPresent() && gplayer.getJail().equalsIgnoreCase(jail.get().getName())) {
                player.transferToWorld(getGame().getServer().getWorld(world).get(), from);
                player.sendMessage(ChatTypes.CHAT,PLOT_NO_ENTER());
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
        Optional<GPlot> gplot = plotManager.getPlot(loc);
        if (gplot.isPresent()){
            if(!gplot.get().getUuidAllowed().contains(player.getUniqueId().toString()) && gplayer.getLevel() != 10 && gplot.get().getNoCommand() == 1){
                player.sendMessage(PLOT_PROTECTED());
                event.setCancelled(true);
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
                       
        Optional<GPlot> gplot = plotManager.getPlot(loc);
        if (gplot.isPresent()){
            if(gplot.get().getNoBreak() == 1 && !gplot.get().getUuidAllowed().contains(player.getUniqueId().toString()) && 
                    gplayer.getLevel() != 10){
                player.sendMessage(PLOT_PROTECTED());
                player.sendMessage(MESSAGE("break"));
                event.setCancelled(true);
            }
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
        
        Optional<GPlot> gplot = plotManager.getPlot(loc);
        if (gplot.isPresent()){
            if(gplot.get().getNoBuild() == 1 && !gplot.get().getUuidAllowed().contains(player.getUniqueId().toString()) && gplayer.getLevel() != 10){
                player.sendMessage(PLOT_PROTECTED());
                event.setCancelled(true);
            }
        }
    }
    
    @Listener
    public void onFluidBlock(ChangeBlockEvent.Modify event) {
        if(event.getTransactions().contains(FLOWING_WATER) || event.getTransactions().contains(FLOWING_LAVA)){
            Optional<Player> optPlayer = event.getCause().first(Player.class);
            if (!optPlayer.isPresent()) {
                return;
            }
            Player player = optPlayer.get();
            GPlayer gplayer = getGPlayer(player.getUniqueId().toString());
            Transaction<BlockSnapshot> block = event.getTransactions().get(0);

            Optional<Location<World>> optLoc = block.getOriginal().getLocation();
            Location loc = optLoc.get();

            Optional<GPlot> gplot = plotManager.getPlot(loc);
            if (gplot.isPresent()){
                if(!gplot.get().getUuidAllowed().contains(player.getUniqueId().toString()) && gplayer.getLevel() != 10){
                    player.sendMessage(PLOT_PROTECTED());
                    event.setCancelled(true);
                }
            }
        }
    }
    
    @Listener
    public void onBurningBlock(ChangeBlockEvent.Post event) {
        Transaction<BlockSnapshot> block = event.getTransactions().get(0);
        Optional<Location<World>> optLoc = block.getOriginal().getLocation();
        Location loc = optLoc.get();
        Optional<GPlot> gplot = plotManager.getPlot(loc);
        if (gplot.isPresent()){
            if(block.getFinal().getState().getType() == FIRE && gplot.get().getNoFire() == 1){                
                event.setCancelled(true);   
                return;
            }
                        
            Optional<Player> optPlayer = event.getCause().first(Player.class);
            Optional<Explosive> optExplosive = event.getCause().first(Explosive.class);
            
            if(block.getFinal().getState().getType() == AIR && block.getOriginal().getState().getType() != FLOWING_LAVA && 
            block.getOriginal().getState().getType() != FLOWING_WATER && !optPlayer.isPresent() && !optExplosive.isPresent() && gplot.get().getNoFire() == 1){
                event.setCancelled(true);   
            }
        }
    }
        
    @Listener
    public void onExplosion(ExplosionEvent.Pre event) {
        Explosion explosion = event.getExplosion();
        Location loc = new Location(event.getTargetWorld(),explosion.getLocation().getBlockPosition());
        Optional<GPlot> gplot = plotManager.getPlot(loc);
        if (gplot.isPresent()){
            if (gplot.get().getNoTNT() == 1){ event.setCancelled(true);}
        }
    }
    
    @Listener
    public void onPVP(DamageEntityEvent event, @First EntityDamageSource source){
        getGame().getServer().getConsole().sendMessage(MESSAGE(source.getSource().getType().getName())); //attaquant
        getGame().getServer().getConsole().sendMessage(MESSAGE(event.getTargetEntity().getType().getName())); //victime
    }
}
