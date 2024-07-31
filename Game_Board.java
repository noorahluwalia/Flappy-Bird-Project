
import javax.swing.*;

public class Game_Board {
	public static void main(String[] args) {
		int Board_Height = 640;
		int Board_Width = 360;
		
		JFrame Frame = new JFrame("Flappy Bird");
		Frame.setSize(Board_Width, Board_Height);
		
		Frame.setLocationRelativeTo(null);
		Frame.setResizable(false);
		Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		
		
		 Game_Graphic Flappy_Bird = new Game_Graphic();
	        Frame.add(Flappy_Bird);
	        Frame.pack();
	        Flappy_Bird.requestFocus();
	        Frame.setVisible(true);
	}

}
