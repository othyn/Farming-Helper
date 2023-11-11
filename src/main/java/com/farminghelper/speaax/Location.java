package com.farminghelper.speaax;

import com.farminghelper.speaax.ItemsAndLocations.ItemRequirement;
import net.runelite.api.coords.WorldPoint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class Location
{
    public enum TeleportCategory
    {
        ITEM,
        PORTAL_NEXUS,
        SPIRIT_TREE,
        JEWELLERY_BOX,
        MOUNTED_XERICS,
        SPELLBOOK
    }

    private String name;

    public String getName()
    {
        return name;
    }

    private Boolean farmLimps;

    public Boolean getFarmLimps()
    {
        return farmLimps;
    }

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

    public class Teleport
    {
        private TeleportCategory category;

        private String description;

        private int id;

        private int interfaceGroupId;

        private int interfaceChildId;

        private int regionId;

        private String enumOption;

        private String rightClickOption;

        private List<ItemRequirement> itemRequirements;

        private WorldPoint point;

        private String overrideLocationName;

        public Teleport(String enumOption, TeleportCategory category, String description, int id, String rightClickOption, int interfaceGroupId, int interfaceChildId, int regionId, WorldPoint point, List<ItemRequirement> itemRequirements)
        {
            this.enumOption = enumOption;
            this.category = category;
            this.description = description;
            this.id = id;
            this.rightClickOption = rightClickOption;
            this.interfaceGroupId = interfaceGroupId;
            this.interfaceChildId = interfaceChildId;
            this.regionId = regionId;
            this.point = point;
            this.itemRequirements = itemRequirements;
            this.overrideLocationName = "";
        }

        public Map<Integer, Integer> getItemRequirements()
        {
            Map<Integer, Integer> requirements = new HashMap<>();

            for (ItemRequirement itemRequirement : itemRequirements) {
                requirements.put(itemRequirement.getItemId(), itemRequirement.getQuantity());
            }

            return requirements;
        }

        public WorldPoint getPoint()
        {
            return point;
        }

        public Location.Teleport overrideLocationName(String overrideLocationName)
        {
            this.overrideLocationName = overrideLocationName;

            return this;
        }

        public String overrideLocationName()
        {
            return this.overrideLocationName;
        }

        public TeleportCategory getCategory()
        {
            return category;
        }

        public int getRegionId()
        {
            return regionId;
        }

        public String getEnumOption()
        {
            return enumOption;
        }

        public String getDescription()
        {
            return description;
        }

        public String getRightClickOption()
        {
            return rightClickOption;
        }

        public int getId()
        {
            return id;
        }

        public int getInterfaceGroupId()
        {
            return interfaceGroupId;
        }

        public int getInterfaceChildId()
        {
            return interfaceChildId;
        }
    }
}