package com.farminghelper.speaax.Patch;

public enum PatchType
{
    HERB("Herb Run"),
    TREE("Tree Run"),
    FRUIT_TREE("Fruit Tree Run"),
    HARDWOOD_TREE("Hardwood Tree Run"),
    REDWOOD_TREE("Redwood Tree Run"),
    CALQUAT_TREE("Calquat Tree Run"),
    CELASTRUS_TREE("Celastrus Tree Run");

    private final String displayName;

    PatchType(String displayName)
    {
        this.displayName = displayName;
    }

    @Override
    public String toString()
    {
        return displayName;
    }

//    public List<ItemRequirement> requiredItems()
//    {
//        return;
//    }
}