package claimworld.net.supporter.commands;

import claimworld.net.supporter.Supporter;
import claimworld.net.supporter.utils.CommandBase;
import claimworld.net.supporter.utils.items.CustomItem;
import claimworld.net.supporter.utils.items.ReadyItems;
import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static claimworld.net.supporter.utils.MessageUtils.getBroadcastPrefix;
import static claimworld.net.supporter.utils.StringUtils.colorize;
import static org.bukkit.Bukkit.*;

public class OpenChest {

    private final List<ItemStack> randomItems = new ArrayList<>();
    private final List<ItemStack> prezentItems = new ArrayList<>();

    private void playVisuals(World world, Location location) {
        //faze1
        int delay = 0;
        new BukkitRunnable() {
            final AtomicInteger counter = new AtomicInteger();

            @Override
            public void run() {
                assert world != null;
                world.spawnParticle(Particle.CLOUD, location, 20, 0.1, 0.1, 0.1, 0.2);

                counter.getAndIncrement();
                if (counter.get() >= 3) {
                    cancel();
                }
            }
        }.runTaskTimer(Supporter.getPlugin(), 0, 8);

        //faze2
        delay += 24;
        getScheduler().runTaskLater(Supporter.getPlugin(), () -> new BukkitRunnable() {
            final AtomicInteger counter = new AtomicInteger();

            @Override
            public void run() {
                assert world != null;
                world.spawnParticle(Particle.CLOUD, location, 5, 0.1, 0.1, 0.1, 0.2);
                world.spawnParticle(Particle.FLAME, location, 5, 0.1, 0.1, 0.1, 0.2);

                counter.getAndIncrement();
                if (counter.get() >= 6) {
                    cancel();
                }
            }
        }.runTaskTimer(Supporter.getPlugin(), 0, 4), delay);

        //faze3
        delay += 24;
        getScheduler().runTaskLater(Supporter.getPlugin(), () -> new BukkitRunnable() {
            final AtomicInteger counter = new AtomicInteger();

            @Override
            public void run() {
                assert world != null;
                world.spawnParticle(Particle.CLOUD, location, 3, 0.1, 0.1, 0.1, 0.2);
                world.spawnParticle(Particle.FLAME, location, 2, 0.1, 0.1, 0.1, 0.2);
                world.spawnParticle(Particle.FIREWORKS_SPARK, location, 2, 0.1, 0.1, 0.1, 0.2);

                counter.getAndIncrement();
                if (counter.get() >= 9) {
                    cancel();
                }
            }
        }.runTaskTimer(Supporter.getPlugin(), 0, 2), delay);

        //faze4
        delay += 18;
        getScheduler().runTaskLater(Supporter.getPlugin(), () -> new BukkitRunnable() {
            final AtomicInteger counter = new AtomicInteger();

            @Override
            public void run() {
                assert world != null;
                world.spawnParticle(Particle.CLOUD, location, 1, 0.1, 0.1, 0.1, 0.2);
                world.spawnParticle(Particle.FLAME, location, 2, 0.1, 0.1, 0.1, 0.2);
                world.spawnParticle(Particle.FIREWORKS_SPARK, location, 2, 0.1, 0.1, 0.1, 0.2);

                counter.getAndIncrement();
                if (counter.get() >= 15) {
                    cancel();
                }
            }
        }.runTaskTimer(Supporter.getPlugin(), 0, 1), delay);

        //faze5
        delay += 15;
        getScheduler().runTaskLater(Supporter.getPlugin(), () -> world.spawnParticle(Particle.EXPLOSION_HUGE, location, 1), delay);
    }

