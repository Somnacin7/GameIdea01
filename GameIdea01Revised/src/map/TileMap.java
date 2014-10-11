package map;

import game.GamePanel;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

public class TileMap
{
	// Position
	private double x, y;

	// Tween makes movement smoof
	private double tween;

	// Bounds
	private int xmin, xmax;
	private int ymin, ymax;

	// Amount of rows and cols
	private int numberOfRows, numberOfCols;

	// Width and height
	private int width, height;

	// Tile stuff
	private int tileSize;
	private BufferedImage tileSheet;
	private int numberOfTilesAcross; // number of tiles in a row of tileSheet
	private Tile[][] tiles;
	private Tile[][] tileMap;

	private int[][] collisionMap;

	// Make sure we only draw what we need to
	private int rowOffset, colOffset;
	private int numberOfRowsToDraw, numberOfColsToDraw;

	public TileMap(int tileSize)
	{
		this.tileSize = tileSize;

		numberOfRowsToDraw = GamePanel.HEIGHT / tileSize + 2; // The 2 ensures
																// we draw ahead
		numberOfColsToDraw = GamePanel.WIDTH / tileSize + 2; // of what we can
																// see

		tween = 1;// 0.07;
	}

	public void loadTiles(String path)
	{
		try
		{
			tileSheet = ImageIO.read(getClass().getResourceAsStream(path));
			numberOfTilesAcross = tileSheet.getWidth() / tileSize;
			tiles = new Tile[3][numberOfTilesAcross];

			for (int row = 0; row < 3; row++)
			{
				for (int col = 0; col < numberOfTilesAcross; col++)
				{
					tiles[row][col] = new Tile(tileSheet.getSubimage(col * tileSize, row * tileSize, tileSize, tileSize));
				}
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void loadCollisionMap(String path)
	{
		try
		{
			BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(path)));
			numberOfRows = Integer.parseInt(br.readLine());
			numberOfCols = Integer.parseInt(br.readLine());

			width = numberOfCols * tileSize;
			height = numberOfRows * tileSize;

			xmin = GamePanel.WIDTH - width;
			ymin = GamePanel.HEIGHT - height;
			xmax = 0;
			ymax = 0;

			collisionMap = new int[numberOfRows][numberOfCols];

			String[] tmp;
			for (int r = 0; r < numberOfRows; r++)
			{
				tmp = br.readLine().split("\\s+");
				for (int c = 0; c < numberOfCols; c++)
				{
					collisionMap[r][c] = Integer.parseInt(tmp[c]);
				}
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void loadTileMap(String path)
	{
		try
		{
			BufferedReader in = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(path)));
			tileMap = new Tile[numberOfRows][numberOfCols];

			String[] strtmp = new String[numberOfCols];

			for (int r = 0; r < numberOfRows; r++)
			{
				strtmp = in.readLine().split("\\s+"); // "\\s+" is the regex for
														// space (' ')

				for (int c = 0; c < numberOfCols; c++)
				{
					if (!strtmp[c].equals("X"))
					{
						switch (Integer.parseInt(strtmp[c], 16))
						{
							case 0:
								tileMap[r][c] = tiles[0][0];
								break;

							case 1:
								tileMap[r][c] = tiles[0][1];
								break;

							case 2:
								tileMap[r][c] = tiles[0][2];
								break;

							case 3:
								tileMap[r][c] = tiles[1][0];
								break;

							case 4:
								tileMap[r][c] = tiles[1][1];
								break;

							case 5:
								tileMap[r][c] = tiles[1][2];
								break;

							case 6:
								tileMap[r][c] = tiles[2][0];
								break;

							case 7:
								tileMap[r][c] = tiles[2][1];
								break;

							case 8:
								tileMap[r][c] = tiles[2][2];
								break;

							case 9:
								tileMap[r][c] = tiles[0][3];
								break;

							case 10:
								tileMap[r][c] = tiles[0][4];
								break;

							case 11:
								tileMap[r][c] = tiles[0][5];
								break;

							case 12:
								tileMap[r][c] = tiles[1][3];
								break;

							case 13:
								tileMap[r][c] = tiles[1][4];
								break;

							case 14:
								tileMap[r][c] = tiles[1][5];
								break;

							case 15:
								tileMap[r][c] = tiles[2][3];
								break;

							default:
								tileMap[r][c] = null;
						}
						
						tileMap[r][c].setType(collisionMap[r][c]);
					}

				}
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void fixBounds()
	{
		if (x < xmin)
			x = xmin;
		if (y < ymin)
			y = ymin;
		if (x > xmax)
			x = xmax;
		if (y > ymax)
			y = ymax;
	}

	public void setPosition(double x, double y)
	{
		this.x += (x - this.x) * tween;
		this.y += (y - this.y) * tween;

		fixBounds();

		colOffset = (int) -this.x / tileSize;
		rowOffset = (int) -this.y / tileSize;
	}

	public void update()
	{
	}

	public void draw(Graphics2D g)
	{
		for (int r = rowOffset; r < numberOfRowsToDraw + rowOffset; r++)
		{
			if (r >= numberOfRows) // Stop drawing if we run out of rows to draw
				break;

			for (int c = colOffset; c < numberOfColsToDraw + colOffset; c++)
			{

				if (c >= numberOfCols) // Stop drawing if we run out of cols
					break;

				if (tileMap[r][c] == null) // We don't need to draw empty space
					continue;

				g.drawImage(tileMap[r][c].getImage(), (int) x + (c * tileSize), (int) y + (r * tileSize), tileSize, tileSize, null);

			}
		}
	}

	public BufferedImage getTileImage(int r, int c)
	{
		return tiles[r][c].getImage();
	}

	public double getx()
	{
		return x;
	}

	public double gety()
	{
		return y;
	}

	public int getTileSize()
	{
		return tileSize;
	}
	
	public boolean getTileCollision(int r, int c)
	{
		// If it does not exist
		if (r >= collisionMap.length || c >= collisionMap[0].length || r < 0 || c < 0)
			return false;
			

		
		if (collisionMap[r][c] == 1)
			return true;
		else
			return false;
	}

}
