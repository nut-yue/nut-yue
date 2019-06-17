package com.cbd.demo.util;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * @ClassName : PageUtil
 * @Date ：2019/6/1 18:07
 * @Desc ：类的介绍：
 * @author：岳超
 */
public class PageUtil {

    public static IPage transform(IPage page){
        IPage pages = new Page();
        pages.setTotal(page.getTotal());
        pages.setSize(page.getSize());
        pages.setCurrent(page.getCurrent());
        return pages;
    }
}
