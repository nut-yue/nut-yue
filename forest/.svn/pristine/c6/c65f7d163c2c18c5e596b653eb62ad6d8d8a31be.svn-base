package com.project.getSql;
import java.util.Map;
public class LogSqlProvider {
    public String getLogByCondiction(Map<String,String> condition){

        String sql="select id, logContent,logDate from t_log where 1=1 ";
        String startDate=condition.get("startDate");
        String endDate=condition.get("endDate");
        if(startDate!=null&&startDate.length()!=0){
           sql+=" and logDate>='"+startDate+"'";
        }
        if(endDate!=null&&startDate.length()!=0){
            sql+=" and logDate=<'"+endDate+"'";
        }
        return sql;
    }

}
