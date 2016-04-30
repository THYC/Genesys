package net.teraoctet.genesys.economy;

import java.io.File;
import java.io.IOException;
import static java.lang.Math.round;
import java.util.List;
import java.util.Optional;
import net.teraoctet.genesys.utils.DeSerialize;
import static net.teraoctet.genesys.utils.MessageManager.MESSAGE;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.DataTransactionResult;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.mutable.item.LoreData;
import org.spongepowered.api.data.translator.ConfigurateTranslator;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;

public class ItemShopManager {
    
    public static File file = new File("config/genesys/itemShop.conf");
    public static final ConfigurationLoader<?> manager = HoconConfigurationLoader.builder().setFile(file).build();
    public static ConfigurationNode shop = manager.createEmptyNode(ConfigurationOptions.defaults());
    
    public static void init() {
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            shop = manager.load();
        } catch (IOException e) {}	
    }
    
    public static Object serializeItemStack(ItemStack itemStack){
        ConfigurationNode node = ConfigurateTranslator.instance().translateData(itemStack.toContainer());
        return node.getValue();
    }

    public static Optional<ItemStack> getItemStack(ConfigurationNode node){
        DataView view = ConfigurateTranslator.instance().translateFrom(node);
        view = (DataView) view.get(DataQuery.of(String.valueOf("item"))).get();
        return Sponge.getDataManager().deserialize(ItemStack.class, view);
    }
    
    /**
     * Sauvegarde un ItemShop
     * @param location location objet Location
     * @param itemShop Object ItemShop
     * @return Boolean
     * @throws IOException Exception
     */
    public boolean saveShop(Location location, ItemShop itemShop) throws IOException{
        shop.getNode(DeSerialize.location(location),"item").setValue(serializeItemStack(itemShop.getItemStack()));
        shop.getNode(DeSerialize.location(location),"transacttype").setValue(itemShop.getTransactType());
        shop.getNode(DeSerialize.location(location),"price").setValue(itemShop.getPrice());
        shop.getNode(DeSerialize.location(location),"qte").setValue(itemShop.getQte());
        manager.save(shop);
        return true;
    }
    
    /**
     * Sauvegarde un ItemShop
     * @param location objet Location de type String au format: UUID:X:Y:Z
     * @param itemShop object ItemShop à sauvegarder
     * @return Boolean 
     * @throws IOException Exception
     */
    public boolean saveShop(String location, ItemShop itemShop) throws IOException{
        shop.getNode(location,"item").setValue(serializeItemStack(itemShop.getItemStack()));
        shop.getNode(location,"transacttype").setValue(itemShop.getTransactType());
        shop.getNode(location,"price").setValue(itemShop.getPrice());
        shop.getNode(location,"qte").setValue(itemShop.getQte());
        manager.save(shop);
        return true;
    }
    
    /**
     * Retourne un ItemShop 
     * @param location objet Location
     * @return ItemShop
     */    
    public Optional<ItemShop> getItemShop(Location location){
	ConfigurationNode node = shop.getNode(DeSerialize.location(location));
	Optional<ItemStack> optItemStack = getItemStack(node);
        if(optItemStack.isPresent()){
            String transactType = node.getNode("transacttype").getString();
            Double price = node.getNode("price").getDouble();
            int qte = node.getNode("qte").getInt();
            ItemShop is = new ItemShop(optItemStack.get(),transactType,price,qte);
            return Optional.of(is);
        }
        return Optional.empty(); 
    }
    
    /**
     * Retourne un ItemShop 
     * @param location objet Location de type String au format: UUID:X:Y:Z
     * @return ItemShop
     */
    public Optional<ItemShop> getItemShop(String location){
	ConfigurationNode node = shop.getNode(location);
	Optional<ItemStack> optItemStack = getItemStack(node);
        if(optItemStack.isPresent()){
            String transactType = node.getNode("transacttype").getString();
            Double price = node.getNode("price").getDouble();
            int qte = node.getNode("qte").getInt();
            ItemShop is = new ItemShop(optItemStack.get(),transactType,price,qte);
            return Optional.of(is);
        }
        return Optional.empty(); 
    }
    
    /**
     * Supprime un object ItemShop
     * @param location Object Location position de l'object ItemShop
     * @throws IOException Exception
     */
    public void delItemShop(Location location) throws IOException{
        String locationString = DeSerialize.location(location);
	ConfigurationNode node = shop.getNode(locationString);
	shop.removeChild(locationString);
        manager.save(shop);
        shop = manager.load();
    }
    
    /**
     * Retourne TRUE si l'Objet Location contient un ItemShop
     * @param location Location du block
     * @return Boolean
     */
    public boolean hasShop(Location location){
        return !shop.getNode(DeSerialize.location(location)).isVirtual();
    }
    
    /**
     * Retourne TRUE si l'Objet ItemStack est un CoinPurses
     * @param coinPurses Objet ItemStack
     * @return Boolean
     */
    public boolean hasCoinPurses(ItemStack coinPurses){
        if(coinPurses.get(Keys.DISPLAY_NAME).isPresent()){
            return coinPurses.get(Keys.DISPLAY_NAME).get().toPlain().equalsIgnoreCase("CoinPurses");	
        }
        return false;
    }
    
    /**
     * Retourne un object ItemStack CoinPurses
     * @param player player recevant l'object ItemStack CoinPurses
     * @param credit Somme a crediter
     * @return un object ItemStack CoinPurses
     */
    public Optional<ItemStack> CoinPurses(Player player, double credit){
        ItemStack coinPurses = ItemStack.builder().itemType(ItemTypes.PAPER).build();
	LoreData loreD = coinPurses.getOrCreate(LoreData.class).get();
        coinPurses.offer(Keys.DISPLAY_NAME, MESSAGE("&eCoinPurses"));
                 
	List<Text> newLore = loreD.lore().get();
        newLore.add(MESSAGE("&2Owner: &e" + player.getName()));
        newLore.add(MESSAGE("&2Credit: &b" + credit));
	DataTransactionResult dataTransactionResult = coinPurses.offer(Keys.ITEM_LORE, newLore);

	if (dataTransactionResult.isSuccessful()){ 
            return Optional.of(coinPurses);
        } else {
            return Optional.empty();
        }				
    }
    
    /**
     * Retourne l'objet CoinPurses crédité de la somme
     * @param credit Somme type Double a crédité sur l'objet CoinPurses
     * @param coinPurses Object ItemStack CoinPurses a crediter
     * @return ItemStack CoinPurses
     */
    public Optional<ItemStack> addCoin(double credit, ItemStack coinPurses){
        //GPlayer gplayer = getGPlayer(player.getIdentifier());
	LoreData loreD = coinPurses.getOrCreate(LoreData.class).get();                
	List<Text> lore = loreD.lore().get();
        String[] arg = lore.get(1).toPlain().split(":");
        Double coin = Double.valueOf(arg[1]);
        coin = coin + credit;
        lore.set(1, MESSAGE("&2Credit: &b" + coin));
        
	DataTransactionResult dataTransactionResult = coinPurses.offer(Keys.ITEM_LORE, lore);
        if(dataTransactionResult.isSuccessful()){
            return Optional.of(coinPurses);
        }
        return Optional.empty();				
    }
    
    /**
     * Retourne l'objet CoinPurses débité de la somme
     * @param debit Somme type Double a debité de l'objet CoinPurses
     * @param coinPurses Object ItemStack CoinPurses a debiter
     * @return ItemStack CoinPurses
     */
    public Optional<ItemStack> removeCoin(double debit, ItemStack coinPurses){
	LoreData loreD = coinPurses.getOrCreate(LoreData.class).get();                
	List<Text> lore = loreD.lore().get();
        String[] arg = lore.get(1).toPlain().split(":");
        Double coin = Double.valueOf(arg[1]);
        if(debit <= coin){
            coin = (round((coin - debit)*100.0))/100.0;
            lore.set(1, MESSAGE("&2Credit: &b" + coin));
            DataTransactionResult dataTransactionResult = coinPurses.offer(Keys.ITEM_LORE, lore);
            if(dataTransactionResult.isSuccessful()){
                return Optional.of(coinPurses);
            }
        }
        return Optional.empty();	
    }
}
