package com.allan.utils;

import com.allan.dao.ConfigDao;
import com.allan.domain.Config;
import com.allan.domain.PopUp;

public class PopUpUtil {

//    private ConfigDao pdao = new ConfigDao();
    private static Config config = new ConfigDao().findAll();

    public PopUpUtil(){
        System.out.println("primaryScreenBounds = " );
    }

    public static PopUp getPopUp(double minX, double minY, double screenWidth, double screenHeight){
        PopUp popUp = new PopUp();

        String size = config.getPopupSize();
        if("small".equals(size)){
            popUp.setWidth(screenWidth/9);
            popUp.setHeight(screenHeight/7.7);

        }
        PopUp popUpCmpl = PopUpUtil.getPopUpPosition(popUp, minX, minY, screenWidth, screenHeight);
        return popUpCmpl;
    }

    private static PopUp getPopUpPosition(PopUp popUp, double minX, double minY, double screenWidth, double screenHeight){
        String position = config.getPopupSize();
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
