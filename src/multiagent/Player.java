package multiagent;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.sun.security.auth.NTDomainPrincipal;

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
import edu.gmu.cs.multiagent.matrix.State;

public abstract class Player {

	protected int action;
	protected int state;
	protected double reward;
	protected int id;
	protected ArrayList<Double[]> qTable;
	protected ActionMode mode;
	protected Game game;
	protected int numStates;
	
	protected enum ActionMode {
		TEMPERATURE,
		DECREASEDEPSILONGREEDY,
		EPSILONGREEDY
	}

	
	protected Player(ParameterDatabase parameters, int id, Game game) {
		this.id = id;
		this.game = game;
		this.processParameters(parameters);
	}

	protected abstract void learning(Game game);
	protected abstract int getAction(SimState sim);
	protected abstract void processParameters(ParameterDatabase parameters);
	protected abstract int[] extractPolicy();
	protected abstract void reset(int state);

	
	protected void initializeQValueTable(ArrayList<State> stateList, boolean firstAgent, double value) {
		qTable = new ArrayList<Double[]>();
		
		
		for(int i = 0;i<stateList.size();++i)
		{
			State state = stateList.get(i);
			int numAction = firstAgent?state.numAgent1Action:state.numAgent2Action;
			Double[] qValues = new Double[numAction];
			for(int j = 0;j<qValues.length;++j)
			{
				qValues[j] = value;
			}
			qTable.add(qValues);
		}
	}
	
	

	
	protected boolean isFirstAgent() {
		return id==0;
	}
	
	protected double maxOf(Double[] array) {
		double max = Double.NEGATIVE_INFINITY;
		for(int i = 0;i<array.length;++i) {
			max = Math.max(array[i], max);
		}
		
		return max;
	}
	
	protected int maxAt(Double[] array) {
		int index = -1;
		double max = Double.NEGATIVE_INFINITY;
		for(int i = 0;i<array.length;++i) {
			if(array[i]>max)
			{
				max = array[i];
				index = i;
			}
		}
		
		return index;
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

	public void setState(int currentState) {
		this.state = currentState;
	}

	
}
