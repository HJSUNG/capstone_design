<?php

error_reporting(E_ALL);
ini_set('display_errors',1);

include('dbcon.php');
include('pbkdf2.compat.php');


    $android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");


    if( (($_SERVER['REQUEST_METHOD'] == 'POST') && isset($_POST['submit'])) || $android )
    {
      $ID=$_POST['ID'];
      $PW=$_POST['PW'];

      $mysqli=mysqli_connect("$host", "$username", "$password", "$dbname");

            try{
              $query_search = "SELECT * from user_information WHERE ID = '".$ID."' ";
              $result = $mysqli->query($query_search);

              if($result->num_rows == 1) {
                $row=$result->fetch_array(MYSQLI_ASSOC);
                if(validate_password($PW, $row['PW'])) {
                  $_SESSION['ID']= $ID;
                  $return_nickname = $row['nickname'];
                  $return_phone = $row['phone'];
                  $return_type = $row['user_type'];

                  if(isset($_SESSION['ID'])) {
                    $successMSG = "$ID,$return_nickname,$return_phone,$return_type";
                  }
                  else {
                    $errMSG = "error";
                  }
                }else{
                  $errMSG = "error";
                }
              }else {
                $errMSG = "error";
            }
          }catch(PDOException $e) {
                die("Database error: " . $e->getMessage());
            }
    }

?>

<?php
    if ($successMSG != "") {
      echo $successMSG;
    } else if (isset($errMSG)) {
        echo "$errMSG";
      } else {
        echo "error";
      }



    if (!$android)
    {
?>
    <html>
       <body>

            <form action="<?php $_PHP_SELF ?>" method="POST">
                ID: <input type = "text" name = "ID" />
                PW: <input type = "text" name = "PW" />
                <input type = "submit" name = "submit" />
            </form>

       </body>
    </html>

<?php
    }
?>
