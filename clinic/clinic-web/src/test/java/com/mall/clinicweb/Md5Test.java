package com.mall.clinicweb;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.junit.Test;

/**
 * @ClassName : Md5Test
 * @Date ：2020/2/15 22:00
 * @author：nut-yue
 */
public class Md5Test {

    @Test
    public void md5 (){
        Md5Hash md5Hash = new Md5Hash("admin", "clinic");
        System.out.println(md5Hash);
    }
}
