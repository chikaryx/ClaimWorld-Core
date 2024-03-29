package claimworld.net.supporter.battlepass;

import claimworld.net.supporter.Supporter;
import claimworld.net.supporter.utils.AttributesManager;
import claimworld.net.supporter.utils.WarehouseUtils;
import claimworld.net.supporter.items.ReadyItems;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import java.util.*;
import java.util.logging.Level;

import static claimworld.net.supporter.utils.MessageUtils.getBattlepassIcon;
import static claimworld.net.supporter.utils.StringUtils.colorize;
import static claimworld.net.supporter.tasks.TaskManager.getUserQuestDataObjectiveName;
import static org.bukkit.Bukkit.*;

public class BattlePassManager {

    private final AttributesManager attributesManager = new AttributesManager();

    public static final String checkingObjectiveName = "odebranybattlepass";
    public static final String mainObjectiveName = "punktyprzepustki";
    public static final int rewardLimit = 100;

    public static BattlePassManager getInstance() {
        if (instance == null) instance = new BattlePassManager();
        return instance;
    }

    private static BattlePassManager instance = null;

    private final HashMap<Integer, BattlePassLevel> battlePassMap = new HashMap<>();

    public ItemStack getBattlepassBook() {
        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta bookMeta = (BookMeta) book.getItemMeta();
        assert bookMeta != null;
        List<BaseComponent[]> componentPages = new ArrayList<>();

        ComponentBuilder page = new ComponentBuilder().append("§cPrzepustka Bojowa§8\n\nBy awansowac, wykonuj zadania.\n\nSwoj poziom znajdziesz na tabliscie.\n\nZawartosc przepustki zwieksza sie z czasem.");
        componentPages.add(page.create());

        page = new ComponentBuilder().append("§cLegenda\n\n§c[I]§8 Przedmiot\n§c[P]§8 Punkt (z mnoznikiem)\n§c[S]§8 Umiejetnosc\n§c[A]§8 Atrybut");
        componentPages.add(page.create());

        page = new ComponentBuilder().append("§cPrzepustka Bojowa");

        int onPageIndex = 0;
        for (Map.Entry<Integer, BattlePassLevel> entry : battlePassMap.entrySet()) {
            page.append("\n§c" + ((entry.getKey() + 1)) + "| §8" + entry.getValue().getReward());

            onPageIndex++;
            if (onPageIndex % 13 == 0 || (!(entry.getKey() < rewardLimit - 1))) {
                componentPages.add(page.create());
                page = new ComponentBuilder().append("§cPrzepustka Bojowa");
            }
        }

        bookMeta.spigot().setPages(componentPages);
        bookMeta.setTitle("Battlepass");
        bookMeta.setAuthor("Chikaraa");
        book.setItemMeta(bookMeta);

        return book;
    }

    public void updateTablistFooter(Player player) {
        String playerName = player.getName();
        Scoreboard scoreboard = player.getScoreboard();
        assert scoreboard.getEntryTeam(playerName) != null;
        int timePlayed = player.getStatistic(Statistic.TOTAL_WORLD_TIME) / 20 / 60 / 60;

        if (scoreboard.getEntryTeam(playerName) == null)  {
            player.setPlayerListFooter("\n" + colorize(scoreboard.getObjective(mainObjectiveName).getScore(playerName).getScore() + getBattlepassIcon() + " " + timePlayed + "h") + "\n");
        } else {
            player.setPlayerListFooter("\n" + colorize(scoreboard.getEntryTeam(playerName).getPrefix() + "&a" + scoreboard.getObjective(mainObjectiveName).getScore(playerName).getScore() + getBattlepassIcon() + " &f" + timePlayed + "h") + "\n");
        }
    }

    public void tryReward(Player player, int level) {
        if (!battlePassMap.containsKey(level - 1)) return;

        String playerName = player.getName();
        int hasBeenAchieved = player.getScoreboard().getObjective(checkingObjectiveName).getScore(playerName).getScore();
        if (hasBeenAchieved == 1) return;

        getLogger().log(Level.INFO, "Reward hasn't been achieved, continuing...");

        Scoreboard scoreboard = player.getScoreboard();
        scoreboard.getObjective(checkingObjectiveName).getScore(playerName).setScore(0);
        scoreboard.getObjective(getUserQuestDataObjectiveName).getScore(playerName).setScore(0);

        renderReward(player, level);
    }

    public void renderReward(Player player, int level) {
        getScheduler().runTask(Supporter.getPlugin(), () -> {
            String command = battlePassMap.get(level - 1).getCommand();
            String playerValue = "<PLAYER>";
            if (command.contains(playerValue)) command = command.replace(playerValue, player.getName());
            dispatchCommand(getConsoleSender(), command);
            
            getLogger().log(Level.INFO, "Dispatching command for battlepass level " + level + " reward...");

            getScheduler().runTaskLater(Supporter.getPlugin(), () -> {
                ReadyItems readyItems = ReadyItems.getInstance();
                List<ItemStack> items = new ArrayList<>();
                items.add(readyItems.get("Skrzynia_smoka"));

                new WarehouseUtils().addItemsSingle(player.getName(), items);

                attributesManager.tryUpdateStats(player);
            }, 10L);
        });
    }

