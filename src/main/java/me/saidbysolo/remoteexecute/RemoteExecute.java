package me.saidbysolo.remoteexecute;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import io.javalin.Javalin;
import io.javalin.http.Context;
import static io.javalin.apibuilder.ApiBuilder.*;

public final class RemoteExecute extends JavaPlugin {
    private static Javalin app = null;

    public static void testPost(Context ctx) {
        String commandLine = ctx.formParam("command");
        if (commandLine.isEmpty()) {
            ctx.result("NOT_OK");
        } else {
            Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), commandLine);
        }

    }

    @Override
    public void onEnable() {
        ClassLoader CL = Thread.currentThread().getContextClassLoader();

        Thread.currentThread().setContextClassLoader(RemoteExecute.class.getClassLoader());

        if (app == null) {
            app = Javalin.create();
            app.routes(() -> {
                path("", () -> {
                    post(RemoteExecute::testPost);
                });
            });
        }
        app.start(6974);

        Thread.currentThread().setContextClassLoader(CL);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
