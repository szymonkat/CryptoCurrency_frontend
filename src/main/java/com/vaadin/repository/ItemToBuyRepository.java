package com.vaadin.repository;

import com.vaadin.domain.ItemToBuy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemToBuyRepository extends JpaRepository<ItemToBuy, Long> {

    @Override
    <S extends ItemToBuy> S save(S ItemToBuy);

}
