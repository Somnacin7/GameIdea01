package entity.player;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

import map.TileMap;
import utility.Keys;
import entity.Entity;
import entity.objects.Collidable;

public class Player extends Entity
{
	private Keys keys;

	// Position
	private double x, y;

	private boolean facingRight;
	
	private boolean isDead;

	// Velocity
	private double dx, dy;

	private TileMap tileMap;

	// Which state to delegate to
	private PlayerState currentState;

	// These should apply in every state (I think)
	private final double GRAVITY_ACCEL = .2;
	private final double GRAVITY_MAX_VELOCITY = 5;

	// Collision checking
	private Rectangle boundingBox;
	private final int H_OFFSET = 9;
	private final int V_OFFSET = 4;
	private final int BOX_WIDTH = 13;
	private final int BOX_HEIGHT = 26;
	private Rectangle[][] surroundingTiles; // Holds whether or not the surrounding tiles collide
	private ArrayList<Collidable> collisions;

	private boolean hasTeleported;

	public static final PlayerState STANDING = new PlayerStateStanding();
	public static final PlayerState JUMPING = new PlayerStateJumping();
	public static final PlayerState FALLING = new PlayerStateFalling();
	public static final PlayerState TELEPORTING = new PlayerStateTeleporting();
	public static final PlayerState DEAD = new PlayerStateDead();

	// public static final PlayerState CROUCHING = new PlayerStateCrouching();

	public Player(TileMap tileMap, Keys keys)
	{
		this.tileMap = tileMap;
		this.keys = keys;
		init();
	}

	public void init()
	{
		currentState = STANDING;
		surroundingTiles = new Rectangle[3][3];
		isDead = false;
	}

	public void processInput(Keys keys)
	{
		currentState.processInput(this, keys);
	}

	public void update()
	{

		// applyGravity();

		currentState.update(this);

		// incrementx();

		// checkXCollision();

		// incrementy();

		// checkYCollision();

		System.out.println(currentState.getClass().getSimpleName());
	}

	public void checkYCollision()
	{
		boundingBox = new Rectangle((int) x + H_OFFSET, (int) y + V_OFFSET, BOX_WIDTH, BOX_HEIGHT);

		int xcol = (int) x / tileMap.getTileSize();
		int yrow = (int) y / tileMap.getTileSize();

		for (int r = yrow - 1; r < 4; r++)
		{
			for (int c = xcol - 1; c < 4; c++)
			{
				// Continue if out of bounds
				if ((r < 0 || c < 0) || (r >= surroundingTiles.length || c >= surroundingTiles[0].length))
				{
					continue;
				}

				if (tileMap.getTileCollision(r, c))
				{
					surroundingTiles[r][c] = new Rectangle(c * tileMap.getTileSize(), r * tileMap.getTileSize(), tileMap.getTileSize(), tileMap.getTileSize());
				}
				else
					surroundingTiles[r][c] = new Rectangle(0, 0, 0, 0); // Pretty much a null rectangle
			}
		}

		for (int c = 0; c < 3; c++)
		{
			if (boundingBox.intersects(surroundingTiles[0][c]))
			{
				y = surroundingTiles[0][c].getY() + tileMap.getTileSize() - V_OFFSET;
				dy = 0;
			}
		}

		for (int c = 0; c < 3; c++)
		{
			if (boundingBox.intersects(surroundingTiles[2][c]))
			{
				y = surroundingTiles[2][c].getY() - tileMap.getTileSize() * 2 + 2;
				dy = 0;
			}
		}
	}

	public void incrementy()
	{
		y += dy;
	}

