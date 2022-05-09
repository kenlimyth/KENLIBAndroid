package com.kenlib.http.retrofitrxjava.dto;

import java.util.List;

public class HHRWPDto {


    public int total;
    public List<HHRWPDto1> rows;

    public ComboCache comboCache;

    public class ComboCache {

        public List<PartnerType> PartnerType;
    }
    public class PartnerType {

        public String value;
        public String text;
    }

    public class HHRWPDto1 {

        public String OrgFullName;
        public String PartnerName;
        public String Percentage;
        public String PartnerType;
        public String CreateDate;
        public String ClientFullNames;
    }
}


