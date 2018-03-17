package com.fortitudetec.java8.ex05.flatmap;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static com.fortitudetec.java8.ex05.flatmap.FlatMapTestData.LOCATIONS;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests showing a common pattern of looping and filtering over a top-level collection, and
 * looping and filtering a sub-collection of each top-level object.
 */
@Slf4j
public class GenericProductAggregatorTest {

    /**
     * Imperative aggregation using nested loops interspersed with conditionals.
     */
    @Test
    public void testAggregateByRegionAndCondition_UsingImperativeStyle() {

        List<String> locations = new ArrayList<>();

        for (Location location : LOCATIONS) {
            if (location.isInRegion("W")) {
                for (Product product : location.getProducts()) {
                    if (product.hasName("Soccer ball") && product.getQuantity() < 15) {
                        locations.add(location.getCode());
                    }
                }
            }
        }

        assertWestRegionLocationsLowOnSoccerBalls(locations);
    }

    /**
     * Functional style aggregation using filter, flatMap, and map operations.
     */
    @Test
    public void testAggregateByRegionAndCondition_UsingFunctionalStyle_CompositeFilter() {

        List<String> locations = LOCATIONS.stream()
                .filter(location -> location.isInRegion("W"))
                .flatMap(location -> location.getProducts().stream())
                .filter(product -> product.hasName("Soccer ball") && product.getQuantity() < 15)
                .map(Product::getLocationCode)
                .collect(toList());

        assertWestRegionLocationsLowOnSoccerBalls(locations);
    }

    /**
     * Functional style aggregation using filter, flatMap, and map operations. In this case the product
     * filters are split into separate filter calls, which could possibly have additional overhead depending on the
     * actual filters and their order.
     */
    @Test
    public void testAggregateByRegionAndCondition_UsingFunctionalStyle_WithSeparateProductFilters() {

        List<String> locations = LOCATIONS.stream()
                .filter(location -> location.isInRegion("W"))
                .flatMap(location -> location.getProducts().stream())
                .filter(product -> product.hasName("Soccer ball"))
                .filter(product -> product.getQuantity() < 15)
                .map(Product::getLocationCode)
                .collect(toList());

        assertWestRegionLocationsLowOnSoccerBalls(locations);
    }

    /**
     * Functional style aggregation using filter, flatMap, and map operations. In this case the product
     * filters are split out to a separate method and a method reference is used.
     */
    @Test
    public void testAggregateByRegionAndCondition_UsingFunctionalStyle_WithCompositeFilterUsingMethodReference() {

        List<String> locations = LOCATIONS.stream()
                .filter(location -> location.isInRegion("W"))
                .flatMap(location -> location.getProducts().stream())
                .filter(this::lowOnSoccerBalls)
                .map(Product::getLocationCode)
                .collect(toList());

        assertWestRegionLocationsLowOnSoccerBalls(locations);
    }

    private boolean lowOnSoccerBalls(Product product) {
        return product.hasName("Soccer ball") && product.getQuantity() < 15;
    }

    /**
     * Test that counts and prints out the number of filter operations that are performed to show differences
     * in the number of filters performed for a composite filter versus separate filters in differing order.
     * It also prints out each filtered Product's unique id and the number of times it was filtered.
     * As you can see, the number of filter operations is dependent on the specific data being filtered
     * and the order.
     */
    @Test
    public void testAggregateByRegionAndCondition_UsingFunctionalStyle_MeasuringFilterOpCounts() {

        Predicate<Product> isSoccerBallAndLowOnStock = product ->
                product.hasName("Soccer ball") && product.getQuantity() < 15;
        Predicate<Product> isSoccerBall = product -> product.hasName("Soccer ball");
        Predicate<Product> lowOnStock = product -> product.getQuantity() < 15;

        AtomicInteger compositeCount = new AtomicInteger();
        Multiset<String> productsSeen1 = HashMultiset.create();
        List<String> locations1 = westRegionProductStream()
                .filter(product -> countingPredicate(compositeCount, productsSeen1, product, isSoccerBallAndLowOnStock))
                .map(Product::getLocationCode)
                .collect(toList());
        assertWestRegionLocationsLowOnSoccerBalls(locations1);
        System.out.println("Composite filter count: " + compositeCount);
        printProductsSeen(productsSeen1);


        Multiset<String> productsSeen2 = HashMultiset.create();
        AtomicInteger soccerBallThenLowStockCount = new AtomicInteger();
        List<String> locations2 = westRegionProductStream()
                .filter(product -> countingPredicate(soccerBallThenLowStockCount, productsSeen2, product, isSoccerBall))
                .filter(product -> countingPredicate(soccerBallThenLowStockCount, productsSeen2, product, lowOnStock))
                .map(Product::getLocationCode)
                .collect(toList());
        assertWestRegionLocationsLowOnSoccerBalls(locations2);
        System.out.println("Soccer ball, then low stock filter count: " + soccerBallThenLowStockCount);
        printProductsSeen(productsSeen2);


        Multiset<String> productsSeen3 = HashMultiset.create();
        AtomicInteger lowStockThenSoccerBallCount = new AtomicInteger();
        List<String> locations3 = westRegionProductStream()
                .filter(product -> countingPredicate(lowStockThenSoccerBallCount, productsSeen3, product, lowOnStock))
                .filter(product -> countingPredicate(lowStockThenSoccerBallCount, productsSeen3, product, isSoccerBall))
                .map(Product::getLocationCode)
                .collect(toList());
        assertWestRegionLocationsLowOnSoccerBalls(locations3);
        System.out.println("Low stock, then soccer ball filter count: " + lowStockThenSoccerBallCount);
        printProductsSeen(productsSeen3);
    }

    private Stream<Product> westRegionProductStream() {
        return LOCATIONS.stream()
                .filter(location -> location.isInRegion("W"))
                .flatMap(location -> location.getProducts().stream());
    }

    // Side-effecting Predicate that increments counter and adds the product being filtered to productsSeen.
    private boolean countingPredicate(AtomicInteger counter,
                                      Multiset<String> productsSeen,
                                      Product product,
                                      Predicate<Product> predicate) {

        LOG.trace("Filtering: {}", product);
        counter.incrementAndGet();
        productsSeen.add(product.getUniqueId());
        return predicate.test(product);
    }

    private void printProductsSeen(Multiset<String> productsSeen) {
        System.out.println("Products seen:");
        prettyPrintMultiset(productsSeen);
    }

    private <T> void prettyPrintMultiset(Multiset<T> multiset) {
        multiset.forEachEntry((value, count) -> System.out.printf("%s: %d%n", value, count));
    }

    private void assertWestRegionLocationsLowOnSoccerBalls(List<String> locations) {
        assertThat(locations)
                .describedAs("West region locations low on soccer balls")
                .containsExactlyInAnyOrder("D", "SF");
    }

}
