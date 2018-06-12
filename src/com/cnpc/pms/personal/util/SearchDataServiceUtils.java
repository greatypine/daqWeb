package com.cnpc.pms.personal.util;
import org.apache.log4j.Logger;

import javax.xml.soap.*;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

public class SearchDataServiceUtils {
    private Logger logger = Logger.getLogger("sync");
    static String webserviceUrl = "http://111.207.11.227:9001/services/SearchDataService";
    //http://111.207.11.227:9001/services/SearchDataService?wsdl
    public static void main(String[] args) {
		//测试
    	//initSearchAllData();
    	
    	//searchDate("2018-06-11 10:00:20"); 
    	
	}
    
    /**
     * 初始化查询全部
     * @return
     */
    public static String initSearchAllData(){
    	 SearchDataServiceUtils ssu = new SearchDataServiceUtils();
    	 StringBuffer buff = new StringBuffer();
         buff.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:hrs=\"http://www.hjsj.com/HrService\">");
         buff.append("<soapenv:Header/>");
         buff.append("<soapenv:Body>");
         buff.append("<hrs:searchAllData/>");
         buff.append("</soapenv:Body>");
         buff.append("</soapenv:Envelope>");
         String xml = ssu.invokeWebservice(webserviceUrl, buff.toString());
         System.out.println("查询所有数据===>" + xml);
    	return xml;
    }
    
    /**
     * 查询差量
     * @param date
     * @return
     */
    public static String searchDate(String date){
    	SearchDataServiceUtils ssu = new SearchDataServiceUtils();
    	StringBuffer buff = new StringBuffer();
    	 // 查询某时间之后变化的数据
        //String date = "2018-05-22 00:00:00";
        buff.delete(0, buff.length());
        buff.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:hrs=\"http://www.hjsj.com/HrService\">");
        buff.append("<soapenv:Header/>");
        buff.append("<soapenv:Body>");
        buff.append("<hrs:searchData>");
        buff.append("<hrs:date>" + date +"</hrs:date>");
        buff.append("</hrs:searchData>");
        buff.append("</soapenv:Body>");
        buff.append("</soapenv:Envelope>");
        String xml = ssu.invokeWebservice(webserviceUrl, buff.toString());
        System.out.println("查询某时间之后变化的数据===>" + xml);
    	return xml;
    }

    /**
     * 调用webservice
     *
     * @param url
     * @param soapStr
     * @return response
     */
    private String invokeWebservice(String url, String soapStr) {
        String xml = "";
        SOAPConnection con = null;
        try {
            MessageFactory reqMsgFactory = MessageFactory.newInstance();
            // 2.
            SOAPMessage request = reqMsgFactory.createMessage();

            SOAPPart soapPart = request.getSOAPPart();

            StringReader reader = new StringReader(soapStr);
            Source source = new StreamSource(reader);
            soapPart.setContent(source);

            SOAPConnectionFactory factory = SOAPConnectionFactory.newInstance();
            con = factory.createConnection();

            URL urlval = new URL(new URL(url), "", new URLStreamHandler() {
                @Override
                protected URLConnection openConnection(URL url) throws IOException {
                    URL target = new URL(url.toString());
                    URLConnection connection = target.openConnection();
                    // Connection settings
                    connection.setConnectTimeout(3 * 1000);
                    connection.setReadTimeout(30 * 1000);
                    return (connection);
                }
            });

            request.writeTo(System.out);
            SOAPMessage response = con.call(request, urlval);
            //response.writeTo(System.out);
            SOAPBody responseBody = response.getSOAPBody();
            //System.out.println("=====>" + responseBody.toString());
            logger.debug("the interface return data ======>" + responseBody.toString());
            xml = responseBody.getFirstChild().getFirstChild().getTextContent();
            logger.debug("the interface return data ======>" + xml);
        } catch (Exception e) {
            e.printStackTrace();
            // message = e.getMessage();
            logger.error("invoke webservice error====>" + e.getMessage());
            logger.error(e.getMessage(), e);
        } finally {
            try {
                if (con != null)
                    con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return xml;
    }

//
//<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
//   <soap:Body>
//      <ns1:searchDataResponse xmlns:ns1="http://www.hjsj.com/HrService">
//         <ns1:out>date</ns1:out>
//      </ns1:searchDataResponse>
//   </soap:Body>
//</soap:Envelope>

}
