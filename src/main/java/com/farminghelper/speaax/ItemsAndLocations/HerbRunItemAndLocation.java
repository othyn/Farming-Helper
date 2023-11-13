package com.farminghelper.speaax.ItemsAndLocations;

import com.farminghelper.speaax.FarmingHelperConfig;
import com.farminghelper.speaax.FarmingHelperPlugin;
import com.farminghelper.speaax.Patch.ItemRequirement;
import com.farminghelper.speaax.Patch.Location;
import com.farminghelper.speaax.Patch.PatchType;
import com.farminghelper.speaax.Patch.Teleport;
import net.runelite.api.ItemID;

import java.util.*;

public class HerbRunItemAndLocation extends ItemAndLocation
{
    public HerbRunItemAndLocation()
    {
    }

    public HerbRunItemAndLocation(FarmingHelperConfig config, FarmingHelperPlugin plugin)
    {
        super(config, plugin);
    }

    public Map<Integer, Integer> getHerbItems()
    {
        return getAllItemRequirements(locations);
    }

    public Map<Integer, Integer> getAllItemRequirements(List<Location> locations)
    {
        Map<Integer, Integer> allRequirements = new HashMap<>();

        setupLocations();

        // Add other items and merge them with allRequirements
        for (Location location : locations) {
            if (plugin.getHerbLocationEnabled(location.getName())) {
                //ItemID.GUAM_SEED is default for herb seeds, code later will allow for any seed to be used, just needed a placeholder ID
                allRequirements.merge(
                    ItemID.GUAM_SEED,
                    1,
                    Integer::sum
                );

                if (selectedCompostID() != - 1 && selectedCompostID() != ItemID.BOTTOMLESS_COMPOST_BUCKET_22997) {
                    allRequirements.merge(
                        selectedCompostID(),
                        1,
                        Integer::sum
                    );
                }

                Teleport teleport = location.desiredTeleport(PatchType.HERB, config);

                Map<Integer, Integer> locationRequirements = teleport.getItemRequirements();

                for (Map.Entry<Integer, Integer> entry : locationRequirements.entrySet()) {
                    int itemId = entry.getKey();
                    int quantity = entry.getValue();

                    if (itemId == ItemID.CONSTRUCT_CAPE || itemId == ItemID.CONSTRUCT_CAPET || itemId == ItemID.MAX_CAPE) {
                        allRequirements.merge(
                            itemId,
                            quantity,
                            (oldValue, newValue) -> Math.min(
                                1,
                                oldValue + newValue
                            )
                        );
                    } else {
                        allRequirements.merge(
                            itemId,
                            quantity,
                            Integer::sum
                        );
                    }
                }

                if (location.patch(PatchType.HERB).shouldFarmLimpwurts() && config.generalLimpwurt()) {
                    allRequirements.merge(
                        ItemID.LIMPWURT_SEED,
                        1,
                        Integer::sum
                    );

                    if (selectedCompostID() != - 1 && selectedCompostID() != ItemID.BOTTOMLESS_COMPOST_BUCKET_22997) {
                        allRequirements.merge(
                            selectedCompostID(),
                            1,
                            Integer::sum
                        );
                    }
                }
            }
        }

        allRequirements.merge(
            ItemID.SEED_DIBBER,
            1,
            Integer::sum
        );

        allRequirements.merge(
            ItemID.SPADE,
            1,
            Integer::sum
        );

        if (selectedCompostID() == ItemID.BOTTOMLESS_COMPOST_BUCKET_22997) {
            allRequirements.merge(
                ItemID.BOTTOMLESS_COMPOST_BUCKET_22997,
                1,
                Integer::sum
            );
        }

        allRequirements.merge(
            ItemID.MAGIC_SECATEURS,
            1,
            Integer::sum
        );

        if (config.generalRake()) {
            allRequirements.merge(
                ItemID.RAKE,
                1,
                Integer::sum
            );
        }

        return allRequirements;
    }

    public void setupLocations()
    {
        super.setupLocations();

        setupArdougneLocation();
        setupCatherbyLocation();
        setupFaladorLocation();
        setupFarmingGuildLocation();
        setupHarmonyLocation();
        setupKourendLocation();
        setupMorytaniaLocation();
        setupTrollStrongholdLocation();
        setupWeissLocation();
    }

