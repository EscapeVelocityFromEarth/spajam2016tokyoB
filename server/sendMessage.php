<?php
$HACKATHON_CHANNEL_URL = "https://hooks.slack.com/services/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";
$HEADERS = array(
	'Content-type: application/json'
	);

if(isset($_POST['user'])){
  $user=$_POST['user'];
//  echo $user;
}
if(isset($_POST['text'])){
  $text=$_POST['text'];
//  echo $text;
}
$sendText = $user . " : " . $text;
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

?>