    public void renderPlayerDoneQuestEffect(Player player) {
        Location location = player.getLocation();
        player.playSound(location, Sound.ENTITY_VILLAGER_CELEBRATE, 0.75f, 0.75f);
        player.playSound(location, Sound.ENTITY_PILLAGER_CELEBRATE, 0.75f, 0.75f);
    }

    public void renderPlayerDoneQuestInfo(Player player) {
        player.sendTitle("", colorize("&aWykonales zadanie, gratulacje!"), 10, 60, 20);
    }

    public void updatePoints(Player player, int value) {
        updatePoints(player, value, true);
    }

    public void updatePoints(Player player, int value, boolean byQuest) {
        String playerName = player.getName();
        Score score = player.getScoreboard().getObjective(mainObjectiveName).getScore(playerName);

        getLogger().log(Level.INFO, "Trying to update battlepass score for " + player.getName() + "...");

        int updatedScore = score.getScore() + value;
        if (updatedScore > rewardLimit) return;

        score.setScore(updatedScore);
        getLogger().log(Level.INFO, "Updated battlepass score of " + player.getName() + " to " + updatedScore);

        if (byQuest) {
            renderPlayerDoneQuestInfo(player);
            renderPlayerDoneQuestEffect(player);
        }

        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(colorize("&aZaktualizowano punkty przepustki&f (" + updatedScore + getBattlepassIcon() + ")")));
        updateTablistFooter(player);

        tryReward(player, updatedScore);
    }

    public BattlePassManager() {
        int index;
        String name;
        List<Integer> pointIndexes = new ArrayList<>();

        //points
        pointIndexes.add(1);
        pointIndexes.add(9);
        pointIndexes.add(19);
        pointIndexes.add(39);
        pointIndexes.add(49);
        pointIndexes.add(69);
        pointIndexes.add(79);
        pointIndexes.add(95);

        name = "Punkt";
        for (int pointIndex : pointIndexes) {
            battlePassMap.put(pointIndex, new BattlePassLevel(pointIndex, "dajpunkty <PLAYER> 1", "[P] " + name));
        }

        //attributes
        pointIndexes.clear();
        pointIndexes.add(4);
        pointIndexes.add(34);
        pointIndexes.add(64);
        pointIndexes.add(84);

        name = "Atrybut";
        for (int pointIndex : pointIndexes) {
            battlePassMap.put(pointIndex, new BattlePassLevel(pointIndex, "addattribute <PLAYER>", "[A] " + name));
        }

        //others
        index = 0;
        name = "Ruda_diamentu";
        battlePassMap.put(index, new BattlePassLevel(index, "addtowarehouse <PLAYER> " + name, "[I] Diament"));

        index = 14;
        name = "Uniwersalny_bilet";
        battlePassMap.put(index, new BattlePassLevel(index, "addtowarehouse <PLAYER> " + name, "[I] U. Bilet"));

        index = 22;
        name = "Ksiazka_fortune4";
        battlePassMap.put(index, new BattlePassLevel(index, "addtowarehouse <PLAYER> " + name, "[I] Fortune IV"));

        index = 24;
        name = "Sztabka_netherytu";
        battlePassMap.put(index, new BattlePassLevel(index, "addtowarehouse <PLAYER> " + name, "[I] Netheryt"));

        index = 29;
        name = "Punkty bywalca";
        battlePassMap.put(index, new BattlePassLevel(index, "privateMessage <PLAYER> Odblokowales umiejetnosc " + name + ", gratulacje!", "[U] " + name));

        index = 44;
        name = "Ksiazka_unbreaking4";
        battlePassMap.put(index, new BattlePassLevel(index, "addtowarehouse <PLAYER> " + name, "[I] Unbreaking IV"));

        index = 54;
        name = "Ksiazka_sharpness6";
        battlePassMap.put(index, new BattlePassLevel(index, "addtowarehouse <PLAYER> " + name, "[I] Sharpness VI"));

        index = 59;
        name = "Mob Killer";
        battlePassMap.put(index, new BattlePassLevel(index, "privateMessage <PLAYER> Odblokowales umiejetnosc " + name + ", gratulacje!", "[U] " + name));

        index = 74;
        name = "Niewidzialna_ramka";
        battlePassMap.put(index, new BattlePassLevel(index, "addtowarehouse <PLAYER> " + name, "[I] Niew. Ramka"));

        index = 89;
        name = "Twoj spawner";
        battlePassMap.put(index, new BattlePassLevel(index, "privateMessage <PLAYER> Odblokowales umiejetnosc " + name + ", gratulacje!", "[U] " + name));

        index = 92;
        name = "Ksiazka_swiftsneak4";
        battlePassMap.put(index, new BattlePassLevel(index, "addtowarehouse <PLAYER> " + name, "[I] SwiftSneak IV"));

        index = 99;
        name = "Kox";
        battlePassMap.put(index, new BattlePassLevel(index, "addtowarehouse <PLAYER> " + name, "[I] " + name));

        for (int i = 0; i < 100; i++) {
            if (battlePassMap.containsKey(i)) continue;
            battlePassMap.put(i, new BattlePassLevel(i, "addtowarehouse <PLAYER> $1", "[I] $1"));
        }
    }
}
