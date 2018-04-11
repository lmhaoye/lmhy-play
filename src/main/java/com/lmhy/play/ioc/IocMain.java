package com.lmhy.play.ioc;

public class IocMain {

    IocInterface iocInterface;

    public void setIocInterface(IocInterface iocInterface) {
        this.iocInterface = iocInterface;
    }
    public void go(){
        System.out.println(iocInterface.say("ioc is good"));
    }

    public static void main(String[] args) {
        //初始化IOC容器
        IocCore.init();

        IocMain main = IocCore.get("main");
        IocInterface iocInterface = IocCore.get("ioc");
        main.setIocInterface(iocInterface);

        main.go();

    }
}
