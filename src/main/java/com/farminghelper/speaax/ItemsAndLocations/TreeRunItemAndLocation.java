package com.farminghelper.speaax.ItemsAndLocations;

import com.farminghelper.speaax.FarmingHelperConfig;
import com.farminghelper.speaax.FarmingHelperPlugin;
import com.farminghelper.speaax.Patch.Location;
import com.farminghelper.speaax.Patch.PatchType;
import com.farminghelper.speaax.Patch.Teleport;
import net.runelite.api.ItemID;
import net.runelite.api.coords.WorldPoint;

import java.util.*;

public class TreeRunItemAndLocation extends ItemAndLocation
{
    public TreeRunItemAndLocation()
    {
    }

    public TreeRunItemAndLocation(FarmingHelperConfig config, FarmingHelperPlugin plugin)
    {
        super(config, plugin);
    }

    public Map<Integer, Integer> getTreeItems()
    {
        return getAllItemRequirements(locations);
    }

    public Map<Integer, Integer> getAllItemRequirements(List<Location> locations)
    {
        Map<Integer, Integer> allRequirements = new HashMap<>();

        setupLocations();

        // Add other items and merge them with allRequirements
        for (Location location : locations) {
            if (plugin.getTreeLocationEnabled(location.getName())) {
                //ItemID.GUAM_SEED is default for herb seeds, code later will allow for any seed to be used, just needed a placeholder ID
                //allRequirements.merge(ItemID.GUAM_SEED, 1, Integer::sum);
                allRequirements.merge(
                    ItemID.OAK_SAPLING,
                    1,
                    Integer::sum
                );

                allRequirements.merge(
                    ItemID.COINS_995,
                    200,
                    Integer::sum
                );

                Teleport teleport = location.getDesiredTeleport(PatchType.TREE, config);

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
            }
        }

        //allRequirements.merge(ItemID.SEED_DIBBER, 1, Integer::sum);
        allRequirements.merge(
            ItemID.SPADE,
            1,
            Integer::sum
        );

        allRequirements.merge(
            ItemID.BOTTOMLESS_COMPOST_BUCKET_22997,
            1,
            Integer::sum
        );

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

        setupFaladorLocation();
        setupFarmingGuildLocation();
        setupGnomeStrongholdLocation();
        setupLumbridgeLocation();
        setupTaverleyLocation();
        setupVarrockLocation();
    }

