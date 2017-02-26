package dev.ryanandcale.rpggame.entities.creatures;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import dev.ryanandcale.rpggame.Handler;
import dev.ryanandcale.rpggame.entities.Entity;
import dev.ryanandcale.rpggame.entities.EntityManager;
import dev.ryanandcale.rpggame.gfx.Animation;
import dev.ryanandcale.rpggame.gfx.Assets;
import dev.ryanandcale.rpggame.tiles.Tile;
import dev.ryanandcale.rpggame.entities.creatures.Player;

public class npcBob extends Creature {
	
	
	
	//Animations
	private Animation animDown, animUp, animLeft, animRight, animStill;
	private BufferedImage lastKnownAnimationFrame = Assets.npcBob_down[0];
	//Attack timer
	private long lastAttackTimer, attackCooldown = 800, attackTimer = attackCooldown;
	
	
	public npcBob(Handler handler, float x, float y) {
		super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);
		
		bounds.x = 20;
		bounds.y = 32;
		bounds.width = 23;
		bounds.height = 32;
		
		//Animations
		animDown = new Animation(100, Assets.npcBob_down);
		animUp = new Animation(100, Assets.npcBob_up);
		animLeft = new Animation(100, Assets.npcBob_left);
		animRight = new Animation(100, Assets.npcBob_right);
		
		
		
		
	}

	@Override
	public void tick() {
		//Animations
		animDown.tick();
		animUp.tick();
		animLeft.tick();
		animRight.tick();
		//Movement		
		getInput();
		move();
		//handler.getGameCamera().centerOnEntity(this);
		// Attack
		//checkAttacks();
	}
	




	private void checkAttacks(){
	//	attackTimer += System.currentTimeMillis() - lastAttackTimer;
	//	lastAttackTimer = System.currentTimeMillis();
	//	if(attackTimer < attackCooldown)
	//		return;
		
		Rectangle cb = getCollisionBounds(0,0);
		Rectangle ar = new Rectangle();
		int arSize = 20;
		ar.width = arSize;
		ar.height = arSize;
		
		//if(checkEntityCollisions(0f, 0f))
			if(lastKnownAnimationFrame == animUp.getCurrentFrame()){
			ar.x = cb.x + cb.width / 2 - arSize / 2;
			ar.y = cb.y - arSize;
		}else if(lastKnownAnimationFrame == animDown.getCurrentFrame()){
			ar.x = cb.x + cb.width / 2 - arSize / 2;
			ar.y = cb.y + cb.height;
		}else if(lastKnownAnimationFrame == animLeft.getCurrentFrame()){
			ar.x = cb.x - arSize;
			ar.y = cb.y + cb.height / 2 - arSize / 2;
		}else if(lastKnownAnimationFrame == animRight.getCurrentFrame()){
			ar.x = cb.x + cb.width;
			ar.y = cb.y + cb.height / 2 - arSize / 2;
		}else{
			return;
		}
		
		attackTimer = 0;
		
		for(Entity e: handler.getWorld().getEntityManager().getEntities()){
			if(e.equals(this))
				continue;
			 if(e.getCollisionBounds(0, 0).intersects(ar)){
				 e.hurt(1);
				 System.out.println("Attack");
			 }
		}
		
	}
	
	@Override
	public void die(){
		System.out.println("You lose");
	}
	
	
	private final long PERIOD = 2000L; // Adjust to suit timing
	private long lastTime = System.currentTimeMillis() - PERIOD;
	int r = 1;
	
	
	
	private void getInput(){
		xMove = 0;
		yMove = 0;
			
		if((handler.getWorld().getEntityManager().getPlayer().getX() > this.x - 200 && handler.getWorld().getEntityManager().getPlayer().getX() < this.x + 200) 
				&& (handler.getWorld().getEntityManager().getPlayer().getY() > this.y - 200 && handler.getWorld().getEntityManager().getPlayer().getY() < this.y + 200)){
			
			if(handler.getWorld().getEntityManager().getPlayer().getX() < this.x && !checkEntityCollisions(xMove - 1, 0f))
				xMove -= 1;
			if(handler.getWorld().getEntityManager().getPlayer().getX() > this.x && !checkEntityCollisions(xMove + 1, 0f))
				xMove += 1;
			if(handler.getWorld().getEntityManager().getPlayer().getY() < this.y && !checkEntityCollisions(0f, yMove - 1))
				yMove -= 1;
			if(handler.getWorld().getEntityManager().getPlayer().getY() > this.y && !checkEntityCollisions(0f, yMove + 1))
				yMove += 1;
		}else{
			long thisTime = System.currentTimeMillis();
	
			if ((thisTime - lastTime) >= PERIOD) {
				lastTime = thisTime;
 
	        	Random rn = new Random();
				r = rn.nextInt(4);
				//r = 0;
				System.out.println(r);
	        }else{	
	    	   	if(r == 0 && !checkEntityCollisions(0f, yMove - 1))
		    		yMove -= 1;
				if(r == 1 && !checkEntityCollisions(0f, yMove + 1))
					yMove += 1;
				if(r == 2 && !checkEntityCollisions(xMove - 1, 0f))
					xMove -= 1;
				if(r == 3 && !checkEntityCollisions(xMove + 1, 0f))
					xMove += 1;
			}
		}
	}

	@Override
	public void render(Graphics g) {  	
		g.drawImage(getCurrentAnimationFrame(), (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);

	}
	
	private BufferedImage getCurrentAnimationFrame() {
        if (xMove < 0 && !collisionWithTile((int) (x + xMove + bounds.x) / Tile.TILEWIDTH, (int) (y + bounds.y) / Tile.TILEHEIGHT) &&
				!collisionWithTile((int) (x + xMove + bounds.x) / Tile.TILEWIDTH, (int) (y + bounds.y + bounds.height) / Tile.TILEHEIGHT)) {
            lastKnownAnimationFrame = animLeft.getCurrentFrame();
        } else if (xMove > 0 && !collisionWithTile((int) (x + xMove + bounds.x + bounds.width) / Tile.TILEWIDTH, (int) (y + bounds.y) / Tile.TILEHEIGHT) &&
				!collisionWithTile((int) (x + xMove + bounds.x + bounds.width) / Tile.TILEWIDTH, (int) (y + bounds.y + bounds.height) / Tile.TILEHEIGHT)) {
            lastKnownAnimationFrame = animRight.getCurrentFrame();
        } else if (yMove < 0 && !collisionWithTile((int) (x + bounds.x) / Tile.TILEWIDTH, (int) (y + yMove + bounds.y) / Tile.TILEHEIGHT) &&
				!collisionWithTile((int) (x + bounds.x + bounds.width) / Tile.TILEWIDTH, (int) (y + yMove + bounds.y) / Tile.TILEHEIGHT)) {
            lastKnownAnimationFrame = animUp.getCurrentFrame();
        } else if (yMove > 0 && !collisionWithTile((int) (x + bounds.x) / Tile.TILEWIDTH, (int) (y + yMove + bounds.y + bounds.height) / Tile.TILEHEIGHT) &&
				!collisionWithTile((int) (x + bounds.x + bounds.width) / Tile.TILEWIDTH, (int) (y + yMove + bounds.y + bounds.height) / Tile.TILEHEIGHT)) {
            lastKnownAnimationFrame = animDown.getCurrentFrame();
        } else if (lastKnownAnimationFrame == null) {
            lastKnownAnimationFrame = Assets.npcBob_down[0];
        }
        return lastKnownAnimationFrame;
    }
		

}