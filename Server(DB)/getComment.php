<?php
error_reporting(E_ALL);
ini_set('display_errors',1);
include('connectDB.php');


$board=$_POST['board'];

$stmt = $con->prepare('SELECT * FROM comment WHERE board = :board');
$stmt->bindParam(':board', $board);
$stmt->execute();

if($stmt->rowCount() > 0){
  $data = array();

  while($row = $stmt->fetch(PDO::FETCH_ASSOC)){
    extract($row);

    array_push($data, array('board'=>$board, 'id'=>$id, 'comment'=>$comment, 'datentime'=>$datentime));

  }
  header('Content-Type: application/json; charset=utf8');
  $json = json_encode(array("result"=>$data), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
  echo $json;
}
 ?>
