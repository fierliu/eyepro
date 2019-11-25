import com.allan.dao.PropertiesDAO;
import com.allan.utils.PopUpUtil;
//import org.junit.Test;

public class PropertiesTest {

    /*@Test
    public void single(){
        PropertiesDAO propertiesDAO1 = PropertiesDAO.getInstance();
        System.out.println("propertiesDAO1 = " + propertiesDAO1);
        PropertiesDAO propertiesDAO2 = PropertiesDAO.getInstance();
        System.out.println("propertiesDAO2 = " + propertiesDAO2);
    }

    @Test
    public void popUpPosition(){
        PropertiesDAO propertiesDAO1 = PropertiesDAO.getInstance();
        propertiesDAO1.writePopUpPosition("middle");
        String s = propertiesDAO1.readPopUpPosition();
        System.out.println("s = " + s);
    }

    @Test
    public void popupUtil(){
//        double x = PopUpUtil.getX();
//        System.out.println("x = " + x);
    }*/
public static void main(String[] args) {
method(null);
}
public static void method(String param) {
switch (param) {
// 肯定不是进入这里
case "sth":
System.out.println("it's sth");
break;
// 也不是进入这里
case "null":
System.out.println("it's null");
break;
// 也不是进入这里
default:
System.out.println("default");
}
}
}
