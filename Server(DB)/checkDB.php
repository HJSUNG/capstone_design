<?php

    error_reporting(E_ALL);
    ini_set('display_errors',1);

    include('connectDB.php');


    $android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");


    if( (($_SERVER['REQUEST_METHOD'] == 'POST') && isset($_POST['submit'])) || $android )
    {

        $homeid=$_POST['homeid'];
        $washing=(isset($_POST['washing']))?1:0;
        $refrigerator=(isset($_POST['refrigerator']))?1:0;
        $desk=(isset($_POST['desk']))?1:0;
        $bed=(isset($_POST['bed']))?1:0;
        $microwave=(isset($_POST['microwave']))?1:0;
        $closet=(isset($_POST['closet']))?1:0;

        if(empty($homeid)){
            $errMSG = "Input homeid.";
        }

        if(!isset($errMSG))
        {
            try{

                $stmt = $con->prepare('INSERT INTO roominform(homeid, washing, refrigerator, desk, bed, microwave, closet) VALUES(:homeid, :washing, :refrigerator, :desk, :bed, :microwave, :closet)');
                $stmt->bindParam(':homeid', $homeid);
                $stmt->bindParam(':washing', $washing);
                $stmt->bindParam(':refrigerator', $refrigerator);
                $stmt->bindParam(':desk', $desk);
                $stmt->bindParam(':bed', $bed);
                $stmt->bindParam(':microwave', $microwave);
                $stmt->bindParam(':closet', $closet);

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

<?php
    if (isset($errMSG)) echo $errMSG;
    if (isset($successMSG)) echo $successMSG;


    if (!$android)
    {
?>
    <html>
       <body>

            <form action="<?php $_PHP_SELF ?>" method="POST">
                homeid: <input type = "number" name = "homeid" />
                washing: <input type = "checkbox" name = "washing" />
                refrigerator: <input type = "checkbox" name = "refrigerator"  />
                desk: <input type = "checkbox" name = "desk"  />
                bed: <input type = "checkbox" name = "bed"  />
                microwave: <input type = "checkbox" name = "microwave"  />
                closet: <input type = "checkbox" name = "closet" />
                <input type = "submit" name = "submit" />
            </form>

       </body>
    </html>

<?php
    }
?>
