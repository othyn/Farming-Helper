//package net.runelite.client.plugins.farminghelper;
package com.farminghelper.speaax;

import net.runelite.client.ui.PluginPanel;
import net.runelite.client.ui.overlay.OverlayManager;

import javax.swing.JButton;
import javax.swing.SwingUtilities;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import com.farminghelper.speaax.FarmingHelperOverlay;

public class FarmingHelperPanel extends PluginPanel
{
	private final FarmingHelperPlugin plugin;
    private final OverlayManager overlayManager;
    private final FarmingTeleportOverlay farmingTeleportOverlay;
    private JButton herbButton;
	private JButton treeButton;
	private JButton fruitTreeButton;

    public FarmingHelperPanel(FarmingHelperPlugin plugin, OverlayManager overlayManager, FarmingTeleportOverlay farmingTeleportOverlay)
    {
        this.farmingTeleportOverlay = farmingTeleportOverlay;
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
        herbButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                plugin.getHerbItems().thenAccept(herbItems -> {
                    SwingUtilities.invokeLater(() -> {
                        System.out.println(herbItems);
                    });
                    plugin.updateOverlay(herbItems);
                    plugin.setOverlayActive(!plugin.isOverlayActive());
                    onHerbButtonClicked();
                });
            }
        });


        add(herbButton, c);

		c.gridx = 0;
		c.gridy = 1;

		treeButton = new JButton("Tree run");
        treeButton.setFocusable(false);
        treeButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                onTreeButtonClicked();
            }
        });
		add(treeButton, c);

		c.gridx = 0;
		c.gridy = 2;

		
		fruitTreeButton = new JButton("Fruit Tree run");
        fruitTreeButton.setFocusable(false);
        fruitTreeButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                onFruitTreeButtonClicked();
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
                    plugin.setItemsCollected(false); // Make sure you call the method on the plugin object
                } else {
                    //farmingTeleportOverlay.RemoveOverlay();
                    System.out.println("Add overlay from button");
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
            public void run()
            {
                System.out.println("tree button clicked.");
            }
        });
    }
	private void onFruitTreeButtonClicked()
    {
        // Handle button click event here
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                System.out.println("fruit tree button clicked.");
            }
        });
    }
}