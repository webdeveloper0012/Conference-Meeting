<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org">
   <head>
      <meta charset="utf-8" />
      <title>Spring Boot + JPA + Datatables</title>
      <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
      <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.6.4/css/bootstrap-datepicker.css">
      <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.47/css/bootstrap-datetimepicker.css">
      <link rel="stylesheet" href="https://cdn.datatables.net/1.10.12/css/jquery.dataTables.min.css">
      
      <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
      <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
      <script src="https://cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js"></script>
      <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.6.4/js/bootstrap-datepicker.js"></script>
      <script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.13.0/moment.min.js"></script>
      <script src=" https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.13.0/locale/nl.js"></script>
      <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.47/js/bootstrap-datetimepicker.min.js"></script>
      
   </head>
   <body>
   
   	  <div class="container">
   	  <br/>
   	  <div class="alert alert-success row" style="display:none;">
   	  <a href="#" class="close" data-dismiss="alert" aria-label="close" title="close">×</a>
  <strong>Success!</strong> Event Booked, Kindly check mail
</div>
   	  <br/>
      <div class="row">
         <button type="button" class="btn btn-primary" data-toggle="collapse" data-target="#filter-panel">
         <span class="glyphicon glyphicon-cog"></span> Advanced Search
         </button>
      </div>
      <br/>
      <div id="filter-panel" class="collapse filter-panel row">
         <div class="panel panel-default">
               <div class="panel-body">
                  <div class="form-group col-sm-3">
                     <label class="filter-col"  for="pref-orderby">Location:</label>
                     <select id="pref-orderby" class="form-control devlocation">
                        <option>Bexhill</option>
                        <option>Norfolk</option>
                        <option>Derbyshire</option>
                     </select>
                  </div>
                  
                 <div class="form-group col-sm-3">
                      <label class="filter-col"  for="pref-orderby">Timing:</label>
                       <select class="form-control devTiming">
					         <c:forEach items="${duration}"  var="duration">
					         	<option value="${duration.getId()}">${duration.getStartTime()}  To  ${duration.getEndTime()} 
					         </option>
					         </c:forEach>
					 </select>
                  </div>
                  
                  <div class="form-group col-sm-3">
                     <label class="filter-col"  for="pref-orderby">Date:</label>
                     <div class="input-group date" data-provide="datepicker">
                        <input type="text" class="form-control devdate">
                        <div class="input-group-addon">
                           <span class="glyphicon glyphicon-th"></span>
                        </div>
                     </div>
                  </div>
                  
                  <div class="form-group col-sm-3">
                  	<button type="button" class="btn btn-primary devsearch form-control">Search</button>
                  	<button type="button" class="btn btn-primary devclear form-control">Clear</button>
                  </div>
            </div>
         </div>
      </div>
      <br/>
      <div class="row">
         <table id="meetingtbl" class="display">
            <!-- Header Table -->
            <thead>
               <tr>
                  <th>Room Name</th>
                  <th>Event Name</th>
                  <th>Location</th>
                  <th>Start Time</th>
                  <th>End Time</th>
                  <th>Schedule Date</th>
                  <th>Book By</th>
               </tr>
            </thead>
         </table>
      </div>
      <script type="text/javascript">
      jQuery.fn.dataTableExt.oApi.fnPagingInfo = function (oSettings) {
    		return {
    			"iStart": oSettings._iDisplayStart,
    			"iEnd": oSettings.fnDisplayEnd(),
    			"iLength": oSettings._iDisplayLength,
    			"iTotal": oSettings.fnRecordsTotal(),
    			"iFilteredTotal": oSettings.fnRecordsDisplay(),
    			"iPage": oSettings._iDisplayLength === -1 ?
    				0 : Math.ceil(oSettings._iDisplayStart / oSettings._iDisplayLength),
    			"iTotalPages": oSettings._iDisplayLength === -1 ?
    				0 : Math.ceil(oSettings.fnRecordsDisplay() / oSettings._iDisplayLength)
    		};
    	};

    	function bookBtn(obj){
    		debugger;
    		$("#book .devbooksavebtn").attr("meetingid",$(obj).attr('meetingid')); 
    		$("#book .devbooksavebtn").attr("durationid",$(obj).attr('durationid')); 
    		$("#book .devbooksavebtn").attr("scheduleDate",$(obj).attr('scheduleDate')); 
    		
    	}
    	
    	$(document).ready(function () {
    		$('.datetimepicker3').datepicker({
    			autoclose: true
    		});
    		
    		var search = false;
    		var table = $('#meetingtbl').DataTable({
    			"bFilter": false,
    			"processing": true,
    			"serverSide": true,
    			"ajaxSource": '${pageContext.request.contextPath}/fetchAll',
    			"serverMethod": "POST",
    			"fnServerParams": function (aoData) {
                    //var includeUsuallyIgnored = $("#include-checkbox").is(":checked");
                    aoData.push({name: "location", value: $('.devlocation').val().trim()});
                    aoData.push({name: "durationid", value: $('.devTiming').val().trim()});
                    aoData.push({name: "time", value: $('.devTiming option:selected').text().trim()});
                    aoData.push({name: "date", value: $('.devdate').val().trim()});
                    aoData.push({name: "issearch", value: search});
                },
    			"destroy": true,
    			"columns": [{
    					"data": "meetingRoomDetails.name"
    				},
    				{
    					"data": "eventName"
    				},
    				{
    					"data": "meetingRoomDetails.location"
    				},
    				{
    					"data": "duration.startTime"
    				},
    				{
    					"data": "duration.endTime"
    				},
    				{
    					"data": "scheduleDate"
    				},
    				{
    					"data": "owner.firstName",
    					"render": function(data, type, full) { 
    							return (data != null) ? data : '<button type="button" scheduleDate = "'+full.scheduleDate+'" durationid= '+full.duration.id+' meetingid= '+full.meetingRoomDetails.id+' onclick="bookBtn(this);" class="btn btn-primary devbook form-control" data-toggle="modal" data-target="#book">Book</button>'
    					}
    				}
    			]
    		});
    		$('.datetimepicker3').datepicker({
    			autoclose: true
    		});
    		$('.devsearch').on("click", function () {
    			search = true;
    			table.draw();
    			search = false;
    		});
    		$('.devclear').on("click", function () {
    			table.draw();
    		});
    		$('.devbooksavebtn').on("click", function () {
    			$.ajax({
    				  type: "POST",
    				  url: "${pageContext.request.contextPath}/book",
    				  data: {
    					  meetingid : $(this).attr('meetingid'),
    					  durationid: $(this).attr('durationid'),
    					  eventname: $("#eventname").val(),
    					  scheduleDate: $(this).attr('scheduleDate')
    				  },
    				  cache: false,
    				  success: function(data){
    					  $('#book').modal('toggle');
    					  $('#meetingtbl').dataTable()._fnAjaxUpdate();
    					  $("div.alert-success").show().delay(5000).fadeOut();
    				  }
    				});
    			
    		});
    	});
      </script>
       </div>
       
       
       <!-- Modal -->
<div class="modal fade" id="book" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-body">
        Event Name:<input type="text" id="eventname"/>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
        <button type="button" class="btn btn-primary devbooksavebtn" durationid="" meetingid="" >Save changes</button>
      </div>
    </div>
  </div>
</div>


   </body>
</html>