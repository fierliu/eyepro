package com.allan.dao;

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

	private DocumentBuilderFactory dbf;
	private DocumentBuilder db;
	private Document doc;
	private Element root, ele;

	//singleton
	private static class InnerObject{
		private static PropertiesDAO propertiesDAO = new PropertiesDAO();
	}
	public static PropertiesDAO getInstance(){
		return InnerObject.propertiesDAO;
	}

	private PropertiesDAO(){
		dbf = DocumentBuilderFactory.newInstance();
		try {
			db = dbf.newDocumentBuilder();
			doc = db.parse("config.xml");
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		//这里表示src所在的根目录
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

//-----------------popUpPosition---------------------
public String readPopUpPosition(){
	return root.getElementsByTagName("popUpPosition").item(0).getTextContent();
}
	public void writePopUpPosition(String position) {
		root.getElementsByTagName("popUpPosition").item(0).setTextContent(position);
		saveXml("config.xml", doc);
	}

//-----------------popUpSize---------------------
public String readPopUpSize(){
	return root.getElementsByTagName("popUpSize").item(0).getTextContent();
}
	public void writePopUpSize(String size) {
		root.getElementsByTagName("popUpSize").item(0).setTextContent(size);
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
//---------------spvtts开关（英文）------------------------
	public Boolean readSpvTTSSwitch(){
		String swch = root.getElementsByTagName("SpvTTSSwitch").item(0).getTextContent();
//		System.out.print(swch);

        if(swch.equals("on")) {
        	return true;
        }
        else return false;
	}
	public void writeSpvTTSSwitch(String swth) {
		root.getElementsByTagName("SpvTTSSwitch").item(0).setTextContent(swth);
		saveXml("config.xml", doc);
	}
	//-----------spvtts开关（中文）-------------------------
	public Boolean readSpvTTSSwitchCh(){
		String swch = root.getElementsByTagName("SpvTTSSwitchCh").item(0).getTextContent();
//		System.out.print(swch);

		if(swch.equals("on")) {
			return true;
		}
		else return false;
	}
	public void writeSpvTTSSwitchCh(String swth) {
		root.getElementsByTagName("SpvTTSSwitchCh").item(0).setTextContent(swth);
		saveXml("config.xml", doc);
	}
	//---------------freetts开关------------------------
	public Boolean readFreeTTSSwitch(){
		String swch = root.getElementsByTagName("FreeTTSSwitch").item(0).getTextContent();
//		System.out.print(swch);

		if(swch.equals("on")) {
			return true;
		}
		else return false;
	}
	public void writeFreeTTSSwitch(String swth) {
		root.getElementsByTagName("FreeTTSSwitch").item(0).setTextContent(swth);
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
