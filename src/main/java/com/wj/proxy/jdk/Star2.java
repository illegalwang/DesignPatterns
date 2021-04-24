package com.wj.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * jdk动态代理
 */
public class Star2 implements Show {
    @Override
    public void sing() {
        System.out.println("明星表演：lalalala。");
    }

    public static void main(String[] args) {
        Star2 s = new Star2();
        // 可以用这两个保存$Proxy文件，两行代码支持的jdk版本不一样
//        System.getProperties().put("jdk.proxy.ProxyGenerator.saveGeneratedFiles","true");
//        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles","true");

        Show money = (Show) Proxy.newProxyInstance(s.getClass().getClassLoader(),
                new Class[]{Show.class}, new ProxyMoney(s));
        Show show = (Show) Proxy.newProxyInstance(money.getClass().getClassLoader(),
                new Class[] {Show.class}, new ProxySell(money));
        show.sing();
    }
}

class ProxySell implements InvocationHandler {
    Object o;

    public ProxySell(Object o) {
        this.o = o;
    }

    public void before() {
        System.out.println("业务代理：与金主协商。");
    }

    public void after() {
        System.out.println("业务代理：演出完成，告诉金主。");
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        Object ob = method.invoke(o, args);
        after();
        return ob;
    }
}

class ProxyMoney implements InvocationHandler {
    Object o;

    public ProxyMoney(Object o) {
        this.o = o;
    }

    public void before() {
        System.out.println("财务代理：签订表演合同。");
    }

    public void after() {
        System.out.println("财务代理：收取表演费用。");
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        Object ob = method.invoke(o, args);
        after();
        return ob;
    }
}

interface Show {
    void sing();
}