package gov.jslt.taxevent.comm;

import java.io.Serializable;

public class JsltReturnState implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String returnCode;
	private String returnMsg;

	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	public String getReturnMsg() {
		return returnMsg;
	}

	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}
}
