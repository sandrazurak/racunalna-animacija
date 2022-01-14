package com.example.lab.vježba1;

import java.lang.Math;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.io.*;
import java.nio.file.Path;
import java.io.File;
import java.util.concurrent.TimeUnit;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.fixedfunc.GLMatrixFunc;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.util.FPSAnimator;

import javax.swing.*;

public class Lab1 {
    static {
        GLProfile.initSingleton();
    }
    public static void main(String[] args) {
        GLU glu = new GLU();

        SwingUtilities.invokeLater(new Runnable() {
            double sx = 0.0;
            double sy = 1.0;
            double sz = 0.0;

            double os_x = 0.0;
            double os_y = 0.0;
            double os_z = 0.0;
            final int[] brS = {0};
            int brF = 0;
            final int[] brB = {0};
            int brV = 0;
            int broj_svih = 0;
            int broj_tan = 0;
            private int k = 0;
            int t = 0;
            int r = 0;

            double srediste_x = 0.0;
            double srediste_y = 0.0;
            double srediste_z = 0.0;

            @Override
            public void run() {
                final GLProfile glprofile = GLProfile.getDefault();
                GLCapabilities glCapabilities = new GLCapabilities(glprofile);
                final GLCanvas glcanvas = new GLCanvas(glCapabilities);
                final ArrayList<Double> v1 = new ArrayList<>();
                final ArrayList<Double> v2 = new ArrayList<>();
                final ArrayList<Double> v3 = new ArrayList<>();

                final ArrayList<Double> v111 = new ArrayList<>();
                final ArrayList<Double> v222 = new ArrayList<>();
                final ArrayList<Double> v333 = new ArrayList<>();
                final ArrayList<Integer> f1 = new ArrayList<>();
                final ArrayList<Integer> f2 = new ArrayList<>();
                final ArrayList<Integer> f3 = new ArrayList<>();

                final ArrayList<Integer> k1 = new ArrayList<>();
                final ArrayList<Integer> k2 = new ArrayList<>();
                final ArrayList<Integer> k3 = new ArrayList<>();

                final ArrayList<Double> t1 = new ArrayList<>();
                final ArrayList<Double> t2 = new ArrayList<>();
                final ArrayList<Double> t3 = new ArrayList<>();

                final ArrayList<Integer> glediste = new ArrayList<>();
                glediste.add(0);
                glediste.add(0);
                glediste.add(0);
                final ArrayList<Integer> ociste = new ArrayList<>();

                Path p = Paths.get("C:\\Users\\Sandra\\Desktop\\kocka.obj.txt");
                File file = p.toFile();
                try{
                    BufferedReader br = new BufferedReader(new FileReader(file));
                    String st;
                    while ((st = br.readLine()) != null) {

                        if(st.split(" ")[0].equals("v")){
                            v1.add(Double.valueOf(st.split(" ")[1]));
                            v2.add(Double.valueOf(st.split(" ")[2]));
                            v3.add(Double.valueOf(st.split(" ")[3]));
                        }
                        if(st.split(" ")[0].equals("f")){
                            f1.add(Integer.valueOf(st.split(" ")[1]));
                            f2.add(Integer.valueOf(st.split(" ")[2]));
                            f3.add(Integer.valueOf(st.split(" ")[3]));
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                for(int g= 0; g< v1.size(); g++){
                    srediste_x += v1.get(g);
                    srediste_y += v2.get(g);
                    srediste_z += v3.get(g);
                }
                srediste_x = srediste_x/v1.size();
                srediste_y = srediste_y/v1.size();
                srediste_z = srediste_z/v1.size();
                Path p1 = Paths.get("C:\\Users\\Sandra\\Desktop\\krivulja.txt");
                File file1 = p1.toFile();
                try{
                    BufferedReader br = new BufferedReader(new FileReader(file1));
                    String st;
                    while ((st = br.readLine()) != null) {

                        if(st.split(" ")[0].equals("v")){
                            k1.add(Integer.valueOf(st.split(" ")[1]));
                            k2.add(Integer.valueOf(st.split(" ")[2]));
                            k3.add(Integer.valueOf(st.split(" ")[3]));
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                brB[0] = k1.size();
                brS[0] = brB[0] -3;
                Path p2 = Paths.get("C:\\Users\\Sandra\\Desktop\\krivulja.txt");
                File file2 = p2.toFile();
                try {
                    BufferedReader br = new BufferedReader(new FileReader(file2));
                    String st;
                    while ((st = br.readLine()) != null) {
                        if (st.split(" ")[0].equals("v")) {
                            t1.add(Double.valueOf(st.split(" ")[1]));
                            t2.add(Double.valueOf(st.split(" ")[2]));
                            t3.add(Double.valueOf(st.split(" ")[3]));
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }


                glcanvas.addGLEventListener(new GLEventListener() {

                    @Override
                    public void init(GLAutoDrawable glAutoDrawable) {
                    }

                    @Override
                    public void dispose(GLAutoDrawable glAutoDrawable) {
                    }


                    @Override
                    public void display(GLAutoDrawable glautodrawable) {
                        int sum_k_B = 12;
                        int sum_k_S = sum_k_B - 3;

                        final ArrayList<Double> b_spline_x = new ArrayList<>();
                        final ArrayList<Double> b_spline_y = new ArrayList<>();
                        final ArrayList<Double> b_spline_z = new ArrayList<>();

                        final ArrayList<Double> tanb_x = new ArrayList<>();
                        final ArrayList<Double> tanb_y = new ArrayList<>();
                        final ArrayList<Double> tanb_z = new ArrayList<>();

                        final ArrayList<Double> sve_x = new ArrayList<>();
                        final ArrayList<Double> sve_y = new ArrayList<>();
                        final ArrayList<Double> sve_z = new ArrayList<>();
                        broj_svih = 0;
                        broj_tan = 0;
                        for(int i = 0; i<sum_k_S; i++) {
                            final ArrayList<Integer> r11 = new ArrayList<>();
                            final ArrayList<Integer> r22 = new ArrayList<>();
                            final ArrayList<Integer> r33 = new ArrayList<>();

                            r11.add(k1.get(i));
                            r11.add(k1.get(i + 1));
                            r11.add(k1.get(i + 2));
                            r11.add(k1.get(i + 3));

                            r22.add(k2.get(i));
                            r22.add(k2.get(i + 1));
                            r22.add(k2.get(i + 2));
                            r22.add(k2.get(i + 3));

                            r33.add(k3.get(i));
                            r33.add(k3.get(i + 1));
                            r33.add(k3.get(i + 2));
                            r33.add(k3.get(i + 3));

                            for (int t = 0; t < 100; t++) {

                                double j = t / 100.0;
                                double f11 = (-Math.pow(j, 3.0) + 3 * Math.pow(j, 2.0) - 3 * j + 1) / 6;
                                double f22 = (3 * Math.pow(j, 3.0) - 6 * Math.pow(j, 2.0) + 4) / 6;
                                double f33 = (-3 * Math.pow(j, 3.0) + 3 * Math.pow(j, 2.0) + 3 * j + 1) / 6;
                                double f44 = (Math.pow(j, 3.0)) / 6;

                                b_spline_x.add(f11 * r11.get(0) + f22 * r11.get(1) + f33 * r11.get(2) + f44 * r11.get(3));
                                b_spline_y.add(f11 * r22.get(0) + f22 * r22.get(1) + f33 * r22.get(2) + f44 * r22.get(3));
                                b_spline_z.add(f11 * r33.get(0) + f22 * r33.get(1) + f33 * r33.get(2) + f44 * r33.get(3));

                                double tt1 = 0.5*(-Math.pow(j, 2.0) +2*j -1);
                                double tt2 = 0.5*(3*Math.pow(j, 2.0) - 4*j);
                                double tt3 = 0.5*(-3* Math.pow(j, 2.0) +2*j +1);
                                double tt4 = 0.5*(Math.pow(j, 2.0));

                                tanb_x.add(tt1 * r11.get(0) + tt2 * r11.get(1) + tt3 * r11.get(2) + tt4 * r11.get(3));
                                tanb_y.add(tt1 * r22.get(0) + tt2 * r22.get(1) + tt3 * r22.get(2) + tt4 * r22.get(3));
                                tanb_z.add(tt1 * r33.get(0) + tt2 * r33.get(1) + tt3 * r33.get(2) + tt4 * r33.get(3));

                            }

                        }


                        final GL2 gl = glautodrawable.getGL().getGL2();
                        gl.glLoadIdentity();
                        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

                        //DODANO
                        gl.glMatrixMode(GL2.GL_PROJECTION);
                        gl.glLoadIdentity();
                        GLU glu = new GLU();
                        glu.gluPerspective(90, 1, 1, 100);
                        gl.glMatrixMode(GL2.GL_MODELVIEW);
                        gl.glLoadIdentity();
                        gl.glTranslatef(0, 0,-80);
                        gl.glRotatef(45, 0,0,1);


                        gl.glBegin( GL2.GL_LINES );
                        for(int i=0; i<(900-1); i++){
                            gl.glVertex3d(b_spline_x.get(i), b_spline_y.get(i), b_spline_z.get(i));
                            gl.glVertex3d(b_spline_x.get(i+1), b_spline_y.get(i+1), b_spline_z.get(i+1));

                        }
                        double pomakx = b_spline_x.get(r) - srediste_x;
                        double pomaky = b_spline_y.get(r) - srediste_y;
                        double pomakz = b_spline_z.get(r) - srediste_z;

                        double ex = tanb_x.get(r);
                        double ey = tanb_y.get(r);
                        double ez = tanb_z.get(r);

                        double os_x = sy*ez - ey*sz;
                        double os_y = ex*sz - sx*ez;
                        double os_z = sx*ey - sy*ex;
                        double apsS = Math.pow(Math.pow(sx, 2.0) + Math.pow(sy, 2.0) + Math.pow(sz, 2.0), 0.5);
                        double apsE = Math.pow(Math.pow(ex, 2.0) + Math.pow(ey, 2.0) + Math.pow(ez, 2.0), 0.5);
                        double se = sx*ex + sy*ey + sz*ez;
                        double kut = Math.acos(se/(apsS*apsE));
                        kut = kut/(2*Math.PI) * 360;
                        gl.glBegin(GL.GL_LINES);
                        gl.glRotated(kut,os_x, os_y, os_z);
                        gl.glPushMatrix();
                        for(int i=0; i<f1.size(); i++) {
                            double v1x = v1.get(f1.get(i) - 1) + pomakx;
                            double v1y = v2.get(f1.get(i) - 1) + pomaky;
                            double v1z = v3.get(f1.get(i) - 1) + pomakz;

                            double v2x = v1.get(f2.get(i) - 1) + pomakx;
                            double v2y = v2.get(f2.get(i) - 1) + pomaky;
                            double v2z = v3.get(f2.get(i) - 1) + pomakz;

                            double v3x = v1.get(f3.get(i) - 1) + pomakx;
                            double v3y = v2.get(f3.get(i) - 1) + pomaky;
                            double v3z = v3.get(f3.get(i) - 1) + pomakz;

                            gl.glVertex3d(v1x, v1y, v1z);
                            gl.glVertex3d(v2x, v2y, v2z);
                            gl.glVertex3d(v3x, v3y, v3z);

                        }

                        gl.glEnd();
                        gl.glPopMatrix();
                        gl.glFlush();

                        r++;


                    }

                    @Override
                    public void reshape(GLAutoDrawable glautodrawable, int x, int y, int width, int height) {

                        GL2 gl2 = glautodrawable.getGL().getGL2();
                        gl2.glMatrixMode(GL2.GL_PROJECTION);
                        gl2.glLoadIdentity();

                        GLU glu = new GLU();
                        glu.gluOrtho2D(0.0f, width, 0.0f, height);

                        gl2.glMatrixMode(GL2.GL_MODELVIEW);
                        gl2.glLoadIdentity();

                        gl2.glViewport(0, 0, width, height);
                    }
                });

                final GLProfile profile = GLProfile.get( GLProfile.GL2 );
                GLCapabilities capabilities = new GLCapabilities( profile );
                // The canvas
                glcanvas.setSize( 400, 400 );
                //creating frame
                Lab1 lab1 = new Lab1();
                final JFrame frame = new JFrame (" 3d line");
                //adding canvas to it
                frame.getContentPane().add( glcanvas );
                frame.setSize( frame.getContentPane().getPreferredSize() );
                frame.setVisible( true );

                for(int r=0; r<899; r++) {
                    try {
                        TimeUnit.MILLISECONDS.sleep(1);
                        glcanvas.display();

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
    }
}
