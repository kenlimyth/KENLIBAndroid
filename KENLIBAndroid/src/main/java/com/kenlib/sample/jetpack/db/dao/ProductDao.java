/*
 * Copyright 2017, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.kenlib.sample.jetpack.db.dao;


import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.kenlib.sample.jetpack.db.entity.ProductEntity;

@Dao
public interface ProductDao {
    @Query("SELECT * FROM products")
    List<ProductEntity> getAllProducts();

    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insertAll(List<ProductEntity> products);

    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insert(ProductEntity products);

    @Update(onConflict = OnConflictStrategy.IGNORE )
    void update(ProductEntity products);
    @Query("update products set name=:name")
    void updateAll(String name);

    @Delete
    void delete(ProductEntity products);
    @Query("delete from products")
    void deleteAll();

    @Query("select * from products where id = :productId")
    ProductEntity getProductOne(int productId);
}
