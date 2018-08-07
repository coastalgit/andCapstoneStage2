package com.bf.portugo.datacreation;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.bf.portugo.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class DBFuncsActivity extends AppCompatActivity {

    DBFuncsViewModel mViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dbfuncs);

        ButterKnife.bind(this);

        mViewModel = ViewModelProviders.of(this).get(DBFuncsViewModel.class);
    }

    @OnClick(R.id.btn_addstockdata_reg)
    public void btnAddStockData_Category1_onClick(Button btn){
        mViewModel.populateFbWithStockData_All();
    }

    @OnClick(R.id.btn_addstockdata_irreg)
    public void btnAddStockData_Category2_onClick(Button btn){
        mViewModel.populateFbWithStockData_NextItem();
    }
}
