package multiagent;


import java.io.File;
import java.io.IOException;
import multiagent.player.DistributedQ;
import multiagent.player.FMQ;
import multiagent.player.Hysteretic;
import multiagent.player.Lenience;
import multiagent.player.RegularQ;
import multiagent.player.Wolf;
import ec.util.ParameterDatabase;


public abstract class Player{

	protected int action;
	protected int state;
	protected double reward;
	
	protected abstract void learning();
	
	public int getAction() {
		return action;
	}

	public static Player getPlayer(String playerType, String parameterFiles) {
		File parameterDatabaseFile = new File(parameterFiles);
		Player player = null;
		try {
			ParameterDatabase parameters = new ParameterDatabase(parameterDatabaseFile.getAbsoluteFile());
			
			if(playerType.equals("l")) {
				player = new Lenience(parameters);
			} else if(playerType.equals("d")) {
				player = new DistributedQ(parameters);
			} else if(playerType.equals("q")) {
				player = new RegularQ(parameters);
			} else if(playerType.equals("hy")) {
				player = new Hysteretic(parameters);
			} else if(playerType.equals("fmq")) {
				player = new FMQ(parameters);
			} else if(playerType.equals("w")) {
				player = new Wolf(parameters);
			}
			
			return player;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
