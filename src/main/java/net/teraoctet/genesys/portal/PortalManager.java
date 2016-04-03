package net.teraoctet.genesys.portal;

import static net.teraoctet.genesys.utils.GData.gportals;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

public class PortalManager {          
        
    public PortalManager(){}
    
    private GPortal portalContainsVector(Location loc){
        for (GPortal portal : gportals) {
            if(foundPortal(loc,portal)){return portal;}
        }
        return null;
    }
    
    public Boolean hasPortal(String name){
        return gportals.stream().anyMatch((gportal) -> (gportal.getName().contains(name)));
    }
            
    private boolean foundPortal(Location location, GPortal portal){
        Location <World> world = location;
        
        int X = location.getBlockX();
        int Y = location.getBlockY();
        int Z = location.getBlockZ();
        
        if (portal.getworld().equalsIgnoreCase(world.getExtent().getName()) == false){return false;}
        if ((X < portal.getX1()) || (X > portal.getX2())){return false;}
        if ((Z < portal.getZ1()) || (Z > portal.getZ2())){return false;}
        if ((Y < portal.getY1()) || (Y > portal.getY2())){return false;}
        return true;
    }
  
    public GPortal getPortal(Location loc){return portalContainsVector(loc);}
        
    public GPortal getPortal(String portalName){
        for(GPortal portal : gportals){
            if(portal.getName().contains(portalName)){return portal;}
        }
        return null;
    }
                    
    public Text listPortal(){
        String listportal = "";
        for(GPortal portal : gportals){
            listportal = listportal + System.getProperty("line.separator") + portal.getName();
        }
        
        Text text = Text.builder(listportal).color(TextColors.GOLD).build();
        return text;
    }
}

