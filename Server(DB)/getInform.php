<?php
error_reporting(E_ALL);
ini_set('display_errors',1);
include('connectDB.php');

$stmt = $con->prepare('SELECT * FROM home join roominform join image where home.homeid = roominform.homeid and home.homeid = image.homeid and home.visible=1 ');
$stmt->execute();

if($stmt->rowCount() > 0){
  $data = array();

  while($row = $stmt->fetch(PDO::FETCH_ASSOC)){
    extract($row);

    array_push($data, array('title'=>$title, 'homeid'=>$homeid, 'estateid'=>$estateid, 'phonenum'=>$phonenum, 'address'=>$address, 'detailaddress'=>$detailaddress, 'detail_exp'=>$detail_exp, 'deposit'=>$deposit, 'monthly'=>$monthly, 'term'=>$term
                            ,'washing'=>$washing, 'refrigerator'=>$refrigerator, 'desk'=>$desk, 'bed'=>$bed, 'microwave'=>$microwave, 'closet'=>$closet
                            , 'imageone'=>$imageone, 'imagetwo'=>$imagetwo, 'imagethree'=>$imagethree, 'manage'=>$manage, 'area'=>$area));

  }
  header('Content-Type: application/json; charset=utf8');
  $json = json_encode(array("result"=>$data), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
  echo $json;
}
 ?>
