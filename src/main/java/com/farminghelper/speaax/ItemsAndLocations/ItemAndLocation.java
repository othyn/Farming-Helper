package com.farminghelper.speaax.ItemsAndLocations;

import com.farminghelper.speaax.FarmingHelperConfig;
import com.farminghelper.speaax.FarmingHelperPlugin;
import com.farminghelper.speaax.ItemRequirement;
import com.farminghelper.speaax.Location;
import net.runelite.api.Client;
import net.runelite.api.ItemID;
import net.runelite.api.coords.WorldPoint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemAndLocation {
    protected FarmingHelperConfig config;

    protected Client client;

    protected FarmingHelperPlugin plugin;

    public List<Location> locations = new ArrayList<>();

    public enum Place
    {
        ARDOUGNE,
        CATHERBY,
        FALADOR,
        FARMING_GUILD,
        HARMONY,
        KOUREND,
        MORYTANIA,
        TROLL_STRONGHOLD,
        WEISS;

        public WorldPoint herbPatch()
        {
            switch (this) {
                case ARDOUGNE:
                    return new WorldPoint(2670, 3374, 0);
                case CATHERBY:
                    return new WorldPoint(2813, 3463, 0);
                case FALADOR:
                    return new WorldPoint(3058, 3307, 0);
                case FARMING_GUILD:
                    return new WorldPoint(1238, 3726, 0);
                case HARMONY:
                    return new WorldPoint(3789, 2837, 0);
                case KOUREND:
                    return new WorldPoint(1738, 3550, 0);
                case MORYTANIA:
                    return new WorldPoint(3601, 3525, 0);
                case TROLL_STRONGHOLD:
                    return new WorldPoint(2824, 3696, 0);
                case WEISS:
                    return new WorldPoint(2847, 3931, 0);
            }

            return null;
        }

        public Location location(FarmingHelperConfig config)
        {
            switch (this) {
                case ARDOUGNE:
                    return new Location(FarmingHelperConfig::enumOptionEnumArdougneTeleport, config, "Ardougne", true);
                case CATHERBY:
                    return new Location(FarmingHelperConfig::enumOptionEnumCatherbyTeleport, config, "Catherby", true);
                case FALADOR:
                    return new Location(FarmingHelperConfig::enumOptionEnumFaladorTeleport, config, "Falador", true);
                case FARMING_GUILD:
                    return new Location(FarmingHelperConfig::enumOptionEnumFarmingGuildTeleport, config, "Farming Guild", true);
                case HARMONY:
                    return new Location(FarmingHelperConfig::enumOptionEnumHarmonyTeleport, config, "Harmony Island", false);
                case KOUREND:
                    return new Location(FarmingHelperConfig::enumOptionEnumKourendTeleport, config, "Kourend", true);
                case MORYTANIA:
                    return new Location(FarmingHelperConfig::enumOptionEnumMorytaniaTeleport, config, "Morytania", true);
                case TROLL_STRONGHOLD:
                    return new Location(FarmingHelperConfig::enumOptionEnumTrollStrongholdTeleport, config, "Troll Stronghold", false);
                case WEISS:
                    return new Location(FarmingHelperConfig::enumOptionEnumWeissTeleport, config, "Weiss", false);
            }

            return null;
        }
    }

    public ItemAndLocation()
    {
    }

    public ItemAndLocation(FarmingHelperConfig config, Client client, FarmingHelperPlugin plugin)
    {
        this.config = config;
        this.client = client;
        this.plugin = plugin;
    }

    public List<ItemRequirement> getHouseTeleportItemRequirements()
    {
        FarmingHelperConfig.OptionEnumHouseTele selectedOption = config.enumConfigHouseTele();

        List<ItemRequirement> itemRequirements = new ArrayList<>();

        switch (selectedOption) {
            case Law_air_earth_runes:
                itemRequirements.add(new ItemRequirement(ItemID.AIR_RUNE, 1));
                itemRequirements.add(new ItemRequirement(ItemID.EARTH_RUNE, 1));
                itemRequirements.add(new ItemRequirement(ItemID.LAW_RUNE, 1));
                break;

//            case Law_dust_runes:
//                itemRequirements.add(new ItemRequirement(ItemID.DUST_RUNE, 1));
//                itemRequirements.add(new ItemRequirement(ItemID.LAW_RUNE, 1));
//                break;

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

    public Integer selectedCompostID()
    {
        FarmingHelperConfig.OptionEnumCompost selectedCompost = config.enumConfigCompost();

        switch (selectedCompost) {
            case Compost:
                return ItemID.COMPOST;

            case Supercompost:
                return ItemID.SUPERCOMPOST;

            case Ultracompost:
                return ItemID.ULTRACOMPOST;

            case Bottomless:
                return ItemID.BOTTOMLESS_COMPOST_BUCKET_22997;
        }

        return -1;
    }

    public void setupTeleports()
    {
        locations.clear();
    }

    protected void setupArdougneTeleports()
    {
        WorldPoint worldPoint = Place.ARDOUGNE.herbPatch();
        Location location = Place.ARDOUGNE.location(config);

        List<ItemRequirement> ardougneTeleportItem = Arrays.asList(
                new ItemRequirement(ItemID.LAW_RUNE, 2),
                new ItemRequirement(ItemID.WATER_RUNE, 2)
        );
        Location.Teleport ardougneTeleport = location.new Teleport(
                "Ardougne_teleport",
                Location.TeleportCategory.SPELLBOOK,
                "Teleport to Ardougne with standard spellbook, and run north.",
                0,
                "null",
                218,
                38,
                10547,
                worldPoint,
                ardougneTeleportItem
        );

        List<ItemRequirement> ardougneTeleTabItem = Arrays.asList(
                new ItemRequirement(ItemID.ARDOUGNE_TELEPORT, 1)
        );
        Location.Teleport ardougneTeleTab = location.new Teleport(
                "Ardougne_Tele_Tab",
                Location.TeleportCategory.ITEM,
                "Teleport to Ardougne with Ardougne tele tab, and run north.",
                ItemID.ARDOUGNE_TELEPORT,
                "null",
                0,
                0,
                10547,
                worldPoint,
                ardougneTeleTabItem
        );

        List<ItemRequirement> ardougneArdyCloak2Item = Arrays.asList(
                new ItemRequirement(ItemID.ARDOUGNE_CLOAK_2, 1)
        );
        Location.Teleport ardougneArdyCloak2 = location.new Teleport(
                "Ardy_Cloak_2",
                Location.TeleportCategory.ITEM,
                "Teleport to Ardougne with Ardougne cloak.",
                ItemID.ARDOUGNE_CLOAK_2,
                "Farm Teleport",
                0,
                0,
                10548,
                worldPoint,
                ardougneArdyCloak2Item
        );

        List<ItemRequirement> ardougneArdyCloak3Item = Arrays.asList(
                new ItemRequirement(ItemID.ARDOUGNE_CLOAK_3, 1)
        );
        Location.Teleport ardougneArdyCloak3 = location.new Teleport(
                "Ardy_Cloak_3",
                Location.TeleportCategory.ITEM,
                "Teleport to Ardougne with Ardougne cloak.",
                ItemID.ARDOUGNE_CLOAK_3,
                "Farm Teleport",
                0,
                0,
                10548,
                worldPoint,
                ardougneArdyCloak3Item
        );

        List<ItemRequirement> ardougneArdyCloak4Item = Arrays.asList(
                new ItemRequirement(ItemID.ARDOUGNE_CLOAK_4, 1)
        );
        Location.Teleport ardougneArdyCloak4 = location.new Teleport(
                "Ardy_Cloak_4",
                Location.TeleportCategory.ITEM,
                "Teleport to Ardougne with Ardougne cloak.",
                ItemID.ARDOUGNE_CLOAK_4,
                "Farm Teleport",
                0,
                0,
                10548,
                worldPoint,
                ardougneArdyCloak4Item
        );

        List<ItemRequirement> ardougneSkillsNecklaceItem = Arrays.asList(
                new ItemRequirement(ItemID.SKILLS_NECKLACE1, 1)
        );
        Location.Teleport ardougneSkillsNecklace = location.new Teleport(
                "Skills_Necklace",
                Location.TeleportCategory.ITEM,
                "Teleport to Fishing guild with Skills necklace, and run east.",
                ItemID.SKILLS_NECKLACE1,
                "null",
                0,
                0,
                10292,
                worldPoint,
                ardougneSkillsNecklaceItem
        );

        location.addTeleportOption(ardougneTeleport);
        location.addTeleportOption(ardougneTeleTab);
        location.addTeleportOption(ardougneArdyCloak2);
        location.addTeleportOption(ardougneArdyCloak3);
        location.addTeleportOption(ardougneArdyCloak4);
        location.addTeleportOption(ardougneSkillsNecklace);

        locations.add(location);
    }

    protected void setupCatherbyTeleports()
    {
        WorldPoint worldPoint = Place.CATHERBY.herbPatch();
        Location location = Place.CATHERBY.location(config);

        List<ItemRequirement> catherbyPortalNexusItems = getHouseTeleportItemRequirements();
        Location.Teleport catherbyPortalNexus = location.new Teleport(
                "Portal_nexus",
                Location.TeleportCategory.PORTAL_NEXUS,
                "Teleport to Catherby with Portal nexus.",
                0,
                "null",
                17,
                13,
                11061,
                worldPoint,
                catherbyPortalNexusItems
        );

        List<ItemRequirement> catherbyCamelotTeleportItems = Arrays.asList(
                new ItemRequirement(ItemID.AIR_RUNE, 5),
                new ItemRequirement(ItemID.LAW_RUNE, 1)
        );
        Location.Teleport catherbyCamelotTeleport = location.new Teleport(
                "Camelot_Teleport",
                Location.TeleportCategory.SPELLBOOK,
                "Teleport to Camelot using the standard spellbook, and run east.(If you have configured the teleport to seers you need to right click and teleport to Camelot)",
                0,
                "null",
                218,
                32,
                11062,
                worldPoint,
                catherbyCamelotTeleportItems
        );

        List<ItemRequirement> catherbyCamelotTeleTabItems = Arrays.asList(
                new ItemRequirement(ItemID.CAMELOT_TELEPORT, 1)
        );
        Location.Teleport catherbyCamelotTeleTab = location.new Teleport(
                "Camelot_Tele_Tab",
                Location.TeleportCategory.ITEM,
                "Teleport to Camelot using a Camelot tele tab, and run east.(If you have configured the teleport to seers you need to right click and teleport to Camelot)",
                ItemID.CAMELOT_TELEPORT,
                "null",
                0,
                0,
                11062,
                worldPoint,
                catherbyCamelotTeleTabItems
        );

        List<ItemRequirement> catherbyTeleTabItems = Arrays.asList(
                new ItemRequirement(ItemID.CATHERBY_TELEPORT, 1)
        );
        Location.Teleport catherbyTeleTab = location.new Teleport(
                "Catherby_Tele_Tab",
                Location.TeleportCategory.ITEM,
                "Teleport to Catherby using Catherby teleport tab.",
                ItemID.CATHERBY_TELEPORT,
                "null",
                0,
                0,
                11061,
                worldPoint,
                catherbyTeleTabItems
        );

        location.addTeleportOption(catherbyPortalNexus);
        location.addTeleportOption(catherbyCamelotTeleport);
        location.addTeleportOption(catherbyCamelotTeleTab);
        location.addTeleportOption(catherbyTeleTab);

        locations.add(location);
    }

    protected void setupFaladorTeleports()
    {
        WorldPoint worldPoint = Place.FALADOR.herbPatch();
        Location location = Place.FALADOR.location(config);

        List<ItemRequirement> faladorExplorersRing2Item = Arrays.asList(
                new ItemRequirement(ItemID.EXPLORERS_RING_2, 1)
        );
        Location.Teleport faladorExplorersRing2 = location.new Teleport(
                "Explorers_ring_2",
                Location.TeleportCategory.ITEM,
                "Teleport to Falador with Explorers ring.",
                ItemID.EXPLORERS_RING_2,
                "Teleport",
                0,
                0,
                12083,
                worldPoint,
                faladorExplorersRing2Item
        );

        List<ItemRequirement> faladorExplorersRing3Item = Arrays.asList(
                new ItemRequirement(ItemID.EXPLORERS_RING_3, 1)
        );
        Location.Teleport faladorExplorersRing3 = location.new Teleport(
                "Explorers_ring_3",
                Location.TeleportCategory.ITEM,
                "Teleport to Falador with Explorers ring.",
                ItemID.EXPLORERS_RING_3,
                "Teleport",
                0,
                0,
                12083,
                worldPoint,
                faladorExplorersRing3Item
        );

        List<ItemRequirement> faladorExplorersRing4Item = Arrays.asList(
                new ItemRequirement(ItemID.EXPLORERS_RING_4, 1)
        );
        Location.Teleport faladorExplorersRing4 = location.new Teleport(
                "Explorers_ring_4",
                Location.TeleportCategory.ITEM,
                "Teleport to Falador with Explorers ring.",
                ItemID.EXPLORERS_RING_4,
                "Teleport",
                0,
                0,
                12083,
                worldPoint,
                faladorExplorersRing4Item
        );

        List<ItemRequirement> faladorTeleportItem = Arrays.asList(
                new ItemRequirement(ItemID.AIR_RUNE, 3),
                new ItemRequirement(ItemID.LAW_RUNE, 1),
                new ItemRequirement(ItemID.WATER_RUNE, 1)
        );
        Location.Teleport faladorTeleport = location.new Teleport(
                "Falador_Teleport",
                Location.TeleportCategory.SPELLBOOK,
                "Teleport to Falador with standard spellbook, and run south-east.",
                0,
                "null",
                218,
                27,
                11828,
                worldPoint,
                faladorTeleportItem
        );

        List<ItemRequirement> faladorTeleTabItem = Arrays.asList(
                new ItemRequirement(ItemID.FALADOR_TELEPORT, 1)
        );
        Location.Teleport faladorTeleTab = location.new Teleport(
                "Falador_Tele_Tab",
                Location.TeleportCategory.ITEM,
                "Teleport to Falador with Falador Tele Tab, and run south-east.",
                ItemID.FALADOR_TELEPORT,
                "null",
                0,
                0,
                11828,
                worldPoint,
                faladorTeleTabItem
        );

        List<ItemRequirement> faladorDraynorManorTeleTabItem = Arrays.asList(
                new ItemRequirement(ItemID.DRAYNOR_MANOR_TELEPORT, 1)
        );
        Location.Teleport faladorDraynorManorTeleTab = location.new Teleport(
                "Draynor_Tele_Tab",
                Location.TeleportCategory.ITEM,
                "Teleport to Draynor Manor with Draynor Manor Tele Tab, and run south-west.",
                ItemID.DRAYNOR_MANOR_TELEPORT,
                "null",
                0,
                0,
                12340,
                worldPoint,
                faladorDraynorManorTeleTabItem
        );

        location.addTeleportOption(faladorExplorersRing2);
        location.addTeleportOption(faladorExplorersRing3);
        location.addTeleportOption(faladorExplorersRing4);
        location.addTeleportOption(faladorTeleport);
        location.addTeleportOption(faladorTeleTab);
        location.addTeleportOption(faladorDraynorManorTeleTab);

        locations.add(location);
    }

    protected void setupFarmingGuildTeleports()
    {
        WorldPoint worldPoint = Place.FARMING_GUILD.herbPatch();
        Location location = Place.FARMING_GUILD.location(config);

        List<ItemRequirement> farmingGuildJewelleryBoxItems = getHouseTeleportItemRequirements();
        Location.Teleport farmingGuildJewelleryBox = location.new Teleport(
                "Jewellery_box",
                Location.TeleportCategory.JEWELLERY_BOX,
                "Teleport to Farming guild with Jewellery box.",
                29155,
                "null",
                0,
                0,
                4922,
                worldPoint,
                farmingGuildJewelleryBoxItems
        );

        List<ItemRequirement> farmingGuildSkillsNecklaceItems = Arrays.asList(
                new ItemRequirement(ItemID.SKILLS_NECKLACE1, 1)
        );
        Location.Teleport farmingGuildSkillsNecklace = location.new Teleport(
                "Skills_Necklace",
                Location.TeleportCategory.ITEM,
                "Teleport to Farming guild using Skills necklace.",
                ItemID.SKILLS_NECKLACE1,
                "null",
                0,
                0,
                4922,
                worldPoint,
                farmingGuildSkillsNecklaceItems
        );

        location.addTeleportOption(farmingGuildJewelleryBox);
        location.addTeleportOption(farmingGuildSkillsNecklace);

        locations.add(location);
    }

    protected void setupHarmonyTeleports()
    {
        WorldPoint worldPoint = Place.HARMONY.herbPatch();
        Location location = Place.HARMONY.location(config);

        List<ItemRequirement> harmonyPortalNexusItems = getHouseTeleportItemRequirements();
        Location.Teleport harmonyPortalNexus = location.new Teleport(
                "Portal_Nexus",
                Location.TeleportCategory.PORTAL_NEXUS,
                "Teleport to Harmony with Portal Nexus.",
                0,
                "null",
                17,
                13,
                15148,
                worldPoint,
                harmonyPortalNexusItems
        );

        List<ItemRequirement> harmonyTeleTabItems = Arrays.asList(
                new ItemRequirement(ItemID.HARMONY_ISLAND_TELEPORT, 1)
        );
        Location.Teleport harmonyTeleTab = location.new Teleport(
                "Harmony_Tele_Tab",
                Location.TeleportCategory.ITEM,
                "Teleport to Harmony with Harmony Tele Tab.",
                ItemID.HARMONY_ISLAND_TELEPORT,
                "null",
                0,
                0,
                15148,
                worldPoint,
                harmonyTeleTabItems
        );

        location.addTeleportOption(harmonyPortalNexus);
        location.addTeleportOption(harmonyTeleTab);

        locations.add(location);
    }

    protected void setupKourendTeleports()
    {
        WorldPoint worldPoint = Place.KOUREND.herbPatch();
        Location location = Place.KOUREND.location(config);

        List<ItemRequirement> kourendXericsTalismanItems = Arrays.asList(
                new ItemRequirement(ItemID.XERICS_TALISMAN, 1)
        );
        Location.Teleport kourendXericsTalisman = location.new Teleport(
                "Xerics_Talisman",
                Location.TeleportCategory.ITEM,
                "Teleport to Kourend with Xeric's Talisman.",
                ItemID.XERICS_TALISMAN,
                "Rub",
                187,
                3,
                6967,
                worldPoint,
                kourendXericsTalismanItems
        );

        List<ItemRequirement> kourendMountedXericsItems = getHouseTeleportItemRequirements();
        Location.Teleport kourendMountedXerics = location.new Teleport(
                "Mounted_Xerics",
                Location.TeleportCategory.MOUNTED_XERICS,
                "Teleport to Kourend with Xeric's Talisman in PoH.",
                0,
                "null",
                187,
                3,
                6967,
                worldPoint,
                kourendMountedXericsItems
        );

        location.addTeleportOption(kourendXericsTalisman);
        location.addTeleportOption(kourendMountedXerics);

        locations.add(location);
    }

    protected void setupMorytaniaTeleports()
    {
        WorldPoint worldPoint = Place.MORYTANIA.herbPatch();
        Location location = Place.MORYTANIA.location(config);

        List<ItemRequirement> morytaniaEctophialItems = Arrays.asList(
                new ItemRequirement(ItemID.ECTOPHIAL, 1)
        );
        Location.Teleport morytaniaEctophial = location.new Teleport(
                "Ectophial",
                Location.TeleportCategory.ITEM,
                "Teleport to Morytania with Ectophial.",
                ItemID.ECTOPHIAL,
                "null",
                0,
                0,
                14647,
                worldPoint,
                morytaniaEctophialItems
        );

        location.addTeleportOption(morytaniaEctophial);

        locations.add(location);
    }

    protected void setupTrollStrongholdTeleports()
    {
        WorldPoint worldPoint = Place.TROLL_STRONGHOLD.herbPatch();
        Location location = Place.TROLL_STRONGHOLD.location(config);

        List<ItemRequirement> tsStonyBasaltItems = Arrays.asList(
                new ItemRequirement(ItemID.STONY_BASALT, 1)
        );
        Location.Teleport tsStonyBasalt = location.new Teleport(
                "Stony_Basalt",
                Location.TeleportCategory.ITEM,
                "Teleport to Troll Stronghold with Stony Basalt.",
                ItemID.STONY_BASALT,
                "null",
                0,
                0,
                11321,
                worldPoint,
                tsStonyBasaltItems
        );

        List<ItemRequirement> tsPortalNexusItems = getHouseTeleportItemRequirements();
        Location.Teleport tsPortalNexus = location.new Teleport(
                "Portal_Nexus",
                Location.TeleportCategory.PORTAL_NEXUS,
                "Teleport to Troll Stronghold with Portal Nexus.",
                0,
                "null",
                17,
                13,
                11321,
                worldPoint,
                tsPortalNexusItems
        );

        location.addTeleportOption(tsStonyBasalt);
        location.addTeleportOption(tsPortalNexus);

        locations.add(location);
    }

    protected void setupWeissTeleports()
    {
        WorldPoint worldPoint = Place.WEISS.herbPatch();
        Location location = Place.WEISS.location(config);

        List<ItemRequirement> weissIcyBasaltItems = Arrays.asList(
                new ItemRequirement(ItemID.ICY_BASALT, 1)
        );
        Location.Teleport weissIcyBasalt = location.new Teleport(
                "Icy_Basalt",
                Location.TeleportCategory.ITEM,
                "Teleport to Weiss with Icy Basalt.",
                ItemID.ICY_BASALT,
                "null",
                0,
                0,
                11325,
                worldPoint,
                weissIcyBasaltItems
        );

        List<ItemRequirement> weissPortalNexusItems = getHouseTeleportItemRequirements();
        Location.Teleport weissPortalNexus = location.new Teleport(
                "Portal_Nexus",
                Location.TeleportCategory.PORTAL_NEXUS,
                "Teleport to Weiss with Portal Nexus.",
                0,
                "null",
                17,
                13,
                11325,
                worldPoint,
                weissPortalNexusItems
        );

        location.addTeleportOption(weissIcyBasalt);
        location.addTeleportOption(weissPortalNexus);

        locations.add(location);
    }
}
