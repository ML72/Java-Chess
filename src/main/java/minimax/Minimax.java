package minimax;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.Side;
import com.github.bhlangonijr.chesslib.move.Move;
import com.github.bhlangonijr.chesslib.move.MoveGenerator;
import com.github.bhlangonijr.chesslib.move.MoveGeneratorException;
import com.github.bhlangonijr.chesslib.move.MoveList;

import evaluation.Evaluator;

public class Minimax {
	
	// node counter for positions
	public int NODES_EVALUATED = 0;
	public int ALPHA_BETA_CUTOFFS = 0;
	public int IDENTICAL_TRANSPOSITIONS = 0;
	
	// settings to tweak
	public int DESIRED_DEPTH;
	public boolean ALPHA_BETA;
	public boolean TRANSPOSITION_TABLE;
	
	// transposition table for recording repeat positions
	private TranspositionTable table = new TranspositionTable();
	
	// ways to instantiate Minimax object with settings
	public Minimax() {
		
		this(4, true, true);
	}
	
	public Minimax(int desiredDepth, boolean alphaBeta, boolean transpositionTable) {
		DESIRED_DEPTH = desiredDepth;
		ALPHA_BETA = alphaBeta;
		TRANSPOSITION_TABLE = transpositionTable;
	}
	
	// call method to find best move given presets
	public Move findBestMove(Board position) throws MoveGeneratorException {
		
		// white maximizes and black minimizes - setup values (lowest possible if maximizing, highest possible if minimizing)
		boolean isMaximizing = Side.WHITE.equals(position.getSideToMove()) ? true : false;
		int bestValue = isMaximizing ? -90000 : 90000;
		Move bestMove = null;
		NODES_EVALUATED = 0;
		ALPHA_BETA_CUTOFFS = 0;
		IDENTICAL_TRANSPOSITIONS = 0;
	
		// loop through possible moves
		MoveList moves = MoveGenerator.generateLegalMoves(position);
		for(Move move : moves) {
			
			// test out the move
			position.doMove(move);
			int value = 0;
			if(ALPHA_BETA) {
				value = minimaxAlphaBeta(position, DESIRED_DEPTH-1, !isMaximizing, -90000, 90000);
			} else {
				value = minimax(position, DESIRED_DEPTH-1, !isMaximizing);
			}
			
			// if its a better move than the current best move, play it; randomly decide whether to change move if its of equal value
			if(isMaximizing && value > bestValue) {
				bestValue = value;
				bestMove = move;
			} else if(!isMaximizing && value < bestValue) {
				bestValue = value;
				bestMove = move;
			} else if(value == bestValue && Math.random() > 0.4) {
				bestMove = move;
			}
						
			position.undoMove();
			
		}
		
		return bestMove;
	}
	
	// classic minimax with no optimizations
	private int minimax(Board position, int depth, boolean maximizing) throws MoveGeneratorException {
		
		NODES_EVALUATED++;
		
		// check if game is in finalized state
		if(position.isDraw()) {
			return 0;
		}
		if(position.isMated()) {
			return maximizing ? -90000 : 90000;
		}
		
		// if at target depth, call evaluation function on position
		if(depth <= 0) {
			return Evaluator.evaluateLeafNode(position, maximizing);
		}
		
		// recurse through nodes with minimax
		int localBest = maximizing ? -90000 : 90000;
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
	
	// minimax with alpha beta pruning
	private int minimaxAlphaBeta(Board position, int depth, boolean maximizing, int alpha, int beta) throws MoveGeneratorException {
				
		NODES_EVALUATED++;
		
		// test for transposition at target depth or deeper
		String positionId = position.getPositionId();
		if(TRANSPOSITION_TABLE) {
			if(table.existsPosition(positionId)) {
				if(table.getDepth(positionId) >= depth) {
					
					IDENTICAL_TRANSPOSITIONS++;
					
					return table.getValuation(positionId);
				}
			}
		}
		
		// check if game is in finalized state
		if(position.isDraw()) {
			return 0;
		}
		if(position.isMated()) {
			return maximizing ? -90000 : 90000;
		}
		
		// if at target depth, call evaluation function on position
		if(depth <= 0) {
			return Evaluator.evaluateLeafNode(position, maximizing);
		}
		
		// recurse through nodes with minimax with alpha beta
		int localBest = maximizing ? -90000 : 90000;
		
		// create priority list with transposition tables - iterative deepening approach
		boolean usingMovePrioritization = TRANSPOSITION_TABLE;
		ArrayList<Priority> prioritization = new ArrayList<Priority>();
		if(usingMovePrioritization) {
			for(Move move : MoveGenerator.generateLegalMoves(position)) {
				position.doMove(move);
				prioritization.add(new Priority(move, table.existsPosition(position.getPositionId()) ? table.getValuation(position.getPositionId()) : Evaluator.evaluateLeafNodeWithDepth(position, maximizing, 0)));
				position.undoMove();
			}
		}
		
		if(maximizing) {
			
			if(usingMovePrioritization) {
				
				Collections.sort(prioritization);
				Collections.reverse(prioritization);
				
				for(Priority p : prioritization) {
										
					// test out the move
					position.doMove(p.MOVE);
					int localValue = minimaxAlphaBeta(position, depth-1, !maximizing, alpha, beta);
					position.undoMove();
					
					localBest = Math.max(localBest, localValue);
					
					// alpha cutoff
					alpha = Math.max(alpha, localBest);
					if(beta <= alpha) {
						
						ALPHA_BETA_CUTOFFS++;
						
						return localBest;
					}
				}
				
				
			} else {
				
				for(Move move : MoveGenerator.generateLegalMoves(position)) {
					
					// test out the move
					position.doMove(move);
					int localValue = minimaxAlphaBeta(position, depth-1, !maximizing, alpha, beta);
					position.undoMove();
					
					localBest = Math.max(localBest, localValue);
					
					// alpha cutoff
					alpha = Math.max(alpha, localBest);
					if(beta <= alpha) {

						ALPHA_BETA_CUTOFFS++;

						return localBest;
					}
					
				}
			}
			
		} else {
			
			if(usingMovePrioritization) {
				
				Collections.sort(prioritization);
				
				for(Priority p : prioritization) {

					
					// test out the move
					position.doMove(p.MOVE);
					int localValue = minimaxAlphaBeta(position, depth-1, !maximizing, alpha, beta);
					position.undoMove();
					
					localBest = Math.min(localBest, localValue);
					
					// beta cutoff
					beta = Math.min(beta, localBest);
					if(beta <= alpha) {
						
						ALPHA_BETA_CUTOFFS++;

						return localBest;
					}
				}
			} else {
				
				for(Move move : MoveGenerator.generateLegalMoves(position)) {
					
					// test out the move
					position.doMove(move);
					int localValue = minimaxAlphaBeta(position, depth-1, !maximizing, alpha, beta);
					position.undoMove();
					
					localBest = Math.min(localBest, localValue);
					
					// beta cutoff
					beta = Math.min(beta, localBest);
					if(beta <= alpha) {
						
						ALPHA_BETA_CUTOFFS++;

						return localBest;
					}
					
				}
			}
			
		}
		
		// update transposition table
		if(TRANSPOSITION_TABLE) {
			table.updatePosition(positionId, localBest, depth);
		}
		
		return localBest;
	}
    
}
