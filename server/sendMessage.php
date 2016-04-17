<?php
//header("Content-type :text/plain;charset=UTF-8");
$SLACK_API_KEY = "xoxp-32785354118-33393129105-35334114071-8896b60a4a";
$SLACK_UPLOAD_URL = "https://slack.com/api/files.upload";
$UPLOAD_CHANNEL = "#webhook";
$UPLOAD_HEADERS = array(
	'Content-type: multipart/form-data'
	);

$UPLOAD_FILENAME = "minutes.txt";

$HACKATHON_CHANNEL_URL = "https://hooks.slack.com/services/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";
$HEADERS = array(
	'Content-type: application/json'
	);

if(isset($_POST['time'])){
	$time = trim($_POST['time']);
}

if(isset($_POST['user'])){
  $user = trim($_POST['user']);
//  echo $user;
}
if(isset($_POST['text'])){
  $text = trim($_POST['text']);
//  echo $text;
}
error_log("user=" . $user . " text=" . $text . "\n");

$sendText = $time . " - " .$user . " : " . $text;




$sendMessage = array(
	'text' => $sendText
	);
$options = array(
	"method" => "POST",
	"content" => json_encode($sendMessage),
	"header" => implode("¥r¥n", $HEADERS),
	);

$ch = curl_init();
curl_setopt($ch, CURLOPT_HTTPHEADER, $HEADERS);
curl_setopt($ch, CURLOPT_CUSTOMREQUEST, 'POST');
curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($sendMessage));
curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
curl_setopt($ch, CURLOPT_URL, $HACKATHON_CHANNEL_URL);
$result = curl_exec($ch);
echo "RETURN: " . $result;
curl_close($ch);
//$contents = file_get_contents($HACHATHON_CHANNEL_URL, false, stream_context_create($options));
//echo json_encode($contents);

if($user === "facilitator" and $text === "start"){
	//file_put_contents($UPLOAD_FILENAME, "", LOCK_EX);
	$file = fopen($UPLOAD_FILENAME, "w");
	fwrite($file, "");
	fclose($file);
} elseif ($user === "facilitator" and $text === "end") {
    $uploadMessage = array(
	    'token' => $SLACK_API_KEY,
	    'content' => file_get_contents($UPLOAD_FILENAME),
//	    'filename' => $filename,
//	    'filetype' => "text",
	    'title' => "議事録",
	    'channels' => $UPLOAD_CHANNEL
	);
	$ch_upload = curl_init();
    curl_setopt($ch_upload, CURLOPT_HTTPHEADER, $UPLOAD_HEADERS);
    curl_setopt($ch_upload, CURLOPT_CUSTOMREQUEST, 'POST');
    curl_setopt($ch_upload, CURLOPT_POSTFIELDS, $uploadMessage);
    curl_setopt($ch_upload, CURLOPT_RETURNTRANSFER, true);
    curl_setopt($ch_upload, CURLOPT_URL, $SLACK_UPLOAD_URL);
    $result = curl_exec($ch_upload);
    echo "RETURN: " . $result;
    curl_close($ch_upload);

} else {
    //file_put_contents($UPLOAD_FILENAME, $sendText, FILE_APPEND | LOCK_EX);
    $file = fopen($UPLOAD_FILENAME, "ab");
    fwrite($file, $sendText . "\n");
    fclose($file);
}

?>


