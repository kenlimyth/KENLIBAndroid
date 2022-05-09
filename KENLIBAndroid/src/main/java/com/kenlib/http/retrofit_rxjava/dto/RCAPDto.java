package com.kenlib.http.retrofit_rxjava.dto;

import java.util.List;

public class RCAPDto {

    public int total;
    public List<RCAPDto1> rows;

    public ComboCache comboCache;

    public class ComboCache {

        public List<PartnerType> CostType;
    }
    public class PartnerType {

        public String value;
        public String text;
    }

    public class RCAPDto1 {
        public String Title;
        public String Content;
        public String Categroy;
        public String BeginDate;
        public String BeginTime;

        public String EndDate;
        public String EndTime;
        public String ID;
        public String CompleteComment;
    }

}


