package net.teraoctet.genesys.world;

import com.flowpowered.math.vector.Vector3d;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.world.Location;

import java.util.HashSet;
import java.util.Set;

public class TreeDetector {

    private final BlockSnapshot startBlock;
    private Set<BlockSnapshot> woodSnaps = null;
    private final Set<Vector3d> locations = new HashSet<>();
    
    public final Vector3d[] DIRECTIONS = {
        Vector3d.ZERO.add(1, 0, 0),
        Vector3d.ZERO.add(1, 0, 1),
        Vector3d.ZERO.add(0, 0, 1),
        Vector3d.ZERO.add(-1, 0, 1),
        Vector3d.ZERO.add(-1, 0, 0),
        Vector3d.ZERO.add(-1, 0, -1),
        Vector3d.ZERO.add(0, 0, -1),
        Vector3d.ZERO.add(1, 0, -1),
        Vector3d.ZERO.add(1, 1, 0),
        Vector3d.ZERO.add(1, 1, 1),
        Vector3d.ZERO.add(0, 1, 1),
        Vector3d.ZERO.add(-1, 1, 1),
        Vector3d.ZERO.add(-1, 1, 0),
        Vector3d.ZERO.add(-1, 1, -1),
        Vector3d.ZERO.add(0, 1, -1),
        Vector3d.ZERO.add(1, 1, -1),
        Vector3d.ZERO.add(0, 1, 0)
    };
    
    public TreeDetector(BlockSnapshot startBlock) {
        this.startBlock = startBlock;
    }

    public static boolean isWood(BlockSnapshot loc) {
        return loc.getState().getType().equals(BlockTypes.LOG) || loc.getState().getType().equals(BlockTypes.LOG2);
    }

    public Set<BlockSnapshot> getWoodLocations() {
        if (woodSnaps == null) {
            woodSnaps = new HashSet<>();
            getWoodLocations(startBlock);
        }
        return woodSnaps;
    }

    private void getWoodLocations(BlockSnapshot startBlock) {
        if (startBlock.getState().getType().equals(BlockTypes.LOG) || startBlock.getState().getType().equals(BlockTypes.LOG2)) {
            woodSnaps.add(startBlock);
            locations.add(startBlock.getLocation().get().getPosition());

            Location nextBlock = startBlock.getLocation().get().add(0, 1, 0);
            if (!locations.contains(nextBlock.getPosition())) { getWoodLocations(nextBlock.getBlock().snapshotFor(nextBlock)); }
            nextBlock = startBlock.getLocation().get().add(1, 0, 0);
            if (!locations.contains(nextBlock.getPosition())) { getWoodLocations(nextBlock.getBlock().snapshotFor(nextBlock)); }
            nextBlock = startBlock.getLocation().get().add(-1, 0, 0);
            if (!locations.contains(nextBlock.getPosition())) { getWoodLocations(nextBlock.getBlock().snapshotFor(nextBlock)); }
            nextBlock = startBlock.getLocation().get().add(0, 0, 1);
            if (!locations.contains(nextBlock.getPosition())) { getWoodLocations(nextBlock.getBlock().snapshotFor(nextBlock)); }
            nextBlock = startBlock.getLocation().get().add(0, 0, -1);
            if (!locations.contains(nextBlock.getPosition())) { getWoodLocations(nextBlock.getBlock().snapshotFor(nextBlock)); }
            
            if (!locations.contains(nextBlock.getPosition())) { getWoodLocations(nextBlock.getBlock().snapshotFor(nextBlock)); }
            nextBlock = startBlock.getLocation().get().add(1, 0, 1);
            if (!locations.contains(nextBlock.getPosition())) { getWoodLocations(nextBlock.getBlock().snapshotFor(nextBlock)); }
            nextBlock = startBlock.getLocation().get().add(1, 0, -1);
            if (!locations.contains(nextBlock.getPosition())) { getWoodLocations(nextBlock.getBlock().snapshotFor(nextBlock)); }
            nextBlock = startBlock.getLocation().get().add(-1, 0, 1);
            if (!locations.contains(nextBlock.getPosition())) { getWoodLocations(nextBlock.getBlock().snapshotFor(nextBlock)); }
            nextBlock = startBlock.getLocation().get().add(-1, 0, -1);
            
            if (!locations.contains(nextBlock.getPosition())) { getWoodLocations(nextBlock.getBlock().snapshotFor(nextBlock)); }
            nextBlock = startBlock.getLocation().get().add(1, 1, 0);
            if (!locations.contains(nextBlock.getPosition())) { getWoodLocations(nextBlock.getBlock().snapshotFor(nextBlock)); }
            nextBlock = startBlock.getLocation().get().add(-1, 1, 0);
            if (!locations.contains(nextBlock.getPosition())) { getWoodLocations(nextBlock.getBlock().snapshotFor(nextBlock)); }
            nextBlock = startBlock.getLocation().get().add(0, 1, 1);
            if (!locations.contains(nextBlock.getPosition())) { getWoodLocations(nextBlock.getBlock().snapshotFor(nextBlock)); }
            nextBlock = startBlock.getLocation().get().add(0, 1, -1);
            if (!locations.contains(nextBlock.getPosition())) { getWoodLocations(nextBlock.getBlock().snapshotFor(nextBlock)); }
        }
    }
}