package minimax;

public class Position {

	// encodes the position, as well as its evaluation and depth
	public String id;
	public int evaluation;
	public int depth;
	
	public Position(String id, int evaluation, int depth) {
		
		this.id = id;
		this.evaluation = evaluation;
		this.depth = depth;
	}
}
