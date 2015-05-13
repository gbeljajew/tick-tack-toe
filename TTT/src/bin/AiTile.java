/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bin;

/**
 *
 * @author gbeljajew
 */
public class AiTile implements Comparable<AiTile>
{
    
    public static final int[][] cm = {{1,2},{0,2},{0,1}};
    
    private final int x, y;
    
    private double weight = 0;
    private static double dif = 3.0;

    public AiTile(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setWeight(double weight) {
        this.weight = weight - dif/2 + Math.random()*dif;
    }
    
    public void resetWeight()
    {
        this.weight = 0.0;
    }

    public static void setDif(double dif) {
        AiTile.dif = dif;
    }
    
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public double getWeight() {
        return weight;
    }

    @Override
    public int compareTo(AiTile o) 
    {
        if(this.weight<o.weight)return 1;
        if(this.weight>o.weight)return -1;
        return 0;
    }
    
    public boolean isFirstDiagonal()
    {
        return x==y;
    }
    
    public boolean isSecondDiagonal()
    {
        return (x==0 && y==2) || (x==1 && y==1) || (x==2 && y==0);
    }
    
}
