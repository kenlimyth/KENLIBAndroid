package com.kenlib.sample.jetpack.viewmodel;

import java.util.Date;
import java.util.List;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.kenlib.sample.jetpack.db.AppDatabase;
import com.kenlib.sample.jetpack.db.dao.ProductDao;
import com.kenlib.sample.jetpack.db.entity.ProductEntity;
import com.kenlib.sample.jetpack.dto.UserInfo;
import com.kenlib.util.ApplicationUtil;


public class MainViewModel extends ViewModel {

    private int id;
    public MutableLiveData<UserInfo> userInfoMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<List<ProductEntity>> product = new MutableLiveData<>();

    public void getProductList() {
        ProductDao productDao = AppDatabase.getInstance(ApplicationUtil.getContext()).productDao();
        int id1 = ++id;
        ProductEntity productEntity = productDao.getProductOne(id1);
        if (productEntity != null) {
            productDao.delete(productEntity);
        }
        ProductEntity productEntity1 = new ProductEntity();
        productEntity1.setId(id1);
        productEntity1.setName(new Date().toString());
        productDao.insert(productEntity1);

        product.setValue(productDao.getAllProducts());
    }

    public void del() {
        ProductDao productDao = AppDatabase.getInstance(ApplicationUtil.getContext()).productDao();
        productDao.deleteAll();
        product.setValue(productDao.getAllProducts());
    }

    public void update() {
        ProductDao productDao = AppDatabase.getInstance(ApplicationUtil.getContext()).productDao();
        productDao.updateAll("update");
        product.setValue(productDao.getAllProducts());
    }

    @Override
    protected void onCleared() {
        super.onCleared();

    }
}

