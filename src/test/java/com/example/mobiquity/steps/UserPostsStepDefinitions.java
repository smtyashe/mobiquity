package com.example.mobiquity.steps;

import com.example.mobiquity.validation.EmailValidation;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.emptyArray;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserPostsStepDefinitions {

    @Value("${resource.endpoint.host}")
    private String host;

    @Value("${resource.endpoint.port}")
    private String port;
    final private String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
    private final String USERS_ENDPOINT = "/users";
    private final String POSTS_ENDPOINT = "/posts";
    private final String COMMENTS_ENDPOINT = "/comments";
    private int userid;
    private ArrayList posts;
    private ExtractableResponse userResponse;
    private ExtractableResponse postsResponse;
    private ExtractableResponse commentsResponse;
    private ArrayList<String> emails;
    private int invalidEmailCount;
    private String username;
    private ArrayList users;
    private ArrayList comments;

    @Given("A user with username {string} is found")
    public void aUserWithUsernameIsFound(String username) {
        assertTrue(users.size() > 0);
    }

    @And("One or more posts can be found for the user found")
    public void oneOrMorePostsCanBeFoundForTheUserFound() {
        assertTrue(posts.size() > 0);
    }

    @And("Posts have one or more comments")
    public void postsHaveOneOrMoreComments() {
       assertTrue(comments.size() > 0);
    }

    @And("A comment has an email address")
    public void aCommentHasAnEmailAddress() {
        emails = commentsResponse.path("email");
        assertTrue(emails.size() > 0);
    }

    @And("check email addresses are valid")
    public void checkEmailAddressesAreValid() {
        emails.forEach(emailAddress -> {
            boolean isEmailValid = EmailValidation.patternMatches(emailAddress, regexPattern);
            System.out.println(emailAddress+ " : "+isEmailValid);
            if (!isEmailValid) {
                invalidEmailCount++;
            }
            assertTrue(isEmailValid);
        });
    }

    @Then("There should be no invalid email address")
    public void thereShouldBeNoInvalidEmailAddress() {
        assertTrue(invalidEmailCount == 0);
    }

    @And("No posts can be found for this user found")
    public void noPostsCanBeFoundForThisUserFound() {

    }

    @Given("{string} is provided as the username")
    public void isProvidedAsTheUsername(String arg0) {
        username  = arg0;
    }

    @When("I call the api service")
    public void iCallTheApiService() {
        userResponse = getUserByUsername(username);
        users = userResponse.path("id");
        if (userResponse.statusCode() == HttpStatus.SC_OK && !users.isEmpty()) {
            postsResponse = getPostsByUserId((int)users.get(0));
            posts = postsResponse.path("id");
            if (postsResponse.statusCode() == HttpStatus.SC_OK && !posts.isEmpty()) {
                getCommentsByPostId(posts);
                comments = postsResponse.path("id");
                if (commentsResponse.statusCode() == HttpStatus.SC_OK && !comments.isEmpty()) {
                    emails = postsResponse.path("email");
                } else {
                    System.out.println("No comments were found for postId : "+ posts.get(0));
                }
            } else {
                System.out.println("No posts were found for userId : "+ posts.get(0));
            }
        } else {
            System.out.println("No user was found for username : "+ username);
        }
    }

    private ExtractableResponse<Response> getUserByUsername(final String username) {
        return given()
                .queryParam("username", username)
                .when()
                .get(host+":"+port.concat(USERS_ENDPOINT))
                .then()
                .extract();
    }

    private ExtractableResponse<Response> getPostsByUserId(final int userId) {
        return given()
                .queryParam("userId", userId)
                .when()
                .get(host+":"+port.concat(POSTS_ENDPOINT))
                .then()
                .extract();
    }

    private void getCommentsByPostId(final ArrayList posts) {
        posts.forEach(postId -> {
            commentsResponse = given()
                    .queryParam("postId", postId)
                    .when()
                    .get(host+":"+port.concat(COMMENTS_ENDPOINT))
                    .then()
                    .assertThat()
                    .statusCode(200)
                    .body(Matchers.notNullValue())
                    .body(not(emptyArray()))
                    .extract();
        });
    }

    @Then("No user should be found")
    public void noUserShouldBeFound() {
        assertTrue(users.size() == 0);
    }

    @And("No posts should be found")
    public void noPostsShouldBeFound() {
        assertEquals(posts, null, "Posts should be null for user"+username);
    }

    @And("No comments should be found")
    public void noCommentsShouldBeFound() {
        assertEquals(posts, null, "Comments should be null for user"+username);
    }
}
