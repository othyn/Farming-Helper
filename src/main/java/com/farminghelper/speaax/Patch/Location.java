package com.farminghelper.speaax.Patch;

import com.farminghelper.speaax.FarmingHelperConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public enum Location
{
    ARDOUGNE(
        "Ardougne",
        Map.of(PatchType.HERB, true),
        Map.of(PatchType.HERB, FarmingHelperConfig::enumOptionEnumArdougneTeleport)
    ),
    BRIMHAVEN(
        "Brimhaven",
        null,
        Map.of(PatchType.FRUIT_TREE, FarmingHelperConfig::enumFruitTreeBrimhavenTeleport)
    ),
    CATHERBY(
        "Catherby",
        Map.of(PatchType.HERB, true),
        Map.of(
            PatchType.HERB,       FarmingHelperConfig::enumOptionEnumCatherbyTeleport,
            PatchType.FRUIT_TREE, FarmingHelperConfig::enumFruitTreeCatherbyTeleport
        )
    ),
    FALADOR(
        "Falador",
        Map.of(PatchType.HERB, true),
        Map.of(
            PatchType.HERB, FarmingHelperConfig::enumOptionEnumFaladorTeleport,
            PatchType.TREE, FarmingHelperConfig::enumTreeFaladorTeleport
        )
    ),
    FARMING_GUILD(
        "Farming Guild",
        Map.of(PatchType.HERB, true),
        Map.of(
            PatchType.HERB,       FarmingHelperConfig::enumOptionEnumFarmingGuildTeleport,
            PatchType.TREE,       FarmingHelperConfig::enumTreeFarmingGuildTeleport,
            PatchType.FRUIT_TREE, FarmingHelperConfig::enumFruitTreeFarmingGuildTeleport
        )
    ),
    GNOME_STRONGHOLD(
        "Gnome Stronghold",
        null,
        Map.of(
            PatchType.TREE,       FarmingHelperConfig::enumTreeGnomeStrongoldTeleport,
            PatchType.FRUIT_TREE, FarmingHelperConfig::enumFruitTreeGnomeStrongholdTeleport
        )
    ),
    HARMONY_ISLAND(
        "Harmony Island",
        null,
        Map.of(PatchType.HERB, FarmingHelperConfig::enumOptionEnumHarmonyTeleport)
    ),
    KOUREND(
        "Kourend",
        Map.of(PatchType.HERB, true),
        Map.of(PatchType.HERB, FarmingHelperConfig::enumOptionEnumKourendTeleport)
    ),
    LLETYA(
        "Lletya",
        null,
        Map.of(PatchType.FRUIT_TREE, FarmingHelperConfig::enumFruitTreeLletyaTeleport)
    ),
    LUMBRIDGE(
        "Lumbridge",
        null,
        Map.of(PatchType.TREE, FarmingHelperConfig::enumTreeLumbridgeTeleport)
    ),
    MORYTANIA(
        "Morytania",
        Map.of(PatchType.HERB, true),
        Map.of(PatchType.HERB, FarmingHelperConfig::enumOptionEnumMorytaniaTeleport)
    ),
    TAVERLY(
        "Taverley",
        null,
        Map.of(PatchType.TREE, FarmingHelperConfig::enumTreeTaverleyTeleport)
    ),
    TREE_GNOME_VILLAGE(
        "Tree Gnome Village",
        null,
        Map.of(PatchType.FRUIT_TREE, FarmingHelperConfig::enumFruitTreeTreeGnomeVillageTeleport)
    ),
    TROLL_STRONGHOLD(
        "Troll Stronghold",
        null,
        Map.of(PatchType.HERB, FarmingHelperConfig::enumOptionEnumTrollStrongholdTeleport)
    ),
    VARROCK(
        "Varrock",
        null,
        Map.of(PatchType.TREE, FarmingHelperConfig::enumTreeVarrockTeleport)
    ),
    WEISS(
        "Weiss",
        null,
        Map.of(PatchType.HERB, FarmingHelperConfig::enumOptionEnumWeissTeleport)
    );

    private String name;

    private Map<PatchType, Boolean> limpwurtRequirements;

    private final Map<PatchType, Function<FarmingHelperConfig, FarmingHelperConfig.OptionEnumTeleport>> desiredTeleport;

    private List<Teleport> teleports = new ArrayList<>();

    Location(
        String name,
        Map<PatchType, Boolean> limpwurtRequirements,
        Map<PatchType, Function<FarmingHelperConfig, FarmingHelperConfig.OptionEnumTeleport>> desiredTeleport
    ) {
        this.name = name;
        this.limpwurtRequirements = limpwurtRequirements;
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

    public void addTeleportOption(Teleport teleport)
    {
        teleports.add(teleport);
    }

    public Teleport getDesiredTeleport(PatchType patchType, FarmingHelperConfig config)
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