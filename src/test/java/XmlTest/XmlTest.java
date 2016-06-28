package XmlTest;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.io.*;

/**
 * Created by xxsy on 2016/4/11.
 */
public class XmlTest {
    public static void main(String[] args) {
        Document document = DocumentHelper.createDocument();
        document.setXMLEncoding("UTF-8");
        Element root=document.addElement("students");
        root.addAttribute("class", "一班").addAttribute("count", "3");
        Element student=root.addElement("student");
        student.addElement("name").setText("小明");
        student.addElement("age").setText("10");

        student=root.addElement("student").addAttribute("position", "班长");
        student.addElement("name").setText("小汪");
        student.addElement("age").setText("11");

        student=root.addElement("student");
        student.addElement("name").setText("小兵");
        student.addElement("age").setText("12");

        String xmlStr=document.asXML();
        try {
            OutputFormat format= OutputFormat.createPrettyPrint();
            XMLWriter writer=new XMLWriter(new FileWriter(new File("conf/students.xml")),format);
            writer.setEscapeText(false);
            writer.write(xmlStr);
            writer.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
