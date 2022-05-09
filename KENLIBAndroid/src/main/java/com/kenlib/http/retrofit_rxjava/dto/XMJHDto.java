package com.kenlib.http.retrofit_rxjava.dto;

import java.util.List;

public class XMJHDto {

    public int total;
    public List<XMJHDto1> rows;

    public ComboCache comboCache;

    public class ComboCache {
        public List<PartnerType> WorkCategory;
    }
    public class PartnerType {

        public String value;
        public String text;
    }
    public class XMJHDto1{
        public String WorkTitle;
        public String StartDate;
        public String EndDate;
        public String ActualStartDate;
        public String ActualEndDate;
        public String TotalHours;
        public String ActualTotalHours;
        public String WorkCategory;

    }




}


