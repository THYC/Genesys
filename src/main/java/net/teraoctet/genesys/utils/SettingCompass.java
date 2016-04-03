package net.teraoctet.genesys.utils;

import com.flowpowered.math.vector.Vector3d;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Optional;
import static net.teraoctet.genesys.utils.MessageManager.MESSAGE;
import static org.spongepowered.api.Sponge.getGame;
import org.spongepowered.api.data.DataTransactionResult;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.mutable.TargetedLocationData;
import org.spongepowered.api.data.manipulator.mutable.item.LoreData;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;

public class SettingCompass {
    
         
        
    public boolean setCompassLocation(Player player, final Vector3d location) {
        Optional<TargetedLocationData> compass = player.getOrCreate(TargetedLocationData.class);
        //if (!compass.isPresent()) {
            
            //return false;
        //}
        getGame().getServer().getConsole().sendMessage(MESSAGE(String.valueOf(compass.get().target()))); //compass.get().target()
        compass.get().set(Keys.TARGETED_LOCATION, location);
        //compass.get().target().set(location);
        
        getGame().getServer().getConsole().sendMessage(MESSAGE(String.valueOf(compass.get().target()))); //compass.get().target()
        return true;
    }
    
    public ItemStack MagicCompass(Player player, String name, String direction){
        ItemStack magicCompass = ItemStack.builder().itemType(ItemTypes.COMPASS).build();
	LoreData loreMCompass = magicCompass.getOrCreate(LoreData.class).get();
        magicCompass.offer(Keys.DISPLAY_NAME, MESSAGE("&4SUPER &5BOUSSOLE DE &9OUF"));
                    
        
	//Text nameLore = TextSerializers.FORMATTING_CODE.deserialize(name);
	List<Text> newLore = loreMCompass.lore().get();
        
	//newLore.add(nameLore);
        newLore.add(MESSAGE("&6Magic Compass"));
        newLore.add(MESSAGE("&8" + player.getName()));
        newLore.add(MESSAGE("&4Direction: HOME"));
	DataTransactionResult dataTransactionResult = magicCompass.offer(Keys.ITEM_LORE, newLore);

	if (dataTransactionResult.isSuccessful()){ 
            return magicCompass;
        } else {
            return null;
        }				
    }
}
