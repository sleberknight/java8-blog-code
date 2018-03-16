package com.fortitudetec.java8.ex05.flatmap;

import org.assertj.core.api.JUnitSoftAssertions;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.stream.Collectors.toList;
import static org.junit.Assume.assumeTrue;

public abstract class AbstractAggregateProductsTest {

    private static final List<Location> LOCATIONS = sampleLocations();

    @Rule
    public final JUnitSoftAssertions softly = new JUnitSoftAssertions();

    private ProductAggregator aggregator;

    abstract ProductAggregator getAggregator();

    @Before
    public void setUp() {
        aggregator = getAggregator();
    }

    @Test
    public void testAggregateByCategory() {
        List<Product> sportsProducts = aggregator.aggregateByCategory(LOCATIONS, "Sports");
        softly.assertThat(sportsProducts)
                .describedAs("Soccer balls")
                .filteredOn(product -> product.getName().equals("Soccer ball"))
                .extracting(Product::getQuantity)
                .containsOnly(25, 10, 15, 5);

        List<Product> clothingProducts = aggregator.aggregateByCategory(LOCATIONS, "Clothing");
        softly.assertThat(clothingProducts)
                .describedAs("Clothing subcategories")
                .extracting(Product::getSubCategory)
                .contains("Jerseys", "Pants", "Shorts", "Shirts");
    }

    @Test
    public void testAggregateByCode() {
        List<Product> womensJeans = aggregator.aggregateByCode(LOCATIONS, "3001");
        softly.assertThat(womensJeans)
                .describedAs("Women's jeans")
                .extracting(Product::getQuantity).containsOnly(10, 30);

        List<Product> macheteSnowboards = aggregator.aggregateByCode(LOCATIONS, "1003");
        softly.assertThat(macheteSnowboards)
                .describedAs("Ride Machete Snowboards")
                .hasSize(1);
    }

    @Test
    public void testAggregateExtended() {
        assumeTrue("skipped because is not a ProductAggregatorExtended", aggregator instanceof ProductAggregatorExtended);

        ProductAggregatorExtended aggregatorExt = (ProductAggregatorExtended) aggregator;


        List<Product> sportsJerseys = aggregatorExt.aggregate(LOCATIONS, product ->
                product.getCategory().equals("Clothing") && product.getSubCategory().equals("Jerseys"));
        softly.assertThat(sportsJerseys)
                .describedAs("Sports jerseys")
                .extracting(Product::getName)
                .containsOnly("Atlanta Falcons Jersey", "Denver Broncos Jersey");


        long countOfLocationsWithHats = aggregatorExt.aggregate(LOCATIONS, product ->
                product.getCategory().equals("Clothing") && product.getSubCategory().equals("Hats"))
                .stream()
                .map(Product::getLocationCode)
                .distinct()
                .count();
        softly.assertThat(countOfLocationsWithHats)
                .describedAs("Number of locations carrying hats")
                .isEqualTo(2);


        List<String> locationsLowOnSoccerBalls = aggregatorExt.aggregate(LOCATIONS, product ->
                product.getName().equals("Soccer ball") && product.getQuantity() < 15)
                .stream()
                .map(Product::getLocationCode)
                .collect(toList());
        softly.assertThat(locationsLowOnSoccerBalls)
                .describedAs("Locations low on soccer balls")
                .containsOnly("B", "D");


        Integer totalOfAllJeans = aggregatorExt.aggregate(LOCATIONS, product -> product.getName().endsWith("Jeans"))
                .stream()
                .map(Product::getQuantity)
                .reduce(0, (total, nextQuantity) -> total + nextQuantity);
        softly.assertThat(totalOfAllJeans)
                .describedAs("Total quantity of all jeans at all locations")
                .isEqualTo(105);
    }

    private static List<Location> sampleLocations() {

        Location atlanta = newLocation("A", "Atlanta", newArrayList(
                newProduct("A", "1000", "Soccer ball", "Sports", "Soccer", 25),
                newProduct("A", "1001", "Baseball glove", "Sports", "Baseball", 15),
                newProduct("A", "2000", "Atlanta Falcons Jersey", "Clothing", "Jerseys", 30),
                newProduct("A", "3000", "Men's Jeans", "Clothing", "Pants", 15)
        ));

        Location boston = newLocation("B", "Boston", newArrayList(
                newProduct("B", "1000", "Soccer ball", "Sports", "Soccer", 10),
                newProduct("B", "1001", "Baseball glove", "Sports", "Baseball", 20),
                newProduct("B", "1002", "Football", "Sports", "Football", 50),
                newProduct("B", "1112", "Boston Red Sox Cap", "Clothing", "Hats", 40),
                newProduct("B", "3000", "Men's Jeans", "Clothing", "Pants", 15),
                newProduct("B", "3001", "Women's Jeans", "Clothing", "Pants", 10)
        ));

        Location charlotte = newLocation("C", "Charlotte", newArrayList(
                newProduct("C", "1000", "Soccer ball", "Sports", "Soccer", 15),
                newProduct("C", "1002", "Football", "Sports", "Football", 60),
                newProduct("C", "3000", "Men's Jeans", "Clothing", "Pants", 20),
                newProduct("C", "3002", "Girls Shorts", "Clothing", "Shorts", 25)
        ));

        Location denver = newLocation("D", "Denver", newArrayList(
                newProduct("D", "1000", "Soccer ball", "Sports", "Soccer", 5),
                newProduct("D", "1113", "Denver Broncos Hat", "Clothing", "Hats", 20),
                newProduct("D", "2001", "Denver Broncos Jersey", "Clothing", "Jerseys", 10),
                newProduct("D", "3000", "Men's Jeans", "Clothing", "Pants", 15),
                newProduct("D", "3001", "Women's Jeans", "Clothing", "Pants", 30),
                newProduct("D", "3003", "Women's T-shirts", "Clothing", "Shirts", 45),
                newProduct("D", "1003", "Ride Machete Snowboard", "Sports", "Snowboarding", 5)
        ));

        return newArrayList(atlanta, boston, charlotte, denver);
    }

    private static Location newLocation(String code, String name, List<Product> products) {
        return new Location(code, name, products);
    }

    private static Product newProduct(String locationCode,
                                      String code,
                                      String name,
                                      String category,
                                      String subCategory,
                                      int quantity) {
        return new Product(code, name, category, subCategory, quantity, locationCode);
    }

}
