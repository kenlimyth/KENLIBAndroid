package com.kenlib.http.retrofit_rxjava.dto;

import java.util.List;

public class GDInfoDto {


    public List<Info> info;
    public List<Detail> detail;

    public class Info {

        public String ArchiveNo;
        public String VolumnNo;
        public String ArchiveName;
        public String ClientID;
        public String ShortName;
        public String ProjectID;
        public String ProjectName;
        public String CreateDate;
        public String IsValidName;
        public String ArchiveTypeName;
        public String Remark;
        public String IsDestroyed;
        public String WFID;


    }

    public class Detail {

        public String OriginalFileName;
        public String ShelfNo;
        public String FileSizeV;
        public String DigitalFlag;
        public String FilePathV;


    }

}


