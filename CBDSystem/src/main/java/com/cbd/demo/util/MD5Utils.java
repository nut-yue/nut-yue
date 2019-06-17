package com.cbd.demo.util;

import java.security.MessageDigest;

/**
 * @ClassName : MD5
 * @Date ：2019/5/28 22:55
 * @Desc ：类的介绍：
 * @author：作者：王佳伟
 */
public class MD5Utils {
    /**
     * 加密算法
     * @param password
     * @return
     */
    public static final String md5(String password){
        //加密后的密码
        StringBuffer sb=null;
        try {
        //获取数据加密的对象
        MessageDigest digest = MessageDigest.getInstance("MD5");
        String s =password;
        byte[] bs = digest.digest( s.getBytes() );
        /*
         * 从数组中把每个元素取出来，每个数字变成字符串中 的2个字符，
         * 最后会得到一个拥有32位字符的一个字符串，最终数据库中存储是32位的字符数据
         * 取出每个数字中的最低位的一个字节数，然后把这个字节数据转成16进制即可
         */
         sb = new StringBuffer();
        for (byte b : bs) {
            int x = b & 0b1111_1111;
            if( x >= 0 && x <=15  ){
                sb.append("0");
                sb.append(Integer.toHexString(x));
            }else{
                sb.append(Integer.toHexString(x));
            }
        }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

}
