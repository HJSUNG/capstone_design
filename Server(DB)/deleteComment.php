<?php

    error_reporting(E_ALL);
    ini_set('display_errors',1);

    include('connectDB.php');

    $android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");



    if( (($_SERVER['REQUEST_METHOD'] == 'POST') && isset($_POST['submit'])) || $android )
    {

        $board=$_POST['board'];

        if(!isset($errMSG))

        {
            try{
                $stmt = $con->prepare('DELETE FROM comment WHERE board=:board');
                $stmt->bindParam(':board', $board);

                $stmt->execute();

            } catch(PDOException $e) {
                die("Database error: " . $e->getMessage());
            }
        }

    }

?>
