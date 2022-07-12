package ru.netology.delivery;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import io.qameta.allure.selenide.AllureSelenide;
import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static ru.netology.delivery.DataGenerator.Registration.*;
import static ru.netology.delivery.DataGenerator.generateDate;


public class DeliveryCardTest {

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }
    @BeforeEach
    public void setUp(){
        Configuration.holdBrowserOpen = true;
        open ("http://localhost:9999");


    }

    @Test
    public void shouldSubmitDelivery() {
        UserInfo user = generateUserInfo();
        $x("//*[contains(@placeholder,\"Город\")]").val(user.getCity());
        $x("//*[contains(@placeholder,\"Дата встречи\")]").sendKeys(Keys.SHIFT, Keys.HOME, Keys.BACK_SPACE);
        $x("//*[contains(@placeholder,\"Дата встречи\")]").val(generateDate(4));
        $x("//*[contains(@name,\"name\")]").val(user.getName());
        $x("//*[contains(@name,\"phone\")]").val(user.getPhone());
        $("[data-test-id=agreement]").click();
        $(".button__text").click();
        $("[data-test-id=success-notification]").shouldBe(appear, Duration.ofSeconds(15));
        $(".notification__title").shouldHave(text("Успешно!"));
        $(".notification__content").shouldHave(text("Встреча успешно запланирована на " + generateDate(4)));
    }
    @Test
    public void shouldSubmitDeliveryWithConfirmation() {
        UserInfo user = generateUserInfo();
        $x("//*[contains(@placeholder,\"Город\")]").val(user.getCity());
        $x("//*[contains(@placeholder,\"Дата встречи\")]").sendKeys(Keys.SHIFT, Keys.HOME, Keys.BACK_SPACE);
        $x("//*[contains(@placeholder,\"Дата встречи\")]").val(generateDate(4));
        $x("//*[contains(@name,\"name\")]").val(user.getName());
        $x("//*[contains(@name,\"phone\")]").val(user.getPhone());
        $("[data-test-id=agreement]").click();
        $(".button__text").click();
        $("[data-test-id=success-notification]").shouldBe(appear, Duration.ofSeconds(15));
        $(".notification__title").shouldHave(text("Успешно!"));
        $(".notification__content").shouldHave(text("Встреча успешно запланирована на " + generateDate(4)));
        $x("//*[contains(@placeholder,\"Дата встречи\")]").sendKeys(Keys.SHIFT, Keys.HOME, Keys.BACK_SPACE);
        $x("//*[contains(@placeholder,\"Дата встречи\")]").val(generateDate(6));
        $(".button__text").click();
        $("[data-test-id=replan-notification]")
                .shouldHave(text("Необходимо подтверждение У вас уже запланирована встреча на другую дату. Перепланировать?"), Duration.ofSeconds(5));
        $(withText("Перепланировать")).click();
        $("[data-test-id=success-notification]").shouldBe(appear, Duration.ofSeconds(15));
        $(".notification__title").shouldHave(text("Успешно!"));
        $(".notification__content").shouldHave(text("Встреча успешно запланирована на " + generateDate(6)));
    }

}
