package multiagent.player;

import javax.media.j3d.Alpha;

import ec.util.ParameterDatabase;
import edu.gmu.cs.multiagent.matrix.Game;
import multiagent.Player;
import sim.engine.SimState;
import sim.util.distribution.Gamma;

public class Hysteretic extends Player {
	
	final private static double INITIAL_VALUE = 0;
	
	private double temperature = 0;
	private double temperatureDecay = 0;
	private double epsilon = 0;
	private double epsilonDecay = 0;
	private double gamma = 0.9;
	private double alpha = 0;
	private double beta = 0;

	public Hysteretic(ParameterDatabase parameters, int id, Game game) {
		super(id, game);
		
		// TODO:need to initialize selection mode there
		
		int stateNum = game.numStates;
		int actionNum = game.numActions[this.isFirstAgent()?0:1];
		
		state = game.currentState;
		
		this.initializeQValueTable(stateNum, actionNum, INITIAL_VALUE);
	}

	@Override
	protected void learning(Game game) {
		
		double rewardTerm = game.getReward(this.isFirstAgent());
		
		if(!game.isGameEnd()) {
			rewardTerm += gamma*maxOf(qTable[game.currentState]);
		}
		
		double temporalDifference = rewardTerm - qTable[state][action];
		
		if(temporalDifference >= 0) {
			qTable[state][action] = qTable[state][action] + alpha*temporalDifference;
		}
		else
			qTable[state][action] = qTable[state][action] + beta *temporalDifference;

		// s <- s'
		state = game.currentState;
		
	}

	@Override
	protected int getAction(Game game) {
		
		if(mode==ActionMode.TEMPERATURE) {
			action = boltzmannSelection(this.qTable[state], temperature);
			temperature *= temperatureDecay;
		}
		else if(mode==ActionMode.EPSILONGREEDY) {
			action = epsilonGreedy(qTable[state], epsilon);
		}
		else if(mode==ActionMode.DECREASEDEPSILONGREEDY) {
			action = epsilonGreedy(qTable[state], epsilon);
			epsilon *= epsilon*epsilonDecay;
		}
		return action;
	}

	

	
}