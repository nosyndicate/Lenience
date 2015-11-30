package multiagent.player;

import ec.util.ParameterDatabase;
import edu.gmu.cs.multiagent.matrix.Game;
import multiagent.Player;

public class RegularQ extends Player {

	private int numAction;
	private int numState;
	private double[][] QTable;
	private double gamma;
	private double alpha;
	private int actionSelectionStrategy;

	public RegularQ(ParameterDatabase parameters, int id, Game game) {
		super(id);
		numAction = game.numActions[id];
		numState = game.numStates;
		QTable = new double[numState][numAction];
	}

	@Override
	protected void learning(Game game) {
		double target = game.getReward(isFirstAgent());
		if (!game.isGameEnd())
			target += gamma * maxOf(QTable[game.currentState]);

		QTable[state][action] = (1 - alpha) * QTable[state][action] + alpha
				* target;
		
		state = game.currentState;
	}

	@Override
	protected int getAction() {
		switch (actionSelectionStrategy) {
		case 0:

			break;
		case 1:

			break;
		case 2:

			break;

		}
		return this.action;
	}

}