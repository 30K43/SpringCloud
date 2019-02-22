<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>会议规划练习管理</title>
	<meta name="decorator" content="ani"/>
	<!-- SUMMERNOTE -->
	<%@include file="/webpage/include/summernote.jsp" %>
	<script type="text/javascript">

		$(document).ready(function() {
			jp.ajaxForm("#inputForm",function(data){
				if(data.success){
				    jp.success(data.msg);
					jp.go("${ctx}/meetingtest/meetingTest");
				}else{
				    jp.error(data.msg);
				    $("#inputForm").find("button:submit").button("reset");
				}
			});

				//富文本初始化
			$('#meetingContent').summernote({
				height: 300,                
                lang: 'zh-CN',
                callbacks: {
                    onChange: function(contents, $editable) {
                        $("input[name='meetingContent']").val($('#meetingContent').summernote('code'));//取富文本的值
                    }
                }
            });
	        $('#meetingStartTime').datetimepicker({
				 format: "YYYY-MM-DD HH:mm:ss"
		    });
	        $('#meetingCloseTime').datetimepicker({
				 format: "YYYY-MM-DD HH:mm:ss"
		    });
		});
	</script>
</head>
<body>
<div class="wrapper wrapper-content">				
<div class="row">
	<div class="col-md-12">
	<div class="panel panel-primary">
		<div class="panel-heading">
			<h3 class="panel-title"> 
				<a class="panelButton" href="${ctx}/meetingtest/meetingTest"><i class="ti-angle-left"></i> 返回</a>
			</h3>
		</div>
		<div class="panel-body">
		<form:form id="inputForm" modelAttribute="meetingTest" action="${ctx}/meetingtest/meetingTest/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
				<div class="form-group">
					<label class="col-sm-2 control-label">备注信息：</label>
					<div class="col-sm-10">
						<form:textarea path="remarks" htmlEscape="false" rows="4"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">会议主持者：</label>
					<div class="col-sm-10">
						<form:input path="prolocutor" htmlEscape="false" maxlength="20"  minlength="0"   class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">参加会议者：</label>
					<div class="col-sm-10">
						<form:input path="conferree" htmlEscape="false" maxlength="20"  minlength="0"   class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">会议内容：</label>
					<div class="col-sm-10">
                        <input type="hidden" name="meetingContent" value=" ${meetingTest.meetingContent}"/>
						<div id="meetingContent">
                          ${fns:unescapeHtml(meetingTest.meetingContent)}
                        </div>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">会议开始时间：</label>
					<div class="col-sm-10">
						<div class='input-group form_datetime' id='meetingStartTime'>
							<input type='text'  name="meetingStartTime" class="form-control "  value="<fmt:formatDate value="${meetingTest.meetingStartTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
							<span class="input-group-addon">
								<span class="glyphicon glyphicon-calendar"></span>
							</span>
						</div>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">会议结束时间：</label>
					<div class="col-sm-10">
						<div class='input-group form_datetime' id='meetingCloseTime'>
							<input type='text'  name="meetingCloseTime" class="form-control "  value="<fmt:formatDate value="${meetingTest.meetingCloseTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
							<span class="input-group-addon">
								<span class="glyphicon glyphicon-calendar"></span>
							</span>
						</div>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">会议福利：</label>
					<div class="col-sm-10">
						<form:input path="meetingWelfare" htmlEscape="false" maxlength="20"  minlength="0"   class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">会议名称：</label>
					<div class="col-sm-10">
						<form:input path="meetingName" htmlEscape="false" maxlength="20"  minlength="0"   class="form-control "/>
					</div>
				</div>
		<c:if test="${mode == 'add' || mode=='edit'}">
				<div class="col-lg-3"></div>
		        <div class="col-lg-6">
		             <div class="form-group text-center">
		                 <div>
		                     <button class="btn btn-primary btn-block btn-lg btn-parsley" data-loading-text="正在提交...">提 交</button>
		                 </div>
		             </div>
		        </div>
		</c:if>
		</form:form>
		</div>				
	</div>
	</div>
</div>
</div>
</body>
</html>