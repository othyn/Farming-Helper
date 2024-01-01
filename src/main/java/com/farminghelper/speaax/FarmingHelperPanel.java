package com.farminghelper.speaax;

import com.farminghelper.speaax.Patch.Location;
import net.runelite.client.ui.PluginPanel;
import net.runelite.client.ui.overlay.OverlayManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

import com.farminghelper.speaax.Patch.PatchType;
import com.farminghelper.speaax.UI.StartStopJButton;
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

    private static final PatchType[] RUN_SELECTOR_OPTIONS = PatchType.values();

    public boolean runLocationsPanelCheckboxDefaultState = true;

    public Map<PatchType, Map<Location, Boolean>> runLocationsPanelCheckboxStates = new HashMap<>();

    private JComboBox<PatchType> runSelector;
    private JPanel runLocationsPanel;
    public StartStopJButton runStartStopButton;

    public FarmingHelperPanel(FarmingHelperPlugin plugin, OverlayManager overlayManager, FarmingTeleportOverlay farmingTeleportOverlay, HerbRunItemAndLocation herbRunItemAndLocation, TreeRunItemAndLocation treeRunItemAndLocation, FruitTreeRunItemAndLocation fruitTreeRunItemAndLocation)
    {
        this.herbRunItemAndLocation = herbRunItemAndLocation;
        this.treeRunItemAndLocation = treeRunItemAndLocation;
        this.fruitTreeRunItemAndLocation = fruitTreeRunItemAndLocation;

        this.farmingTeleportOverlay = farmingTeleportOverlay;

        this.plugin = plugin;
        this.overlayManager = overlayManager;

		setBorder(new EmptyBorder(10, 10, 10, 10));
		setLayout(new GridBagLayout());

        JPanel titlePanel = createTitlePanel();

        runSelector = new JComboBox<>(RUN_SELECTOR_OPTIONS);
        runSelector.setFocusable(false);

        runLocationsPanel = new JPanel();

        runStartStopButton = new StartStopJButton("N/A");
        runStartStopButton.setFocusable(false);

        runSelector.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateComponents();
            }
        });

		final GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		c.gridx = 0;
		c.gridy = 0;

        add(titlePanel, c);
        c.gridy++;
		add(runSelector, c);
		c.gridy++;
		add(runLocationsPanel, c);
		c.gridy++;
		add(runStartStopButton, c);

        updateComponents();
    }

    private void updateComponents()
    {
        runLocationsPanel.removeAll();
        runStartStopButton.removeAllActionListeners();

        PatchType selectedOption = (PatchType) runSelector.getSelectedItem();
        JPanel selectedOptionCheckboxes;

        switch (selectedOption) {
            case HERB:
                this.herbRunItemAndLocation.setupLocations();
                selectedOptionCheckboxes = getCheckboxesForLocations(selectedOption, this.herbRunItemAndLocation.locations);

                runStartStopButton.changeText("Herb Run");
                runStartStopButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        plugin.runOnClientThread(() -> {
                            Map<Integer, Integer> herbItems = herbRunItemAndLocation.getHerbItems();
                            plugin.updateHerbOverlay(herbItems);
                            plugin.setOverlayActive(!plugin.isOverlayActive());

                            runStartStopButton.setStartStopState(plugin.isOverlayActive());

                            onHerbButtonClicked();
                        });
                    }
                });

                break;

            case TREE:
                this.treeRunItemAndLocation.setupLocations();
                selectedOptionCheckboxes = getCheckboxesForLocations(selectedOption, this.treeRunItemAndLocation.locations);

                runStartStopButton.changeText("Tree Run");
                runStartStopButton.addActionListener(new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        plugin.runOnClientThread(() -> {
                            Map<Integer, Integer> treeItems = treeRunItemAndLocation.getTreeItems();
                            plugin.updateTreeOverlay(treeItems);
                            plugin.setOverlayActive(!plugin.isOverlayActive());

                            runStartStopButton.setStartStopState(plugin.isOverlayActive());

                            onTreeButtonClicked();
                        });
                    }
                });

                break;

            case FRUIT_TREE:
                this.fruitTreeRunItemAndLocation.setupLocations();
                selectedOptionCheckboxes = getCheckboxesForLocations(selectedOption, this.fruitTreeRunItemAndLocation.locations);

                runStartStopButton.changeText("Fruit Tree Run");
                runStartStopButton.addActionListener(new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        plugin.runOnClientThread(() -> {
                            Map<Integer, Integer> fruitTreeItems = fruitTreeRunItemAndLocation.getFruitTreeItems();
                            plugin.updateFruitTreeOverlay(fruitTreeItems);
                            plugin.setOverlayActive(!plugin.isOverlayActive());

                            runStartStopButton.setStartStopState(plugin.isOverlayActive());

                            onFruitTreeButtonClicked();
                        });
                    }
                });

                break;

            default:
                selectedOptionCheckboxes = new JPanel();
        }

        runLocationsPanel.add(selectedOptionCheckboxes);

        runLocationsPanel.setLayout(new BoxLayout(runLocationsPanel, BoxLayout.X_AXIS));
        runLocationsPanel.revalidate();
        runLocationsPanel.repaint();
    }

    private JPanel getCheckboxesForLocations(PatchType selectedOption, List<Location> locations)
    {
        JPanel checkboxes = new JPanel();

        checkboxes.setLayout(new BoxLayout(checkboxes, BoxLayout.Y_AXIS));
        checkboxes.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Ensure it's populated on the first run, otherwise the UI is not sync'd with the enabled locations check!
        if (!this.runLocationsPanelCheckboxStates.containsKey(selectedOption)) {
            this.runLocationsPanelCheckboxStates.put(selectedOption, new HashMap<>());
        }

        // This has to be after the key check, otherwise the object loaded into memory is stale, duh!
        Map<Location, Boolean> locationCheckboxStates = this.runLocationsPanelCheckboxStates.getOrDefault(selectedOption, new HashMap<>());

        for (Location location : locations) {
            JCheckBox locationCheckbox = new JCheckBox(location.getName());
            locationCheckbox.setFocusable(false);

            boolean isChecked = locationCheckboxStates.getOrDefault(location, this.runLocationsPanelCheckboxDefaultState);

            // Ensure it's populated on the first run, otherwise the UI is not sync'd with the enabled locations check!
            if (!locationCheckboxStates.containsKey(location)) {
                locationCheckboxStates.put(location, isChecked);
            }

            locationCheckbox.setSelected(isChecked);

            locationCheckbox.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    locationCheckboxStates.put(location, locationCheckbox.isSelected());
                }
            });

            checkboxes.add(locationCheckbox);
        }

        return checkboxes;
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

    private void onHerbButtonClicked()
    {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                FarmingHelperOverlay overlay = plugin.getFarmingHelperOverlay();

                runSelector.setEnabled(!plugin.isOverlayActive());

                if (!plugin.isOverlayActive()) {
                    farmingTeleportOverlay.RemoveOverlay();
                } else {
                    plugin.getFarmingTeleportOverlay().runPatchType = PatchType.HERB;
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

                runSelector.setEnabled(!plugin.isOverlayActive());

                if (!plugin.isOverlayActive()) {
                    farmingTeleportOverlay.RemoveOverlay();
                } else {
                    plugin.getFarmingTeleportOverlay().runPatchType = PatchType.TREE;
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

                runSelector.setEnabled(!plugin.isOverlayActive());

                if (!plugin.isOverlayActive()) {
                    farmingTeleportOverlay.RemoveOverlay();
                } else {
                    plugin.getFarmingTeleportOverlay().runPatchType = PatchType.FRUIT_TREE;
                    overlayManager.add(overlay);
                    overlayManager.add(farmingTeleportOverlay);
                }
            }
        });
    }
}