<?php

    error_reporting(E_ALL);
    ini_set('display_errors',1);

    include('dbcon.php');

    $android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");


    if( (($_SERVER['REQUEST_METHOD'] == 'POST') && isset($_POST['submit'])) || $android )
    {

        $ID=$_POST['ID'];
        $item_num=$_POST['item_num'];

        if(empty($ID)){
            $errMSG = "Input ID.";
        }
        else if(empty($item_num)){
            $errMSG = "Input item_num.";
        }

        if(!isset($errMSG))

        {
            try{

                $stmt = $con->prepare('INSERT INTO bookmark(ID, item_num) VALUES(:ID, :item_num)');
                $stmt->bindParam(':ID', $ID);
                $stmt->bindParam(':item_num', $item_num);

                if($stmt->execute())
                {
                    $successMSG = "New Bookmark Added !";
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


<?php
    if (isset($errMSG)) echo $errMSG;
    if (isset($successMSG)) echo $successMSG;


    if (!$android)
    {
?>
    <html>
       <body>

            <form action="<?php $_PHP_SELF ?>" method="POST">
                ID: <input type = "text" name = "ID" />
                PW: <input type = "text" name = "PW" />
                nickname: <input type = "text" name = "nickname" />
                user_type: <input type = "text" name = "user_type"/>
                phone: <input type= "text" name = "phone"/>
                <input type = "submit" name = "submit" />
            </form>

       </body>
    </html>

<?php
    }
?>
