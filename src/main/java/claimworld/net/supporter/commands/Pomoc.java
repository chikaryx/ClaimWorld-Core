package claimworld.net.supporter.commands;

import claimworld.net.supporter.utils.CommandBase;
import claimworld.net.supporter.utils.GeyserUtils;
import claimworld.net.supporter.utils.GoalUtils;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.ArrayList;
import java.util.List;

public class Pomoc {

    GoalUtils goalUtils = GoalUtils.getInstance();

    private final GeyserUtils geyserUtils = new GeyserUtils();
    
    private final List<String> helpMessages = new ArrayList<>();

    private ItemStack getBook() {
        ItemStack book = new ItemStack(Material.WRITTEN_BOOK, 1);
        BookMeta bookMeta = (BookMeta)book.getItemMeta();

        assert bookMeta != null;
        bookMeta.setAuthor("Chikaraa");
        bookMeta.setTitle("Pomoc");

        bookMeta.setPages(helpMessages);
        book.setItemMeta(bookMeta);
        return book;
    }

    public Pomoc() {
        helpMessages.add("§8Informacje\n\n§c+§8 W tym poradniku znajdziesz niemal wszystkie najwazniejsze informacje o serwerze.\n\n§c+§8 Masz pytania? Smialo, kieruj je na Discorda.");
        helpMessages.add("§8Komendy\n\n§c/msg§8,§c/r§8 - pw\n§c/cdefault <nick>§8 - dostep do wszystkich stawianych blokow\n§c/cmodify <nick>§8 - dostep do pojedynczego bloku\n§c/cremove§8 - usuniecie zabezpieczenia bloku\n§c/lwc mode persist§8 - utrzymanie obecnego trybu");
        helpMessages.add("§8Komendy VIP\n\n§c/sit§8 - usiadz sobie\n§c/playtime <nick>§8 - czas online\n§c/me§8 - opis postaci\n§c/skin§8 - zmien swojego skina\n§c/hat§8 - zaloz na glowe trzymany blok");
        helpMessages.add("§8Komendy MVP+\n\n§c/lay§8,§c /crawl§8, §c/spin§8, §c/bellyflop§8 - animacje postaci\n§c/pp§8 - konsola efektow\n§c/fw§8 - fajerwerk pod nogami\n§c/seriafw§8 - seria fajerwerkow w niebo\n\nMVP ma rowniez wszystkie komendy VIP.");
        helpMessages.add("§8Dodatki / Zmiany\n\n§c+§8 Skrzynie blokuja sie po postawieniu\n§c+§8 Ograniczenie giga maszyn z redstone\n§c+§8 Brodawki wypadaja z piglinow\n§c+§8 Wiekszosc bytow mozna podnosic przy pomocy SHIFT+PPM\n§c+§8 Dodatkowi Handlarze\n§c+§8 System teleportacji");
        helpMessages.add("§8Dodatki / Zmiany\n\n§c+§8 Senat\n§c+§8 Globalne wzmocnienia\n§c+§8 Nieco spawn mobow, inne leje\n§c+§8 Limit bytow na chunk\n§c+§8 Battlepass i system zadan\n§c+§8 Umiejetnosci\n§c+§8Z mobow wypadaja ich glowy");
        helpMessages.add("§8Dodatki / Zmiany\n\n§c+§8 Przedmioty i exp grupuja sie\n§c+§8 Niestandardowe przedmioty\n§c+§8 Wielofunkcyjny Kowal");
        helpMessages.add("§8Senat\n\n§cSenat§8 to organizacja, ktora zajmuje sie obsluga graczy.\n\n§cSiedziba Senatu§8 jest obecnie w przygotowaniu, dlatego jesli czegos potrzebujesz to pisz bezposrednio do Senatora.");
        helpMessages.add("§8Senat\n\n§cSenatorowie§8: kinia_98, _NeKoToJa_, MarJan_Lubi_Kuce, Mr_PogromcA, xCaptain865, rtygta, Chikaraa.\n\nU Senatorow mozecie zakupic nastepujace uslugi:");
        helpMessages.add("§8Senat\n\n§c+§8 Niestandardowe ustawienia dzialki ($16/ustawienie/chunk)\n§c+§8 Zmiane biomu na dzialce ($16/chunk za biom z Overworlda lub $64/chunk za inny biom)");
        helpMessages.add("§8Senat\n\n§c+§8 Zmiane biomu na zimowy (ZA DARMO DO KONCA ROKU!)\n§c+§8 Rezerwacja baneru ($5 za standardowy lub $15 za niestandardowy wzor)\n§c+§8 Niestandardowy baner ($1/sztuka)\n§c+§8 Wlasna grafika na mapach ($8/mape).");
        helpMessages.add("§8Senat\n\nSenatorowi mozecie rowniez zglosic swoja §cfarme§8, by dodal ja do spisu farm publicznych ($5/farma).\n\nRaz w miesiacu wybierana jest farma danego typu, a jej wlasciciel otrzymuje §cdolary§8.");
        helpMessages.add("§8Wybrane Farmy\n\nNastepna aktualizacja: 30.12.2022\n\nZglos farme Senatorowi, by miec szanse na dodatkowy zarobek!\n\n§c+§8 Zelazo [365 49 -284 Nether]\n§c+§8 Zloto [-128 253 -84 Nether]");
        helpMessages.add("§8Wybrane Farmy\n\n§c+§8 Blazy [-334 72 -3 Nether]\n§c+§8 Owce [656 -703 Overworld]\n§c+§8 Kaktusy [409 289 Overworld]\n§c+§8 Trzcina [1096 -185 Overworld]\n§c+§8 Wiesniaki [310 -1979 Overworld]");
        helpMessages.add("§8Sztandary\n\nZajete §cwzory§8 sztandardow dostepne sa do wgladu w specjalnym miejscu obok spawnu (285x, 432z).\n\nKazdy moze zarezerwowac swoj wzor u Senatora.");
        helpMessages.add("§8Zakupy w Sklepie\n\n§c+§8 Za osiagniecie progu w sklepie gracze odblokowuja dodatkowe rzeczy.\n\nKolejna nagroda: §c" + goalUtils.getCurrentReward() + "\n\n§8Do celu brakuje: §c" + goalUtils.getMissingAmount() + "zl");
        helpMessages.add("§8Osiagniecia\n\nZa zrealizowanie wszystkich osiagniec Senat moze przydzielic graczowi specjalna §cstatuetke§8.\n\nWystarczy wyslac odpowiednie screeny na Discorda i oznaczyc Senatora.");
        helpMessages.add("§8Skrzynki\n\nZawieraja rozne przedmioty. Najciekawsze z nich to:\n§c+§8 Elytra, Beacon, ShulkerBox (~0.4%)\n§c+§8 Netherytowe Narzedzia (~3%)\n§c+§8 Niestandardowe Przedmioty (~9%)");
        helpMessages.add("§8O Serwerze\n\n§cClaim World§8 stawia przede wszystkim na dlugoterminowosc.\n\nChcemy utrzymac rozgrywke w klimatach klasycznego Minecrafta, jednoczesnie caly czas nieco ja urozmaicajac.");
        
        new CommandBase("pomoc", true) {
            @Override
            public boolean onCommand(CommandSender sender, String[] arguments) {
                if (geyserUtils.isPlayerFromGeyser(((Player) sender).getUniqueId())) {
                    sender.sendMessage(String.valueOf(helpMessages));
                } else {
                    ((Player) sender).openBook(getBook());
                }
                return true;
            }

            @Override
            public String getUsage() {
                return "/pomoc";
            }
        }.setPermission("claimworld.player");
    }
}
