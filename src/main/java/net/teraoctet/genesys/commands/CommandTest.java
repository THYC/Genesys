package net.teraoctet.genesys.commands;

import com.flowpowered.math.vector.Vector3d;
import java.io.IOException;

import java.util.Collection;
import java.util.Iterator;
import net.teraoctet.genesys.economy.ItemShop;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;

import org.spongepowered.api.entity.Entity;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import static net.teraoctet.genesys.Genesys.itemShopManager;
import net.teraoctet.genesys.player.GPlayer;
import net.teraoctet.genesys.utils.DeSerialize;
import static net.teraoctet.genesys.utils.GData.getGPlayer;
import static net.teraoctet.genesys.utils.MessageManager.MESSAGE;
import static net.teraoctet.genesys.utils.MessageManager.message;
import static org.spongepowered.api.Sponge.getGame;
import org.spongepowered.api.command.args.CommandElement;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.mutable.entity.PassengerData;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.living.ArmorStand;
import org.spongepowered.api.entity.living.Bat;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.NamedCause;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;

import org.spongepowered.api.util.blockray.BlockRay;
import org.spongepowered.api.util.blockray.BlockRayHit;

public class CommandTest implements CommandExecutor {
            
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {    
 
        Player player = (Player) src;
        
        Text t = Text.builder().append(MESSAGE("&2+ &aAjouter un membre")).onClick(TextActions.executeCallback(CommandBank(player))).build();
        player.sendMessages(t);
        return CommandResult.success();
    }	

    private Consumer<CommandSource> CommandBank(Player player) {
	return (CommandSource src) -> {
            GPlayer gp = getGPlayer(player.getIdentifier());
            player.sendMessage(MESSAGE(String.valueOf(gp.getMoney())));
            src.sendMessage(MESSAGE("PROUuuuT ! pardon .."));
            //pages.sendTo(src);
           
        };
    }

}
