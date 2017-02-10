$(document).ready(function(){
	$(".answer-write input[type=submit]").click(addAnswer);
	$('.link-delete-article').click(deleteAnswer);
	
})

/*
 * 2016.02.10
 * 댓글 추가
 * author : aron
 */
function addAnswer(e) {
	e.preventDefault();
	if($('textarea[name=contents]').val() == "") {
		alert('내용을 입력하세요.');
		$('textarea[name=contents]').focus();
		return;
	}
	var queryString = $('.answer-write').serialize();
	var url = $('.answer-write').attr('action');
	$.ajax({
		type : 'post',
		url : url,
		data : queryString,
		dataType : 'json',
		error : function(xhr, status) {
			alert('로그인이 필요합니다.');
			location.href = "/users/loginForm";
		},
		success : function(data, status) {
			var answerTemplate = $('#answerTemplate').html();
			var template = answerTemplate.format(data.writer.userId, data.formattedCreateDate, data.contents, data.question.id, data.id);
			$('.answer-write').prepend(template);
			$('.qna-comment-count strong').text(data.question.countOfAnswer);
			
			$('textarea[name=contents]').val('');
		}
	});
}

/*
 * 2016.02.10
 * 댓글 삭제
 * author : aron
 */
function deleteAnswer(e) {
	e.preventDefault();
	
	var deleteBtn = $(this);
	var url = deleteBtn.attr('href');
	console.log(url);
	
	$.ajax({
		type : 'delete',
		url : url,
		dataType : 'json',
		error : function(xhr, status) {
			console.log("error");
		},
		success : function(data, status) {
			if(data == "login") {
				alert("로그인이 필요합니다.");
				location.href = "/users/loginForm";
			}
			if(data == "permission") {
				alert("자기 자신의 댓글만 삭제가능합니다.");
			}
			if(data == "success") {
				deleteBtn.closest("article").remove();
				$('.qna-comment-count strong').text(data.question.countOfAnswer);
			}
		}
	});
}

String.prototype.format = function() {
	  var args = arguments;
	  return this.replace(/{(\d+)}/g, function(match, number) {
	    return typeof args[number] != 'undefined'
	        ? args[number]
	        : match
	        ;
	  });
	};