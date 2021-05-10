package me.saidbysolo.remoteexecute;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class RemoteExecute extends JavaPlugin {

    @Override
    public void onEnable() {
        Request request = new Request(this);
        Bukkit.getServer().getScheduler().runTaskAsynchronously(this, () -> {
            int length = request.makeJsonForm("id", 10000, "game", "key");
            request.requestPostJson("https://webhook.site/82d39c46-11e8-4482-9126-093eb3ff49d5", length);
        });
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
