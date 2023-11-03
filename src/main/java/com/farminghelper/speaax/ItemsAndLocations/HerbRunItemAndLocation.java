package com.farminghelper.speaax.ItemsAndLocations;

import com.farminghelper.speaax.FarmingHelperConfig;
import com.farminghelper.speaax.FarmingHelperPlugin;
import com.farminghelper.speaax.ItemRequirement;
import com.farminghelper.speaax.Location;
import net.runelite.api.Client;
import net.runelite.api.ItemID;
import net.runelite.api.coords.WorldPoint;

import java.util.*;

public class HerbRunItemAndLocation extends ItemAndLocation {
    public Location ardougneLocation;
    public Location catherbyLocation;
    public Location faladorLocation;
    public Location farmingGuildLocation;
    public Location harmonyLocation;
    public Location kourendLocation;
    public Location morytaniaLocation;
    public Location trollStrongholdLocation;
    public Location weissLocation;

    public HerbRunItemAndLocation() {
    }

    public HerbRunItemAndLocation(FarmingHelperConfig config, Client client, FarmingHelperPlugin plugin) {
        super(config, client, plugin);
    }

    public Map<Integer, Integer> getHerbItems() {
        return getAllItemRequirements(locations);
    }

    public Map<Integer, Integer> getAllItemRequirements(List<Location> locations) {
        Map<Integer, Integer> allRequirements = new HashMap<>();

        setupHerbLocations();

        // Add other items and merge them with allRequirements
        for (Location location : locations) {
            if (plugin.getHerbLocationEnabled(location.getName())) {
                //ItemID.GUAM_SEED is default for herb seeds, code later will allow for any seed to be used, just needed a placeholder ID
                allRequirements.merge(ItemID.GUAM_SEED, 1, Integer::sum);

                if (selectedCompostID() != -1 && selectedCompostID() != ItemID.BOTTOMLESS_COMPOST_BUCKET_22997) {
                    allRequirements.merge(selectedCompostID(), 1, Integer::sum);
                }

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

                    if (selectedCompostID() != -1 && selectedCompostID() != ItemID.BOTTOMLESS_COMPOST_BUCKET_22997) {
                        allRequirements.merge(selectedCompostID(), 1, Integer::sum);
                    }
                }
            }
        }

        allRequirements.merge(ItemID.SEED_DIBBER, 1, Integer::sum);
        allRequirements.merge(ItemID.SPADE, 1, Integer::sum);

        if (selectedCompostID() == ItemID.BOTTOMLESS_COMPOST_BUCKET_22997) {
            allRequirements.merge(ItemID.BOTTOMLESS_COMPOST_BUCKET_22997, 1, Integer::sum);
        }

        allRequirements.merge(ItemID.MAGIC_SECATEURS, 1, Integer::sum);

        if (config.generalRake()) {
            allRequirements.merge(ItemID.RAKE, 1, Integer::sum);
        }

