package com.farminghelper.speaax.ItemsAndLocations;

import com.farminghelper.speaax.FarmingHelperConfig;
import com.farminghelper.speaax.FarmingHelperPlugin;
import com.farminghelper.speaax.ItemRequirement;
import com.farminghelper.speaax.Location;
import net.runelite.api.Client;
import net.runelite.api.ItemID;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemAndLocation
{
    protected FarmingHelperConfig config;

    protected Client client;

    protected FarmingHelperPlugin plugin;

    public List<Location> locations = new ArrayList<>();

    protected List<Integer> woodcuttingAxes = Arrays.asList(
        ItemID.BRONZE_AXE,
        ItemID.BRONZE_FELLING_AXE,
        ItemID.IRON_AXE,
        ItemID.IRON_FELLING_AXE,
        ItemID.STEEL_AXE,
        ItemID.STEEL_FELLING_AXE,
        ItemID.BLACK_AXE,
        ItemID.BLACK_FELLING_AXE,
        ItemID.MITHRIL_AXE,
        ItemID.MITHRIL_FELLING_AXE,
        ItemID.ADAMANT_AXE,
        ItemID.ADAMANT_FELLING_AXE,
        ItemID.BLESSED_AXE,
        ItemID.RUNE_AXE,
        ItemID.RUNE_FELLING_AXE,
        ItemID.GILDED_AXE,
        ItemID.DRAGON_AXE,
        ItemID.DRAGON_FELLING_AXE,
        ItemID.INFERNAL_AXE,
        // Third Age Axe doesn't appear in this enum yet...
        // Third Age Felling Axe doesn't appear in this enum yet...
        ItemID.CRYSTAL_AXE,
        ItemID.CRYSTAL_FELLING_AXE
    );

    public ItemAndLocation()
    {
    }

    public ItemAndLocation(FarmingHelperConfig config, Client client, FarmingHelperPlugin plugin)
    {
        this.config = config;
        this.client = client;
        this.plugin = plugin;
    }

    public List<ItemRequirement> getHouseTeleportItemRequirements()
    {
        FarmingHelperConfig.OptionEnumHouseTele selectedOption = config.enumConfigHouseTele();

        List<ItemRequirement> itemRequirements = new ArrayList<>();

        switch (selectedOption) {
            case Law_air_earth_runes:
                itemRequirements.add(new ItemRequirement(
                    ItemID.AIR_RUNE,
                    1
                ));

                itemRequirements.add(new ItemRequirement(
                    ItemID.EARTH_RUNE,
                    1
                ));

                itemRequirements.add(new ItemRequirement(
                    ItemID.LAW_RUNE,
                    1
                ));

                break;

            // case Law_dust_runes:
            //     itemRequirements.add(new ItemRequirement(
            //         ItemID.DUST_RUNE,
            //         1
            //     ));
            //
            //     itemRequirements.add(new ItemRequirement(
            //         ItemID.LAW_RUNE,
            //         1
            //     ));
            //
            //     break;

            case Teleport_To_House:
                itemRequirements.add(new ItemRequirement(
                    ItemID.TELEPORT_TO_HOUSE,
                    1
                ));

                break;

            case Construction_cape:
                itemRequirements.add(new ItemRequirement(
                    ItemID.CONSTRUCT_CAPE,
                    1
                ));

                break;

            case Construction_cape_t:
                itemRequirements.add(new ItemRequirement(
                    ItemID.CONSTRUCT_CAPET,
                    1
                ));

                break;

            case Max_cape:
                itemRequirements.add(new ItemRequirement(
                    ItemID.MAX_CAPE,
                    1
                ));

                break;

            default:
                throw new IllegalStateException("Unexpected value: " + selectedOption);
        }

        return itemRequirements;
    }

    public Integer selectedCompostID()
    {
        FarmingHelperConfig.OptionEnumCompost selectedCompost = config.enumConfigCompost();

        switch (selectedCompost) {
            case Compost:
                return ItemID.COMPOST;

            case Supercompost:
                return ItemID.SUPERCOMPOST;

            case Ultracompost:
                return ItemID.ULTRACOMPOST;

            case Bottomless:
                return ItemID.BOTTOMLESS_COMPOST_BUCKET_22997;
        }

        return - 1;
    }

    public void setupLocations()
    {
        locations.clear();
    }
}
