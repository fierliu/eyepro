package application;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

public class ReadBreak {
	public Integer readBreak() {
		Properties properties = new Properties();
        try {
			properties.loadFromXML(new FileInputStream("config.xml"));
		} catch (InvalidPropertiesFormatException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        // 打印内存中的属性
//        System.out.println("breakTime:" + properties.getProperty("breakTime"));
        return Integer.parseInt(properties.getProperty("breakTime"));
	}

	public void writeBreak(String num) throws FileNotFoundException, IOException {
//		System.out.println("writed");
		// 同样先初始化Properties类
        // 初始化之后在内存就出现一个保存key-value对的properties对象
        Properties properties = new Properties();
        properties.setProperty("flag", readFlag().toString());
        properties.setProperty("breakTime", num);
        properties.storeToXML(new FileOutputStream("config.xml"), "注释");

	}
	public Integer readFlag(){
		Properties properties = new Properties();
        try {
			properties.loadFromXML(new FileInputStream("config.xml"));
		} catch (InvalidPropertiesFormatException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        // 打印内存中的属性
//        System.out.println("flag:"+properties.getProperty("flag"));
        return Integer.parseInt(properties.getProperty("flag"));
	}
	public void writeFlag(String numb){
        Properties properties = new Properties();
        properties.setProperty("breakTime", readBreak().toString());
        properties.setProperty("flag", numb);
        try {
			properties.storeToXML(new FileOutputStream("config.xml"), "注释");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
