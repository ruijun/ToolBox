package com.tool.search.alexa;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;


public class AlexaPullService {

	public static List<Alexa> readXml(InputStream inStream) throws Exception {
		List<Alexa> alexas = null;
		boolean first = true;
		//获取XMLPull解析工厂
		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
		//创建XML PULL解析器
		XmlPullParser parser = factory.newPullParser();
		//设置XML Pull解析器的输入内容和编码
		parser.setInput(inStream, "UTF-8");
		//获取事件类型
		int eventCode = parser.getEventType();
		Alexa alexa = null;
		while (eventCode != XmlPullParser.END_DOCUMENT) {
			switch (eventCode) {
			//判断当前事件是否是文档开始事件 
			case XmlPullParser.START_DOCUMENT: // 0 文档开始事件
				alexas = new ArrayList<Alexa>();
				break;
			//判断当前事件是否是标签元素开始事件  
			case XmlPullParser.START_TAG: // 2 开始元素
				if ("SD".equals(parser.getName()) && first ) {
					//初始化ips集合  
					alexa = new Alexa();
					first = false;
				} else if (null != alexa) {
					//判断开始标签元素是否是ip
					if ("TITLE".equals(parser.getName())) {			
					    alexa.setTitle(parser.getAttributeValue(0));				
					}else if("SPEED".equals(parser.getName())){
						alexa.setMsSpeed(parser.getAttributeValue(0));
						alexa.setSecond(parser.getAttributeValue(1));
					}else if ("LINKSIN".equals(parser.getName())) {			
					    alexa.setReferrers(parser.getAttributeValue(0));				
					}else if ("POPULARITY".equals(parser.getName())) {
						alexa.setRank(parser.getAttributeValue(1));
					}
							
				}
				break;
				//判断当前事件是否是标签元素结束事件  
			case XmlPullParser.END_TAG: // 结束元素
				if ("ALEXA".equals(parser.getName()) && alexa != null) {
					//将ip添加到ips集合
					alexas.add(alexa);
					alexa = null;
				}
				break;
			}
			//进入下一个元素并触发相应事件  
			eventCode = parser.next();
		}
		return alexas;
	}
}
