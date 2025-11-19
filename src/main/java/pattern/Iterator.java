package pattern;

import java.util.List;

public class Iterator {

    public static void main(String[] args) {
        List<Book> list = List.of(new Book("a", 10), new Book("aa", 20));

        Library library = new Library(list);
        BookIterator libraryIterator = library.getIterator();
        while (libraryIterator.hasNext()) {
            System.out.println(libraryIterator.next().name);
        }
    }
}

class Book {
    String name;
    int age;

    public Book(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
class Library {
    List<Book> list;

    public Library(List<Book> list) {
        this.list = list;
    }

    public BookIterator getIterator() {
        return new BookIterator(list);
    }

}

interface Iteratorr {
    boolean hasNext();

    Book next();
}

class BookIterator implements Iteratorr {

    List<Book> list;
    int index;

    public BookIterator(List<Book> list) {
        this.list = list;
    }

    @Override
    public boolean hasNext() {
        return index < list.size();
    }

    @Override
    public Book next() {
        if (hasNext()) {
            return list.get(index++);
        }
        return null;
    }
}