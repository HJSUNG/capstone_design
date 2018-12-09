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
        $phonenum=$_POST['phonenum'];
        $address=$_POST['address'];
        $detailaddress=$_POST['detailaddress'];
        $detail_exp=$_POST['detail_exp'];
        $deposit=$_POST['deposit'];
        $monthly=$_POST['monthly'];
        $manage=$_POST['manage'];
        $term=$_POST['term'];
        $area=$_POST['area'];
        $visible=$_POST['visible'];

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
                $stmt = $con->prepare('INSERT INTO home(title, homeid, estateid, phonenum, address, detailaddress, detail_exp, deposit, monthly, manage, term, area, visible) VALUES(:title, :homeid, :estateid, :phonenum, :address, :detailaddress, :detail_exp, :deposit, :monthly, :manage, :term, :area, :visible)');
                $stmt->bindParam(':title', $title);
                $stmt->bindParam(':homeid', $homeid);
                $stmt->bindParam(':estateid', $estateid);
                $stmt->bindParam(':phonenum', $phonenum);
                $stmt->bindParam(':address', $address);
                $stmt->bindParam(':detailaddress', $detailaddress);
                $stmt->bindParam(':detail_exp', $detail_exp);
                $stmt->bindParam(':deposit', $deposit);
                $stmt->bindParam(':monthly', $monthly);
                $stmt->bindParam(':manage', $manage);
                $stmt->bindParam(':term', $term);
                $stmt->bindParam(':area', $area);
                $stmt->bindParam(':visible',$visible);

                if($stmt->execute())
                {
                    $successMSG = "New HOME Added !";
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
                estateid: <input type = "text" name = "estateid" />
                address: <input type = "text" name = "address" />
                detailaddress: <input type = "text" name = "detailaddress" />
                detail_exp: <input type = "text" name = "detail_exp" />
                deposit: <input type = "number" name = "deposit" />
                monthly: <input type = "number" name = "monthly" />
                manage: <input type = "number" name = "manage" />
                term: <input type = "number" name = "term" />
                area: <input type = "number" name = "area" />
                <input type = "submit" name = "submit" />
            </form>

       </body>
    </html>

<?php
    }
?>
