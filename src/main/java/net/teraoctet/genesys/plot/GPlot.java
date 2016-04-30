package net.teraoctet.genesys.plot;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import net.teraoctet.genesys.utils.GData;
import static net.teraoctet.genesys.utils.GData.datasource;
import static net.teraoctet.genesys.utils.GData.getGPlayer;
import net.teraoctet.genesys.utils.DeSerialize;
import net.teraoctet.genesys.utils.GConfig;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.block.tileentity.Sign;
import org.spongepowered.api.block.tileentity.TileEntity;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.mutable.tileentity.SignData;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import static net.teraoctet.genesys.utils.MessageManager.MESSAGE;
import static org.spongepowered.api.Sponge.getGame;
import static org.spongepowered.api.block.BlockTypes.AIR;

public class GPlot {
    
    private String plotName;
    private int level;
    private String world;
    private int x1;
    private int y1;
    private int z1;
    private int x2;
    private int y2;
    private int z2;
    private int jail;
    private int noEnter;
    private int noFly;
    private int noBuild;
    private int noBreak;
    private int noTeleport;
    private int noInteract;
    private int noFire;
    private String message;
    private int mode;
    private int noMob;
    private int noTNT;
    private int noCommand;
    private String uuidOwner;
    private String uuidAllowed;
    
    public GPlot(String plotName, int level, String world, int x1, int y1, int z1, int x2, int y2, int z2, 
    int jail, int noEnter, int noFly, int noBuild, int noBreak, int noTeleport, int noInteract, int noFire, 
    String message, int mode, int noMob, int noTNT, int noCommand, String uuidOwner, String uuidAllowed){
        
        this.plotName = plotName;
        this.level = level;
        this.world = world;
        this.x1 = x1;
        this.y1 = y1;
        this.z1 = z1;
        this.x2 = x2;
        this.y2 = y2;
        this.z2 = z2;
        this.jail = jail;
        this.noEnter = noEnter;
        this.noFly = noFly;
        this.noBuild = noBuild;
        this.noBreak = noBreak;
        this.noTeleport = noTeleport;
        this.noInteract = noInteract;
        this.noFire = noFire;
        this.message = message;
        this.mode = mode;
        this.uuidOwner = uuidOwner;
        this.uuidAllowed = uuidAllowed;
        this.noMob = noMob;
        this.noTNT = noTNT;
        this.noCommand = noCommand;
    }
    
    public GPlot(String world, int x1, int y1, int z1, int x2, int y2, int z2){
        this.plotName = "TMP";
        this.level = 0;
        this.world = world;
        this.x1 = x1;
        this.y1 = y1;
        this.z1 = z1;
        this.x2 = x2;
        this.y2 = y2;
        this.z2 = z2;
        this.jail = 0;
        this.noEnter = 0;
        this.noFly = 0;
        this.noBuild = 0;
        this.noBreak = 0;
        this.noTeleport = 0;
        this.noInteract = 0;
        this.noFire = 0;
        this.message = "";
        this.mode = 0;
        this.uuidOwner = "";
        this.uuidAllowed = "";
        this.noMob = 0;
        this.noTNT = 0;
        this.noCommand = 1;
    }
        
    public void insert() {
	GData.queue("INSERT INTO gplot VALUES ('" + plotName + "', " + level + ", '" + world + "', " + x1 + ", " + y1 + ", " + z1
        + ", " + x2 + ", " + y2 + ", " + z2 + ", " + jail + ", " + noEnter + ", " + noFly + ", " + noBuild + ", " + noBreak + ", " + noTeleport 
        + ", " + noInteract + ", " + noFire + ", '" + message + "', " + mode + ", " + noMob + ", " + noTNT + ", " + noCommand + ", '" + uuidOwner + "', '" + uuidAllowed + "')");
    }
    
    /**
     * Enregistre une annonce de vente de la parcelle (Plot)
     * dans la base de donnée.
     * @param loc Location  
     */
    public void addSale(Location loc) {
        String location = DeSerialize.location(loc);
	GData.queue("INSERT INTO gplsale VALUES ('" + plotName + "', '" + location + "')");
    }
    
