package application;
// 文件名:MuiscPlay.java
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import sun.audio.*;
/**
*
* @author wuhuiwen
* 播放音频文件，产生音效
*/
public class MusicPlay {
    private AudioStream  as; //单次播放声音用
    ContinuousAudioDataStream cas;//循环播放声音
    // 构造函数
    public MusicPlay(URL url)
    {
        try {
            //打开一个声音文件流作为输入
            as = new AudioStream (url.openStream());
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    // 一次播放 开始
    public void start()
    {
        if( as==null ){
            System.out.println("AudioStream object is not created!");
            return;
        }else{
            AudioPlayer.player.start (as);
        }
    }
    // 一次播放 停止
    public void stop()
    {
        if( as==null ){
            System.out.println("AudioStream object is not created!");
            return;
        }else{
            AudioPlayer.player.stop(as);
        }
    }
    // 循环播放 开始
    public void continuousStart()
    {
        // Create AudioData source.
        AudioData data = null;
        try {
            data = as.getData();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // Create ContinuousAudioDataStream.
        cas = new ContinuousAudioDataStream (data);

        // Play audio.
        AudioPlayer.player.start(cas);
    }
    // 循环播放 停止
    public void continuousStop()
    {
        if(cas != null)
        {
            AudioPlayer.player.stop (cas);
        }
    }

//    public static void main(String[] args)
    public static void playOnce(String musicName) throws ParserConfigurationException, SAXException, IOException
    {
    	PropertiesDAO pdao = new PropertiesDAO();
    	if(pdao.readMusicSwich()){
    		String path = System.getProperty("user.dir")+"\\Music\\"+ musicName;
//        	System.out.println(path);
            URL url = null;
            try {
                url = new URL("file:///"+ path);
            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            MusicPlay musicPlay=new MusicPlay(url);
            musicPlay.start();
             try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            musicPlay.start();
    	}

    }
}