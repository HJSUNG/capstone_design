<?php
error_reporting(E_ALL);
ini_set('display_errors',1);
include('connectDB.php');

$stmt = $con->prepare('SELECT * FROM board');
$stmt->execute();

if($stmt->rowCount() > 0){
  $data = array();

  while($row = $stmt->fetch(PDO::FETCH_ASSOC)){
    extract($row);

    array_push($data, array('board'=>$board, 'id'=>$id, 'title'=>$title, 'detail'=>$detail));

  }
  header('Content-Type: application/json; charset=utf8');
  $json = json_encode(array("result"=>$data), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
  echo $json;
}
 ?>
