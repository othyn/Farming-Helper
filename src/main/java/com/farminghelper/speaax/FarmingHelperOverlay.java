package com.farminghelper.speaax;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.List;
import javax.inject.Inject;

import net.runelite.api.*;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.components.ImageComponent;
import net.runelite.client.ui.overlay.components.PanelComponent;
import java.awt.image.BufferedImage;
import net.runelite.client.game.ItemManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.AbstractMap;
import java.awt.Color;
import com.farminghelper.speaax.ItemsAndLocations.HerbRunItemAndLocation;
import com.farminghelper.speaax.ItemsAndLocations.TreeRunItemAndLocation;
import com.farminghelper.speaax.ItemsAndLocations.FruitTreeRunItemAndLocation;

public class FarmingHelperOverlay extends Overlay {

    private HerbRunItemAndLocation herbRunItemAndLocation;
    private TreeRunItemAndLocation treeRunItemAndLocation;
    private FruitTreeRunItemAndLocation fruitTreeRunItemAndLocation;
    private final Client client;
    private final FarmingHelperPlugin plugin;
    private final PanelComponent panelComponent = new PanelComponent();
    @Inject
    private ItemManager itemManager;

    public static final List<Integer> TELEPORT_CRYSTAL_IDS = Arrays.asList(ItemID.TELEPORT_CRYSTAL_1, ItemID.TELEPORT_CRYSTAL_2, ItemID.TELEPORT_CRYSTAL_3, ItemID.TELEPORT_CRYSTAL_4, ItemID.TELEPORT_CRYSTAL_5);
    private static final int BASE_TELEPORT_CRYSTAL_ID = ItemID.TELEPORT_CRYSTAL_1;
    public List<Integer> getTeleportCrystalIdsIds() {
        return TELEPORT_CRYSTAL_IDS;
    }
    private boolean isTeleportCrystal(int itemId) {
        return TELEPORT_CRYSTAL_IDS.contains(itemId);
    }


    public static final List<Integer> HERB_PATCH_IDS = Arrays.asList(33176, 27115, 8152, 8150, 8153, 18816, 8151, 9372, 33979 );
    public List<Integer> getHerbPatchIds() {
        return HERB_PATCH_IDS;
    }
    private static final List<Integer> HERB_SEED_IDS = Arrays.asList(5291, 5292, 5293, 5294, 5295, 5296, 5297, 5298, 5299, 5300, 5301, 5302, 5303, 5304  );
    private static final int BASE_SEED_ID = 5291;
    public List<Integer> getHerbSeedIds() {
        return HERB_SEED_IDS;
    }
    private boolean isHerbSeed(int itemId) {
        return HERB_SEED_IDS.contains(itemId);
    }



    private static final List<Integer> FLOWER_PATCH_IDS = Arrays.asList(27111, 7849, 7847, 7850, 7848, 33649);
    public List<Integer> getFlowerPatchIds() {
        return FLOWER_PATCH_IDS;
    }


    public static final List<Integer> TREE_PATCH_IDS = Arrays.asList(8389, 33732, 19147, 8391, 8388, 8390);
    public List<Integer> getTreePatchIds() {
        return TREE_PATCH_IDS;
    }
    private static final List<Integer> TREE_SAPLING_IDS = Arrays.asList(ItemID.OAK_SAPLING, ItemID.WILLOW_SAPLING,ItemID.MAPLE_SAPLING,ItemID.YEW_SAPLING,ItemID.MAGIC_SAPLING);
    private static final int BASE_SAPLING_ID = ItemID.OAK_SAPLING;
    public List<Integer> getTreeSaplingIds() {
        return TREE_SAPLING_IDS;
    }
    private boolean isTreeSapling(int itemId) {return TREE_SAPLING_IDS.contains(itemId);}


    public static final List<Integer> FRUIT_TREE_PATCH_IDS = Arrays.asList(7964, 7965, 34007, 7962, 26579, 7963);
    public List<Integer> getFruitTreePatchIds() {
        return FRUIT_TREE_PATCH_IDS;
    }
    private static final List<Integer> FRUIT_TREE_SAPLING_IDS = Arrays.asList(ItemID.APPLE_SAPLING, ItemID.BANANA_SAPLING,ItemID.ORANGE_SAPLING,ItemID.CURRY_SAPLING,ItemID.PINEAPPLE_SAPLING,ItemID.PAPAYA_SAPLING,ItemID.PALM_SAPLING, ItemID.DRAGONFRUIT_SAPLING);
    private static final int BASE_FRUIT_SAPLING_ID = ItemID.APPLE_SAPLING;
    public List<Integer> getFruitTreeSaplingIds() {return FRUIT_TREE_SAPLING_IDS;}
    private boolean isFruitTreeSapling(int itemId) {return FRUIT_TREE_SAPLING_IDS.contains(itemId);}