    private void playSounds(World world, Location location) {
        Sound sound = Sound.BLOCK_NOTE_BLOCK_BASS;
        float volume = 0.6f;
        
        //faze1
        int delay = 0;
        new BukkitRunnable() {
            final AtomicInteger counter = new AtomicInteger();

            @Override
            public void run() {
                assert world != null;
                world.playSound(location, sound, volume, volume);

                counter.getAndIncrement();
                if (counter.get() >= 4) {
                    cancel();
                }
            }
        }.runTaskTimer(Supporter.getPlugin(), 0, 5);

        //faze2
        delay += 20;
        getScheduler().runTaskLater(Supporter.getPlugin(), () -> new BukkitRunnable() {
            final AtomicInteger counter = new AtomicInteger();

            @Override
            public void run() {
                assert world != null;
                world.playSound(location, sound, volume, volume);

                counter.getAndIncrement();
                if (counter.get() >= 5) {
                    cancel();
                }
            }
        }.runTaskTimer(Supporter.getPlugin(), 0, 4), delay);

        //faze3
        delay += 20;
        getScheduler().runTaskLater(Supporter.getPlugin(), () -> new BukkitRunnable() {
            final AtomicInteger counter = new AtomicInteger();

            @Override
            public void run() {
                assert world != null;
                world.playSound(location, sound, volume, volume);

                counter.getAndIncrement();
                if (counter.get() >= 6) {
                    cancel();
                }
            }
        }.runTaskTimer(Supporter.getPlugin(), 0, 3), delay);

        //faze4
        delay += 18;
        getScheduler().runTaskLater(Supporter.getPlugin(), () -> new BukkitRunnable() {
            final AtomicInteger counter = new AtomicInteger();

            @Override
            public void run() {
                assert world != null;
                world.playSound(location, sound, volume, volume);

                counter.getAndIncrement();
                if (counter.get() >= 7) {
                    cancel();
                }
            }
        }.runTaskTimer(Supporter.getPlugin(), 0, 2), delay);

        //faze5
        delay += 14;
        getScheduler().runTaskLater(Supporter.getPlugin(), () -> new BukkitRunnable() {
            final AtomicInteger counter = new AtomicInteger();

            @Override
            public void run() {
                assert world != null;
                world.playSound(location, sound, volume, volume);

                counter.getAndIncrement();
                if (counter.get() >= 8) {
                    cancel();
                }
            }
        }.runTaskTimer(Supporter.getPlugin(), 0, 1), delay);

        //faze6
        delay += 8;
        getScheduler().runTaskLater(Supporter.getPlugin(), () -> world.playSound(location, Sound.ENTITY_GENERIC_EXPLODE, volume, volume), delay);
    }

    private void updateItemEntitySettings(Player player, Item item) {
        item.setPickupDelay(30);
        item.setVisualFire(true);
        item.setCustomNameVisible(true);
        item.setOwner(player.getUniqueId());

        ItemMeta itemMeta = item.getItemStack().getItemMeta();
        String displayName = itemMeta.getDisplayName();
        item.setCustomName(displayName);
    }

    private void renderLateEffects(Location location, World world) {
        world.playSound(location, Sound.ENTITY_ITEM_PICKUP, 1f, 1f);
        world.playSound(location, Sound.ITEM_BUNDLE_DROP_CONTENTS, 1f, 1f);
    }

