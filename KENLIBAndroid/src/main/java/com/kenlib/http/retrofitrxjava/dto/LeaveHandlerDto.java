package com.kenlib.http.retrofitrxjava.dto;

import java.util.List;

public class LeaveHandlerDto {

    public int total;
    public List<LeaveHandlerDto1> rows;

    public ComboCache comboCache;

    public class ComboCache {

        public List<PartnerType> LeaveType;
        public List<PartnerType> WFStatus;
    }
    public class PartnerType {

        public String value;
        public String text;
    }

    public class LeaveHandlerDto1 {

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
    }
}


