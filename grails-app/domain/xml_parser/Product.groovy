package xml_parser

class Product {

    static constraints = {
        productId()
        title(blank: true, nullable: true)
        description(blank: true, nullable: true)
        rating()
        price(scale: 2)
        image(blank: true, nullable: true)
    }
    static mapping = {
        description type: 'text'
        //image sqlType : 'blob'
    }

    Integer productId;
    String title;
    String description;
    Float rating;
    BigDecimal price;
    String image;   //byte[] image;

    static hasOne = [category: Category]
    static hasMany = [viewCounters : ViewCounter]

    public getCategoryName(){
        return category.name
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", productId=" + productId +
                ", title='" + title + '\'' +
                ", rating=" + rating +
                ", price=" + price +
                ", image='" + image + '\'' +
                ", version=" + version +
                '}';
    }
}
