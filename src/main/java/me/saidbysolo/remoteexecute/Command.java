package me.saidbysolo.remoteexecute;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Command implements CommandExecutor {
    private final RemoteExecute plugin;
    private final Request request;

    public Command(RemoteExecute plugin) {
        this.plugin = plugin;
        this.request = new Request();
    }

    private Map<String, Material> getMappping() {
        Map<String, Material> hashmap = new HashMap<String, Material>();
        hashmap.put("다이아몬드", Material.DIAMOND);
        hashmap.put("금", Material.GOLD_INGOT);
        hashmap.put("철", Material.IRON_INGOT);
        hashmap.put("에메랄드", Material.EMERALD);
        // hashmap.put("네더라이트", Material.NETHERITE_INGOT); 상위 버전 테스트 필요.
        return hashmap;
    }

    private boolean checkNumberArg(String number) {
        try {
            return Integer.parseInt(number) > 0;
        } catch (NumberFormatException ignored) {
            return false;
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (label.equalsIgnoreCase("환전")) {
                if (args.length != 2) {
                    player.sendMessage("인자가 부족합니다.");
                } else {
                    if (getMappping().containsKey(args[0]) && checkNumberArg(args[1])) { // arg[0] 광물 arg[1] 숫자
                        if (player.getInventory().containsAtLeast(new ItemStack(getMappping().get(args[0])),
                                Integer.parseInt(args[1]))) { // 인벤토리에 아이템 충분한지 확인
                            player.getInventory()
                                    .removeItem(new ItemStack(getMappping().get(args[0]), Integer.parseInt(args[1]))); // 아이템
                                                                                                                       // 삭제
                            Bukkit.getServer().getScheduler().runTaskAsynchronously(this.plugin, () -> { // 비동기로 실행해야함
                                int code = this.request.post("1", 200, "1", "a"); // 상태코드
                                if (code != 200) { // 핸들
                                    player.sendMessage("환전에 실패했어요");
                                } else {
                                    player.sendMessage("환전이 완료되었어요!");
                                }
                            });
                        } else { // 아이템 부족 핸들
                            player.sendMessage("아이템이 부족합니다.");
                        }

                    }
                }
            }
        }
        return false;
    }
}