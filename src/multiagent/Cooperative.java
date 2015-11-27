package multiagent;

import edu.gmu.cs.multiagent.matrix.Game;
import sim.engine.SimState;




public class Cooperative extends SimState {

	private static final long serialVersionUID = 1L;
	public static String[] arguments;
	public String gameFile;
	public String firstPlayerType;
	public String firstPlayerParameters;
	public String secondPlayerType;
	public String secondPlayerParameters;
	public Game game;
	public GameScheduler schuduler;
	public Player playerOne;
	public Player playerTwo;
	
	public Cooperative(long seed) {
		super(seed);
	}

	static boolean keyExists(String key, String[] args) {

		for (String arg : args) {
			if (arg.equalsIgnoreCase(key)) {
				return true;
			}
		}
		return false;
	}

	static String argumentForKey(String key, String[] args) {
		// if a key has an argument, it can't be the last string
		for (int x = 0; x < args.length - 1; x++) {
			if (args[x].equalsIgnoreCase(key)) {
				return args[x + 1];
			}
		}
		return null;
	}

	public void start() {
		super.start(); // clear out the schedule
		processArguments();
		
		game = new Game(gameFile);
		playerOne = Player.getPlayer(firstPlayerType, firstPlayerParameters);
		playerTwo = Player.getPlayer(secondPlayerType, secondPlayerParameters);
		schuduler = new GameScheduler(playerOne, playerTwo);
		schedule.scheduleRepeating(schuduler);
		
		
	}

	private void processArguments() {
		
		if(arguments !=null && keyExists("-game", arguments)) {
			gameFile = argumentForKey("-game", arguments);
		}
		
		if(arguments !=null && keyExists("-1", arguments)) {
			firstPlayerType = argumentForKey("-1", arguments);
		}
		
		if(arguments !=null && keyExists("-1p", arguments)) {
			firstPlayerParameters = argumentForKey("-1p", arguments);
		}
		
		if(arguments !=null && keyExists("-2", arguments)) {
			secondPlayerType = argumentForKey("-2", arguments);
		}
		
		if(arguments !=null && keyExists("-2p", arguments)) {
			secondPlayerParameters = argumentForKey("-2p", arguments);
		}
		
	}

	public static void main(String[] args) {
		arguments = args;
		doLoop(Cooperative.class, args);
		System.exit(0);
	}
}