    private void setupArdougneLocation()
    {
        Location.ARDOUGNE.addTeleportOption(new Teleport(
            "Portal_Nexus",
            Teleport.Category.PORTAL_NEXUS,
            "Teleport to Ardougne with Portal Nexus.",
            0,
            "null",
            17,
            13,
            10547,
            getHouseTeleportItemRequirements()
        ));

        Location.ARDOUGNE.addTeleportOption(new Teleport(
            "Ardougne_teleport",
            Teleport.Category.SPELLBOOK,
            "Teleport to Ardougne with standard spellbook, and run north.",
            0,
            "null",
            218,
            38,
            10547,
            Arrays.asList(
                new ItemRequirement(
                    ItemID.LAW_RUNE,
                    2
                ),
                new ItemRequirement(
                    ItemID.WATER_RUNE,
                    2
                )
            )
        ));

        Location.ARDOUGNE.addTeleportOption(new Teleport(
            "Ardougne_Tele_Tab",
            Teleport.Category.ITEM,
            "Teleport to Ardougne with Ardougne tele tab, and run north.",
            ItemID.ARDOUGNE_TELEPORT,
            "null",
            0,
            0,
            10547,
            Collections.singletonList(new ItemRequirement(
                ItemID.ARDOUGNE_TELEPORT,
                1
            ))
        ));

        Location.ARDOUGNE.addTeleportOption(new Teleport(
            "Ardy_Cloak_2",
            Teleport.Category.ITEM,
            "Teleport to Ardougne with Ardougne cloak.",
            ItemID.ARDOUGNE_CLOAK_2,
            "Farm Teleport",
            0,
            0,
            10548,
            Collections.singletonList(new ItemRequirement(
                ItemID.ARDOUGNE_CLOAK_2,
                1
            ))
        ));

        Location.ARDOUGNE.addTeleportOption(new Teleport(
            "Ardy_Cloak_3",
            Teleport.Category.ITEM,
            "Teleport to Ardougne with Ardougne cloak.",
            ItemID.ARDOUGNE_CLOAK_3,
            "Farm Teleport",
            0,
            0,
            10548,
            Collections.singletonList(new ItemRequirement(
                ItemID.ARDOUGNE_CLOAK_3,
                1
            ))
        ));

        Location.ARDOUGNE.addTeleportOption(new Teleport(
            "Ardy_Cloak_4",
            Teleport.Category.ITEM,
            "Teleport to Ardougne with Ardougne cloak.",
            ItemID.ARDOUGNE_CLOAK_4,
            "Farm Teleport",
            0,
            0,
            10548,
            Collections.singletonList(new ItemRequirement(
                ItemID.ARDOUGNE_CLOAK_4,
                1
            ))
        ));

        Location.ARDOUGNE.addTeleportOption(new Teleport(
            "Skills_Necklace",
            Teleport.Category.ITEM,
            "Teleport to Fishing guild with Skills necklace, and run east.",
            ItemID.SKILLS_NECKLACE1,
            "null",
            0,
            0,
            10292,
            Collections.singletonList(new ItemRequirement(
                ItemID.SKILLS_NECKLACE1,
                1
            ))
        ));

        locations.add(Location.ARDOUGNE);
    }

    private void setupCatherbyLocation()
    {
        Location.CATHERBY.addTeleportOption(new Teleport(
            "Portal_Nexus_Catherby",
            Teleport.Category.PORTAL_NEXUS,
            "Teleport to Catherby with Portal Nexus.",
            0,
            "null",
            17,
            13,
            11061,
            getHouseTeleportItemRequirements()
        ));

        Location.CATHERBY.addTeleportOption(new Teleport(
            "Portal_Nexus_Camelot",
            Teleport.Category.PORTAL_NEXUS,
            "Teleport to Camelot with Portal Nexus and run to Catherby.",
            0,
            "null",
            17,
            13,
            11062,
            getHouseTeleportItemRequirements()
        ).overrideLocationName("Camelot"));

        Location.CATHERBY.addTeleportOption(new Teleport(
            "Camelot_Teleport",
            Teleport.Category.SPELLBOOK,
            "Teleport to Camelot using the standard spellbook, and run east. (If you have configured the teleport to seers you need to right click and teleport to Camelot)",
            0,
            "null",
            218,
            32,
            11062,
            Arrays.asList(
                new ItemRequirement(
                    ItemID.AIR_RUNE,
                    5
                ),
                new ItemRequirement(
                    ItemID.LAW_RUNE,
                    1
                )
            )
        ));

        Location.CATHERBY.addTeleportOption(new Teleport(
            "Camelot_Tele_Tab",
            Teleport.Category.ITEM,
            "Teleport to Camelot using a Camelot tele tab, and run east.(If you have configured the teleport to seers you need to right click and teleport to Camelot)",
            ItemID.CAMELOT_TELEPORT,
            "null",
            0,
            0,
            11062,
            Collections.singletonList(new ItemRequirement(
                ItemID.CAMELOT_TELEPORT,
                1
            ))
        ));

        Location.CATHERBY.addTeleportOption(new Teleport(
            "Catherby_Tele_Tab",
            Teleport.Category.ITEM,
            "Teleport to Catherby using Catherby teleport tab.",
            ItemID.CATHERBY_TELEPORT,
            "null",
            0,
            0,
            11061,
            Collections.singletonList(new ItemRequirement(
                ItemID.CATHERBY_TELEPORT,
                1
            ))
        ));

        locations.add(Location.CATHERBY);
    }

