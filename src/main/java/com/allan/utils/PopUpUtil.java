package com.allan.utils;

import com.allan.dao.PropertiesDAO;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

public class PopUpUtil {

    private static PropertiesDAO pdao = PropertiesDAO.getInstance();

    public PopUpUtil(){
        System.out.println("primaryScreenBounds = " );
    }

    public static double getX(){
        String position = pdao.readPopUpPosition();
        System.out.println("position = " + position);
        String size = pdao.readPopUpSize();
//        return primaryScreenBounds.getMinX() + primaryScreenBounds.getWidth()/2 - 180;
        return 0;
    }

    public static double getY(){
//        return primaryScreenBounds.getMinY() + primaryScreenBounds.getHeight()/2 - 130;
        return 0;
    }
}
