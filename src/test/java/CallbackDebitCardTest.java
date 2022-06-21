import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.cssClass;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CallbackDebitCardTest {
    @Test
    void shouldSubmitRequest() {
        open("http://localhost:9999");
        $("[data-test-id=name] input").setValue("Василий Васильев");
        $("[data-test-id=phone] input").setValue("+79270000000");
        $("[data-test-id=agreement]").click();
        $("button.button").click();

        $("[data-test-id=order-success]").shouldHave(
                exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    void shouldSubmitRequestIfOneWord() {
        open("http://localhost:9999");
        $("[data-test-id=name] input").setValue("Василий");
        $("[data-test-id=phone] input").setValue("+79270000000");
        $("[data-test-id=agreement]").click();
        $("button.button").click();

        $("[data-test-id=order-success]").shouldHave(
                exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    void shouldSubmitRequestIfThreeWords() {
        open("http://localhost:9999");
        $("[data-test-id=name] input").setValue("Василий Васильев Васильевич");
        $("[data-test-id=phone] input").setValue("+79270000000");
        $("[data-test-id=agreement]").click();
        $("button.button").click();

        $("[data-test-id=order-success]").shouldHave(
                exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    void shouldSubmitRequestIfUseEnglish() {
        open("http://localhost:9999");
        $("[data-test-id=name] input").setValue("Vasya");
        $("[data-test-id=phone] input").setValue("+79270000000");
        $("[data-test-id=agreement]").click();
        $("button.button").click();

        $("[data-test-id=name].input_invalid .input__sub").shouldHave(
                exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldSubmitRequestIfNumberBeginNotPlus() {
        open("http://localhost:9999");
        $("[data-test-id=name] input").setValue("Василий Васильев Васильевич");
        $("[data-test-id=phone] input").setValue("79270000000");
        $("[data-test-id=agreement]").click();
        $("button.button").click();

        $("[data-test-id=phone].input_invalid .input__sub").shouldHave(
                exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldSubmitRequestIfNumberIncorrectFormat() {
        open("http://localhost:9999");
        $("[data-test-id=name] input").setValue("Василий Васильев Васильевич");
        $("[data-test-id=phone] input").setValue("+43224556754");
        $("[data-test-id=agreement]").click();
        $("button.button").click();

        $("[data-test-id=order-success]").shouldHave(
                exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    void shouldSubmitRequestIfNumberConsistsOfLetters() {
        open("http://localhost:9999");
        $("[data-test-id=name] input").setValue("Василий Васильев Васильевич");
        $("[data-test-id=phone] input").setValue("79270000000");
        $("[data-test-id=agreement]").click();
        $("button.button").click();

        $("[data-test-id=phone].input_invalid .input__sub").shouldHave(
                exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldSubmitRequestWithoutName() {
        open("http://localhost:9999");
        $("[data-test-id=phone] input").setValue("79270000000");
        $("[data-test-id=agreement]").click();
        $("button.button").click();

        $("[data-test-id=name].input_invalid .input__sub").shouldHave(
                exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldSubmitRequestIfCheckBoxOff() {
        open("http://localhost:9999");
        $("[data-test-id=name] input").setValue("Василий Васильев Васильевич");
        $("[data-test-id=phone] input").setValue("+43224556754");
        $("button.button").click();

        $("[data-test-id=agreement]").should(cssClass("input_invalid"));
    }
}
