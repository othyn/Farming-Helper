package com.farminghelper.speaax.ItemsAndLocations;

import com.farminghelper.speaax.FarmingHelperConfig;
import com.farminghelper.speaax.FarmingHelperPlugin;
import com.farminghelper.speaax.ItemRequirement;
import com.farminghelper.speaax.Location;
import net.runelite.api.Client;
import net.runelite.api.ItemID;
import net.runelite.api.coords.WorldPoint;

import java.util.*;

public class HerbRunItemAndLocation {
    private FarmingHelperConfig config;

    private Client client;
    private FarmingHelperPlugin plugin;

    public Location ardougneLocation;
    public Location catherbyLocation;
    public Location faladorLocation;
    public Location farmingGuildLocation;
    public Location harmonyLocation;
    public Location kourendLocation;
    public Location morytaniaLocation;
    public Location trollStrongholdLocation;
    public Location weissLocation;

    public List<Location> locations = new ArrayList<>();
    public HerbRunItemAndLocation() {
    }

    public HerbRunItemAndLocation(FarmingHelperConfig config, Client client, FarmingHelperPlugin plugin) {
        this.config = config;
        this.client = client;
        this.plugin = plugin;
    }

    public Map<Integer, Integer> getHerbItems() {
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
            case Law_dust_runes:
                itemRequirements.add(new ItemRequirement(ItemID.DUST_RUNE, 1));
                itemRequirements.add(new ItemRequirement(ItemID.LAW_RUNE, 1));
                break;
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
        setupHerbLocations();
        // Add other items and merge them with allRequirements
        for (Location location : locations) {
            if (plugin.getHerbLocationEnabled(location.getName())) {
                //ItemID.GUAM_SEED is default for herb seeds, code later will allow for any seed to be used, just needed a placeholder ID
                allRequirements.merge(ItemID.GUAM_SEED, 1, Integer::sum);
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
                if(location.getFarmLimps() && config.generalLimpwurt()) {
                    allRequirements.merge(ItemID.LIMPWURT_SEED, 1, Integer::sum);
                }
            }
        }
        allRequirements.merge(ItemID.SEED_DIBBER, 1, Integer::sum);
        allRequirements.merge(ItemID.SPADE, 1, Integer::sum);
        allRequirements.merge(ItemID.BOTTOMLESS_COMPOST_BUCKET_22997, 1, Integer::sum);
        allRequirements.merge(ItemID.MAGIC_SECATEURS, 1, Integer::sum);
        if(config.generalRake()){allRequirements.merge(ItemID.RAKE, 1, Integer::sum);}
        return allRequirements;
    }
    public void setupHerbLocations() {
        // Clear the existing locations list
        locations.clear();

        WorldPoint ardoiugneHerbPatchPoint = new WorldPoint(2670, 3374, 0);
        ardougneLocation = new Location(FarmingHelperConfig::enumOptionEnumArdougneTeleport, config, "Ardougne", true);
        List<ItemRequirement> ardougneArdyCloak2Item = Arrays.asList(
                new ItemRequirement(ItemID.ARDOUGNE_CLOAK_2, 1));
        Location.Teleport ardougneArdyCloak2 = ardougneLocation.new Teleport(
                "Ardy_Cloak_2",
                Location.TeleportCategory.ITEM,
                "Teleport to Ardougne with Ardougne cloak.",
                ItemID.ARDOUGNE_CLOAK_2,
                "Farm Teleport",
                0,
                0,
                10548,
                ardoiugneHerbPatchPoint,
                ardougneArdyCloak2Item
        );
        List<ItemRequirement> ardougneArdyCloak3Item = Arrays.asList(
                new ItemRequirement(ItemID.ARDOUGNE_CLOAK_3, 1));
        Location.Teleport ardougneArdyCloak3 = ardougneLocation.new Teleport(
                "Ardy_Cloak_3",
                Location.TeleportCategory.ITEM,
                "Teleport to Ardougne with Ardougne cloak.",
                ItemID.ARDOUGNE_CLOAK_3,
                "Farm Teleport",
                0,
                0,
                10548,
                ardoiugneHerbPatchPoint,
                ardougneArdyCloak3Item
        );
        List<ItemRequirement> ardougneArdyCloak4Item = Arrays.asList(
                new ItemRequirement(ItemID.ARDOUGNE_CLOAK_4, 1)
        );
        Location.Teleport ardougneArdyCloak4 = ardougneLocation.new Teleport(
                "Ardy_Cloak_4",
                Location.TeleportCategory.ITEM,
                "Teleport to Ardougne with Ardougne cloak.",
                ItemID.ARDOUGNE_CLOAK_4,
                "Farm Teleport",
                0,
                0,
                10548,
                ardoiugneHerbPatchPoint,
                ardougneArdyCloak4Item
        );
        ardougneLocation.addTeleportOption(ardougneArdyCloak2);
        ardougneLocation.addTeleportOption(ardougneArdyCloak3);
        ardougneLocation.addTeleportOption(ardougneArdyCloak4);
        locations.add(ardougneLocation);

        WorldPoint catherbyHerbPatchPoint = new WorldPoint(2813, 3463, 0);
        catherbyLocation = new Location(FarmingHelperConfig::enumOptionEnumCatherbyTeleport, config, "Catherby", true);
        List<ItemRequirement> catherbyPortalNexusItems = getHouseTeleportItemRequirements();
        Location.Teleport catherbyPortalNexus = catherbyLocation.new Teleport(
                "Portal_nexus",
                Location.TeleportCategory.PORTAL_NEXUS,
                "Teleport to Catherby with Portal nexus.",
                0,
                "null",
                17,
                13,
                11061,
                catherbyHerbPatchPoint,
                catherbyPortalNexusItems
        );
        catherbyLocation.addTeleportOption(catherbyPortalNexus);
        locations.add(catherbyLocation);

        WorldPoint faladorHerbPatchPoint = new WorldPoint(3058, 3307, 0);
        faladorLocation = new Location(FarmingHelperConfig::enumOptionEnumFaladorTeleport, config, "Falador", true);
        List<ItemRequirement> faladorExplorersRing2Item = Arrays.asList(
                new ItemRequirement(ItemID.EXPLORERS_RING_2, 1));
        Location.Teleport faladorExplorersRing2 = faladorLocation.new Teleport(
                "Explorers_ring_2",
                Location.TeleportCategory.ITEM,
                "Teleport to Falador with Explorers ring.",
                ItemID.EXPLORERS_RING_2,
                "Teleport",
                0,
                0,
                12083,
                faladorHerbPatchPoint,
                faladorExplorersRing2Item);

        List<ItemRequirement> faladorExplorersRing3Item = Arrays.asList(
                new ItemRequirement(ItemID.EXPLORERS_RING_3, 1));
        Location.Teleport faladorExplorersRing3 = faladorLocation.new Teleport(
                "Explorers_ring_3",
                Location.TeleportCategory.ITEM,
                "Teleport to Falador with Explorers ring.",
                ItemID.EXPLORERS_RING_3,
                "Teleport",
                0,
                0,
                12083,
                faladorHerbPatchPoint,
                faladorExplorersRing3Item);

        List<ItemRequirement> faladorExplorersRing4Item = Arrays.asList(
                new ItemRequirement(ItemID.EXPLORERS_RING_4, 1));
        Location.Teleport faladorExplorersRing4 = faladorLocation.new Teleport(
                "Explorers_ring_4",
                Location.TeleportCategory.ITEM,
                "Teleport to Falador with Explorers ring.",
                ItemID.EXPLORERS_RING_4,
                "Teleport",
                0,
                0,
                12083,
                faladorHerbPatchPoint,
                faladorExplorersRing4Item);

        faladorLocation.addTeleportOption(faladorExplorersRing2);
        faladorLocation.addTeleportOption(faladorExplorersRing3);
        faladorLocation.addTeleportOption(faladorExplorersRing4);
        locations.add(faladorLocation);

        WorldPoint farmingGuildHerbPatchPoint = new WorldPoint(1238, 3726, 0);
        farmingGuildLocation = new Location(FarmingHelperConfig::enumOptionEnumFarmingGuildTeleport, config, "Farming Guild", true);
        List<ItemRequirement> farmingGuildJewelleryBoxItems = getHouseTeleportItemRequirements();
        Location.Teleport farmingGuildJewelleryBox = farmingGuildLocation.new Teleport(
                "Jewellery_box",
                Location.TeleportCategory.JEWELLERY_BOX,
                "Teleport to Farming guild with Jewellery box.",
                29155,
                "null",
                0,
                0,
                4922,
                farmingGuildHerbPatchPoint,
                farmingGuildJewelleryBoxItems
        );
        farmingGuildLocation.addTeleportOption(farmingGuildJewelleryBox);
        locations.add(farmingGuildLocation);

        WorldPoint harmonyHerbPatchPoint = new WorldPoint(3789, 2837, 0);
        harmonyLocation = new Location(FarmingHelperConfig::enumOptionEnumHarmonyTeleport, config, "Harmony Island", false);
        List<ItemRequirement> harmonyPortalNexusItems = getHouseTeleportItemRequirements();
        Location.Teleport harmonyPortalNexus = harmonyLocation.new Teleport(
                "Portal_Nexus",
                Location.TeleportCategory.PORTAL_NEXUS,
                "Teleport to Harmony with Portal Nexus.",
                0,
                "null",
                17,
                13,
                15148,
                harmonyHerbPatchPoint,
                harmonyPortalNexusItems);
        harmonyLocation.addTeleportOption(harmonyPortalNexus);
        locations.add(harmonyLocation);

        WorldPoint kourendHerbPatchPoint = new WorldPoint(1738, 3550, 0);
        kourendLocation = new Location(FarmingHelperConfig::enumOptionEnumKourendTeleport, config, "Kourend", true);
        List<ItemRequirement> kourendXericsTalismanItems = Arrays.asList(
                new ItemRequirement(ItemID.XERICS_TALISMAN, 1)
        );
        Location.Teleport kourendXericsTalisman = kourendLocation.new Teleport(
                "Xerics_Talisman",
                Location.TeleportCategory.ITEM,
                "Teleport to Kourend with Xeric's Talisman.",
                ItemID.XERICS_TALISMAN,
                "Rub",
                187,
                3,
                6967,
                kourendHerbPatchPoint,
                kourendXericsTalismanItems
        );
        List<ItemRequirement> kourendMountedXericsItems = getHouseTeleportItemRequirements();
        Location.Teleport kourendMountedXerics = kourendLocation.new Teleport(
                "Mounted_Xerics",
                Location.TeleportCategory.MOUNTED_XERICS,
                "Teleport to Kourend with Xeric's Talisman in PoH.",
                0,
                "null",
                187,
                3,
                6967,
                kourendHerbPatchPoint,
                kourendMountedXericsItems
        );
        kourendLocation.addTeleportOption(kourendXericsTalisman);
        kourendLocation.addTeleportOption(kourendMountedXerics);
        locations.add(kourendLocation);

        WorldPoint morytaniaHerbPatchPoint = new WorldPoint(3601, 3525, 0);
        morytaniaLocation = new Location(FarmingHelperConfig::enumOptionEnumMorytaniaTeleport, config, "Morytania", true);
        List<ItemRequirement> morytaniaEctophialItems = Arrays.asList(
                new ItemRequirement(ItemID.ECTOPHIAL, 1)
        );
        Location.Teleport morytaniaEctophial = morytaniaLocation.new Teleport(
                "Ectophial",
                Location.TeleportCategory.ITEM,
                "Teleport to Morytania with Ectophial.",
                ItemID.ECTOPHIAL,
                "null",
                0,
                0,
                14647,
                morytaniaHerbPatchPoint,
                morytaniaEctophialItems
        );
        morytaniaLocation.addTeleportOption(morytaniaEctophial);
        locations.add(morytaniaLocation);

        WorldPoint trollStrongholhHerbPatchPoint = new WorldPoint(2824, 3696, 0);
        trollStrongholdLocation = new Location(FarmingHelperConfig::enumOptionEnumTrollStrongholdTeleport, config, "Troll Stronghold", false);
        List<ItemRequirement> tsStonyBasaltItems = Arrays.asList(
                new ItemRequirement(ItemID.STONY_BASALT, 1)
        );
        Location.Teleport tsStonyBasalt = trollStrongholdLocation.new Teleport(
                "Stony_Basalt",
                Location.TeleportCategory.ITEM,
                "Teleport to Troll Stronghold with Stony Basalt.",
                ItemID.STONY_BASALT,
                "null",
                0,
                0,
                11321,
                trollStrongholhHerbPatchPoint,
                tsStonyBasaltItems
        );
        List<ItemRequirement> tsPortalNexusItems = getHouseTeleportItemRequirements();
        Location.Teleport tsPortalNexus = trollStrongholdLocation.new Teleport(
                "Portal_Nexus",
                Location.TeleportCategory.PORTAL_NEXUS,
                "Teleport to Troll Stronghold with Portal Nexus.",
                0,
                "null",
                17,
                13,
                11321,
                trollStrongholhHerbPatchPoint,
                tsPortalNexusItems
        );
        trollStrongholdLocation.addTeleportOption(tsStonyBasalt);
        trollStrongholdLocation.addTeleportOption(tsPortalNexus);
        locations.add(trollStrongholdLocation);

        WorldPoint weissHerbPatchPoint = new WorldPoint(2847, 3931, 0);
        weissLocation = new Location(FarmingHelperConfig::enumOptionEnumWeissTeleport, config, "Weiss", false);
        List<ItemRequirement> weissIcyBasaltItems = Arrays.asList(
                new ItemRequirement(ItemID.ICY_BASALT, 1)
        );
        Location.Teleport weissIcyBasalt = weissLocation.new Teleport(
                "Icy_Basalt",
                Location.TeleportCategory.ITEM,
                "Teleport to Weiss with Icy Basalt.",
                ItemID.ICY_BASALT,
                "null",
                0,
                0,
                11325,
                weissHerbPatchPoint,
                weissIcyBasaltItems
        );
        List<ItemRequirement> weissPortalNexusItems = getHouseTeleportItemRequirements();
        Location.Teleport weissPortalNexus = weissLocation.new Teleport(
                "Portal_Nexus",
                Location.TeleportCategory.PORTAL_NEXUS,
                "Teleport to Weiss with Portal Nexus.",
                0,
                "null",
                17,
                13,
                11325,
                weissHerbPatchPoint,
                weissPortalNexusItems
        );
        weissLocation.addTeleportOption(weissIcyBasalt);
        weissLocation.addTeleportOption(weissPortalNexus);
        locations.add(weissLocation);

    }



}