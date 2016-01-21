package multiagent;

import java.io.File;
import java.io.IOException;

import multiagent.player.DistributedQ;
import multiagent.player.FMQ;
import multiagent.player.Hysteretic;
import multiagent.player.Lenience;
import multiagent.player.RegularQ;
import multiagent.player.Wolf;
import sim.engine.SimState;
import sun.nio.cs.ext.TIS_620;
import ec.util.ParameterDatabase;
import edu.gmu.cs.multiagent.matrix.Game;

public abstract class Player {

	protected int action;
	protected int state;
	protected double reward;
	protected int id;

	
	protected Player(int id, Game game) {
		this.id = id;

	}

	protected abstract void learning(Game game);
	protected abstract int getAction(SimState sim);
	
	
	protected boolean isFirstAgent() {
		return id==0;
	}
	
	protected double maxOf(double[] array) {
		double max = Double.NEGATIVE_INFINITY;
		for(int i = 0;i<array.length;++i) {
			max = Math.max(array[i], max);
		}
		
		return max;
	}

	public static Player getPlayer(String playerType, String parameterFiles,
			int id, Game game) {
		File parameterDatabaseFile = new File(parameterFiles);
		Player player = null;
		try {
			ParameterDatabase parameters = new ParameterDatabase(
					parameterDatabaseFile.getAbsoluteFile());

			if (playerType.equals("l")) {
				player = new Lenience(parameters, id, game);
			} else if (playerType.equals("d")) {
				player = new DistributedQ(parameters, id, game);
			} else if (playerType.equals("q")) {
				player = new RegularQ(parameters, id, game);
			} else if (playerType.equals("hy")) {
				player = new Hysteretic(parameters, id, game);
			} else if (playerType.equals("fmq")) {
				player = new FMQ(parameters, id, game);
			} else if (playerType.equals("w")) {
				player = new Wolf(parameters, id, game);
			}

			return player;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

}
