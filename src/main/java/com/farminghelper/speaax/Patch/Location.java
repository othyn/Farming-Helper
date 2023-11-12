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
        FarmingHelperConfig::enumOptionEnumArdougneTeleport
    ),
    BRIMHAVEN(
        "Brimhaven",
        null,
        FarmingHelperConfig::enumFruitTreeBrimhavenTeleport
    ),
    CATHERBY(
        "Catherby",
        Map.of(PatchType.HERB, true),
        FarmingHelperConfig::enumOptionEnumCatherbyTeleport
        // FarmingHelperConfig::enumFruitTreeCatherbyTeleport
    ),
    FALADOR(
        "Falador",
        Map.of(PatchType.HERB, true),
        FarmingHelperConfig::enumTreeFaladorTeleport
        // FarmingHelperConfig::enumOptionEnumFaladorTeleport
    ),
    FARMING_GUILD(
        "Farming Guild",
        Map.of(PatchType.HERB, true),
        FarmingHelperConfig::enumTreeFarmingGuildTeleport
        // FarmingHelperConfig::enumOptionEnumFarmingGuildTeleport
        // FarmingHelperConfig::enumFruitTreeFarmingGuildTeleport
    ),
    GNOME_STRONGHOLD(
        "Gnome Stronghold",
        null,
        FarmingHelperConfig::enumTreeGnomeStrongoldTeleport
        // FarmingHelperConfig::enumFruitTreeGnomeStrongholdTeleport
    ),
    HARMONY_ISLAND(
        "Harmony Island",
        null,
        FarmingHelperConfig::enumOptionEnumHarmonyTeleport
    ),
    KOUREND(
        "Kourend",
        Map.of(PatchType.HERB, true),
        FarmingHelperConfig::enumOptionEnumKourendTeleport
    ),
    LLETYA(
        "Lletya",
        null,
        FarmingHelperConfig::enumFruitTreeLletyaTeleport
    ),
    LUMBRIDGE(
        "Lumbridge",
        null,
        FarmingHelperConfig::enumTreeLumbridgeTeleport
    ),
    MORYTANIA(
        "Morytania",
        Map.of(PatchType.HERB, true),
        FarmingHelperConfig::enumOptionEnumMorytaniaTeleport
    ),
    TAVERLY(
        "Taverley",
        null,
        FarmingHelperConfig::enumTreeTaverleyTeleport
    ),
    TREE_GNOME_VILLAGE(
        "Tree Gnome Village",
        null,
        FarmingHelperConfig::enumFruitTreeTreeGnomeVillageTeleport
    ),
    TROLL_STRONGHOLD(
        "Troll Stronghold",
        null,
        FarmingHelperConfig::enumOptionEnumTrollStrongholdTeleport
    ),
    VARROCK(
        "Varrock",
        null,
        FarmingHelperConfig::enumTreeVarrockTeleport
    ),
    WEISS(
        "Weiss",
        null,
        FarmingHelperConfig::enumOptionEnumWeissTeleport
    );

    private String name;

    private Map<PatchType, Boolean> limpwurtRequirements;

    private final Function<FarmingHelperConfig, FarmingHelperConfig.OptionEnumTeleport> selectedTeleportFunction;

    private List<Teleport> teleports = new ArrayList<>();

    Location(
        String name,
        Map<PatchType, Boolean> limpwurtRequirements,
        Function<FarmingHelperConfig, FarmingHelperConfig.OptionEnumTeleport> selectedTeleportFunction
    ) {
        this.name = name;
        this.limpwurtRequirements = limpwurtRequirements;
        this.selectedTeleportFunction = selectedTeleportFunction;
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

    public Teleport getDesiredTeleport(FarmingHelperConfig config)
    {
        String selectedEnumOption = selectedTeleportFunction.apply(config).name();

        for (Teleport teleport : teleports) {
            if (teleport.getEnumOption().equalsIgnoreCase(selectedEnumOption)) {
                return teleport;
            }
        }

        return teleports.isEmpty() ? null : teleports.get(0);
    }
}