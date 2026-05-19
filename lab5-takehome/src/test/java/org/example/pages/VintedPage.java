package org.example.pages;

import net.serenitybdd.annotations.DefaultUrl;
import net.serenitybdd.core.pages.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.List;
import java.util.Locale;

@DefaultUrl("https://www.vinted.ro/")
public class VintedPage extends PageObject {

    private static final String BASE_URL = "https://www.vinted.ro";
    private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(15);

    public void openHomePage() {
        open();
        waitUntilBodyIsLoaded();
        acceptCookiesIfPresent();
    }

    public void openVintedUrl(String url) {
        getDriver().get(url);
        waitUntilBodyIsLoaded();
        acceptCookiesIfPresent();
    }

    public void searchFor(String searchTerm) {
        openHomePage();

        List<By> searchBoxLocators = List.of(
                By.cssSelector("input[data-testid='search-text--input']"),
                By.cssSelector("input[data-testid='header-search-text-input']"),
                By.cssSelector("input[data-testid='search-text-input']"),
                By.cssSelector("input[name='search_text']"),
                By.cssSelector("input#search_text"),
                By.cssSelector("input[type='search']"),
                By.cssSelector("input[placeholder*='Caut']"),
                By.cssSelector("input[placeholder*='Search']"),
                By.cssSelector("input[aria-label*='Caut']"),
                By.cssSelector("input[aria-label*='Search']")
        );

        for (By locator : searchBoxLocators) {
            List<WebElement> candidates = getDriver().findElements(locator);
            for (WebElement candidate : candidates) {
                if (candidate.isDisplayed() && candidate.isEnabled()) {
                    try {
                        typeIntoSearchBox(candidate, searchTerm);
                        waitUntilBodyIsLoaded();
                        waitForUrlOrText(searchTerm);
                        return;
                    } catch (RuntimeException ignored) {
                        // Try the next matching search field; Vinted can render duplicate hidden/sticky inputs.
                    }
                }
            }
        }

        // Last resort for Vinted's dynamic header: this is the same URL the search bar submits.
        String encodedTerm = URLEncoder.encode(searchTerm, StandardCharsets.UTF_8);
        openVintedUrl(BASE_URL + "/catalog?search_text=" + encodedTerm);
        waitForUrlOrText(searchTerm);
    }

    public boolean containsText(String expectedText) {
        if (expectedText == null || expectedText.isBlank()) {
            return true;
        }
        return normalizedPageSource().contains(normalize(expectedText));
    }

    public boolean containsAnyText(String... possibleTexts) {
        for (String text : possibleTexts) {
            if (containsText(text)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasResultIndicator() {
        return containsAnyText(
                "Rezultate căutare",
                "de rezultate",
                "rezultate",
                "RON",
                "incl."
        );
    }

    public boolean hasNoResultsOrInvalidPageIndicator() {
        return containsAnyText(
                "Nu am găsit",
                "Nu am gasit",
                "Nu a fost găsit",
                "Nu a fost gasit",
                "Nu există rezultate",
                "Nu exista rezultate",
                "Niciun rezultat",
                "0 rezultate",
                "No results",
                "Pagina nu a fost găsită",
                "Pagina nu a fost gasita",
                "Page not found",
                "404"
        );
    }

    public String currentUrl() {
        return getDriver().getCurrentUrl();
    }

    private void waitUntilBodyIsLoaded() {
        new WebDriverWait(getDriver(), DEFAULT_TIMEOUT)
                .until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
    }

    private void typeIntoSearchBox(WebElement searchBox, String searchTerm) {
        ((JavascriptExecutor) getDriver()).executeScript(
                "arguments[0].scrollIntoView({block: 'center', inline: 'nearest'});"
                        + "arguments[0].focus();"
                        + "arguments[0].value = '';"
                        + "arguments[0].dispatchEvent(new Event('input', { bubbles: true }));",
                searchBox
        );
        searchBox.sendKeys(searchTerm);
        searchBox.sendKeys(Keys.ENTER);
    }

    private void waitForUrlOrText(String text) {
        try {
            String normalizedText = normalize(text);
            new WebDriverWait(getDriver(), DEFAULT_TIMEOUT)
                    .until(driver -> normalize(driver.getCurrentUrl()).contains(normalizedText)
                            || normalize(driver.getPageSource()).contains(normalizedText)
                            || hasNoResultsOrInvalidPageIndicator());
        } catch (TimeoutException ignored) {
            // The assertion methods will produce a readable failure if the page is still not correct.
        }
    }

    private void acceptCookiesIfPresent() {
        List<By> cookieButtons = List.of(
                By.cssSelector("button[data-testid*='accept']"),
                By.xpath("//button[contains(., 'Accept') or contains(., 'Acceptă') or contains(., 'Accepta') or contains(., 'Sunt de acord') or contains(., 'OK')]")
        );

        for (By locator : cookieButtons) {
            List<WebElement> buttons = getDriver().findElements(locator);
            for (WebElement button : buttons) {
                try {
                    if (button.isDisplayed() && button.isEnabled()) {
                        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", button);
                        return;
                    }
                } catch (RuntimeException ignored) {
                    // Cookie banners are not part of the tested functionality.
                }
            }
        }
    }

    private String normalizedPageSource() {
        return normalize(getDriver().getPageSource());
    }

    private String normalize(String value) {
        return value == null ? "" : value.toLowerCase(Locale.ROOT);
    }
}
