package com.kenlib.http.retrofitrxjava.dto;

import java.util.List;

public class ClientinfoDto {

    public int total;
    public List<ClientinfoDto1> rows;

    public ComboCache comboCache;

    public class ComboCache {

        public List<ClientType> ClientType;
    }
    public class ClientType {

        public String value;
        public String text;
    }

}


