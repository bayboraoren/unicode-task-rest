package com.unico.rest.data;

import com.unico.rest.data.model.Parameter;
import com.unico.rest.data.repository.ParameterRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Created by baybora on 2/10/18.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@ContextConfiguration(classes = {RepositoryConfig.class})
public class ParameterRepositoryTest {

    @Autowired
    private ParameterRepository parameterRepository;

    @Test
    public void shouldSaveParameters(){
        Long expectedId = 1L;

        int param1=1, expectedParam1 = 1;
        int param2=2, expectedParam2 = 2;

        Parameter parameter = Parameter.builder()
                .parameter1(param1)
                .parameter2(param2)
                .build();

        parameterRepository.save(parameter);
        assertThat(expectedId, is(parameter.getId()));
        assertThat(expectedParam1, is(parameter.getParameter1()));
        assertThat(expectedParam2, is(parameter.getParameter2()));
    }

}
