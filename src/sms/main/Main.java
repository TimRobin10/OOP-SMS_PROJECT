package sms.main;

import javafx.application.Application;
import sms.LoginPage.LoginPage;
import resources.fonts.FontLoader;


public class Main{


    public static void main(String[] args){

        FontLoader.loadFonts();
        Application.launch(LoginPage.class, args);
    }
}