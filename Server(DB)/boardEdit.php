<?php

    error_reporting(E_ALL);
    ini_set('display_errors',1);

    include('connectDB.php');

    $android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");



    if( (($_SERVER['REQUEST_METHOD'] == 'POST') && isset($_POST['submit'])) || $android )
    {

        $title=$_POST['title'];
        $detail=$_POST['detail'];
        $board=$_POST['board'];


        if(!isset($errMSG))

        {
            try{
                $stmt = $con->prepare('UPDATE board SET title=:title, detail=:detail WHERE board=:board');
                $stmt->bindParam(':title', $title);
                $stmt->bindParam(':detail', $detail);
                $stmt->bindParam(':board', $board);

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