    private void setupFaladorLocation()
    {
        Location.FALADOR.addTeleportOption(new Teleport(
            "Portal_Nexus",
            Teleport.Category.PORTAL_NEXUS,
            "Teleport to Falador with Portal Nexus.",
            0,
            "null",
            17,
            13,
            11828, getHouseTeleportItemRequirements()
        ));

        Location.FALADOR.addTeleportOption(new Teleport(
            "Explorers_ring_2",
            Teleport.Category.ITEM,
            "Teleport to Falador with Explorers ring.",
            ItemID.EXPLORERS_RING_2,
            "Teleport",
            0,
            0,
            12083, Collections.singletonList(new ItemRequirement(
                ItemID.EXPLORERS_RING_2,
                1
            ))
        ));

        Location.FALADOR.addTeleportOption(new Teleport(
            "Explorers_ring_3",
            Teleport.Category.ITEM,
            "Teleport to Falador with Explorers ring.",
            ItemID.EXPLORERS_RING_3,
            "Teleport",
            0,
            0,
            12083, Collections.singletonList(new ItemRequirement(
                ItemID.EXPLORERS_RING_3,
                1
            ))
        ));

        Location.FALADOR.addTeleportOption(new Teleport(
            "Explorers_ring_4",
            Teleport.Category.ITEM,
            "Teleport to Falador with Explorers ring.",
            ItemID.EXPLORERS_RING_4,
            "Teleport",
            0,
            0,
            12083, Collections.singletonList(new ItemRequirement(
                ItemID.EXPLORERS_RING_4,
                1
            ))
        ));

        Location.FALADOR.addTeleportOption(new Teleport(
            "Falador_Teleport",
            Teleport.Category.SPELLBOOK,
            "Teleport to Falador with standard spellbook, and run south-east.",
            0,
            "null",
            218,
            27,
            11828, Arrays.asList(
                new ItemRequirement(
                    ItemID.AIR_RUNE,
                    3
                ),
                new ItemRequirement(
                    ItemID.LAW_RUNE,
                    1
                ),
                new ItemRequirement(
                    ItemID.WATER_RUNE,
                    1
                )
            )
        ));

        Location.FALADOR.addTeleportOption(new Teleport(
            "Falador_Tele_Tab",
            Teleport.Category.ITEM,
            "Teleport to Falador with Falador Tele Tab, and run south-east.",
            ItemID.FALADOR_TELEPORT,
            "null",
            0,
            0,
            11828, Collections.singletonList(new ItemRequirement(
                ItemID.FALADOR_TELEPORT,
                1
            ))
        ));

        Location.FALADOR.addTeleportOption(new Teleport(
            "Draynor_Tele_Tab",
            Teleport.Category.ITEM,
            "Teleport to Draynor Manor with Draynor Manor Tele Tab, and run south-west.",
            ItemID.DRAYNOR_MANOR_TELEPORT,
            "null",
            0,
            0,
            12340, Collections.singletonList(new ItemRequirement(
                ItemID.DRAYNOR_MANOR_TELEPORT,
                1
            ))
        ));

        locations.add(Location.FALADOR);
    }