    /**
     * Supprime l'annonce de vente de la parcelle (Plot) 
     * ainsi que tous les panneaux (Sign) associés si :
     * le paramètre DEL_SIGN_AFTER_SALE = true
     * sinon : supprime le texte uniquement.
     */
    public void delSale() {
        try {
            Connection c = datasource.getConnection();
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery("SELECT * FROM gplsale WHERE plotName = '" + plotName + "'");
            while(rs.next()) {
                Location loc = DeSerialize.getLocation(rs.getString("location"));
                if (loc.getBlockType().equals(BlockTypes.STANDING_SIGN) || loc.getBlockType().equals(BlockTypes.WALL_SIGN)){
                    if(GConfig.DEL_SIGN_AFTER_SALE()){
                        loc.setBlockType(AIR);
                    } else {
                        Optional<TileEntity> signBlock = loc.getTileEntity();
                        TileEntity tileSign = signBlock.get();
                        Sign sign=(Sign)tileSign;
                        Optional<SignData> opSign = sign.getOrCreate(SignData.class);

                        SignData signData = opSign.get();
                        List<Text> sale = new ArrayList<>();
                        sale.add(MESSAGE("&1VENDU"));
                        sale.add(MESSAGE(""));
                        sale.add(MESSAGE(""));
                        sale.add(MESSAGE(""));
                        signData.set(Keys.SIGN_LINES,sale );
                        sign.offer(signData);
                    }
                } 
            }
            s.close();
            c.close();
        } catch (SQLException e) {}
	GData.queue("DELETE FROM gplsale WHERE plotName = '" + plotName + "'");
    }
    
	
    public void update() {
	GData.queue("UPDATE gplot SET plotName = '" + plotName + "', level = " + level + ", world = '" + world 
        + "', X1 = " + x1 + ", Y1 = " + y1 + ", Z1 = " + z1 + ", X2 = " + x2 + ", Y2 = " + y2 + ", Z2 = " + z2 + ", jail = " + jail 
        + ", noEnter = " + noEnter + ", noFly = " + noFly + ", noBuild = " + noBuild + ", noBreak = " + noBreak + ", noTeleport = " + noTeleport 
        + ", noInteract = " + noInteract + ", noFire = " + noFire + ", message = '" + message + "', mode = " + mode + ", uuidOwner = '" + uuidOwner 
        + "', uuidAllowed = '" + uuidAllowed + "', noMob = " + noMob + ", noTNT = " + noTNT + ", noCommand = " + noTNT + " WHERE plotName = '" + plotName + "'");
    }
	
    public void delete() {
	GData.queue("DELETE FROM gplot WHERE plotName = '" + plotName + "'");
    }
    
    /**
     * Chaine type String comprenant la liste des Flags et de leurs valeurs
     * enregistré sur la parcelle (Plot)
     * @return String
     */
    public String getFlag(){
        String flag = "Jail(prison) : " + this.jail + " | ";
        flag = flag + "noFly(vol) : " + this.noFly + " | ";
        flag = flag + "noEnter(entrée) : " + this.noEnter + " | ";
        flag = flag + "noBuild(construire) : " + this.noBuild + " | ";
        flag = flag + "noBreak(casser) : " + this.noBreak + " | ";
        flag = flag + "notp(téléportation) : " + this.noTeleport + " | ";
        flag = flag + "noInterac(Interaction) : " + this.noInteract + " | ";
        flag = flag + "noFire(Incendie) : " + this.noFire + " | ";
        flag = flag + "Gamemode : " + this.mode + " | ";
        flag = flag + "noMob(monstre) : " + this.noMob + " | ";
        flag = flag + "noTNT(TNT) : " + this.noTNT + " | ";
        flag = flag + "noCommand(commande) : " + this.noCommand + " | ";
        return flag;
    }
   
    public void setUuidOwner(String uuidOwner){this.uuidOwner = uuidOwner;}
    public void setUuidAllowed(String uuidAllowed){this.uuidAllowed = uuidAllowed;}
    public void setNoInteract(int noInteract){this.noInteract = noInteract;}
    public void setName(String plotName){this.plotName = plotName;}
    public void setLevel(int level){this.level = level;}
    public void setworld(String world){this.world = world;}
    public void setX1(int x1){this.x1 = x1;}
    public void setX2(int x2){this.x2 = x2;}
    public void setY1(int y1){this.y1 = y1;}
    public void setY2(int y2){this.y2 = y2;}
    public void setZ1(int z1){this.z1 = z1;}
    public void setZ2(int z2){this.z2 = z2;}
    public void setNoBreak(int noBreak){this.noBreak = noBreak;}
    public void setNoBuild(int noBuild){this.noBuild = noBuild;}
    public void setNoFire(int noFire){this.noFire = noFire;}
    public void setNoTeleport(int noTeleport){this.noTeleport = noTeleport;}
    public void setNoFly(int noFly){this.noFly = noFly;}
    public void setJail(int jail){this.jail = jail;}
    public void setNoEnter(int noEnter){this.noEnter = noEnter;}
    public void setMessage(String message){this.message = message;}
    public void setNoMob(int noMob){this.noMob = noMob;}
    public void setNoTNT(int noTNT){this.noTNT = noTNT;}
    public void setMode(int mode){this.mode = mode;} 
    public void setNoCommand(int noCommand){this.noCommand = noCommand;} 
    
    public String getUuidOwner(){return this.uuidOwner;}
    public String getUuidAllowed(){return this.uuidAllowed;}
    public int getNoInteract(){return this.noInteract;}
    public String getName(){return this.plotName;}
    public int getLevel(){return this.level;}
    public String getworldName(){return this.world;}
    public int getX1(){return this.x1;}
    public int getX2(){return this.x2;}
    public int getY1(){return this.y1;}
    public int getY2(){return this.y2;}
    public int getZ1(){return this.z1;}
    public int getZ2(){return this.z2;}
    public int getNoBreak(){return this.noBreak;}
    public int getNoBuild(){return this.noBuild;}
    public int getNoFire(){return this.noFire;}
    public int getNoTeleport(){return this.noTeleport;}
    public int getNoFly(){return this.noFly;}
    public int getJail(){return this.jail;}
    public int getNoEnter(){return this.noEnter;}
    public String getMessage(){return this.message;}
    public int getNoMob(){return this.noMob;}
    public int getNoTNT(){return this.noTNT;}
    public int getMode(){return this.mode;} 
    public int getNoCommand(){return this.noCommand;} 
    
