package pattern;

public class BuilderPattern {
    public static void main(String[] args) {
        Emp emp= new Emp.Builder().name("chandra").age(100).build();
    }
}

 class Emp {
    String name;
    int age;

    private Emp(Builder builder) {
        this.age = builder.age;
        this.name = builder.name;
    }

    public static class Builder {

        String name;
        int age;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder age(int age) {
            this.age = age;
            return this;
        }

        public Emp build() {
            return new Emp(this);
        }
    }
}