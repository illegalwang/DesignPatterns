package com.wj.proxy.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * cglib动态代理
 */
public class Star3 {
    public void sing() {
        System.out.println("明星表演：lalalala。");
    }

    public static void main(String[] args) {
        Star3 s = (Star3) new ProxySell().getProxy(Star3.class);
        s.sing();
    }
}

class ProxySell implements MethodInterceptor {

    public Object getProxy(Class cls) {
        // CGLIB Enhancer增强类
        Enhancer enhancer = new Enhancer();
        // 设定增强类类型（目标代理对象的类型）
        enhancer.setSuperclass(cls);
        // 定义当前对象为代理对象，需要实现MethodInterceptor接口
        enhancer.setCallback(this);
        // 返回代理对象
        return enhancer.create();
    }

    public void before() {
        System.out.println("业务代理：与金主协商。");
    }

    public void after() {
        System.out.println("业务代理：演出完成，告诉金主。");
    }

    // o cglib生成动态代理对象本身
    // method 代理类中被拦截的接口方法Method实例
    // 接口方法参数
    // 用于调用父类真正的业务方法的方法代理
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println(o.getClass().getSuperclass().getName());
        before();
        Object result = methodProxy.invokeSuper(o, objects);
        after();
        return result;
    }
}


