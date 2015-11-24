<?php  require './config/database.php';

class Contact
{
    function __construct() {}

    public static function getAll()
    {
        $query = "SELECT id, photo, CONCAT_WS(' ', name, lastname ) AS fullname, cellphone, email FROM contacts";
        
        try {
            $cmd = Database::getInstance()->getDb()->prepare($query);
            $cmd->execute();

            return $cmd->fetchAll(PDO::FETCH_ASSOC);
        } catch (PDOException $e) {
            return false;
        }
    }

    public static function getById($contactId)
    {
        $query = "SELECT id, photo, name, lastname, cellphone, phone, email, description FROM contacts WHERE id = ?";

        try {
            $cmd = Database::getInstance()->getDb()->prepare($query);
            $cmd->execute([$contactId]);
            $row = $cmd->fetch(PDO::FETCH_ASSOC);
            
            return $row;
        } catch (PDOException $e) {
            return -1;
        }
    }

    public static function insert($photo, $name, $lastname, $cellphone, $phone, $email, $description)
    {
        $query = "INSERT INTO contacts (photo,name,lastname,cellphone,phone,email,description) VALUES (?,?,?,?,?,?,?)";
        $cmd   = Database::getInstance()->getDb()->prepare($query);

        $cmd->execute([$photo, $name, $lastname, $cellphone, $phone, $email, $description]);

        return Database::getInstance()->getDb()->lastInsertId();
    }

    public static function delete($contactId)
    {
        $query  = "DELETE FROM contacts WHERE id=?";
        $cmd    = Database::getInstance()->getDb()->prepare($query);
        $status = $cmd->execute([$contactId]);

        return $status;
    }
    
}

 ?>