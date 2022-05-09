package com.kenlib.http.retrofit_rxjava.dto;

import java.util.List;

public class GGDto {

    public int total;
    public List<GGDto1> rows;

    public ComboCache comboCache;

    public class ComboCache {
        public List<PartnerType> WorkCategory;
    }
    public class PartnerType {

        public String value;
        public String text;
    }
    public class GGDto1{
        public String ID;
        public String Title;
        public String CreateDate;
        public String TypeName;
        public String WFID;

    }




}


