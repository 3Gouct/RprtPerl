package ru.netology.delivery;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.delivery.data.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class DeliveryTest {

    private static final DataGenerator.UserInfo User = DataGenerator.Registration.generateUser ("ru");
    private static final int daysForFirstMeeting = 4;
    private static final String firstMeetingDate = DataGenerator.generateDate (daysForFirstMeeting);

    @BeforeAll
    static void setUpAll() { SelenideLogger.addListener ("allure", new AllureSelenide ()); }

    @AfterAll
    static void tearDownALL (){ SelenideLogger.removeListener ("allure"); }


    @BeforeEach
    void setup () { open ("http://localhost:9999"); }

    @Test
    void successfulMeeting () {
        Configuration.holdBrowserOpen = true;
        $ ("[data-test-id='city'] input").setValue (User.getCity ());
        $ ("[data-test-id='date'] input").sendKeys (Keys.chord (Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $ ("[data-test-id='date'] input").setValue (firstMeetingDate);
        $ ("[data-test-id='name'] input").setValue (User.getName ());
        $ ("[data-test-id='phone'] input").setValue (User.getPhone ());
        $ ("[data-test-id='agreement']").click ();
        $ (byText ("Запланировать")).click ();
        $ (byText ("Успешно!")).shouldBe (visible, Duration.ofSeconds (15));
        $ ("[data-test-id='success-notification'] [class='notification__content']").shouldHave (exactText ("Встреча успешно запланирована на " + firstMeetingDate));
    }

    @Test
    void unsuccesfullMeeting () {
        Configuration.holdBrowserOpen = true;
        $ ("[data-test-id='city'] input").setValue (User.getCity ());
        $ ("[data-test-id='date'] input").sendKeys (Keys.chord (Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $ ("[data-test-id='date'] input").setValue (firstMeetingDate);
        $ ("[data-test-id='name'] input").setValue (User.getName ());
        $ ("[data-test-id='phone'] input").setValue (DataGenerator.generateWrongPhone ("en"));
        $ ("[data-test-id='agreement']").click ();
        $ (byText ("Запланировать")).click ();
        $ ("[data-test-id='phone'].input_sub").shouldHave (exactText ("Неверный формат номера мобильного телефона"));
    }
}