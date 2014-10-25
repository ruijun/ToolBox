package com.tool.search.phone;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;


public class PhonePullService {

	public static List<Phone> readXml(InputStream inStream){
		List<Phone> phones = null;
		Phone phone = null;
		try {
			//获取XMLPull解析工厂
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			//创建XML PULL解析器
			XmlPullParser parser = factory.newPullParser();
			//设置XML Pull解析器的输入内容和编码
			parser.setInput(inStream, "GBK");
			//获取事件类型
			int eventCode = parser.getEventType();
			try {
				while (eventCode != XmlPullParser.END_DOCUMENT) {
						switch (eventCode) {
						//判断当前事件是否是文档开始事件 
						case XmlPullParser.START_DOCUMENT: // 0 文档开始事件
							phones = new ArrayList<Phone>();
							break;
						//判断当前事件是否是标签元素开始事件  
						case XmlPullParser.START_TAG: // 2 开始元素
							if ("product".equals(parser.getName())) {
								//初始化ips集合  
								phone = new Phone();
								//person.setId(new Integer(parser.getAttributeValue(0)));
							} else if (null != phone) {
								//判断开始标签元素是否是ip
								if ("phonenum".equals(parser.getName())) {
									phone.setPhonenum(parser.nextText());
								  //判断开始标签元素是否是location
								} else if ("location".equals(parser.getName())) {
									phone.setLocation(parser.nextText());
								}
							}
							break;
							//判断当前事件是否是标签元素结束事件  
						case XmlPullParser.END_TAG: // 结束元素
							if ("product".equals(parser.getName()) && phone != null) {
								//将ip添加到ips集合
								phones.add(phone);
								phone = null;
							}
							break;
						}
						//进入下一个元素并触发相应事件  
						eventCode = parser.next();
					}
					
			} catch (IOException e) {
				// TODO: handle exception
			}
		} catch (XmlPullParserException e) {
			// TODO: handle exception
			System.out.println("22222222222");
		}
		
		return phones;
	}
}
