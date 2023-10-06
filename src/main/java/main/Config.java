package main;

import java.io.*;

public class Config {
    GamePanel gp;

    public Config(GamePanel gp){
        this.gp = gp;
    }
    public void saveConfig(){
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("src/config"));

            //Full Screen
            if(gp.fullScreenOn == true){
                bw.write("On");
            }
            if(gp.fullScreenOn == false){
                bw.write("Off");
            }
            bw.newLine();

            // Music Volume
            bw.write(String.valueOf(gp.music.volumeScale));
            bw.newLine();

            //SE Volume
            bw.write(String.valueOf(gp.se.volumeScale));
            bw.newLine();

            bw.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    public void loadConfig(){

        try {
            BufferedReader br = new BufferedReader(new FileReader("src/config"));

            String s = br.readLine();

            // Full Screen
            if(s.equals("On")){
                gp.fullScreenOn = true;
            }
            if(s.equals("Off")){
                gp.fullScreenOn = false;
            }

            //Music volume
            s = br.readLine();
            gp.music.volumeScale = Integer.parseInt(s);

            //SE volume
            s = br.readLine();
            gp.se.volumeScale = Integer.parseInt(s);

            br.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
