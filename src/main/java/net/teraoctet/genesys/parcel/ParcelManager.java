package net.teraoctet.genesys.parcel;

import static net.teraoctet.genesys.utils.GData.jails;
import static net.teraoctet.genesys.utils.GData.parcels;
import static net.teraoctet.genesys.utils.GData.setts;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.extent.Extent;

public class ParcelManager {   
    private Location border1;
    private Location border2;
    private Player player;
    
    public ParcelManager() {}
    
    public ParcelManager(Player player, Location border1, Location border2){
        this.player = player;
        this.border1 = border1;
        this.border2 = border2;
    }
    
    public static ParcelManager getSett(Player player){
        if (!setts.containsKey(player.getName())){
            ParcelManager sett = new ParcelManager(player, null, null);
            setts.put(player.getName(), sett);
            return sett;
        }
        
        ParcelManager sett = (ParcelManager)setts.get(player.getName());
        sett.setPlayer(player);
        return sett;
    }

    public void setPlayer(Player player){this.player = player;}
  
    private GParcel parcelContainsVector(Location loc, boolean flagJail){
        if (flagJail == true){
            for(GParcel jail : jails){if(foundParcelle(loc,jail)){return jail;}}
        }else{
            for(GParcel parcel : parcels){if(foundParcelle(loc,parcel)){return parcel;}}
        }
        return null;
    }
            
    private boolean foundParcelle(Location location, GParcel parcel){
        Location <World> world = location;
        
        int X = location.getBlockX();
        int Y = location.getBlockY();
        int Z = location.getBlockZ();
        
        if (parcel.getworld().equalsIgnoreCase(world.getExtent().getName()) == false){return false;}
        if ((X < parcel.getX1()) || (X > parcel.getX2())){return false;}
        if ((Z < parcel.getZ1()) || (Z > parcel.getZ2())){return false;}
        if ((Y < parcel.getY1()) || (Y > parcel.getY2())){return false;}
        return true;
    }
  
    public GParcel getParcel(Location loc, boolean flagJail){return parcelContainsVector(loc, flagJail);}
    public GParcel getParcel(Location loc){return parcelContainsVector(loc, false);}
    public Boolean hasParcel(String name){for(GParcel parcel : parcels){if(parcel.getName().contains(name)){return true;}}return false;}
    public Boolean hasJail(String name){for(GParcel jail : jails){if(jail.getName().contains(name)){return true;}}return false;}
    
    public GParcel getParcel(String parcelName){
        for(GParcel parcel : parcels){
            if(parcel.getName().contains(parcelName)){return parcel;}
        }
        return null;
    }
    
    public String getParcelOwner(String parcelName){
        for(GParcel parcel : parcels){
            if(parcel.getName().contains(parcelName)){return parcel.getUuidOwner();}
        }
        return null;
    }
                
    public void setBorder(int a, Location b){
        if (a == 1){
            this.border1 = b;
        } else if (a == 2) {
            this.border2 = b;
        }
    }
    
    public Location getBorder1(){
        if ((this.border1 != null) && (this.border2 != null)){
            int x0 = this.border1.getBlockX();
            int y0 = this.border1.getBlockY();
            int z0 = this.border1.getBlockZ();
            int x1 = this.border2.getBlockX();
            int y1 = this.border2.getBlockY();
            int z1 = this.border2.getBlockZ();
            
            Extent extent = this.border1.getExtent();
            
            return new Location(extent, Math.min(x0, x1), Math.min(y0, y1), Math.min(z0, z1));
        }
        return this.border1;
    }
  
    public Location getBorder2()
    {
        if ((this.border1 != null) && (this.border2 != null))
        {
            int x0 = this.border1.getBlockX();
            int y0 = this.border1.getBlockY();
            int z0 = this.border1.getBlockZ();
            int x1 = this.border2.getBlockX();
            int y1 = this.border2.getBlockY();
            int z1 = this.border2.getBlockZ();
            
            Extent extent = this.border1.getExtent();
            
          return new Location(extent, Math.max(x0, x1), Math.max(y0, y1), Math.max(z0, z1));
        }
        return this.border2;
    }
            
    public Text getListParcel(boolean flagJail){
        String listparcel = "";
        if (flagJail == true){
            for(GParcel jail : jails){
                listparcel = listparcel + System.getProperty("line.separator") + jail.getName();
            }
        } else {
            for(GParcel parcel : parcels){
                listparcel = listparcel + System.getProperty("line.separator") + parcel.getName();
            }
        }
        Text text = Text.builder(listparcel).color(TextColors.GOLD).build();
        return text;
    }
    
    public Text getListParcel(String playerUUID){
        String listparcel = "";
        for(GParcel parcel : parcels){
            if(parcel.getUuidOwner().equalsIgnoreCase(playerUUID)){
                listparcel = listparcel + System.getProperty("line.separator") + parcel.getName();
            }
        }
        
        Text text = Text.builder(listparcel).color(TextColors.GOLD).build();
        return text;
    }
    
    public boolean parcelAllow(Location l1, Location l2){                
        Location <World> w = l1;
        World world = w.getExtent();
        GParcel newParcel = new GParcel(world.getName(),this.border1.getBlockX(),0,l1.getBlockZ(),l2.getBlockX(),500,l2.getBlockZ());
                
        for(GParcel parcel : parcels){            
            if(parcel.getworld().equalsIgnoreCase(newParcel.getworld())){
                if( foundParcelle(parcel.getLocation1(),newParcel) || foundParcelle(parcel.getLocation2(),newParcel) || 
                    foundParcelle(parcel.getLocation3(),newParcel) || foundParcelle(parcel.getLocation4(),newParcel) || 
                    foundParcelle(parcel.getLocation5(),newParcel) || foundParcelle(parcel.getLocation6(),newParcel) ||
                    foundParcelle(parcel.getLocation7(),newParcel) || foundParcelle(parcel.getLocation8(),newParcel)){
                    return true;
                }
            }
        }
        return false;
    }
}

