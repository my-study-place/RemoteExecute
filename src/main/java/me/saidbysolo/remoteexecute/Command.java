package me.saidbysolo.remoteexecute;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Command implements CommandExecutor {
    private final RemoteExecute plugin;
    private final Request request;

    public Command(RemoteExecute plugin) {
        this.plugin = plugin;
        this.request = new Request(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (label.equalsIgnoreCase("요청")) {
                int a = this.request.makeJsonForm("A", 2000, "a", "A");
                Bukkit.getServer().getScheduler().runTaskAsynchronously(this.plugin, () -> {
                    this.request.requestPostJson("https://webhook.site/82d39c46-11e8-4482-9126-093eb3ff49d5", a);
                });
                player.sendMessage("a");
            }
        }

        return false;
    }
}