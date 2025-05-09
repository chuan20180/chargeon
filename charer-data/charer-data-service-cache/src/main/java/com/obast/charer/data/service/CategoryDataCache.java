package com.obast.charer.data.service;

import com.obast.charer.data.cache.CategoryCacheEvict;
import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.constant.Constants;
import com.obast.charer.data.business.ICategoryData;
import com.obast.charer.model.product.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
@Qualifier("categoryDataCache")
public class CategoryDataCache implements ICategoryData {

    @Autowired
    private ICategoryData categoryData;
    @Autowired
    private CategoryCacheEvict categoryCacheEvict;

    @Override
    @Cacheable(value = Constants.CACHE_CATEGORY, key = "#root.method.name+#s", unless = "#result == null")
    public Category findById(String s) {
        return categoryData.findById(s);
    }

    @Override
    public List<Category> findByIds(Collection<String> id) {
        return Collections.emptyList();
    }

    @Override
    public Category save(Category data) {
        data = categoryData.save(data);
        categoryCacheEvict.findById(data.getId());
        return data;
    }

    @Override
    public void batchSave(List<Category> data) {

    }

    @Override
    public void deleteById(String s) {
        categoryData.deleteById(s);
    }

    @Override
    public void deleteByIds(Collection<String> strings) {

    }

    @Override
    public long count() {
        return categoryData.count();
    }

    @Override
    public List<Category> findAll() {
        return categoryData.findAll();
    }

    @Override
    public Paging<Category> findAll(PageRequest<Category> pageRequest) {
        return categoryData.findAll(pageRequest);
    }

    @Override
    public List<Category> findAllByCondition(Category data) {
        return Collections.emptyList();
    }

    @Override
    public Category findOneByCondition(Category data) {
        return null;
    }

}
