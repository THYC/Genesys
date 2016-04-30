package net.teraoctet.genesys.utils;

public class GHome {
	
	private String uuid;
	private String name;
	private String world;
	private int x;
	private int y;
	private int z;
	
	public GHome(String uuid, String name, String world, int x, int y, int z) {
		this.uuid = uuid;
		this.name = name;
		this.world = world;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void insert() {
		GData.queue("INSERT INTO ghomes VALUES ('" + uuid + "', '" + name + "', '" + world + "', " + x + ", " + y + ", " + z + ")");
	}
	
	public void update() {
		GData.queue("UPDATE ghomes SET world = '" + world + "', x = " + x + ", y = " + y + ", z = " + z + " WHERE uuid = '" + uuid + "' AND name = '" + name + "'");
	}
	
	public void delete() {
		GData.queue("DELETE FROM ghomes WHERE uuid = '" + uuid + "' AND name = '" + name + "'");
	}
	
	public void setUUID(String uuid) { this.uuid = uuid; }
	public void setName(String name) { this.name = name; }
	public void setWorld(String world) { this.world = world; }
	public void setX(int x) { this.x = x; }
	public void setY(int y) { this.y = y; }
	public void setZ(int z) { this.z = z; }
	
	public String getUUID() { return uuid; }
	public String getName() { return name; }
	public String getWorld() { return world; }
	public int getX() { return x; }
	public int getY() { return y; }
	public int getZ() { return z; }

}
