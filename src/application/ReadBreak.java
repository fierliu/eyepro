package application;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class ReadBreak {

	SAXReader reader = new SAXReader();

	public Integer readBreak() throws DocumentException {
		Document read = reader.read("config.xml");
		Element rootElement = read.getRootElement();
		Element nameElement =
				(Element)rootElement.selectSingleNode("/properties/entry[@key='breakTime']");
        return Integer.parseInt(nameElement.getText());
	}
	public void writeBreak(String num) throws FileNotFoundException, IOException, DocumentException {
		Document read = reader.read("config.xml");
		Element rootElement = read.getRootElement();
		Element nameElement =
				(Element)rootElement.selectSingleNode("/properties/entry[@key='breakTime']");
		nameElement.setText(num);
		save(reader);
	}

	public Integer readFlag() throws DocumentException{
		Document read = reader.read("config.xml");
		Element rootElement = read.getRootElement();
		Element nameElement =
				(Element)rootElement.selectSingleNode("/properties/entry[@key='flag']");
        return Integer.parseInt(nameElement.getText());
	}
	public void writeFlag(String numb) throws DocumentException, IOException{
		Document read = reader.read("config.xml");
		Element rootElement = read.getRootElement();
		Element nameElement =
				(Element)rootElement.selectSingleNode("/properties/entry[@key='flag']");
		nameElement.setText(numb);
		save(reader);
	}

	public Integer readNoticeWord() throws DocumentException{
		Document read = reader.read("config.xml");
		Element rootElement = read.getRootElement();
		Element nameElement =
				(Element)rootElement.selectSingleNode("/properties/entry[@key='noticeWord']");
        return Integer.parseInt(nameElement.getText());
	}
	public void writeNoticeWord(String word) throws DocumentException, IOException{
		Document read = reader.read("config.xml");
		Element rootElement = read.getRootElement();
		Element nameElement =
				(Element)rootElement.selectSingleNode("/properties/entry[@key='noticeWord']");
		nameElement.setText(word);
		save(reader);
	}

	public static void save(SAXReader reader) throws IOException{
		FileOutputStream out = new FileOutputStream("config.xml");//指定文件输出的位置
		OutputFormat format = OutputFormat.createPrettyPrint(); //格式好的格式.有空格和换行.
		format.setEncoding("utf-8");//2.指定生成的xml文档的编码
		XMLWriter writer = new XMLWriter(out,format);//创建写出对象
		writer.write(reader);//写出对象
		writer.close();//关闭流
	}
}
