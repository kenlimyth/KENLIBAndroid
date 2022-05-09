package com.kenlib.http.retrofitrxjava.dto;

import java.util.List;

public class XMCBDto {

    public int total;
    public List<XMCBDto1> rows;

    public ComboCache comboCache;

    public class ComboCache {

        public List<PartnerType> CostType;
    }
    public class PartnerType {

        public String value;
        public String text;
    }

    public class XMCBDto1 {

        public String ExpenseItem;
        public String CostType;
        public String Remark;
        public String BudgetValue;
        public String ActualValue;
        public String LastModifyDate;
    }
}


