package engine;

import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import com.github.bhlangonijr.chesslib.move.MoveGeneratorException;

import minimax.Minimax;

public class Index {

	public static void main(String[] args) throws MoveGeneratorException, IOException, EncryptedDocumentException, InvalidFormatException {
		
		System.out.println("Starting adversary program");
		
		for(int i = 0; i < 3; i++) {
			
			int depth = i % 3 + 1;
			String dst = "C:\\Applications\\EE_Engines\\CustomResults\\Raw_Data\\Output" + (i+1) + ".xlsx";
			Adversary a = new Adversary(dst, new Minimax(depth, true, true, true), "Iteration" + (i+1));
			a.playFullGame();
			
			System.out.println("Written game file to " + dst + " as iteration " + (i+1) + " with depth " + depth);
		}
		
	}

}
