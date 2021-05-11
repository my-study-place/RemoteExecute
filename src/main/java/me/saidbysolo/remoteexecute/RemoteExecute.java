package me.saidbysolo.remoteexecute;

import org.bukkit.plugin.java.JavaPlugin;

public final class RemoteExecute extends JavaPlugin {

    @Override
    public void onEnable() {
        getCommand("환전").setExecutor(new Command(this));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
