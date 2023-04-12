//package net.runelite.client.plugins.farminghelper;
package com.farminghelper.speaax;

import net.runelite.api.*;
import com.google.inject.Provides;
import java.awt.image.BufferedImage;
import javax.inject.Inject;

import net.runelite.api.events.AnimationChanged;
import net.runelite.api.events.ChatMessage;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.NavigationButton;
import net.runelite.client.util.ImageUtil;
import net.runelite.client.ui.ClientToolbar;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.api.events.ItemContainerChanged;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.callback.ClientThread;
import java.util.concurrent.CompletableFuture;
import java.util.Map;
import java.util.HashMap;
import com.farminghelper.speaax.FarmingHelperConfig.OptionEnumHouseTele;
//import net.runelite.client.plugins.farminghelper.FarmingHelperConfig.OptionEnumHouseTele;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.client.eventbus.EventBus;
@PluginDescriptor(
		name = "Farming Helper",
		description = "Test run",
		enabledByDefault = false
)


public class FarmingHelperPlugin extends Plugin
{

	public enum Item {
		dustRune(4696),
		lawRune(563),
		airRune(556),
		earthRune(	557),
		constrCape(9789),
		constrCapeT(9790),
		teleportToHouseTab(8013),
		maxCape(13280),;

		private final int value;

		private Item(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}
	private boolean isTeleportOverlayActive = false;
	public boolean isTeleportOverlayActive() {
		return isTeleportOverlayActive;
	}
	public void setTeleportOverlayActive(boolean isTeleportOverlayActive) {
		this.isTeleportOverlayActive = isTeleportOverlayActive;
	}
	@Inject
	private FarmingHelperOverlayInfoBox farmingHelperOverlayInfoBox;
	public FarmingHelperOverlayInfoBox getFarmingHelperOverlayInfoBox()
	{
		return farmingHelperOverlayInfoBox;
	}

	private String lastMessage = "";
	@Subscribe
	public void onChatMessage(ChatMessage event) {
		if (event.getType() == ChatMessageType.GAMEMESSAGE) {
			lastMessage = event.getMessage();
			System.out.println("Last message updated: " + lastMessage);
		}
		if (event.getType() == ChatMessageType.SPAM) {
			lastMessage = event.getMessage();
			System.out.println("Last message updated: " + lastMessage);
		}
	}
	public String getLastMessage() {
		return lastMessage;
	}
	public boolean checkMessage(String targetMessage, String lastMessage) {
		return lastMessage.trim().equalsIgnoreCase(targetMessage.trim());
	}

	@Inject
	private EventBus eventBus;
	@Inject
	private Client client;
	@Inject
	private ClientThread clientThread;


	@Inject
	private FarmingTeleportOverlay farmingTeleportOverlay;
	public FarmingTeleportOverlay getFarmingTeleportOverlay()
	{
		return farmingTeleportOverlay;
	}
	private int lastClickedGroupId;
	private int lastClickedChildId;
	private boolean clicked = false;

	public boolean isClicked(int groupId, int childId) {
		return clicked && groupId == lastClickedGroupId && childId == lastClickedChildId;
	}
	@Subscribe
	public void onMenuOptionClicked(MenuOptionClicked event) {
		clientThread.invokeLater(() -> {
			int groupId = event.getWidgetId() >>> 16;
			int childId = event.getWidgetId() & 0xFFFF;
			clicked = true;
			lastClickedGroupId = groupId;
			lastClickedChildId = childId;
			System.out.printf("Clicked widget: groupId=%d, childId=%d%n", groupId, childId);
		});
	}

	private FarmingHelperPanel farmingHelperPanel;
	private FarmingHelperPanel panel;
	private NavigationButton navButton;

	@Inject
	private ClientToolbar clientToolbar;

	@Inject
	private FarmingHelperConfig config;
	@Inject
	public OverlayManager overlayManager;

	private boolean isOverlayActive = true;
	private Map<Integer, Integer> herbItemsCache;

	public Map<Integer, Integer> getHerbItemsCache()
	{
		return herbItemsCache;
	}
	@Inject
	private FarmingHelperOverlay farmingHelperOverlay;

	public FarmingHelperOverlay getFarmingHelperOverlay()
	{
		return farmingHelperOverlay;
	}

	private boolean itemsCollected = false;
	public boolean areItemsCollected() {
		return itemsCollected;
	}

	public void setItemsCollected(boolean itemsCollected) {
		this.itemsCollected = itemsCollected;
	}
	public Client getClient() {
		return client;
	}

