package engine;

import java.io.FileNotFoundException;

import com.github.bhlangonijr.chesslib.move.MoveGeneratorException;

import minimax.Minimax;

public class Index {

	public static void main(String[] args) throws MoveGeneratorException, FileNotFoundException {
		
		System.out.println("Starting adversary program");
		
		for(int i = 0; i < 200; i++) {
			
			int depth = i % 4 + 1;
			String dst = "C:\\Applications\\EE_Engines\\CustomResults\\Raw\\Game" + i + "_Depth" + depth + ".txt";
			Adversary a = new Adversary(dst, new Minimax(depth, false, false));
			a.playFullGame();
			
			System.out.println("Written game file to " + dst);
		}
		
	}

}
