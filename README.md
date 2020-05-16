# gne
java 版实现新闻内容自动抓取 
只需一个列表页url ,自动识别列表页主体区域，抓取详情url ，识别详情页中标题，作者，发布时间，正文内容。
正文内容识别用到计算文本密度算法。

python 原版 https://github.com/kingname/GeneralNewsExtractor/

程序采用okhttp/htmlunit + jsoup /正则表达式 三方jar

# 运行

App.java main 入口，目前测试了

    String url = "https://news.sina.cn/gn/?from=wrap";
    String url = "http://www.cjddsb.com/ym/xhy/";
    String url = "https://readhub.cn/topics";
    String url = "http://www.xinhuanet.com/fortune/";
    String url = "https://news.163.com/world/";
    String url = "http://military.people.com.cn/";
    String url = "https://new.qq.com/tag/82542";
    String url = "https://www.toutiao.com/";
    String url = "http://gongyi.hebnews.cn/";
    String url = "http://cn.chinadaily.com.cn/";
