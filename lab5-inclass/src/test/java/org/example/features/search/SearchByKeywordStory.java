package org.example.features.search;

import net.serenitybdd.annotations.Issue;
import net.serenitybdd.annotations.Managed;
import net.serenitybdd.annotations.Steps;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;

import org.example.steps.serenity.EndUserSteps;

@ExtendWith(SerenityJUnit5Extension.class)
public class SearchByKeywordStory {

    @Managed(uniqueSession = true)
    public WebDriver webdriver;

    @Steps
    public EndUserSteps anna;

    @Issue("#WIKI-1")
    @Test
    public void searching_by_keyword_apple_should_display_the_corresponding_article() {
        anna.is_the_home_page();
        anna.looks_for("apple");
        anna.should_see_results();
    }

    @Issue("#WIKI-2")
    @Test
    public void searching_by_keyword_admitere_should_display_results() {
        anna.is_the_home_page();
        anna.looks_for("admitere");
    }

    @Issue("#WIKI-3")
    @Test
    public void searching_by_keyword_informatica_should_display_results() {
        anna.is_the_home_page();
        anna.looks_for("informatica");
    }
}
