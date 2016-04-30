package net.teraoctet.genesys.economy;

import static java.lang.Math.round;
import org.spongepowered.api.item.inventory.ItemStack;

public class ItemShop {
    
    private ItemStack itemStack;
    private String transactType;
    private double price;
    private int qte;
        
    public ItemShop(ItemStack itemStack, String transactType, double price, int qte){
        this.itemStack = itemStack;
        this.transactType = transactType;
        this.price = price;
        this.qte = qte;
    }
           
    public ItemStack getItemStack(){return this.itemStack;}
    public Double getPrice(){return this.price;}
    public int getPriceInt(){
        int priceInt = (int) round(this.price);
        return priceInt;
    }
    public String getTransactType(){return this.transactType;}
    public int getQte(){return this.qte;}
    
    public void setItemStack(ItemStack itemStack){this.itemStack = itemStack;}
    public void setPrice(Double price){this.price = price;}
    public void setTransactType(String transactType){this.transactType = transactType;}
    public void setQte(int qte){this.qte = qte;}
    
}
