package com.kenlib.http.retrofitrxjava.dto;

import java.util.List;

public class GDDto {

    public int total;
    public List<GDDto1> rows;

    public ComboCache comboCache;

    public class ComboCache {

        public List<PartnerType> IsValid;
    }
    public class PartnerType {

        public String value;
        public String text;
    }

    public class GDDto1 {
        public String ID;
        public String ArchiveNo;
        public String ArchiveName;
        public String ShortName;
        public String ProjectName;

        public String ReportNo;
        public String IsValid;
        public String CreateUserName;
        public String OTHours;
        public String Remark;
        public String LastModifyDate;
        public String WFID;
    }

}


