package minimax;

import com.github.bhlangonijr.chesslib.move.Move;

public class Priority implements Comparable {

	public Move MOVE;
	public int EVALUATION;
	
	public Priority(Move move, int evaluation) {
		
		MOVE = move;
		EVALUATION = evaluation;
	}

	public int compareTo(Object o) {
		
		int compareValue = ((Priority)o).EVALUATION;
		return this.EVALUATION-compareValue;
	}
	
	public String toString() {
		
		return "Move: " + MOVE.toString() + ", eval: " + EVALUATION;
	}
}
