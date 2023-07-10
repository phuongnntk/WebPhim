<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="/common/taglib.jsp"%>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>${video.title}</title>
<%@ include file="/common/head.jsp"%>
</head>
<body>
	<!-- Header -->
	<%@ include file="/common/header.jsp"%>
	<!-- Body -->
	<div class="container-fluid tm-container-content tm-mt-60">
		<div class="row mb-4">
			<h2 class="col-12 tm-text-primary">${video.title}</h2>
		</div>
		<div class="row tm-mb-90">
			<div class="col-xl-8 col-lg-7 col-md-6 col-sm-12">
				<iframe id="tm-video"
					src="https://www.youtube.com/embed/${video.href}"
					style="height: 700px !important"></iframe>
			</div>
			<div class="col-xl-4 col-lg-5 col-md-6 col-sm-12">
				<div class="tm-bg-gray tm-video-details">
					<c:if test="${not empty sessionScope.currentUser}">
						<div class="text-center mb-5">
							<button id="likeOrUnlikeBtn" class="btn btn-primary tm-btn-big">
								<c:choose>
									<c:when test="${flagLikeBtn == true}">
									UnLike
									</c:when>
									<c:otherwise>Like</c:otherwise>
								</c:choose>
							</button>
						</div>
						<div class="text-center mb-5">
							<a href="#" class="btn btn-primary tm-btn-big">Share</a>
						</div>
					</c:if>
					<div class="mb-4">
						<h3 class="tm-text-gray-dark mb-3">Description</h3>
						<p>${video.description}</p>
					</div>
					<input id="videoIdHdn" type="hidden" value="${video.href}" />
				</div>
			</div>
		</div>
	</div>
	<!-- footer -->
	<%@ include file="/common/footer.jsp"%>
	<script>
	$('#likeOrUnlikeBtn').click(function(){
		var videoId = $('#videoIdHdn').val();
		$.ajax({
			url: 'video?action=like&id=' + videoId
		}).then(function(data){
			var text = $('#likeOrUnlikeBtn').text();
			if(text.indexOf('Like') != -1){
				$('#likeOrUnlikeBtn').text('UnLike');
			}else {
				$('#likeOrUnlikeBtn').text('Like');
			}
		}).fail(function(error){
			alert('Oops!!! please try again ^^');
		});
	});
	</script>
</body>
</html>