package com.wj.proxy.staticproxy;

/**
 * 静态代理
 */
public class Star implements Show {
    @Override
    public void sing() {
        System.out.println("明星表演：lalalala。");
    }

    public static void main(String[] args) {
        Show s = new Star();
        ProxyMoney m = new ProxyMoney(s);
        ProxySell p = new ProxySell(m);
        p.sing();
    }
}

class ProxySell implements Show {
    Show s;

    public ProxySell(Show s) {
        this.s = s;
    }

    @Override
    public void sing() {
        System.out.println("业务代理：与金主协商。");
        s.sing();
        System.out.println("业务代理：演出完成，告诉金主。");
    }
}

class ProxyMoney implements Show {
    Show s;

    public ProxyMoney(Show s) {
        this.s = s;
    }

    @Override
    public void sing() {
        System.out.println("财务代理：签订表演合同。");
        s.sing();
        System.out.println("财务代理：收取表演费用。");
    }
}

interface Show {
    void sing();
}