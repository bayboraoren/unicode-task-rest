package com.unico.rest.data;

import com.unico.rest.data.model.Parameter;
import com.unico.rest.data.repository.ParameterRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;

/**
 * Created by baybora on 2/10/18.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@ContextConfiguration(classes = {RepositoryConfig.class})
public class ParameterRepositoryTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ParameterRepositoryTest.class);

    @Autowired
    private ParameterRepository parameterRepository;

    @Test
    public void shouldSaveParameters() {
        Parameter parameter = createParameterDummy();
        parameterRepository.save(createParameterDummy());
        assertThat(parameter.getId(), is(parameter.getId()));
        assertThat(parameter.getParameter1(), is(parameter.getParameter1()));
        assertThat(parameter.getParameter2(), is(parameter.getParameter2()));
    }

    private final int PAGE_NUMBER = 0;
    private final int PAGE_SIZE = 1;

    @Test
    public void shouldGetParameters() throws Exception {
        parameterRepository.save(createParameterDummy());
        parameterRepository
                .findAllBy(createPageable())
                .thenAccept(pageParameters -> {
                    assertThat(pageParameters.getContent().size(), greaterThan(0));
                    pageParameters.forEach(parameter -> LOGGER.info("ID:" + parameter.getId()));
                    LOGGER.info("get parameters async COMPLETED.");
                });
    }


    private Pageable createPageable() {
        return new PageRequest(PAGE_NUMBER, PAGE_SIZE);
    }

    private Page<Parameter> createPageParameter() {
        return new PageImpl<>(createParameterList(), createPageable(), PAGE_NUMBER);
    }

    private List<Parameter> createParameterList() {
        List<Parameter> parameterList = new ArrayList<>();
        parameterList.add(createParameter());
        return parameterList;
    }

    private Parameter createParameter() {
        return Parameter.builder().id(1L).parameter1(1).parameter2(2).insertTimestamp(new Date()).build();
    }

    private Parameter createParameterDummy() {
        return Parameter.builder().parameter1(1).parameter2(2).insertTimestamp(new Date()).build();
    }
}
