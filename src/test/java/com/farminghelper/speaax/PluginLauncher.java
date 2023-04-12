package com.farminghelper.speaax;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class PluginLauncher
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(FarmingHelperPlugin.class);
		RuneLite.main(args);
	}
}