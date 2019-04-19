<?php
	
	require_once("include.php");
	
	$tableName  = "ads";
	$tableName_cat  = "cat";
	$orderBy = "title";
	$connect  = @mysqli_connect($hostName, $username, $password, $database);

	if ($connect) {
		@mysqli_query($connect, "SET CHARACTER SET UTF8");
		
		if (isset($_GET['cat'])) {
			$cat_id = $_GET['cat'];
			$myQuery = "SELECT * FROM ".$tableName." WHERE `cat_id` = '".$cat_id."' ORDER BY ".$orderBy;
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
				} // while fetch array
			}else{
				$response['success'] = 0;
				$response['message'] = "nothing";
			}		
		}else{
			$response['error'] = "cat_id";
		}
		// echo "<pre/>";
		// print_r($response);
		echo json_encode($response/*, JSON_PRETTY_PRINT*/);
		@mysqli_close($connect);
	} // if connect