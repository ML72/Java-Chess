package evaluation;

import com.github.bhlangonijr.chesslib.*;
import com.github.bhlangonijr.chesslib.move.*;

import evaluation.PieceValues;

public class Evaluator {

	public static int evaluateLeafNode(Board position) {
		
		int valuation = 0;
		
		// loop through all ranks and files to get the piece
		for(byte rank = 0; rank < 8; rank++) {
			Rank r = Rank.allRanks[rank];
			for(byte file = 0; file < 8; file++) {
				File f = File.allFiles[file];
				
				if(!File.NONE.equals(f) && !Rank.NONE.equals(r)) {
					Square sq = Square.encode(r, f);
					Piece piece = position.getPiece(sq);
					
					valuation += PieceValues.pieceValue(rank, file, piece);
				} else {
					throw new RuntimeException("Leaf node evaluator can't decode ranks and files");
				}

			}
		}
		
		return valuation;
	}
	
	// A Quiescence Search to avoid the horizon effect
	public static int quietSearch(Board position) {
		
		return 1;
	}
}
