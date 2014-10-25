package com.tool.search.creditcard;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;


public class CreditCardPullService {

	public static List<CreditCard> readXml(InputStream inStream){
		List<CreditCard> cards = null;
		CreditCard card = null;
		try {
			//获取XMLPull解析工厂
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			//创建XML PULL解析器
			XmlPullParser parser = factory.newPullParser();
			//设置XML Pull解析器的输入内容和编码
			parser.setInput(inStream, "UTF-8");
			//获取事件类型
			int eventCode = parser.getEventType();
			try {
				while (eventCode != XmlPullParser.END_DOCUMENT) {
					switch (eventCode) {
					//判断当前事件是否是文档开始事件 
					case XmlPullParser.START_DOCUMENT: // 0 文档开始事件
						cards = new ArrayList<CreditCard>();
						break;
					//判断当前事件是否是标签元素开始事件  
					case XmlPullParser.START_TAG: // 2 开始元素
						if ("bankinfo".equals(parser.getName())) {
							//初始化cardCards集合  
							card = new CreditCard();
							//person.setcard(new Integer(parser.getAttributeValue(0)));
						} else if (null != card) {
							//判断开始标签元素是否是cardCard
							if ("status".equals(parser.getName())) {
								
							  //判断开始标签元素是否是location
							}else if ("cardNum".equals(parser.getName())) {
								card.setCardNum(parser.nextText());
							}else if ("area".equals(parser.getName())) {
								card.setArea(parser.nextText());
							}else if ("bank".equals(parser.getName())) {
								card.setBank(parser.nextText());
							}else if ("cardType".equals(parser.getName())) {
								card.setCardType(parser.nextText());				
							}else if ("cardName".equals(parser.getName())) {
								card.setCardName(parser.nextText());				
							}
						}
						break;
						//判断当前事件是否是标签元素结束事件  
					case XmlPullParser.END_TAG: // 结束元素
						if ("bankinfo".equals(parser.getName()) && card != null) {
							//将cardCard添加到cardCards集合
							cards.add(card);
							card = null;
						}
						break;
					}
					//进入下一个元素并触发相应事件  
					eventCode = parser.next();
				}
			} catch (IOException e) {
				// TODO: handle exception
				System.out.println("11111111111111");
			}
		} catch (XmlPullParserException e) {
			// TODO: handle exception
			System.out.println("2222222222222");
		}		
		return cards;
	}
}
