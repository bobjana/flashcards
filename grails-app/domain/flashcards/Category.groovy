package flashcards

class Category {

    static Category ALL = new Category(id:-1, name:"All")

    String name

    static constraints = {
    }

    String toString() {
        return name
    }

    boolean equals(o) {
        if (this.is(o)) return true;
        if (!(o instanceof Category)) return false;

        Category category = (Category) o;

        if (name != category.name) return false;

        return true;
    }

    int hashCode() {
        return (name != null ? name.hashCode() : 0);
    }
}
