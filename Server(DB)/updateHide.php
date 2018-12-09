<?php

    error_reporting(E_ALL);
    ini_set('display_errors',1);

    include('connectDB.php');

    $android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");



    if( (($_SERVER['REQUEST_METHOD'] == 'POST') && isset($_POST['submit'])) || $android )
    {

        $visible=$_POST['visible'];
        $homeid=$_POST['homeid'];


        if(!isset($errMSG))

        {
            try{
                $stmt = $con->prepare('UPDATE home SET visible=:visible WHERE homeid=:homeid');
                $stmt->bindParam(':visible', $visible);
                $stmt->bindParam(':homeid', $homeid);

                if($stmt->execute())
                {
                    $successMSG = "HOME UPDATED !";
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
