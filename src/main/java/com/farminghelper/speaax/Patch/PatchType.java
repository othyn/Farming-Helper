package com.farminghelper.speaax.Patch;

public enum PatchType
{
    HERB("Herb Run"),
    TREE("Tree Run"),
    FRUIT_TREE("Fruit Tree Run");

//    FLOWER("Flower Run");

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