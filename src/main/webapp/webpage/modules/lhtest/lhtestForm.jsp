<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>测试练习管理</title>
	<meta name="decorator" content="ani"/>
	<!-- SUMMERNOTE -->
	<%@include file="/webpage/include/summernote.jsp" %>
	<script type="text/javascript">

		$(document).ready(function() {
			jp.ajaxForm("#inputForm",function(data){
				if(data.success){
				    jp.success(data.msg);
					jp.go("${ctx}/lhtest/lhtest");
				}else{
				    jp.error(data.msg);
				    $("#inputForm").find("button:submit").button("reset");
				}
			});

	        $('#practicetime').datetimepicker({
				 format: "YYYY-MM-DD HH:mm:ss"
		    });
				//富文本初始化
			$('#practicecontents').summernote({
				height: 300,                
                lang: 'zh-CN',
                callbacks: {
                    onChange: function(contents, $editable) {
                        $("input[name='practicecontents']").val($('#practicecontents').summernote('code'));//取富文本的值
                    }
                }
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
				<a class="panelButton" href="${ctx}/lhtest/lhtest"><i class="ti-angle-left"></i> 返回</a>
			</h3>
		</div>
		<div class="panel-body">
		<form:form id="inputForm" modelAttribute="lhtest" action="${ctx}/lhtest/lhtest/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
				<div class="form-group">
					<label class="col-sm-2 control-label">练习课程：</label>
					<div class="col-sm-10">
						<form:input path="course" htmlEscape="false" maxlength="20"  minlength="0"   class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">练习人：</label>
					<div class="col-sm-10">
						<sys:userselect id="practicer" name="practicer.id" value="${lhtest.practicer.id}" labelName="practicer.name" labelValue="${lhtest.practicer.name}"
							    cssClass="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">练习时间：</label>
					<div class="col-sm-10">
						<div class='input-group form_datetime' id='practicetime'>
							<input type='text'  name="practicetime" class="form-control "  value="<fmt:formatDate value="${lhtest.practicetime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
							<span class="input-group-addon">
								<span class="glyphicon glyphicon-calendar"></span>
							</span>
						</div>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">练习内容：</label>
					<div class="col-sm-10">
                        <input type="hidden" name="practicecontents" value=" ${lhtest.practicecontents}"/>
						<div id="practicecontents">
                          ${fns:unescapeHtml(lhtest.practicecontents)}
                        </div>
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