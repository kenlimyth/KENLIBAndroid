package com.kenlib.http.retrofit_rxjava.dto;

import java.util.List;

public class ProjectHandlerDto {

    public int total;
    public List<ProjectHandler1Dto> rows;

    public HTDto.ComboCache comboCache;

    public class ComboCache {
        public List<PartnerType> WorkCategory;
    }
    public class PartnerType {

        public String value;
        public String text;
    }

}


