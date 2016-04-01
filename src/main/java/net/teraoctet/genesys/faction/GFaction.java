package net.teraoctet.genesys.faction;

import static net.teraoctet.genesys.utils.GData.addGFaction;
import static net.teraoctet.genesys.utils.GData.queue;
import static net.teraoctet.genesys.utils.GData.removeGFaction;

public class GFaction {
    private int id_faction;
    private String name;
    private String world;
    private int X;
    private int Y;
    private int Z;
    private double money;
    private int point;
    private int kill;
    private int dead;
    
    // tu peut ajouter plusieurs lanceur comme celui là qui alimente un certain nombre de chant, il faut en faire un qui alimente tout
    public GFaction(int id_faction, String name, String world, int X, int Y, int Z, double money, int point, int kill, int dead) { 
        this.name = name;
        this.world = world;
        this.X = X;
        this.Y = Y;
        this.Z = Z;
        this.money = money;
        this.point = point;
        this.kill = kill;
        this.dead = dead;
    }
    
    public GFaction(String name, String world, int X, int Y, int Z) { // tu peut ajouter plusieurs lanceur comme celui là qui alimente un certain nombre de chant
        this.name = name;
        this.world = world;
        this.X = X;
        this.Y = Y;
        this.Z = Z;
        this.money = 0;
        this.point = 0;
        this.kill = 0;
        this.dead = 0;
    }
    
    public GFaction(String name) { // celui n'alimentera que le nom pour une creation simple
        this.name = name;
        this.world = null;
        this.X = 0;
        this.Y = 0;
        this.Z = 0;
        this.money = 0;
        this.point = 0;
        this.kill = 0;
        this.dead = 0;
    }

    public void insert() {
        queue("INSERT INTO gfactions VALUES ('" + name + "', '" + world + "', " + X + ", " + Y + ", " + Z + ", " + money + ", " + point + ", " + kill + ", " + dead + ")");
        addGFaction(name, this);
    }

    public void update() {
        queue("UPDATE gfactions SET name = '" + name + "', world = '" + world + "', X = " + X + ", Y = " + Y + ", Z = " + Z + ", money = " + money + ", point = " + point + ", kill = " + kill + ", dead = " + dead + " WHERE id_faction = " + id_faction);
        removeGFaction(id_faction);
        addGFaction(name, this);
    }

    public void delete() {
        queue("DELETE FROM gfactions WHERE id_faction = " + id_faction);
        removeGFaction(id_faction);
    }
    
    public Integer getID() { return id_faction; }
    public String getName() { return name; }
    public String getWorld() { return world; }
    public Integer getX() { return X; }
    public Integer getY() { return Y; }
    public Integer getZ() { return Z; }
    public Double getMoney() { return money; }
    public Integer getPoint() { return point; }
    public Integer getKill() { return kill; }
    public Integer getDead() { return dead; }
    
    //public void setID(Integer id_faction) { this.id_faction = id_faction; } --> a supprimer car on ne peut alimenter un chant auto car il est auto alimenteé
    public void setName(String name) { this.name = name; }
    public void setWorld(String world) { this.world = world; }
    public void setX(Integer X) { this.X = X; }
    public void setY(Integer Y) { this.Y = Y; }
    public void setZ(Integer Z) { this.Z = Z; }
    public void setMoney(Double money) { this.money = money; }
    public void setPoint(Integer point) { this.point = point; }
    public void setKill(Integer kill) { this.kill = kill; }
    public void setDead(Integer dead) { this.dead = dead; }
}
