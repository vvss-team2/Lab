package org.example.features.vinted;

import net.serenitybdd.annotations.Managed;
import net.serenitybdd.annotations.Steps;
import net.serenitybdd.junit.runners.SerenityParameterizedRunner;
import net.thucydides.junit.annotations.Qualifier;
import net.thucydides.junit.annotations.UseTestDataFrom;
import org.example.steps.serenity.VintedUserSteps;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

@RunWith(SerenityParameterizedRunner.class)
@UseTestDataFrom("src/test/resources/testdata/vinted_search.csv")
public class VintedSearchByKeywordStory {

    @Managed(uniqueSession = true)
    public WebDriver webdriver;

    @Steps
    public VintedUserSteps user;

    public String caseName;
    public String searchTerm;
    public String expectedOutcome;
    public String expectedText;

    @Qualifier
    public String getQualifier() {
        return caseName;
    }

    @Test
    public void search_vinted_items_by_keyword_using_ddt() {
        user.searchesFor(searchTerm);
        user.shouldBeOnSearchResultsPage();

        if ("valid".equalsIgnoreCase(expectedOutcome)) {
            user.shouldSeeValidResultText(expectedText);
        } else {
            user.shouldSeeInvalidOrNoResultsFeedback();
        }
    }

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }

    public String getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    public String getExpectedOutcome() {
        return expectedOutcome;
    }

    public void setExpectedOutcome(String expectedOutcome) {
        this.expectedOutcome = expectedOutcome;
    }

    public String getExpectedText() {
        return expectedText;
    }

    public void setExpectedText(String expectedText) {
        this.expectedText = expectedText;
    }
}
