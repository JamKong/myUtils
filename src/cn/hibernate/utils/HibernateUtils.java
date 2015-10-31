package cn.hibernate.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class HibernateUtils {
	private static String CONFIG_FILE_LOCATION = "/hibernate.cfg.xml";
	private static final ThreadLocal<Session> threadLocal = new ThreadLocal<Session>();
	private static Configuration configuration = new Configuration();
	private static SessionFactory sessionFactory ;
	private static String configFile = CONFIG_FILE_LOCATION;
	
	/* 静态代码块创建SessionFactory */
	static{
		try{
			configuration.configure(configFile);
			sessionFactory=configuration.buildSessionFactory();
		}catch(Exception e){
			System.out.println("Error Creating SessionFactory ");
			e.printStackTrace();
		}
	}
	//私有化构造器
	private HibernateUtils(){}
	
	/**
	 * 返回ThreadLocal中的session实例
	 */
	public static Session getSession(){
		Session session = threadLocal.get();
		if(null == session || !session.isOpen()){
			if(sessionFactory == null){
				rebuildSessionFactory();
			}
			session = (sessionFactory!=null)?sessionFactory.openSession():null;
			threadLocal.set(session);
		}
		return session;
	}
	
	/**
	 * 返回Hibernate的SessionFactory
	 */
	private static void rebuildSessionFactory() {
		try{
			configuration.configure(configFile);
			sessionFactory = configuration.buildSessionFactory();
		}catch(Exception e){
			System.out.println(" Error Creating SessionFactory ");
			e.printStackTrace();
		}
	}
	/**
	 * 关闭Session实例并且把ThreadLocal中的副本清除
	 */
	public static void closeSession(){
		Session session = (Session)threadLocal.get();
		threadLocal.set(null);
		if(session!=null){
			session.close();
		}
	}
	/**
	 * 返回SessionFactory
	 */
	public static SessionFactory getSessionFactory(){
		return sessionFactory;
	}
	public static void setConfigFile(String configFile){
		HibernateUtils.configFile = configFile;
		sessionFactory = null;//因为配置重新设置，所以sessionFactory需要重新建立 
	}
	public static Configuration getConfiguration(){
		return configuration;
	}
}
