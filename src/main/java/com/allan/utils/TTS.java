package com.allan.utils;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

/**
 * jacob-xxx-x32.dll要放在C:\Windows\System32
 * jacob-xxx-x64.dll要放在C:\Windows\SystemWoW64
 * 再将这两个放在Java\jdk1.8.0_191\bin
 *否则报错：Exception in thread "JavaFX Application Thread" java.lang.UnsatisfiedLinkError:
 *  no jacob-1.18-x64 in java.library.path
 * */
public class TTS {

	/**
     * 调用Window系统语音
     * */
	public static void playSpVoiceTTS(String s){
		ActiveXComponent ax = null;

        ax = new ActiveXComponent("Sapi.SpVoice");
        Dispatch spVoice = ax.getObject();

        ax = new ActiveXComponent("Sapi.SpFileStream");
        Dispatch spFileStream = ax.getObject();

        ax = new ActiveXComponent("Sapi.SpAudioFormat");
        Dispatch spAudioFormat = ax.getObject();

        //设置音量 0到100
        Dispatch.put(spVoice, "Volume", new Variant(100));
        //设置朗读速度
        Dispatch.put(spVoice, "Rate", new Variant(0));
        //开始朗读
        Dispatch.call(spVoice, "Speak", new Variant(s));

        //关闭输出文件

        Dispatch.putRef(spVoice, "AudioOutputStream", null);

        spAudioFormat.safeRelease();
        spFileStream.safeRelease();
        spVoice.safeRelease();
        ax.safeRelease();

	}

	/**
     * 使用freetts语音，仅限英文
     */
	public static void playFreeTTS(String s){
        VoiceManager vm = VoiceManager.getInstance();
        Voice voice = vm.getVoice("kevin16");

        voice.allocate();

        voice.speak(s);
    }

    public static void main(String[] args) throws Exception {
//        playSpVoiceTTS("It's "+ GetTime.getSharpAndHalfTime()+" now");
        playSpVoiceTTS("It's "+ "19 o'clock" +" now");
//        System.out.println("GetTime.getSharpAndHalfTime() = " + GetTime.getSharpAndHalfTime());
        String substring = "19:00".substring(2);
        System.out.println("substring = " + "19:00".substring(0,2));
    }

}

