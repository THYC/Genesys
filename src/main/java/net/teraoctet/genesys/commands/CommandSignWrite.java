package net.teraoctet.genesys.commands;

import java.util.Optional;
import static net.teraoctet.genesys.Genesys.plotManager;
import net.teraoctet.genesys.player.GPlayer;
import net.teraoctet.genesys.plot.GPlot;
import static net.teraoctet.genesys.utils.GData.getGPlayer;
import static net.teraoctet.genesys.utils.MessageManager.MESSAGE;
import static net.teraoctet.genesys.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.genesys.utils.MessageManager.NO_PERMISSIONS;
import static net.teraoctet.genesys.utils.MessageManager.USAGE;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.block.tileentity.Sign;
import org.spongepowered.api.block.tileentity.TileEntity;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.data.manipulator.mutable.tileentity.SignData;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.util.blockray.BlockRay;
import org.spongepowered.api.util.blockray.BlockRayHit;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import static net.teraoctet.genesys.utils.MessageManager.MESSAGE;
import static net.teraoctet.genesys.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.genesys.utils.MessageManager.NO_PERMISSIONS;

public class CommandSignWrite implements CommandExecutor {
        
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {

        if(src instanceof Player && src.hasPermission("genesys.sign.write")) {
            Player player = (Player) src;
            GPlayer gplayer = getGPlayer(player.getUniqueId().toString());
        
            if(!ctx.getOne("line").isPresent()){
                player.sendMessage(USAGE("/write <line> <message>"));
                return CommandResult.empty();  
            }
            
            Location location = null;

            BlockRay<World> playerBlockRay = BlockRay.from(player).blockLimit(10).build(); 
            while (playerBlockRay.hasNext()){ 
                BlockRayHit<World> currentHitRay = playerBlockRay.next(); 

                if (player.getWorld().getBlockType(currentHitRay.getBlockPosition()).equals(BlockTypes.WALL_SIGN) || 
                        player.getWorld().getBlockType(currentHitRay.getBlockPosition()).equals(BlockTypes.STANDING_SIGN)){ 
                    location = currentHitRay.getLocation(); 
                    break;
                }                     
            } 

            GPlot gplot = plotManager.getPlot(location);
            if (gplot != null){ 
                if(!gplot.getUuidOwner().equalsIgnoreCase(player.getIdentifier())){
                    src.sendMessage(NO_PERMISSIONS());
                    return CommandResult.empty(); 
                }
            }
            
            int line = ctx.<Integer> getOne("line").get();
            String message = ctx.<String> getOne("message").get();
                        
            String[] args = message.split(" ");
            String msg = "";
            for (String arg : args) {
                msg = msg + arg + " ";
            }
            Text messageText = MESSAGE(msg);
            
            Optional<TileEntity> signBlock = location.getTileEntity();
            TileEntity tileSign = signBlock.get();
            Sign sign=(Sign)tileSign;
            Optional<SignData> opSign = sign.getOrCreate(SignData.class);
            SignData signData = opSign.get();
            signData.setElement(line, messageText);
            sign.offer(signData);
            return CommandResult.success();
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
