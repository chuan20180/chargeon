package com.obast.charer.data.service;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.common.utils.StringUtils;
import com.obast.charer.data.AbstractCommonData;
import com.obast.charer.data.IJPACommData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.data.business.ITaskInfoData;
import com.obast.charer.data.dao.TaskInfoRepository;
import com.obast.charer.data.model.TbTaskInfo;
import com.obast.charer.model.task.TaskInfo;
import com.obast.charer.qo.TaskInfoQueryBo;
import lombok.RequiredArgsConstructor;
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
public class TaskInfoDataImpl extends AbstractCommonData<TaskInfoQueryBo>
        implements ITaskInfoData, IJPACommData<TaskInfo, String>, IJPACommonData<TaskInfo, TaskInfoQueryBo, String> {

    @Autowired
    private TaskInfoRepository baseRepository;

    @Override
    public JpaRepository<?,?> getBaseRepository() {
        return baseRepository;
    }

    @Override
    public Class<?> getJpaRepositoryClass() {
        return TbTaskInfo.class;
    }

    @Override
    public Class<?> getTClass() {
        return TaskInfo.class;
    }

    @Override
    public Paging<TaskInfo> findPage(PageRequest<TaskInfoQueryBo> request) {
        Specification<TbTaskInfo> specification = buildSpecification(request.getData());
        Page<TbTaskInfo> page = baseRepository.findAll(specification, processPageSort(request));
        List<TbTaskInfo> list = page.getContent();
        long total = page.getTotalElements();

        List<TaskInfo> newList = new ArrayList<>();
        for(TbTaskInfo tbSysCountry: list) {
            newList.add(fillData(tbSysCountry));
        }
        return new Paging<>(total, newList);
    }

    @Override
    public List<TaskInfo> findList(TaskInfoQueryBo queryBo) {
        Specification<TbTaskInfo> specification = buildSpecification(queryBo);
        List<TbTaskInfo> list = baseRepository.findAll(specification);
        List<TaskInfo> newList = new ArrayList<>();
        for(TbTaskInfo tbTaskInfo: list) {
            newList.add(fillData(tbTaskInfo));
        }
        return newList;
    }

    public Specification<TbTaskInfo> buildSpecification(TaskInfoQueryBo bo) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(bo != null) {
                if(StringUtils.isNoneBlank(bo.getName())) {
                    predicates.add(cb.equal(root.get("name"), bo.getName()));
                }
            }
            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
    }

    @Override
    public TaskInfo update(TaskInfo bo) {
        return fillData(baseRepository.saveAndFlush(bo.to(TbTaskInfo.class)));
    }


    private TaskInfo fillData(TbTaskInfo tbTaskInfo) {
        return MapstructUtils.convert(tbTaskInfo, TaskInfo.class);
    }
}
