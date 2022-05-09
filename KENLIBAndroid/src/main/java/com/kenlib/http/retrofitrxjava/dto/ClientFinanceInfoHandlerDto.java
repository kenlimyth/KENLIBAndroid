package com.kenlib.http.retrofitrxjava.dto;

import java.util.List;

public class ClientFinanceInfoHandlerDto {

    public int total;
    public List<ClientFinanceInfoHandlerDto1> rows;

    public ComboCache comboCache;

    public class ComboCache {
        public List<PartnerType> Sex;
    }

    public class PartnerType {

        public String value;
        public String text;
    }

    public class ClientFinanceInfoHandlerDto1 {
        public String FinYear;
        public String TotalAssets;
        public String TotalLiabilities;
        public String TotalNetAssets;
        public String Revenue;
        public String NetProfit;

    }


}


