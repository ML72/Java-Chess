package minimax;

import java.util.HashMap;
import minimax.Position;

public class TranspositionTable {

	// map where all the data is encoded
	public HashMap<String, Position> positions = new HashMap<String, Position>();
	
	// query if the position is in the transposition table
	public boolean existsPosition(String id) {
		
		return positions.keySet().contains(id);
	}
	
	// get the depth of a position
	public int getDepth(String id) {
		
		return positions.get(id).depth;
	}
	
	// get the value of a existing position
	public int getValuation(String id) {

		return positions.get(id).evaluation;
	}
		
	// insert a new position into the table or update an existing one
	public void updatePosition(String id, int evaluation, int depth) {
		
		positions.put(id, new Position(id, evaluation, depth));
	}
	
}