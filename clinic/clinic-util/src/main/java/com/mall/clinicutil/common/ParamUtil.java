package com.mall.clinicutil.common;


import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.validation.constraints.NotNull;
import java.util.Iterator;
import java.util.Map;

/**
 * @ClassName : ParamUtil
 * @Date ：2020/2/15 17:52
 * @author：nut-yue
 */
@Component
public class ParamUtil {

    public Map<String,Object> getParam(@NotNull Map<String,Object> param){
        Iterator<Map.Entry<String, Object>> iterator = param.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> next = iterator.next();
            String key = next.getKey();
            if (ObjectUtils.isEmpty(next.getValue())) {
                iterator.remove();
            }
            if (("currentPage").equals(key) || ("pageSize").equals(key)){
                iterator.remove();
            }
        }
        return param;
    }
}
