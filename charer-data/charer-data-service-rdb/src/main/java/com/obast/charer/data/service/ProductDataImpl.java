package com.obast.charer.data.service;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.utils.JsonUtils;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.data.AbstractCommonData;
import com.obast.charer.data.IJPACommData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.data.platform.IProductData;
import com.obast.charer.data.dao.ProductRepository;
import com.obast.charer.data.model.TbProduct;
import com.obast.charer.enums.ProductTypeEnum;
import com.obast.charer.model.product.Product;
import com.obast.charer.qo.ProductQueryBo;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;


@Primary
@Service
@RequiredArgsConstructor
public class ProductDataImpl extends AbstractCommonData<ProductQueryBo>
        implements IProductData, IJPACommData<Product, String>, IJPACommonData<Product, ProductQueryBo, String> {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public JpaRepository<?,?> getBaseRepository() {
        return productRepository;
    }

    @Override
    public Class<?> getJpaRepositoryClass() {
        return TbProduct.class;
    }

    @Override
    public Class<?> getTClass() {
        return Product.class;
    }

    @Override
    public Paging<Product> findPage(PageRequest<ProductQueryBo> request) {
        Specification<TbProduct> specification = buildSpecification(request.getData());
        Page<TbProduct> page = productRepository.findAll(specification, processPageSort(request));
        List<TbProduct> list = page.getContent();
        List<Product> products = new ArrayList<>();
        for (TbProduct product : list) {
            Product entity = fillProduct(product);
            products.add(entity);
        }
        long total = page.getTotalElements();
        return new Paging<>(total, products);
    }

    @Override
    public List<Product> findList(ProductQueryBo queryBo) {
        Specification<TbProduct> specification = buildSpecification(queryBo);
        List<TbProduct> list = productRepository.findAll(specification);
        List<Product> products = new ArrayList<>();
        for (TbProduct product : list) {
            Product entity = fillProduct(product);
            products.add(entity);
        }
        return products;
    }

    @Override
    public List<Product> findByCategory(String category) {
        return MapstructUtils.convert(productRepository.findByCategory(category), Product.class);
    }

    @Override
    public Product findByProductKey(String productKey) {
        ProductQueryBo bo = new ProductQueryBo();
        bo.setProductKey(productKey);
        Specification<TbProduct> specification = buildSpecification(bo);
        TbProduct entity = productRepository.findOne(specification).orElse(null);
        return fillProduct(entity);
    }

    @Override
    public Product findById(String id) {
        TbProduct entity = productRepository.findById(id).orElse(null);
        return fillProduct(entity);
    }

    private Product fillProduct(TbProduct product) {
        if (product == null) {
            return null;
        }

        Product entity = MapstructUtils.convert(product, Product.class);
        if(entity == null) {
            return null;
        }
        if(product.getType().equals(ProductTypeEnum.Charger)) {
            entity.setCharger(JsonUtils.parseObject(product.getProperties(), Product.Charger.class));
        }

        return entity;
    }

    public Specification<TbProduct> buildSpecification(ProductQueryBo bo) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(bo.getName() != null) {
                Predicate predicate = cb.like(root.get("name"), bo.getName());
                predicates.add(predicate);
            }

            if(bo.getStatus() != null) {
                Predicate predicate = cb.equal(root.get("status"), bo.getStatus().getCode());
                predicates.add(predicate);
            }

            if(bo.getType() != null) {
                Predicate predicate = cb.equal(root.get("type"), bo.getType());
                predicates.add(predicate);
            }

            if(StringUtils.isNoneBlank(bo.getProductKey())) {
                Predicate statusPredicate = cb.equal(root.get("productKey"), bo.getProductKey());
                predicates.add(statusPredicate);
            }

            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
    }
}
