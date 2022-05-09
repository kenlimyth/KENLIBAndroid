package com.kenlib.sample;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 动态代理
 * 静态代理
 */
public class ProxyTest {

    /**
     * 动态代理工厂
     */
    public static class ProxyFactory {
        private Object mTarget;

        /**
         * 维护一个目标对象
         */
        public ProxyFactory(Object target) {
            this.mTarget = target;
        }

        /**
         * 获取动态代理对象
         *
         * @return
         */
        public Object getProxyInstance() {
            return Proxy.newProxyInstance(mTarget.getClass().getClassLoader(), mTarget.getClass().getInterfaces(), new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    System.out.println("--------start-------");
                    Object returnValue = method.invoke(mTarget,args);
                    System.out.println("---------end--------");
                    return returnValue;
                }
            });
        }
    }

    /**
     * 目标接口，抽象购买行为
     */
    public interface IShopPhone{

        void  shopPhone(String string);
    }

    /**
     * 目标接口的实现类，实现具体的行为
     */
    public static class ShopPhone implements IShopPhone{

        @Override
        public void shopPhone(String string) {
            System.out.println(string);
        }
    }

    /**
     * 代理类，代理购买手机的行为。
     */
    public static class ProxyShopPhone implements IShopPhone {
        IShopPhone mTarget;

        /**
         * 目标
         */
        public ProxyShopPhone(IShopPhone target) {
            this.mTarget = target;
        }

        @Override
        public void shopPhone(String str) {
            System.out.println("-----start----");
            mTarget.shopPhone(str);
            System.out.println("-----end----");
        }
    }




    public static void main(String[] args) {

        //动态代理
//        ShopPhone shopPhone = new ShopPhone();
//        IShopPhone proxy2 = (IShopPhone) new ProxyFactory(shopPhone).getProxyInstance();
//        proxy2.shopPhone("通过代购商店，在香港买了一个iphone X，一共花了6666元");


        //静态代理
        ShopPhone shopPhone = new ShopPhone();
        ProxyShopPhone proxyShopPhone=new ProxyShopPhone(shopPhone);
        proxyShopPhone.shopPhone("通过代购，在香港买了一个iphone X，一共花了6666元");

    }




}
