package com.farminghelper.speaax.ItemsAndLocations;

import com.farminghelper.speaax.FarmingHelperConfig;
import com.farminghelper.speaax.FarmingHelperPlugin;
import com.farminghelper.speaax.Location;
import net.runelite.api.Client;
import net.runelite.api.ItemID;

import java.util.*;

public class HerbRunItemAndLocation extends ItemAndLocation {
    public HerbRunItemAndLocation() {
    }

    public HerbRunItemAndLocation(FarmingHelperConfig config, Client client, FarmingHelperPlugin plugin) {
        super(config, client, plugin);
    }

    public Map<Integer, Integer> getHerbItems() {
        return getAllItemRequirements(locations);
    }

    public Map<Integer, Integer> getAllItemRequirements(List<Location> locations) {
        Map<Integer, Integer> allRequirements = new HashMap<>();

        setupTeleports();

        // Add other items and merge them with allRequirements
        for (Location location : locations) {
            if (plugin.getHerbLocationEnabled(location.getName())) {
                //ItemID.GUAM_SEED is default for herb seeds, code later will allow for any seed to be used, just needed a placeholder ID
                allRequirements.merge(ItemID.GUAM_SEED, 1, Integer::sum);

                if (selectedCompostID() != -1 && selectedCompostID() != ItemID.BOTTOMLESS_COMPOST_BUCKET_22997) {
                    allRequirements.merge(selectedCompostID(), 1, Integer::sum);
                }

                Location.Teleport teleport = location.getSelectedTeleport();
                Map<Integer, Integer> locationRequirements = teleport.getItemRequirements();

                for (Map.Entry<Integer, Integer> entry : locationRequirements.entrySet()) {
                    int itemId = entry.getKey();
                    int quantity = entry.getValue();

                    if (itemId == ItemID.CONSTRUCT_CAPE || itemId == ItemID.CONSTRUCT_CAPET || itemId == ItemID.MAX_CAPE) {
                        allRequirements.merge(itemId, quantity, (oldValue, newValue) -> Math.min(1, oldValue + newValue));
                    } else {
                        allRequirements.merge(itemId, quantity, Integer::sum);
                    }
                }

                if(location.getFarmLimps() && config.generalLimpwurt()) {
                    allRequirements.merge(ItemID.LIMPWURT_SEED, 1, Integer::sum);

                    if (selectedCompostID() != -1 && selectedCompostID() != ItemID.BOTTOMLESS_COMPOST_BUCKET_22997) {
                        allRequirements.merge(selectedCompostID(), 1, Integer::sum);
                    }
                }
            }
        }

        allRequirements.merge(ItemID.SEED_DIBBER, 1, Integer::sum);
        allRequirements.merge(ItemID.SPADE, 1, Integer::sum);

        if (selectedCompostID() == ItemID.BOTTOMLESS_COMPOST_BUCKET_22997) {
            allRequirements.merge(ItemID.BOTTOMLESS_COMPOST_BUCKET_22997, 1, Integer::sum);
        }

        allRequirements.merge(ItemID.MAGIC_SECATEURS, 1, Integer::sum);

        if (config.generalRake()) {
            allRequirements.merge(ItemID.RAKE, 1, Integer::sum);
        }

        return allRequirements;
    }

    public void setupTeleports() {
        super.setupTeleports();

//        move these to a new teleports() method in the enum?

        setupArdougneTeleports();
        setupCatherbyTeleports();
        setupFaladorTeleports();
        setupFarmingGuildTeleports();
        setupHarmonyTeleports();
        setupKourendTeleports();
        setupMorytaniaTeleports();
        setupTrollStrongholdTeleports();
        setupWeissTeleports();
    }
}