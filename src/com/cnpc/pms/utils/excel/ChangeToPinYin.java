package com.cnpc.pms.utils.excel;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * Created by sunning on 2018/8/29.
 */
public class ChangeToPinYin {
    private HanyuPinyinOutputFormat format = null;
    private String[] pinyin;
    public ChangeToPinYin()
    {
        /**
         *设置声调格式：setToneType
         HanyuPinyinToneType.WITH_TONE_MARK      用声调符号表示，例如huáng
         HanyuPinyinToneType.WITH_TONE_NUMBER  用数字表示声调，例如：huang2
         HanyuPinyinToneType.WITHOUT_TONE           无声调表示，例如：huang
         设置特殊拼音ü的显示格式：setVCharType
         HanyuPinyinVCharType.WITH_U_UNICODE             以U表示该拼音，
         HanyuPinyinVCharType.WITH_U_AND_COLON       以U和一个冒号表示该拼音，
         HanyuPinyinVCharType.WITH_V                             以V表示该字符，
         设置大小写格式：setCaseType
         HanyuPinyinCaseType.LOWERCASE                   转换后以全小写方式输出
         HanyuPinyinCaseType.UPPERCASE                    转换后以全大写方式输出
         */
        format = new HanyuPinyinOutputFormat();
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        format.setVCharType(HanyuPinyinVCharType.WITH_U_UNICODE);
        format.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        pinyin = null;
    }
    // 转换单个字符
    public String getCharacterPinYin(char c)
    {
        try
        {
            pinyin = PinyinHelper.toHanyuPinyinStringArray(c, format);
        }
        catch (BadHanyuPinyinOutputFormatCombination e)
        {
            e.printStackTrace();
        }
        // 如果c不是汉字，toHanyuPinyinStringArray会返回null
        if (pinyin == null)
            return null;
        // 只取一个发音，如果是多音字，仅取第一个发音
        return pinyin[0];
    }

    // 转换一个字符串
    public String getStringPinYin(String str)
    {
        StringBuilder sb = new StringBuilder();
        String tempPinyin = null;
        for (int i = 0; i < str.length(); ++i)
        {
            tempPinyin = getCharacterPinYin(str.charAt(i));
            if (tempPinyin == null)
            {
                // 如果str.charAt(i)非汉字，则保持原样
                sb.append(str.charAt(i));
            }
            else
            {
                sb.append(tempPinyin);
            }
        }
        return sb.toString();
    }


    public static void main(String[] args) {
        ChangeToPinYin changeToPinYin = new ChangeToPinYin();
        String stringPinYin = changeToPinYin.getStringPinYin("1011111111111_呼家楼社区信息.xls");
        System.out.println(stringPinYin);
    }
}
