//package net.runelite.client.plugins.farminghelper;
package com.farminghelper.speaax;

// Mandatory imports
import java.util.List;
import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;

@ConfigGroup("farminghelper")
public interface FarmingHelperConfig extends Config
{
	@ConfigSection(
			name = "General",
			description = "The highlighted and hidden item lists",
			position = 0
	)
	String generalList = "generalList";

	enum OptionEnumHouseTele
	{
		Law_air_earth_runes,
		Law_dust_runes,
		Teleport_To_House,
		Construction_cape,
		Construction_cape_t,
		Max_cape
	}
	@ConfigItem(
			position = 1,
			keyName = "enumConfigHouseTele",
			name = "House teleport",
			description = "Desired way to teleport to house",
			section = teleportOptionList
	)
	default OptionEnumHouseTele enumConfigHouseTele() { return OptionEnumHouseTele.Law_dust_runes; }

	enum OptionEnumCompost
	{
		Compost,
		Supercompost,
		Ultracompost,
		Bottomless
	}
	@ConfigItem(
			position = 23,
			keyName = "enumConfigCompost",
			name = "Compost",
			description = "Desired Compost",
			section = generalList
	)
	default OptionEnumCompost enumConfigCompost() { return OptionEnumCompost.Bottomless; }


	@ConfigItem(
			keyName = "booleanConfigRake",
			name = "Rake",
			description = "Include rake?",
			position = 24,
			section = generalList
	)
	default boolean generalRake() { return false; }
	@ConfigItem(
			keyName = "booleanConfigLimpwurt",
			name = "Limpwurt",
			description = "Want to include limpwurts in your farm run?",
			position = 28,
			section = generalList
	)
	default boolean generalLimpwurt() { return false; }
	@ConfigItem(
			keyName = "booleanConfigAllotment",
			name = "Allotment",
			description = "Want to include Allotment in your farm run?",
			position = 29,
			section = generalList
	)
	default boolean generalAllotment() { return false; }


	@ConfigSection(
		name = "Herbs",
		description = "The highlighted and hidden item lists",
		position = 1
	)
	String herbList = "herbList";

	@ConfigItem(
		keyName = "booleanConfigArdougneHerb",
		name = "Ardougne",
		description = "Include Ardougne?",
		position = 1,
		section = herbList
	)
	default boolean ardougneHerb() { return true; }
	
	@ConfigItem(
		position = 2,
		keyName = "booleanConfigCatherbyHerb",
		name = "Catherby",
		description = "Include Catherby?",
		section = herbList
	)
	default boolean catherbyHerb() { return true; }
	
	@ConfigItem(
		position = 3,
		keyName = "booleanConfigFaladorHerb",
		name = "Falador",
		description = "Include Falador?",
		section = herbList
	)
	default boolean faladorHerb() { return true; }
	
	@ConfigItem(
		position = 4,
		keyName = "booleanConfigFarmingGuildHerb",
		name = "Farming Guild",
		description = "Include Farming guild?(Requires level 65 farming and 60% Hosidious favour)",
		section = herbList
	)
	default boolean farmingGuildHerb() { return false; }
	
	@ConfigItem(
		position = 5,
		keyName = "booleanConfigHarmonyHerb",
		name = "Harmony",
		description = "Include Harmony?(Requires elite Morytania diary)",
		section = herbList
	)
	default boolean harmonyHerb() { return false; }
	
	@ConfigItem(
		position = 6,
		keyName = "booleanConfigKourendHerb",
		name = "Kourend",
		description = "Include Kourend?",
		section = herbList
	)
	default boolean kourendHerb() { return true; }
	
	@ConfigItem(
		position = 7,
		keyName = "booleanConfigMorytaniaHerb",
		name = "Morytania",
		description = "Include Morytania?",
		section = herbList
	)
	default boolean morytaniaHerb() { return true; }
	
	@ConfigItem(
		position = 8,
		keyName = "booleanConfigTrollStrongholdHerb",
		name = "Troll Stronghold",
		description = "Include Troll Stronghold(Requires completion of My Arm's Big Adventure)?",
		section = herbList
	)
	default boolean trollStrongholdHerb() { return false; }
	
