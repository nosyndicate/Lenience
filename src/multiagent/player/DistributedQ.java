package multiagent.player;

import ec.util.ParameterDatabase;
import edu.gmu.cs.multiagent.matrix.Game;
import multiagent.Player;

public class DistributedQ extends Player {

	public DistributedQ(ParameterDatabase parameters, int id, Game game) {
		super(id);
	}

	@Override
	protected void learning(Game game) {

	}

	@Override
	protected int getAction() {
		// TODO Auto-generated method stub
		return 0;
	}

}
