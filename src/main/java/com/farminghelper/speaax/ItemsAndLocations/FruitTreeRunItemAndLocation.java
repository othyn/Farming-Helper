package com.farminghelper.speaax.ItemsAndLocations;

import com.farminghelper.speaax.FarmingHelperConfig;
import com.farminghelper.speaax.FarmingHelperPlugin;
import com.farminghelper.speaax.ItemRequirement;
import com.farminghelper.speaax.Location;
import net.runelite.api.Client;
import net.runelite.api.ItemID;
import net.runelite.api.coords.WorldPoint;

import java.util.*;

public class FruitTreeRunItemAndLocation {
    private FarmingHelperConfig config;

    private Client client;
    private FarmingHelperPlugin plugin;

    public Location brimhavenFruitTreeLocation;
    public Location catherbyFruitTreeLocation;
    public Location farmingGuildFruitTreeLocation;
    public Location gnomeStrongholdFruitTreeLocation;
    public Location lletyaFruitTreeLocation;
    public Location treeGnomeVillageFruitTreeLocation;


    public List<Location> locations = new ArrayList<>();
    public FruitTreeRunItemAndLocation() {
    }

    public FruitTreeRunItemAndLocation(FarmingHelperConfig config, Client client, FarmingHelperPlugin plugin) {
        this.config = config;
        this.client = client;
        this.plugin = plugin;
    }

    public Map<Integer, Integer> getFruitTreeItems() {
        return getAllItemRequirements(locations);
    }

    public List<ItemRequirement> getHouseTeleportItemRequirements() {
        FarmingHelperConfig.OptionEnumHouseTele selectedOption = config.enumConfigHouseTele();
        List<ItemRequirement> itemRequirements = new ArrayList<>();

        switch (selectedOption) {
            case Law_air_earth_runes:
                itemRequirements.add(new ItemRequirement(ItemID.AIR_RUNE, 1));
                itemRequirements.add(new ItemRequirement(ItemID.EARTH_RUNE, 1));
                itemRequirements.add(new ItemRequirement(ItemID.LAW_RUNE, 1));
                break;
                /*
            case Law_dust_runes:
                itemRequirements.add(new ItemRequirement(ItemID.DUST_RUNE, 1));
                itemRequirements.add(new ItemRequirement(ItemID.LAW_RUNE, 1));
                break;

                 */
            case Teleport_To_House:
                itemRequirements.add(new ItemRequirement(ItemID.TELEPORT_TO_HOUSE, 1));
                break;
            case Construction_cape:
                itemRequirements.add(new ItemRequirement(ItemID.CONSTRUCT_CAPE, 1));
                break;
            case Construction_cape_t:
                itemRequirements.add(new ItemRequirement(ItemID.CONSTRUCT_CAPET, 1));
                break;
            case Max_cape:
                itemRequirements.add(new ItemRequirement(ItemID.MAX_CAPE, 1));
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + selectedOption);
        }

