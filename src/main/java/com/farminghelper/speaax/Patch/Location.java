package com.farminghelper.speaax.Patch;

import com.farminghelper.speaax.FarmingHelperConfig;

import net.runelite.api.coords.WorldPoint;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public enum Location
{
    ARDOUGNE(
        "Ardougne",
        Map.of(
            PatchType.HERB, new Patch(
                new WorldPoint(2670, 3374, 0),
                FarmingHelperConfig::enumOptionEnumArdougneTeleport,
                true
            )
        )
    ),
    BRIMHAVEN(
        "Brimhaven",
        Map.of(
            PatchType.FRUIT_TREE, new Patch(
                new WorldPoint(2764, 3212, 0),
                FarmingHelperConfig::enumFruitTreeBrimhavenTeleport,
                false
            )
        )
    ),
    CATHERBY(
        "Catherby",
        Map.of(
            PatchType.HERB, new Patch(
                new WorldPoint(2813, 3463, 0),
                FarmingHelperConfig::enumOptionEnumCatherbyTeleport,
                true
            ),
            PatchType.FRUIT_TREE, new Patch(
                new WorldPoint(2860, 3433, 0),
                FarmingHelperConfig::enumFruitTreeCatherbyTeleport,
                false
            )
        )
    ),
    FALADOR(
        "Falador",
        Map.of(
            PatchType.HERB, new Patch(
                new WorldPoint(3058, 3307, 0),
                FarmingHelperConfig::enumOptionEnumFaladorTeleport,
                true
            ),
            PatchType.TREE, new Patch(
                new WorldPoint(3000, 3373, 0),
                FarmingHelperConfig::enumTreeFaladorTeleport,
                false
            )
        )
    ),
    FARMING_GUILD(
        "Farming Guild",
        Map.of(
            PatchType.HERB, new Patch(
                new WorldPoint(1238, 3726, 0),
                FarmingHelperConfig::enumOptionEnumFarmingGuildTeleport,
                true
            ),
            PatchType.TREE, new Patch(
                new WorldPoint(1232, 3736, 0),
                FarmingHelperConfig::enumTreeFarmingGuildTeleport,
                false
            ),
            PatchType.FRUIT_TREE, new Patch(
                new WorldPoint(1243, 3759, 0),
                FarmingHelperConfig::enumFruitTreeFarmingGuildTeleport,
                false
            )
        )
    ),
    GNOME_STRONGHOLD(
        "Gnome Stronghold",
        Map.of(
            PatchType.TREE, new Patch(
                new WorldPoint(2436, 3415, 0),
                FarmingHelperConfig::enumTreeGnomeStrongoldTeleport,
                false
            ),
            PatchType.FRUIT_TREE, new Patch(
                new WorldPoint(2475, 3446, 0),
                FarmingHelperConfig::enumFruitTreeGnomeStrongholdTeleport,
                false
            )
        )
    ),
    HARMONY_ISLAND(
        "Harmony Island",
        Map.of(
            PatchType.HERB, new Patch(
                new WorldPoint(3789, 2837, 0),
                FarmingHelperConfig::enumOptionEnumHarmonyTeleport,
                false
            )
        )
    ),
    KOUREND(
        "Kourend",
        Map.of(
            PatchType.HERB, new Patch(
                new WorldPoint(1738, 3550, 0),
                FarmingHelperConfig::enumOptionEnumKourendTeleport,
                true
            )
        )
    ),
    LLETYA(
        "Lletya",
        Map.of(
            PatchType.FRUIT_TREE, new Patch(
                new WorldPoint(2346, 3162, 0),
                FarmingHelperConfig::enumFruitTreeLletyaTeleport,
                false
            )
        )
    ),
    LUMBRIDGE(
        "Lumbridge",
        Map.of(
            PatchType.TREE, new Patch(
                new WorldPoint(3193, 3231, 0),
                FarmingHelperConfig::enumTreeLumbridgeTeleport,
                false
            )
        )
    ),
    MORYTANIA(
        "Morytania",
        Map.of(
            PatchType.HERB, new Patch(
                new WorldPoint(3601, 3525, 0),
                FarmingHelperConfig::enumOptionEnumMorytaniaTeleport,
                true
            )
        )
    ),
    TAVERLY(
        "Taverley",
        Map.of(
            PatchType.TREE, new Patch(
                new WorldPoint(2936, 3438, 0),
                FarmingHelperConfig::enumTreeTaverleyTeleport,
                false
            )
        )
    ),
    TREE_GNOME_VILLAGE(
        "Tree Gnome Village",
        Map.of(
            PatchType.FRUIT_TREE, new Patch(
                new WorldPoint(2490, 3180, 0),
                FarmingHelperConfig::enumFruitTreeTreeGnomeVillageTeleport,
                false
            )
        )
    ),
    TROLL_STRONGHOLD(
        "Troll Stronghold",
        Map.of(
            PatchType.HERB, new Patch(
                new WorldPoint(2824, 3696, 0),
                FarmingHelperConfig::enumOptionEnumTrollStrongholdTeleport,
                false
            )
        )
    ),
    VARROCK(
        "Varrock",
        Map.of(
            PatchType.TREE, new Patch(
                new WorldPoint(3229, 3459, 0),
                FarmingHelperConfig::enumTreeVarrockTeleport,
                false
            )
        )
    ),
    WEISS(
        "Weiss",
        Map.of(
            PatchType.HERB, new Patch(
                new WorldPoint(2847, 3931, 0),
                FarmingHelperConfig::enumOptionEnumWeissTeleport,
                false
            )
        )
    );

    private String name;

    private Map<PatchType, Patch> patches;

    private List<Teleport> teleports = new ArrayList<>();

    Location(
        String name,
        Map<PatchType, Patch> patches
    ) {
        this.name = name;
        this.patches = patches;
    }

    public String getName()
    {
        return name;
    }

    public Patch patch(PatchType patchType)
    {
        return patches != null && patches.get(patchType) != null
            ? patches.get(patchType)
            : null;
    }

    public void addTeleportOption(Teleport teleport)
    {
        teleports.add(teleport);
    }

    public Teleport desiredTeleport(PatchType patchType, FarmingHelperConfig config)
    {
        Patch patch = patch(patchType);

        if (patch == null) {
            return teleports.isEmpty() ? null : teleports.get(0);
        }

        String selectedEnumOption = patch.getDesiredTeleportFunction().apply(config).name();

        for (Teleport teleport : teleports) {
            if (teleport.getEnumOption().equalsIgnoreCase(selectedEnumOption)) {
                return teleport;
            }
        }

        return teleports.isEmpty() ? null : teleports.get(0);
    }
}