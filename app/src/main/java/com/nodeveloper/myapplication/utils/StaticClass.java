package com.nodeveloper.myapplication.utils;

public class StaticClass {

    //闪屏延时
    public static final int HANDLER_SPLASH = 1001;
    //是否第一次启动
    public static final String SHARE_IS_FIRST = "isFirst";

    //Bugly key
    public static final String BUGLY_APPID = "6d46231d08";
    //Bmob key
    public static final String BMOB_APPID = "b82ba455d8931fb5d967cdf9d0c52c6f";
    //是否记住密码
    public static final String KEEP_PASS = "keeppass";
    //用户名
    public static final String USER_NAME = "uname";
    //密码
    public static final String USER_PASS = "upass";
    //头像
    public static final String IMAGE_TITLE = "image_avatar";

    //快递key
    private static final String JUHE_EXPRESS_KEY = "a4e6952c07acfef5ed60aeec59629c46";
    //归属地key
    private static final String JUHE_BELONG_KEY = "0119af526758e4fce0d5c71968e98806";
    //微信精选key
    private static final String JUHE_WECHAT_KEY = "a36552eda7bfe2a695ee0cf05b683711";

    //快递100 API
    public static final String EXPRESS100_QUERY_URL = "http://www.kuaidi100.com/query";

    //快递API URL
    public static final String EXPRESS_QUERY_URL = "http://v.juhe.cn/exp/index?key=" + JUHE_EXPRESS_KEY;
    //归属地API URL
    public static final String BELONG_QUERY_URL = "http://apis.juhe.cn/mobile/get?key=" + JUHE_BELONG_KEY;
    //微信精选API URL
    public static final String WECHAT_ARTICLE_URL = "http://v.juhe.cn/weixin/query?key=" + JUHE_WECHAT_KEY;
    //妹子接口
    public static final String GIRL_URL = "https://gank.io/api/data/%E7%A6%8F%E5%88%A9/50/1";
    //图灵机器人key
    public static final String TULING_KEY = "741ee46f9630aa9f7a996ed9f603fd66";
    //图灵机器人url
    public static final String TULING_URL = "741ee46f9630aa9f7a996ed9f603fd66";
    //今日头条Key
    public static final String NEWS_KEY = "c06e200b75ae578fdc160b5d93bf9de5";
    //今日头条
    public static final String NEWS_URL = "http://v.juhe.cn/toutiao/index?type=top&key="+NEWS_KEY;
}
