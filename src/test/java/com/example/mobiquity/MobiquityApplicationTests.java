package com.example.mobiquity;

import com.example.mobiquity.validation.EmailValidation;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class MobiquityApplicationTests {
	private String endpoint = "http://localhost:3000";
	private String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
			+ "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
	@Test
	void test() {
		int userid = (int)((ArrayList)given()
				.queryParam("username", "Delphine")
				.when()
				.get(endpoint + "/users")
				.then()
				.assertThat()
				.statusCode(200)
				.body(Matchers.notNullValue())
				.extract()
				.path("id"))
				.get(0);

		ArrayList posts = given()
				.queryParam("userId", userid)
				.when()
				.get(endpoint + "/posts")
				.then()
				.assertThat()
				.statusCode(200)
				.body(Matchers.notNullValue())
				.extract()
				.path("id");

		posts.forEach(postId -> {

			ArrayList<String> emails = (ArrayList)given()
					.queryParam("postId", postId)
					.when()
					.get(endpoint + "/comments")
					.then()
					.assertThat()
					.statusCode(200)
					.body(Matchers.notNullValue())
					.extract()
					.path("email");

			emails.forEach(emailAddress -> {
				boolean isEmailValid = EmailValidation.patternMatches(emailAddress, regexPattern);
				System.out.println(emailAddress+ " : "+isEmailValid);
				assertTrue(isEmailValid);
			});
		});
	}

}
