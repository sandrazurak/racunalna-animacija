package com.example.lab.vježba2;
import java.awt.*;

public class Particle {

    private int fixed_life;
    private int scalar;
    private int x;
    private int y;
    private int dx;
    private int dy;
    private int size;
    private int life;
    private Color color;


    public Particle(int x, int y, int dx, int dy, int size, int life, Color c){
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        this.size = size;
        this.life = life;
        this.color = c;
        this.fixed_life = life;
        this.scalar = 255;
    }

    public boolean update(){
        x += Math.sin(x/600)*dx;
        y += Math.sin(x/600)*dx;
        life--;
        size = size - (size  - life*size/this.fixed_life);
        color = color.darker();
        if(life <= 0)
            return true;
        return false;
    }

    public void render(Graphics g){
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(this.color);
        g2d.fillOval(x-(size/2), y-(size/2), size, size);
        g2d.dispose();
    }


}
