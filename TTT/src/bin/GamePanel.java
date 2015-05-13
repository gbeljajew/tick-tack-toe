/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bin;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import static bin.AiTile.cm;
import java.util.Collections;

/**
 * this class will contain all you game logic and graphic<br/>
 * use it for smaller games
 * 
 * @author gbeljajew
 */
public class GamePanel extends JPanel
{
    public static final int width = 500;
    public static final int high = 300;
    public static final int player = 1;
    public static final int computer = 2;
    
    public static final double weightBlock = 3;
    public static final double weightWin = 5;
    //-----------------------------------
    
    
    JTextField winT = new JTextField();
    JTextField looseT = new JTextField();
    JTextField drawT = new JTextField();
    //-----------------------------------
    private final int[][] map = new int[3][3];
    
    private int win=0, loose=0, draw=0, nowPlay = computer, timer = 0;
    
    private ArrayList<AiTile> tiles, aiChose;
    
    
    
    public GamePanel() 
    {
        this.setPreferredSize(new Dimension(width, high));
        
        this.setLayout(null);
        
        JLabel winL = new JLabel("WINS");
        JLabel looseL = new JLabel("LOOSES");
        JLabel drawL = new JLabel("DRAWS");
        
        winL.setBounds(305, 5, 135, 20);
        this.add(winL);
        
        looseL.setBounds(305, 30, 135, 20);
        this.add(looseL);
        
        drawL.setBounds(305, 55, 135, 20);
        this.add(drawL);
        
        winT.setBounds(445, 5, 50, 20);
        winT.setEditable(false);
        winT.setText(String.valueOf(win));
        winT.setHorizontalAlignment(JTextField.RIGHT);
        this.add(winT);
        
        looseT.setBounds(445, 30, 50, 20);
        looseT.setEditable(false);
        looseT.setText(String.valueOf(loose));
        looseT.setHorizontalAlignment(JTextField.RIGHT);
        this.add(looseT);
        
        drawT.setBounds(445, 55, 50, 20);
        drawT.setEditable(false);
        drawT.setText(String.valueOf(draw));
        drawT.setHorizontalAlignment(JTextField.RIGHT);
        this.add(drawT);
        
        this.tiles = new ArrayList<>();
        this.aiChose = new ArrayList<>();
        
        for(int x = 0; x < 3; x++)
            for(int y = 0; y < 3; y++)
                tiles.add(new AiTile(x, y));
        
        
        this.addMouseListener(new MouseAdapter() 
        {

//            @Override
//            public void mouseClicked(MouseEvent e) {
//                int x = e.getX();
//                int y = e.getY();
//                
//                if(x<=300)
//                    set(x/100,y/100);
//            }

            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                
                if(nowPlay == player &&  x<=300)
                    set(x/100,y/100);
            }
            
            
            
        });
        
    }
    
    
    
    
    
    public void init()
    {
        for(int x = 0; x < 3; x++)
            for(int y = 0; y < 3; y++)
                map[x][y]=0;
        
        
        winT.setText(String.valueOf(win));
        looseT.setText(String.valueOf(loose));
        drawT.setText(String.valueOf(draw));
       
    }
    
    public void update()
    {
        if(nowPlay == computer)
        {
            timer--;
            
            if(timer <= 0)
                ai();
        }
    }

    @Override
    public void paint(Graphics g1) 
    {
        Graphics2D g = (Graphics2D)g1;
        
        g.clearRect(0, 0, width, high);
        super.paint(g1);
        
        g.drawLine(0, 100, 300-1, 100);
        g.drawLine(0, 200, 300-1, 200);
        g.drawLine(100, 0, 100, 300);
        g.drawLine(200, 0, 200, 300);
        g.drawLine(300, 0, 300, 300);
        
        Color c = g.getColor();
        Stroke st = g.getStroke();
        for(int x = 0; x < 3; x++)
            for(int y = 0; y < 3; y++)
            {
                if(map[x][y]==player)
                {
                    g.setColor(Color.blue);
                    g.setStroke(new BasicStroke(10.0F));
                    g.drawOval(x*100+10, y*100+10, 80, 80);
                }
                else if(map[x][y]==computer)
                {
                    g.setColor(Color.red);
                    g.setStroke(new BasicStroke(10.0F));
                    g.drawLine(x*100+10, y*100+10, x*100+90, y*100+90);
                    g.drawLine(x*100+10, y*100+90, x*100+90, y*100+10);
                }
                
            }
        g.setColor(c);
        g.setStroke(st);
        
        
        
    }
    
    private void isGameOver()
    {
        for(int i = 0; i < 3; i++)
        {
            if(map[i][0]==map[i][1] && map[i][0]==map[i][2])
                if(map[i][0]==player)
                {
                    JOptionPane.showMessageDialog(null, "Player WIN!");
                    
                    win++;
                    nowPlay = computer;
                    init();
                    return;
                }
                else if(map[i][0]==computer)
                {
                    
                    JOptionPane.showMessageDialog(null, "Computer WIN!");
                    loose++;
                    nowPlay = player;
                    init();
                    return;
                }
            
            if(map[0][i]==map[1][i] && map[0][i]==map[2][i])
                if(map[0][i]==player)
                {
                    
                    JOptionPane.showMessageDialog(null, "Player WIN!");
                    win++;
                    nowPlay = computer;
                    init();
                    return;
                }
                else if(map[0][i]==computer)
                {
                    
                    JOptionPane.showMessageDialog(null, "Computer WIN!");
                    loose++;
                    nowPlay = player;
                    init();
                    return;
                }
        }
        
        if(map[0][0]==map[1][1] && map[0][0]==map[2][2])
            if(map[0][0]==player)
            {
                
                JOptionPane.showMessageDialog(null, "Player WIN!");
                win++;
                nowPlay = computer;
                init();
                return;
            }
            else if(map[0][0]==computer)
            {
                
                JOptionPane.showMessageDialog(null, "Computer WIN!");
                loose++;
                nowPlay = player;
                init();
                return;
            }
        
        if(map[0][2]==map[1][1] && map[0][2]==map[2][0])
            if(map[0][2]==player)
            {
                
                JOptionPane.showMessageDialog(null, "Player WIN!");
                win++;
                nowPlay = computer;
                init();
                return;
            }
            else if(map[0][2]==computer)
            {
                
                JOptionPane.showMessageDialog(null, "Computer WIN!");
                loose++;
                nowPlay = player;
                init();
                return;
            }
        
        int s = 0;
        for(int x = 0; x < 3; x++)
            for(int y = 0; y < 3; y++)
                if(map[x][y]==0)s++;
        
        if(s==0)
        {
            JOptionPane.showMessageDialog(null, "DRAW");
            draw++;
            init();
        }
    }
    
    private void set(int x, int y)
    {
        if(x<0 || x>2 || y<0 || y>2)return;
        
        if(map[x][y]!=0)return;
        
        map[x][y]=nowPlay;
        
        this.repaint();
        
        isGameOver();
        
        if(nowPlay==player)
            nowPlay=computer;
        else
            nowPlay=player;
        
        timer = 25;
    }
    
    private void ai()
    {
        aiChose.clear();
        
        for(AiTile at: tiles)
        {
            if(isFree(at))
            {
                setWeight(at);
                aiChose.add(at);
            }
        }
        
        Collections.sort(aiChose);
        
        AiTile at = aiChose.get(0);
        
        set(at.getX(), at.getY());
    }
    
    private boolean isFree(AiTile at)
    {
        return map[at.getX()][at.getY()] == 0;
    }
    
    private void setWeight(AiTile at)
    {
        int x = at.getX();
        int y = at.getY();
        double weight=1;
        //-----horisontal---------------
        if(map[cm[x][0]][y]==map[cm[x][1]][y])
            if(map[cm[x][0]][y]==player)
                weight = weightBlock;
            else if(map[cm[x][0]][y]==computer)
                weight = weightWin;
        //-----vertikal--------------------
        if(map[x][cm[y][0]]==map[x][cm[y][1]])
            if(map[x][cm[y][0]]==player)
                if(weight<weightBlock)
                    weight = weightBlock;
            else if(map[x][cm[y][0]]==computer)
                weight = weightWin;
        //-----first-diagonal-----------------
        if(at.isFirstDiagonal())
            if(map[cm[x][0]][cm[y][0]]==map[cm[x][1]][cm[y][1]])
                if(map[cm[x][0]][cm[y][0]]==player)
                    if(weight<weightBlock)
                        weight = weightBlock;
                else if(map[cm[x][0]][cm[y][0]]==computer)
                    weight = weightWin;
        //-----second-diagonal----------------
        if(at.isSecondDiagonal())
            if(map[cm[x][0]][cm[y][1]]==map[cm[x][1]][cm[y][0]])
                if(map[cm[x][0]][cm[y][1]]==player)
                    if(weight<weightBlock)
                        weight = weightBlock;
                else if(map[cm[x][0]][cm[y][1]]==computer)
                    weight = weightWin;
                
        
        at.setWeight(weight);
        
    }
    
}
