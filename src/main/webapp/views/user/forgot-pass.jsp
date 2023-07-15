<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="/common/taglib.jsp"%>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Forgot Password</title>
<%@ include file="/common/head.jsp"%>
</head>
<body>
	<!-- Header -->
	<%@ include file="/common/header.jsp"%>
	<!-- Body -->
	<div class="container-fluid tm-mt-60">
		<div class="row tm-mb-50">
		<div class="col-lg-4 col-4 mb-5">
				
			</div>
			<div class="col-lg-4 col-4 mb-5">
				<center>
					<h2 class="tm-text-primary mb-5">Forgot Password</h2>
					<div class="form-group">
						<input type="text" name="email" id="email"
							class="form-control rounded-0" placeholder="Email" required />
					</div>
					<div class="form-group tm-text-right">
						<button type="submit" id="sendBtn" class="btn btn-primary">Send</button>
					</div>
					<h5 style="color: red" id="messageReturn"></h5>
			</div>
			<div class="col-lg-4 col-4 mb-5">
			
			</div>
		</div>
	</div>
	<!-- footer -->
	<%@ include file="/common/footer.jsp"%>
	<script>
	$('#sendBtn').click(function(){
		 $('#messageReturn').text('');
	var email = $('#email').val();
	var formData = {'email' : email};
	$.ajax({
		url : 'forgotPass',
		type: 'POST',
		data: formData
		}).then(function(data){
			$('#messageReturn').text('Password has been reset. please check your email');
			setTimeout(function() {
				windown.location.href = 'http://localhost:8080/WebProject/index';
			}, 5 * 1000);
		}).fail(function(error){
			$('#messageReturn').text('Your information not correct, please check again');
		});
	});
	</script>
</body>

</html>