<html>
<head>
<title>System Information</title>
<script src="http://code.jquery.com/jquery-latest.min.js"
	type="text/javascript"></script>

</head>
<script lang="javascript">
	$(function() {
		callServer();
	});

	function callServer() {

		$.getJSON('info', function(data) {
			outputObject(data);
		});
		setTimeout(callServer, 1000);
	}

	function outputObject(o) {
		$.each(o, function(k, v) {

			var object = $('#' + k);

			if ($.isPlainObject(v)) {
				if (object.length == 0) {
					$('#content').append('<h1 id=\''+k+'\'>' + k + '</h1>');
					outputObject(v);
					$('#content').append('<br/>');
				} else {
					outputObject(v);
				}
			} else {
				//primitave type
				if (object.length > 0) {
					//update value
					if (object.text() != v) {
						object.hide().text(v).fadeIn();
					}

				} else {
					//add
					$('#content').append(
							'<label for=\''+k+'\'>' + k
									+ '</label><span id=\''+k+'\'>' + v
									+ '</span><br/>');
				}

			}

		});

	}
</script>
<style>
label {
	font-weight: bold;
	padding-right: 10px;
}
</style>
<body>

	<div id="chart_div"></div>
	<div id="content"></div>
</body>
</html>