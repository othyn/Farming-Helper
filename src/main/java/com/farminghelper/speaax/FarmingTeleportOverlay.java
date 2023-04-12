//package net.runelite.client.plugins.farminghelper;
package com.farminghelper.speaax;

import java.awt.*;
import javax.inject.Inject;
import net.runelite.api.*;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.api.Client;
import net.runelite.api.GameObject;
import net.runelite.api.coords.WorldPoint;
import java.awt.Color;
import java.awt.Point;
import java.awt.Shape;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.farminghelper.speaax.FarmingHelperPlugin;
//import net.runelite.client.plugins.farminghelper.FarmingHelperPlugin;

import net.runelite.client.ui.overlay.components.LineComponent;
import net.runelite.client.ui.overlay.components.PanelComponent;
import net.runelite.api.events.ChatMessage;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.api.ChatMessageType;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

//import net.runelite.client.plugins.farminghelper.FarmingHelperOverlay;
import com.farminghelper.speaax.FarmingHelperOverlay;


public class FarmingTeleportOverlay extends Overlay {
    private final Client client;
    private final FarmingHelperPlugin plugin;
    private boolean clicked = false;
    @Inject
    private FarmingHelperConfig config;
    @Inject
    private FarmingHelperOverlay farmingHelperOverlay;
    @Inject
    private FarmingTeleportOverlay farmingTeleportOverlay;
    @Inject
    private FarmingHelperOverlayInfoBox farmingHelperOverlayInfoBox;

    private final PanelComponent panelComponent = new PanelComponent();
    public boolean patchCleared = false;
    public Color brightBlue = new Color(0, 191, 255, 128);
    public Color brightGreen = new Color(0, 191, 30, 128);
    public Map<String, Boolean> herbConfigMap = new HashMap<>();
    public boolean isHerbComposted(String message) {
        String regexCompost = "You treat the herb patch with (compost|supercompost|ultracompost)\\.";
        Pattern patternCompost = Pattern.compile(regexCompost);
        Matcher matcherCompost = patternCompost.matcher(plugin.getLastMessage());

        return matcherCompost.matches();
    }
    public boolean isHerbPlanted(String message) {
        String regex = "You plant (a guam|a marrentill|a tarromin|a harralander|a ranarr|a toadflax|an irit|an avantoe|a kwuarm|a snapdragon|a cadantine|a lantadyme|a dwarf weed|a torstol) seed in the herb patch\\.";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(message);

        return matcher.matches();
    }

    @Inject
    public FarmingTeleportOverlay(FarmingHelperPlugin plugin, Client client) {
        setPosition(OverlayPosition.DYNAMIC);
        setLayer(OverlayLayer.ABOVE_WIDGETS);
        this.plugin = plugin;
        this.client = client;
    }

