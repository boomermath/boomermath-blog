package com.boomermath.boomermathblog.data.repositories;

import com.blazebit.persistence.CriteriaBuilder;
import com.blazebit.persistence.CriteriaBuilderFactory;
import com.blazebit.persistence.view.EntityViewManager;
import com.blazebit.persistence.view.EntityViewSetting;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@Slf4j
public class BlogArticleViewRepository {
    @Autowired
    EntityManager em;
    @Autowired
    CriteriaBuilderFactory cbf;
    @Autowired
    EntityViewManager evm;

    public <T> T findById(EntityViewSetting<T, CriteriaBuilder<T>> setting, UUID id) {
        return evm.find(em, setting, id);
    }

    public <T> List<T> findAll(EntityViewSetting<T, ?> setting) {
        return evm.applySetting(setting,
                cbf.create(em, evm.getMetamodel().managedView(setting.getEntityViewClass()).getEntityClass())).getResultList();
    }
}
