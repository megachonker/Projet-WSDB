<?php 
$body = '';
$to = '';
if(isset($_POST['email'])) {
$from = $_POST['email'];
}
// Dynamic subject from contact form if set otherwise default value.
if(isset($_POST['subject'])) {
$subject = $_POST['subject'];
}
else {
$subject = 'Message from Contact Demo ';
}
// contact form other fields
foreach($_POST as $k => $val)
{
$body .=  ucfirst($k) . ":" . $val . "\r\n";
}
// set header
$headers = "From: $from";
// attachment work
if(!empty($_FILES['attach_file']['name'])) {
// boundary
$semi_random = md5(time());
$mime_boundary = "==Multipart_Boundary_x{$semi_random}x";
// headers for attachment
$headers .= "\nMIME-Version: 1.0\n" . "Content-Type: multipart/mixed;\n" . " boundary=\"{$mime_boundary}\"";
// multipart boundary
$body .= "This is a multi-part message in MIME format.\n\n" . "--{$mime_boundary}\n" . "Content-Type: text/plain; charset=\"iso-8859-1\"\n" . "Content-Transfer-Encoding: 7bit\n\n" . $body . "\n\n";
$body .= "--{$mime_boundary}\n";
// read attached file
$file = fopen($_FILES['attach_file']['tmp_name'],'rb');
$data = fread($file,filesize($_FILES['attach_file']['tmp_name']));
fclose($file);
$data = chunk_split(base64_encode($data));
$name = $_FILES['attach_file']['name'];
$body .= "Content-Type: {\"application/octet-stream\"};\n" . " name=\"$name\"\n" .
"Content-Disposition: attachment;\n" . " filename=\"$name\"\n" .
"Content-Transfer-Encoding: base64\n\n" . $data . "\n\n";
$body .= '--{$mime_boundary}\n';
}
if (mail ($to, $subject, $body,$headers)) {
echo "sent";
} else {
echo "fail";
}
?>