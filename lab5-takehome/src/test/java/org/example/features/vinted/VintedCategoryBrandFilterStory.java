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
@UseTestDataFrom("src/test/resources/testdata/vinted_filter.csv")
public class VintedCategoryBrandFilterStory {

    @Managed(uniqueSession = true)
    public WebDriver webdriver;

    @Steps
    public VintedUserSteps user;

    public String caseName;
    public String filterUrl;
    public String expectedOutcome;
    public String expectedText;

    @Qualifier
    public String getQualifier() {
        return caseName;
    }

    @Test
    public void filter_vinted_catalog_by_category_and_brand_using_ddt() {
        user.opensFilteredCatalogPage(filterUrl);

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

    public String getFilterUrl() {
        return filterUrl;
    }

    public void setFilterUrl(String filterUrl) {
        this.filterUrl = filterUrl;
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
