package gov.jslt.taxevent.comm;

import java.io.Serializable;

public class JsltReqBwxxVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String blhName;
	private String dealMethod;
	private String content;

	public String getBlhName() {
		return blhName;
	}

	public void setBlhName(String blhName) {
		this.blhName = blhName;
	}

	public String getDealMethod() {
		return dealMethod;
	}

	public void setDealMethod(String dealMethod) {
		this.dealMethod = dealMethod;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
