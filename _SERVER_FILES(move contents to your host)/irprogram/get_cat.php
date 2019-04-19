<?php
	
	require_once("include.php");
	$tableName  = "cat";
	$tableName_ads  = "ads";
	$orderBy = "name";
	$recordsLimit = 5;
	$connect  = @mysqli_connect($hostName, $username, $password, $database);

	if ($connect) {
		@mysqli_query($connect, "SET CHARACTER SET UTF8");
		$temp1 = @mysqli_query($connect, "SELECT COUNT(*) FROM ".$tableName);
		$temp2 = @mysqli_fetch_row($temp1);
		$totalRecords = $temp2[0];
		if (isset($_GET['page'])) {
			$page = $_GET['page'] + 1;
			$offset = $page * $recordsLimit;
		}else{
			$page = 0;
			$offset = 0;
		}
		$myQuery = "SELECT * FROM ".$tableName." ORDER BY ".$orderBy." LIMIT ".$offset." ,".$recordsLimit;
		$result = @mysqli_query($connect, $myQuery);
		if ($result) {
			$response['cat'] = array();
			$response['success'] = 1;
			while ($row = @mysqli_fetch_array($result)) {
				$cat = array();

				$cat['id'] = $row['id'];
				$cat['name'] = $row['name'];

				$temp1 = @mysqli_query($connect, "SELECT COUNT(*) FROM ".$tableName_ads." WHERE `cat_id` = '".$cat['id']."'");
				$temp2 = @mysqli_fetch_row($temp1);
				$amount = $temp2[0];

				$cat['amount'] = $amount;

				array_push($response['cat'], $cat);
			}
		}else{
			$response['success'] = 0;
			$response['message'] = "nothing";
		}
		// echo "<pre/>";
		// print_r($response);
		echo json_encode($response/*, JSON_PRETTY_PRINT*/);
		@mysqli_close($connect);
	}