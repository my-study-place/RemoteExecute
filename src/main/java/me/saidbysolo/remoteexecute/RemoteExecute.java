package me.saidbysolo.remoteexecute;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import io.javalin.Javalin;
import io.javalin.http.Context;
import static io.javalin.apibuilder.ApiBuilder.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public final class RemoteExecute extends JavaPlugin {
    private static Javalin app = null;

    public static void testPost(Context ctx) {
        String commandLine = ctx.formParam("command");
        if (commandLine.isEmpty()) {
            ctx.result("NOT_OK");
        } else {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PrintStream ps = new PrintStream(baos);
            PrintStream old = System.out;
            System.setOut(ps);
            Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), commandLine);
            System.setOut(old);
            System.out.flush();
            ctx.result(baos.toString());
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
