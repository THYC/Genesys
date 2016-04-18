package net.teraoctet.genesys.plot;

import java.util.ArrayList;
import static net.teraoctet.genesys.utils.GData.jails;
import static net.teraoctet.genesys.utils.GData.plots;
import static net.teraoctet.genesys.utils.GData.setts;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.extent.Extent;

public class PlotManager {   
    private Location border1;
    private Location border2;
    private Player player;
    
    public PlotManager() {}
    
    public PlotManager(Player player, Location border1, Location border2){
        this.player = player;
        this.border1 = border1;
        this.border2 = border2;
    }
    
    public static PlotManager getSett(Player player){
        if (!setts.containsKey(player.getName())){
            PlotManager sett = new PlotManager(player, null, null);
            setts.put(player.getName(), sett);
            return sett;
        }
        
        PlotManager sett = (PlotManager)setts.get(player.getName());
        sett.setPlayer(player);
        return sett;
    }

    public void setPlayer(Player player){this.player = player;}
  
    private GPlot plotContainsVector(Location loc, boolean flagJail){
        if (flagJail == true){
            for(GPlot jail : jails){if(foundPlot(loc,jail)){return jail;}}
        }else{
            for(GPlot plot : plots){if(foundPlot(loc,plot)){return plot;}}
        }
        return null;
    }
    
    private boolean foundPlot(Location location, GPlot plot){
        Location <World> world = location;
        
        int X = location.getBlockX();
        int Y = location.getBlockY();
        int Z = location.getBlockZ();
        
        if (plot.getworldName().equalsIgnoreCase(world.getExtent().getName()) == false){return false;}
        else if ((X < plot.getX1()) || (X > plot.getX2())){return false;}
        else if ((Z < plot.getZ1()) || (Z > plot.getZ2())){return false;}
        else if ((Y < plot.getY1()) || (Y > plot.getY2())){return false;}
        return true;
    }
    
    /**
     * Parcelle (Plot) enregistré au point de l'objet Location 
     * avec le Flag Jail = True si la paramètre flaJail = True
     * @param loc Objet Location
     * @param flagJail True pour les parcelles Jails
     * @return La parcelle ou Null si rien trouvé
     */
    public GPlot getPlot(Location loc, boolean flagJail){return plotContainsVector(loc, flagJail);}
    
    /**
     * Parcelle (Plot) enregistré au point de l'objet Location
     * @param loc Objet Location
     * @return La parcelle ou Null si rien trouvé
     */
    public GPlot getPlot(Location loc){return plotContainsVector(loc, false);}
    
    /**
     * Retourne True si le nom indiqué correspond bien a un nom de parcelle(Plot)
     * @param name
     * @return 
     */
    public Boolean hasPlot(String name){return plots.stream().anyMatch((plot) -> (plot.getName().contains(name)));}
    
    /**
     * Retourne True si le nom indiqué correspond a une parcelle enregistré en Jail(Prison)
     * @param name Nom de la parcelle (Plot)
     * @return Boolean
     */
    public Boolean hasJail(String name){return jails.stream().anyMatch((jail) -> (jail.getName().contains(name)));}
    
    /**
     * Retourne la valeur GPlot de la parcelle nommée
     * @param plotName
     * @return 
     */
    public GPlot getPlot(String plotName){
        for (GPlot plot : plots) {
            if(plot.getName().contains(plotName)){return plot;}
        }
        return null;
    }
    
    /**
     * Retourne le propriétaire de la parcelle nommée
     * @param plotName
     * @return 
     */
    public String getPlotOwner(String plotName){
        for(GPlot plot : plots){
            if(plot.getName().contains(plotName)){return plot.getUuidOwner();}
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
    
    /**
     * Si TRUE retourne la list des jails enregistré
     * Si FALSE retourne la liste des parcelles enregistré
     * @param flagJail
     * @return 
     */        
    public Text getListPlot(boolean flagJail){
        String listplot = "&6";
        if (flagJail == true){
            for(GPlot jail : jails){
                listplot = listplot + System.getProperty("line.separator") + jail.getName();
            }
        } else {
            for(GPlot plot : plots){
                listplot = listplot + System.getProperty("line.separator") + plot.getName();
            }
        }
        Text text = Text.builder().append(TextSerializers.formattingCode('&').deserialize(listplot)).toText();
        return text;
    }
    
    /**
     * Retourne la liste des parcelles appartenant au joueur nommé
     * @param playerUUID
     * @return 
     */
    public Text getListPlot(String playerUUID){
        String listplot = "&6Total : " + plots.size();
        for(GPlot plot : plots){
            if(plot.getUuidOwner().equalsIgnoreCase(playerUUID)){
                listplot = listplot + System.getProperty("line.separator") + plot.getName();
            }
        }
        Text text = Text.builder().append(TextSerializers.formattingCode('&').deserialize(listplot)).toText();
        return text;
    }
    
    /**
     * Retourne un Array comprenant les parcelles du joueur
     * @param playerUUID
     * @return 
     */
    public static final ArrayList<GPlot> playerPlots (String playerUUID)
    {
        ArrayList<GPlot> playerPlots = new ArrayList<>();
        plots.stream().filter((plot) -> (plot.getUuidOwner().equalsIgnoreCase(playerUUID))).forEach((plot) -> {playerPlots.add(plot);});
        return playerPlots;        
    }
    
    /**
     * Retourne la validité des coodonnées pour la création d'une parcelle
     * @param l1
     * @param l2
     * @return 
     */
    public boolean plotAllow(Location l1, Location l2){                
        Location <World> w = l1;
        World world = w.getExtent();
        GPlot newPlot = new GPlot(world.getName(),this.border1.getBlockX(),0,l1.getBlockZ(),l2.getBlockX(),500,l2.getBlockZ());
                
        return plots.stream().filter((plot) -> (plot.getworldName().equalsIgnoreCase(newPlot.getworldName()))).anyMatch((plot) -> ( 
                foundPlot(plot.getLocX1Y1Z1(),newPlot) || foundPlot(plot.getLocX2Y2Z2(),newPlot) ||
                foundPlot(plot.getLocX1Y1Z2(),newPlot) || foundPlot(plot.getLocX2Y2Z1(),newPlot) ||
                foundPlot(plot.getLocX1Y2Z2(),newPlot) || foundPlot(plot.getLocX2Y1Z2(),newPlot) ||
                foundPlot(plot.getLocX2Y1Z1(),newPlot) || foundPlot(plot.getLocX1Y2Z1(),newPlot)));
    }
}

