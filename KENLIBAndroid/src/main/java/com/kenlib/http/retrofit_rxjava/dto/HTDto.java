package com.kenlib.http.retrofit_rxjava.dto;

import java.util.List;

public class HTDto {


    public int total;
    public List<HTDto1> rows;
    public ComboCache comboCache;

    public class ComboCache {
        public List<PartnerType> Category;
    }

    public class PartnerType {

        public String value;
        public String text;
    }

    public class HTDto1 {

        public String ContractID;
        public String NumberingDate;
        public String ProjectName;
        public String SignUserName;
        public String CurrencyName;
        public String TotalAmount;
        public String Category;
        public String ClientCode;
        public String EngagementName;
        public String WFID;
        public String ContractCode;
        public String ProjectID;
        public String ProjectCode;
        public String ClientID;
        public String ChineseName;
        public String OrgName;
        public String ClientSignUser;
        public String StartDate;
        public String EndDate;
        public String PaymentMethod;
        public String UploadFilePath;
        public String UploadFileName;
    }
}


