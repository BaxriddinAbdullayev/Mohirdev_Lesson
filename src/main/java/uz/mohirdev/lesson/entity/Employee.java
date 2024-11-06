package uz.mohirdev.lesson.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import org.springframework.lang.NonNull;

import java.io.Serializable;
import java.util.Set;

@Entity
public class Employee implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String lastName;

    @NonNull
    @Size(min = 5,max = 150)
    @Email
    @Column(name = "email",length = 150,unique = true,nullable = false)
    private String emil;

    @ManyToOne
    private Department department;

    @OneToOne(optional = false)
    @JoinColumn(name = "account_id",unique = true,nullable = false)
    private Account account;

    @OneToMany
    private Set<Item> items;

    @ManyToMany
    @JoinTable(
            name = "dev_employe_project",
            joinColumns = {@JoinColumn(name = "employee_id",referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "project_id",referencedColumnName = "id")}
    )
    private Set<Project> projects;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmil() {
        return emil;
    }

    public void setEmil(String emil) {
        this.emil = emil;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Set<Item> getItems() {
        return items;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }
}
