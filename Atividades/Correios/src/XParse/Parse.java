package XParse;

import org.w3c.dom.Document;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
/**
 *
 * @author Rafael Viana
 */
public class Parse {
    public static String passarXMLParaString(Document xml, int espacosIdentacao) {
        try {
            //set up a transformer
            TransformerFactory transfac = TransformerFactory.newInstance();
            transfac.setAttribute("indent-number", new Integer(espacosIdentacao));
            Transformer trans = transfac.newTransformer();
            trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            trans.setOutputProperty(OutputKeys.INDENT, "yes");

            //create string from xml tree
            StringWriter sw = new StringWriter();
            StreamResult result = new StreamResult(sw);
            DOMSource source = new DOMSource(xml);
            trans.transform(source, result);
            String xmlString = sw.toString();
            return xmlString;
        } catch (TransformerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.exit(0);
        }
        return null;
    }
}
