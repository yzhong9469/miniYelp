package edu.upenn.cit594.miniYelp;

import javax.swing.text.BadLocationException;

/**
 * Our main program, please run this program
 * @author Yan Zhong
 *
 */
public class miniYelp {

	public static void main(String[] args) {
		try {
			Controller miniYelp = new Controller();
			miniYelp.runMiniYelp();
		} catch (BadLocationException e) {
		}

	}

}
