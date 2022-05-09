package com.kenlib.view.xxk;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.kenlib.android.R;


/**
 * Created by ken on 16/8/3.
 *
 */
public class TabContentFragment extends Fragment {

    private static final String EXTRA_CONTENT = "content";

    public static TabContentFragment newInstance(String content){
        Bundle arguments = new Bundle();
        arguments.putString(EXTRA_CONTENT, content);
        TabContentFragment tabContentFragment = new TabContentFragment();
        tabContentFragment.setArguments(arguments);
        return tabContentFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_tab_content, null);
        ((TextView)contentView.findViewById(R.id.tv_content)).setText(getArguments().getString(EXTRA_CONTENT)+"");
        return contentView;
    }
}
