package com.allan.utils;

import com.allan.dao.PropertiesDAO;
import com.allan.domain.PopUp;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

public class PopUpUtil {

    private static PropertiesDAO pdao = PropertiesDAO.getInstance();

    public PopUpUtil(){
        System.out.println("primaryScreenBounds = " );
    }

    public static PopUp getPopUp(double minX, double minY, double screenWidth, double screenHeight){
        PopUp popUp = new PopUp();
        String size = pdao.readPopUpSize();
        if("small".equals(size)){
            popUp.setWidth(screenWidth/9);
            popUp.setHeight(screenHeight/7.7);

        }

        else if("middle".equals(size)){

            popUp.setWidth(screenWidth/4.5);
            popUp.setHeight(screenHeight/3.9);
        }

        else if ("large".equals(size)){
            popUp.setWidth(screenWidth/3);
            popUp.setHeight(screenHeight/2.6);
        }

        PopUp popUpCmpl = PopUpUtil.getPopUpPosition(popUp, minX, minY, screenWidth, screenHeight);
        return popUpCmpl;
    }

    private static PopUp getPopUpPosition(PopUp popUp, double minX, double minY, double screenWidth, double screenHeight){
        String position = pdao.readPopUpPosition();
        if ("middle".equals(position)){
            popUp.setX(minX + (screenWidth - 505)/2);
            popUp.setY(minY + (screenHeight - 366)/2);
        }

        else if ("leftBottom".equals(position)){
            popUp.setX(minX);
            popUp.setY(minY+ screenHeight - 366);
        }

        else if ("rightBottom".equals(position)){
            popUp.setX(minX + screenWidth - 505);
            popUp.setY(minY + screenHeight - 366);
        }

        return popUp;
    }

}
