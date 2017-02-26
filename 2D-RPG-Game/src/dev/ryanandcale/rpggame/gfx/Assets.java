package dev.ryanandcale.rpggame.gfx;

import java.awt.image.BufferedImage;

public class Assets {
	
	private static final int width = 32, height = 32;
	
	public static BufferedImage dirt, grass, stone, tree;
	public static BufferedImage[] player, player_down, player_up, player_left, player_right;
	public static BufferedImage[] npcBob, npcBob_down, npcBob_up, npcBob_left, npcBob_right;
	public static BufferedImage[] btn_start;

	public static void init(){
		SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/textures/sheet.png"));
		
		btn_start = new BufferedImage[2];
		btn_start[0] = sheet.crop(width * 4, 0, width * 2, height);
		btn_start[1] = sheet.crop(width * 4, height, width * 2, height);
		
		player_down = new BufferedImage[3];
		player_right = new BufferedImage[3];
		player_left = new BufferedImage[3];
		player_up = new BufferedImage[3];
		player = new BufferedImage[1];
		
		player_down[0] = sheet.crop(width * 1, height * 1, width, height);
		player_down[1] = sheet.crop(0, height * 1, width, height);
		player_down[2] = sheet.crop(width * 2, height * 1, width, height);
		
		
		player_up[0] = sheet.crop(width * 1, height * 4, width, height);
		player_up[1] = sheet.crop(0, height * 4, width, height);
		player_up[2] = sheet.crop(width * 2, height * 4, width, height);
		
		
		player_left[0] = sheet.crop(width * 1, height * 2, width, height);
		player_left[1] = sheet.crop(0, height * 2, width, height);
		player_left[2] = sheet.crop(width * 2, height * 2, width, height);
		
		
		player_right[0] = sheet.crop(width * 1, height * 3, width, height);
		player_right[1] = sheet.crop(0, height * 3, width, height);
		player_right[2] = sheet.crop(width * 2, height * 3, width, height);
		
		npcBob_down = new BufferedImage[3];
		npcBob_right = new BufferedImage[3];
		npcBob_left = new BufferedImage[3];
		npcBob_up = new BufferedImage[3];
		npcBob = new BufferedImage[1];
		
		npcBob_down[0] = sheet.crop(width * 4, height * 2, width, height);
		npcBob_down[1] = sheet.crop(width * 3, height * 2, width, height);
		npcBob_down[2] = sheet.crop(width * 5, height * 2, width, height);
		
		
		npcBob_up[0] = sheet.crop(width * 4, height * 5, width, height);
		npcBob_up[1] = sheet.crop(width * 3, height * 5, width, height);
		npcBob_up[2] = sheet.crop(width * 5, height * 5, width, height);
		
		
		npcBob_left[0] = sheet.crop(width * 4, height * 3, width, height);
		npcBob_left[1] = sheet.crop(width * 3, height * 3, width, height);
		npcBob_left[2] = sheet.crop(width * 5, height * 3, width, height);
		
		
		npcBob_right[0] = sheet.crop(width * 4, height * 4, width, height);
		npcBob_right[1] = sheet.crop(width * 3, height * 4, width, height);
		npcBob_right[2] = sheet.crop(width * 5, height * 4, width, height);
		
		dirt = sheet.crop(width, 0, width, height);
		grass = sheet.crop(width * 2, 0, width, height);
		stone = sheet.crop(width * 3, 0, width, height);
		tree = sheet.crop(0, 0, width, height);
		player[0] = sheet.crop(width * 1, height * 1, width, height);
		npcBob[0] = sheet.crop(width * 4, height * 2, width, height);
	}
	
}