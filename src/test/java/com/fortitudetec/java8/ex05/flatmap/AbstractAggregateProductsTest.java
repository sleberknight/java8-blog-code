package com.fortitudetec.java8.ex05.flatmap;

import org.assertj.core.api.JUnitSoftAssertions;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.junit.Assume.assumeTrue;

public abstract class AbstractAggregateProductsTest {

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
        List<Product> sportsProducts = aggregator.aggregateByCategory(FlatMapTestData.LOCATIONS, "Sports");
        softly.assertThat(sportsProducts)
                .describedAs("Soccer balls")
                .filteredOn(product -> product.getName().equals("Soccer ball"))
                .extracting(Product::getQuantity)
                .containsExactlyInAnyOrder(25, 10, 15, 5, 0);

        List<Product> clothingProducts = aggregator.aggregateByCategory(FlatMapTestData.LOCATIONS, "Clothing");
        softly.assertThat(clothingProducts)
                .describedAs("Clothing subcategories")
                .extracting(Product::getSubCategory)
                .contains("Jerseys", "Pants", "Shorts", "Shirts")
                .doesNotContain("Soccer", "Baseball", "Snowboarding", "Surfing");
    }

    @Test
    public void testAggregateByCode() {
        List<Product> womensJeans = aggregator.aggregateByCode(FlatMapTestData.LOCATIONS, "3001");
        softly.assertThat(womensJeans)
                .describedAs("Women's jeans")
                .extracting(Product::getQuantity).containsExactlyInAnyOrder(10, 30, 25);

        List<Product> macheteSnowboards = aggregator.aggregateByCode(FlatMapTestData.LOCATIONS, "1003");
        softly.assertThat(macheteSnowboards)
                .describedAs("Ride Machete Snowboards")
                .hasSize(1);
    }

    @Test
    public void testAggregateExtended() {
        assumeTrue("skipped because is not a ProductAggregatorExtended", aggregator instanceof ProductAggregatorExtended);

        ProductAggregatorExtended aggregatorExt = (ProductAggregatorExtended) aggregator;

        List<Product> sportsJerseys = aggregatorExt.aggregate(FlatMapTestData.LOCATIONS, product ->
                product.getCategory().equals("Clothing") && product.getSubCategory().equals("Jerseys"));
        softly.assertThat(sportsJerseys)
                .describedAs("Sports jerseys")
                .extracting(Product::getName)
                .containsExactlyInAnyOrder(
                        "Atlanta Falcons Jersey", "Denver Broncos Jersey", "San Francisco 49ers Jersey");


        long countOfLocationsWithHats = aggregatorExt.aggregate(FlatMapTestData.LOCATIONS, product ->
                product.getCategory().equals("Clothing") && product.getSubCategory().equals("Hats"))
                .stream()
                .map(Product::getLocationCode)
                .distinct()
                .count();
        softly.assertThat(countOfLocationsWithHats)
                .describedAs("Number of locations carrying hats")
                .isEqualTo(2);


        List<String> locationsLowOnSoccerBalls = aggregatorExt.aggregate(FlatMapTestData.LOCATIONS, product ->
                product.getName().equals("Soccer ball") && product.getQuantity() < 15)
                .stream()
                .map(Product::getLocationCode)
                .collect(toList());
        softly.assertThat(locationsLowOnSoccerBalls)
                .describedAs("Locations low on soccer balls")
                .containsExactlyInAnyOrder("B", "D", "SF");


        Integer totalOfAllJeans = aggregatorExt.aggregate(FlatMapTestData.LOCATIONS, product -> product.getName().endsWith("Jeans"))
                .stream()
                .map(Product::getQuantity)
                .reduce(0, (total, nextQuantity) -> total + nextQuantity);
        softly.assertThat(totalOfAllJeans)
                .describedAs("Total quantity of all jeans at all locations")
                .isEqualTo(140);
    }

}
