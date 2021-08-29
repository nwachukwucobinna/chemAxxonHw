package com.odana.CostRegister.repository;

import com.odana.CostRegister.model.Cost;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CostRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private CostRepository costRepository;

    @Test
    public void testFindByUserAndInterval() {

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime earliest = now.minusDays(2);
        LocalDateTime latest = now.plusDays(2);
        String user = "test@test.com";
        List<Cost> costsByUserAndInterval;

        List<Cost> costs = Arrays.asList(
                new Cost(1,user, earliest),
                new Cost(1,user, now),
                new Cost(1,user, latest)
        );

        costs = costRepository.saveAll(costs);

        costsByUserAndInterval = costRepository.findByUserAndInterval(earliest, latest, user);

        //when a cost exists with the user and within the interval
        assertThat(costsByUserAndInterval)
                .isNotEmpty();
        assertThat(costsByUserAndInterval)
                .isEqualTo(costs);

        //when a cost exists with the user but not within the interval
        assertThat(costRepository.findByUserAndInterval(latest.plusDays(1), latest.plusDays(2), user))
                .isEmpty();

        //when a cost exists within the interval but not with the user
        assertThat(costRepository.findByUserAndInterval(earliest, latest, "fake@test.com"))
                .isEmpty();
    }
}
