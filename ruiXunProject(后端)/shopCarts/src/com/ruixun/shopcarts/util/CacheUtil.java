package com.ruixun.shopcarts.util;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * ehcache缓存工具类
 *  基本存储方式: 类似Map结构的键值对缓存
 * @author runmu
 */
public class CacheUtil {
    private static CacheManager cacheManager;
    static {
        cacheManager = CacheManager.create(CacheUtil.class.getClassLoader().getResourceAsStream("ehcache.xml"));
    }

    /**
     *  存缓存
     * @param key
     * @param value
     */
    public static void  put(String key,Object value){
        Cache cache = cacheManager.getCache("categoryCache");

        Element element = new Element(key,value);
        cache.put(element);
    }

    /**
     *  从缓存中根据键获取值
     * @param key
     * @return
     */
    public static Object get(String key){
        Cache cache = cacheManager.getCache("categoryCache");
        Element element = cache.get(key);
        Object result = null;

        if(null != element){
            result=element.getObjectValue();
        }

        return result;

    }

    /**
     * 关闭ehcache缓存
     */
    public static void closeCacheManger() {
        System.out.println("正在关闭ehcache缓存...");
        cacheManager.getInstance().shutdown();
    }
}
