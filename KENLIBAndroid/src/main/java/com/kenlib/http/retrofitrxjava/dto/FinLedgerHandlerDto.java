package com.kenlib.http.retrofitrxjava.dto;

import java.util.List;

public class FinLedgerHandlerDto {

    public int total;
    public List<FinLedgerHandlerDto1> rows;

    public ComboCache comboCache;

    public class ComboCache {
        public List<PartnerType> Sex;
    }

    public class PartnerType {

        public String value;
        public String text;
    }

    public class FinLedgerHandlerDto1 {
        public String BusType;
        public String Dr;
        public String Cr;
        public String RefDate;
        public String EngagementCode;
        public String ProjectCode;
        public String OrgName;

    }


}


