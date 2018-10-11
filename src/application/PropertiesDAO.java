package application;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
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

    public static void saveXml(String fileName, Document doc) {//将Document输出到文件
        TransformerFactory transFactory=TransformerFactory.newInstance();
        try {
            Transformer transformer = transFactory.newTransformer();
            transformer.setOutputProperty("indent", "yes");
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
