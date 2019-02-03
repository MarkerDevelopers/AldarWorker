package com.ndy.astar.path;

import com.ndy.MinecraftAstar;
import com.ndy.astar.node.Node;
import com.ndy.Particle;
import net.minecraft.server.v1_12_R1.EnumParticle;
import org.bukkit.Bukkit;

import java.util.List;

public class PathVisualization {

    private PathFinder finder;

    public PathVisualization(PathFinder finder) {
        this.finder = finder;
    }

    public void visual() {
        List<Node> path = finder.getPath();
        System.out.println("size: " + path.size());

        if(path.size() == 0) return;

        Bukkit.getScheduler().scheduleSyncRepeatingTask(MinecraftAstar.getPlugin(MinecraftAstar.class), () -> {
            try {

                for (Node node : path)
                    Particle.send(node.getLocation().clone().add(0, 0.5, 0), 0.1f, 0.1f, 0.1f, 0, 1, EnumParticle.FIREWORKS_SPARK);

            }catch (Exception e) {
                e.printStackTrace();
            }

        }, 0L, 20L);
    }

}
