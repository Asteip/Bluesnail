package com.alma.extension;

import com.alma.application.interfaces.alien.IAlien;
import com.alma.application.interfaces.alien.IAlienFactory;
import java.util.Random;

/**
 * 
 *
 */
public class AliensFactory implements IAlienFactory {
	public AliensFactory() {

	}

	private int[][] pos = new int[50][50];

	@Override
	public IAlien createAlien() {
		Random rdm;
		rdm = new Random();
		for (int i = 0; i < 50; i++) {
			for (int j = 0; j < 50; j++) {
				this.pos[i][j] = rdm.nextInt(400);
			}
		}

		String currentDir = System.getProperty("user.dir");
		currentDir = currentDir.replace("platform", "extensions/aliensFactory/src/main/ressources/");
		System.out.println(currentDir);
		Random rd = new Random();
		
		IAlien a = new Alien(pos[rd.nextInt(49)][rd.nextInt(49)] + 1000, pos[rd.nextInt(49)][rd.nextInt(49)]);
		a.setHp(rd.nextInt(19) + 1);
		switch (rd.nextInt(3)) {
		case 0:
			currentDir += "monster.png";
			a.loadImage(currentDir);
			a.setVisible(Boolean.TRUE);
			a.getImageDimensions();
			break;
		case 1:
			currentDir += "monster1.png";
			a.loadImage(currentDir);
			a.setVisible(Boolean.TRUE);
			a.getImageDimensions();
			break;
		case 2:
			currentDir += "monster2.png";
			a.loadImage(currentDir);
			a.setVisible(Boolean.TRUE);
			a.getImageDimensions();

			break;

		default:
			break;
		}
		System.out.println(a.getImage());
		return a;
	}

}