<?php

    error_reporting(E_ALL);
    ini_set('display_errors',1);

    include('connectDB.php');

    $android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");



    if( (($_SERVER['REQUEST_METHOD'] == 'POST') && isset($_POST['submit'])) || $android )
    {

        $board=$_POST['board'];
        $id=$_POST['id'];
        $title=$_POST['title'];
        $detail=$_POST['detail'];

        if(empty($title)){
          $errMSG = "Input title.";
        }

        if(!isset($errMSG))

        {
            try{
                $stmt = $con->prepare('INSERT INTO board(board, id ,title, detail) VALUES(:board, :id, :title, :detail)');
                $stmt->bindParam(':board', $board);
                $stmt->bindParam(':id', $id);
                $stmt->bindParam(':title', $title);
                $stmt->bindParam(':detail', $detail);

                if($stmt->execute())
                {
                    $successMSG ="success";
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
