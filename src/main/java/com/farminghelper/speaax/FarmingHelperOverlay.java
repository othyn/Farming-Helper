//package net.runelite.client.plugins.farminghelper;
package com.farminghelper.speaax;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.List;
import javax.inject.Inject;

import net.runelite.api.*;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.components.PanelComponent;
import java.awt.image.BufferedImage;
import net.runelite.client.game.ItemManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import java.util.AbstractMap;
import java.awt.Color;

public class FarmingHelperOverlay extends Overlay {
    private Map<Integer, Integer> itemsToCheck;
    private final Client client;
    private final FarmingHelperPlugin plugin;
    private final PanelComponent panelComponent = new PanelComponent();
    @Inject
    private ItemManager itemManager;

    public void setItemsToCheck(Map<Integer, Integer> itemsToCheck) {
        this.itemsToCheck = itemsToCheck;
    }
    private static final List<Integer> HERB_PATCH_IDS = Arrays.asList(33176, 27115, 8152, 8150, 8153, 18816, 8151, 9372, 33979 );
    public List<Integer> getHerbPatchIds() {
        return HERB_PATCH_IDS;
    }
    private static final List<Integer> FLOWER_PATCH_IDS = Arrays.asList(27111, 7849, 7847, 7850, 7848, 33649);
    public List<Integer> getFlowerPatchIds() {
        return FLOWER_PATCH_IDS;
    }
    private static final List<Integer> HERB_SEED_IDS = Arrays.asList(5291, 5292, 5293, 5294, 5295, 5296, 5297, 5298, 5299, 5300, 5301, 5302, 5303, 5304  );
    private static final int BASE_SEED_ID = 5291;
    public List<Integer> getHerbSeedIds() {
        return HERB_SEED_IDS;
    }
    private boolean isHerbSeed(int itemId) {
        return HERB_SEED_IDS.contains(itemId);
    }

    @Inject
    public FarmingHelperOverlay(Client client, FarmingHelperPlugin plugin) {
        setPosition(OverlayPosition.TOP_LEFT);
        setLayer(OverlayLayer.ABOVE_SCENE);
        this.client = client;
        this.plugin = plugin;
        //this.itemsToCheck = new ArrayList<>();
        this.itemsToCheck = new HashMap<>();
    }

    public void updateItems(Map<Integer, Integer> items) {
        itemsToCheck.clear();
        itemsToCheck.putAll(items);
    }

    @Override
    public Dimension render(Graphics2D graphics) {
        if (plugin.isOverlayActive() && !plugin.areItemsCollected()) {
            if (!plugin.isOverlayActive()) {
                return null;
            }
            // List of items to check
            Map<Integer, Integer> itemsToCheck = plugin.getHerbItemsCache();
            if (itemsToCheck == null || itemsToCheck.isEmpty()) {
                return null;
            }

            ItemContainer inventory = client.getItemContainer(InventoryID.INVENTORY);
            if (inventory == null) {
                return null;
            }

            Item[] items = inventory.getItems();
            if (items == null || items.length == 0) {
                return null;
            }
            int totalSeeds = 0;
            for (Item item : items) {
                if (isHerbSeed(item.getId())) {
                    totalSeeds += item.getQuantity();
                }
            }

            panelComponent.getChildren().clear();
            int yOffset = 0;

            List<AbstractMap.SimpleEntry<Integer, Integer>> missingItemsWithCounts = new ArrayList<>();
            boolean allItemsCollected = true;
            for (Map.Entry<Integer, Integer> entry : itemsToCheck.entrySet()) {
                int itemId = entry.getKey();
                int count = entry.getValue();

                int inventoryCount = 0;
                if (itemId == BASE_SEED_ID) {
                    inventoryCount = totalSeeds;
                } else {
                    for (Item item : items) {
                        if (item != null && item.getId() == itemId) {
                            inventoryCount = item.getQuantity();
                            break;
                        }
                    }
                }

                for (Item item : items) {
                    if (item != null && item.getId() == itemId) {
                        inventoryCount = item.getQuantity();
                        break;
                    }
                }

                if (inventoryCount < count) {
                    allItemsCollected = false;
                    int missingCount = count - inventoryCount;
                    BufferedImage itemImage = itemManager.getImage(itemId);
                    if (itemImage != null) {
                        OutlinedImageComponent imageComponent = new OutlinedImageComponent(itemImage, 0, yOffset);
                        panelComponent.getChildren().add(imageComponent);

                        // Add the missing item and count to the list
                        missingItemsWithCounts.add(new AbstractMap.SimpleEntry<>(itemId, missingCount));

                        yOffset += itemImage.getHeight() + 2; // Update yOffset for the next item
                    }
                }
            }
            plugin.setTeleportOverlayActive(allItemsCollected);
            Dimension panelSize = panelComponent.render(graphics);

            // Draw item count on top of the overlay
            yOffset = 0;
            for (AbstractMap.SimpleEntry<Integer, Integer> pair : missingItemsWithCounts) {
                int itemId = pair.getKey();
                int missingCount = pair.getValue();

                BufferedImage itemImage = itemManager.getImage(itemId);
                if (itemImage != null) {
                    // Draw item count
                    if (missingCount > 1) {
                        String countText = Integer.toString(missingCount);
                        int textX = 2; // Calculate X position for the count text
                        int textY = yOffset + 15; // Calculate Y position for the count text
                        graphics.setColor(Color.WHITE);
                        graphics.drawString(countText, textX, textY);
                    }

                    yOffset += itemImage.getHeight() + 2; // Update yOffset for the next item
                }
            }
            // Check if all items have been collected
            if (missingItemsWithCounts.isEmpty()) {
                plugin.setItemsCollected(true);
            } else {
                plugin.setItemsCollected(false);
            }

            return panelSize;
        }
        return null;
    }
}