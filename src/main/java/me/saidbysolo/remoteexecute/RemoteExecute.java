package me.saidbysolo.remoteexecute;

import org.bukkit.plugin.java.JavaPlugin;

import io.javalin.Javalin;
import io.javalin.http.Context;
import static io.javalin.apibuilder.ApiBuilder.*;

public final class RemoteExecute extends JavaPlugin {
    private static Javalin app = null;

    public static void testPost(Context ctx) {
        String a = ctx.attribute("a");
        String b = ctx.queryParam("a");
        String c = ctx.sessionAttribute("a");
        String d = ctx.formParam("a");
        System.out.println("a:" + a + "b:" + b + "c:" + c + "d:" + d);
        if (a == "b") {
            ctx.result("OK");
        } else {
            ctx.result("NOT_OK");
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
