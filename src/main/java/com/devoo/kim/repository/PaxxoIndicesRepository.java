package com.devoo.kim.repository;

import com.devoo.kim.domain.paxxo.Maker;
import com.devoo.kim.domain.paxxo.PaxxoItem;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * Created by rikim on 2017. 8. 22..
 */
@Repository
public class PaxxoIndicesRepository {

    public void save(Collection<Maker> makers) {}

    public void saveItems(Collection<PaxxoItem> items) {}
}
