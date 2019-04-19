<?php
	require_once("include.php");
	
	$tableName  = "ads";
	$tableName_cat  = "cat";
	$orderBy = "title";
	$recordsLimit = 6;
	$connect  = @mysqli_connect($hostName, $username, $password, $database);

	if ($connect) {
		@mysqli_query($connect, "SET CHARACTER SET UTF8");
		$temp1 = @mysqli_query($connect, "SELECT COUNT(*) FROM ".$tableName);
		$temp2 = @mysqli_fetch_row($temp1);
		$totalRecords = $temp2[0];
		if (isset($_GET['page'])) {
			$page = $_GET['page'];
			$offset = $page * $recordsLimit;
		}else{
			$page = 0;
			$offset = 0;
		}
		$myQuery = "SELECT * FROM ".$tableName." ORDER BY ".$orderBy." LIMIT ".$offset." ,".$recordsLimit;
		$result = @mysqli_query($connect, $myQuery);
		if ($result) {
			$response['ads'] = array();
			$response['success'] = 1;
			while ($row = @mysqli_fetch_array($result)) {
				$ads = array();

				$ads['id'] = $row['id'];
				$ads['title'] = $row['title'];
				$ads['intro'] = $row['intro'];
				$ads['desc'] = $row['description'];
				$ads['image'] = $siteName . $row['image'];
				$ads['seller'] = $row['seller'];
				$ads['email'] = $row['email'];
				$ads['phone'] = $row['phone'];
				$ads['date'] = $row['date'];

				$temp1 = @mysqli_query($connect, "SELECT `name` FROM ".$tableName_cat." WHERE `id` = '".$row['cat_id']."'");
				$temp2 = @mysqli_fetch_row($temp1);
				$catName = $temp2[0];
				$ads['cat'] = $catName;
				array_push($response['ads'], $ads);
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