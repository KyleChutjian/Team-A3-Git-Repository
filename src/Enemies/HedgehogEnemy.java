package Enemies;

import Builders.FrameBuilder;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.ImageEffect;
import GameObject.SpriteSheet;
import Level.Enemy;
import Level.Player;
import Utils.AirGroundState;
import Utils.Direction;
import Utils.Point;
import java.util.HashMap;

// This class is for the hedgehog enemy & is essentially a copy of the bug enemy class
public class HedgehogEnemy extends Enemy {

	private float gravity = .5f;
	private float movementSpeed = .5f;
	private Direction startFacingDirection;
	private Direction facingDirection;
	private AirGroundState airGroundState;

	public HedgehogEnemy(Point location, Direction facingDirection) {
		super(location.x, location.y, new SpriteSheet(ImageLoader.load("Hedgehog.png"), 44, 20), "WALK_LEFT");
		this.startFacingDirection = facingDirection;
		this.initialize();
	}

	@Override
	public void initialize() {
		super.initialize();
		facingDirection = startFacingDirection;
		if (facingDirection == Direction.RIGHT) {
			currentAnimationName = "WALK_RIGHT";
		} else if (facingDirection == Direction.LEFT) {
			currentAnimationName = "WALK_LEFT";
		}
		airGroundState = AirGroundState.GROUND;
	}

	@Override
	public void update(Player player) {
		float moveAmountX = 0;
		float moveAmountY = 0;

		// add gravity (if in air, this will cause hedgehog to fall)
		moveAmountY += gravity;

		// if on ground, walk forward based on facing direction
		if (airGroundState == AirGroundState.GROUND) {
			if (facingDirection == Direction.RIGHT) {
				moveAmountX += movementSpeed;
			} else {
				moveAmountX -= movementSpeed;
			}
		}

		// move hedgehog
		moveYHandleCollision(moveAmountY);
		moveXHandleCollision(moveAmountX);

		super.update(player);
	}

	@Override
	public void onEndCollisionCheckX(boolean hasCollided, Direction direction) {
		// if hedgehog has collided into something while walking forward,
		// it turns around (changes facing direction)
		if (hasCollided) {
			if (direction == Direction.RIGHT) {
				facingDirection = Direction.LEFT;
				currentAnimationName = "WALK_LEFT";
			} else {
				facingDirection = Direction.RIGHT;
				currentAnimationName = "WALK_RIGHT";
			}
		}
	}

	@Override
	public void onEndCollisionCheckY(boolean hasCollided, Direction direction) {
		// if hedgehog is colliding with the ground, change its air ground state to
		// GROUND
		// if it is not colliding with the ground, it means that it's currently in the
		// air, so its air ground state is changed to AIR
		if (direction == Direction.DOWN) {
			if (hasCollided) {
				airGroundState = AirGroundState.GROUND;
			} else {
				airGroundState = AirGroundState.AIR;
			}
		}
	}

	@Override
	public HashMap<String, Frame[]> getAnimations(SpriteSheet spriteSheet) {
		return new HashMap<String, Frame[]>() {
			{
				put("WALK_LEFT",
						new Frame[] {
								new FrameBuilder(spriteSheet.getSprite(0, 0), 100).withScale(2).withBounds(2, 2, 36, 16)
										.build(),
								new FrameBuilder(spriteSheet.getSprite(1, 0), 100).withScale(2).withBounds(2, 2, 36, 16)
										.build(),
								new FrameBuilder(spriteSheet.getSprite(2, 0), 100).withScale(2).withBounds(2, 2, 36, 16)
										.build(),
								new FrameBuilder(spriteSheet.getSprite(1, 0), 100).withScale(2).withBounds(2, 2, 36, 16)
										.build() });

				put("WALK_RIGHT", new Frame[] {
						new FrameBuilder(spriteSheet.getSprite(0, 0), 100).withScale(2)
								.withImageEffect(ImageEffect.FLIP_HORIZONTAL).withBounds(2, 2, 36, 16).build(),
						new FrameBuilder(spriteSheet.getSprite(1, 0), 100).withScale(2)
								.withImageEffect(ImageEffect.FLIP_HORIZONTAL).withBounds(2, 2, 36, 16).build(),
						new FrameBuilder(spriteSheet.getSprite(2, 0), 100).withScale(2)
								.withImageEffect(ImageEffect.FLIP_HORIZONTAL).withBounds(2, 2, 36, 16).build(),
						new FrameBuilder(spriteSheet.getSprite(1, 0), 100).withScale(2)
								.withImageEffect(ImageEffect.FLIP_HORIZONTAL).withBounds(2, 2, 36, 16).build() });
			}
		};
	}
}