package mx.datafit.contactswithsoap.async;

import java.util.ArrayList;

import mx.datafit.contactswithsoap.models.Contact;

public interface TaskContacts {

    public void ContactsCallback(ArrayList<Contact> contacts);

}