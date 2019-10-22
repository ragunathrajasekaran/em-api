package com.rrr.expense.manager.emapi.controllers;

import com.rrr.expense.manager.emapi.models.Account;
import com.rrr.expense.manager.emapi.models.AccountBuilder;
import com.rrr.expense.manager.emapi.repositories.AccountRepository;
import com.rrr.expense.manager.emapi.services.AccountService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.restassured.path.json.JsonPath.from;

import static io.restassured.http.ContentType.JSON;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.assertj.core.api.Assertions.anyOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.springframework.data.domain.Sort.Direction.DESC;
import static org.springframework.http.HttpStatus.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@EnableAutoConfiguration
class AccountControllerTest {

    @InjectMocks
    private AccountController accountController;

    @Mock
    private AccountService accountService;

    @BeforeEach
    void setUp() {
        //https://www.cherryshoetech.com/2017/05/spring-boot-unit-test-error-could-not.html
        //http://stackoverflow.com/questions/22174665/isolated-controller-test-cant-instantiate-pageable
        final MockMvc build = MockMvcBuilders.standaloneSetup(accountController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
        RestAssuredMockMvc.mockMvc(build);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testFetchAccountByID() {
        final Account account = AccountBuilder.anAccount().withTitle("Credit Account").withId(1L).withDescription("Credit Card Account").build();

        Mockito.when(accountService.findAccountById(1L))
                .thenReturn(java.util.Optional.of(account));

        final JsonPath jsonPath = given()
                .when()
                .get("/accounts/1")
                .then()
                .statusCode(ACCEPTED.value())
                .contentType(JSON)
                .log().body()
                .extract()
                .body()
                .jsonPath();

            assertThat(jsonPath).as("Account By ID : Json Path").isNotNull();
            assertThat(jsonPath.getLong("id")).as("Account By ID : ID").isEqualTo(1L);
            assertThat(jsonPath.getString("title")).as("Account By ID : Title").isEqualTo("Credit Account");
            assertThat(jsonPath.getString("description")).as("Account By ID : Description").isEqualTo("Credit Card Account");
    }

    @Test
    void testFetchAllAccounts() {
        final ArrayList<Object> accounts = Stream.of(
                AccountBuilder.anAccount().withTitle("Credit Account").withId(1L).withDescription("Credit Card Account").build(),
                AccountBuilder.anAccount().withTitle("Debit Account").withId(2L).withDescription("Debit Card Account").build(),
                AccountBuilder.anAccount().withTitle("Saving Account").withId(3L).withDescription("My Savings Account").build()
        ).collect(Collectors.toCollection(ArrayList::new));


        final PageRequest pageRequest = PageRequest.of(0, 3, DESC, "id");
        PageImpl page = new PageImpl<>(accounts, pageRequest, accounts.size());
        Mockito.when(accountService.findAllAccounts(any(Pageable.class)))
                .thenReturn(page);

        final JsonPath jsonPath = given()
                .accept(JSON)
                .queryParam("page", 0)
                .queryParam("size", 3)
                .queryParam("sort", "id,DESC")
                .when()
                .get("/accounts")
                .then()
                .statusCode(OK.value())
                .contentType(JSON)
                .log().body()
                .extract()
                .body()
                .jsonPath();


        assertThat(jsonPath).as("Find All Accounts : Json Path").isNotNull();
        assertThat(jsonPath.getList("content").size()).as("Find All Accounts : Accounts List Count").isEqualTo(3);
        assertThat(jsonPath.getList("content.id")).as("Find All Accounts : Account Ids").contains(1,2,3);
        assertThat(jsonPath.getString("content.title[2]")).as("Find All Accounts : Account Title").isEqualTo("Saving Account");
    }
}