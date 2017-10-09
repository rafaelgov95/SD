package PrazoDeEntrega;/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import XParse.Parse;
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
        requestSoap =  "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"http://tempuri.org/\">\n"
                + "   <soapenv:Header/>\n"
                + "   <soapenv:Body>\n"
                + "      <tem:CalcPrazo>\n"
                + "         <!--Optional:-->\n"
                + "         <tem:nCdServico>40010</tem:nCdServico>\n"
                + "         <!--Optional:-->\n"
                + "         <tem:sCepOrigem>01207000</tem:sCepOrigem>\n"
                + "         <!--Optional:-->\n"
                + "         <tem:sCepDestino>01504001</tem:sCepDestino>\n"
                + "      </tem:CalcPrazo>\n"
                + "   </soapenv:Body>\n"
                + "</soapenv:Envelope>";
        SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
        SOAPConnection soapConnection = soapConnectionFactory.createConnection();
        String url = "http://ws.correios.com.br/calculador/CalcPrecoPrazo.asmx";//url do webservice nao e a url do wsdl do webservice, repare que isto foi copia da parte vermelha da figura 1
        MimeHeaders headers = new MimeHeaders();
        headers.addHeader("Content-Type", "text/xml");

        //exclua esta regiao caso o webservice nao possua a proprieade SOAPAction
        headers.addHeader("SOAPAction", "http://tempuri.org/CalcPrazo"); // header SOAPAction e sua respectiva url, esta url muda de webservice para webservice. Alguns webservice nao possuem esta proprieade, nestes webservice esta linha deve ser excluida
        // o valor "http://tempuri.org/CalcPrazo" foi obtido com base na região destacada em verde da figura 2.

        //fim da regiao a ser excluida caso o webservice nao possua a proprieade SOAPAction

        MessageFactory messageFactory = MessageFactory.newInstance();

        SOAPMessage msg = messageFactory.createMessage(headers, (new ByteArrayInputStream(requestSoap.getBytes())));

        SOAPMessage soapResponse = soapConnection.call(msg, url);
        Document xmlRespostaARequisicao=soapResponse.getSOAPBody().getOwnerDocument();
        System.out.println(Parse.passarXMLParaString(xmlRespostaARequisicao,4));//imprime na tela o xml de retorno.
    }

}
