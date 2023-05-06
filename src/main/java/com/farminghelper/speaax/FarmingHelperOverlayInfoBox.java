package com.farminghelper.speaax;

import net.runelite.api.Client;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.components.LineComponent;
import net.runelite.client.ui.overlay.components.PanelComponent;

import javax.inject.Inject;
import java.awt.Dimension;
import java.awt.Graphics2D;

public class FarmingHelperOverlayInfoBox extends Overlay {
    private final Client client;
    private final PanelComponent panelComponent = new PanelComponent();
    private final FarmingHelperPlugin plugin;

    private String text;

    @Inject
    public FarmingHelperOverlayInfoBox(Client client, FarmingHelperPlugin plugin) {
        this.client = client;
        this.plugin = plugin;
        setPosition(OverlayPosition.TOP_LEFT);
        setLayer(OverlayLayer.ALWAYS_ON_TOP);
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public Dimension render(Graphics2D graphics) {
        if (!plugin.isOverlayActive()) {
            return null;
        }

        panelComponent.getChildren().clear();

        if (text != null) {
            panelComponent.getChildren().add(LineComponent.builder().left(text).build());
        }

        return panelComponent.render(graphics);
    }
}