    public OpenChest() {
        //last amount update - 26.11.2022
        //total amount > 350
        
        ReadyItems readyItems = ReadyItems.getInstance();

        for (Map.Entry<String, CustomItem> entry : readyItems.getItemMap().entrySet()) {
            if (entry.getKey().equals("Menu") || entry.getKey().equals("Cofnij")) continue;
            randomItems.add(entry.getValue().getItem());
        }

        randomItems.add(readyItems.get("Kupa"));
        randomItems.add(readyItems.get("Uniwersalny_bilet"));

        randomItems.add(readyItems.get("$1"));
        randomItems.add(readyItems.get("$1", 24));
        randomItems.add(readyItems.get("$1", 32));
        randomItems.add(new ItemStack(Material.PHANTOM_MEMBRANE, 24));
        randomItems.add(new ItemStack(Material.PHANTOM_MEMBRANE, 32));

        randomItems.add(new ItemStack(Material.ELYTRA));
        randomItems.add(new ItemStack(Material.BEACON));
        randomItems.add(new ItemStack(Material.SHULKER_BOX));
        randomItems.add(new ItemStack(Material.ENDER_CHEST));
        
        int baseAmount = 2;
        for (int i = 1; i < 6; i++) {
            randomItems.add(readyItems.get("$1", baseAmount * i));
            randomItems.add(readyItems.get("Skarpeta", baseAmount * i));

            randomItems.add(new ItemStack(Material.PHANTOM_MEMBRANE, baseAmount * i));
            randomItems.add(new ItemStack(Material.NETHER_WART, baseAmount * i));
            randomItems.add(new ItemStack(Material.NETHER_WART_BLOCK, baseAmount * i));
            randomItems.add(new ItemStack(Material.BROWN_MUSHROOM_BLOCK, baseAmount * i));
            randomItems.add(new ItemStack(Material.RED_MUSHROOM_BLOCK, baseAmount * i));
            randomItems.add(new ItemStack(Material.ACACIA_WOOD, baseAmount * i));
            randomItems.add(new ItemStack(Material.BIRCH_WOOD, baseAmount * i));
            randomItems.add(new ItemStack(Material.DARK_OAK_WOOD, baseAmount * i));
            randomItems.add(new ItemStack(Material.JUNGLE_WOOD, baseAmount * i));
            randomItems.add(new ItemStack(Material.MANGROVE_WOOD, baseAmount * i));
            randomItems.add(new ItemStack(Material.OAK_WOOD, baseAmount * i));
            randomItems.add(new ItemStack(Material.SPRUCE_WOOD, baseAmount * i));
            randomItems.add(new ItemStack(Material.DIAMOND, baseAmount * i));
            randomItems.add(new ItemStack(Material.EMERALD, baseAmount * i));
            randomItems.add(new ItemStack(Material.EMERALD_BLOCK, baseAmount * i));
            randomItems.add(new ItemStack(Material.IRON_BLOCK, baseAmount * i));
            randomItems.add(new ItemStack(Material.IRON_INGOT, baseAmount * i));
            randomItems.add(new ItemStack(Material.GOLD_INGOT, baseAmount * i));
            randomItems.add(new ItemStack(Material.GOLD_BLOCK, baseAmount * i));
            randomItems.add(new ItemStack(Material.JACK_O_LANTERN, baseAmount * i));
            randomItems.add(new ItemStack(Material.LAPIS_LAZULI, baseAmount * i));
            randomItems.add(new ItemStack(Material.LAPIS_BLOCK, baseAmount * i));
            randomItems.add(new ItemStack(Material.CHARCOAL, baseAmount * i));
            randomItems.add(new ItemStack(Material.COAL, baseAmount * i));
            randomItems.add(new ItemStack(Material.COPPER_ORE, baseAmount * i));
            randomItems.add(new ItemStack(Material.COPPER_BLOCK, baseAmount * i));
            randomItems.add(new ItemStack(Material.COPPER_INGOT, baseAmount * i));
            randomItems.add(new ItemStack(Material.SHROOMLIGHT, baseAmount * i));
            randomItems.add(new ItemStack(Material.GRASS_BLOCK, baseAmount * i));
            randomItems.add(new ItemStack(Material.GLOWSTONE, baseAmount * i));
            randomItems.add(new ItemStack(Material.OBSIDIAN, baseAmount * i));
            randomItems.add(new ItemStack(Material.CRYING_OBSIDIAN, baseAmount * i));
            randomItems.add(new ItemStack(Material.FIREWORK_ROCKET, baseAmount * i));
            randomItems.add(new ItemStack(Material.FIREWORK_ROCKET, baseAmount * i));
            randomItems.add(new ItemStack(Material.MUD, baseAmount * i));
            randomItems.add(new ItemStack(Material.RED_SAND, baseAmount * i));
            randomItems.add(new ItemStack(Material.DRIPSTONE_BLOCK, baseAmount * i));
            randomItems.add(new ItemStack(Material.AMETHYST_BLOCK, baseAmount * i));
            randomItems.add(new ItemStack(Material.AMETHYST_SHARD, baseAmount * i));
            randomItems.add(new ItemStack(Material.END_STONE, baseAmount * i));
            randomItems.add(new ItemStack(Material.NETHER_BRICK, baseAmount * i));
            randomItems.add(new ItemStack(Material.BLACKSTONE, baseAmount * i));
            randomItems.add(new ItemStack(Material.STONE_BRICKS, baseAmount * i));
            randomItems.add(new ItemStack(Material.REDSTONE, baseAmount * i));
            randomItems.add(new ItemStack(Material.REDSTONE_BLOCK, baseAmount * i));
            randomItems.add(new ItemStack(Material.TNT, baseAmount * i));
            randomItems.add(new ItemStack(Material.EXPERIENCE_BOTTLE, baseAmount * i));
            randomItems.add(new ItemStack(Material.SHULKER_SHELL, baseAmount * i));
            randomItems.add(new ItemStack(Material.STONE, baseAmount * i));
            randomItems.add(new ItemStack(Material.COBBLESTONE, baseAmount * i));
            randomItems.add(new ItemStack(Material.DIRT, baseAmount * i));
            randomItems.add(new ItemStack(Material.SAND, baseAmount * i));
            randomItems.add(new ItemStack(Material.DEEPSLATE, baseAmount * i));
            randomItems.add(new ItemStack(Material.GLASS, baseAmount * i));
            randomItems.add(new ItemStack(Material.MOSSY_COBBLESTONE, baseAmount * i));
            randomItems.add(new ItemStack(Material.PURPUR_BLOCK, baseAmount * i));
            randomItems.add(new ItemStack(Material.TERRACOTTA, baseAmount * i));
            randomItems.add(new ItemStack(Material.GRANITE, baseAmount * i));
            randomItems.add(new ItemStack(Material.DIORITE, baseAmount * i));
            randomItems.add(new ItemStack(Material.ANDESITE, baseAmount * i));
            randomItems.add(new ItemStack(Material.CLAY, baseAmount * i));
            randomItems.add(new ItemStack(Material.GRAVEL, baseAmount * i));
            randomItems.add(new ItemStack(Material.SANDSTONE, baseAmount * i));
        }

        randomItems.add(new ItemStack(Material.GOLDEN_AXE));
        randomItems.add(new ItemStack(Material.IRON_PICKAXE));
        randomItems.add(new ItemStack(Material.GOLDEN_PICKAXE));
        randomItems.add(new ItemStack(Material.DIAMOND_SHOVEL));
        randomItems.add(new ItemStack(Material.DIAMOND_PICKAXE));
        randomItems.add(new ItemStack(Material.NAME_TAG));
        randomItems.add(new ItemStack(Material.SADDLE));
        randomItems.add(new ItemStack(Material.REINFORCED_DEEPSLATE));

        prezentItems.add(readyItems.get("Skrzynia_smoka"));
        prezentItems.add(readyItems.get("Kupa"));
        prezentItems.add(readyItems.get("Uniwersalny_bilet"));
        prezentItems.add(readyItems.get("$1"));

        for (int i = 1; i < 5; i++) {
            prezentItems.add(readyItems.get("$1", i));
        }

        prezentItems.add(new ItemStack(Material.EMERALD, 16));
        prezentItems.add(new ItemStack(Material.PHANTOM_MEMBRANE, 16));

        new CommandBase("openchest", 2, false) {
            @Override
            public boolean onCommand(CommandSender sender, String[] arguments) {
                ItemStack itemStack = readyItems.get(arguments[0]);
                if (itemStack == null) return false;

                Player player = Bukkit.getPlayer(arguments[1]);
                if (player == null) {
                    sender.sendMessage("player is null");
                    return true;
                }

                Location location = player.getLocation().add(player.getLocation().getDirection().multiply(3)).add(0, 2, 0);
                location.setY(player.getLocation().getY() + 1);

                World world = location.getWorld();

                if (itemStack.equals(readyItems.get("Skrzynia_smoka"))) {
                    playVisuals(world, location);
                    playSounds(world, location);

                    getScheduler().runTaskLaterAsynchronously(Supporter.getPlugin(), () -> {
                        assert world != null;
                        getScheduler().runTask(Supporter.getPlugin(), () -> {
                            renderLateEffects(location, world);

                            Item item = world.dropItem(location, randomItems.get(new Random().nextInt(randomItems.size())));
                            updateItemEntitySettings(player, item);

                            getScheduler().runTaskAsynchronously(Supporter.getPlugin(), () -> {
                                ItemMeta itemMeta = item.getItemStack().getItemMeta();
                                if (itemMeta.hasDisplayName()) broadcastMessage(getBroadcastPrefix() + colorize(player.getName() + " znalazl " + item.getItemStack().getAmount() +  "x " + itemMeta.getDisplayName() + "&f w &cSkrzyni Smoka"));
                            });
                        });
                    }, 100);

                    return true;
                }

                if (itemStack.equals(readyItems.get("Prezent"))) {
                    assert world != null;
                    renderLateEffects(location, world);

                    //dodac tu inne rzeczy
                    int chance = new Random().nextInt(4);
                    if (chance > 1) {
                        Item item = world.dropItem(location, prezentItems.get(new Random().nextInt(prezentItems.size())));
                        updateItemEntitySettings(player, item);
                    } else {
                        dispatchCommand(getConsoleSender(), "dajpunkty " + player.getName() + " 3");
                    }
                }

                return true;
            }

            @Override
            public String getUsage() {
                return "/openchest Prezent/Skrzynia_smoka <nick>";
            }
        }.setPermission("claimworld.admin");
    }
}