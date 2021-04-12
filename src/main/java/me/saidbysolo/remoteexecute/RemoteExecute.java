package me.saidbysolo.remoteexecute;

import org.bukkit.plugin.java.JavaPlugin;

import io.javalin.Javalin;

public final class RemoteExecute extends JavaPlugin {
    private static Javalin app = null;

    @Override
    public void onEnable() {
        ClassLoader CL = Thread.currentThread().getContextClassLoader();

        Thread.currentThread().setContextClassLoader(RemoteExecute.class.getClassLoader());

        if (app == null) {
            app = Javalin.create().start(6974);
            app.get("", ctx -> ctx.result("Hello, World"));

        }
        app.start(6974);
        Thread.currentThread().setContextClassLoader(CL);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
