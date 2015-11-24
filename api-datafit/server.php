<?php 

require 'lib/nusoap.php';
require 'config/mysql_connect.php';
require 'model/contact.php';

$server = new soap_server("Contactos.wsdl");

function getContacts()
{	
	$contact  = Contact::getAll();
	$contacts = ['contact' => $contact];
	
	return $contacts;
}

function getContact($id)
{	
	$contact = Contact::getById($id);

	return $contact;
}

function addContact($photo, $name, $lastname, $cellphone, $phone, $email, $description)
{
	$status = Contact::insert($photo, $name, $lastname, $cellphone, $phone, $email, $description);
	
	return $status;
}

function deleteContact($id)
{
	$status = Contact::delete($id);

	return $status;
}

$HTTP_RAW_POST_DATA = isset($HTTP_RAW_POST_DATA) ? $HTTP_RAW_POST_DATA : '';
$server->service($HTTP_RAW_POST_DATA);

?>