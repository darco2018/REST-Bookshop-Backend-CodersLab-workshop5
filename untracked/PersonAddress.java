package pl.ust.bookshop.aaa;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity(name = "PersonAddress")
public  class PersonAddress implements Serializable {

    @Id
    @ManyToOne
    private Person person;

    @Id
    @ManyToOne
    private Address address;

    public PersonAddress() {
    }

    public PersonAddress(Person person, Address address) {
        this.person = person;
        this.address = address;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if ( this == o ) {
            return true;
        }
        if ( o == null || getClass() != o.getClass() ) {
            return false;
        }
        PersonAddress that = (PersonAddress) o;
        return Objects.equals( person, that.person ) &&
                Objects.equals( address, that.address );
    }

    @Override
    public int hashCode() {
        return Objects.hash( person, address );
    }
}
