package multiagent.player;

import ec.util.ParameterDatabase;
import edu.gmu.cs.multiagent.matrix.Game;
import multiagent.Player;
import sim.engine.SimState;

public class DistributedQ extends Player {

	public DistributedQ(ParameterDatabase parameters, int id, Game game) {
		super(id, game);
	}

	@Override
	protected void learning(Game game) {

	}

	@Override
	protected int getAction(SimState sim) {
		// TODO Auto-generated method stub
		return 0;
	}

}
