package com.kenlib.http.retrofitrxjava.dto;

import java.util.List;

public class BGDto {

    public int total;
    public List<BGDto1> rows;

    public ComboCache comboCache;

    public class ComboCache {

        public List<PartnerType> PartnerType;
    }
    public class PartnerType {

        public String value;
        public String text;
    }

    public class BGDto1 {

        public String ReportNo;
        public String ProjectName;
        public String ShortNamePrj;
        public String EngagementName;
        public String ReviewManagerName;
        public String ReportingTypeName;
        public String ReportingDate;
        public String WFID;
        public String ID;
    }
}


