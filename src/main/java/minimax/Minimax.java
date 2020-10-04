package minimax;

import com.github.bhlangonijr.chesslib.*;
import com.github.bhlangonijr.chesslib.move.*;
import evaluation.Evaluator;

public class Minimax {
	
	public static int NODES_EVALUATED = 0;
	
	public static Move findBestMove(Board position, int depth) throws MoveGeneratorException {
		
		// white maximizes and black minimizes - setup values (lowest possible if maximizing, highest possible if minimizing)
		boolean isMaximizing = Side.WHITE.equals(position.getSideToMove()) ? true : false;
		int bestValue = isMaximizing ? -9000 : 9000;
		Move bestMove = null;
	
		// loop through possible moves
		MoveList moves = MoveGenerator.generateLegalMoves(position);
		for(Move move : moves) {
			
			// test out the move
			position.doMove(move);
			int value = minimax(position, depth-1, !isMaximizing);
			
			// if its a better move than the current best move, play it; randomly decide whether to change move if its of equal value
			if(isMaximizing && value > bestValue) {
				bestValue = value;
				bestMove = move;
			} else if(!isMaximizing && value < bestValue) {
				bestValue = value;
				bestMove = move;
			} else if(value == bestValue && Math.random() > 0.6) {
				bestMove = move;
			}
			
			position.undoMove();
		}
		
		return bestMove;
	}
	
	private static int minimax(Board position, int depth, boolean maximizing) throws MoveGeneratorException {
		
		NODES_EVALUATED++;
		
		// check if game is in finalized state
		if(position.isDraw()) {
			return 0;
		}
		if(position.isMated()) {
			return maximizing ? -9000 : 9000;
		}
		
		// if at target depth, call evaluation function on position
		if(depth <= 0) {
			return Evaluator.evaluateLeafNode(position);
		}
		
		// recurse through nodes with minimax
		int localBest = maximizing ? -9000 : 9000;
		for(Move move : MoveGenerator.generateLegalMoves(position)) {
			
			// test out the move
			position.doMove(move);
			int localValue = minimax(position, depth-1, !maximizing);
			
			// if its a better move than the current best move, update score
			if(maximizing && localValue > localBest) {
				localBest = localValue;
			} else if(!maximizing && localValue < localBest) {
				localBest = localValue;
			}
			
			position.undoMove();
		}
		
		return localBest;
	}
    
}
