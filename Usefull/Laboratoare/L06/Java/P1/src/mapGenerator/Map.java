package mapGenerator;

public interface Map extends Cloneable{

	public void printMap();
	public Coord getStartCoord();
	public Coord getFinishCoord();
	public void genMatrix(boolean random);
	public MapPosition[][] getMap();
	public MapPosition getCell(int x, int y);
	public int getWidth();
	public int getHeight();
	public Map clone();
	public void setCell(Coord myPosition, MapPosition me);

}