    /**
     * Retourne l'objet World correspondant
     * @return World
     */
    public Optional<World> getWorld(){
        Optional<World> w = getGame().getServer().getWorld(world);
        if(w.isPresent()){
            return w;
        }else{
            return Optional.empty();
        }
    }
    
    /**
     * Nom du propriétaire de la parcelle (Plot)
     * @return String
     */
    public String getNameOwner(){
        if (uuidOwner.equalsIgnoreCase("ADMIN")) return "ADMIN";
        return getGPlayer(uuidOwner).getName();
    }
    
    /**
     * Chaine type String comprenant les noms des joueurs autorisés 
     * à utiliser la parcelle (Plot)
     * @return Chaine type String
     */
    public String getNameAllowed(){
        String[] UUID = this.uuidAllowed.split(" ");
        String NameAllowed = "";
        for(String uuid : UUID){
            if (uuid.equalsIgnoreCase("ADMIN")) return "ADMIN";
            NameAllowed = NameAllowed + " " + getGPlayer(uuid).getName();
        }
        return NameAllowed;
    }
    
    /**
     * Objet Location correspondant au point X1, Y1, Z1
     * @return Location
     */
    public Location getLocX1Y1Z1()
    {
        World w = getGame().getServer().getWorld(this.world).get();
        Location location = new Location(w, this.x1, this.y1, this.z1);
        return location;
    }
    
    /**
     * Objet Location correspondant au point X2, Y2, Z2
     * @return Location
     */
    public Location getLocX2Y2Z2()
    {
        World w = getGame().getServer().getWorld(this.world).get();
        Location location = new Location(w, this.x2, this.y2, this.z2);
        return location;
    }
    
    /**
     * Objet Location correspondant au point X1, Y1, Z2
     * @return Location
     */
    public Location getLocX1Y1Z2()
    {
        World w = getGame().getServer().getWorld(this.world).get();
        Location location = new Location(w, this.x1, this.y1, this.z2);
        return location;
    }
    
    /**
     * Objet Location correspondant au point X2, Y2, Z1
     * @return Location
     */
    public Location getLocX2Y2Z1()
    {
        World w = getGame().getServer().getWorld(this.world).get();
        Location location = new Location(w, this.x2, this.y2, this.z1);
        return location;
    }
    
    /**
     * Objet Location correspondant au point X1, Y2, Z2
     * @return Location
     */
    public Location getLocX1Y2Z2()
    {
        World w = getGame().getServer().getWorld(this.world).get();
        Location location = new Location(w, this.x1, this.y2, this.z2);
        return location;
    }
    
    /**
     * Objet Location correspondant au point X2, Y1, Z2
     * @return Location
     */
    public Location getLocX2Y1Z2()
    {
        World w = getGame().getServer().getWorld(this.world).get();
        Location location = new Location(w, this.x2, this.y1, this.z1);
        return location;
    }
    
    /**
     * Objet Location correspondant au point X2, Y1, Z1
     * @return Location
     */
    public Location getLocX2Y1Z1()
    {
        World w = getGame().getServer().getWorld(this.world).get();
        Location location = new Location(w, this.x2, this.y1, this.z2);
        return location;
    }
    
    /**
     * Objet Location correspondant au point X1, Y2, Z1
     * @return Location
     */
    public Location getLocX1Y2Z1()
    {
        World w = getGame().getServer().getWorld(this.world).get();
        Location location = new Location(w, this.x1, this.y2, this.z1);
        return location;
    }
    
    /**
     * Objet Location correspondant au centre de la parcelle (Plot)
     * @return l'objet Location
     */
    public Optional<Location> getSpawnPlot(){
        Optional<World> worldopt = getWorld();
        if(worldopt.isPresent()){
            int locX = (getX1() + getX2())/2;
            int locZ = (getZ1() + getZ2())/2;
            int locY = getYSpawn(locX,locZ);
            Location location = new Location(worldopt.get(),locX, locY, locZ);
            return Optional.of(location);
        } else {
            return Optional.empty();
        }
    }
    
    /**
     * Point Y correspondant au premier bloc le plus au niveau du sol
     * @param X coordonnée X du point à calculer
     * @param Z coordonnée Z du point à calculer
     * @return le point Y
     */
    private int getYSpawn(int X, int Z){
        Optional<World> worldopt = getWorld();
        if(worldopt.isPresent()){
            Location location = new Location(worldopt.get(), X, 250, Z);
            while (location.getBlockType().equals(AIR)){
                location = location.add(0,-1,0);
            }
            location = location.add(0,1,0);
            return location.getBlockY();
        }
        return 0;
    }
    
}
