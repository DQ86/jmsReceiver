package gov.jslt.taxcore.taxblh.comm;

import javax.ejb.EJBHome;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

public class AbstractEJBLocator extends AbstractLocator {
	public AbstractEJBLocator() {
	}

	/**
	 * 该方法作为超类中lookup方法的实现，用来查询得到EJB的远程接口。该方法
	 * 调用getContext方法来生成Context对象，然后用Context来查询得到 EJB的远程接口，最后饭后该远程接口。
	 * 
	 * @param name
	 *            :String - 被查找对象的JNDI名
	 * @return Object - JNDI得到的对象
	 */
	protected Object lookupDef(String name) throws NamingException,
			ClassCastException {
		Context ctx = getDefContext();
		Object obj = ctx.lookup(name);
		EJBHome home = (EJBHome) PortableRemoteObject
				.narrow(obj, EJBHome.class);
		closeContext(ctx);
		return home;

	}

}