    private void setupFaladorLocation()
    {
        // TODO: The following...

        // Location takes in the teleports and stores them
        // Also takes in teh world point
        // Location becomes and enum with self declarations (FUCK. config is passed in... just get rid of it, pass it in)
        // ... it can't tho, because locations are based on what TYPE they are under

        // This is where the structure is all wrong, its currently:
        //  Patch (Type) > Location > Teleport
        // It needs to be:
        //  Location > Teleport | Patch (Type)

        // The Location should store the state on which patch types are enabled/disabled under one centralised Location
        //  e.g. all 'Falador's' are merged

        // Which solves the 'FarmingHelperConfig::enumTreeFaladorTeleport' problem stored at location level, as they can
        //  be statically linked then to the flat definition of a Location and an 'enabled' state based on the patch
        //  type.

        // TODO: MOVE all Teleport declarations into the Enum singleton at Location init

        // Location.FALADOR.patch.TREE.worldPoint
        WorldPoint faladorTreePatchPoint = new WorldPoint(
            3000,
            3373,
            0
        );

        Location.FALADOR.addTeleportOption(new Teleport(
            "Portal_Nexus",
            Teleport.Category.PORTAL_NEXUS,
            "Teleport to Falador with Portal Nexus.",
            0,
            "null",
            17,
            13,
            11828,
            faladorTreePatchPoint,
            getHouseTeleportItemRequirements()
        ));

        Location.FALADOR.addTeleportOption(new Teleport(
            "Falador_teleport",
            Teleport.Category.SPELLBOOK,
            "Teleport to Falador with Spellbook and run to Falador park.",
            0,
            "null",
            218,
            27,
            11828,
            faladorTreePatchPoint,
            Arrays.asList(
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

        locations.add(Location.FALADOR);
    }

    private void setupFarmingGuildLocation()
    {
        WorldPoint farmingGuildTreePatchPoint = new WorldPoint(
            1232,
            3736,
            0
        );

        Location.FARMING_GUILD.addTeleportOption(new Teleport(
            "Jewellery_box",
            Teleport.Category.JEWELLERY_BOX,
            "Teleport to Farming Guild with Jewellery box.",
            0,
            "null",
            0,
            0,
            4922,
            farmingGuildTreePatchPoint,
            getHouseTeleportItemRequirements()
        ));

        locations.add(Location.FARMING_GUILD);
    }

    private void setupGnomeStrongholdLocation()
    {
        WorldPoint gnomeStrongholdTreePatchPoint = new WorldPoint(
            2436,
            3415,
            0
        );

        Location.GNOME_STRONGHOLD.addTeleportOption(new Teleport(
            "Royal_seed_pod",
            Teleport.Category.ITEM,
            "Teleport to Gnome Stronghold with Royal seed pod.",
            ItemID.ROYAL_SEED_POD,
            "null",
            0,
            0,
            9782,
            gnomeStrongholdTreePatchPoint,
            Collections.singletonList(new ItemRequirement(
                ItemID.ROYAL_SEED_POD,
                1
            ))
        ));

        Location.GNOME_STRONGHOLD.addTeleportOption(new Teleport(
            "Spirit_Tree",
            Teleport.Category.SPIRIT_TREE,
            "Teleport to Gnome Stronghold via a Spirit Tree.",
            0,
            "null",
            187,
            3,
            9781,
            gnomeStrongholdTreePatchPoint,
            Collections.<ItemRequirement> emptyList()
        ));

        locations.add(Location.GNOME_STRONGHOLD);
    }

    private void setupLumbridgeLocation()
    {
        WorldPoint lumbridgeTreePatchPoint = new WorldPoint(
            3193,
            3231,
            0
        );

        Location.LUMBRIDGE.addTeleportOption(new Teleport(
            "Portal_Nexus",
            Teleport.Category.PORTAL_NEXUS,
            "Teleport to Lumbridge with Portal Nexus.",
            0,
            "null",
            17,
            13,
            12850,
            lumbridgeTreePatchPoint,
            getHouseTeleportItemRequirements()
        ));

        Location.LUMBRIDGE.addTeleportOption(new Teleport(
            "Lumbridge_teleport",
            Teleport.Category.SPELLBOOK,
            "Teleport to Lumbridge with spellbook.",
            0,
            "null",
            218,
            24,
            12850,
            lumbridgeTreePatchPoint,
            Arrays.asList(
                new ItemRequirement(
                    ItemID.AIR_RUNE,
                    3
                ),
                new ItemRequirement(
                    ItemID.LAW_RUNE,
                    1
                ),
                new ItemRequirement(
                    ItemID.EARTH_RUNE,
                    1
                )
            )
        ));

        locations.add(Location.LUMBRIDGE);
    }

    private void setupTaverleyLocation()
    {
        WorldPoint taverlyPatchPoint = new WorldPoint(
            2936,
            3438,
            0
        );

        Location.TAVERLY.addTeleportOption(new Teleport(
            "Portal_Nexus",
            Teleport.Category.PORTAL_NEXUS,
            "Teleport to Falador with Portal Nexus and run to Taverly.",
            0,
            "null",
            17,
            13,
            11828,
            taverlyPatchPoint,
            getHouseTeleportItemRequirements()
        ).overrideLocationName("Falador"));

        Location.TAVERLY.addTeleportOption(new Teleport(
            "Falador_teleport",
            Teleport.Category.SPELLBOOK,
            "Teleport to Falador with spellbook and run to Taverly.",
            0,
            "null",
            218,
            27,
            11828,
            taverlyPatchPoint,
            Arrays.asList(
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

        locations.add(Location.TAVERLY);
    }

    private void setupVarrockLocation()
    {
        WorldPoint varrockTreePatchPoint = new WorldPoint(
            3229,
            3459,
            0
        );

        Location.VARROCK.addTeleportOption(new Teleport(
            "Portal_Nexus",
            Teleport.Category.PORTAL_NEXUS,
            "Teleport to Varrock with Portal Nexus.",
            0,
            "null",
            17,
            13,
            12853,
            varrockTreePatchPoint,
            getHouseTeleportItemRequirements()
        ));

        Location.VARROCK.addTeleportOption(new Teleport(
            "Varrock_teleport",
            Teleport.Category.SPELLBOOK,
            "Teleport to Varrock with spellbook.",
            0,
            "null",
            218,
            21,
            12853,
            varrockTreePatchPoint,
            Arrays.asList(
                new ItemRequirement(
                    ItemID.AIR_RUNE,
                    3
                ),
                new ItemRequirement(
                    ItemID.LAW_RUNE,
                    1
                ),
                new ItemRequirement(
                    ItemID.FIRE_RUNE,
                    1
                )
            )
        ));

        locations.add(Location.VARROCK);
    }
}