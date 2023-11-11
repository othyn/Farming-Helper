package com.farminghelper.speaax.Patch;

import com.farminghelper.speaax.ItemsAndLocations.ItemRequirement;
import net.runelite.api.coords.WorldPoint;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Teleport
{
    public enum Category
    {
        ITEM,
        PORTAL_NEXUS,
        SPIRIT_TREE,
        JEWELLERY_BOX,
        MOUNTED_XERICS,
        SPELLBOOK
    }

    private Category category;

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

    public Teleport(String enumOption, Category category, String description, int id, String rightClickOption, int interfaceGroupId, int interfaceChildId, int regionId, WorldPoint point, List<ItemRequirement> itemRequirements)
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

    public Teleport overrideLocationName(String overrideLocationName)
    {
        this.overrideLocationName = overrideLocationName;

        return this;
    }

    public String overrideLocationName()
    {
        return this.overrideLocationName;
    }

    public Category getCategory()
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