	public int getArdougneDiaryTier() {
		int easy = client.getVarbitValue(Varbits.DIARY_ARDOUGNE_EASY);
		int medium = client.getVarbitValue(Varbits.DIARY_ARDOUGNE_MEDIUM);
		int hard = client.getVarbitValue(Varbits.DIARY_ARDOUGNE_HARD);
		int elite = client.getVarbitValue(Varbits.DIARY_ARDOUGNE_ELITE);

		if (elite == 1) {
			return 4;
		} else if (hard == 1) {
			return 3;
		} else if (medium == 1) {
			return 2;
		} else if (easy == 1) {
			return 1;
		}

		return 0;
	}
	public int getLumbridgeDiaryTier() {
		int easy = client.getVarbitValue(Varbits.DIARY_LUMBRIDGE_EASY);
		int medium = client.getVarbitValue(Varbits.DIARY_LUMBRIDGE_MEDIUM);
		int hard = client.getVarbitValue(Varbits.DIARY_LUMBRIDGE_HARD);
		int elite = client.getVarbitValue(Varbits.DIARY_LUMBRIDGE_ELITE);

		if (elite == 1) {
			return 4;
		} else if (hard == 1) {
			return 3;
		} else if (medium == 1) {
			return 2;
		} else if (easy == 1) {
			return 1;
		}

		return 0;
	}
	//Herb run item list
	@Subscribe
	public void onItemContainerChanged(ItemContainerChanged event)
	{
		if (event.getItemContainer() == client.getItemContainer(InventoryID.INVENTORY))
		{
			// Update the overlay with the new list of items
			getHerbItems().thenAccept(items -> {
				herbItemsCache = items;
				updateOverlay(items);
			});
		}
	}
	private int lastAnimationId = -1;

	@Subscribe
	public void onAnimationChanged(AnimationChanged event)
	{
		if (client.getGameState() != GameState.LOGGED_IN
				|| event.getActor() != client.getLocalPlayer())
		{
			return;
		}

		int currentAnimationId = event.getActor().getAnimation();
		if (currentAnimationId != lastAnimationId)
		{
			System.out.println("Animation ID: " + currentAnimationId);
			lastAnimationId = currentAnimationId;
		}
	}

	public Item fetchValueOfDropdownHouseTele() {
		OptionEnumHouseTele selectedOption = config.enumConfigHouseTele();

		switch (selectedOption) {
			case Law_air_earth_runes:
					return Item.airRune;
				case Law_dust_runes:
					return Item.lawRune;
				case Teleport_To_House:
					return Item.teleportToHouseTab;
				case Construction_cape:
					return Item.constrCape;
				case Construction_cape_t:
					return Item.constrCapeT;
				case Max_cape:
				return Item.maxCape;
				default:
					throw new IllegalStateException("Unexpected value: " + selectedOption);
		}
	}

