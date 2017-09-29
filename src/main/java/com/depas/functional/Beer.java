package com.depas.functional;

public class Beer {

    private final String name;
    private final String brewery;
    private final BeerType type;
    private final double abv;
    private final int ibu;
    private final int calories;
    private final double rating;

    private Beer(BeerBuilder builder){
        this.name = builder.name;
        this.brewery = builder.brewery;
        this.type = builder.type;
        this.abv = builder.abv;
        this.ibu = builder.ibu;
        this.calories = builder.calories;
        this.rating = builder.rating;
    }

    public String getName() {
        return name;
    }

    public String getBrewery() {
        return brewery;
    }

    public BeerType getType() {
        return type;
    }

    public double getAbv() {
        return abv;
    }

    public int getIbu() {
        return ibu;
    }

    public int getCalories() {
        return calories;
    }

    public double getRating() {
        return rating;
    }

    public CaloricLevel getCaloricLevel(){
        if (this.getCalories() <= 175){
            return CaloricLevel.DIET;
        }
        else if (this.getCalories() <= 250){
            return CaloricLevel.NORMAL;
        }
        else{
            return CaloricLevel.FAT;
        }
    }

    public boolean isDrinkable(){
        if (this.getRatingType()==BeerRatingType.WORLD_CLASS){
            return true;
        }
        else if ((this.getType() == BeerType.IPA || this.getType() == BeerType.PALE_ALES) &&
                this.getRatingType()==BeerRatingType.OUTSTANDING){
            return true;
        }
        return false;
    }

    public BeerRatingType getRatingType(){
        if (this.getRating()>=95){
            return BeerRatingType.WORLD_CLASS;
        }
        else if (this.getRating()>=90){
            return BeerRatingType.OUTSTANDING;
        }
        else if (this.getRating()>=85){
            return BeerRatingType.VERY_GOOD;
        }
        else if (this.getRating()>=80){
            return BeerRatingType.GOOD;
        }
        else if (this.getRating()>=75){
            return BeerRatingType.AVERAGE;
        }
        else{
            return BeerRatingType.POOR;
        }
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[name=").append(name)
                .append(", brewery=").append(brewery)
                .append(", type=").append(type)
                .append(", abv=").append(abv)
                .append(", ibu=").append(ibu)
                .append(", calories=").append(calories)
                .append(", rating=").append(rating)
                .append("]");
        return sb.toString();
    }

    public static class BeerBuilder {
        private String name;
        private String brewery;
        private BeerType type;
        private double abv=-1;
        private int ibu=-1;
        private int calories=-1;
        private double rating=-1;


        public BeerBuilder name(String name){
            this.name=name;
            return this;
        }

        public BeerBuilder brewery(String brewery){
            this.brewery=brewery;
            return this;
        }

        public BeerBuilder type(BeerType type){
            this.type=type;
            return this;
        }

        public BeerBuilder abv(double abv){
            this.abv=abv;
            return this;
        }

        public BeerBuilder ibu(int ibu){
            this.ibu=ibu;
            return this;
        }

        public BeerBuilder calories(int calories){
            this.calories=calories;
            return this;
        }

        public BeerBuilder rating(double rating){
            this.rating=rating;
            return this;
        }

        public Beer build(){
            if (name==null){
                throw new IllegalArgumentException("Name was not set.");
            }
            if (brewery==null){
                throw new IllegalArgumentException("Brewery was not set.");
            }
            if (type==null){
                throw new IllegalArgumentException("Type was not set.");
            }
            if (abv<0){
                throw new IllegalArgumentException("Abv was not set.");
            }
            if (ibu<0){
                throw new IllegalArgumentException("Ibu was not set.");
            }
            if (calories<0){
                throw new IllegalArgumentException("Calories was not set.");
            }
            if (rating<0){
                throw new IllegalArgumentException("Rating was not set.");
            }


            return new Beer(this);
        }

    }

}
