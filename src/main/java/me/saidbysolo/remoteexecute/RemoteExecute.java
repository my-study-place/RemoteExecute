package me.saidbysolo.remoteexecute;

import org.bukkit.plugin.java.JavaPlugin;

import io.javalin.Javalin;

public final class RemoteExecute extends JavaPlugin {
    private static Javalin app = null;

    @Override
    public void onEnable() {
        if (app == null) {
            app = Javalin.create().start(6974);
            app.get("", ctx -> ctx.result("Hello, World"));

        }
        app.start(6974);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