	private void addTeleportToHouseItems(Map<Integer, Integer> items) {
		int itemValue = fetchValueOfDropdownHouseTele().getValue();
		if (itemValue == Item.lawRune.getValue()) {
			addItemToMap(items, Item.dustRune.getValue(), 1);
			addItemToMap(items, Item.lawRune.getValue(), 1);
		} else if (itemValue == Item.airRune.getValue()) {
			addItemToMap(items, Item.airRune.getValue(), 1);
			addItemToMap(items, Item.earthRune.getValue(), 1);
			addItemToMap(items, Item.lawRune.getValue(), 1);
		} else {
			if (!items.containsKey(itemValue)) {
				addItemToMap(items, itemValue, 1);
			}
		}
	}
	public CompletableFuture<Map<Integer, Integer>> getHerbItems()
	{
		CompletableFuture<Map<Integer, Integer>> futureItems = new CompletableFuture<>();
		Map<Integer, Integer> items = new HashMap<>(Map.of(
				5343, 1,
				7409, 1,
				952, 1,
				22997, 1
		));

		if (config.generalRake())
		{
			addItemToMap(items, 5341, 1);
		}
		if (config.ardougneHerb())
		{
			addItemToMap(items, 5291, 1);
			if(config.generalLimpwurt())
			{
				addItemToMap(items, 5100, 1);
			}
			clientThread.invokeLater(() -> {
				int ardougneDiaryTier = getArdougneDiaryTier();
				if (ardougneDiaryTier > 0)
				{
					int itemId;
					switch (ardougneDiaryTier)
					{
						case 1:
							itemId = 13121;
							break;
						case 2:
							itemId = 13122;
							break;
						case 3:
							itemId = 13123;
							break;
						case 4:
							itemId = 13124;
							break;
						default:
							itemId = 13121;
							break;
					}
					addItemToMap(items, itemId, 1);
				}
				//catherbyHerb
				// Add more conditions for other config items

				futureItems.complete(items);
			});
		}

		else
		{
			futureItems.complete(items);
		}
		if (config.catherbyHerb())
		{
			addItemToMap(items, 5291, 1);
			addTeleportToHouseItems(items);
			if(config.generalLimpwurt())
			{
				addItemToMap(items, 5100, 1);
			}
		}
		if (config.faladorHerb())
		{
			addItemToMap(items, 5291, 1);
			if(config.generalLimpwurt())
			{
				addItemToMap(items, 5100, 1);
			}
			clientThread.invokeLater(() -> {
				int lumbridgeDiaryTier = getArdougneDiaryTier();
				if (lumbridgeDiaryTier > 0)
				{
					int itemId;
					switch (lumbridgeDiaryTier)
					{
						case 1:
							itemId = 13125;
							break;
						case 2:
							itemId = 13126;
							break;
						case 3:
							itemId = 13127;
							break;
						case 4:
							itemId = 13128;
							break;
						default:
							itemId = 13125;
							break;
					}
					addItemToMap(items, itemId, 1);
				}
				futureItems.complete(items);
			});
		}

		else
		{
			futureItems.complete(items);
		}

		if (config.farmingGuildHerb())
		{
			if(config.generalLimpwurt())
			{
				addItemToMap(items, 5100, 1);
			}
			addItemToMap(items, 5291, 1);
			FarmingHelperConfig.OptionEnumFarmingGuildTeleport teleportOption = config.enumOptionEnumFarmingGuildTeleport();
			if(teleportOption == FarmingHelperConfig.OptionEnumFarmingGuildTeleport.Skills_Necklace){
				addItemToMap(items, ItemID.SKILLS_NECKLACE1, 1);
			}
			else{
				addTeleportToHouseItems(items);
			}
		}
		if (config.harmonyHerb())
		{
			addItemToMap(items, 5291, 1);
			addTeleportToHouseItems(items);
		}
		if (config.kourendHerb())
		{
			if(config.generalLimpwurt())
			{
				addItemToMap(items, 5100, 1);
			}
			addItemToMap(items, 5291, 1);
			FarmingHelperConfig.OptionEnumKourendTeleport teleportOption = config.enumOptionEnumKourendTeleport();
			if(teleportOption == FarmingHelperConfig.OptionEnumKourendTeleport.Xerics_Talisman)
			{
				addItemToMap(items, 13393, 1);
			}
			else
			{
				addTeleportToHouseItems(items);
			}
		}
		if (config.morytaniaHerb())
		{
			if(config.generalLimpwurt())
			{
				addItemToMap(items, 5100, 1);
			}
			addItemToMap(items, 5291, 1);
			addItemToMap(items, 4251, 1);
		}
		if (config.trollStrongholdHerb())
		{
			addItemToMap(items, 5291, 1);
			FarmingHelperConfig.OptionEnumTrollStrongholdTeleport teleportOption = config.enumOptionEnumTrollStrongholdTeleport();
			if(teleportOption == FarmingHelperConfig.OptionEnumTrollStrongholdTeleport.Stony_Basalt)
			{
				addItemToMap(items, 22601, 1);
			}
			else{
				addTeleportToHouseItems(items);
			}
		}
		if (config.weissHerb())
		{
			addItemToMap(items, 5291, 1);
			FarmingHelperConfig.OptionEnumWeissTeleport teleportOption = config.enumOptionEnumWeissTeleport();
			if(teleportOption == FarmingHelperConfig.OptionEnumWeissTeleport.Icy_basalt)
			{
				addItemToMap(items, 22599, 1);
			}
			else{
				addTeleportToHouseItems(items);
			}
		}

		return futureItems;
	}
	private void addItemToMap(Map<Integer, Integer> items, int itemId, int count)
	{
		items.put(itemId, items.getOrDefault(itemId, 0) + count);
	}
	//update item list
	public void updateOverlay(Map<Integer, Integer> herbItems)
	{
		//farmingHelperOverlay.updateItems(items);
		this.herbItemsCache = herbItems;
	}

	@Provides
	FarmingHelperConfig getConfig(ConfigManager configManager)
	{
		return configManager.getConfig(FarmingHelperConfig.class);
	}

	public boolean isOverlayActive()
	{
		return isOverlayActive;
	}

	public void setOverlayActive(boolean overlayActive)
	{
		isOverlayActive = overlayActive;
	}
	public void addTextToInfoBox(String text) {
		farmingHelperOverlayInfoBox.setText(text);
	}

	@Override
	protected void startUp()
	{
		//farmingTeleportOverlay.addHerbPatchToList(null);

		panel = new FarmingHelperPanel(this, overlayManager, farmingTeleportOverlay);
		final BufferedImage icon = ImageUtil.loadImageResource(getClass(), "/com/farminghelper/speaax/icon.png");

		navButton = NavigationButton.builder()
				.tooltip("Farming Helper")
				.icon(icon)
				.priority(6)
				.panel(panel)
				.build();
		clientToolbar.addNavigation(navButton);

		overlayManager.add(farmingHelperOverlay);
		overlayManager.add(farmingTeleportOverlay);
		overlayManager.add(farmingHelperOverlayInfoBox);

		farmingHelperOverlay = new FarmingHelperOverlay(client, this);
		//farmingHelperOverlay = new FarmingHelperOverlay(client, this);

		// set overlay to inactive
		isOverlayActive = false;
		eventBus.register(this);
	}

	@Override
	protected void shutDown()
	{
		clientToolbar.removeNavigation(navButton);
		overlayManager.remove(farmingHelperOverlay);
		overlayManager.remove(farmingTeleportOverlay);
		overlayManager.remove(farmingHelperOverlayInfoBox);
		int itemID = 4151; // Replace this with the ID of the item you want to highlight initially.
		eventBus.unregister(this);
	}
}