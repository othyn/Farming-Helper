package com.farminghelper.speaax.Patch;

import com.farminghelper.speaax.FarmingHelperConfig;
import net.runelite.api.coords.WorldPoint;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public enum Location
{
    ARDOUGNE(
        "Ardougne",
        Map.of(
            PatchType.HERB, true
        ),
        Map.of(
            PatchType.HERB, new WorldPoint(2670, 3374, 0)
        ),
        Map.of(
            PatchType.HERB, FarmingHelperConfig::enumOptionEnumArdougneTeleport
        )
    ),
    BRIMHAVEN(
        "Brimhaven",
        null,
        Map.of(
            PatchType.FRUIT_TREE, new WorldPoint(2764, 3212, 0)
        ),
        Map.of(
            PatchType.FRUIT_TREE, FarmingHelperConfig::enumFruitTreeBrimhavenTeleport
        )
    ),
    CATHERBY(
        "Catherby",
        Map.of(
            PatchType.HERB, true
        ),
        Map.of(
            PatchType.HERB,       new WorldPoint(2813, 3463, 0),
            PatchType.FRUIT_TREE, new WorldPoint(2860, 3433, 0)
        ),
        Map.of(
            PatchType.HERB,       FarmingHelperConfig::enumOptionEnumCatherbyTeleport,
            PatchType.FRUIT_TREE, FarmingHelperConfig::enumFruitTreeCatherbyTeleport
        )
    ),
    // TODO: Bugged on Tree run, gets stuck on teleport to Falador step
    FALADOR(
        "Falador",
        Map.of(
            PatchType.HERB, true
        ),
        Map.of(
            PatchType.HERB, new WorldPoint(3058, 3307, 0),
            PatchType.TREE, new WorldPoint(3000, 3373, 0)
        ),
        Map.of(
            PatchType.HERB, FarmingHelperConfig::enumOptionEnumFaladorTeleport,
            PatchType.TREE, FarmingHelperConfig::enumTreeFaladorTeleport
        )
    ),
    FARMING_GUILD(
        "Farming Guild",
        Map.of(
            PatchType.HERB, true
        ),
        Map.of(
            PatchType.HERB,       new WorldPoint(1238, 3726, 0),
            PatchType.TREE,       new WorldPoint(1232, 3736, 0),
            PatchType.FRUIT_TREE, new WorldPoint(1243, 3759, 0)
        ),
        Map.of(
            PatchType.HERB,       FarmingHelperConfig::enumOptionEnumFarmingGuildTeleport,
            PatchType.TREE,       FarmingHelperConfig::enumTreeFarmingGuildTeleport,
            PatchType.FRUIT_TREE, FarmingHelperConfig::enumFruitTreeFarmingGuildTeleport
        )
    ),
    // TODO: Bugged on Fruit Tree run, gets stuck on teleport to Gnome Stronghold step
    GNOME_STRONGHOLD(
        "Gnome Stronghold",
        null,
        Map.of(
            PatchType.TREE,       new WorldPoint(2436, 3415, 0),
            PatchType.FRUIT_TREE, new WorldPoint(2475, 3446, 0)
        ),
        Map.of(
            PatchType.TREE,       FarmingHelperConfig::enumTreeGnomeStrongoldTeleport,
            PatchType.FRUIT_TREE, FarmingHelperConfig::enumFruitTreeGnomeStrongholdTeleport
        )
    ),
    HARMONY_ISLAND(
        "Harmony Island",
        null,
        Map.of(
            PatchType.HERB, new WorldPoint(3789, 2837, 0)
        ),
        Map.of(
            PatchType.HERB, FarmingHelperConfig::enumOptionEnumHarmonyTeleport
        )
    ),
    KOUREND(
        "Kourend",
        Map.of(
            PatchType.HERB, true
        ),
        Map.of(
            PatchType.HERB, new WorldPoint(1738, 3550, 0)
        ),
        Map.of(
            PatchType.HERB, FarmingHelperConfig::enumOptionEnumKourendTeleport
        )
    ),
    LLETYA(
        "Lletya",
        null,
        Map.of(
            PatchType.FRUIT_TREE, new WorldPoint(2346, 3162, 0)
        ),
        Map.of(
            PatchType.FRUIT_TREE, FarmingHelperConfig::enumFruitTreeLletyaTeleport
        )
    ),
    LUMBRIDGE(
        "Lumbridge",
        null,
        Map.of(
            PatchType.TREE, new WorldPoint(3193, 3231, 0)
        ),
        Map.of(
            PatchType.TREE, FarmingHelperConfig::enumTreeLumbridgeTeleport
        )
    ),
    MORYTANIA(
        "Morytania",
        Map.of(
            PatchType.HERB, true
        ),
        Map.of(
            PatchType.HERB, new WorldPoint(3601, 3525, 0)
        ),
        Map.of(
            PatchType.HERB, FarmingHelperConfig::enumOptionEnumMorytaniaTeleport
        )
    ),
    TAVERLY(
        "Taverley",
        null,
        Map.of(
            PatchType.TREE, new WorldPoint(2936, 3438, 0)
        ),
        Map.of(
            PatchType.TREE, FarmingHelperConfig::enumTreeTaverleyTeleport
        )
    ),
    TREE_GNOME_VILLAGE(
        "Tree Gnome Village",
        null,
        Map.of(
            PatchType.FRUIT_TREE, new WorldPoint(2490, 3180, 0)
        ),
        Map.of(
            PatchType.FRUIT_TREE, FarmingHelperConfig::enumFruitTreeTreeGnomeVillageTeleport
        )
    ),
    TROLL_STRONGHOLD(
        "Troll Stronghold",
        null,
        Map.of(
            PatchType.HERB, new WorldPoint(2824, 3696, 0)
        ),
        Map.of(
            PatchType.HERB, FarmingHelperConfig::enumOptionEnumTrollStrongholdTeleport
        )
    ),
    VARROCK(
        "Varrock",
        null,
        Map.of(
            PatchType.TREE, new WorldPoint(3229, 3459, 0)
        ),
        Map.of(
            PatchType.TREE, FarmingHelperConfig::enumTreeVarrockTeleport
        )
    ),
    WEISS(
        "Weiss",
        null,
        Map.of(
            PatchType.HERB, new WorldPoint(2847, 3931, 0)
        ),
        Map.of(
            PatchType.HERB, FarmingHelperConfig::enumOptionEnumWeissTeleport
        )
    );

    private String name;

    private Map<PatchType, Boolean> limpwurtRequirements;

    private Map<PatchType, WorldPoint> patchWorldPoints;

    private final Map<PatchType, Function<FarmingHelperConfig, FarmingHelperConfig.OptionEnumTeleport>> desiredTeleport;

    private List<Teleport> teleports = new ArrayList<>();

    Location(
        String name,
        Map<PatchType, Boolean> limpwurtRequirements,
        Map<PatchType, WorldPoint> patchWorldPoints,
        Map<PatchType, Function<FarmingHelperConfig, FarmingHelperConfig.OptionEnumTeleport>> desiredTeleport
    ) {
        this.name = name;
        this.limpwurtRequirements = limpwurtRequirements;
        this.patchWorldPoints = patchWorldPoints;
        this.desiredTeleport = desiredTeleport;
    }

    public String getName()
    {
        return name;
    }

    public Boolean shouldFarmLimpwurts(PatchType patchType)
    {
        return limpwurtRequirements != null && limpwurtRequirements.get(patchType) != null
            ? limpwurtRequirements.get(patchType)
            : false;
    }

    public WorldPoint patchWorldPoint(PatchType patchType)
    {
        return patchWorldPoints != null && patchWorldPoints.get(patchType) != null
            ? patchWorldPoints.get(patchType)
            : new WorldPoint(0, 0, 0);
    }

    public void addTeleportOption(Teleport teleport)
    {
        teleports.add(teleport);
    }

    public Teleport desiredTeleport(PatchType patchType, FarmingHelperConfig config)
    {
        if (desiredTeleport.get(patchType) == null) {
            return teleports.isEmpty() ? null : teleports.get(0);
        }

        Function<FarmingHelperConfig, FarmingHelperConfig.OptionEnumTeleport> desiredTeleportFunction = desiredTeleport.get(patchType);

        String selectedEnumOption = desiredTeleportFunction.apply(config).name();

        for (Teleport teleport : teleports) {
            if (teleport.getEnumOption().equalsIgnoreCase(selectedEnumOption)) {
                return teleport;
            }
        }

        return teleports.isEmpty() ? null : teleports.get(0);
    }
}