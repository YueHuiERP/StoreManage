package com.rehoshi.util;

import net.sourceforge.pinyin4j.PinyinHelper;

public class PYUtil {
    /**
     * 提取每个汉字的首字母
     *
     * @param str
     * @return
     */
    public static String getPinYinHeadChar(String str) {
        StringBuilder convert = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char word = str.charAt(i);
            //提取汉字的首字母
            String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
            if (pinyinArray != null) {
                convert.append(pinyinArray[0].charAt(0));
            } else if (word >= '0' && word <= '9') {
                convert.append(word);
            } else if (word >= 'a' && word <= 'z') {
                convert.append(word);
            } else if (word >= 'A' && word <= 'Z') {
                convert.append(word);
            }
        }
        return convert.toString().toUpperCase();
    }
}
