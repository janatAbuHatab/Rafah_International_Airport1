package rafahairport1;

public abstract class Employee {

    private String id;
    private String name;
    private String phone;
    private double salary;
    private String hireDate;

    public Employee(String id, String name, String phone, double salary) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.salary = salary;
        this.hireDate = new java.util.Date().toString();
    }

    // Getters 
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public double getSalary() {
        return salary;
    }

    public String getHireDate() {
        return hireDate;
    }

    //Setters  
    public void setName(String name) {
        if (name != null && !name.isEmpty()) {
            this.name = name;
        }
    }

    public void setPhone(String phone) {
        if (phone != null && !phone.isEmpty()) {
            this.phone = phone;
        }
    }

    public void setSalary(double salary) {
        if (salary >= 0) {
            this.salary = salary;
        }
    }

    // Abstract Methods it will implement in the son(Pilot)
    public abstract String getRole();

    public abstract double calculateBonus();

    @Override
    public String toString() {
        return id + " - " + name + " (" + getRole() + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Employee) {
            return this.id.equals(((Employee) obj).id);
        }
        return false;
    }
}
