package com.example.lab.vježba2;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;

public class Window extends JFrame {

    private ArrayList<Particle> particles = new ArrayList<Particle>(100);

    private int source_x = 300;
    private int source_y = 300;
    private BufferStrategy bufferstrat = null;
    private Canvas render;

    public static void main(String[] args) throws InterruptedException {
        Window window = new Window(600, 600, " ");
        Random r = new Random();
        int num_of_particles = r.nextInt(20) + 60;
        int j = 0;

        while(true){
            window.source_x = 0 + j;
            window.source_y = 0 + j;
            for (int i = 0; i < num_of_particles; i++) {
                window.addParticle();
            }
            for(int i = 0; i <= window.particles.size() - 1;i++){
                if(window.particles.get(i).update())
                    window.particles.remove(i);
            }            window.render();

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            j = j + 20;
        }

    }

    public void addParticle(){
        int dx,dy;
        Random r = new Random();
        int way = r.nextInt( 4);
        if(way == 1){
            dx = (int) (Math.random()*4);
            dy = (int) (Math.random()*4);
        }
        else if(way == 2) {
            dx = (int) (Math.random() * -3);
            dy = (int) (Math.random() * 5);
        }else if(way == 3){
            dx = (int) (Math.random() * 3);
            dy = (int) (Math.random() * -4);
        }else{
            dx = (int) (Math.random() * -4);
            dy = (int) (Math.random() * -5);
        }
        int size = (int) (Math.random()*50);
        int life = (int) Math.random()*(70)+500;

        particles.add(new Particle(source_x, source_y,dx,dy,size,life, Color.red));
    }


    public Window( int width, int height, String title){
        super();
        setTitle(title);
        setIgnoreRepaint(true);
        setResizable(false);

        render = new Canvas();
        render.setIgnoreRepaint(true);
        int nHeight = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        int nWidth = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        nHeight /= 2;
        nWidth /= 2;

        setBounds(nWidth-(width/2), nHeight-(height/2), width, height);
        render.setBounds(nWidth-(width/2), nHeight-(height/2), width, height);

        add(render);
        pack();
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        render.createBufferStrategy(2);
        bufferstrat = render.getBufferStrategy();
    }


    public void render(){
        do{
            do{
                Graphics2D g2d = (Graphics2D) bufferstrat.getDrawGraphics();
                g2d.fillRect(0, 0, render.getWidth(), render.getHeight());
                for(int i = 0; i <= particles.size() - 1;i++){
                    particles.get(i).render(g2d);
                }
                g2d.dispose();
            }while(bufferstrat.contentsRestored());
            bufferstrat.show();
        }while(bufferstrat.contentsLost());
    }


}