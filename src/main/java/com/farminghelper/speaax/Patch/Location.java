package com.farminghelper.speaax.Patch;

import com.farminghelper.speaax.FarmingHelperConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Location
{
    private String name;

    private Boolean farmLimps;

    private List<Teleport> teleportOptions;

    private FarmingHelperConfig config;

    private final Function<FarmingHelperConfig, FarmingHelperConfig.OptionEnumTeleport> selectedTeleportFunction;

    public Location(Function<FarmingHelperConfig, FarmingHelperConfig.OptionEnumTeleport> selectedTeleportFunction, FarmingHelperConfig config, String name, Boolean farmLimps)
    {
        this.config = config;
        this.selectedTeleportFunction = selectedTeleportFunction;
        this.name = name;
        this.farmLimps = farmLimps;
        this.teleportOptions = new ArrayList<>();
    }

    public String getName()
    {
        return name;
    }

    public Boolean getFarmLimps()
    {
        return farmLimps;
    }

    public void addTeleportOption(Teleport teleport)
    {
        teleportOptions.add(teleport);
    }

    public Teleport getSelectedTeleport()
    {
        String selectedEnumOption = selectedTeleportFunction.apply(config).name();

        for (Teleport teleport : teleportOptions) {
            if (teleport.getEnumOption().equalsIgnoreCase(selectedEnumOption)) {
                return teleport;
            }
        }

        return teleportOptions.isEmpty() ? null : teleportOptions.get(0);
    }
}