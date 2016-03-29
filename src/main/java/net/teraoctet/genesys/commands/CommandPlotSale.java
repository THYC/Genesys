package net.teraoctet.genesys.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static net.teraoctet.genesys.Genesys.plotManager;
import net.teraoctet.genesys.plot.GPlot;
import net.teraoctet.genesys.utils.GData;
import static net.teraoctet.genesys.utils.GData.getGPlayer;
import static net.teraoctet.genesys.utils.MessageManager.USAGE;
import net.teraoctet.genesys.player.GPlayer;
import org.spongepowered.api.block.BlockTypes;
import static org.spongepowered.api.block.BlockTypes.STANDING_SIGN;
import org.spongepowered.api.block.tileentity.Sign;
import org.spongepowered.api.block.tileentity.TileEntity;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.mutable.tileentity.SignData;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.chat.ChatTypes;
import org.spongepowered.api.util.blockray.BlockRay;
import org.spongepowered.api.util.blockray.BlockRayHit;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import static net.teraoctet.genesys.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.genesys.utils.MessageManager.NO_PERMISSIONS;
import static net.teraoctet.genesys.utils.MessageManager.ALREADY_OWNED_PLOT;
import static net.teraoctet.genesys.utils.MessageManager.MESSAGE;

public class CommandPlotSale implements CommandExecutor {
       
    @Override
    @SuppressWarnings("UnusedAssignment")
    public CommandResult execute(CommandSource sender, CommandContext ctx) throws CommandException {

        Player player = (Player) sender;
        GPlayer gplayer = getGPlayer(player.getUniqueId().toString());

        if(!player.hasPermission("genesys.plot.sale")) { 
                sender.sendMessage(NO_PERMISSIONS()); 
                return CommandResult.success(); 
        }
                   
        if(sender instanceof Player == false) { 
            sender.sendMessage(NO_CONSOLE()); 
            return CommandResult.success(); 
        }
        
        if(!ctx.getOne("price").isPresent()){
            player.sendMessage(ChatTypes.CHAT,USAGE("/plot sale <price>"));
            player.sendMessage(ChatTypes.CHAT,USAGE("/plot sale <price> [plotName]"));
            return CommandResult.success();  
        }

        GPlot gplot = null;
        
        if(ctx.getOne("name").isPresent()){
            String plotName = ctx.<String> getOne("name").get();
            if (plotManager.hasPlot(plotName)){  
                gplot = plotManager.getPlot(plotName); 
            } else {
                player.sendMessage(ChatTypes.CHAT,MESSAGE("&7parcelle &e" + plotName + " &7introuvable"));
                player.sendMessage(ChatTypes.CHAT,USAGE("/plot sale <price> [plotName]"));
                return CommandResult.success();  
            }       
        } else {
            gplot = plotManager.getPlot(player.getLocation());
            if (gplot == null){
                player.sendMessage(ChatTypes.CHAT,MESSAGE("&7vous devez \352tre sur la parcelle ou renseigner le nom de parcelle"));
                player.sendMessage(ChatTypes.CHAT,USAGE("/plot sale <price> [plotName]"));
                return CommandResult.success();  
            }
        }

        if (!gplot.getUuidOwner().equalsIgnoreCase(player.getUniqueId().toString()) && gplayer.getLevel() != 10){
            player.sendMessage(ChatTypes.CHAT,ALREADY_OWNED_PLOT());
            return CommandResult.success();
        }
        
        Location location = null;
        
        BlockRay<World> playerBlockRay = BlockRay.from(player).blockLimit(10).build(); 
        while (playerBlockRay.hasNext()) 
        { 
            BlockRayHit<World> currentHitRay = playerBlockRay.next(); 

            if (player.getWorld().getBlockType(currentHitRay.getBlockPosition()).equals(BlockTypes.WALL_SIGN) || 
                    player.getWorld().getBlockType(currentHitRay.getBlockPosition()).equals(BlockTypes.STANDING_SIGN)) 
            { 
                location = currentHitRay.getLocation(); 
                break;
            }                     
        } 

        if (location == null){
            location = player.getLocation();
            location.setBlockType(STANDING_SIGN);  
        }
        
        Optional<TileEntity> signBlock = location.getTileEntity();
        TileEntity tileSign = signBlock.get();
        Sign sign=(Sign)tileSign;
        Optional<SignData> opSign = sign.getOrCreate(SignData.class);
        Integer price = ctx.<Integer> getOne("price").get();
        
        SignData signData = opSign.get();
        List<Text> sale = new ArrayList<>();
        sale.add(MESSAGE("&1PARCELLE A VENDRE"));
        sale.add(MESSAGE("&1" + gplot.getName()));
        sale.add(MESSAGE("&4" + String.valueOf(price)));
        sale.add(MESSAGE("&1Emeraudes"));
        signData.set(Keys.SIGN_LINES,sale );
        sign.offer(signData);
        
        gplot.addSale(location);
        GData.commit();
        return CommandResult.success();	
    }   
}