    public Overlay interfaceOverlay(int groupId, int childId) {
        return new Overlay() {
            @Override
            public Dimension render(Graphics2D graphics) {
                Client client = plugin.getClient();
                if (client != null) {
                    Widget widget = client.getWidget(groupId, childId);
                    if (widget != null) {
                        Rectangle bounds = widget.getBounds();
                        // Set a brighter blue color and make it transparent
                        Color brightBlue = new Color(0, 191, 255, 128); // Transparent bright blue
                        graphics.setColor(brightBlue);

                        // Set the composite for transparency
                        AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
                        graphics.setComposite(alphaComposite);

                        // Draw a rectangle over the widget
                        graphics.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);

                        // Reset the composite back to the original
                        graphics.setComposite(AlphaComposite.SrcOver);
                    }
                }
                return null;
            }
        };
    }

    public int getChildIndexPN(String searchText) {
        Widget parentWidget = client.getWidget(17, 12);
        if (parentWidget == null) {
            return -1;
        }

        Widget[] children = parentWidget.getChildren();
        if (children == null) {
            return -1;
        }

        for (int index = 0; index < children.length; index++) {
            Widget child = children[index];
            String text = child.getText();
            if (text != null) {
                int colonIndex = text.indexOf(':');
                if (colonIndex != -1 && colonIndex + 1 < text.length()) {
                    String textAfterColon = text.substring(colonIndex + 1).trim();
                    if (textAfterColon.equals(searchText)) {
                        return index;
                    }
                }
            }
        }
        return -1; // Return -1 if the specified text is not found
    }



    public void highlightDynamicComponent(Graphics2D graphics, Widget widget, int dynamicChildIndex, Color color) {
        if (widget != null) {
            Widget[] dynamicChildren = widget.getDynamicChildren();
            if (dynamicChildren != null && dynamicChildIndex >= 0 && dynamicChildIndex < dynamicChildren.length) {
                Widget dynamicChild = dynamicChildren[dynamicChildIndex];
                if (dynamicChild != null) {
                    Rectangle bounds = dynamicChild.getBounds();
                    graphics.setColor(color);
                    //graphics.draw(bounds);
                    graphics.fill(bounds);
                }
            }
        }
    }


    private void drawTile(Graphics2D graphics, Client client, WorldPoint worldPoint, Color color) {
        LocalPoint localPoint = LocalPoint.fromWorld(client, worldPoint);
        if (localPoint != null) {
            Polygon tilePoly = Perspective.getCanvasTilePoly(client, localPoint);
            if (tilePoly != null) {
                graphics.setColor(color);
                graphics.draw(tilePoly);
                graphics.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha() / 5));
                graphics.fill(tilePoly);
            }
        }
    }
    public void itemHighlight(Graphics2D graphics, int itemID, Color color) {
        ItemContainer inventory = client.getItemContainer(InventoryID.INVENTORY);

        if (inventory != null) {
            Item[] items = inventory.getItems();
            Widget inventoryWidget = client.getWidget(WidgetInfo.INVENTORY);

            for (int i = 0; i < items.length; i++) {
                Item item = items[i];

                if (item.getId() == itemID) {
                    Widget itemWidget = inventoryWidget.getChild(i);
                    Rectangle bounds = itemWidget.getBounds();
                    graphics.setColor(color);
                    //graphics.draw(bounds);
                    graphics.fill(bounds);
                }
            }
        }
    }
    //WorldPoint worldPoint = new WorldPoint(3194, 3466, client.getPlane());
    //Color color = new Color(0, 191, 255, 128); // Transparent bright blue
    //drawTile(graphics, client, worldPoint, color);
    private List<GameObject> findGameObjectsByID(int objectID) {
        List<GameObject> gameObjects = new ArrayList<>();
        for (int x = 0; x < Constants.SCENE_SIZE; x++) {
            for (int y = 0; y < Constants.SCENE_SIZE; y++) {
                Tile tile = client.getScene().getTiles()[client.getPlane()][x][y];
                if (tile == null) {
                    continue;
                }

                for (GameObject gameObject : tile.getGameObjects()) {
                    if (gameObject != null && gameObject.getId() == objectID) {
                        gameObjects.add(gameObject);
                    }
                }
            }
        }
        return gameObjects;
    }
    private void drawGameObjectClickbox(Graphics2D graphics, GameObject gameObject, Color color) {
        Shape objectClickbox = gameObject.getClickbox();
        if (objectClickbox != null) {
            graphics.setColor(color);
            graphics.draw(objectClickbox);
            graphics.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha() / 5));
            graphics.fill(objectClickbox);
        }
    }
    public Overlay gameObjectOverlay(int objectId, Color color) {
        return new Overlay() {
            @Override
            public Dimension render(Graphics2D graphics) {
                Client client = plugin.getClient();
                if (client != null) {
                    //Color highlightColor = new Color(0, 191, 255, 128); // Transparent bright blue
                    List<GameObject> gameObjects = findGameObjectsByID(objectId);
                    for (GameObject gameObject : gameObjects) {
                        drawGameObjectClickbox(graphics, gameObject, color);
                    }
                }
                return null;
            }
        };
    }

    public List<DecorativeObject> findDecorativeObjectsByID(int objectId) {
        Client client = plugin.getClient();
        List<DecorativeObject> foundDecorativeObjects = new ArrayList<>();

        if (client != null) {
            Tile[][][] tiles = client.getScene().getTiles();
            for (int plane = 0; plane < tiles.length; plane++) {
                for (int x = 0; x < tiles[plane].length; x++) {
                    for (int y = 0; y < tiles[plane][x].length; y++) {
                        Tile tile = tiles[plane][x][y];
                        if (tile != null) {
                            DecorativeObject decorativeObject = tile.getDecorativeObject();
                            if (decorativeObject != null && decorativeObject.getId() == objectId) {
                                foundDecorativeObjects.add(decorativeObject);
                            }
                        }
                    }
                }
            }
        }

        return foundDecorativeObjects;
    }
    public Overlay decorativeObjectOverlay(int objectId, Color color) {
        return new Overlay() {
            @Override
            public Dimension render(Graphics2D graphics) {
                Client client = plugin.getClient();
                if (client != null) {
                    List<DecorativeObject> decorativeObjects = findDecorativeObjectsByID(objectId);
                    for (DecorativeObject decorativeObject : decorativeObjects) {
                        drawDecorativeObjectClickbox(graphics, decorativeObject, color);
                    }
                }
                return null;
            }
        };
    }


    public void drawDecorativeObjectClickbox(Graphics2D graphics, DecorativeObject decorativeObject, Color color) {
        Shape clickbox = decorativeObject.getClickbox();
        if (clickbox != null) {
            graphics.setColor(color);
            graphics.draw(clickbox);
            graphics.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 50));
            graphics.fill(clickbox);
        }
    }




    private boolean isInterfaceOpen(int groupId, int childId) {
        Widget widget = client.getWidget(groupId, childId);
        return widget != null && !widget.isHidden();
    }

    private void printWidgetText(int groupId, int childId) {
        Widget widget = client.getWidget(groupId, childId);
        if (widget != null) {
            String text = widget.getText();
            System.out.println("Widget text: " + text);
        } else {
            System.out.println("Widget not found for groupId: " + groupId + " and childId: " + childId);
        }
    }
    public void highlightHerbPatches(Graphics2D graphics, Color color) {
        List<Integer> herbPatchIds = farmingHelperOverlay.getHerbPatchIds();
        for (Integer patchId : herbPatchIds) {
            gameObjectOverlay(patchId, color).render(graphics);
        }
    }
    public void highlightFlowerPatches(Graphics2D graphics, Color color) {
        List<Integer> flowerPatchIds = farmingHelperOverlay.getFlowerPatchIds();
        for (Integer patchId : flowerPatchIds) {
            gameObjectOverlay(patchId, color).render(graphics);
        }
    }
    public void highlightHerbSeeds(Graphics2D graphics, Color color) {
        List<Integer> herbSeedIds = farmingHelperOverlay.getHerbSeedIds();
        for(Integer seedId : herbSeedIds) {
            itemHighlight(graphics, seedId, color);
        }
    }


    private int currentHerbCase = 1;
    public Boolean herbPatchDone = false;
    public void herbSteps(Graphics2D graphics){
        switch (currentHerbCase) {
            case 1:
                plugin.addTextToInfoBox("Harvest Herbs.(or plant one if patch is empty/herb died)");
                highlightHerbPatches(graphics, brightBlue);
                if (plugin.checkMessage("The herb patch is now empty.", plugin.getLastMessage())) {
                    System.out.println("Found string");
                    currentHerbCase = 2;
                }
                if (isHerbPlanted(plugin.getLastMessage())) {
                    System.out.println("Planted");
                    currentHerbCase = 3;
                }
                break;
            case 2:
                plugin.addTextToInfoBox("Use Herb seed on patch.");
                highlightHerbPatches(graphics, brightGreen);
                highlightHerbSeeds(graphics, brightGreen);
                if (isHerbPlanted(plugin.getLastMessage())) {
                    System.out.println("Planted");
                    currentHerbCase = 3;
                }
                break;
            case 3:
                plugin.addTextToInfoBox("Use Compost on patch.");
                highlightHerbPatches(graphics, brightBlue);
                itemHighlight(graphics, ItemID.BOTTOMLESS_COMPOST_BUCKET_22997, brightBlue);
                if (isHerbComposted(plugin.getLastMessage())) {
                    System.out.println("Composted");
                    currentHerbCase = 1;
                    herbPatchDone = true;
                }
                break;
        }
    }
    private int currentFlowerCase = 1;
    public static boolean flowerPatchDone = false;
    public void flowerSteps(Graphics2D graphics){
        if (farmLimps) {
            switch (currentFlowerCase) {
                case 1:
                    plugin.addTextToInfoBox("Harvest Limwurt root.(or plant one if the patch is empty/Limwurt died)");
                    highlightFlowerPatches(graphics, brightBlue);
                    if (client.getLocalPlayer().getAnimation() == AnimationID.FARMING_HARVEST_FLOWER) {
                        System.out.println("Found string");
                        currentFlowerCase = 2;
                    }
                    //if statement is a current safety messurment to make sure user wont get stuck at case 1 if
                    //they havent planted a limwurt yet and the patch is empty
                    if (plugin.checkMessage("You plant a limpwurt seed in the flower patch.", plugin.getLastMessage())) {
                        System.out.println("Limpwurt planted");
                        currentFlowerCase = 3;
                    }
                    break;
                case 2:
                    plugin.addTextToInfoBox("Use Limwurt seed on the patch.");
                    highlightFlowerPatches(graphics, brightGreen);
                    itemHighlight(graphics, ItemID.LIMPWURT_SEED, brightGreen);
                    if (plugin.checkMessage("You plant a limpwurt seed in the flower patch.", plugin.getLastMessage())) {
                        System.out.println("Limpwurt planted");
                        currentFlowerCase = 3;
                    }
                    break;
                case 3:
                    plugin.addTextToInfoBox("Use compost on Limpwurt patch.");
                    highlightFlowerPatches(graphics, brightBlue);
                    itemHighlight(graphics, ItemID.BOTTOMLESS_COMPOST_BUCKET_22997, brightBlue);
                    if (plugin.checkMessage("You treat the flower patch with ultracompost.", plugin.getLastMessage())) {
                        System.out.println("Limpwurt composted");
                        flowerPatchDone = true;
                        currentFlowerCase = 1;
                    }
                    break;
            }
        }
        else {
            flowerPatchDone = true;
        }
    }

    private List<Integer> getGameObjectIdsByName(String name) {
        List<Integer> foundObjectIds = new ArrayList<>();
        Scene scene = client.getScene();
        Tile[][][] tiles = scene.getTiles();

        for (int x = 0; x < Constants.SCENE_SIZE; x++) {
            for (int y = 0; y < Constants.SCENE_SIZE; y++) {
                Tile tile = tiles[client.getPlane()][x][y];
                if (tile == null) {
                    continue;
                }

                for (GameObject gameObject : tile.getGameObjects()) {
                    if (gameObject != null) {
                        ObjectComposition objectComposition = client.getObjectDefinition(gameObject.getId());
                        if (objectComposition != null && objectComposition.getName().equals(name)) {
                            foundObjectIds.add(gameObject.getId());
                        }
                    }
                }
            }
        }

        return foundObjectIds;
    }

    private int currentTeleportToHouseCase = 1;
    public void gettingToHouse(Graphics2D graphics) {
        FarmingHelperConfig.OptionEnumHouseTele teleportOption = config.enumConfigHouseTele();
        switch(teleportOption) {
            case Law_air_earth_runes:
            case Law_dust_runes:
                switch (currentTeleportToHouseCase) {
                    case 1:
                        interfaceOverlay(161, 64).render(graphics);
                        if (plugin.isClicked(161, 64)) {
                            System.out.println("Spellbook clicked");
                            currentTeleportToHouseCase = 2;
                        }
                        break;
                    case 2:
                        interfaceOverlay(218, 29).render(graphics);
                        if (plugin.isClicked(218, 29)) {
                            System.out.println("House teleport clicked");
                            currentTeleportToHouseCase = 1;
                            currentTeleportCase = 2;

                        }
                        break;
                }
                break;
            case Teleport_To_House:
                itemHighlight(graphics, ItemID.TELEPORT_TO_HOUSE, brightBlue);
                break;
            case Construction_cape:
                itemHighlight(graphics, ItemID.CONSTRUCT_CAPE, brightBlue);
                break;
            case Construction_cape_t:
                itemHighlight(graphics, ItemID.CONSTRUCT_CAPET, brightBlue);
                break;
            case Max_cape:
                itemHighlight(graphics, ItemID.MAX_CAPE, brightBlue);
                break;
        }
    }
    public static int currentTeleportCase = 1;
    public static boolean isAtArdougne = false;
    public void gettingToArdougne(Graphics2D graphics){
        if(!isAtArdougne) {
            FarmingHelperConfig.OptionEnumArdougneTeleport teleportOption = config.enumOptionEnumArdougneTeleport();
            switch (teleportOption) {
                case Ardy_cloak:
                    plugin.addTextToInfoBox("Teleport to Ardougne farm patches.");
                    itemHighlight(graphics, ItemID.ARDOUGNE_CLOAK_2, brightBlue);
                    itemHighlight(graphics, ItemID.ARDOUGNE_CLOAK_3, brightBlue);
                    itemHighlight(graphics, ItemID.ARDOUGNE_CLOAK_4, brightBlue);
                    if (client.getLocalPlayer().getAnimation() == 3872) {
                        isAtArdougne = true;
                        startSubCases = true;
                        if(config.generalLimpwurt()) {
                            farmLimps = true;
                        }
                    }
                    break;
            }
        }
    }
    public static boolean isAtCatherby = false;
    public void gettingToCatherby(Graphics2D graphics){
        if(!isAtCatherby) {
        FarmingHelperConfig.OptionEnumCatherbyTeleport teleportOption = config.enumOptionEnumCatherbyTeleport();
            switch (teleportOption) {
                case Portal_Nexus:
                    plugin.addTextToInfoBox("Teleport to Catherby using the Portal Nexus.");
                    switch (currentTeleportCase) {
                        case 1:
                            gettingToHouse(graphics);
                            break;
                        case 2:
                            //gameObjectOverlay(33409, brightBlue).render(graphics);
                            //gameObjectOverlay(ObjectID.PORTAL_NEXUS_SPACE, brightBlue).render(graphics);
                            List<Integer> portalNexusIds = getGameObjectIdsByName("Portal Nexus");
                            for (Integer objectId : portalNexusIds) {
                                gameObjectOverlay(objectId, brightBlue).render(graphics);
                            }
                            if (isInterfaceOpen(17, 0)) {
                                currentTeleportCase = 3;
                            }
                            break;
                        case 3:
                            Widget widget = client.getWidget(17, 13);
                            int catherbyIndex = getChildIndexPN("Catherby");
                            highlightDynamicComponent(graphics, widget, catherbyIndex, brightBlue);
                            if (plugin.isClicked(17, 13)) {
                                currentTeleportCase = 1;
                                isAtCatherby = true;
                                startSubCases = true;
                                if(config.generalLimpwurt()) {
                                    farmLimps = true;
                                }
                            }
                            break;
                    }
                    break;
            }
        }
    }
    public static boolean isAtFalador = false;
    public void gettingToFalador(Graphics2D graphics){
        if(!isAtFalador) {
        FarmingHelperConfig.OptionEnumFaladorTeleport teleportOption = config.enumOptionEnumFaladorTeleport();
        switch (teleportOption) {
            case Explorers_ring:
                plugin.addTextToInfoBox("Teleport to Falador farm patches.");
                itemHighlight(graphics, ItemID.EXPLORERS_RING_2, brightBlue);
                itemHighlight(graphics, ItemID.EXPLORERS_RING_3, brightBlue);
                itemHighlight(graphics, ItemID.EXPLORERS_RING_4, brightBlue);
                if (client.getLocalPlayer().getAnimation() == 3869) {
                    isAtFalador = true;
                    startSubCases = true;
                    if(config.generalLimpwurt()) {
                        farmLimps = true;
                    }

                }
        }
        }
    }
    public static boolean isAtFarmingGuild = false;
    public void gettingToFarmingGuild(Graphics2D graphics){
        if(!isAtFarmingGuild) {
            FarmingHelperConfig.OptionEnumFarmingGuildTeleport teleportOption = config.enumOptionEnumFarmingGuildTeleport();
            switch (teleportOption) {
                case Jewellery_box:
                    plugin.addTextToInfoBox("Teleport to Farming Guild.");
                    switch (currentTeleportCase) {
                        case 1:
                            gettingToHouse(graphics);
                            break;
                        case 2:
                            gameObjectOverlay(29155, brightBlue).render(graphics);
                            if (isInterfaceOpen(590, 0)) {
                                currentTeleportCase = 3;
                                System.out.println("Jewellery interface open");
                            }
                            break;
                        case 3:
                            Widget widget = client.getWidget(590, 5);
                            highlightDynamicComponent(graphics, widget, 10, brightBlue);
                            //interfaceOverlay(590, 5).render(graphics);
                            if (client.getLocalPlayer().getAnimation() == 714) {
                                currentTeleportCase = 1;
                                isAtFarmingGuild = true;
                                startSubCases = true;
                                if(config.generalLimpwurt()) {
                                    farmLimps = true;
                                }
                            }
                            break;
                    }
                    break;
            }
        }
    }
    public static boolean isAtHarmony = false;
    public void gettingToHarmony(Graphics2D graphics){
        if(!isAtHarmony) {
            FarmingHelperConfig.OptionEnumHarmonyTeleport teleportOption = config.enumOptionEnumHarmonyTeleport();
            switch (teleportOption) {
                case Portal_Nexus:
                    plugin.addTextToInfoBox("Teleport to Harmony Island.");
                    switch (currentTeleportCase) {
                        case 1:
                            gettingToHouse(graphics);
                            break;
                        case 2:
                            gameObjectOverlay(33409, brightBlue).render(graphics);
                            if (isInterfaceOpen(17, 0)) {
                                currentTeleportCase = 3;
                            }
                            break;
                        case 3:
                            Widget widget = client.getWidget(17, 13);
                            int harmonyIslandIndex = getChildIndexPN("Harmony Island");
                            highlightDynamicComponent(graphics, widget, harmonyIslandIndex, brightBlue);
                            if (plugin.isClicked(17, 13)) {
                                currentTeleportCase = 1;
                                startSubCases = true;
                                isAtHarmony = true;
                            }
                            break;
                    }
                    break;
                default:
                    // Optional: Code for handling unexpected values
                    break;
            }
        }
    }
    public static boolean isAtKourend = false;
    public void gettingToKourend(Graphics2D graphics){
        if(!isAtKourend) {
            FarmingHelperConfig.OptionEnumKourendTeleport teleportOption = config.enumOptionEnumKourendTeleport();
            switch (teleportOption) {
                case Mounted_Xerics:
                    plugin.addTextToInfoBox("Teleport to Kourend farm patchs.");
                    switch (currentTeleportCase) {
                        case 1:
                            gettingToHouse(graphics);
                            break;
                        case 2:
                            List<Integer> xericsTalismanIds = Arrays.asList(33411 , 33412, 33413, 33414, 33415);

                            for (int id : xericsTalismanIds) {
                                Overlay decorativeObjectHighlight = decorativeObjectOverlay(id, brightBlue);
                                decorativeObjectHighlight.render(graphics);
                            }
                            if(isInterfaceOpen(187, 3)) {
                                currentTeleportCase = 3;
                            }
                            break;
                        case 3:
                            Widget widget = client.getWidget(187, 3);
                            highlightDynamicComponent(graphics, widget, 1, brightBlue);
                            if (client.getLocalPlayer().getAnimation() == 3865) {
                                currentTeleportCase = 1;
                                startSubCases = true;
                                isAtKourend = true;
                                if(config.generalLimpwurt()) {
                                    farmLimps = true;
                                }
                            }
                            break;
                    }
                    break;
                case Xerics_Talisman:
                    plugin.addTextToInfoBox("Teleport to Kourend farm patchs.");
                    switch (currentTeleportCase) {
                        case 1:
                            itemHighlight(graphics, ItemID.XERICS_TALISMAN, brightBlue);
                            if(isInterfaceOpen(187, 3)) {
                                currentTeleportCase = 2;
                            }
                            //if (client.getLocalPlayer().getAnimation() == 3865) {
                             //   currentTeleportCase = 2;
                             //   System.out.println("Xerics talisman used");
                            //}
                        case 2:
                            Widget widget = client.getWidget(187, 3);
                            highlightDynamicComponent(graphics, widget, 1, brightBlue);
                            if (client.getLocalPlayer().getAnimation() == 3865) {
                                currentTeleportCase = 1;
                                startSubCases = true;
                                isAtKourend = true;
                                if(config.generalLimpwurt()) {
                                    farmLimps = true;
                                }
                            }
                            break;
                            default:
                                // Optional: Code for handling unexpected values
                                break;
                    }
            }
        }
    }
    public static boolean isAtMorytania = false;
    public void gettingToMorytania(Graphics2D graphics){
        if(!isAtMorytania) {
            FarmingHelperConfig.OptionEnumMorytaniaTeleport teleportOption = config.enumOptionEnumMorytaniaTeleport();
            switch (teleportOption) {
                case Ectophial:
                    plugin.addTextToInfoBox("Teleport to Morytania with Ectophial and run east until you can see the farming patches.");
                    itemHighlight(graphics, ItemID.ECTOPHIAL, brightBlue);
                    if (client.getLocalPlayer().getAnimation() == 878) {
                        isAtMorytania = true;
                        startSubCases = true;
                        if(config.generalLimpwurt()) {
                            farmLimps = true;
                        }
                    }
            }
        }
    }
    public static boolean isAtTrollStronghold = false;
    public void gettingToTrollStronghold(Graphics2D graphics){
        if(!isAtTrollStronghold) {
            FarmingHelperConfig.OptionEnumTrollStrongholdTeleport teleportOption = config.enumOptionEnumTrollStrongholdTeleport();
            switch (teleportOption) {
                case Portal_Nexus:
                    plugin.addTextToInfoBox("Use Portal Nexus to get to Troll Stronghold.");
                    switch (currentTeleportCase) {
                        case 1:
                            gettingToHouse(graphics);
                            break;
                        case 2:
                            gameObjectOverlay(33409, brightBlue).render(graphics);
                            if (isInterfaceOpen(17, 0)) {
                                currentTeleportCase = 3;
                            }
                            break;
                        case 3:
                            Widget widget = client.getWidget(17, 13);
                            int trollStrongholdIndex = getChildIndexPN("Troll Stronghold");
                            highlightDynamicComponent(graphics, widget, trollStrongholdIndex, brightBlue);
                            if (plugin.isClicked(17, 13)) {
                                currentTeleportCase = 1;
                                startSubCases = true;
                                isAtTrollStronghold = true;
                            }
                            break;
                    }
                    break;
                case Stony_Basalt:
                    plugin.addTextToInfoBox("Use Stony Basalt to get to Troll Stronghold.");
                    itemHighlight(graphics, ItemID.STONY_BASALT, brightBlue);
                    if (client.getLocalPlayer().getAnimation() == 8172) {
                        isAtTrollStronghold = true;
                        startSubCases = true;
                        System.out.println("Stony basalt used");
                    }
                    break;
                default:
                    // Optional: Code for handling unexpected values
                    break;
            }
        }
    }

    public static boolean isAtWeiss = false;

    public void gettingToWeiss(Graphics2D graphics) {
        if(!isAtWeiss) {
            FarmingHelperConfig.OptionEnumWeissTeleport teleportOption = config.enumOptionEnumWeissTeleport();
            switch (teleportOption) {
                case Portal_Nexus:
                    plugin.addTextToInfoBox("Use Portal Nexus to get to Weiss.");
                    switch (currentTeleportCase) {
                        case 1:
                            gettingToHouse(graphics);
                            break;
                        case 2:
                            gameObjectOverlay(33409, brightBlue).render(graphics);
                            if (isInterfaceOpen(17, 0)) {
                                currentTeleportCase = 3;
                            }
                            break;
                        case 3:
                            Widget widget = client.getWidget(17, 13);
                            int weissIndex = getChildIndexPN("Weiss");
                            highlightDynamicComponent(graphics, widget, weissIndex, brightBlue);
                            if (plugin.isClicked(17, 13)) {
                                currentTeleportCase = 1;
                                startSubCases = true;
                                isAtWeiss = true;
                            }
                            break;
                    }
                    break;
                case Icy_basalt:
                    plugin.addTextToInfoBox("Use Icy Basalt to get to Weiss.");
                    itemHighlight(graphics, ItemID.ICY_BASALT, brightBlue);
                    if (client.getLocalPlayer().getAnimation() == 8172) {
                        isAtWeiss = true;
                        startSubCases = true;
                        System.out.println("Icy basalt used");
                    }
                    break;
                default:
                    // Optional: Code for handling unexpected values
                    break;
            }
        }
    }
    public static boolean farmLimps = false;
    public void farming(Graphics2D graphics) {
        if (startSubCases) {
            if (subCase == 1) {
                herbSteps(graphics);
                if (herbPatchDone) {
                    subCase = 2;
                    herbPatchDone = false;
                }
            } else if (subCase == 2) {
                if (config.generalLimpwurt()){
                    flowerSteps(graphics);
                    if (flowerPatchDone) {
                        subCase = 1;
                        startSubCases = false;
                        isAtArdougne = false;
                        isAtCatherby = false;
                        isAtFalador = false;
                        isAtFarmingGuild = false;
                        isAtHarmony = false;
                        isAtKourend = false;
                        isAtMorytania = false;
                        isAtTrollStronghold = false;
                        isAtWeiss = false;
                        herbRunIndex++;
                        farmLimps = false;
                        flowerPatchDone = false;

                    }
                }
                else{
                    subCase = 1;
                    startSubCases = false;
                    isAtArdougne = false;
                    isAtCatherby = false;
                    isAtFalador = false;
                    isAtFarmingGuild = false;
                    isAtHarmony = false;
                    isAtKourend = false;
                    isAtMorytania = false;
                    isAtTrollStronghold = false;
                    isAtWeiss = false;
                    herbRunIndex++;
                    farmLimps = false;
                    flowerPatchDone = false;
                }
            }
        }
    }

    public static int subCase = 1;
    public static boolean startSubCases = false;
    public static int herbRunIndex = 0;
    public static void resetHerbRun() {
        herbRunIndex = 0;
        currentTeleportCase = 1;
        subCase = 1;
        startSubCases = false;
        isAtArdougne = false;
        isAtCatherby = false;
        isAtFalador = false;
        isAtFarmingGuild = false;
        isAtHarmony = false;
        isAtKourend = false;
        isAtMorytania = false;
        isAtTrollStronghold = false;
        isAtWeiss = false;
        farmLimps = false;
        flowerPatchDone = false;
        // Reset other variables if necessary
    }

    public void RemoveOverlay(){
        System.out.println("RemoveOverlay");
        plugin.overlayManager.remove(farmingHelperOverlay);
        plugin.overlayManager.remove(farmingTeleportOverlay);
        plugin.overlayManager.remove(farmingHelperOverlayInfoBox);
        plugin.setOverlayActive(false);
        plugin.setTeleportOverlayActive(false);
        herbRunIndex = 0;
        currentTeleportCase = 1;
        subCase = 1;
        startSubCases = false;
        isAtArdougne = false;
        isAtCatherby = false;
        isAtFalador = false;
        isAtFarmingGuild = false;
        isAtHarmony = false;
        isAtKourend = false;
        isAtMorytania = false;
        isAtTrollStronghold = false;
        isAtWeiss = false;
        farmLimps = false;
        flowerPatchDone = false;
    }

    @Override
    public Dimension render(Graphics2D graphics) {
        if (plugin.isTeleportOverlayActive()) {
            Client client = plugin.getClient();
            if (client != null) {
                HerbRunLocation[] herbRunLocations = {
                        new HerbRunLocation(config.ardougneHerb(), this::gettingToArdougne, isAtArdougne),
                        new HerbRunLocation(config.catherbyHerb(), this::gettingToCatherby, isAtCatherby),
                        new HerbRunLocation(config.faladorHerb(), this::gettingToFalador, isAtFalador),
                        new HerbRunLocation(config.farmingGuildHerb(), this::gettingToFarmingGuild, isAtFarmingGuild),
                        new HerbRunLocation(config.harmonyHerb(), this::gettingToHarmony, isAtHarmony),
                        new HerbRunLocation(config.kourendHerb(), this::gettingToKourend, isAtKourend),
                        new HerbRunLocation(config.morytaniaHerb(), this::gettingToMorytania, isAtMorytania),
                        new HerbRunLocation(config.trollStrongholdHerb(), this::gettingToTrollStronghold, isAtTrollStronghold),
                        new HerbRunLocation(config.weissHerb(), this::gettingToWeiss, isAtWeiss)
                };

                while (herbRunIndex < herbRunLocations.length) {
                    HerbRunLocation location = herbRunLocations[herbRunIndex];
                    if (location.isEnabled()) {
                        location.gettingToMethod(graphics);
                        if (location.isAtLocation()) {
                            farming(graphics);
                            //herbRunIndex++; was moved to: "public void farming" to increase after completing the required farming steps
                        }
                        break;
                    } else {
                        herbRunIndex++;
                    }
                }
                if (herbRunIndex >= herbRunLocations.length) {
                    System.out.println("Herb run reset");
                    //resetHerbRun();
                    RemoveOverlay();
                    //plugin.overlayManager.remove(farmingHelperOverlay);
                    //plugin.overlayManager.remove(plugin.getFarmingHelperOverlayInfoBox());
                    // Add any other actions you want to perform when the herb run is complete
                }
            }
        }
        return null;
    }

    private class HerbRunLocation {
        private boolean enabled;
        private Consumer<Graphics2D> gettingToMethod;
        private boolean isAtLocation;

        public HerbRunLocation(boolean enabled, Consumer<Graphics2D> gettingToMethod, boolean isAtLocation) {
            this.enabled = enabled;
            this.gettingToMethod = gettingToMethod;
            this.isAtLocation = isAtLocation;
        }

        public boolean isEnabled() {
            return enabled;
        }

        public void gettingToMethod(Graphics2D graphics) {
            gettingToMethod.accept(graphics);
        }

        public boolean isAtLocation() {
            return isAtLocation;
        }
    }
}
