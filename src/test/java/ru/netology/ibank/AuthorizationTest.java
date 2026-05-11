package ru.netology.ibank;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataGenerator;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class AuthorizationTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    public void shouldLogInWithRegisteredUser() {
        var registeredUser = DataGenerator.Registration.getRegisteredUser("active");

        $("[data-test-id='login'] input").setValue(registeredUser.getLogin());
        $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
        $("button").click();
        $("h2").should(Condition.visible).shouldHave(Condition.text("Личный кабинет"));
    }

    @Test
    void shouldLogInWithUnregisteredUser() {
        var user = DataGenerator.Registration.getUser("active");

        $("[data-test-id='login'] input").setValue(user.getLogin());
        $("[data-test-id='password'] input").setValue(user.getPassword());
        $("button").click();
        $("[data-test-id='error-notification']")
                .should(Condition.visible)
                .shouldHave(Condition.text("Неверно указан логин или пароль"));
    }

    @Test
    void shouldLogInWithBlockedUser() {
        var blockedUser = DataGenerator.Registration.getRegisteredUser("blocked");

        $("[data-test-id='login'] input").setValue(blockedUser.getLogin());
        $("[data-test-id='password'] input").setValue(blockedUser.getPassword());
        $("button").click();
        $("[data-test-id='error-notification']")
                .should(Condition.visible)
                .shouldHave(Condition.text("Пользователь заблокирован"));
    }

    @Test
    void shouldLogInWithRegisteredUserAndWrongLogin() {
        var registeredUser = DataGenerator.Registration.getRegisteredUser("active");
        var wrongLogin = DataGenerator.getRandomLogin();

        $("[data-test-id='login'] input").setValue(wrongLogin);
        $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
        $("button").click();
        $("[data-test-id='error-notification']")
                .should(Condition.visible)
                .shouldHave(Condition.text("Неверно указан логин или пароль"));
    }

    @Test
    void shouldLogInWithRegisteredUserAndWrongPassword() {
        var registeredUser = DataGenerator.Registration.getRegisteredUser("active");
        var wrongPassword = DataGenerator.getRandomPassword();

        $("[data-test-id='login'] input").setValue(registeredUser.getLogin());
        $("[data-test-id='password'] input").setValue(wrongPassword);
        $("button").click();
        $("[data-test-id='error-notification']")
                .should(Condition.visible)
                .shouldHave(Condition.text("Неверно указан логин или пароль"));
    }
}
