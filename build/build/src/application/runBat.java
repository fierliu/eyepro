package application;

import java.io.IOException;

public class runBat {

    public void run_bat(String batName) {

            Process ps;
            try {
                ps = Runtime.getRuntime().exec(batName);
                ps.waitFor();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        System.out.println("finish");
    }

}