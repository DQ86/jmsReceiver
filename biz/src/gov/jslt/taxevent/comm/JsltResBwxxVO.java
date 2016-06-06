package gov.jslt.taxevent.comm;

import java.io.Serializable;

public class JsltResBwxxVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JsltReturnState returnState;
	private String content;

	public JsltReturnState getReturnState() {
		return returnState;
	}

	public void setReturnState(JsltReturnState returnState) {
		this.returnState = returnState;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
