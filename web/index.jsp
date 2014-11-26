<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>
    <head>
        <title>TODO supply a title</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css">

    </head>
    <body>

        <div class="panel panel-default">
            <div class="panel-heading">Employee Registration Form</div>
            <div class="panel-body">
                <form method="post" action="Myservlet" role="form" >
                    <input type="hidden" name="action" value="addemp" >
                    <div class="form-group">    
                        <label for="empname">Emp Name &nbsp;: </label>
                        <input id="empname" name="empname" type="text" >
                    </div>
                    <div class="form-group">
                        <label for="empsalary">Emp Salary : </label>
                        <input id="empsalary" name="empsalary" type="text">
                    </div>
                    <div class="form-group">
                        <input type="submit" value="submit" class="btn btn-success">
                    </div>
                </form>
                <br >
            </div>
        </div>
        <div>

            <table id="emptbl" class="table table-striped">
                <thead>
                    <tr>
                        <th>#ID</th>
                        <th>Name</th>
                        <th>Salary</th>
                        <th>Edit</th>
                        <th>Delete</th>
                    </tr>
                </thead>
                <tbody>

                </tbody>

            </table>

        </div>
        <%
            if(request.getParameterMap().containsKey("edited") && Integer.parseInt(request.getParameter("edited"))==0){
                out.println("<div class='alert alert-success' role='alert'><span aria-hidden='true' class='glyphicon glyphicon-thumbs-up'></span>Record successfully edited...!</div>");
            }else if(request.getParameterMap().containsKey("deleted") && Integer.parseInt(request.getParameter("deleted"))==0){
                out.println("<div class='alert alert-success' role='alert'><span aria-hidden='true' class='glyphicon glyphicon-thumbs-up'></span>Record successfully deleted...!</div>");
            }else{
                
            }
        %>
        <!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
        <h4 class="modal-title" id="myModalLabel">Edit Employee</h4>
      </div>
        <form method="post" action="Myservlet" role="form">
            
            <div class="modal-body">
                 <input type="hidden" name="action" value="editEmp" >
                 <input type="hidden" name="id" id="empid" value="0">
                    <div class="form-group">    
                        <label for="editempname">Emp Name &nbsp;: </label>
                        <input id="editempname" class="form-control" name="editempname" type="text" >
                    </div>
                    <div class="form-group">
                        <label for="editempsalary">Emp Salary : </label>
                        <input id="editempsalary" class="form-control" name="editempsalary" type="text">
                    </div>
                
            </div>
            <div class="modal-footer">
              <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
              <button type="submit" class="btn btn-primary">Save changes</button>
            </div>
        </form>
    </div>
  </div>
</div>
        <script src="js/jquery.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/js/bootstrap.min.js"></script>
        <script type="text/javascript">
            $(document).ready(function () {
                
                
        //var getDelete = function(id){};
                
                $.ajax({
                    type: "post",
                    url: "Myservlet",
                    data: {
                        action: "viewemp",
                    },
                    success: function (mydata) {
                        myJSON = $.parseJSON(mydata);
                        //alert(JSON.stringify(mydata));

                        $.each(myJSON, function (key, item) {
                            $("#emptbl tbody").append("<tr id="+item.empid+"><td>" +
                                    item.empid +
                                    "</td><td>" +
                                    item.empname +
                                    "</td><td>" +
                                    item.empsalary +
                                    "</td><td>" +
                                    "<a href='#' class='edit' data-toggle='modal' data-target='#myModal'><span class='glyphicon glyphicon-pencil'></span></a></td><td>"+
                                    "<a href='Myservlet?action=delete&id="+item.empid+"' class='delete' ><span class='glyphicon glyphicon-trash'></span></a></td></tr>");
                        });
                    },
                    error: function (mydata) {
                        alert("error");
                    }
                });
                
                $(document).on('click','tbody tr td a.edit',function(e){
                    e.preventDefault();
                   
                    var id = $(this).parents('tr').attr('id');
                    
                    $.ajax({
                        type: "post",
                        url: "Myservlet",
                        data: {
                            'action' : "getEmp",
                            'id' : id,
                        },
                        success: function (mydata) {
                            myJSON = $.parseJSON(mydata);
                           // alert(JSON.stringify(mydata));
                            $.each(myJSON, function (key, item) {
                                $('#editempname').val(item.empname);
                                $('#editempsalary').val(item.empsalary);
                                $('#empid').val(item.empid);
                            });
                        },
                        error: function (mydata) {
                            alert("error");
                        }
                    });
                });
            });
        </script>
    </body>
</html>
