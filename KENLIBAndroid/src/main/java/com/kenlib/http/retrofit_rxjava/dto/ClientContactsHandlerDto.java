package com.kenlib.http.retrofit_rxjava.dto;

import java.util.List;

public class ClientContactsHandlerDto {

    public int total;
    public List<ClientContactsHandlerDto1> rows;

    public ComboCache comboCache;

    public class ComboCache {
        public List<PartnerType> Sex;
    }
    public class PartnerType {

        public String value;
        public String text;
    }
    public class ClientContactsHandlerDto1{
        public String Name;
        public String JobPosition;
        public String Sex;
        public String Mobile;
        public String Email;
        public String ClientID;

    }




}


