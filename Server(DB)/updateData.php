<?php

    error_reporting(E_ALL);
    ini_set('display_errors',1);

    include('connectDB.php');

    $android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");



    if( (($_SERVER['REQUEST_METHOD'] == 'POST') && isset($_POST['submit'])) || $android )
    {

        $title=$_POST['title'];
        $homeid=$_POST['homeid'];
        $estateid=$_POST['estateid'];
        $address=$_POST['address'];
        $detailaddress=$_POST['detailaddress'];
        $detail_exp=$_POST['detail_exp'];
        $deposit=$_POST['deposit'];
        $monthly=$_POST['monthly'];
        $term=$_POST['term'];

        if(empty($title)){
          $errMSG = "Input title.";
        }
        else if(empty($homeid)){
            $errMSG = "Input homeid.";
        }
        else if(empty($estateid)){
            $errMSG = "Input estateid.";
        }
        else if(empty($address)){
            $errMSG = "Input address.";
        }

        if(!isset($errMSG))

        {
            try{
                $stmt = $con->prepare('UPDATE home SET title=:title, address=:address, detailaddress=:detailaddress, detail_exp=:detail_exp, deposit=:deposit, monthly=:monthly, term=:term WHERE homeid=:homeid');
                $stmt->bindParam(':title', $title);
                $stmt->bindParam(':homeid', $homeid);
                $stmt->bindParam(':address', $address);
                $stmt->bindParam(':detailaddress', $detailaddress);
                $stmt->bindParam(':detail_exp', $detail_exp);
                $stmt->bindParam(':deposit', $deposit);
                $stmt->bindParam(':monthly', $monthly);
                $stmt->bindParam(':term', $term);

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

<?php
    if (isset($errMSG)) echo $errMSG;
    if (isset($successMSG)) echo $successMSG;


    if (!$android)
    {
?>
    <html>
       <body>

            <form action="<?php $_PHP_SELF ?>" method="POST">
                title: <input type = "text" name = "title" />
                homeid: <input type = "number" name = "homeid" />
                estateid: <input type = "number" name = "estateid" />
                address: <input type = "text" name = "address" />
                detailaddress: <input type = "text" name = "detailaddress" />
                detail_exp: <input type = "text" name = "detail_exp" />
                deposit: <input type = "number" name = "deposit" />
                monthly: <input type = "number" name = "monthly" />
                term: <input type = "number" name = "term" />
                <input type = "submit" name = "submit" />
            </form>

       </body>
    </html>

<?php
    }
?>
