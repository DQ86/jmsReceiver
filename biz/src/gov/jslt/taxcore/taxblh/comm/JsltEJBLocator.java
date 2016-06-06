package gov.jslt.taxcore.taxblh.comm;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

import com.ctp.core.exception.TaxLookupException;
import com.ctp.core.gateway.facade.TaxFacadeGateWay;
import com.ctp.core.gateway.facade.TaxFacadeGateWayHome;
import com.ctp.core.log.LogWritter;

public class JsltEJBLocator extends AbstractEJBLocator {
	// 缓存，用来存放查找到的EJBHome对象。Map中的key是EJB的jndi名，value 是EJBHome对象。
	private static ConcurrentMap<String, Object> defHome = new ConcurrentHashMap<String, Object>();

	// EJBLocator的静态变量,经过类的初始化后成为全局的一个实例.
	private static JsltEJBLocator ejbLocator = new JsltEJBLocator();

	private JsltEJBLocator() {
	}

	/**
	 * 用来根据实例的jndi名得到EJBHome实例。方法会先在home属性中查找<br>
	 * 是否有给实例的缓存。如果有，则用实例生成EJBObject实例并返回。如<br>
	 * 果生成出错，则调用clear方法，然后调用lookup方法找到该EJBHome实<br>
	 * 例，放入home中，调用LogWritter的sysInfo方法记录存放的实例。如<br>
	 * 果缓存中没有该实例，调用lookup方法找到该EJBHome实例，放入home<br>
	 * 中，调用LogWritter的sysInfo方法记录存放的实例。
	 * 
	 * @param key
	 *            :String 实例名
	 * @return Object 返回的实例，是Object类型。
	 * @throws NamingException
	 * @throws ClassCastException
	 */
	public Object getDefInstance(String key) throws TaxLookupException {
		if (defHome.containsKey(key)) {
			// 如果缓存中有此对象，则返回此对象
			TaxFacadeGateWayHome ejbHome = (TaxFacadeGateWayHome) defHome
					.get(key);
			try {
				Object obj = narrow(ejbHome.create(), TaxFacadeGateWay.class);
				return obj;
			} catch (Exception ex) { // 当缓存中的实例过期时，重新生成缓存。
				clearDef(key);
				Object obj1 = null;
				try {
					TaxFacadeGateWayHome ejbHomeBak = (TaxFacadeGateWayHome) lookupDef(key);
					obj1 = narrow(ejbHomeBak.create(), TaxFacadeGateWay.class);
					defHome.put(key, ejbHomeBak);
				} catch (NamingException ex1) { // 封装异常
					LogWritter.sysError(ex1.getMessage(), ex1);
					throw new TaxLookupException(ex1.getMessage());
				} catch (Exception exOther1) {
					LogWritter.sysError(exOther1.getMessage(), exOther1);
					throw new TaxLookupException(exOther1.getMessage());
				}
				return obj1;
			}
		} else { // 缓存中无此实例，重新查找得到
			TaxFacadeGateWayHome ejbHome = null;
			Object obj2 = null;
			try {
				ejbHome = (TaxFacadeGateWayHome) this.lookupDef(key);
				obj2 = narrow(ejbHome.create(), TaxFacadeGateWay.class);
				defHome.put(key, ejbHome);
			} catch (NamingException ex2) { // 封装异常
				LogWritter.sysError(ex2.getMessage(), ex2);
				throw new TaxLookupException(ex2.getMessage());
			} catch (Exception exOther2) {
				LogWritter.sysError(exOther2.getMessage(), exOther2);
				throw new TaxLookupException(exOther2.getMessage());
			}
			return obj2;
		}
	}

	/**
	 * 返回ejbLocator属性。
	 * 
	 * @return
	 */
	public static JsltEJBLocator singleton() {
		return ejbLocator;
	}

	/**
	 * 清空home属性中的所用缓存实例。然后调用LogWritter的静态方<br>
	 * 法sysInfo,在日志中记录缓存已经清空的信息。
	 */
	private void clearDef(String key) {
		if (defHome.containsKey(key)) {
			defHome.remove(key);
			LogWritter.sysInfo("EJBLocator中defHome的缓存 " + key + " 已经清空！");
		}
	}

	private Object narrow(Object ref, Class<TaxFacadeGateWay> c) {
		return PortableRemoteObject.narrow(ref, c);
	}

}
