package com.farminghelper.speaax;

import net.runelite.client.ui.PluginPanel;
import net.runelite.client.ui.overlay.OverlayManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import com.farminghelper.speaax.ItemsAndLocations.HerbRunItemAndLocation;
import com.farminghelper.speaax.ItemsAndLocations.TreeRunItemAndLocation;
import com.farminghelper.speaax.ItemsAndLocations.FruitTreeRunItemAndLocation;

public class FarmingHelperPanel extends PluginPanel
{
    private final HerbRunItemAndLocation herbRunItemAndLocation;
    private final TreeRunItemAndLocation treeRunItemAndLocation;
    private  final FruitTreeRunItemAndLocation fruitTreeRunItemAndLocation;
	private final FarmingHelperPlugin plugin;
    private final OverlayManager overlayManager;
    private final FarmingTeleportOverlay farmingTeleportOverlay;
    private JButton herbButton;
	private JButton treeButton;
	private JButton fruitTreeButton;
    private JLabel textLabel;

    public FarmingHelperPanel(FarmingHelperPlugin plugin, OverlayManager overlayManager, FarmingTeleportOverlay farmingTeleportOverlay, HerbRunItemAndLocation herbRunItemAndLocation, TreeRunItemAndLocation treeRunItemAndLocation, FruitTreeRunItemAndLocation fruitTreeRunItemAndLocation)
    {
        this.herbRunItemAndLocation = herbRunItemAndLocation;
        this.treeRunItemAndLocation = treeRunItemAndLocation;
        this.farmingTeleportOverlay = farmingTeleportOverlay;
        this.fruitTreeRunItemAndLocation = fruitTreeRunItemAndLocation;
        this.plugin = plugin;
        this.overlayManager = overlayManager;
        setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0, 2, 4, 2);

        c.gridx = 0;
        c.gridy = 0;

        herbButton = new JButton("Herb run");
		herbButton.setFocusable(false);
        herbButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                plugin.runOnClientThread(() -> {
                    Map<Integer, Integer> herbItems = herbRunItemAndLocation.getHerbItems();
                    plugin.updateHerbOverlay(herbItems);
                    plugin.setOverlayActive(!plugin.isOverlayActive());
                    onHerbButtonClicked();
                });
            }
        });

        add(herbButton, c);

        JLabel textLabel = new JLabel("Tree/Fruit Tree run is not recommended.");

        c.gridx = 0;
        c.gridy = 1;

        add(textLabel, c);


		c.gridx = 0;
		c.gridy = 2;


		treeButton = new JButton("Tree run");
        treeButton.setFocusable(false);
        treeButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                plugin.runOnClientThread(() -> {
                    Map<Integer, Integer> treeItems = treeRunItemAndLocation.getTreeItems();
                    plugin.updateTreeOverlay(treeItems);
                    plugin.setOverlayActive(!plugin.isOverlayActive());
                    onTreeButtonClicked();
                });
            }
        });

		add(treeButton, c);

		c.gridx = 0;
		c.gridy = 3;

		
		fruitTreeButton = new JButton("Fruit Tree run");
        fruitTreeButton.setFocusable(false);
        fruitTreeButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                plugin.runOnClientThread(() -> {
                    Map<Integer, Integer> fruitTreeItems = fruitTreeRunItemAndLocation.getFruitTreeItems();
                    plugin.updateFruitTreeOverlay(fruitTreeItems);
                    plugin.setOverlayActive(!plugin.isOverlayActive());
                    onFruitTreeButtonClicked();
                });
            }
        });

        add(fruitTreeButton, c);
    }

    private void onHerbButtonClicked() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                FarmingHelperOverlay overlay = plugin.getFarmingHelperOverlay();

                if (!plugin.isOverlayActive()) {
                    farmingTeleportOverlay.RemoveOverlay();
                    System.out.println("Remove overlay from button");
                } else {
                    System.out.println("Add overlay from button");
                    plugin.getFarmingTeleportOverlay().herbRun = true;
                    overlayManager.add(overlay);
                    overlayManager.add(farmingTeleportOverlay);
                }
            }
        });
    }

    private void onTreeButtonClicked()
    {
        // Handle button click event here
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run() {
                FarmingHelperOverlay overlay = plugin.getFarmingHelperOverlay();

                if (!plugin.isOverlayActive()) {
                    farmingTeleportOverlay.RemoveOverlay();
                    System.out.println("Remove overlay from button");
                } else {
                    System.out.println("Add overlay from button");
                    plugin.getFarmingTeleportOverlay().treeRun = true;
                    overlayManager.add(overlay);
                    overlayManager.add(farmingTeleportOverlay);
                }
            }
        });
    }
	private void onFruitTreeButtonClicked()
    {
        // Handle button click event here
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run() {
                FarmingHelperOverlay overlay = plugin.getFarmingHelperOverlay();

                if (!plugin.isOverlayActive()) {
                    farmingTeleportOverlay.RemoveOverlay();
                    System.out.println("Remove overlay from button");
                } else {
                    System.out.println("Add overlay from button");
                    plugin.getFarmingTeleportOverlay().fruitTreeRun = true;
                    overlayManager.add(overlay);
                    overlayManager.add(farmingTeleportOverlay);
                }
            }
        });
    }
}