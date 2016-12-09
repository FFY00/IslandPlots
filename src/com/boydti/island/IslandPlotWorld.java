package com.boydti.island;

import com.intellectualcrafters.configuration.ConfigurationSection;
import com.intellectualcrafters.plot.config.Configuration;
import com.intellectualcrafters.plot.config.ConfigurationNode;
import com.intellectualcrafters.plot.generator.ClassicPlotWorld;
import com.intellectualcrafters.plot.generator.IndependentPlotGenerator;
import com.intellectualcrafters.plot.object.PlotId;

public class IslandPlotWorld extends ClassicPlotWorld {
	public IslandPlotWorld(String worldname, String id, IndependentPlotGenerator generator, PlotId min, PlotId max) {
		super(worldname, id, generator, min, max);
	}

	public void loadConfiguration(ConfigurationSection config) {
		super.loadConfiguration(config);
		this.ROAD_OFFSET_X = (this.SIZE / 2);
		this.ROAD_OFFSET_Z = (this.SIZE / 2);
	}

	public ConfigurationNode[] getSettingNodes() {
		return new ConfigurationNode[] {
				new ConfigurationNode("island-size", Integer.valueOf(4), "Island Size", Configuration.INTEGER),
				new ConfigurationNode("ocean-size", Integer.valueOf(1), "Ocean Size", Configuration.INTEGER) };
	}
}
