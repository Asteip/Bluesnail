/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alma.application.interfaces.alien;

import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

/**
 *
 * @author nadhir
 */
public interface IAlien {
        int getHp();
        void setHp(int hp);
        String getImagePath();
        void setImage(String image);
        
	public String toString();

	public void getImageDimensions();

	public void loadImage(String imageName);

	 
        public void move();
	public int getX();
	public int getY();
	public boolean isVisible();
	public void setVisible(Boolean visible);
	public Rectangle getBounds();

        public Image getImage();

        
}
