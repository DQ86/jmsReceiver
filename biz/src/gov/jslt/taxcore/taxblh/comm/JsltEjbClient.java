package gov.jslt.taxcore.taxblh.comm;

import gov.jslt.taxevent.comm.JsltReqBwxxVO;
import gov.jslt.taxevent.comm.JsltResBwxxVO;
import gov.jslt.taxevent.comm.JsltReturnState;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

import com.ctp.core.event.BaseRequestEvent;
import com.ctp.core.event.RequestEvent;
import com.ctp.core.event.ResponseEvent;
import com.ctp.core.exception.TaxLookupException;
import com.ctp.core.gateway.facade.TaxFacadeGateWay;
import com.ctp.core.log.LogWritter;
import com.thoughtworks.xstream.XStream;

public class JsltEjbClient {
	public String callJsltEjb(String reqXml, String swglm, String djXh)
			throws TaxLookupException, RemoteException {
		XStream xStream = new XStream();
		xStream.alias("jslt", JsltReqBwxxVO.class);
		LogWritter.sysError("将报文转换成JAVA对象:" + reqXml);
		JsltReqBwxxVO vo = (JsltReqBwxxVO) xStream.fromXML(reqXml.replaceAll(
				"&lt;", "<").replaceAll("&gt;", ">"));
		System.out.println("将报文转换成JAVA对象成功");
		LogWritter.sysError("将报文转换成JAVA对象成功");
		BaseRequestEvent baseRequest = new BaseRequestEvent(vo.getBlhName(),
				"web", "sessionId");
		baseRequest.setDealMethod(vo.getDealMethod());
		Map<String, Object> reqMapParam = new HashMap<String, Object>();
		reqMapParam.put("swglm", swglm);
		reqMapParam.put("djXh", djXh);
		reqMapParam.put("content", vo.getContent());
		System.out.println("调用的BLH名称为:" + vo.getBlhName());
		LogWritter.sysError("调用的BLH名称为:" + vo.getBlhName());
		System.out.println("调用的dealMethod名称为:" + vo.getDealMethod());
		LogWritter.sysError("调用的dealMethod名称为:" + vo.getDealMethod());
		System.out.println("swglm为:" + swglm);
		LogWritter.sysError("swglm为:" + swglm);
		System.out.println("djXh为:" + djXh);
		LogWritter.sysError("djXh为:" + djXh);
		System.out.println("content为:" + vo.getContent());
		LogWritter.sysError("content为:" + vo.getContent());
		baseRequest.setReqMapParam((HashMap<String, Object>) reqMapParam);
		baseRequest.setCallType(RequestEvent.CALL_TYPE_REMOTE);
		baseRequest.setTxType("0");
		TaxFacadeGateWay tfgw = (TaxFacadeGateWay) JsltEJBLocator.singleton()
				.getDefInstance("TaxFacadeGateWayBean");
		System.out.println("获取EJB对象成功");
		LogWritter.sysError("获取EJB对象成功");
		ResponseEvent responseEvent = tfgw.invokeTask(baseRequest);
		System.out.println("远程调用EJB接口成功");
		LogWritter.sysError("远程调用EJB接口成功");
		JsltResBwxxVO resBwxxVO = new JsltResBwxxVO();
		resBwxxVO.setContent("<![CDATA["
				+ responseEvent.respMapParam.get("content").toString() + "]]>");
		JsltReturnState returnState = new JsltReturnState();
		returnState.setReturnCode(responseEvent.getRepCode());
		returnState.setReturnMsg(responseEvent.getReponseMesg());
		resBwxxVO.setReturnState(returnState);
		xStream = new XStream();
		xStream.alias("jslt", JsltResBwxxVO.class);
		xStream.alias("returnState", JsltReturnState.class);
		return xStream.toXML(resBwxxVO).replaceAll("&lt;", "<")
				.replaceAll("&gt;", ">");
	}
}