	public void checkXCollision()
	{
		boundingBox = new Rectangle((int) x + H_OFFSET, (int) y + V_OFFSET, BOX_WIDTH, BOX_HEIGHT);

		int xcol = (int) (boundingBox.getCenterX() / tileMap.getTileSize());
		int yrow = (int) (boundingBox.getCenterY() / tileMap.getTileSize());

		int row = 0, col = 0;
		for (int r = yrow - 1; r < yrow - 1 + 3; r++)
		{
			for (int c = xcol - 1; c < xcol - 1 + 3; c++)
			{
				if (tileMap.getTileCollision(r, c))
				{
					surroundingTiles[row][col] = new Rectangle(c * tileMap.getTileSize(), r * tileMap.getTileSize(), tileMap.getTileSize(), tileMap.getTileSize());
				}
				else
					surroundingTiles[row][col] = new Rectangle(0, 0, 0, 0); // Pretty much a null rectangle

				col++;
			}
			row++;
			col = 0;
		}

		for (int r = 0; r < 3; r++)
		{
			if (boundingBox.intersects(surroundingTiles[r][0]))
			{
				x = surroundingTiles[r][0].getX() + tileMap.getTileSize() - H_OFFSET;
				dx = 0;
			}
		}

		for (int r = 0; r < 3; r++)
		{
			if (boundingBox.intersects(surroundingTiles[r][2]))
			{
				x = surroundingTiles[r][2].x - tileMap.getTileSize() * 2 + H_OFFSET + 1;
				dx = 0;
			}
		}
	}

	public void incrementx()
	{
		x += dx;
	}

	public void applyGravity()
	{
		if (dy < GRAVITY_MAX_VELOCITY)
			dy += GRAVITY_ACCEL;
	}

	public void draw(Graphics2D g)
	{
		g.drawImage(currentState.getImage(this), (int) (x + tileMap.getx()), (int) (y + tileMap.gety()), null);

		/*
		g.setColor(Color.RED);
		int xcol = (int) (boundingBox.getCenterX() / tileMap.getTileSize());
		int yrow = (int) (boundingBox.getCenterY() / tileMap.getTileSize());

		int row = 0, col = 0;
		for (int r = yrow - 1; r < yrow - 1 + 3; r++)
		{
			for (int c = xcol - 1; c < xcol - 1 + 3; c++)
			{
				if (tileMap.getTileCollision(r, c))
				{
					g.fillRect(c * tileMap.getTileSize() + (int) tileMap.getx(), r * tileMap.getTileSize() + (int) tileMap.gety(), tileMap.getTileSize(), tileMap.getTileSize());
				}

				col++;
			}
			row++;
			col = 0;
		}
		*/
	}

	public void setTilePosition(int x, int y)
	{
		this.x = x * tileMap.getTileSize();
		this.y = y * tileMap.getTileSize();
	}

	public double getx()
	{
		return x;
	}

	public double gety()
	{
		return y;
	}

	public void changeState(PlayerState state)
	{
		currentState.exit(this);
		currentState = state;

		// Check if player exits teleport inside a block. If so, kill that player
		int row = (int) (getBoundingBox().getCenterY()) / getTileMap().getTileSize();
		int col = (int) (getBoundingBox().getCenterX()) / getTileMap().getTileSize();

		if (getTileMap().getTileCollision(row, col))
		{
			setState(DEAD);
		}

		currentState.enter(this, keys);
	}

	public void setState(PlayerState state)
	{
		currentState = state;
	}

	public void setdx(double dx)
	{
		this.dx = dx;
	}

	public void setdy(double dy)
	{
		this.dy = dy;
	}

	public void incrementdx(double i)
	{
		dx += i;
	}

	public void incrementdy(double i)
	{
		dy += i;
	}

	public double getdx()
	{
		return dx;
	}

	public double getdy()
	{
		return dy;
	}

	public boolean getFacingRight()
	{
		return facingRight;
	}

	public void setFacingRight(boolean bool)
	{
		facingRight = bool;
	}

	public void setHasTeleported(boolean bool)
	{
		hasTeleported = bool;
	}

	public boolean getHasTeleported()
	{
		return hasTeleported;
	}

	public Rectangle getBoundingBox()
	{
		return boundingBox;
	}

	public TileMap getTileMap()
	{
		return tileMap;
	}
	
	public boolean getIsDead()
	{
		return isDead;
	}
	
	public void kill()
	{
		isDead = true;
	}
	
}