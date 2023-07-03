public class Person {
    private String id;
    private String name;
    private String age;
    private String sex;

    public Person(String id, String name, String age, String sex){
        this.id = id;
        this.name = name;
        this.age = age;
        this.sex = sex;
    }
    public String getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public String getAge(){
        return age;
    }
    public String getSex(){
        return sex;
    }
}