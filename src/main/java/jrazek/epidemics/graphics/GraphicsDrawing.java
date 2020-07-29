package jrazek.epidemics.graphics;

import jrazek.epidemics.Map;
import jrazek.epidemics.Person;
import jrazek.epidemics.Main;

import javax.swing.*;
import java.awt.*;

public class GraphicsDrawing extends JPanel{
    private Map map;
    private boolean isLinux;
    GraphicsDrawing(){
        this.map = Main.map;
        if(System.getProperty("os.name").equalsIgnoreCase("linux"))
            isLinux = true;
    }
    public void paintComponent(Graphics gp){
        if(isLinux)
            Toolkit.getDefaultToolkit().sync();
        Graphics2D g2d = (Graphics2D) gp;
        g2d.setStroke(new BasicStroke(2));
        super.paintComponents(gp);
        for(Person p : Main.map.getPeople()) {
            if (p.getSickness() == null) {
                if(p.getAge() < map.getEnvironmentRestrictions().getMinaReproduceAge()) {
                    g2d.setColor(Color.pink);
                    //    System.out.println("young one");
                }
                else {
                    int rgb = 255/(p.getAge() / map.getEnvironmentRestrictions().getYearLength());
                    if(rgb > 255)
                        rgb = 255;
                    g2d.setColor(new Color(0, rgb, 0));
                }
            } else {
                Color cl = p.getSickness().getEpidemy().getSicknessColor();
                if (cl != null) {
                    g2d.setColor(cl);
                }
            }
            if (p.isDead()) {
                g2d.setColor(Color.WHITE);
            }
            //x, y, width, height

            g2d.fillOval(p.getPos().getX(), p.getPos().getY(), 10, 10);
            //  g2d.fillOval(100, 100, 200,100);
        }
    }

}