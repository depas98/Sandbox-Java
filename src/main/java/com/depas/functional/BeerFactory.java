package com.depas.functional;

import java.util.ArrayList;
import java.util.List;

public class BeerFactory {

    public static List<Beer> getBeers(){

        List<Beer> beers = new ArrayList<>();

        // APA
        Beer beer =  new Beer.BeerBuilder()
                .name("Zombie Dust")
                .brewery("3 Floyds Brewing Co.")
                .type(BeerType.APA)
                .abv(6.2)
                .ibu(60)
                .calories(185)
                .rating(98.1)
                .build();

        beers.add(beer);

        beer =  new Beer.BeerBuilder()
                .name("Galaxy Dry Hopped Fort Point Pale Ale")
                .brewery("Trillium Brewing Company")
                .type(BeerType.APA)
                .abv(6.6)
                .ibu(55)
                .calories(196)
                .rating(99.1)
                .build();

        beers.add(beer);

        beer =  new Beer.BeerBuilder()
                .name("Drumroll")
                .brewery("Odell Brewing Company")
                .type(BeerType.APA)
                .abv(5.3)
                .ibu(45)
                .calories(161)
                .rating(78.3)
                .build();

        beers.add(beer);

        // BROWN Ales
        beer =  new Beer.BeerBuilder()
                .name("Moe's Bender")
                .brewery("Surly Brewing Company")
                .type(BeerType.BROWN)
                .abv(5.52)
                .ibu(20)
                .calories(165)
                .rating(95.9)
                .build();

        beers.add(beer);

        beer =  new Beer.BeerBuilder()
                .name("Bravo Imperial Brown Ale")
                .brewery("Firestone Walker Brewing Co.")
                .type(BeerType.BROWN)
                .abv(13.2)
                .ibu(20)
                .calories(394)
                .rating(95.4)
                .build();

        beers.add(beer);

        // IPA
        beer =  new Beer.BeerBuilder()
                .name("Odell IPA")
                .brewery("Odell Brewing Company")
                .type(BeerType.IPA)
                .abv(7)
                .ibu(60)
                .calories(210)
                .rating(93)
                .build();

        beers.add(beer);

        beer =  new Beer.BeerBuilder()
                .name("Julus")
                .brewery("Tree House Brewing Company")
                .type(BeerType.IPA)
                .abv(6.8)
                .ibu(72)
                .calories(205)
                .rating(99.2)
                .build();

        beers.add(beer);

        beer =  new Beer.BeerBuilder()
                .name("Focal Banger")
                .brewery("The Alchemist Brewery")
                .type(BeerType.IPA)
                .abv(7)
                .ibu(90)
                .calories(210)
                .rating(98.7)
                .build();

        beers.add(beer);

        beer =  new Beer.BeerBuilder()
                .name("Double Dry Hopped Congress Street")
                .brewery("Trillium Brewing Company")
                .type(BeerType.IPA)
                .abv(7.2)
                .ibu(75)
                .calories(215)
                .rating(99.2)
                .build();

        beers.add(beer);

        beer =  new Beer.BeerBuilder()
                .name("Blind Pig IPA")
                .brewery("Russian River Brewing Company")
                .type(BeerType.IPA)
                .abv(6.1)
                .ibu(70)
                .calories(182)
                .rating(96)
                .build();

        beers.add(beer);

        // PALE_ALES
        beer =  new Beer.BeerBuilder()
                .name("Dale's Pale Ale")
                .brewery("Oskar Blues")
                .type(BeerType.PALE_ALES)
                .abv(6.5)
                .ibu(55)
                .calories(195)
                .rating(74.9)
                .build();

        beers.add(beer);

        beer =  new Beer.BeerBuilder()
                .name("Citra Pale Ale")
                .brewery("Upslope")
                .type(BeerType.PALE_ALES)
                .abv(5.8)
                .ibu(40)
                .calories(176)
                .rating(76.6)
                .build();

        beers.add(beer);


        return beers;
    }
}
