package mx.datafit.contactswithsoap.webservice;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.IOException;
import java.util.ArrayList;

import mx.datafit.contactswithsoap.models.Contact;

public class WebService {

    private SoapSerializationEnvelope envelope;
    private HttpTransportSE http;
    private SoapObject resultSoap;
    private SoapObject request;

    protected WebService() {}

    private static class SingletonHolder {
        private final static WebService INSTANCE = new WebService();
    }

    public static WebService getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public ArrayList<Contact> getAllContacts()  {
        ArrayList<Contact> contacts = new ArrayList<>();
        request         = new SoapObject(Config.NameSpace, Config.GetAll);
        http            = new HttpTransportSE(Config.HttpTransportSE);
        envelope        = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = false;
        envelope.setOutputSoapObject(request);

        try {
            http.call(Config.CapeConnect + Config.GetAll, envelope);
        } catch (IOException e) {
            e.printStackTrace();
            return contacts;
        } catch (Exception e) {
            e.printStackTrace();
            return contacts;
        }

        try {
            resultSoap = (SoapObject) envelope.getResponse();
            if (resultSoap != null) {

                Contact contact;

                for (int i = 0; i < resultSoap.getPropertyCount(); i++) {
                    SoapObject r = (SoapObject) resultSoap.getProperty(i);
                    contact = new Contact();
                    contact.setId(Integer.valueOf(r.getPropertyAsString("id")));
                    contact.setAvatar("http://" + Config.Host + "/" + Config.Project + "/"
                            + r.getPropertyAsString("photo"));
                    contact.setFullname(r.getPropertyAsString("fullname"));
                    contact.setCellphone(r.getPropertyAsString("cellphone"));
                    contact.setEmail(r.getPropertyAsString("email"));

                    contacts.add(contact);
                }

            } else {
                return contacts;
            }
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
            return contacts;
        }

        return contacts;
    }

    public Contact getById (int id) {
        Contact contact = new Contact();
        request         = new SoapObject(Config.NameSpace, Config.GetById);
        http            = new HttpTransportSE(Config.HttpTransportSE);
        envelope        = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = false;
        request.addProperty("id", id);
        envelope.setOutputSoapObject(request);

        try {
            http.call(Config.CapeConnect + Config.GetById, envelope);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            System.out.print(e.toString());
            return null;
        }

        try {
            resultSoap = (SoapObject) envelope.getResponse();
            if (resultSoap != null) {
                contact.setId(Integer.parseInt(resultSoap.getPropertyAsString("id")));
                contact.setAvatar(resultSoap.getPropertyAsString("photo"));
                contact.setName(resultSoap.getPropertyAsString("name"));
                contact.setLastname(resultSoap.getPropertyAsString("lastname"));
                contact.setCellphone(resultSoap.getPropertyAsString("cellphone"));
                contact.setPhone(resultSoap.getPropertyAsString("phone"));
                contact.setEmail(resultSoap.getPropertyAsString("email"));
                contact.setDescription(resultSoap.getPropertyAsString("description"));
            } else {
                return null;
            }
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
            return null;
        }

        return contact;
    }
}