    private void setupFarmingGuildLocation()
    {
        Location.FARMING_GUILD.addTeleportOption(new Teleport(
            "Jewellery_box",
            Teleport.Category.JEWELLERY_BOX,
            "Teleport to Farming guild with Jewellery box.",
            29155,
            "null",
            0,
            0,
            4922, getHouseTeleportItemRequirements()
        ));

        Location.FARMING_GUILD.addTeleportOption(new Teleport(
            "Skills_Necklace",
            Teleport.Category.ITEM,
            "Teleport to Farming guild using Skills necklace.",
            ItemID.SKILLS_NECKLACE1,
            "null",
            0,
            0,
            4922, Collections.singletonList(new ItemRequirement(
                ItemID.SKILLS_NECKLACE1,
                1
            ))
        ));

        locations.add(Location.FARMING_GUILD);
    }

    private void setupHarmonyLocation()
    {
        Location.HARMONY_ISLAND.addTeleportOption(new Teleport(
            "Portal_Nexus",
            Teleport.Category.PORTAL_NEXUS,
            "Teleport to Harmony with Portal Nexus.",
            0,
            "null",
            17,
            13,
            15148, getHouseTeleportItemRequirements()
        ));

        Location.HARMONY_ISLAND.addTeleportOption(new Teleport(
            "Harmony_Tele_Tab",
            Teleport.Category.ITEM,
            "Teleport to Harmony with Harmony Tele Tab.",
            ItemID.HARMONY_ISLAND_TELEPORT,
            "null",
            0,
            0,
            15148, Collections.singletonList(new ItemRequirement(
                ItemID.HARMONY_ISLAND_TELEPORT,
                1
            ))
        ));

        locations.add(Location.HARMONY_ISLAND);
    }

    private void setupKourendLocation()
    {
        Location.KOUREND.addTeleportOption(new Teleport(
            "Xerics_Talisman",
            Teleport.Category.ITEM,
            "Teleport to Kourend with Xeric's Talisman.",
            ItemID.XERICS_TALISMAN,
            "Rub",
            187,
            3,
            6967, Collections.singletonList(new ItemRequirement(
                ItemID.XERICS_TALISMAN,
                1
            ))
        ));

        Location.KOUREND.addTeleportOption(new Teleport(
            "Mounted_Xerics",
            Teleport.Category.MOUNTED_XERICS,
            "Teleport to Kourend with Xeric's Talisman in PoH.",
            0,
            "null",
            187,
            3,
            6967, getHouseTeleportItemRequirements()
        ));

        locations.add(Location.KOUREND);
    }

    private void setupMorytaniaLocation()
    {
        Location.MORYTANIA.addTeleportOption(new Teleport(
            "Ectophial",
            Teleport.Category.ITEM,
            "Teleport to Morytania with Ectophial.",
            ItemID.ECTOPHIAL,
            "null",
            0,
            0,
            14647, Collections.singletonList(new ItemRequirement(
                ItemID.ECTOPHIAL,
                1
            ))
        ));

        locations.add(Location.MORYTANIA);
    }

    private void setupTrollStrongholdLocation()
    {
        Location.TROLL_STRONGHOLD.addTeleportOption(new Teleport(
            "Stony_Basalt",
            Teleport.Category.ITEM,
            "Teleport to Troll Stronghold with Stony Basalt.",
            ItemID.STONY_BASALT,
            "null",
            0,
            0,
            11321, Collections.singletonList(new ItemRequirement(
                ItemID.STONY_BASALT,
                1
            ))
        ));

        Location.TROLL_STRONGHOLD.addTeleportOption(new Teleport(
            "Portal_Nexus",
            Teleport.Category.PORTAL_NEXUS,
            "Teleport to Troll Stronghold with Portal Nexus.",
            0,
            "null",
            17,
            13,
            11321, getHouseTeleportItemRequirements()
        ));

        locations.add(Location.TROLL_STRONGHOLD);
    }

    private void setupWeissLocation()
    {
        Location.WEISS.addTeleportOption(new Teleport(
            "Icy_Basalt",
            Teleport.Category.ITEM,
            "Teleport to Weiss with Icy Basalt.",
            ItemID.ICY_BASALT,
            "null",
            0,
            0,
            11325, Collections.singletonList(new ItemRequirement(
                ItemID.ICY_BASALT,
                1
            ))
        ));

        Location.WEISS.addTeleportOption(new Teleport(
            "Portal_Nexus",
            Teleport.Category.PORTAL_NEXUS,
            "Teleport to Weiss with Portal Nexus.",
            0,
            "null",
            17,
            13,
            11325, getHouseTeleportItemRequirements()
        ));

        locations.add(Location.WEISS);
    }
}