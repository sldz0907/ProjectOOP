package Physic;
import Mainpackage.Game;
import Physic.MyShape2D.MyRectangle;


public class EntititesCollision {
    public static boolean CanMoveHere(float x, float y, float width, float height, int[][] lvlData) {
		if (!IsSolid(x, y, lvlData))
			if (!IsSolid(x + width, y + height, lvlData))
				if (!IsSolid(x + width, y, lvlData))
					if (!IsSolid(x, y + height, lvlData))
					     if (!IsSolid(x, y + height/2, lvlData))
						     if (!IsSolid(x + width, y + height/2, lvlData))
						return true;
		return false;
	}
	private static boolean IsSolid(float x, float y, int[][] lvlData) {
		int maxWidth = lvlData[0].length * Game.TILE_WIDTH_SIZE;
		int maxHeight = lvlData.length * Game.TILE_HEIGHT_SIZE;
		if (x < 0 || x >= maxWidth)
			return true;
		if (y < 0 || y >= maxHeight)
			return true;
		float xIndex = x / Game.TILE_WIDTH_SIZE;
		float yIndex = y / Game.TILE_HEIGHT_SIZE;

		int value = lvlData[(int) yIndex][(int) xIndex];

		int[] goThrough = {0, 1, 16, 31, 46, 61, 8, 23, 38, 53, 68, 65, 72};
		for (int i : goThrough) {
			if (i == value) return false;
		}
		return true;

		// if (value != 0 && value != 1 && value != 16 && value != 31 && value != 46 && value != 8 && value != 23&& value != 38&& value != 53)
		// 	return true;
		// return false;
	}
	public static boolean IsTileSolid(int xTile, int yTile, int[][] lvData) {
		int value = lvData[(int) yTile][(int) xTile];
		if (value != 0 && value != 1 && value != 16 && value != 31 && value != 46 && value != 8 && value != 23 && value != 38 && value != 53)
			return true;
		return false;
	}
	public static float GetEntityYPosUnderRoofOrAboveFloor(MyRectangle hitbox, float airSpeed)
	{
		int currentTile = (int)(hitbox.y1 / Game.TILE_HEIGHT_SIZE) ;
		if(airSpeed > 0)
		{
			int tileYPos = currentTile*Game.TILE_HEIGHT_SIZE;
			int yOffset = (int)(2*Game.TILE_HEIGHT_SIZE - hitbox.height);
			return tileYPos + yOffset ;
		}else{
			return currentTile * Game.TILE_HEIGHT_SIZE;
		}
	}

	public static boolean IsEntityOnFloor(MyRectangle hitbox, int[][] lvlData){
		if(!IsSolid(hitbox.x1,hitbox.y1 + hitbox.height + 1 , lvlData))
		{
			if(!IsSolid(hitbox.x1 + hitbox.width, hitbox.y1 + hitbox.height + 1, lvlData))
			{
                 return false;
			}
		}
		return true;
	}

	public static boolean IsFloor(MyRectangle hitbox, float xSpeed, int[][] lvlData) {
		if (xSpeed > 0)
			return IsSolid(hitbox.x1 + hitbox.width + xSpeed, hitbox.y1 + hitbox.height + 1, lvlData);
		else
			return IsSolid(hitbox.x1 + xSpeed, hitbox.y1 + hitbox.height + 1, lvlData);
	}

	public static boolean IsAllTileWalkable(int xStart, int xEnd, int y, int[][] lvData) {
		for (int i = 0; i <= xEnd - xStart; i++) {
			int currentX = xStart + i;
	
			// Kiểm tra out-of-bounds
			if (currentX < 0 || currentX >= lvData[0].length || y < 0 || y + 1 >= lvData.length) {
				return false; // Ngoài phạm vi, không thể đi qua
			}
	
			// Kiểm tra nếu ô gạch tại hàng y hoặc y+1 là "rắn"
			if (IsTileSolid(currentX, y, lvData)) {
				return false;
			}
		}
		return true;
	}
	

	public static boolean IsSightClear(int[][] lvData, MyRectangle firstHitbox, MyRectangle secondHitbox, int yTile) {
		int firstXTile = (int)(firstHitbox.x1/Game.TILE_WIDTH_SIZE);
		int secondXTile = (int)(secondHitbox.x1/Game.TILE_WIDTH_SIZE);
		if(firstXTile < secondXTile)
			return IsAllTileWalkable(firstXTile, secondXTile, yTile, lvData);
		else
			return IsAllTileWalkable(secondXTile, firstXTile, yTile, lvData);
	}
}