package multiagent;

import java.util.ArrayList;
import java.util.Collections;

import ec.util.MersenneTwisterFast;


public class Utils {
	public static int epsilonGreedy(MersenneTwisterFast random, double epsilon, ArrayList<Double> actionList) {
		if(random.nextDouble()<epsilon)
		{
			return random.nextInt(actionList.size());
		}
		else {
			return bestAction(random, actionList);
		}
	}
	
	public static int epsilonGreedy(MersenneTwisterFast random, double epsilon, Double[] values) {
		if(random.nextDouble()<epsilon)
		{
			return random.nextInt(values.length);
		}
		else {
			return bestAction(random, values);
		}
	}
	
	public static int orderedEpsilonGreedy(MersenneTwisterFast random, double epsilon, ArrayList<Double> actionList) {
		if(random.nextDouble()<epsilon)
		{
			return random.nextInt(actionList.size());
		}
		else {
			return orderedBestAction(actionList);
		}
	}
	
	public static int orderedEpsilonGreedy(MersenneTwisterFast random, double epsilon, Double[] values) {
		if(random.nextDouble()<epsilon)
		{
			return random.nextInt(values.length);
		}
		else {
			return orderedBestAction(values);
		}
	}
	
	private static ArrayList<Integer> bestActionsPool(ArrayList<Double> actionList){
		ArrayList<Integer> pool = new ArrayList<Integer>();
		double max =  Double.NEGATIVE_INFINITY;
		for(int i = 0;i<actionList.size();++i)
		{
			double v =  actionList.get(i);
			if(max < v) // new max value, clear the pool
			{
				max = v;
				pool.clear();	
			}
			
			if(max == v) // add the index to the pool
			{
				pool.add(i);
			}
		}
		
		return pool;
	}
	
	private static ArrayList<Integer> bestActionsPool(Double[] values){
		ArrayList<Integer> pool = new ArrayList<Integer>();
		double max =  Double.NEGATIVE_INFINITY;
		for(int i = 0;i<values.length;++i)
		{
			double v =  values[i];
			if(max < v) // new max value, clear the pool
			{
				max = v;
				pool.clear();	
			}
			
			if(max == v) // add the index to the pool
			{
				pool.add(i);
			}
		}
		
		return pool;
	}

	public static int orderedBestAction(ArrayList<Double> actionList) {
		ArrayList<Integer> pool = bestActionsPool(actionList);
		
		// alway return the first one
		return pool.get(0);
	}
	
	public static int orderedBestAction(Double[] values) {
		ArrayList<Integer> pool = bestActionsPool(values);
		
		// alway return the first one
		return pool.get(0);
	}
	

	public static int bestAction(MersenneTwisterFast random, ArrayList<Double> actionList) {
		ArrayList<Integer> pool = bestActionsPool(actionList);
		
		// choose randomly from the pool
		int randomChoosen = random.nextInt(pool.size());
		int index = pool.get(randomChoosen);
		
		// return a index from the pool, break tie randomly
		return index;
	}
	
	public static int bestAction(MersenneTwisterFast random, Double[] values) {
		ArrayList<Integer> pool = bestActionsPool(values);
		
		// choose randomly from the pool
		int randomChoosen = random.nextInt(pool.size());
		int index = pool.get(randomChoosen);
		
		// return a index from the pool, break tie randomly
		return index;
	}


	public static int boltzmannSelection(MersenneTwisterFast random, double temperature, ArrayList<Double> actionList)
	{
		ArrayList<Double> prob = new ArrayList<Double>();
		double sum = 0;
		try{
			// compute the cumulative value
			for(int i = 0;i<actionList.size();++i)
			{
				double v = actionList.get(i);
				v = v/temperature;
				v = Math.exp(v);
				sum += v;
				prob.add(sum);
			}
			
			// normalized it so we have a cumulative probability distribution
			for(int i = 0;i<prob.size();++i)
			{
				double v = prob.get(i);
				v = v/sum;
				prob.set(i, v);
				
				if(Double.isNaN(v))
					System.err.print("we have a NaN value");
			}
		} catch (ArithmeticException e) {
			System.err.println("arithmetic error");
		}
		
		return selectFrom(random, prob);
	}

	
	public static int boltzmannSelection(MersenneTwisterFast random, double temperature, Double[] values)
	{
		ArrayList<Double> prob = new ArrayList<Double>();
		double sum = 0;
		try{
			// compute the cumulative value
			for(int i = 0;i<values.length;++i)
			{
				double v = values[i];
				v = v/temperature;
				v = Math.exp(v);
				sum += v;
				prob.add(sum);
			}
			
			// normalized it so we have a cumulative probability distribution
			for(int i = 0;i<prob.size();++i)
			{
				double v = prob.get(i);
				v = v/sum;
				prob.set(i, v);
				
				if(Double.isNaN(v))
					System.err.print("we have a NaN value");
			}
		} catch (ArithmeticException e) {
			System.err.println("arithmetic error");
		}
		
		return selectFrom(random, prob);
	}
	
	
	public static int selectFrom(MersenneTwisterFast random, ArrayList<Double> prob) {
		double val = random.nextDouble();
		
		int index = Collections.binarySearch(prob, val);
		
		if (index < 0)
		{
			index = -(index + 1);		// Arrays.binarySearch(...) returns (-(insertion point) - 1) if the key isn't found
		}
		
		if (index == prob.size())
			System.err.format("Error in Boltzmann selection");
		
		return index;
	}
	
	
}
