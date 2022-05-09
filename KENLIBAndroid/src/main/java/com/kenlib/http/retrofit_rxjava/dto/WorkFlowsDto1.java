package com.kenlib.http.retrofit_rxjava.dto;

import java.io.Serializable;
import java.util.List;

public class WorkFlowsDto1 implements Serializable {

    public String controlName;
    public String Text;
    public String Value;
    public String Type;
    public String ActionType;
    public String ConfirmText;
    public boolean Visible;
    public String SourceName;
    public String Source;
    public List<Items> Items;

    public class Items implements Serializable{
        public String Text;
        public String Value;
    }
}


