package dev.ryanandcale.rpggame.entities.creatures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import dev.ryanandcale.rpggame.Game;
import dev.ryanandcale.rpggame.Handler;
import dev.ryanandcale.rpggame.entities.Entity;
import dev.ryanandcale.rpggame.gfx.Animation;
import dev.ryanandcale.rpggame.gfx.Assets;

public class Player extends Creature {
	
	//Animations
	private Animation animDown, animUp, animLeft, animRight, animStill;
	private BufferedImage lastKnownAnimationFrame = Assets.player_down[0];



	//Attack timer
	private long lastAttackTimer, attackCooldown = 800, attackTimer = attackCooldown;
	
	public Player(Handler handler, float x, float y) {
		super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);
		
		
		bounds.x = 20;
		bounds.y = 32;
		bounds.width = 23;
		bounds.height = 32;
		x = this.x;
		
		//Animations
		animDown = new Animation(100, Assets.player_down);
		animUp = new Animation(100, Assets.player_up);
		animLeft = new Animation(100, Assets.player_left);
		animRight = new Animation(100, Assets.player_right);
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
		handler.getGameCamera().centerOnEntity(this);
		// Attack
		checkAttacks();
		//System.out.println(getY());
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
		
		if(handler.getKeyManager().attack)
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
			 }
		}
		
	}
	
	@Override
	public void die(){
		System.out.println("You lose");
	}
	
	private void getInput(){
		xMove = 0;
		yMove = 0;
		
		
		
		if(handler.getKeyManager().up)
			yMove = -speed;
		if(handler.getKeyManager().down)
			yMove = speed;
		if(handler.getKeyManager().left)
			xMove = -speed;
		if(handler.getKeyManager().right)
			xMove = speed;
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(getCurrentAnimationFrame(), (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
		
		//g.setColor(Color.red);
	//	g.fillRect((int) (x + bounds.x - handler.getGameCamera().getxOffset()),
	//			(int) (y + bounds.y - handler.getGameCamera().getyOffset()),
	//			bounds.width, bounds.height);
	}
	
	private BufferedImage getCurrentAnimationFrame() {
        if (xMove < 0) {
            lastKnownAnimationFrame = animLeft.getCurrentFrame();
        } else if (xMove > 0) {
            lastKnownAnimationFrame = animRight.getCurrentFrame();
        } else if (yMove < 0) {
            lastKnownAnimationFrame = animUp.getCurrentFrame();
        } else if (yMove > 0) {
            lastKnownAnimationFrame = animDown.getCurrentFrame();
        } else if (lastKnownAnimationFrame == null) {
            lastKnownAnimationFrame = Assets.player_down[0];
        }
        return lastKnownAnimationFrame;
    }

}