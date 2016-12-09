package com.boydti.island;

import com.intellectualcrafters.plot.generator.ClassicPlotManager;
import com.intellectualcrafters.plot.generator.IndependentPlotGenerator;
import com.intellectualcrafters.plot.object.PlotArea;
import com.intellectualcrafters.plot.object.PlotId;
import com.intellectualcrafters.plot.object.PlotManager;
import com.intellectualcrafters.plot.object.PseudoRandom;
import com.intellectualcrafters.plot.object.SetupObject;
import com.intellectualcrafters.plot.util.block.ScopedLocalBlockQueue;

public class IslandPlotGenerator extends IndependentPlotGenerator {
	public String getName() {
		return "IslandPlots";
	}

	public PlotArea getNewPlotArea(String world, String id, PlotId min, PlotId max) {
		return new IslandPlotWorld(world, id, this, max, max);
	}

	public PlotManager getNewPlotManager() {
		return new ClassicPlotManager();
	}

	public void initialize(PlotArea arg0) {
	}

	public void processSetup(SetupObject object) {
		object.setupManager = new IslandSetupManager();
		object.type = 1;
		object.terrain = 3;
	}

	public void generateChunk(ScopedLocalBlockQueue arg0, PlotArea arg1, PseudoRandom arg2) {
	}
}
