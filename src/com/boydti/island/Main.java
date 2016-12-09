package com.boydti.island;

import com.github.hoqhuuep.islandcraft.api.IslandCraft;
import com.github.hoqhuuep.islandcraft.bukkit.IslandCraftPlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	public static IslandCraft islandCraft;
	public static FileConfiguration islandConfig;
	public static IslandCraftPlugin islandCraftPlugin;

	public void onEnable() {
		islandCraftPlugin = (IslandCraftPlugin) getPlugin(IslandCraftPlugin.class);
		islandCraft = islandCraftPlugin.getIslandCraft();
		islandConfig = islandCraftPlugin.getConfig();
	}

	public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
		return (ChunkGenerator) new IslandPlotGenerator().specify(worldName);
	}
}