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
import java.util.logging.Level;
import java.util.logging.Logger;
import static net.teraoctet.genesys.Genesys.itemShopManager;
import net.teraoctet.genesys.utils.DeSerialize;
import static net.teraoctet.genesys.utils.MessageManager.MESSAGE;
import static net.teraoctet.genesys.utils.MessageManager.message;
import static org.spongepowered.api.Sponge.getGame;
import org.spongepowered.api.entity.living.ArmorStand;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.NamedCause;
import org.spongepowered.api.item.inventory.Inventory;

import org.spongepowered.api.util.blockray.BlockRay;
import org.spongepowered.api.util.blockray.BlockRayHit;

public class CommandTest implements CommandExecutor {
            
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException {    

        Player player = (Player) src;
        
       Inventory inv = player.getInventory();
       player.openInventory(inv,Cause.of(NamedCause.source(src)));

            //player.sendMessage(MESSAGE("pas assez d'emeraudes"));
                     
        return CommandResult.success();
    }	
}

