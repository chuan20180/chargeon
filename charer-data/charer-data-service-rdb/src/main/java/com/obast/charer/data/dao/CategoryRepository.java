package com.obast.charer.data.dao;

import com.obast.charer.data.model.TbCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<TbCategory, String> {

}