    @Inject
    public FarmingHelperOverlay(Client client, FarmingHelperPlugin plugin, ItemManager itemManager, HerbRunItemAndLocation herbRunItemAndLocation, TreeRunItemAndLocation treeRunItemAndLocation, FruitTreeRunItemAndLocation fruitTreeRunItemAndLocation) {
        this.client = client;
        this.plugin = plugin;
        this.itemManager = itemManager;
        this.herbRunItemAndLocation = herbRunItemAndLocation;
        this.treeRunItemAndLocation = treeRunItemAndLocation;
        this.fruitTreeRunItemAndLocation = fruitTreeRunItemAndLocation;
        setPosition(OverlayPosition.BOTTOM_RIGHT);
        setLayer(OverlayLayer.ABOVE_SCENE);
    }

    public Map<Integer, Integer> itemsToCheck;
    @Override
    public Dimension render(Graphics2D graphics) {
        if (plugin.isOverlayActive() && !plugin.areItemsCollected()) {
            if (!plugin.isOverlayActive()) {
                return null;
            }
            plugin.addTextToInfoBox("Grab all the items needed");
            // List of items to check
            //Map<Integer, Integer> itemsToCheck = plugin.getAllItemRequirements(plugin.locations);
            //Map<Integer, Integer> itemsToCheck = herbRunItemAndLocation.getHerbItems();
            Map<Integer, Integer> itemsToCheck = null;
            if(plugin.getFarmingTeleportOverlay().herbRun) {
                itemsToCheck = herbRunItemAndLocation.getHerbItems();
            }
            if(plugin.getFarmingTeleportOverlay().treeRun) {
                itemsToCheck = treeRunItemAndLocation.getTreeItems();
            }
            if(plugin.getFarmingTeleportOverlay().fruitTreeRun) {
                itemsToCheck = fruitTreeRunItemAndLocation.getFruitTreeItems();
            }

            if (itemsToCheck == null || itemsToCheck.isEmpty()) {
                return null;
            }

            ItemContainer inventory = client.getItemContainer(InventoryID.INVENTORY);

            Item[] items;
            if (inventory == null || inventory.getItems() == null) {
                items = new Item[0];
            } else {
                items = inventory.getItems();
            }

            int teleportCrystalCount = 0;
            for (Item item : items) {
                if (isTeleportCrystal(item.getId())) {
                    teleportCrystalCount += item.getQuantity();
                    break;
                }
            }



            int totalSeeds = 0;
            if(plugin.getFarmingTeleportOverlay().herbRun) {
                for (Item item : items) {
                    if (isHerbSeed(item.getId())) {
                        totalSeeds += item.getQuantity();
                    }
                }
            }
            if(plugin.getFarmingTeleportOverlay().treeRun) {
                for (Item item : items) {
                    if (isTreeSapling(item.getId())) {
                        totalSeeds += item.getQuantity();
                    }
                }
            }
            if(plugin.getFarmingTeleportOverlay().fruitTreeRun) {
                for (Item item : items) {
                    if (isFruitTreeSapling(item.getId())) {
                        totalSeeds += item.getQuantity();
                    }
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
                if(plugin.getFarmingTeleportOverlay().herbRun) {
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
                }
                if(plugin.getFarmingTeleportOverlay().treeRun) {
                    if (itemId == BASE_SAPLING_ID) {
                        inventoryCount = totalSeeds;
                    } else {
                        for (Item item : items) {
                            if (item != null && item.getId() == itemId) {
                                inventoryCount = item.getQuantity();
                                break;
                            }
                        }
                    }
                }
                if(plugin.getFarmingTeleportOverlay().fruitTreeRun) {
                    if (itemId == BASE_FRUIT_SAPLING_ID) {
                        inventoryCount = totalSeeds;
                    } else {
                        for (Item item : items) {
                            if (item != null && item.getId() == itemId) {
                                inventoryCount = item.getQuantity();
                                break;
                            }
                        }
                    }
                }

                if (itemId == BASE_TELEPORT_CRYSTAL_ID) {
                    inventoryCount = teleportCrystalCount;
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
                        ImageComponent imageComponent = new ImageComponent(itemImage);
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