
public class vertices 
{
	boolean[] verticesRiverActive = new boolean[6];
	int[][] riverID;
	public vertices()
	{
		riverID = new int[6][2];
		for (int i = 0; i < 6; i ++)
		{
			verticesRiverActive[i] = false;
			riverID[i][0] = - 1;
			riverID[i][1] = - i;
		}
		
	}
	public boolean isAdjacentToRiver()
    {
        return (verticesRiverActive[0] || verticesRiverActive[1] || verticesRiverActive[2] || verticesRiverActive[3] || verticesRiverActive[4] || verticesRiverActive[5]);
    }
}
