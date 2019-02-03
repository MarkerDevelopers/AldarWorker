package com.ndy;

import com.ndy.astar.AStarArea;
import com.ndy.astar.node.Node;
import com.ndy.astar.path.PathFindType;
import com.ndy.astar.path.PathFinder;
import com.ndy.astar.path.PathVisualization;
import net.minecraft.server.v1_12_R1.EnumParticle;
import org.bukkit.Location;
import org.bukkit.Material;
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

                try {
                    Particle.send(node.getNode().getLocation().clone().add(0, 0.5, 0), 0.1f, 0.1f, 0.1f, 0, 1, EnumParticle.FIREWORKS_SPARK);
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return false;
    }


    private int getBlockHeight(Location location) {
        int y = 0;
        while(location.add(0, y, 0).getBlock().getTypeId() != 0) {
            y++;
        }

        return y;
    }
}


