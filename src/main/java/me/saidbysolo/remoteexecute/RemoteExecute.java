package me.saidbysolo.remoteexecute;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class RemoteExecute extends JavaPlugin {

    @Override
    public void onEnable() {
        getCommand("요청").setExecutor(new Command(this));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
