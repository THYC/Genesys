package net.teraoctet.genesys.economy;

import java.util.List;
import java.util.Optional;
import org.spongepowered.api.data.DataTransactionResult;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.mutable.item.LoreData;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import static net.teraoctet.genesys.utils.MessageManager.MESSAGE;
import static net.teraoctet.genesys.utils.MessageManager.SHOP_BUY;
import static net.teraoctet.genesys.utils.MessageManager.SHOP_SALE;

public class ItemShopPlayer {
    
    private final ItemStack itemStack;
    private final String name;
    private String shopType;
    private final double price;
    private final String itemType;
    private final int durability;
    private final int amount;
    private final String enchantment;
        
    public ItemShopPlayer(ItemStack itemStack, String shopType, double price, String itemType){
        this.itemStack = itemStack;
        this.shopType = shopType;
        this.price = price;
        this.itemType = itemType;
        this.name = "&cAV &e[" + this.itemType + "] : &2" + this.price;
        this.durability = 0;
        this.amount = 0;
        this.enchantment = "";
    }
    
    public ItemShopPlayer(ItemStack itemStack, String shopType, double price){
        this.itemStack = itemStack;
        this.shopType = shopType;
        this.price = price;
        this.itemType = itemStack.getItem().getType().getName();
        this.name = "&cAV &e[" + this.itemType + "] : &2" + this.price;
        this.durability = 0;
        this.amount = 0;
        this.enchantment = "";
    }
    
    /**
     * Retourne l'objet ItemStack correspondand à l'ItemShop
     * @return 
     */
    public Optional<ItemStack> getItemShop(){
        
	LoreData loreData = itemStack.getOrCreate(LoreData.class).get();
        itemStack.offer(Keys.DISPLAY_NAME, MESSAGE(name));
                            
	List<Text> newLore = loreData.lore().get();
        newLore.add(MESSAGE("&8" + shopType));
        newLore.add(MESSAGE("&5" + itemType));
        newLore.add(MESSAGE("&6PRICE: " + price));

	DataTransactionResult dataTransactionResult = itemStack.offer(Keys.ITEM_LORE, newLore);
        

	if (dataTransactionResult.isSuccessful()){ 
            return Optional.of(itemStack);
        } else {
            return Optional.empty();
        }				
    }
    
    /**
     * Retourne le prix de l'objet ItemShop
     * @return 
     */
    public Optional<Double> getPrice(){
        LoreData loreData = itemStack.getOrCreate(LoreData.class).get(); 

        if(loreData.get(2).isPresent()){
            Text loreText = loreText = loreData.get(2).get();
            String loreString = loreText.toPlain();
            String lore[] = loreString.split(" ");
            return Optional.of(Double.valueOf(lore[1]));
        } else {       
            return Optional.empty();
        }
    }
    
    /**
     * 
     * @return 
     */
    public Optional<String> getShopType(){
        LoreData loreData = itemStack.getOrCreate(LoreData.class).get(); 

        if(loreData.get(1).isPresent()){
            Text loreText = loreText = loreData.get(1).get();
            if(loreText.equals(SHOP_SALE())){shopType = "SALE";}
            else if(loreText.equals(SHOP_BUY())){shopType = "BUY";}
            return Optional.of(shopType);
        }        
        return Optional.empty();
    }
    
}
