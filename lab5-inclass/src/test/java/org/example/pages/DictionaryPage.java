package org.example.pages;

import net.serenitybdd.annotations.DefaultUrl;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import net.serenitybdd.core.pages.WebElementFacade;

import net.serenitybdd.core.annotations.findby.FindBy;

import net.serenitybdd.core.pages.PageObject;

import java.util.List;
import java.util.stream.Collectors;

@DefaultUrl("http://www.cs.ubbcluj.ro")
public class DictionaryPage extends PageObject {

    @FindBy(id="s")
    private WebElementFacade searchTerms;

    public void enter_keywords(String keyword) {
        searchTerms.typeAndEnter(keyword);
    }

    public void lookup_terms() {
        // search is submitted via Enter in enter_keywords
    }

    public List<String> getDefinitions() {
        WebElementFacade searchList = find(By.className("post-wrap"));
        return searchList.findElements(By.className("post")).stream()
                .map(element -> element.getText())
                .collect(Collectors.toList());
    }
}
