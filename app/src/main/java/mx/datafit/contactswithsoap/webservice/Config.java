package mx.datafit.contactswithsoap.webservice;

public class Config {

    // Configuración General
    public static String Host            = "192.168.0.15";
    public static String Project         = "api-datafit";
    public static String HttpTransportSE = "http://" + Config.Host + "/" + Config.Project + "/server.php";
    public static String NameSpace       = "http://www.datafit.mx/Contactos.wsdl";
    public static String CapeConnect     = "capeconnect:Contactos:ContactosPortType#";

    // Nombre de los Web Services
    public static String GetAll  = "getContacts";
    public static String GetById = "getContact";
    public static String Add     = "addContact";
    public static String Delete  = "deleteContact";

}