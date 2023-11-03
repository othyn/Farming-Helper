package com.farminghelper.speaax.ItemsAndLocations;

import com.farminghelper.speaax.FarmingHelperConfig;
import com.farminghelper.speaax.FarmingHelperPlugin;
import com.farminghelper.speaax.ItemRequirement;
import com.farminghelper.speaax.Location;
import net.runelite.api.Client;
import net.runelite.api.ItemID;
import net.runelite.api.coords.WorldPoint;

import java.util.*;

public class TreeRunItemAndLocation extends ItemAndLocation {
    public Location faladorTreeLocation;
    public Location farmingGuildTreeLocation;
    public Location gnomeStrongholdTreeLocation;
    public Location lumbridgeTreeLocation;
    public Location taverleyTreeLocation;
    public Location varrockTreeLocation;

    public TreeRunItemAndLocation() {
    }

    public TreeRunItemAndLocation(FarmingHelperConfig config, Client client, FarmingHelperPlugin plugin) {
        super(config, client, plugin);
    }

    public Map<Integer, Integer> getTreeItems() {
        return getAllItemRequirements(locations);
    }

    public Map<Integer, Integer> getAllItemRequirements(List<Location> locations) {
        Map<Integer, Integer> allRequirements = new HashMap<>();

        setupTreeLocations();

        // Add other items and merge them with allRequirements
        for (Location location : locations) {
            if (plugin.getTreeLocationEnabled(location.getName())) {
                //ItemID.GUAM_SEED is default for herb seeds, code later will allow for any seed to be used, just needed a placeholder ID
                //allRequirements.merge(ItemID.GUAM_SEED, 1, Integer::sum);
                allRequirements.merge(ItemID.OAK_SAPLING, 1, Integer::sum);
                allRequirements.merge(ItemID.COINS_995, 200, Integer::sum);
                Location.Teleport teleport = location.getSelectedTeleport();
                Map<Integer, Integer> locationRequirements = teleport.getItemRequirements();

                for (Map.Entry<Integer, Integer> entry : locationRequirements.entrySet()) {
                    int itemId = entry.getKey();
                    int quantity = entry.getValue();

                    if (itemId == ItemID.CONSTRUCT_CAPE || itemId == ItemID.CONSTRUCT_CAPET || itemId == ItemID.MAX_CAPE) {
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

        if (config.generalRake()) {
            allRequirements.merge(ItemID.RAKE, 1, Integer::sum);
        }

        return allRequirements;
    }

    public void setupTreeLocations() {
        // Clear the existing locations list
        locations.clear();

        faladorTreeLocation = new Location(FarmingHelperConfig::enumTreeFaladorTeleport, config, "Falador", false);
        List<ItemRequirement> faladorTreeTeleportItems = Arrays.asList(
                new ItemRequirement(ItemID.AIR_RUNE, 3),
                new ItemRequirement(ItemID.LAW_RUNE, 1),
                new ItemRequirement(ItemID.WATER_RUNE, 1));
        WorldPoint faladorTreePatchPoint = new WorldPoint(3000, 3373, 0);
        Location.Teleport faladorTreeTeleport = faladorTreeLocation.new Teleport(
                "Falador_teleport",
                Location.TeleportCategory.SPELLBOOK,
                "Teleport to Falador with Spellbook and run to Falador park.",
                0,
                "null",
                218,
                27,
                11828,
                faladorTreePatchPoint,
                faladorTreeTeleportItems);
        faladorTreeLocation.addTeleportOption(faladorTreeTeleport);
        locations.add(faladorTreeLocation);

        farmingGuildTreeLocation = new Location(FarmingHelperConfig::enumTreeFarmingGuildTeleport, config, "Farming Guild", false);
        List<ItemRequirement> farmingGuildTreeTeleportItems = getHouseTeleportItemRequirements();
        WorldPoint farmingGuildTreePatchPoint = new WorldPoint(1232, 3736, 0);
        Location.Teleport farmingGuildTreeTeleport = farmingGuildTreeLocation.new Teleport(
                "Jewellery_box",
                Location.TeleportCategory.JEWELLERY_BOX,
                "Teleport to Farming Guild with Jewellery box.",
                0,
                "null",
                0,
                0,
                4922,
                farmingGuildTreePatchPoint,
                farmingGuildTreeTeleportItems);
        farmingGuildTreeLocation.addTeleportOption(farmingGuildTreeTeleport);
        locations.add(farmingGuildTreeLocation);

        gnomeStrongholdTreeLocation = new Location(FarmingHelperConfig::enumTreeGnomeStrongoldTeleport, config, "Gnome Stronghold", false);
        List<ItemRequirement> gnomeStrongholdTreeTeleportItems = Arrays.asList(
                new ItemRequirement(ItemID.ROYAL_SEED_POD, 1));
        WorldPoint gnomeStrongholdTreePatchPoint = new WorldPoint(2436, 3415, 0);
        Location.Teleport gnomeStrongholdTreeTeleport = gnomeStrongholdTreeLocation.new Teleport(
                "Royal_seed_pod",
                Location.TeleportCategory.ITEM,
                "Teleport to Gnome Stronghold with Royal seed pod.",
                ItemID.ROYAL_SEED_POD,
                "null",
                0,
                0,
                9782,
                gnomeStrongholdTreePatchPoint,
                gnomeStrongholdTreeTeleportItems);
        gnomeStrongholdTreeLocation.addTeleportOption(gnomeStrongholdTreeTeleport);
        locations.add(gnomeStrongholdTreeLocation);

        lumbridgeTreeLocation = new Location(FarmingHelperConfig::enumTreeLumbridgeTeleport, config, "Lumbridge", false);
        List<ItemRequirement> lumbridgeTreeTeleportItems = Arrays.asList(
                new ItemRequirement(ItemID.AIR_RUNE, 3),
                new ItemRequirement(ItemID.LAW_RUNE, 1),
                new ItemRequirement(ItemID.EARTH_RUNE, 1));
        WorldPoint lumbridgeTreePatchPoint = new WorldPoint(3193, 3231, 0);
        Location.Teleport lumbridgeTreeTeleport = lumbridgeTreeLocation.new Teleport(
                "Lumbridge_teleport",
                Location.TeleportCategory.SPELLBOOK,
                "Teleport to Lumbridge with spellbook.",
                0,
                "null",
                218,
                24,
                12850,
                lumbridgeTreePatchPoint,
                lumbridgeTreeTeleportItems);
        lumbridgeTreeLocation.addTeleportOption(lumbridgeTreeTeleport);
        locations.add(lumbridgeTreeLocation);

        taverleyTreeLocation = new Location(FarmingHelperConfig::enumTreeTaverleyTeleport, config, "Taverley", false);
        List<ItemRequirement> taverleyTreeTeleportItems = Arrays.asList(
                new ItemRequirement(ItemID.AIR_RUNE, 3),
                new ItemRequirement(ItemID.LAW_RUNE, 1),
                new ItemRequirement(ItemID.WATER_RUNE, 1));
        WorldPoint taverlyPatchPoint = new WorldPoint(2936, 3438, 0);
        Location.Teleport taverleyTreeTeleport = taverleyTreeLocation.new Teleport(
                "Falador_teleport",
                Location.TeleportCategory.SPELLBOOK,
                "Teleport to Falador with spellbook and run to Taverly.",
                0,
                "null",
                218,
                27,
                11828,
                taverlyPatchPoint,
                taverleyTreeTeleportItems);
        taverleyTreeLocation.addTeleportOption(taverleyTreeTeleport);
        locations.add(taverleyTreeLocation);

        varrockTreeLocation = new Location(FarmingHelperConfig::enumTreeVarrockTeleport, config, "Varrock", false);
        List<ItemRequirement> varrockTreeTeleportItems = Arrays.asList(
                new ItemRequirement(ItemID.AIR_RUNE, 3),
                new ItemRequirement(ItemID.LAW_RUNE, 1),
                new ItemRequirement(ItemID.FIRE_RUNE, 1));
        WorldPoint varrockTreePatchPoint = new WorldPoint(3229, 3459, 0);
        Location.Teleport varrockTreeTeleport = varrockTreeLocation.new Teleport(
                "Varrock_teleport",
                Location.TeleportCategory.SPELLBOOK,
                "Teleport to Varrock with spellbook.",
                0,
                "null",
                218,
                21,
                12853,
                varrockTreePatchPoint,
                varrockTreeTeleportItems);
        varrockTreeLocation.addTeleportOption(varrockTreeTeleport);
        locations.add(varrockTreeLocation);
    }
}