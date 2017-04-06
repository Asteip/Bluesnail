/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alma.extension;
import com.alma.application.interfaces.alien.IAlien;
import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

/**
 *
 * @author nadhir
 */
public class Alien implements IAlien {

	private final int INITIAL_X = 1000;
        private int hp;
        private String imagePath;
        private int x;
    	protected int y;
	protected int width;
	protected int height;
	protected boolean vis;
	protected Image image;
	
        public Alien(int x, int y) {
		this.x = x;
                this.y = y;
                
		//initAlien();
	}
        
       /*    public Alien() {
            hp = 0;
            image = "";
        }
          public Alien(int hp, String image) {
		this.hp = hp;
		this.image = image;
	}*/

	private void initAlien() {
		String currentDir = System.getProperty("user.dir");
		currentDir = currentDir
				.replace("platform",
						"applications/roger-runner/src/main/resources/monster.png");
		loadImage(currentDir);
		getImageDimensions();
	}

        @Override
	public void move() {

		if (x < 0) {
			x = INITIAL_X;
		}

		x -= 1;
	}


    
	
     
          
    @Override
	public int getHp() {
		return hp;
	}
    @Override
	public void setHp(int hp) {
		this.hp = hp;
	}
        @Override
	public String getImagePath() {
		return imagePath;
	}
    @Override
	public void setImage(String image) {
		this.imagePath = image;
	}
  
	
	@Override
	public String toString() {
		return "Alien [hp=" + hp + ", image=" + image + "]";
	}


   
        @Override
	public void getImageDimensions() {

		width = image.getWidth(null);
		height = image.getHeight(null);
	}

   
        @Override
	public void loadImage(String imageName) {
		ImageIcon ii = new ImageIcon(imageName);
		image = ii.getImage();
	}

	 
        
        
        @Override
	public int getX() {
		return x;
	}
        @Override
	public int getY() {
		return y;
	}
        
        @Override
	public boolean isVisible() {
		return vis;
	}
        
        @Override
	public void setVisible(Boolean visible) {
		vis = visible;
	}
        
        @Override
	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}

    @Override
    public Image getImage() {
           return this.image;
    }

  

    

}
