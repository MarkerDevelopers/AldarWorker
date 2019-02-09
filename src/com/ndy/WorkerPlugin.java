package com.ndy;

import com.ndy.module.PluginModuleManager;
import com.ndy.module.impl.IModuleInitializer;
import com.ndy.module.type.ModuleLoadResult;
import com.ndy.worker.command.WorkerCommand;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class WorkerPlugin extends JavaPlugin implements IModuleInitializer, Listener {

    @Override
    public void onEnable() {
        PluginModuleManager.getManager().registerModule(this, this);
    }

    @Override
    public ModuleLoadResult initialize() throws Exception {
        getCommand("worker").setExecutor(new WorkerCommand());

        Bukkit.getPluginManager().registerEvents(this, this);

        return ModuleLoadResult.Success;
    }

    @Override
    public void dispose() {

    }

    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        if(event.getClickedBlock() != null) {
            World world = event.getClickedBlock().getWorld();
            System.out.println(event.getClickedBlock().toString());
        }
    }

}


