package com.kenlib.http.retrofit_rxjava.dto;

import java.util.List;

public class GSDto {

    public int total;
    public List<GSDto1> rows;

    public ComboCache comboCache;

    public class ComboCache {

        public List<PartnerType> WorklogType;
    }
    public class PartnerType {

        public String value;
        public String text;
    }

    public class GSDto1 {
        public String UserName;
        public String TimeSheetDate;
        public String WorklogType;
        public String WorkType;
        public String FromTime;

        public String ToTime;
        public String TotalHours;
        public String ID;
        public String OTHours;
        public String Remark;
    }

}


