package gov.jslt.taxcore.taxblh.comm;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.ctp.core.config.ApplicationContext;
import com.ctp.core.log.LogWritter;

/**
 * <p>
 * Title: AbstractLocator
 * </p>
 * <p>
 * Description:Locator的实现类所继承的抽象类。这个抽象<br>
 * 类有三个实现方法和两个抽象方法。所有这个类的实现类都必须<br>
 * 是Singleton模式的！
 * </p>
 * <p>
 * Copyright: Copyright (c) 2008
 * </p>
 * <p>
 * Company: CSS
 * </p>
 * 
 * @author 周红江
 * @version 1.1
 */

public abstract class AbstractLocator {
	public AbstractLocator() {
	}

	/**
	 * 实现方法，用来得到Context实例。这个方法首先会调用getPorperties方法<br>
	 * 得到一个java.util.Porperties的实例，然后再根据Porperties的实例得<br>
	 * 到一个java.util.Context类的实例，并返回。
	 * 
	 * @return Context - 生成的Context类的实例。
	 * @throws NamingException
	 */
	protected Context getDefContext() throws NamingException {
		Context ctx = new InitialContext(getDefProperties());
		return ctx;
	}

	/**
	 * 实现方法。用来关闭Context类的实例。
	 * 
	 * @param context
	 *            :Context 被关闭的Context类的实例
	 * @throws NamingException
	 */
	protected void closeContext(Context context) throws NamingException {
		if (context != null) {
			context.close();
		}
	}

	/**
	 * 这个方法用来生成一个java.util.Porperties对象的实例。实例中存<br>
	 * 储两个key，分别是：Context类的静态属性<br>
	 * INITIAL_CONTEXT_FACTORY和静态属性PROVIDER_URL。他们的<br>
	 * value是通过两次调用ApplicationContext类的<br>
	 * getValueAsString方法。两次调用该方法输入的参数分别是："<br>
	 * ctp.jndi.factory","ctp.jndi.hostname.key"和<br>
	 * “ctp.jndi.portNumber.key”。
	 * 
	 * @return Properties - 生成的Porperties 实例。
	 */
	protected Properties getDefProperties() {
		Properties prop = new Properties();
		prop.put(Context.INITIAL_CONTEXT_FACTORY, ApplicationContext
				.singleton().getValueAsString("ctp.default.jndi.factory"));
		prop.put(Context.PROVIDER_URL, ApplicationContext.singleton()
				.getValueAsString("jslt.ejb.url"));
		LogWritter.sysDebug(prop.toString());
		return prop;
	}

}
