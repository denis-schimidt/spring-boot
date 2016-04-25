package com.hackday.elo7.rest.data.jpa.controller;

import static com.jayway.restassured.RestAssured.when;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.hackday.elo7.rest.data.jpa.RestApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = RestApplication.class)
@WebAppConfiguration
@IntegrationTest
public class HotelApiRestControllerTest {

	@Test
	public void deveRetornarVinteESeteHoteisEmTodasPaginacoes(){

		when()
			.get("/hoteis?pagina=0")
		.then()
			.statusCode(200)
			.body("$", hasSize(10));

		when()
			.get("/hoteis?pagina=1")
		.then()
			.statusCode(200)
			.body("$", hasSize(10));

		when()
			.get("/hoteis?pagina=2")
		.then()
			.statusCode(200)
			.body("$", hasSize(7));
	}

	@Test
	public void deveRetornarSequenciaHoteisOrdenadosPorNomeEmTodasPaginacoes(){

		when()
			.get("/hoteis?pagina=0")
		.then()
			.statusCode(200)
			.body("name",contains(new String[]{"70 Park Avenue Hotel", "Bath Travelodge", "Chilworth Manor",
				"Conrad Miami", "Conrad Treasury Place", "Doubletree", "Hilton Diagonal Mar", "Hilton Tel Aviv",
				"Hotel Allegro", "Hotel Beaulac"}));
		when()
			.get("/hoteis?pagina=1")
		.then()
			.statusCode(200)
			.body("name",contains(new String[]{"Hotel Rouge", "InterContinental Tokyo Bay", "Jameson Inn",
				"Marriot Downtown", "Marriott Courtyard", "Melia White House", "Radisson Suite Hotel Oceanfront",
				"Ritz Carlton", "Ritz Carlton", "Sea Horse Inn"}));

		when()
			.get("/hoteis?pagina=2")
		.then()
			.statusCode(200)
			.body("name",contains(new String[]{"Super 8 Eau Claire Campus Area", "Swissotel", "The Bath Priory Hotel",
				"The Langham", "W Lexington Hotel", "W Union Hotel", "Westin Diplomat"}));
	}

	@Test
	public void deveRetornarHotelComMenorZipCode(){

		when()
			.get("/hoteis?pagina=0&campoAOrdenar=zip&ordenacao=ASC")
		.then()
			.statusCode(200)
			.root("[0]")
			.body("zip", equalTo("08019"))
			.body("id", equalTo(7))
			.body("name", equalTo("Hilton Diagonal Mar"))
			.body("address", equalTo("Passeig del Taulat 262-264"))
			.body("city.name", equalTo("Barcelona"))
			.body("city.state", equalTo("Catalunya"))
			.body("city.country", equalTo("Spain"))
			.body("city.map", equalTo("41.387917, 2.169919"));
	}

	@Test
	public void deveRetornarHotelComMaiorZipCode(){

		when()
			.get("/hoteis?pagina=2&campoAOrdenar=zip&ordenacao=ASC")
		.then()
			.statusCode(200)
			.root("[6]")
			.body("zip", equalTo("SO16 7JF"))
			.body("id", equalTo(12))
			.body("name", equalTo("Chilworth Manor"))
			.body("address", equalTo("The Cottage, Southampton Business Park"))
			.body("city.name", equalTo("Southampton"))
			.body("city.state", equalTo("Hampshire"))
			.body("city.country", equalTo("UK"))
			.body("city.map", equalTo("50.902571, -1.397238"));
	}

	@Test
	public void naoDeveRetornarMaisDeTresPaginas(){

		when()
			.get("/hoteis?pagina=4")
		.then()
			.statusCode(200)
			.body("$", hasSize(0));
	}

	@Test
	public void deveRetornarSempreContentTypeJsonComUtf8(){

		when()
			.get("/hoteis")
		.then()
			.contentType("application/json;charset=UTF-8");
	}
}
