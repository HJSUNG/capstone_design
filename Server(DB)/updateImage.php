<?php

    error_reporting(E_ALL);
    ini_set('display_errors',1);

    include('connectDB.php');


    $android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");


    if( (($_SERVER['REQUEST_METHOD'] == 'POST') && isset($_POST['submit'])) || $android )
    {

        $homeid=$_POST['homeid'];
        $imageone=$_POST['imageone'];
        $imagetwo=$_POST['imagetwo'];
        $imagethree=$_POST['imagethree'];

        if(!isset($errMSG))
        {
            try{

                $stmt = $con->prepare('UPDATE image SET imageone=:imageone, imagetwo=:imagetwo, imagethree=:imagethree WHERE homeid=:homeid');
                $stmt->bindParam(':homeid', $homeid);
                $stmt->bindParam(':imageone', $imageone);
                $stmt->bindParam(':imagetwo', $imagetwo);
                $stmt->bindParam(':imagethree', $imagethree);
                if($stmt->execute())
                {
                }
                else
                {
                    $errMSG = "ERROR";
                }

            } catch(PDOException $e) {
                die("Database error: " . $e->getMessage());
            }
        }
    }
?>
