<?php

    error_reporting(E_ALL);
    ini_set('display_errors',1);

    include('connectDB.php');

    $android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");



    if( (($_SERVER['REQUEST_METHOD'] == 'POST') && isset($_POST['submit'])) || $android )
    {

        $board=$_POST['board'];
        $commentid=$_POST['commentid'];
        $id=$_POST['id'];
        $comment=$_POST['comment'];
        $datentime = $_POST['datentime'];

        if(!isset($errMSG))

        {
            try{
                $stmt = $con->prepare('INSERT INTO comment(board, commentid, id, comment, datentime) VALUES(:board, :commentid, :id, :comment, :datentime)');
                $stmt->bindParam(':board', $board);
                $stmt->bindParam(':commentid', $commentid);
                $stmt->bindParam(':id', $id);
                $stmt->bindParam(':comment', $comment);
                $stmt->bindParam(':datentime', $datentime);

                if($stmt->execute())
                {
                    $successMSG = "comment Added !";
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
