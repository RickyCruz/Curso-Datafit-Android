package mx.datafit.contactswithsoap.async;

import mx.datafit.contactswithsoap.models.Contact;

public interface TaskContact {

    public void ContactCallback(Contact contact);

}