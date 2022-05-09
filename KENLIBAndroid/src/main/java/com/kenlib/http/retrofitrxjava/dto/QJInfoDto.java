package com.kenlib.http.retrofitrxjava.dto;

import java.util.List;

public class QJInfoDto {


    public List<QJInfoDto1> Table;

    public class QJInfoDto1 {
        public String ID;
        public String UserName;
        public String LeaveType;
        public String Reason;
        public String BeginTime;
        public String EndTime;
        public String TotalDays;
        public String ActualBeginTime;
        public String ActualEndTime;

        public String ActualTotalDays;
        public String WFID;
        public String WFStatus;
        public String LeaveTypeName;
        public String WFStatusName;


    }

}


