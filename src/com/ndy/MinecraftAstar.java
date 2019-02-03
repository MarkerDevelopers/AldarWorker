package com.ndy;

import com.ndy.astar.node.Node;
import com.ndy.astar.path.PathFindTool;
import com.ndy.astar.path.PathFindType;
import com.ndy.astar.path.PathFinder;
import com.ndy.astar.path.PathVisualization;
import net.minecraft.server.v1_12_R1.EnumParticle;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class MinecraftAstar extends JavaPlugin {

    private Location location1, location2;
    private PathFinder finder;
    private int n;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(label.equals("astar")) {
            Player player = (Player) sender;
            if(args.length == 1 && args[0].equals("goal")) {
                location1 = player.getLocation();
                player.sendMessage("goal set");
            }
            if(args.length == 1 && args[0].equals("pos2")) {
                location2 = player.getLocation();
                player.sendMessage("pos2 set");
            }
            if(args.length == 1 && args[0].equals("start")) {
                Location end = location1;
                finder = new PathFinder(player.getLocation(), end);
                PathFindType type = finder.find();

                player.sendMessage(type.name());
            }
            if(args.length == 1 && args[0].equals("visual")) {
                if(finder != null) {
                    new PathVisualization(finder).visual();
                }
            }
            if(args.length == 1 && args[0].equals("test")) {
                Node node = new Node(((Player) sender).getLocation(), location1, 0, -999);
                node.initialize();

                System.out.println("F : " + node.getHeuristic());
                System.out.println("distance : " + getDistance(node.getLocation(), finder.getEndNode().getLocation()));

                node.print();

                try {
                    Particle.send(node.getNode().getLocation().clone().add(0, 0.5, 0), 0.1f, 0.1f, 0.1f, 0, 1, EnumParticle.FIREWORKS_SPARK);
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }else if (args.length == 1 && args[0].equals("a")) {
                System.out.println("distance: " + getDistance(player.getLocation(), finder.getEndNode().getLocation()));
            }
        }

        return false;
    }

    public float getDistance(Location loc1, Location loc2) {
        int x2 = loc2.getBlockX(), x1 = loc1.getBlockX();
        int y2 = loc2.getBlockY(), y1 = loc1.getBlockY();
        int z2 = loc2.getBlockZ(), z1 = loc1.getBlockZ();

        return (float) Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2) + Math.pow(z1 - z2, 2));
    }


    private int getBlockHeight(Location location) {
        int y = 0;
        while(location.add(0, y, 0).getBlock().getTypeId() != 0) {
            y++;
        }

        return y;
    }
}