	@ConfigItem(
		position = 9,
		keyName = "booleanConfigWeissHerb",
		name = "Weiss",
		description = "Include Weiss?(Requires completion of Making Friends with My Arm, and The Fire of Nourishment must be built)",
		section = herbList
	)
	default boolean weissHerb() { return false; }

	@ConfigSection(
		name = "Tree",
		description = "The highlighted and hidden item lists",
		position = 2
	)
	String treeList = "treeList";
	
	@ConfigItem(
		position = 10,
		keyName = "booleanConfigFaladorTree",
		name = "Falador",
		description = "Include Falador?",
		section = treeList
	)
	default boolean faladorTree() { return true; }
	
	@ConfigItem(
		position = 11,
		keyName = "booleanConfigFarmingGuildTree",
		name = "Farming guild",
		description = "Include Farming Guild?(Requires 65 farming)",
		section = treeList
	)
	default boolean farmingGuildTree() { return false; }
	
	@ConfigItem(
		position = 12,
		keyName = "booleanConfigGnomeStrongholdTree",
		name = "Gnome Stronghold",
		description = "Include Gnome Stronghold?",
		section = treeList
	)
	default boolean gnomeStrongholdTree() { return true; }
	
	@ConfigItem(
		position = 13,
		keyName = "booleanConfigLumbridgeTree",
		name = "Lumbridge",
		description = "Include Lumbridge?",
		section = treeList
	)
	default boolean lumbridgeTree() { return true; }
	
	@ConfigItem(
		position = 14,
		keyName = "booleanConfigTaverleyTree",
		name = "Taverley",
		description = "Include Taverley?",
		section = treeList
	)
	default boolean taverleyTree() { return true; }
	
	@ConfigItem(
		position = 15,
		keyName = "booleanConfigVarrockTree",
		name = "Varrock",
		description = "Include Varrock?",
		section = treeList
	)
	default boolean varrockTree() { return true; }
	
	
	
	
	
	
	@ConfigSection(
		name = "Fruit Tree",
		description = "The highlighted and hidden item lists",
		position = 3
	)
	String fruitTreeList = "fruitTreeList";
	
	@ConfigItem(
		position = 16,
		keyName = "booleanConfigBrimhavenFruitTree",
		name = "Brimhaven",
		description = "Include Brimhaven?",
		section = fruitTreeList
	)
	default boolean brimhavenFruitTree() { return true; }
	
	@ConfigItem(
		position = 17,
		keyName = "booleanConfigCatherbyFruitTree",
		name = "Catherby",
		description = "Include Catherby?",
		section = fruitTreeList
	)
	default boolean catherbyFruitTree() { return true; }
	
	@ConfigItem(
		position = 18,
		keyName = "booleanConfigFarmingGuildFruitTree",
		name = "Farming Guild",
		description = "Include Farming Guild?(Requires 85 farming)",
		section = fruitTreeList
	)
	default boolean farmingGuildFruitTree() { return false; }
	
	@ConfigItem(
		position = 19,
		keyName = "booleanConfigGnomeStrongholdFruitTree",
		name = "Gnome Stronghold",
		description = "Include Gnome Stronghold?",
		section = fruitTreeList
	)
	default boolean gnomeStrongholdFruitTree() { return true; }
	
	@ConfigItem(
		position = 20,
		keyName = "booleanConfigLletyaFruitTree",
		name = "Lletya",
		description = "Include Lletya?(Requires starting Mourning's End Part I)",
		section = fruitTreeList
	)
	default boolean lletyaFruitTree() { return false; }
	
	@ConfigItem(
		position = 21,
		keyName = "booleanConfigTreeGnomeVillageFruitTree",
		name = "Tree Gnome Village",
		description = "Include Tree Gnome Village?",
		section = fruitTreeList
	)
	default boolean treeGnomeVillageFruitTree() { return true; }


	@ConfigSection(
			name = "Teleport options",
			description = "Choose what teleport to use for each Herb patch",
			position = 4
	)
	String teleportOptionList = "teleportOptionList";