        return allRequirements;
    }

    public void setupHerbLocations() {
        // Clear the existing locations list
        locations.clear();
        WorldPoint ardougneHerbPatchPoint = new WorldPoint(2670, 3374, 0);
        ardougneLocation = new Location(FarmingHelperConfig::enumOptionEnumArdougneTeleport, config, "Ardougne", true);

        List<ItemRequirement> ardougneTeleportItem = Arrays.asList(
                new ItemRequirement(ItemID.LAW_RUNE, 2),
                new ItemRequirement(ItemID.WATER_RUNE, 2));
        Location.Teleport ardougneTeleport = ardougneLocation.new Teleport(
                "Ardougne_teleport",
                Location.TeleportCategory.SPELLBOOK,
                "Teleport to Ardougne with standard spellbook, and run north.",
                0,
                "null",
                218,
                38,
                10547,
                ardougneHerbPatchPoint,
                ardougneTeleportItem
        );
        List<ItemRequirement> ardougneTeleTabItem = Arrays.asList(
                new ItemRequirement(ItemID.ARDOUGNE_TELEPORT, 1));
        Location.Teleport ardougneTeleTab = ardougneLocation.new Teleport(
                "Ardougne_Tele_Tab",
                Location.TeleportCategory.ITEM,
                "Teleport to Ardougne with Ardougne tele tab, and run north.",
                ItemID.ARDOUGNE_TELEPORT,
                "null",
                0,
                0,
                10547,
                ardougneHerbPatchPoint,
                ardougneTeleTabItem
        );

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
                ardougneHerbPatchPoint,
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
                ardougneHerbPatchPoint,
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
                ardougneHerbPatchPoint,
                ardougneArdyCloak4Item
        );

        List<ItemRequirement> ardougneSkillsNecklaceItem = Arrays.asList(
                new ItemRequirement(ItemID.SKILLS_NECKLACE1, 1)
        );
        Location.Teleport ardougneSkillsNecklace = ardougneLocation.new Teleport(
                "Skills_Necklace",
                Location.TeleportCategory.ITEM,
                "Teleport to Fishing guild with Skills necklace, and run east.",
                ItemID.SKILLS_NECKLACE1,
                "null",
                0,
                0,
                10292,
                ardougneHerbPatchPoint,
                ardougneSkillsNecklaceItem
        );
        ardougneLocation.addTeleportOption(ardougneTeleport);
        ardougneLocation.addTeleportOption(ardougneTeleTab);
        ardougneLocation.addTeleportOption(ardougneArdyCloak2);
        ardougneLocation.addTeleportOption(ardougneArdyCloak3);
        ardougneLocation.addTeleportOption(ardougneArdyCloak4);
        ardougneLocation.addTeleportOption(ardougneSkillsNecklace);
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

        List<ItemRequirement> catherbyCamelotTeleportItems = Arrays.asList(
                new ItemRequirement(ItemID.AIR_RUNE, 5),
                new ItemRequirement(ItemID.LAW_RUNE, 1)
        );
        Location.Teleport catherbyCamelotTeleport = catherbyLocation.new Teleport(
                "Camelot_Teleport",
                Location.TeleportCategory.SPELLBOOK,
                "Teleport to Camelot using the standard spellbook, and run east.(If you have configured the teleport to seers you need to right click and teleport to Camelot)",
                0,
                "null",
                218,
                32,
                11062,
                catherbyHerbPatchPoint,
                catherbyCamelotTeleportItems
        );

        List<ItemRequirement> catherbyCamelotTeleTabItems = Arrays.asList(
                new ItemRequirement(ItemID.CAMELOT_TELEPORT, 1)
        );
        Location.Teleport catherbyCamelotTeleTab = catherbyLocation.new Teleport(
                "Camelot_Tele_Tab",
                Location.TeleportCategory.ITEM,
                "Teleport to Camelot using a Camelot tele tab, and run east.(If you have configured the teleport to seers you need to right click and teleport to Camelot)",
                ItemID.CAMELOT_TELEPORT,
                "null",
                0,
                0,
                11062,
                catherbyHerbPatchPoint,
                catherbyCamelotTeleTabItems
        );

        List<ItemRequirement> catherbyTeleTabItems = Arrays.asList(
                new ItemRequirement(ItemID.CATHERBY_TELEPORT, 1)
        );
        Location.Teleport catherbyTeleTab = catherbyLocation.new Teleport(
                "Catherby_Tele_Tab",
                Location.TeleportCategory.ITEM,
                "Teleport to Catherby using Catherby teleport tab.",
                ItemID.CATHERBY_TELEPORT,
                "null",
                0,
                0,
                11061,
                catherbyHerbPatchPoint,
                catherbyTeleTabItems
        );

        catherbyLocation.addTeleportOption(catherbyPortalNexus);
        catherbyLocation.addTeleportOption(catherbyCamelotTeleport);
        catherbyLocation.addTeleportOption(catherbyCamelotTeleTab);
        catherbyLocation.addTeleportOption(catherbyTeleTab);
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

        List<ItemRequirement> faladorTeleportItem = Arrays.asList(
                new ItemRequirement(ItemID.AIR_RUNE, 3),
                new ItemRequirement(ItemID.LAW_RUNE, 1),
                new ItemRequirement(ItemID.WATER_RUNE, 1));
        Location.Teleport faladorTeleport = faladorLocation.new Teleport(
                "Falador_Teleport",
                Location.TeleportCategory.SPELLBOOK,
                "Teleport to Falador with standard spellbook, and run south-east.",
                0,
                "null",
                218,
                27,
                11828,
                faladorHerbPatchPoint,
                faladorTeleportItem);

        List<ItemRequirement> faladorTeleTabItem = Arrays.asList(
                new ItemRequirement(ItemID.FALADOR_TELEPORT, 1));
        Location.Teleport faladorTeleTab = faladorLocation.new Teleport(
                "Falador_Tele_Tab",
                Location.TeleportCategory.ITEM,
                "Teleport to Falador with Falador Tele Tab, and run south-east.",
                ItemID.FALADOR_TELEPORT,
                "null",
                0,
                0,
                11828,
                faladorHerbPatchPoint,
                faladorTeleTabItem);

        List<ItemRequirement> faladorDraynorManorTeleTabItem = Arrays.asList(
                new ItemRequirement(ItemID.DRAYNOR_MANOR_TELEPORT, 1));
        Location.Teleport faladorDraynorManorTeleTab = faladorLocation.new Teleport(
                "Draynor_Tele_Tab",
                Location.TeleportCategory.ITEM,
                "Teleport to Draynor Manor with Draynor Manor Tele Tab, and run south-west.",
                ItemID.DRAYNOR_MANOR_TELEPORT,
                "null",
                0,
                0,
                12340,
                faladorHerbPatchPoint,
                faladorDraynorManorTeleTabItem);

        faladorLocation.addTeleportOption(faladorExplorersRing2);
        faladorLocation.addTeleportOption(faladorExplorersRing3);
        faladorLocation.addTeleportOption(faladorExplorersRing4);
        faladorLocation.addTeleportOption(faladorTeleport);
        faladorLocation.addTeleportOption(faladorTeleTab);
        faladorLocation.addTeleportOption(faladorDraynorManorTeleTab);
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

        List<ItemRequirement> farmingGuildSkillsNecklaceItems = Arrays.asList(
                new ItemRequirement(ItemID.SKILLS_NECKLACE1, 1));
        Location.Teleport farmingGuildSkillsNecklace = farmingGuildLocation.new Teleport(
                "Skills_Necklace",
                Location.TeleportCategory.ITEM,
                "Teleport to Farming guild using Skills necklace.",
                ItemID.SKILLS_NECKLACE1,
                "null",
                0,
                0,
                4922,
                farmingGuildHerbPatchPoint,
                farmingGuildSkillsNecklaceItems
        );
        farmingGuildLocation.addTeleportOption(farmingGuildJewelleryBox);
        farmingGuildLocation.addTeleportOption(farmingGuildSkillsNecklace);
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

        List<ItemRequirement> harmonyTeleTabItems = Arrays.asList(
                new ItemRequirement(ItemID.HARMONY_ISLAND_TELEPORT, 1));
        Location.Teleport harmonyTeleTab = harmonyLocation.new Teleport(
                "Harmony_Tele_Tab",
                Location.TeleportCategory.ITEM,
                "Teleport to Harmony with Harmony Tele Tab.",
                ItemID.HARMONY_ISLAND_TELEPORT,
                "null",
                0,
                0,
                15148,
                harmonyHerbPatchPoint,
                harmonyTeleTabItems);
        harmonyLocation.addTeleportOption(harmonyPortalNexus);
        harmonyLocation.addTeleportOption(harmonyTeleTab);
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