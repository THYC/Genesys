package net.teraoctet.genesys.commands;

import com.flowpowered.math.vector.Vector3d;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import net.teraoctet.genesys.player.GPlayer;
import static net.teraoctet.genesys.plot.PlotManager.playerPlots;
import static net.teraoctet.genesys.utils.GData.getGPlayer;
import net.teraoctet.genesys.utils.GHome;
import static net.teraoctet.genesys.utils.MessageManager.HOME_NOT_FOUND;
import static net.teraoctet.genesys.utils.MessageManager.MESSAGE;
import static net.teraoctet.genesys.utils.MessageManager.NO_PERMISSIONS;
import net.teraoctet.genesys.utils.Permissions;
import net.teraoctet.genesys.utils.SettingCompass;
import static org.spongepowered.api.Sponge.getGame;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.gamemode.GameMode;
import static org.spongepowered.api.entity.living.player.gamemode.GameModes.CREATIVE;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.service.permission.SubjectData;
import org.spongepowered.api.util.Tristate;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.WorldCreationSettings;
import org.spongepowered.api.world.extent.Extent;
import org.spongepowered.api.world.storage.WorldProperties;

public class CommandTest implements CommandExecutor {
            
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException {    

        Player player = (Player) src;
        SettingCompass sc = new SettingCompass();
        ItemStack is = sc.MagicCompass(player,"&eHOME","");
        player.setItemInHand(is);
        
        GPlayer gplayer = getGPlayer(player.getUniqueId().toString());
        String homename = "default"; 
        GHome ghome = gplayer.getHome(homename);
            if(ghome == null) { 
                src.sendMessage(HOME_NOT_FOUND()); 
                return CommandResult.empty();
            }
        Vector3d d = new Vector3d(
                ghome.getX()- player.getLocation().getBlockX(),
                ghome.getY()- player.getLocation().getBlockY(), 
                ghome.getZ()- player.getLocation().getBlockZ());
        sc.setCompassLocation(player, d);
                    
        return CommandResult.success();
    }	
}

