<?php

    error_reporting(E_ALL);
    ini_set('display_errors',1);

    include('connectDB.php');

    $android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");



    if( (($_SERVER['REQUEST_METHOD'] == 'POST') && isset($_POST['submit'])) || $android )
    {

        $estateid=$_POST['estateid'];

        if(!isset($errMSG))

        {
            try{
                $stmt = $con->prepare('DELETE FROM home WHERE estateid=:estateid');
                $stmt->bindParam(':estateid', $estateid);
                $stmt->execute();


            } catch(PDOException $e) {
                die("Database error: " . $e->getMessage());
            }
        }

    }

?>
