package net.teraoctet.genesys.commands;

import com.flowpowered.math.vector.Vector3d;
import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;
import static javax.sql.rowset.spi.SyncFactory.getLogger;
import net.teraoctet.genesys.economy.ItemShop;
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
import org.spongepowered.api.data.manipulator.mutable.entity.RespawnLocationData;
import org.spongepowered.api.data.value.mutable.MapValue;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.gamemode.GameMode;
import static org.spongepowered.api.entity.living.player.gamemode.GameModes.CREATIVE;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.service.permission.SubjectData;
import org.spongepowered.api.util.RespawnLocation;
import org.spongepowered.api.util.Tristate;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.WorldCreationSettings;
import org.spongepowered.api.world.extent.Extent;
import org.spongepowered.api.world.storage.WorldProperties;
import java.util.Optional;
import javax.annotation.concurrent.Immutable;
import static net.teraoctet.genesys.Genesys.bookManager;
import org.spongepowered.api.text.BookView;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import static net.teraoctet.genesys.utils.MessageManager.HOME_NOT_FOUND;
import static net.teraoctet.genesys.utils.MessageManager.MESSAGE;
import static net.teraoctet.genesys.utils.MessageManager.NO_PERMISSIONS;
import static net.teraoctet.genesys.utils.MessageManager.ONHOVER_FACTION_INVIT;
import static org.spongepowered.api.command.args.GenericArguments.entity;
import org.spongepowered.api.data.manipulator.mutable.entity.PassengerData;
import org.spongepowered.api.entity.living.ArmorStand;
import static org.spongepowered.api.item.ItemTypes.DIAMOND_SWORD;
import org.spongepowered.api.text.action.TextActions;

public class CommandTest implements CommandExecutor {
            
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException {    

        

        /*final Optional<RespawnLocationData> optPlayerRLD = player.getOrCreate(RespawnLocationData.class);
        if (!optPlayerRLD.isPresent()) {
            return CommandResult.empty();
        }
        
        
        
        final World currentWorld = player.getWorld();
        final UUID worldUUID = currentWorld.getUniqueId();
        final MapValue<UUID, RespawnLocation> respawnLocations = optPlayerRLD.get().respawnLocation();

        for(Map.Entry<UUID, RespawnLocation> entry : respawnLocations.entrySet()) {
            if (entry.getKey().equals(worldUUID)) {
                boolean changed = sc.setCompassLocation(player, d);
            }
        }*/
        /*Player player = (Player) src;	
        ItemStack is = player.getItemInHand().get();
        player.sendMessage(MESSAGE(is.getItem().getName()));
        player.sendMessage(MESSAGE(String.valueOf(is.getItem().getTemplate().getContentVersion())));
        player.sendMessage(MESSAGE(is.getItem().getType().getName()));
        Logger.getLogger("INFO").info(is.getKeys().toString());
                    Logger.getLogger("INFO").info(is.getValue(Keys.ITEM_BLOCKSTATE).get().toString());
                    Logger.getLogger("INFO").info(is.getValue(Keys.ITEM_BLOCKSTATE).toString());*/
        //BookMessage book = new BookMessage("Test2",MESSAGE("Genesys"),"1",MESSAGE("ttttttttt"));
        
        
        
        
        
        
        /*
        Player player = (Player) src;	
        BookView.Builder helpBook = BookView.builder()
                .author(Text.of(TextColors.DARK_BLUE, "Genesys"))
                .title(Text.of(TextColors.GOLD, "Help Book"));
		Text helpCommand = Text.builder()
		.append(Text.of(TextColors.DARK_AQUA, "Command: ", TextColors.GOLD, "test", "\n"))
                .append(MESSAGE("&2+ &aAjouter un membre"))
                                        .onClick(TextActions.suggestCommand("/faction invit "))    
                                        .onHover(TextActions.showText(ONHOVER_FACTION_INVIT()))
		.append(Text.of(TextColors.DARK_AQUA, "Info: ", TextColors.GOLD, "test", "\n"))
		.append(Text.of(TextColors.DARK_AQUA, "Usage: ", TextColors.GOLD, "/", "test", " ", "test", "\n"))
		.append(Text.of(TextColors.DARK_AQUA, "Permission: ", TextColors.GOLD, "test", "test", "\n")).color(TextColors.DARK_AQUA)
		.append(Text.of(TextColors.DARK_AQUA, "Access: ", TextColors.GOLD, "test", " \n")).color(TextColors.DARK_AQUA)
		.build();
		helpBook.addPage(helpCommand);
			
			player.sendBookView(helpBook.build());*/
        
        //summon ArmorStand ~ ~ ~ {Invulnerable:1b,NoBasePlate:1b,ShowArms:1b,Small:1b,Rotation:[145f],Equipment:[{id:"diamond_sword",Count:1b},{},{},{},{}],DisabledSlots:0,Pose:{LeftLeg:[19f,0f,0f],LeftArm:[138f,45f,0f],RightArm:[20f,0f,0f]}}
      
 
    
        //BookView.Builder helpBook = BookView.builder().author(Text.of(TextColors.DARK_BLUE, "LandProtect")).title(Text.of(TextColors.GOLD, "LandProtect Help Book"));
        //Text helpCommand = MESSAGE("&3TEST BOOK");
	//helpBook.addPage(helpCommand);
        //helpBook.build();
		
	//player.sendBookView(helpBook.build());
        
        return CommandResult.success();
    }	
}

