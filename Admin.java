package com.example.hellofx;

public class Admin extends User{
    private float WorkingHours;
    private String role;

    public Admin(){}
    public Admin(String username, String password, String dateOfBirth, Gender gender, String address, String role,float workingHours) {
        super(username, password, dateOfBirth, gender, address);
        WorkingHours = workingHours;
        this.role = role;
    }

    public float getWorkingHours() {
        return WorkingHours;
    }

    public void setWorkingHours(float workingHours) {
        WorkingHours = workingHours;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public Admin Login(String username, String password) {
        for (Admin admin: Database.getAdmins()){
            if (admin.getUsername().equals(username) && admin.getPassword().equals(password)) {
                System.out.println("Login successful for: " + username);
                return admin;
            }
        }
        return null;
    }

    @Override
    public void Registration(String username, String password, String dateOfBirth, Gender gender, String address) {
        System.out.println("Admin cannot self register");
    }

    @Override
    public String toString() {
        return super.toString()+ "Admin{" +
                "WorkingHours=" + WorkingHours +
                ", role='" + role + '\'' +
                '}';
    }
}
