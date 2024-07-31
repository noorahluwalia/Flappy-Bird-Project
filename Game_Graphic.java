import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;




	public class Game_Graphic extends JPanel implements ActionListener, KeyListener {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		int Board_Width = 360;
	    int Board_Height = 640;
	    
	    //Variables to store images
		Image Bird;
 	    Image Background;
 	    Image Top_Pipe;
 	    Image Bottom_Pipe;
 	    
 	    //Bird Position Parameters
 	    int X_Position = Board_Width/8;
 	    int Y_Position = Board_Height/2;
 	    int BirdWidth = 28;
 	    int BirdHeight = 18;
 	    
 	    //Pipe Position Parameters
 	    int PipeX = Board_Width;  //X Position
 	    int PipeY = 0;            //Y Position
 	    int PipeWidth = 64;           //Pipe
 	    int PipeHeight = 512;         //Dimensions
 	    
 	    
 	    //Class to store Bird Image requirements
 	    class FlappyBird
 	       {
 	        int X = X_Position;
 	    	int Y = Y_Position;
 	    	int Width = BirdWidth;
 	    	int Heigth = BirdHeight;
 	    	Image img;
 	    	FlappyBird(Image img){
 	    		this.img = img;}
 	       }
 	    	
 	    //Class to store Pipe Specification
 	    class Pipe 
 	    	{   
 	    	    int x = PipeX;
 	    		int y = PipeY;
 	    		int width = PipeWidth;
 	    		int height=PipeHeight;
 	    		Image img;
 	    		boolean passed = true;
 	    		Pipe(Image img){
 	    			this.img = img;}
 	    	 }
 	   //Game Logic
 	    int VelocityY = 0;
 	    int VelocityX = -4;
 	    int gravity = 1;
 	    FlappyBird Bird_Object;
 	    Timer GameLoop;
 	    Timer PipeTimer;
 	    boolean gameOver = false;
 	    double score = 0;
 	    ArrayList<Pipe> AllPipes;
 	    Random random_pipe_generator = new Random();
 	    		
      Game_Graphic()
	      {
	    	setPreferredSize(new Dimension(Board_Width,Board_Height));
	    	//setBackground(Color.cyan);
	    	setFocusable(true);
	    	addKeyListener(this);
	    	//Linking images with variable
	        Background = new ImageIcon(getClass().getResource("./flappybirdbg.png")).getImage();
	        Bird = new ImageIcon(getClass().getResource("./bird.png")).getImage();
	        Top_Pipe = new ImageIcon(getClass().getResource("./toppipe.png")).getImage();
	        Bottom_Pipe = new ImageIcon(getClass().getResource("./bottompipe.png")).getImage();
	        
	        Bird_Object = new FlappyBird(Bird);
	        AllPipes = new ArrayList<Pipe>(); 
	         
	        //Adding Pipes Timer
	         PipeTimer = new Timer(1500, new ActionListener() {
	        	 
	        	 public void actionPerformed(ActionEvent e) {
	        		 add_pipes();}
	        	 });
	         PipeTimer.start();
	         //Game Loop Timer
	         GameLoop = new Timer(1000/60,this);
	         GameLoop.start();
	         
	       
	    }
	    public void add_pipes() {
			   int randomPipeY = (int) (PipeY - PipeHeight/4 - Math.random()*(PipeHeight/2));
		       int openingSpace = Board_Height/4;
		    
		        Pipe topPipe = new Pipe(Top_Pipe);
		        topPipe.y = randomPipeY;
		        AllPipes.add(topPipe);
		    
		        Pipe bottomPipe = new Pipe(Bottom_Pipe);
		        bottomPipe.y = topPipe.y  + PipeHeight + openingSpace;
		        AllPipes.add(bottomPipe);
		   }
	   
		 public void paintComponent(Graphics g) 
	    {
	 			super.paintComponent(g);
	 			draw(g);
	    }

	 	
		public void draw(Graphics g) 
	 	{
	 	        // Draw the background
	 	        g.drawImage(Background, 0 , 0, Board_Width, Board_Height, null);
	 	        // Draw the Bird
	 	        g.drawImage(Bird,Bird_Object.X, Bird_Object.Y, Bird_Object.Width, Bird_Object.Heigth, null);
	 	        //Draw the Pipes
	 	           for (int i = 0; i < AllPipes.size(); i++) {
	 	              Pipe pipes = AllPipes.get(i);
	 	              g.drawImage(pipes.img, pipes.x, pipes.y, pipes.width, pipes.height, null);
	 	          }

	 	          //score
	 	          g.setColor(Color.white);

	 	          g.setFont(new Font("Arial", Font.PLAIN, 32));
	 	          if (gameOver) {
	 	              g.drawString("Game Over: " + String.valueOf((int) score), 10, 35);
	 	          }
	 	          else {
	 	              g.drawString(String.valueOf((int) score), 10, 35);
	 	          }
	 	    }

	 	  
		public void move() {
 			VelocityY += gravity;
	 		Bird_Object.Y += VelocityY;
	 		Bird_Object.Y = Math.max(Y_Position,0);
	 		
	 		  for (int i = 0; i < AllPipes.size(); i++) {
	 	            Pipe pipes = AllPipes.get(i);
	 	            pipes.x += VelocityX;

	 	            if (!pipes.passed && Bird_Object.X > pipes.x + pipes.width) {
	 	                score += 0.5; 
	 	                pipes.passed = true;
	 	            }

	 	            if (collision(Bird_Object, pipes)) {
	 	                gameOver = true;
	 	            }
	 	        }

	 	        if (Bird_Object.Y > Board_Height) {
	 	            gameOver = true;
	 	           }
		}
		
		boolean collision(FlappyBird a, Pipe b) {
	        return a.X < b.x + b.width &&   //a's top left corner doesn't reach b's top right corner
	               a.X + a.Width > b.x &&   //a's top right corner passes b's top left corner
	               a.Y < b.y + b.height &&  //a's top left corner doesn't reach b's bottom left corner
	               a.Y + a.Heigth > b.y;    //a's bottom left corner passes b's top left corner
	    }
        
		
		 
		    public void actionPerformed(ActionEvent e) { //called every x milliseconds by gameLoop timer
		        move();
		        repaint();
		        if (gameOver) {
		            PipeTimer.stop();
		            GameLoop.stop();
		        }
		    } 
		 public void keyPressed(KeyEvent e) {
		        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
		            // System.out.println("JUMP!");
		            VelocityY = -9;

		            if (gameOver) {
		                //restart game by resetting conditions
		                Bird_Object.Y = BirdHeight;
		                VelocityY = 0;
		                AllPipes.clear();
		                gameOver = false;
		                score = 0;
		                GameLoop.start();
		                PipeTimer.start();
		            }
		        }
		    }

		
  


	    public void keyTyped(KeyEvent e) {
			
		}

		public void keyReleased(KeyEvent e) {
			
		}
	 	}        

	 	         
	   
	 		
	 