package pattern;

public class ProtoTypePattern {

    public static void main(String[] args) throws CloneNotSupportedException {
        Student student=new Student("chandra");
        Student cloned= (Student) student.clone();

    }
}

abstract class ProtoType {
    protected abstract ProtoType clone() throws CloneNotSupportedException;
}

class Student extends ProtoType {

    private String field;

    public Student(String field) {
        this.field=field;
    }

    @Override
    public ProtoType clone() throws CloneNotSupportedException {
        return new Student(field);
    }
}