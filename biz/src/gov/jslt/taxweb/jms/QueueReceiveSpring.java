package gov.jslt.taxweb.jms;

import gov.jslt.taxevent.comm.DateUtil;
import gov.jslt.taxevent.comm.JsltReqBwxxVO;
import gov.jslt.taxevent.comm.JsltResBwxxVO;
import gov.jslt.taxevent.comm.JsltReturnState;

import java.util.HashMap;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.springframework.jms.listener.SessionAwareMessageListener;

import com.ctp.core.bizdelegate.BizDelegate;
import com.ctp.core.event.BaseRequestEvent;
import com.ctp.core.event.ResponseEvent;
import com.ctp.core.log.LogWritter;
import com.thoughtworks.xstream.XStream;

public class QueueReceiveSpring implements
		SessionAwareMessageListener<MapMessage> {

	@Override
	public void onMessage(MapMessage msg, Session qsession) {
		String reqXml = "";
		String replyMsg = "";
		String swglm = "";
		// 1.接收请求参数
		try {
			if (null == msg.getString("reqXml")) {
				return;
			}
			reqXml = msg.getString("reqXml");
			if (null != msg.getString("swglm")) {
				LogWritter.sysError("获取的swglm为：" + msg.getString("swglm"));
				System.out.println("获取的swglm为：" + msg.getString("swglm"));
				swglm = msg.getString("swglm");
			}
		} catch (JMSException e) {
			LogWritter.sysError("请求的报文为空:" + e.getMessage());
			return;
		}
		if (!"".equals(swglm)) {
			LogWritter.sysError("请求报文为:" + reqXml);
			System.out.println("请求报文为:" + reqXml);
			// 2.处理BLH业务请求参数
			XStream xStream = new XStream();
			xStream.alias("jslt", JsltReqBwxxVO.class);
			LogWritter.sysError("将请求报文转换成JAVA对象:" + reqXml);
			JsltReqBwxxVO jsltReqBwxxVO = (JsltReqBwxxVO) xStream
					.fromXML(reqXml.replaceAll("&lt;", "<").replaceAll("&gt;",
							">"));
			System.out.println("将请求报文转换成JAVA对象成功");
			LogWritter.sysError("将请求报文转换成JAVA对象成功");
			// 3.调用BLH处理业务请求
			HashMap<String, Object> reqMapParam = new HashMap<String, Object>();
			reqMapParam.put("swglm", swglm);
			BaseRequestEvent baseRequest = new BaseRequestEvent(
					jsltReqBwxxVO.getBlhName(), "web", "sessionID");
			baseRequest.setDealMethod(jsltReqBwxxVO.getDealMethod());
			baseRequest.setReqMapParam(reqMapParam);
			ResponseEvent responseEvent = BizDelegate.delegate(baseRequest);
			// 4.处理BLH业务返回参数
			JsltResBwxxVO resBwxxVO = new JsltResBwxxVO();
			resBwxxVO.setContent("<![CDATA["
					+ responseEvent.respMapParam.get("content").toString()
					+ "]]>");
			JsltReturnState returnState = new JsltReturnState();
			returnState.setReturnCode(responseEvent.getRepCode());
			returnState.setReturnMsg(responseEvent.getReponseMesg());
			resBwxxVO.setReturnState(returnState);
			xStream = new XStream();
			xStream.alias("jslt", JsltResBwxxVO.class);
			xStream.alias("returnState", JsltReturnState.class);
			replyMsg = xStream.toXML(resBwxxVO).replaceAll("&lt;", "<")
					.replaceAll("&gt;", ">").replaceAll("&quot;", "\"");
			LogWritter.sysError("返回报文为:" + replyMsg);
			System.out.println("返回报文为:" + replyMsg);
			// 5.返回结果
			try {
				Destination destination = msg.getJMSReplyTo();
				// 返回目的地不为空表示同步调用
				if (destination != null) {
					MapMessage mapMsg;
					mapMsg = qsession.createMapMessage();
					mapMsg.setString("replyMsg", replyMsg);
					mapMsg.setJMSCorrelationID(msg.getJMSCorrelationID());
					MessageProducer producer = qsession
							.createProducer(destination);
					producer.send(mapMsg);
					System.out.println("已成功回复消息"
							+ DateUtil.getNowString("yyyy-MM-dd HH:mm:ss:SSS"));
					LogWritter.sysError("已成功回复消息"
							+ DateUtil.getNowString("yyyy-MM-dd HH:mm:ss:SSS"));
				} else {
					System.out.println("未获取到消息回复目的地，不执行回复");
					LogWritter.sysError("未获取到消息回复目的地，不执行回复");
				}
			} catch (JMSException e) {
				System.out.println("业务处理成功，但JMS返回出错:" + e.getMessage());
				LogWritter.sysError("业务处理成功，但JMS返回出错:" + e.getMessage());
				return;
			}
		}
	}
}
