package com.boydti.island;

import com.github.hoqhuuep.islandcraft.api.ICLocation;
import com.github.hoqhuuep.islandcraft.api.ICRegion;
import com.github.hoqhuuep.islandcraft.api.IslandDistribution;
import com.github.hoqhuuep.islandcraft.core.ICLogger;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.lang.StringUtils;

public class PlotDistribution implements IslandDistribution {
	private int islandSize;
	private int oceanSize;
	private int islandSeparation;
	private int innerRadius;
	private int outerRadius;

	public PlotDistribution(String[] args) {
		try {
			ICLogger.logger.info("Creating PlotDistribution with args: " + StringUtils.join(args, " "));
			if (args.length != 2) {
				System.err.println("PlotDistribution requrires 2 parameters, " + args.length + " given");
				throw new IllegalArgumentException("PlotDistribution requrires 2 parameters");
			}
			this.islandSize = Integer.parseInt(args[0]);
			this.oceanSize = Integer.parseInt(args[1]);
			if ((this.islandSize <= 0) || (this.islandSize % 32 != 0)) {
				System.err.println("PlotDistribution.island-size must be a positive multiple of 32");
				throw new IllegalArgumentException("PlotDistribution.island-size must be a positive multiple of 32");
			}
			if ((this.oceanSize <= 0) || (this.oceanSize % 32 != 0)) {
				System.err.println("PlotDistribution.ocean-size must be a positive multiple of 32");
				throw new IllegalArgumentException("PlotDistribution.ocean-size must be a positive multiple of 32");
			}
			this.islandSeparation = (this.islandSize + this.oceanSize);
			this.innerRadius = (this.islandSize / 2);
			this.outerRadius = (this.innerRadius + this.oceanSize);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public ICLocation getCenterAt(int x, int z, long worldSeed) {
		int zPrime = z + this.innerRadius;
		int zRelative = ifloormod(zPrime, this.islandSeparation);
		if (zRelative >= this.islandSize) {
			return null;
		}
		int row = ifloordiv(zPrime, this.islandSeparation);
		int xPrime = x + this.innerRadius;
		int xRelative = ifloormod(xPrime, this.islandSeparation);
		if (xRelative >= this.islandSize) {
			return null;
		}
		int column = ifloordiv(xPrime, this.islandSeparation);
		return getCenter(row, column);
	}

	public Set<ICLocation> getCentersAt(int x, int z, long worldSeed) {
		int xPrime = x + this.outerRadius;
		int zPrime = z + this.outerRadius;

		int absoluteHashX = ifloordiv(xPrime, this.islandSeparation) * this.islandSeparation;
		int absoluteHashZ = ifloordiv(zPrime, this.islandSeparation) * this.islandSeparation;

		int relativeX = xPrime - absoluteHashX;
		int relativeZ = zPrime - absoluteHashZ;
		Set<ICLocation> result = new HashSet<ICLocation>(3);
		if (relativeZ < this.oceanSize) {
			int centerZ = absoluteHashZ - this.islandSeparation;
			if (relativeX < this.oceanSize) {
				int centerX = absoluteHashX - this.islandSeparation;
				result.add(getCenter(centerX, centerZ));
				result.add(getCenter(centerX, absoluteHashZ));
			}
			result.add(getCenter(absoluteHashX, centerZ));
		} else if (relativeX < this.oceanSize) {
			int centerX = absoluteHashX - this.islandSeparation;
			result.add(getCenter(centerX, absoluteHashZ));
		}
		result.add(getCenter(absoluteHashX, absoluteHashZ));
		return result;
	}

	public ICRegion getInnerRegion(ICLocation center, long worldSeed) {
		int centerX = center.getX();
		int centerZ = center.getZ();
		if (!isCenter(centerX, centerZ)) {
			return null;
		}
		return new ICRegion(new ICLocation(centerX - this.innerRadius, centerZ - this.innerRadius),
				new ICLocation(centerX + this.innerRadius, centerZ + this.innerRadius));
	}

	public ICRegion getOuterRegion(ICLocation center, long worldSeed) {
		int centerX = center.getX();
		int centerZ = center.getZ();
		if (!isCenter(centerX, centerZ)) {
			return null;
		}
		return new ICRegion(new ICLocation(centerX - this.outerRadius, centerZ - this.outerRadius),
				new ICLocation(centerX + this.outerRadius, centerZ + this.outerRadius));
	}

	private ICLocation getCenter(int row, int column) {
		int centerZ = row * this.islandSeparation;
		int centerX = column * this.islandSeparation;
		return new ICLocation(centerX, centerZ);
	}

	private boolean isCenter(int centerX, int centerZ) {
		return (ifloormod(centerZ, this.islandSeparation) == 0) && (ifloormod(centerX, this.islandSeparation) == 0);
	}

	private static int ifloordiv(int n, int d) {
		if (d >= 0) {
			return n >= 0 ? n / d : (n ^ 0xFFFFFFFF) / d ^ 0xFFFFFFFF;
		}
		return n <= 0 ? n / d : (n - 1) / d - 1;
	}

	private static int ifloormod(int n, int d) {
		if (d >= 0) {
			return n >= 0 ? n % d : d + ((n ^ 0xFFFFFFFF) % d ^ 0xFFFFFFFF);
		}
		return n <= 0 ? n % d : d + 1 + (n - 1) % d;
	}
}
