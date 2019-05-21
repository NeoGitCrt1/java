package com.company;

import org.apache.commons.lang3.StringUtils;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author ysy
 * @version v1.0
 * @description T1
 * @date 2019-03-18 08:55
 */
public class T1 {
    public static void main(String[] a) {
        int loop = 5000;
        Mesure.loop(loop, s -> {
            StringUtils.replace(s, "${client}", "wechat");
            StringUtils.replace(s, "${memberId}", "9527");
            return null;
        }, "StringUtils rep cm", "https://dev-webapp.tcjk.com/?needLogin=true&client=${client}&memberId=${memberId}#inviteCode.html");

        Mesure.loop(loop, s -> {
            StringUtils.replace(s, "${client}", "wechat",1);
            StringUtils.replace(s, "${memberId}", "9527",1);
            return null;
        }, "StringUtils rep cm max 1", "https://dev-webapp.tcjk.com/?needLogin=true&client=${client}&memberId=${memberId}#inviteCode.html");

        Mesure.loop(loop, s -> {
            String url = s;
            url.replace("${client}", "wechat").replace("${memberId}", "9527");
            return null;
        }, "native rep cm", "https://dev-webapp.tcjk.com/?needLogin=true&client=${client}&memberId=${memberId}#inviteCode.html");

        Mesure.loop(loop, s -> {
            StringUtils.replace(s, "${client}", "wechat");
            StringUtils.replace(s, "${memberId}", "9527");
            return null;
        }, "StringUtils rep c", "https://dev-webapp.tcjk.com/?needLogin=true&client=${client}#inviteCode.html");

        Mesure.loop(loop, s -> {
            StringUtils.replace(s, "${client}", "wechat",1);
            StringUtils.replace(s, "${memberId}", "9527",1);
            return null;
        }, "StringUtils rep c max 1", "https://dev-webapp.tcjk.com/?needLogin=true&client=${client}#inviteCode.html");

        Mesure.loop(loop, s -> {
            String url = s;
            url.replace("${client}", "wechat").replace("${memberId}", "9527");
            return null;
        }, "native rep c", "https://dev-webapp.tcjk.com/?needLogin=true&client=${client}#inviteCode.html");

        Mesure.loop(loop, s -> {
            StringUtils.replace(s, "${client}", "wechat",1);
            StringUtils.replace(s, "${memberId}", "9527",1);
            return null;
        }, "StringUtils rep max 1", "https://dev-webapp.tcjk.com/?needLogin=true#inviteCode.html");

        Mesure.loop(loop, s -> {
            String url = s;
            url.replace("${client}", "wechat").replace("${memberId}", "9527");
            return null;
        }, "native rep ", "https://dev-webapp.tcjk.com/?needLogin=true#inviteCode.html");

    }
}
