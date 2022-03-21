package com.swtestacademy.springbootselenium.cucumberTask.BejemasPages;

import com.swtestacademy.springbootselenium.annotations.LazyComponent;
import net.bytebuddy.implementation.bytecode.Throw;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Value;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@LazyComponent
public class BejemasHomePage extends BasePageBejemas {

    @Override
    public boolean isAt() {
        return false;
    }
    public static InheritableThreadLocal<String> imgSrc = new InheritableThreadLocal<>();

    public static final By PHOTO_OF_THE_DAY_IMAGE = By.xpath("//p[text()='Photo of the day']/../img");
    public static final By PAGINATION_NUMBERS = By.xpath("//*[starts-with(@class,'Pagination')]//li[not (@disabled)]");
    public static final By ALL_ITEMS_IMG = By.xpath("//*[starts-with(@class,'ProductCardstyle')]//img");
    public static final By FIRST_ITEM_ADD_CARD_BUTTON = By.xpath("(//button[text()='Add to Cart'])[2]");
    public static final By BASKET_ITEM_IMG = By.xpath("//*[starts-with(@class,'Navbarstyle__CartWrapper')]//img");
    public static final By ALL_ITEM_NAME_TEXT = By.xpath("//p[starts-with(@class,'ProductCardstyle__Category')]");
    public static final By ALL_ITEM_PRICE_TEXT = By.xpath("//p[starts-with(@class,'ProductCardstyle__Price')]");
    public static final By ALL_ITEM_TITLE_TEXT = By.xpath("//p[starts-with(@class,'ProductCardstyle__Name')]");
    public static final By SORTING_TYPE_DROPDOWN = By.xpath("//select");

    @Value("${application.url}")
    private String baseURL;

    public BejemasHomePage goToHomePage() {
        driver.get(baseURL);
        return this;
    }

    public BejemasHomePage checkPhotoOfTheDay() {
        String mainPhotoSource = readAttribute(PHOTO_OF_THE_DAY_IMAGE, "data-src");
        boolean flagImage = false;
        waitElement(PAGINATION_NUMBERS);
        List<WebElement> elements = driver.findElements(PAGINATION_NUMBERS);
        for (int i = 1; i < elements.size(); i++) {
            boolean src = driver.findElements(ALL_ITEMS_IMG)
                    .stream()
                    .map(items -> items.getAttribute("data-src"))
                    .anyMatch(img -> img.equals(mainPhotoSource));
            if (src) {
                flagImage = true;
                break;
            }
            click(elements.get(i));
        }
        Assert.assertTrue(flagImage);
        return this;
    }

    public BejemasHomePage hoverFirstItem() {
        hoverElement(driver.findElement(ALL_ITEMS_IMG));
        return this;
    }

    public BejemasHomePage clickAddToCardButton() {
        imgSrc.set(readAttribute(ALL_ITEMS_IMG,"src"));
        System.out.println("imgSrc.get() = " + imgSrc.get());
        waitElement(FIRST_ITEM_ADD_CARD_BUTTON);
        jsClick(FIRST_ITEM_ADD_CARD_BUTTON);
        return this;
    }

    public BejemasHomePage checkBasketForItem(){
        waitElement(BASKET_ITEM_IMG);
        staticWait(2);
        Assert.assertTrue(driver.findElements(BASKET_ITEM_IMG)
                .stream()
                .parallel()
                .map(n -> n.getAttribute("src"))
                .peek(System.out::println)
                .anyMatch(n -> n.equals(imgSrc.get())));
        return this;
    }

    public BejemasHomePage selectOneCategory(String category) {
        click(By.cssSelector("input[label='" + category.toLowerCase() + "']"));
        return this;
    }

    public BejemasHomePage checkAllTheItemName(String category) {
        Assert.assertTrue(driver.findElements(ALL_ITEM_NAME_TEXT)
                .stream()
                .parallel()
                .map(n -> n.getText())
                .allMatch(n->n.equalsIgnoreCase(category))
        );
        return this;
    }

    public BejemasHomePage selectSortingType(String sortingType){
        selectOptions(SORTING_TYPE_DROPDOWN).selectByVisibleText(sortingType);
        return this;
    }

    public void checkSortingType(String sortingType){
        if(sortingType.equalsIgnoreCase("price")){
            sortPrice();
        }else if (sortingType.equalsIgnoreCase("alphabetic")){
            sortAlphabetic();
        } else{
            throw new RuntimeException("sorting type is not valid. Check sort type");
        }
    }

    private void sortPrice(){
        List<Double> allPrices = driver.findElements(ALL_ITEM_PRICE_TEXT)
                .stream()
                .parallel()
                .map(n -> n.getText().replaceAll("\\$", ""))
                .map(Double::parseDouble)
                .collect(Collectors.toList());
        List<Double> sortedList = allPrices.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        Assert.assertEquals("sorting is not correct ",sortedList,allPrices);
    }

    private void sortAlphabetic(){
        List<String> allTitle = driver.findElements(ALL_ITEM_TITLE_TEXT)
                .stream()
                .parallel()
                .map(n -> n.getText().split(" ")[0])
                .collect(Collectors.toList());
        List<String> sortedList = allTitle.stream().sorted(Comparator.naturalOrder()).collect(Collectors.toList());
        Assert.assertEquals("sorting is not correct ",sortedList,allTitle);
    }
}

