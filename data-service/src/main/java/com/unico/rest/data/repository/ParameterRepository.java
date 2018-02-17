package com.unico.rest.data.repository;

import com.unico.rest.data.model.Parameter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.CompletableFuture;

/**
 * Created by baybora on 2/10/18.
 */
public interface ParameterRepository extends JpaRepository<Parameter,Long>{

    @Async("TASK_EXECUTER")
    CompletableFuture<Page<Parameter>> findAllBy(final Pageable pageable);

}
