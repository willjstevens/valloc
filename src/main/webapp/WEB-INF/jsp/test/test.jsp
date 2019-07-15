<!doctype html>
<html lang="en">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 

<head>
	<meta charset="utf-8" />
	<title>Valloc 2 Tests</title>


		
	<!-- link 
		href="/gks/r/gks.css"
		rel="stylesheet" / -->	
	
<script type="text/javascript">
/*
	$(document).ready(function (){
		
		$.ajax({
			"type": "POST", 
			"url": "/gks/admin/jobs",
			"dataType" : "json",
			"contentType" : 'application/json',
			"success": function(data, textStatus, jqXHR) {
					$('#msg').text(data.statusText);
					var jobsTable = $('#jobsTable');
					//jobsTable.append("<tr><td><b>Invite Code</b></td><td><b>Is Active</b></td></tr>");
					jobsTable.attr('border', '1');
					var jobs = data.jobs;
					for (var i = 0; i < jobs.length; i++) {
						var job = jobs[i];
						var html = 	"<tr>" + 
										"<td>" + job.jobId + "</td>" + 
										"<td>" + job.status + "</td>" + 
										"<td>" + job.consumer + "</td>" + 
										"<td>" + job.resultMessage + "</td>" + 
										"<td>" + job.note + "</td>" + 
										"<td>" + job.startTime + "</td>";
									"</tr>";
						jobsTable.append(html);
					}
				},
			"error": function(jqXHR, textStatus, errorThrown) {
				$('#msg').text("Error getting load of jobs: " + textStatus);
			}
		});
	});
	*/
</script>
</head>
<body>

	<h3>Tests</h3>
	<ul>
		<li><a href="/test/test-script-include">/test-script-include</a></li>
		<li><a href="/r/t/view-global.html">/view-global.html</a></li>
		<li><a href="/r/t/validate-page.html">/validate-page.html</a></li>
		<li><a href="/r/t/page-load.html">/page-load.html</a></li>
		<li><a href="/r/t/ajax.html">/ajax.html</a></li>
		<li><a href="/r/t/load-app-data.html">/load-app-data.html</a></li>
		<li><a href="/r/t/jquery-ui-demo.html">/jquery-ui-demo.html</a></li>
        <li><a href="/r/t/angular-test.html">/angular-test.html</a></li>
		<!-- 
		<li><a href="">/</a></li>
		 -->
	</ul>


	<h3>Site Links</h3>
	<ul>
		<li><a href="/account/create">/account/create</a></li>
        <li><a href="/stub/dashboard">/stub/dashboard</a></li>
        <li><a href="/page/new">/page/new</a></li>
        <li><a href="/stub/page/edit/dev">/stub/page/edit/dev</a></li>
        <li><a href="/login">/login</a></li>
		
	</ul>
	
<noscript>
Please turn on JavaScript to use Valloc.com
</noscript>
</body>
</html>