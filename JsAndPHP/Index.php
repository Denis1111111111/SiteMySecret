<?php
include "controller.php";

$reqStr = $_GET['data'];

$controller = new controller();

$responce = $controller->execute($reqStr);

echo $responce;
