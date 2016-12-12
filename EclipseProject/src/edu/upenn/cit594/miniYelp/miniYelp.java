package edu.upenn.cit594.miniYelp;

import javax.swing.text.BadLocationException;

public class miniYelp {

	public static void main(String[] args) {
		try {
			Controller miniYelp = new Controller();
			miniYelp.runMiniYelp();
		} catch (BadLocationException e) {
		}

	}

}
