package application;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class PropertiesDAO {

	DocumentBuilderFactory dbf;
	DocumentBuilder db;
	Document doc;
	Element root, ele;

	public PropertiesDAO() throws ParserConfigurationException, SAXException, IOException{
		dbf = DocumentBuilderFactory.newInstance();
		db = dbf.newDocumentBuilder();
		doc = db.parse("config.xml");
		root = doc.getDocumentElement();
//		System.out.print(root.getNodeName());
	}
//-----------------PopUp开关---------------------
	public Boolean readPopUpSwitch(){
		String swch = root.getElementsByTagName("popUpSwitch").item(0).getTextContent();
        if(swch.equals("on")) {
        	return true;
        }
        else return false;
	}
	public void writePopUpSwitch(String swth) {
		root.getElementsByTagName("popUpSwitch").item(0).setTextContent(swth);
		saveXml("config.xml", doc);
	}
//-----------------声音开关-----------------------
	public Boolean readMusicSwich(){
		String swch = root.getElementsByTagName("musicSwith").item(0).getTextContent();
//		System.out.print(swch);

        if(swch.equals("on")) {
        	return true;
        }
        else return false;
	}
	public void writeMusicSound(String swth) {
		root.getElementsByTagName("musicSwith").item(0).setTextContent(swth);
		saveXml("config.xml", doc);
	}
//---------------tts开关------------------------
	public Boolean readTTSSwitch(){
		String swch = root.getElementsByTagName("TTSSwitch").item(0).getTextContent();
//		System.out.print(swch);

        if(swch.equals("on")) {
        	return true;
        }
        else return false;
	}
	public void writeTTSSwitch(String swth) {
		root.getElementsByTagName("TTSSwitch").item(0).setTextContent(swth);
		saveXml("config.xml", doc);
	}
//---------------提醒文字-------------------------
	public String readNoticeWord(){
		String word = root.getElementsByTagName("noticeWord").item(0).getTextContent();
		return word;
	}
	public void writeNoticeWord(String word){
		root.getElementsByTagName("noticeWord").item(0).setTextContent(word);
		saveXml("config.xml", doc);
	}
//-----------------dayCountDown--------------------
	public String readDayCountDown(){
		String days = root.getElementsByTagName("dayCountDown").item(0).getTextContent();
		return days;
	}
	public void writeDayCountDown(String days){
		root.getElementsByTagName("dayCountDown").item(0).setTextContent(days);
		saveXml("config.xml", doc);
	}
//-------------------mission-----------------------------
	public String readMission(){
		String days = root.getElementsByTagName("mission").item(0).getTextContent();
		return days;
	}
	public void writeMission(String mission){
		root.getElementsByTagName("mission").item(0).setTextContent(mission);
		saveXml("config.xml", doc);
	}

    public static void saveXml(String fileName, Document doc) {//将Document输出到文件
        TransformerFactory transFactory=TransformerFactory.newInstance();
        try {
            Transformer transformer = transFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT,"no");//若 设置为no，会在每次保存时都增加一行空格
            DOMSource source=new DOMSource();
            source.setNode(doc);
            StreamResult result=new StreamResult();
            result.setOutputStream(new FileOutputStream(fileName));

            transformer.transform(source, result);
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
