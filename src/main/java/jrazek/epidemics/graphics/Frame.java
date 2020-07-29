package jrazek.epidemics.graphics;

import javax.swing.*;

import jrazek.epidemics.Map;
import jrazek.epidemics.Person;
import jrazek.epidemics.Utilities;
import jrazek.epidemics.Main;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Frame extends JFrame implements ActionListener {
    private Map map;
    int fps;
    Timer timer;
    int time = 0;

    private String title;
    private int xS, yS;
    private boolean visible;
    private JFrame frame = new JFrame();
    private GraphicsDrawing gpD = new GraphicsDrawing();
    public Frame(Utilities.Vector2I mapSize, String title){
        this.xS = mapSize.getX();
        this.yS = mapSize.getY();
        this.title = title;
        this.setSize(this.xS, this.yS);
        this.setTitle(this.title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBackground(Color.GRAY);
        this.add(gpD);
        this.setVisible(true);
        map = Main.map;
        fps = map.getEnvironmentRestrictions().getFps();
        timer = new Timer(1000/fps, this);
        timer.start();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        time++;//ticks (30 per second)
        map.randomEvents();
        boolean deadRemoval = false;


        if(((float)time / (float)fps) % 1 == 0) {
            System.out.println(time/fps + " seconds passed");
        }

        if((float)time % map.getEnvironmentRestrictions().getDayLength() == 0) {
            map.nextDay();
        }
        if((float)time/map.getEnvironmentRestrictions().getDayLength()
                % map.getEnvironmentRestrictions().getDeadPersonRemovalPeriod() == 0){
            deadRemoval = true;
            System.out.println("removing dead people");
        }
        //old style loop to let myself add to a list during iteration
        for(int i = 0; i < map.getPeople().size(); i++){
            Person p = map.getPeople().get(i);
            p.move();
            p.reproduce();
            if(deadRemoval){
                if(p.isDead()){
                    map.getPeople().remove(p);
                    i--;
                }
            }
        }
        repaint();
    }
}