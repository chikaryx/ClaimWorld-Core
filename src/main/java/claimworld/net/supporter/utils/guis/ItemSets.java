package claimworld.net.supporter.utils.guis;

import claimworld.net.supporter.utils.CustomHead;
import claimworld.net.supporter.utils.CustomItem;
import claimworld.net.supporter.utils.Ranks;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;

import static claimworld.net.supporter.utils.StringUtils.colorize;
import static org.bukkit.Bukkit.getLogger;

public class ItemSets {
    
    private final HashMap<Integer, ItemStack> itemMap = new HashMap<>();
    private final ReadyItems readyItems = new ReadyItems();
    
    public HashMap<Integer, ItemStack> initializeInventoryContent(Player player, String inventoryName) {
        if (inventoryName == null || inventoryName.isEmpty()) {
            getLogger().log(Level.INFO, "initializing error - inventoryName is empty or null");
            return null;
        }

        itemMap.clear();

        if (inventoryName.equals("Menu")) {
            itemMap.put(10, new CustomHead("&aInformacje", player, 1, Arrays.asList(
                    colorize("&fNick: " + player.getName() + "&f."),
                    colorize("&fRanga: " + new Ranks().getRankName(player) + "&f."),
                    colorize("&fDolaczono: " + new Date(player.getFirstPlayed()) + "&f."),
                    "",
                    colorize("&fOpoznienie: " + player.getPing() + "&fms.")
            )).getItem());
            itemMap.put(12, new CustomItem("&aTeleportacja", Material.COMPASS, Collections.singletonList(colorize("&7&oPrzenies sie szybko!"))).getItem());
            itemMap.put(13, new CustomItem("&aPunkty", Material.EXPERIENCE_BOTTLE, Collections.singletonList(colorize("&7&oZarzadzaj swoimi punktami!"))).getItem());
            itemMap.put(14, new CustomItem("&aPrzepustka", Material.IRON_INGOT, Arrays.asList(colorize("&7&o- Jeszcze wiecej opcji!"), colorize("&7&oDostepna juz niebawem!"))).getItem());
            itemMap.put(15, new CustomItem("&bPrzepustka Premium ✦", Material.DIAMOND, Arrays.asList(colorize("&7&o- Nowe, unikalne mozliwosci!"), colorize("&7&oDostepna juz niebawem!"))).getItem());
            itemMap.put(16, new CustomItem("&bPanel VIP", Material.ENCHANTING_TABLE, Arrays.asList(colorize("&7&oTajemnicze miejsce!"), colorize("&7&oDostepna juz niebawem!"))).getItem());
            itemMap.put(28, new CustomItem("&fOgnisko u Mariana", Material.CAMPFIRE, Collections.singletonList(colorize("&7&oCentrum pomocy."))).getItem());
            itemMap.put(37, new CustomItem("&fUlatwienia dostepu", Material.NAME_TAG, Collections.singletonList(colorize("&7&oDodatkowe ustawienia."))).getItem());
        }

        if (inventoryName.equals("Teleportacja")) {
            String lore = colorize("&7&oUzyj, by sie przeteleportowac.");

            itemMap.put(53, readyItems.get("Cofnij"));
            itemMap.put(10, new CustomItem("&fUstaw dom", Material.WHITE_BED, Collections.singletonList(colorize("&7&oUzyj, by ustawic dom."))).getItem());
            itemMap.put(12, new CustomItem("&aDom", Material.LIME_BED, Collections.singletonList(lore)).getItem());
            itemMap.put(13, new CustomItem("&aSpawn", Material.LIME_BANNER, Collections.singletonList(lore)).getItem());
            itemMap.put(14, new CustomItem("&aAutostrada Polnocna", Material.LIME_BANNER, Collections.singletonList(lore)).getItem());
            itemMap.put(15, new CustomItem("&aAutostrada Poludniowa", Material.LIME_BANNER, Collections.singletonList(lore)).getItem());
            itemMap.put(16, new CustomItem("&aCentrum Publiczne", Material.LIME_BANNER, Collections.singletonList(lore)).getItem());
        }

        if (inventoryName.equals("Ulatwienia dostepu")) {
            itemMap.put(53, readyItems.get("Cofnij"));
            itemMap.put(10, new CustomItem("&aPoziom morza", Material.WATER_BUCKET, Collections.singletonList(colorize("&7&oSprawdz poziom morza."))).getItem());
            itemMap.put(11, new CustomItem("&aKosz", Material.HOPPER_MINECART, Collections.singletonList(colorize("&7&oLatwo usun przedmioty."))).getItem());
        }

        return itemMap;
    }
}