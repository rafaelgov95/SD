package LocalizarEncomenda;/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import XParse.Parse;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import org.w3c.dom.Document;

/**
 *
 * @author Rafael Viana
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SOAPException, IOException {
        // TODO code application logic here
        // TODO code application logic here
        String requestSoap;//requisicao/request no formato xml, repare que isto foi copiado da regiao destacada em azul na figura 1
        requestSoap =  "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:res=\"http://resource.webservice.correios.com.br/\">\n"
                + "   <soapenv:Header/>\n"
                + "   <soapenv:Body>\n"
                + "      <res:buscaEventos>\n"
                + "         <usuario>ECT</usuario>\n"
                + "         <senha>SRO</senha>\n"
                + "         <tipo>L</tipo>\n"
                + "         <resultado>T</resultado>\n"
                + "         <lingua>101</lingua>\n"
                + "          <objetos>RY907728402SG</objetos>\n"
                + "      </res:buscaEventos>\n"
                + "    </soapenv:Body>\n"
                + "</soapenv:Envelope>";
        SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
        SOAPConnection soapConnection = soapConnectionFactory.createConnection();
        String url = "http://webservice.correios.com.br:80/service/rastro";//url do webservice nao e a url do wsdl do webservice, repare que isto foi copia da parte vermelha da figura 1
        MimeHeaders headers = new MimeHeaders();
        headers.addHeader("Content-Type", "text/xml");

        headers.addHeader("SOAPAction", "http://webservice.correios.com.br:80"); // header SOAPAction e sua respectiva url, esta url muda de webservice para webservice. Alguns webservice nao possuem esta proprieade, nestes webservice esta linha deve ser excluida


        MessageFactory messageFactory = MessageFactory.newInstance();

        SOAPMessage msg = messageFactory.createMessage(headers, (new ByteArrayInputStream(requestSoap.getBytes())));

        SOAPMessage soapResponse = soapConnection.call(msg, url);
        Document xmlRespostaARequisicao=soapResponse.getSOAPBody().getOwnerDocument();
        System.out.println(Parse.passarXMLParaString(xmlRespostaARequisicao,4));//imprime na tela o xml de retorno.
    }

}
