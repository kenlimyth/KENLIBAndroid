package com.kenlib.http.retrofit_rxjava.dto;

import java.util.List;

public class XMCYDto {

    public int total;
    public List<XMCYDto1> rows;

    public ComboCache comboCache;

    public class ComboCache {
        public List<PartnerType> Responsibility;
    }
    public class PartnerType {

        public String value;
        public String text;
    }
    public class XMCYDto1{
        public String UserName;
        public String GradeName;
        public String PlanStartDate;
        public String PlanEndDate;
        public String PlanTotalHours;
        public String ActualTotalHours;
        public String Responsibility;

    }




}


