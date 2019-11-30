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

import com.allan.domain.Property;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class PropertiesDAO {

	private DocumentBuilderFactory dbf;
	private DocumentBuilder db;
	private Document doc;
	private Element root, ele;
	private Property property;

	//singleton
	private static class InnerObject{
		private static PropertiesDAO propertiesDAO = new PropertiesDAO();
	}
	public static PropertiesDAO getInstance(){
		return InnerObject.propertiesDAO;
	}

	public void load(){
		this.property = Property.getInstance();
//		this.property.setBreakTime();
		this.property.setDayCountDown(this.readDayCountDown());
		this.property.setFreeTTSSwitch(this.readFreeTTSSwitch());
		this.property.setMission(this.readMission());
		this.property.setMusicSwith(this.readMusicSwich());
		this.property.setNoticeWord(this.readNoticeWord());
		this.property.setPopUpSwitch(this.readPopUpSwitch());
		this.property.setSpvTTSSwitch(this.readSpvTTSSwitch());
		this.property.setSpvTTSSwitchCh(this.readSpvTTSSwitchCh());
	}

	public PropertiesDAO(){
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
        return swch.equals("on");
	}
	public void setPopUpSwitch(String swth) {
		root.getElementsByTagName("popUpSwitch").item(0).setTextContent(swth);
//		saveXml("config.xml", doc);
		this.property.setPopUpSwitch("on".equals(swth));
	}

//-----------------popUpPosition---------------------
public String readPopUpPosition(){
	return root.getElementsByTagName("popUpPosition").item(0).getTextContent();
}
	public void writePopUpPosition(String position) {
		root.getElementsByTagName("popUpPosition").item(0).setTextContent(position);
	}

//-----------------popUpSize---------------------
public String readPopUpSize(){
	return root.getElementsByTagName("popUpSize").item(0).getTextContent();
}
	public void writePopUpSize(String size) {
		root.getElementsByTagName("popUpSize").item(0).setTextContent(size);
	}

//-----------------声音开关-----------------------
	public Boolean readMusicSwich(){
		String swch = root.getElementsByTagName("musicSwith").item(0).getTextContent();
        return swch.equals("on");
	}

//---------------spvtts开关（英文）------------------------
	public Boolean readSpvTTSSwitch(){
		String swch = root.getElementsByTagName("SpvTTSSwitch").item(0).getTextContent();
		return swch.equals("on");
	}

	//-----------spvtts开关（中文）-------------------------
	public Boolean readSpvTTSSwitchCh(){
		String swch = root.getElementsByTagName("SpvTTSSwitchCh").item(0).getTextContent();
//		System.out.print(swch);
		return swch.equals("on");
	}

	//---------------freetts开关------------------------
	public Boolean readFreeTTSSwitch(){
		String swch = root.getElementsByTagName("FreeTTSSwitch").item(0).getTextContent();
		return swch.equals("on");
	}

//---------------提醒文字-------------------------
	public String readNoticeWord(){
		String word = root.getElementsByTagName("noticeWord").item(0).getTextContent();
		return word;
	}
	public void setNoticeWord(String word){
		root.getElementsByTagName("noticeWord").item(0).setTextContent(word);
		this.property.setNoticeWord(word);
	}
//-----------------dayCountDown--------------------
	public String readDayCountDown(){
		String days = root.getElementsByTagName("dayCountDown").item(0).getTextContent();
		return days;
	}
	public void setDayCountDown(String days){
		root.getElementsByTagName("dayCountDown").item(0).setTextContent(days);
		property.setDayCountDown(days);
	}
//-------------------mission-----------------------------
	public String readMission(){
		String days = root.getElementsByTagName("mission").item(0).getTextContent();
		return days;
	}
	public void setMission(String mission){
		root.getElementsByTagName("mission").item(0).setTextContent(mission);
		property.setMission(mission);
	}

	public void setSoundSwitch(int selectSoundOrdinal) {
		root.getElementsByTagName("musicSwith").item(0).setTextContent("off");
		root.getElementsByTagName("SpvTTSSwitch").item(0).setTextContent("off");
		root.getElementsByTagName("FreeTTSSwitch").item(0).setTextContent("off");
		root.getElementsByTagName("SpvTTSSwitchCh").item(0).setTextContent("off");
		root.getElementsByTagName("silence").item(0).setTextContent("off");
		property.setMusicSwith(false);
		property.setSpvTTSSwitch(false);
		property.setFreeTTSSwitch(false);
		property.setSpvTTSSwitchCh(false);
		property.setSilence(false);
		switch (selectSoundOrdinal){
			case 1:
				root.getElementsByTagName("musicSwith").item(0).setTextContent("on");
				property.setMusicSwith(true);
				break;
			case 2:
				root.getElementsByTagName("SpvTTSSwitch").item(0).setTextContent("on");
				property.setSpvTTSSwitch(true);
				break;
			case 3:
				root.getElementsByTagName("FreeTTSSwitch").item(0).setTextContent("on");
				property.setFreeTTSSwitch(true);
				break;
			case 4:
				root.getElementsByTagName("SpvTTSSwitchCh").item(0).setTextContent("on");
				property.setSpvTTSSwitchCh(true);
				break;
			case 5:
				root.getElementsByTagName("silence").item(0).setTextContent("on");
				property.setSilence(true);
				break;
		}
	}

    public void saveXml() {//将Document输出到文件
        TransformerFactory transFactory=TransformerFactory.newInstance();
        try {
            Transformer transformer = transFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT,"no");//若 设置为no，会在每次保存时都增加一行空格
            DOMSource source=new DOMSource();
            source.setNode(this.doc);
            StreamResult result=new StreamResult();
            result.setOutputStream(new FileOutputStream("config.xml"));

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
