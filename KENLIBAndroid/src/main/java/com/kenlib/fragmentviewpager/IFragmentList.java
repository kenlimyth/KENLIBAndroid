package com.kenlib.fragmentviewpager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import androidx.fragment.app.Fragment;


/**
 * 接口
 * 
 */
public interface IFragmentList {

	ArrayList<Fragment> addFragment(List<Map<String, String>> list);

}