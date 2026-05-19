package org.example.steps.serenity;

import net.serenitybdd.annotations.Step;
import org.example.pages.VintedPage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;

public class VintedUserSteps {

    private VintedPage vintedPage;

    @Step("Search Vinted.ro for keyword: {0}")
    public void searchesFor(String searchTerm) {
        vintedPage.searchFor(searchTerm);
    }

    @Step("Open Vinted.ro filtered catalog page: {0}")
    public void opensFilteredCatalogPage(String filterUrl) {
        vintedPage.openVintedUrl(filterUrl);
    }

    @Step("The search results URL should contain search_text")
    public void shouldBeOnSearchResultsPage() {
        assertThat("The current URL should be a Vinted search/catalog URL.",
                vintedPage.currentUrl().toLowerCase(), containsString("search_text"));
    }

    @Step("The page should contain expected valid text: {0}")
    public void shouldSeeValidResultText(String expectedText) {
        assertThat("Expected page to contain: " + expectedText,
                vintedPage.containsText(expectedText), is(true));
        assertThat("Expected the page to show Vinted result indicators.",
                vintedPage.hasResultIndicator(), is(true));
    }

    @Step("The page should show no-results or invalid-page feedback")
    public void shouldSeeInvalidOrNoResultsFeedback() {
        assertThat("Expected a no-results / invalid-page message. If Vinted changed the Romanian wording, update VintedPage.hasNoResultsOrInvalidPageIndicator().",
                vintedPage.hasNoResultsOrInvalidPageIndicator(), is(true));
    }
}