        return itemRequirements;
    }

    public Map<Integer, Integer> getAllItemRequirements(List<Location> locations) {
        Map<Integer, Integer> allRequirements = new HashMap<>();
        setupFruitTreeLocations();
        // Add other items and merge them with allRequirements
        for (Location location : locations) {
            if (plugin.getFruitTreeLocationEnabled(location.getName())) {
                //ItemID.GUAM_SEED is default for herb seeds, code later will allow for any seed to be used, just needed a placeholder ID
                //allRequirements.merge(ItemID.GUAM_SEED, 1, Integer::sum);
                allRequirements.merge(ItemID.APPLE_SAPLING, 1, Integer::sum);
                allRequirements.merge(ItemID.COINS_995, 200, Integer::sum);
                Location.Teleport teleport = location.getSelectedTeleport();
                Map<Integer, Integer> locationRequirements = teleport.getItemRequirements();
                for (Map.Entry<Integer, Integer> entry : locationRequirements.entrySet()) {
                    int itemId = entry.getKey();
                    int quantity = entry.getValue();

                    if (itemId == ItemID.CONSTRUCT_CAPE || itemId == ItemID.CONSTRUCT_CAPET || itemId == ItemID.MAX_CAPE || itemId == ItemID.ROYAL_SEED_POD) {
                        allRequirements.merge(itemId, quantity, (oldValue, newValue) -> Math.min(1, oldValue + newValue));
                    } else {
                        allRequirements.merge(itemId, quantity, Integer::sum);
                    }
                }
            }
        }
        //allRequirements.merge(ItemID.SEED_DIBBER, 1, Integer::sum);
        allRequirements.merge(ItemID.SPADE, 1, Integer::sum);
        allRequirements.merge(ItemID.BOTTOMLESS_COMPOST_BUCKET_22997, 1, Integer::sum);
        allRequirements.merge(ItemID.MAGIC_SECATEURS, 1, Integer::sum);
        if(config.generalRake()){allRequirements.merge(ItemID.RAKE, 1, Integer::sum);}
        return allRequirements;
    }
    public void setupFruitTreeLocations() {
        // Clear the existing locations list
        locations.clear();

        brimhavenFruitTreeLocation = new Location(FarmingHelperConfig::enumFruitTreeBrimhavenTeleport, config, "Brimhaven", false);
        List<ItemRequirement> brimhavenFruitTreeTeleportItems = Arrays.asList(
                new ItemRequirement(ItemID.COINS_995, 30),
                new ItemRequirement(ItemID.LAW_RUNE, 2),
                new ItemRequirement(ItemID.WATER_RUNE, 2));
        WorldPoint brimhavenFruitTreePatchPoint = new WorldPoint(2764, 3212, 0);
        Location.Teleport brimhavenFruitTreeTeleport = brimhavenFruitTreeLocation.new Teleport(
                "Ardougne_teleport",
                Location.TeleportCategory.SPELLBOOK,
                "Teleport to Ardougne with Spellbook and run to Brimhaven.",
                0,
                "null",
                218,
                38,
                10547,
                brimhavenFruitTreePatchPoint,
                brimhavenFruitTreeTeleportItems);
        brimhavenFruitTreeLocation.addTeleportOption(brimhavenFruitTreeTeleport);
        locations.add(brimhavenFruitTreeLocation);

        catherbyFruitTreeLocation = new Location(FarmingHelperConfig::enumFruitTreeCatherbyTeleport, config, "Catherby", false);
        List<ItemRequirement> catherbyFruitTreeTeleportItems = getHouseTeleportItemRequirements();
        WorldPoint cathebyFruitTreePatchPoint = new WorldPoint(2860, 3433, 0);
        Location.Teleport catherbyFruitTreeTeleport = catherbyFruitTreeLocation.new Teleport(
                "Portal_nexus",
                Location.TeleportCategory.PORTAL_NEXUS,
                "Teleport to Catherby with Portal nexus.",
                0,
                "null",
                17,
                13,
                11061,
                cathebyFruitTreePatchPoint,
                catherbyFruitTreeTeleportItems);
        catherbyFruitTreeLocation.addTeleportOption(catherbyFruitTreeTeleport);
        locations.add(catherbyFruitTreeLocation);

        farmingGuildFruitTreeLocation = new Location(FarmingHelperConfig::enumFruitTreeFarmingGuildTeleport, config, "Farming Guild", false);
        List<ItemRequirement> farmingGuildFruitTreeTeleportItems = getHouseTeleportItemRequirements();
        WorldPoint farmingGuildFruitTreePatchPoint = new WorldPoint(1243, 3759, 0);
        Location.Teleport farmingGuildFruitTreeTeleport = farmingGuildFruitTreeLocation.new Teleport(
                "Jewellery_box",
                Location.TeleportCategory.JEWELLERY_BOX,
                "Teleport to Farming Guild with Jewellery box.",
                0,
                "null",
                17,
                13,
                4922,
                farmingGuildFruitTreePatchPoint,
                farmingGuildFruitTreeTeleportItems);
        farmingGuildFruitTreeLocation.addTeleportOption(farmingGuildFruitTreeTeleport);
        locations.add(farmingGuildFruitTreeLocation);

        gnomeStrongholdFruitTreeLocation = new Location(FarmingHelperConfig::enumFruitTreeGnomeStrongholdTeleport, config, "Gnome Stronghold", false);
        List<ItemRequirement> gnomeStrongholdFruitTreeTeleportItems = Arrays.asList(
                new ItemRequirement(ItemID.ROYAL_SEED_POD, 1));
        WorldPoint gnomeStrongholdFruitTreePatchPoint = new WorldPoint(2475, 3446, 0);
        Location.Teleport gnomeStrongholdFruitTreeTeleport = gnomeStrongholdFruitTreeLocation.new Teleport(
                "Royal_seed_pod",
                Location.TeleportCategory.ITEM,
                "Teleport to Gnome Stronghold with Royal seed pod.",
                ItemID.ROYAL_SEED_POD,
                "null",
                0,
                0,
                9782,
                gnomeStrongholdFruitTreePatchPoint,
                gnomeStrongholdFruitTreeTeleportItems);
        gnomeStrongholdFruitTreeLocation.addTeleportOption(gnomeStrongholdFruitTreeTeleport);
        locations.add(gnomeStrongholdFruitTreeLocation);

        lletyaFruitTreeLocation = new Location(FarmingHelperConfig::enumFruitTreeLletyaTeleport, config, "Lletya", false);
        List<ItemRequirement> lletyaFruitTreeTeleportItems = Arrays.asList(
                new ItemRequirement(ItemID.TELEPORT_CRYSTAL_1, 1));
        WorldPoint lletyaFruitTreePatchPoint = new WorldPoint(2346, 3162, 0);
        Location.Teleport lletyaFruitTreeTeleport = lletyaFruitTreeLocation.new Teleport(
                "Teleport_crystal",
                Location.TeleportCategory.ITEM,
                "Teleport to Lletya with Teleport crystal.",
                ItemID.TELEPORT_CRYSTAL_1,
                "null",
                0,
                0,
                9265,
                lletyaFruitTreePatchPoint,
                lletyaFruitTreeTeleportItems);
        lletyaFruitTreeLocation.addTeleportOption(lletyaFruitTreeTeleport);
        locations.add(lletyaFruitTreeLocation);

        treeGnomeVillageFruitTreeLocation = new Location(FarmingHelperConfig::enumFruitTreeTreeGnomeVillageTeleport, config, "Tree Gnome Village", false);
        List<ItemRequirement> treeGnomeVillageFruitTreeTeleportItems = Arrays.asList(
                new ItemRequirement(ItemID.ROYAL_SEED_POD, 1));
        WorldPoint treeGnomeVillageFruitTreePatchPoint = new WorldPoint(2490, 3180, 0);
        Location.Teleport treeGnomeVillageFruitTreeTeleport = treeGnomeVillageFruitTreeLocation.new Teleport(
                "Royal_seed_pod",
                Location.TeleportCategory.ITEM,
                "Teleport to Tree Gnome Village with Royal seed pod and use Spirit tree to Tree Gnome Village.",
                ItemID.ROYAL_SEED_POD,
                "null",
                0,
                0,
                9782,
                treeGnomeVillageFruitTreePatchPoint,
                treeGnomeVillageFruitTreeTeleportItems);
        treeGnomeVillageFruitTreeLocation.addTeleportOption(treeGnomeVillageFruitTreeTeleport);
        locations.add(treeGnomeVillageFruitTreeLocation);

    }
}