package com.example.demo;

import com.example.demo.entity.Flight;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
class SpringDataOverviewApplicationTests {

	@Autowired
	private EntityManager entityManager;


	@Test
	public void verifyFlightCanBeSaved(){
		final Flight flight = new Flight();
		flight.setOrigin("Amsterdam");
		flight.setDestination("New York");
		flight.setScheduledAt(LocalDateTime.parse("2022-01-10T12:12:00"));

		entityManager.persist(flight);

		final TypedQuery<Flight> results = entityManager.createQuery("SELECT f FROM Flight f", Flight.class);

		final List<Flight> resultList = results.getResultList();

		Assertions.assertThat(resultList)
				.hasSize(1)
				.first()
				.isEqualTo(flight);
	}

	@Test
	void contextLoads() {
	}

}
