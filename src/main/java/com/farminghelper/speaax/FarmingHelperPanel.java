package com.farminghelper.speaax;

import net.runelite.client.ui.PluginPanel;
import net.runelite.client.ui.overlay.OverlayManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
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

    public StartStopJButton herbButton;
    public StartStopJButton treeButton;
    public StartStopJButton fruitTreeButton;

    public FarmingHelperPanel(FarmingHelperPlugin plugin, OverlayManager overlayManager, FarmingTeleportOverlay farmingTeleportOverlay, HerbRunItemAndLocation herbRunItemAndLocation, TreeRunItemAndLocation treeRunItemAndLocation, FruitTreeRunItemAndLocation fruitTreeRunItemAndLocation)
    {
        this.herbRunItemAndLocation = herbRunItemAndLocation;
        this.treeRunItemAndLocation = treeRunItemAndLocation;
        this.farmingTeleportOverlay = farmingTeleportOverlay;
        this.fruitTreeRunItemAndLocation = fruitTreeRunItemAndLocation;

        this.plugin = plugin;
        this.overlayManager = overlayManager;

        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel layoutPanel = new JPanel();
        layoutPanel.setLayout(new BoxLayout(layoutPanel, BoxLayout.Y_AXIS));

        JPanel titlePanel = createTitlePanel();
        JPanel farmRunButtons = createFarmRunButtons();
        JPanel infoPanel = createInfoPanel();

        layoutPanel.add(titlePanel);
        layoutPanel.add(farmRunButtons);
        layoutPanel.add(infoPanel);

        add(layoutPanel, BorderLayout.NORTH);
    }

    private JPanel createTitlePanel()
    {
        JPanel titlePanel = new JPanel();
        titlePanel.setBorder(new EmptyBorder(0, 0, 15, 0));
        titlePanel.setLayout(new BorderLayout());

        JLabel title = new JLabel("Pick a new farm run:");
        titlePanel.add(title, BorderLayout.WEST);

        return titlePanel;
    }

    private JPanel createFarmRunButtons()
    {
        JPanel farmRunButtonsContainingPanel = new JPanel();
        farmRunButtonsContainingPanel.setLayout(new BoxLayout(farmRunButtonsContainingPanel, BoxLayout.Y_AXIS));

        // With GridLayout, you can't set the button height.
        // With GridBagLayout, you can't make the buttons the full width of the container.
        // The height seemed like the better thing to let go of.
//        JPanel farmRunButtonsPanel = new JPanel(new GridBagLayout());
        JPanel farmRunButtonsPanel = new JPanel(new GridLayout(0, 1, 0, 15));
        farmRunButtonsPanel.setBorder(new EmptyBorder(0, 0, 0, 0));

//        GridBagConstraints constraints = new GridBagConstraints();
//        constraints.insets = new Insets(5, 0, 5, 0);
//        constraints.fill = GridBagConstraints.HORIZONTAL;
//        constraints.gridwidth = 1;
//        constraints.ipady = 10;

        herbButton = new StartStopJButton("Herb Run");
		herbButton.setFocusable(false);
        herbButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                plugin.runOnClientThread(() -> {
                    Map<Integer, Map<Integer, Integer>> herbItems = herbRunItemAndLocation.getHerbItems();
                    plugin.updateHerbOverlay(herbItems);
                    plugin.setOverlayActive(!plugin.isOverlayActive());

                    herbButton.setStartStopState(plugin.isOverlayActive());

                    onHerbButtonClicked();
                });
            }
        });
//        constraints.gridy = 0;
//        farmRunButtonsPanel.add(herbButton, constraints);
        farmRunButtonsPanel.add(herbButton);

        treeButton = new StartStopJButton("Tree Run");
        treeButton.setFocusable(false);
        treeButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                plugin.runOnClientThread(() -> {
                    Map<Integer, Map<Integer, Integer>> treeItems = treeRunItemAndLocation.getTreeItems();
                    plugin.updateTreeOverlay(treeItems);
                    plugin.setOverlayActive(!plugin.isOverlayActive());

                    treeButton.setStartStopState(plugin.isOverlayActive());

                    onTreeButtonClicked();
                });
            }
        });
//        constraints.gridy = 1;
//        farmRunButtonsPanel.add(treeButton, constraints);
        farmRunButtonsPanel.add(treeButton);

        fruitTreeButton = new StartStopJButton("Fruit Tree Run");
        fruitTreeButton.setFocusable(false);
        fruitTreeButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                plugin.runOnClientThread(() -> {
                    Map<Integer, Map<Integer, Integer>> fruitTreeItems = fruitTreeRunItemAndLocation.getFruitTreeItems();
                    plugin.updateFruitTreeOverlay(fruitTreeItems);
                    plugin.setOverlayActive(!plugin.isOverlayActive());

                    fruitTreeButton.setStartStopState(plugin.isOverlayActive());

                    onFruitTreeButtonClicked();
                });
            }
        });
//        constraints.gridy = 2;
//        farmRunButtonsPanel.add(fruitTreeButton, constraints);
        farmRunButtonsPanel.add(fruitTreeButton);

        farmRunButtonsContainingPanel.add(farmRunButtonsPanel);

        return farmRunButtonsContainingPanel;
    }

    private JPanel createInfoPanel()
    {
        JPanel infoContainingPanel = new JPanel();
        infoContainingPanel.setLayout(new BoxLayout(infoContainingPanel, BoxLayout.Y_AXIS));
        
        JPanel infoPanel = new JPanel(new GridLayout(0, 1, 0, 0));
        infoPanel.setBorder(new EmptyBorder(25, 0, 0, 0));

        JTextArea textAreaTip = new JTextArea("Tips: \n - Rune pouch and combination runes work. \n - If you don't have Bottomless compost bucket you should store compost @ Tool Leprechaun, the plugin checks if you have compost stored there.");
        textAreaTip.setWrapStyleWord(true);
        textAreaTip.setLineWrap(true);
        textAreaTip.setEditable(false);
        infoPanel.add(textAreaTip);

        infoContainingPanel.add(infoPanel);

        return infoContainingPanel;
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