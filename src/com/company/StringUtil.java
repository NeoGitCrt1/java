package com.company;

/**
 * @author ysy
 * @version v1.0
 * @description StringUtil
 * @date 2019-02-22 14:12
 */
public class StringUtil {


    public static int compareVersion(String version1, String version2) {
        String[] versionArray1 = version1.split("\\.");//注意此处为正则匹配，不能用"."；
        for(int i = 0 ; i<versionArray1.length ; i++){ //如果位数只有一位则自动补零（防止出现一个是04，一个是5 直接以长度比较）
            if(versionArray1[i].length() == 1){
                versionArray1[i] = "0" + versionArray1[i];
            }
        }
        String[] versionArray2 = version2.split("\\.");
        for(int i = 0 ; i<versionArray2.length ; i++){//如果位数只有一位则自动补零
            if(versionArray2[i].length() == 1){
                versionArray2[i] = "0" + versionArray2[i];
            }
        }
        int idx = 0;
        int minLength = Math.min(versionArray1.length, versionArray2.length);//取最小长度值
        int diff = 0;
        while (idx < minLength
                && (diff = versionArray1[idx].length() - versionArray2[idx].length()) == 0//先比较长度
                && (diff = versionArray1[idx].compareTo(versionArray2[idx])) == 0) {//再比较字符
            ++idx;
        }
        //如果已经分出大小，则直接返回，如果未分出大小，则再比较位数，有子版本的为大；
        diff = (diff != 0) ? diff : versionArray1.length - versionArray2.length;
        return diff;
    }

    public static int compareVersion2(String version1, String version2) {
        boolean end = false;
        int v1DotPos = -1;
        int v1DotPosOld = -1;
        int v2DotPos = -1;
        int v2DotPosOld = -1;
        int comp = 0;
        while (!end) {
            v1DotPosOld = v1DotPos + 1;
            v2DotPosOld = v2DotPos + 1;
            v1DotPos = version1.indexOf(".", v1DotPosOld );
            v2DotPos = version2.indexOf(".", v2DotPosOld );
            comp = Integer.valueOf(version1.substring(v1DotPosOld, v1DotPos < 0? version1.length(): v1DotPos)).compareTo(Integer.valueOf(version2.substring(v2DotPosOld, v2DotPos < 0? version2.length(): v2DotPos)));
            if (comp != 0) {
                return comp;
            }
            end = v1DotPos < 0 & v2DotPos < 0;
        }
        return 0;
    }
}