	enum OptionEnumArdougneTeleport
	{
		Ardy_cloak
	}
	@ConfigItem(
			position = 2,
			keyName = "enumOptionEnumArdougneTeleport",
			name = "Ardougne",
			description = "Desired way to teleport to Ardougne",
			section = teleportOptionList
	)
	default OptionEnumArdougneTeleport enumOptionEnumArdougneTeleport() { return OptionEnumArdougneTeleport.Ardy_cloak; }
	enum OptionEnumCatherbyTeleport
	{
		Portal_Nexus
	}
	@ConfigItem(
			position = 3,
			keyName = "enumOptionEnumCatherbyTeleport",
			name = "Catherby",
			description = "Desired way to teleport to Catherby",
			section = teleportOptionList
	)
	default OptionEnumCatherbyTeleport enumOptionEnumCatherbyTeleport() { return OptionEnumCatherbyTeleport.Portal_Nexus; }

	enum OptionEnumFaladorTeleport
	{
		Explorers_ring
	}
	@ConfigItem(
			position = 4,
			keyName = "enumOptionEnumFaladorTeleport",
			name = "Falador",
			description = "Desired way to teleport to Falador",
			section = teleportOptionList
	)
	default OptionEnumFaladorTeleport enumOptionEnumFaladorTeleport() { return OptionEnumFaladorTeleport.Explorers_ring; }

	enum OptionEnumFarmingGuildTeleport
	{
		Jewellery_box,
		Skills_Necklace
	}
	@ConfigItem(
			position = 5,
			keyName = "enumOptionEnumFarmingGuildTeleport",
			name = "Farming Guild",
			description = "Desired way to teleport to Farming Guild",
			section = teleportOptionList
	)
	default OptionEnumFarmingGuildTeleport enumOptionEnumFarmingGuildTeleport() { return OptionEnumFarmingGuildTeleport.Skills_Necklace; }

	enum OptionEnumHarmonyTeleport
	{
		Portal_Nexus,
	}
	@ConfigItem(
			position = 6,
			keyName = "enumOptionEnumHarmonyTeleport",
			name = "Harmony",
			description = "Desired way to teleport to Harmony",
			section = teleportOptionList
	)
	default OptionEnumHarmonyTeleport enumOptionEnumHarmonyTeleport() { return OptionEnumHarmonyTeleport.Portal_Nexus; }

	enum OptionEnumKourendTeleport
	{
		Xerics_Talisman,
		Mounted_Xerics
	}
	@ConfigItem(
			position = 7,
			keyName = "enumOptionEnumKourendTeleport",
			name = "Kourend",
			description = "Desired way to teleport to Kourend",
			section = teleportOptionList
	)
	default OptionEnumKourendTeleport enumOptionEnumKourendTeleport() { return OptionEnumKourendTeleport.Xerics_Talisman; }

	enum OptionEnumMorytaniaTeleport
	{
		Ectophial
	}
	@ConfigItem(
			position = 8,
			keyName = "enumOptionEnumMorytaniaTeleport",
			name = "Morytania",
			description = "Desired way to teleport to Morytania",
			section = teleportOptionList
	)
	default OptionEnumMorytaniaTeleport enumOptionEnumMorytaniaTeleport() { return OptionEnumMorytaniaTeleport.Ectophial; }

	enum OptionEnumTrollStrongholdTeleport
	{
		Portal_Nexus,
		Stony_Basalt
	}
	@ConfigItem(
			position = 9,
			keyName = "enumOptionEnumTrollStrongholdTeleport",
			name = "Troll Stronghold",
			description = "Desired way to teleport to Troll Stronghold",
			section = teleportOptionList
	)
	default OptionEnumTrollStrongholdTeleport enumOptionEnumTrollStrongholdTeleport() { return OptionEnumTrollStrongholdTeleport.Portal_Nexus; }

	enum OptionEnumWeissTeleport
	{
		Portal_Nexus,
		Icy_basalt
	}
	@ConfigItem(
			position = 10,
			keyName = "enumOptionEnumWeissTeleport",
			name = "Weiss",
			description = "Desired way to teleport to Weiss",
			section = teleportOptionList
	)
	default OptionEnumWeissTeleport enumOptionEnumWeissTeleport() { return OptionEnumWeissTeleport.Portal_Nexus; }


}